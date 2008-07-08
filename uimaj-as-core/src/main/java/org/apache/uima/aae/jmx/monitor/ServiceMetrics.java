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
package org.apache.uima.aae.jmx.monitor;

public class ServiceMetrics {

	private double timestamp;
	private String serviceName;
	private double idleTime;
	private long processCount;
	private double casPoolWaitTime;
	private double shadowCasPoolWaitTime;
	private double timeInCMGetNext;
	private int samplingInterval;
	private boolean isServiceRemote;
	private boolean isCasMultiplier;
	private boolean topLevelService;
	private long queueDepth;
	
	public boolean isTopLevelService() {
		return topLevelService;
	}
	public void setTopLevelService(boolean topLevelService) {
		this.topLevelService = topLevelService;
	}
	public double getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public double getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(double idleTime) {
		this.idleTime = idleTime;
	}
	public long getProcessCount() {
		return processCount;
	}
	public void setProcessCount(long processCount) {
		this.processCount = processCount;
	}
	public double getCasPoolWaitTime() {
		return casPoolWaitTime;
	}
	public void setCasPoolWaitTime(double casPoolWaitTime) {
		this.casPoolWaitTime = casPoolWaitTime;
	}
	public double getShadowCasPoolWaitTime() {
		return shadowCasPoolWaitTime;
	}
	public void setShadowCasPoolWaitTime(double shadowCasPoolWaitTime) {
		this.shadowCasPoolWaitTime = shadowCasPoolWaitTime;
	}
	public double getTimeInCMGetNext() {
		return timeInCMGetNext;
	}
	public void setTimeInCMGetNext(double timeInCMGetNext) {
		this.timeInCMGetNext = timeInCMGetNext;
	}
	public int getSamplingInterval() {
		return samplingInterval;
	}
	public void setSamplingInterval(int samplingInterval) {
		this.samplingInterval = samplingInterval;
	}
	public boolean isServiceRemote() {
		return isServiceRemote;
	}
	public void setServiceRemote(boolean isServiceRemote) {
		this.isServiceRemote = isServiceRemote;
	}
	public boolean isCasMultiplier() {
		return isCasMultiplier;
	}
	public void setCasMultiplier(boolean isCasMultiplier) {
		this.isCasMultiplier = isCasMultiplier;
	}
	public long getQueueDepth() {
		return queueDepth;
	}
	public void setQueueDepth(long queueDepth) {
		this.queueDepth = queueDepth;
	}
	
	
}