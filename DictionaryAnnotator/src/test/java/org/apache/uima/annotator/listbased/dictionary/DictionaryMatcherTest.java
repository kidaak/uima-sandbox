/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.uima.annotator.listbased.dictionary;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.uima.UIMAFramework;
import org.apache.uima.annotator.dict_annot.dictionary.Dictionary;
import org.apache.uima.annotator.dict_annot.dictionary.DictionaryBuilder;
import org.apache.uima.annotator.dict_annot.dictionary.DictionaryMatch;
import org.apache.uima.annotator.dict_annot.dictionary.impl.HashMapDictionaryBuilder;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.XCASDeserializer;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.metadata.FsIndexDescription;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.test.junit_extension.JUnitExtension;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.XMLInputSource;

/**
 * Tests if the dictionary matches works correctly.
 */
public class DictionaryMatcherTest extends TestCase {

   /**
    * Test matcher that takes the text and the dictionary and adds all matches
    * to the array list.
    * 
    * @param dict
    *           dictionary to use
    * @param tokens
    *           tokenized string
    * @param matches
    *           match list
    */
   public void match(Dictionary dict, AnnotationFS[] annotFSs,
         ArrayList<String> matches) {
      int currentPos = 0;
      while (currentPos < annotFSs.length) {

         DictionaryMatch dictMatch = dict.matchEntry(currentPos, annotFSs);
         if (dictMatch != null) {
            // we have found a match starting at currentPos
            int matchLength = dictMatch.getMatchLength();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < matchLength; i++) {
               buffer.append(annotFSs[currentPos + i].getCoveredText());
               buffer.append(" ");
            }
            matches.add(buffer.toString().trim());
            // adjust current token position in case of multi word match
            currentPos = currentPos + matchLength;
         } else {
            // no match found, go to the next token
            currentPos++;
         }
      }
   }

   /**
    * tests the dictionary matching for single words and multi words.
    * 
    * @throws Exception
    */
   public void testDictionaryMatchingOutsideAnnotator() throws Exception {

      // create the dictionary
      File dictFile = JUnitExtension
            .getFile("DictionaryMatchTests/MultiWords.txt");
      DictionaryBuilder dictBuilder = new HashMapDictionaryBuilder(true, true);
      dictBuilder.createDictionary(dictFile, "UTF-8");
      Dictionary dict = dictBuilder.getDictionary();

      // -- read input XCAS and create a CAS --

      // read type system file
      File typeSystemFile = JUnitExtension
            .getFile("DictionaryMatchTests/Token.xml");
      // get XCAS file
      File xcasFile = JUnitExtension.getFile("DictionaryMatchTests/Token.xcas");

      // parse type system file
      Object descriptor = UIMAFramework.getXMLParser().parse(
            new XMLInputSource(typeSystemFile));
      TypeSystemDescription tsDesc = (TypeSystemDescription) descriptor;

      // create a CAS and add XCAS content
      CAS cas = CasCreationUtils.createCas(tsDesc, null,
            new FsIndexDescription[0]);
      SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
      XCASDeserializer xcasDeserializer = new XCASDeserializer(cas
            .getTypeSystem());
      parser.parse(xcasFile, xcasDeserializer.getXCASHandler(cas));

      // get dictionary match input type
      Type inputType = cas.getTypeSystem().getType(
            "org.apache.uima.TokenAnnotation");
      Assert
            .assertNotNull("Type org.apache.uima.TokenAnnotation not found in the type system"
                  + inputType);

      // copy input match type annotations to an array
      FSIterator it = cas.getAnnotationIndex(inputType).iterator();
      ArrayList<AnnotationFS> inputTypeAnnots = new ArrayList<AnnotationFS>();
      while (it.hasNext()) {
         inputTypeAnnots.add((AnnotationFS) it.next());
      }
      AnnotationFS[] annotFSs = inputTypeAnnots.toArray(new AnnotationFS[] {});

      // check matches for the CAS
      ArrayList<String> matches = new ArrayList<String>();
      match(dict, annotFSs, matches);

      // check match results
      Assert.assertEquals("new", matches.get(0));
      Assert.assertEquals("new york", matches.get(1));
      Assert.assertEquals("new orleans", matches.get(2));
      Assert.assertEquals("new york city", matches.get(3));
   }
}
