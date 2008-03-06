@echo off

REM   Licensed to the Apache Software Foundation (ASF) under one
REM   or more contributor license agreements.  See the NOTICE file
REM   distributed with this work for additional information
REM   regarding copyright ownership.  The ASF licenses this file
REM   to you under the Apache License, Version 2.0 (the
REM   "License"); you may not use this file except in compliance
REM   with the License.  You may obtain a copy of the License at
REM
REM    http://www.apache.org/licenses/LICENSE-2.0
REM
REM   Unless required by applicable law or agreed to in writing,
REM   software distributed under the License is distributed on an
REM   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
REM   KIND, either express or implied.  See the License for the
REM   specific language governing permissions and limitations
REM   under the License.

@rem All this nonsense is necessary to remove quotes from the CLASSPATH and also handle the case where there is no CLASSPATH
@set _NOQUOTES=%CLASSPATH:"=%
@set _REALLYNOQUOTES=%_NOQUOTES:"=%
@if "%_REALLYNOQUOTES%"=="=" set _REALLYNOQUOTES=
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\examples\resources;%UIMA_HOME%\lib\uima-core.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\uima-document-annotation.jar;%UIMA_HOME%\lib\uima-cpe.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\uima-tools.jar;%UIMA_HOME%\lib\uima-examples.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\uima-adapter-soap.jar;%UIMA_HOME%\lib\uima-adapter-vinci.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\activation.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\axis.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\commons-discovery.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\commons-discovery-0.2.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\commons-logging.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\commons-logging-1.0.4.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\jaxrpc.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\mail.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%CATALINA_HOME%\webapps\axis\WEB-INF\lib\saaj.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\jVinci.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\apache-activemq.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\activemq-optional.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\commons-httpclient.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\xstream.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\xmlpull.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\jetty.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\jetty-util.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\optional\servlet-api-2.5.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\commons-logging.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\commons-pool.jar;%UIMA_HOME%\lib\spring.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\uimaj-as-core.jar;%UIMA_HOME%\lib\uima-as-activemq.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\lib\uimaj-as-jms.jar
@set UIMA_CLASSPATH=%UIMA_CLASSPATH%;%UIMA_HOME%\config;%_REALLYNOQUOTES%
@rem set path to support running C++ annotators
set PATH=%UIMA_HOME%\uimacpp\bin;%UIMA_HOME%\uimacpp\examples\tutorial\src;%PATH%
@rem Also set VNS_HOST and VNS_PORT to default values if they are not specified
@if "%VNS_HOST%"=="" set VNS_HOST=localhost
@if "%VNS_PORT%"=="" set VNS_PORT=9000
@rem Also set default value for UIMA_LOGGER_CONFIG_FILE
@if "%UIMA_LOGGER_CONFIG_FILE%"=="" set UIMA_LOGGER_CONFIG_FILE=%UIMA_HOME%\config\Logger.properties
@rem Set default JVM opts
@if "%UIMA_JVM_OPTS%"=="" set UIMA_JVM_OPTS=-Xms128M -Xmx800M