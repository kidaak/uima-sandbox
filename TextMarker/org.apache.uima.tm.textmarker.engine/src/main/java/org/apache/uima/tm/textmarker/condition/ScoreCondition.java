package org.apache.uima.tm.textmarker.condition;

import java.util.List;

import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.tm.textmarker.kernel.TextMarkerStream;
import org.apache.uima.tm.textmarker.kernel.expression.number.NumberExpression;
import org.apache.uima.tm.textmarker.kernel.expression.number.SimpleNumberExpression;
import org.apache.uima.tm.textmarker.kernel.rule.EvaluatedCondition;
import org.apache.uima.tm.textmarker.kernel.rule.TextMarkerRuleElement;
import org.apache.uima.tm.textmarker.kernel.type.TextMarkerAnnotation;
import org.apache.uima.tm.textmarker.kernel.type.TextMarkerBasic;
import org.apache.uima.tm.textmarker.kernel.visitor.InferenceCrowd;


public class ScoreCondition extends TerminalTextMarkerCondition {
  private final NumberExpression min;

  private final NumberExpression max;

  private final String var;

  public ScoreCondition(NumberExpression min, NumberExpression max, String var) {
    this.min = min == null ? new SimpleNumberExpression(Integer.MIN_VALUE) : min;
    this.max = max == null ? new SimpleNumberExpression(Integer.MAX_VALUE) : max;
    this.var = var;
  }

  @Override
  public EvaluatedCondition eval(TextMarkerBasic basic, Type matchedType,
          TextMarkerRuleElement element, TextMarkerStream stream, InferenceCrowd crowd) {
    AnnotationFS annotation = stream.expandAnchor(basic, matchedType);
    Type heuristicType = stream.getJCas().getCasType(TextMarkerAnnotation.type);
    List<AnnotationFS> annotationsInWindow = stream.getAnnotationsInWindow(annotation,
            heuristicType);
    double score = 0;
    if (!annotationsInWindow.isEmpty()) {
      TextMarkerAnnotation heuristicAnnotation = (TextMarkerAnnotation) stream.getCas()
              .createAnnotation(heuristicType, annotation.getBegin(), annotation.getEnd());
      heuristicAnnotation.setAnnotation((Annotation) annotation);
      TextMarkerAnnotation tma = stream.getCorrectTMA(annotationsInWindow, heuristicAnnotation);
      score = tma.getScore();
    }
    if (var != null) {
      element.getParent().getEnvironment().setVariableValue(var, score);
    }
    boolean value = score >= min.getDoubleValue(element.getParent())
            && score <= max.getDoubleValue(element.getParent());
    return new EvaluatedCondition(this, value);
  }

  public NumberExpression getMin() {
    return min;
  }

  public NumberExpression getMax() {
    return max;
  }

  public String getVar() {
    return var;
  }

}