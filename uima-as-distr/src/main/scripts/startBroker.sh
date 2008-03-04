#!/bin/sh

#   Licensed to the Apache Software Foundation (ASF) under one
#   or more contributor license agreements.  See the NOTICE file
#   distributed with this work for additional information
#   regarding copyright ownership.  The ASF licenses this file
#   to you under the Apache License, Version 2.0 (the
#   "License"); you may not use this file except in compliance
#   with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing,
#   software distributed under the License is distributed on an
#   #  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
#   KIND, either express or implied.  See the License for the
#   specific language governing permissions and limitations
#   under the License.

# ActiveMQ needs a writeable directory for the log files and derbydb.
if [ -z "$ACTIVEMQ_BASE" ] ; then
  export ACTIVEMQ_BASE=amq
fi

# If the directory doesn't exist, make it
if [ ! -d "$ACTIVEMQ_BASE" ] ; then
  mkdir $ACTIVEMQ_BASE
  mkdir $ACTIVEMQ_BASE/conf
fi

# If the config file isn't there, copy it
if [ ! -f $ACTIVEMQ_BASE/conf/activemq-nojournal.xml ] ; then
  cp $UIMA_HOME/config/activemq-nojournal.xml $ACTIVEMQ_BASE/conf/
  cp $UIMA_HOME/apache-activemq-4.1.1/conf/log4j.properties $ACTIVEMQ_BASE/conf/
fi

$UIMA_HOME/apache-activemq-4.1.1/bin/activemq xbean:file:$ACTIVEMQ_BASE/conf/activemq-nojournal.xml