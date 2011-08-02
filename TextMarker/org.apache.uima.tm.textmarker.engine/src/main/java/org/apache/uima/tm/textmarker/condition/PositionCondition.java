package org.apache.uima.tm.textmarker.condition;

import java.util.List;

import org.apache.uima.cas.Type;
import org.apache.uima.tm.textmarker.kernel.TextMarkerStream;
import org.apache.uima.tm.textmarker.kernel.expression.number.NumberExpression;
import org.apache.uima.tm.textmarker.kernel.expression.type.TypeExpression;
import org.apache.uima.tm.textmarker.kernel.rule.EvaluatedCondition;
import org.apache.uima.tm.textmarker.kernel.rule.TextMarkerRuleElement;
import org.apache.uima.tm.textmarker.kernel.type.TextMarkerBasic;
import org.apache.uima.tm.textmarker.kernel.visitor.InferenceCrowd;


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
  public EvaluatedCondition eval(TextMarkerBasic basic, Type matchedType,
          TextMarkerRuleElement element, TextMarkerStream stream, InferenceCrowd crowd) {
    Type t = type.getType(element.getParent());
    if (!basic.isPartOf(t.getName())) {
      return new EvaluatedCondition(this, false);
    }
    if (matchedType == null)
      return new EvaluatedCondition(this, false);
    int counter = 0;
    List<TextMarkerBasic> annotationsInWindow = null;
    if (t.getName().equals("uima.tcas.DocumentAnnotation")
            || stream.getDocumentAnnotation().getType().equals(t)) {
      annotationsInWindow = stream.getBasicsInWindow(stream.getDocumentAnnotation());
    } else {
      annotationsInWindow = stream.getAnnotationsOverlappingWindow(basic, t);
    }
    for (TextMarkerBasic eachBasic : annotationsInWindow) {
      if (eachBasic.isAnchorOf(matchedType.getName())
              || stream.getCas().getTypeSystem().subsumes(matchedType, eachBasic.getType())) {
        counter++;
        if (eachBasic.equals(basic)) {
          if (counter == position.getIntegerValue(element.getParent())) {
            return new EvaluatedCondition(this, true);
          }
        }
      }
    }
    return new EvaluatedCondition(this, false);
  }

}