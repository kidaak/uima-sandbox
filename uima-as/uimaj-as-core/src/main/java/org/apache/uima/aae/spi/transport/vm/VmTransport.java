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

package org.apache.uima.aae.spi.transport.vm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.uima.aae.UimaAsContext;
import org.apache.uima.aae.controller.AnalysisEngineController;
import org.apache.uima.aae.error.UimaSpiException;
import org.apache.uima.aae.message.AsynchAEMessage;
import org.apache.uima.aae.message.UIMAMessage;
import org.apache.uima.aae.spi.transport.SpiListener;
import org.apache.uima.aae.spi.transport.UimaMessageDispatcher;
import org.apache.uima.aae.spi.transport.UimaMessageListener;
import org.apache.uima.aae.spi.transport.UimaTransport;

/**
 * This class provides implementation for internal messaging between collocated Uima AS services.
 * It uses {@link UimaMessageDispatcher} to send messages to {@link UimaMessageListener}.
 * 
 *
 */
public class VmTransport implements UimaTransport {

  private List<SpiListener> spiListeners = new ArrayList<SpiListener>();

  private ConcurrentHashMap<String, UimaVmMessageDispatcher> dispatchers = new ConcurrentHashMap<String, UimaVmMessageDispatcher>();

  private ThreadPoolExecutor executor = null;

  private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

  private VmTransport vmConnector;

  private UimaVmMessageDispatcher dispatcher;

  private UimaVmMessageListener listener;

  private AnalysisEngineController controller;

  private UimaAsContext context;

  public VmTransport(UimaAsContext aContext) {
    context = aContext;
  }

  public void addSpiListener(SpiListener listener) {
    spiListeners.add(listener);
  }

  public void initialize(UimaTransport connector, AnalysisEngineController aController)
          throws UimaSpiException {
    vmConnector = (VmTransport) connector;
    executor = vmConnector.getExecutorInstance();
    controller = aController;
  }

  public void initialize(AnalysisEngineController aController) throws UimaSpiException {
    controller = aController;
  }

  public UimaVmMessage produceMessage() {
    return new UimaVmMessage();
  }

  public UimaVmMessage produceMessage(int aCommand, int aMessageType, String aMessageFrom) {
    UimaVmMessage message = produceMessage();
    message.addIntProperty(AsynchAEMessage.Command, aCommand);
    message.addIntProperty(AsynchAEMessage.MessageType, aMessageType);
    switch (aCommand) {
      case AsynchAEMessage.GetMeta:
        message.addIntProperty(AsynchAEMessage.Payload, AsynchAEMessage.Metadata);
        break;
      case AsynchAEMessage.CollectionProcessComplete:
        message.addIntProperty(AsynchAEMessage.Payload, AsynchAEMessage.None);
        break;
      case AsynchAEMessage.Process:
        message.addIntProperty(AsynchAEMessage.Payload, AsynchAEMessage.CASRefID);
        break;
    }

    message.addStringProperty(AsynchAEMessage.MessageFrom, aMessageFrom);
    message.addStringProperty(UIMAMessage.ServerURI, "vm://localhost");
    return message;
  }

  public void startIt() throws UimaSpiException {
    dispatcher = new UimaVmMessageDispatcher(executor, null, (String) context.get("EndpointName"));
  }

  public void stopIt() throws UimaSpiException {
    executor.shutdown();
    try {
      executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
    }
  }

  protected ThreadPoolExecutor getExecutorInstance() {
    if (executor == null) {
      int concurrentConsumerCount = context.getConcurrentConsumerCount();
      // Create a ThreadPoolExecutor with as many threads as needed. The pool has 
      // a fixed number of threads that never expire and are never passivated.
      executor = new ThreadPoolExecutor(concurrentConsumerCount, concurrentConsumerCount, Long.MAX_VALUE,
              TimeUnit.NANOSECONDS, workQueue);
      executor.prestartAllCoreThreads();
    }
    return executor;
  }

  public UimaMessageDispatcher getMessageDispatcher() throws UimaSpiException {
    return dispatcher;
  }

  public UimaMessageListener getUimaMessageListener() {
    return listener;
  }

  public UimaMessageListener produceUimaMessageListener(AnalysisEngineController aController)
          throws UimaSpiException {
    listener = new UimaVmMessageListener(aController);
    return listener;
  }

  public UimaMessageDispatcher getUimaMessageDispatcher() throws UimaSpiException {
    return getUimaMessageDispatcher(controller.getName());
  }

  public UimaMessageDispatcher getUimaMessageDispatcher(String aKey) throws UimaSpiException {
    return dispatchers.get(aKey);
  }

  public UimaVmMessageDispatcher produceUimaMessageDispatcher() throws UimaSpiException {
    return produceUimaMessageDispatcher(controller, null);
  }

  public UimaVmMessageDispatcher produceUimaMessageDispatcher(UimaTransport aTransport)
          throws UimaSpiException {
    return produceUimaMessageDispatcher(controller, aTransport);
  }

  public UimaVmMessageDispatcher produceUimaMessageDispatcher(AnalysisEngineController aController,
          UimaTransport aTransport) throws UimaSpiException {
    UimaVmMessageDispatcher dispatcher = null;
    controller = aController;
    if (aTransport != null) {
      dispatcher = new UimaVmMessageDispatcher(((VmTransport) aTransport).getExecutorInstance(),
              ((VmTransport) aTransport).getUimaMessageListener(), (String) context
                      .get("EndpointName"));
    } else {
      dispatcher = new UimaVmMessageDispatcher(executor, ((VmTransport) aTransport)
              .getUimaMessageListener(), (String) context.get("EndpointName"));
    }

    dispatchers.put(aController.getName(), dispatcher);
    return dispatcher;
  }

}
