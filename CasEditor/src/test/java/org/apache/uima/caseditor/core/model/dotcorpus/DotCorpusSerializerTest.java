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

package org.apache.uima.caseditor.core.model.dotcorpus;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.uima.caseditor.core.model.dotcorpus.AnnotationStyle;
import org.apache.uima.caseditor.core.model.dotcorpus.DotCorpus;
import org.apache.uima.caseditor.core.model.dotcorpus.DotCorpusSerializer;
import org.eclipse.core.runtime.CoreException;

/**
 * TODO: add javadoc here
 * 
 * @author <a href="mailto:kottmann@gmail.com">Joern Kottmann</a>
 * @version $Revision: 1.4.2.2 $, $Date: 2007/01/04 14:56:25 $
 */
public class DotCorpusSerializerTest {
  /**
   * Tests if serialization and recreations creates and object that is equal to the original.
   * 
   * @throws CoreException
   */
  @Test
  public void testSerializeAndCreate() throws CoreException {
    DotCorpus original = new DotCorpus();
    original.setTypeSystemFilename("typesystem");
    original.setUimaConfigFolderName("uima config folder");
    original.setStyle(new AnnotationStyle("test", Style.BRACKET, Color.GRAY));
    original.addCorpusFolder("corpus");

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    DotCorpusSerializer.serialize(original, out);

    InputStream in = new ByteArrayInputStream(out.toByteArray());

    DotCorpus recreated = DotCorpusSerializer.parseDotCorpus(in);

    assertEquals(original, recreated);
  }
}
