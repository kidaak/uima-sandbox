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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.uima.lucas.indexer.analysis.PositionFilter;
import org.apache.uima.lucas.indexer.test.util.CollectionTokenStream;
import org.junit.Test;

public class PositionFilterTest {

  @Test
  public void testNext() throws Exception {
    Collection<Token> tokens = new ArrayList<Token>();
    tokens.add(new Token("token1", 0, 6));
    tokens.add(new Token("token1", 7, 13));
    tokens.add(new Token("token2", 14, 20));
    tokens.add(new Token("token2", 21, 27));
    tokens.add(new Token("token3", 28, 33));
    tokens.add(new Token("token4", 34, 40));

    TokenStream tokenStream = new CollectionTokenStream(tokens);
    TokenFilter filter = new PositionFilter(tokenStream, PositionFilter.FIRST_POSITION);

    Token nextToken = filter.next();
    assertNotNull(nextToken);
    assertEquals("token1", new String(nextToken.termBuffer(), 0, nextToken.termLength()));

    nextToken = filter.next();
    assertNull(nextToken);

    filter = new PositionFilter(tokenStream, PositionFilter.LAST_POSITION);
    nextToken = filter.next();
    assertNotNull(nextToken);
    assertEquals("token4", new String(nextToken.termBuffer(), 0, nextToken.termLength()));

    nextToken = filter.next();
    assertNull(nextToken);
  }
}