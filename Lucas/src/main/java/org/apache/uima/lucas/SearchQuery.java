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

package org.apache.uima.lucas;

import org.apache.lucene.search.Query;

/**
 * A <code>SearchQuery</code> defines a "search request" which is monitored
 * against a stream of text. The <code>SearchQuery</code> is a combinition of
 * a lucene {@link Query} and a long id to identify the query. The id is later
 * needed to map the search result to the search query.
 */
public interface SearchQuery {
	
	/**
	 * Id of the search query.
	 * 
	 * @return
	 */
	long id();
	
	/**
	 * The Lucene query.
	 * 
	 * @return
	 */
	Query query();
}
