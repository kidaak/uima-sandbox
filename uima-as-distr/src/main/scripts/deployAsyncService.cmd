@REM   Licensed to the Apache Software Foundation (ASF) under one
@REM   or more contributor license agreements.  See the NOTICE file
@REM   distributed with this work for additional information
@REM   regarding copyright ownership.  The ASF licenses this file
@REM   to you under the Apache License, Version 2.0 (the
@REM   "License"); you may not use this file except in compliance
@REM   with the License.  You may obtain a copy of the License at
@REM
@REM    http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM   Unless required by applicable law or agreed to in writing,
@REM   software distributed under the License is distributed on an
@REM   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM   KIND, either express or implied.  See the License for the
@REM   specific language governing permissions and limitations
@REM   under the License.

@if .%1 == . goto usage
@if NOT exist %1 goto usage

if not defined UIMA_HOME goto USAGE_UIMA
goto RUN

:USAGE_UIMA
echo UIMA_HOME environment variable is not set 
goto end

:RUN
@setlocal
@call "%UIMA_HOME%\bin\setUimaClassPath"

@rem set spring_file=%~n1_spring.xml
@rem ddmake does not work because it will not rebuild if the component descriptor changed
@rem but the deployment descriptor did not.  For now we always build the Spring XML.
@rem call ddmake %1
@rem call dd2spring %1 %spring_file%

@if "%JAVA_HOME%"=="" (set UIMA_JAVA_CALL=java) else (set UIMA_JAVA_CALL=%JAVA_HOME%\bin\java)
@"%UIMA_JAVA_CALL%" -cp "%UIMA_CLASSPATH%" "-Duima.datapath=%UIMA_DATAPATH%" "-Djava.util.logging.config.file=%UIMA_LOGGER_CONFIG_FILE%" %UIMA_JVM_OPTS%  org.apache.uima.adapter.jms.service.UIMA_Service -saxonURL "file:%UIMA_HOME%\lib\saxon8.jar" -xslt "%UIMA_HOME%\bin\dd2spring.xsl" -dd %*
@goto end
:usage
 @echo Deployment descriptor %1 not found
 @echo Usage: deployAsyncService file-path-of-deployment-descriptor [another-dd ...]
:end
