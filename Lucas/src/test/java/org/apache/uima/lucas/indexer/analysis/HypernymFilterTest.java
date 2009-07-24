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

package org.apache.uima.lucas.indexer.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.lucene.analysis.Token;
import org.apache.uima.lucas.indexer.analysis.HypernymFilter;
import org.apache.uima.lucas.indexer.test.util.CollectionTokenStream;

public class HypernymFilterTest extends TestCase {

	public void testNext() throws IOException{
		List<Token> tokens = new ArrayList<Token>();
		tokens.add(new Token("token1", 0, 6));
		tokens.add(new Token("token2", 6, 11));
		tokens.add(new Token("token3", 11, 17));
		tokens.add(new Token("token4", 17, 23));
		
		CollectionTokenStream tokenStream = new CollectionTokenStream(tokens);
		Map<String, List<String>> hypernyms = new HashMap<String, List<String>>();
		List<String> tokenHypernyms = new ArrayList<String>();
		tokenHypernyms.add("token21");
		tokenHypernyms.add("token22");
		tokenHypernyms.add("token23");
		hypernyms.put("token2", tokenHypernyms);
		
		tokenHypernyms = new ArrayList<String>();
		tokenHypernyms.add("token41");
		tokenHypernyms.add("token42");
		hypernyms.put("token4", tokenHypernyms);
		
		HypernymFilter tokenFilter = new HypernymFilter(tokenStream, hypernyms);
		
		Token nextToken = tokenFilter.next();		
		assertEquals("token1", nextToken.termText());
		assertEquals(0, nextToken.startOffset());
		assertEquals(6, nextToken.endOffset());
		assertEquals(1, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token2", nextToken.termText());
		assertEquals(6, nextToken.startOffset());
		assertEquals(11, nextToken.endOffset());
		assertEquals(1, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token21", nextToken.termText());
		assertEquals(6, nextToken.startOffset());
		assertEquals(11, nextToken.endOffset());
		assertEquals(0, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token22", nextToken.termText());
		assertEquals(6, nextToken.startOffset());
		assertEquals(11, nextToken.endOffset());
		assertEquals(0, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token23", nextToken.termText());
		assertEquals(6, nextToken.startOffset());
		assertEquals(11, nextToken.endOffset());
		assertEquals(0, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token3", nextToken.termText());
		assertEquals(11, nextToken.startOffset());
		assertEquals(17, nextToken.endOffset());
		assertEquals(1, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token4", nextToken.termText());
		assertEquals(17, nextToken.startOffset());
		assertEquals(23, nextToken.endOffset());
		assertEquals(1, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token41", nextToken.termText());
		assertEquals(17, nextToken.startOffset());
		assertEquals(23, nextToken.endOffset());
		assertEquals(0, nextToken.getPositionIncrement());

		nextToken = tokenFilter.next();		
		assertEquals("token42", nextToken.termText());
		assertEquals(17, nextToken.startOffset());
		assertEquals(23, nextToken.endOffset());
		assertEquals(0, nextToken.getPositionIncrement());

	}
}
