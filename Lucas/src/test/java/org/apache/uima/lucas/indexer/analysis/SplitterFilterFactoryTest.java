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

import java.util.Properties;

import org.apache.lucene.analysis.TokenStream;
import org.junit.Before;
import org.junit.Test;
import static org.easymock.classextension.EasyMock.*;
import static org.junit.Assert.*;

public class SplitterFilterFactoryTest {

	private SplitterFilterFactory splitterFilterFactory;
	private TokenStream tokenStream;
	
	@Before
	public void setUp(){
		tokenStream = createMock(TokenStream.class);
		splitterFilterFactory = new SplitterFilterFactory();
	}
	
	@Test
	public void testCreateTokenFilter() throws Exception{
		Properties properties = new Properties();
		properties.setProperty(SplitterFilterFactory.SPLIT_STRING_PARAMETER, ",");
		SplitterFilter splitterFilter = (SplitterFilter) splitterFilterFactory.createTokenFilter(tokenStream, properties );
		assertEquals("," , splitterFilter.getSplitString());
	}
	
}
