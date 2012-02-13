/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.textmarker.condition;

import java.util.List;

import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.textmarker.TextMarkerStream;
import org.apache.uima.textmarker.expression.number.NumberExpression;
import org.apache.uima.textmarker.expression.type.TypeExpression;
import org.apache.uima.textmarker.rule.EvaluatedCondition;
import org.apache.uima.textmarker.rule.RuleElement;
import org.apache.uima.textmarker.type.TextMarkerBasic;
import org.apache.uima.textmarker.visitor.InferenceCrowd;

public class PositionCondition extends TypeSentiveCondition {

  private final NumberExpression position;

  public NumberExpression getPosition() {
    return position;
  }

  public PositionCondition(TypeExpression type, NumberExpression position) {
    super(type);
    this.position = position;
  }

  @Override
  public EvaluatedCondition eval(AnnotationFS annotation, RuleElement element,
          TextMarkerStream stream, InferenceCrowd crowd) {
    Type t = type.getType(element.getParent());

    TextMarkerBasic beginAnchor = stream.getBeginAnchor(annotation.getBegin());
    TextMarkerBasic endAnchor = stream.getEndAnchor(annotation.getEnd());
    if (!beginAnchor.isPartOf(t) || !endAnchor.isPartOf(t)) {
      return new EvaluatedCondition(this, false);
    }

    FSIterator<AnnotationFS> iterator = stream.getCas().getAnnotationIndex(t).iterator(beginAnchor);
    if (!iterator.isValid()) {
      iterator.moveToNext();
    }
    if (!iterator.isValid()) {
      iterator.moveToLast();
    }
    AnnotationFS window = null;
    while (iterator.isValid()) {
      AnnotationFS annotationFS = iterator.get();
      if (annotationFS.getBegin() <= annotation.getBegin()
              && annotationFS.getEnd() >= annotation.getEnd()) {
        window = annotationFS;
        break;
      }
      iterator.moveToPrevious();
    }

    if (window == null) {
      return new EvaluatedCondition(this, false);
    }

    int counter = 0;
    List<TextMarkerBasic> inWindow = stream.getBasicsInWindow(window);
    for (TextMarkerBasic each : inWindow) {
      counter++;
      boolean beginsWith = each.beginsWith(annotation.getType());
      int integerValue = position.getIntegerValue(element.getParent());
      if (each.getBegin() == beginAnchor.getBegin() && beginsWith && counter == integerValue) {
        return new EvaluatedCondition(this, true);
      } else if (counter > integerValue) {
        return new EvaluatedCondition(this, false);
      }
    }

    return new EvaluatedCondition(this, false);
  }

}
