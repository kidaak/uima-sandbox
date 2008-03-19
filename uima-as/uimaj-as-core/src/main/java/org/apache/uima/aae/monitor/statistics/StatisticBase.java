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

package org.apache.uima.aae.monitor.statistics;

import java.util.HashMap;
import java.util.Map;

public class StatisticBase implements Statistic
{
	private Map statMap = new HashMap();

	public void add(Object key, Object value)
	{
		statMap.put( key, value);
	}
	
	public void reset()
	{
		
	}

	public Object get(Object key)
	{
		if ( statMap.containsKey(key))
		{
			return statMap.get(key);
		}
		return null;
	}

	public synchronized void increment(Object key)
	{
		if ( statMap.containsKey(key) )
		{
			Long count = (Long)statMap.get(key);
			count++;
			statMap.remove(key);
			add(key, count);
		}
		
	}

	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}
	

}