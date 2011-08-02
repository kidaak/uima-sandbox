package org.apache.uima.tm.textmarker.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.tm.textmarker.kernel.TextMarkerStream;
import org.apache.uima.tm.textmarker.kernel.expression.bool.BooleanExpression;
import org.apache.uima.tm.textmarker.kernel.expression.number.NumberExpression;
import org.apache.uima.tm.textmarker.kernel.expression.resource.WordListExpression;
import org.apache.uima.tm.textmarker.kernel.expression.string.StringExpression;
import org.apache.uima.tm.textmarker.kernel.expression.type.TypeExpression;
import org.apache.uima.tm.textmarker.kernel.rule.RuleMatch;
import org.apache.uima.tm.textmarker.kernel.rule.TextMarkerRuleElement;
import org.apache.uima.tm.textmarker.kernel.type.TextMarkerBasic;
import org.apache.uima.tm.textmarker.kernel.visitor.InferenceCrowd;
import org.apache.uima.tm.textmarker.resource.TextMarkerWordList;


public class TrieAction extends AbstractTextMarkerAction {

  private final WordListExpression list;

  private final Map<StringExpression, TypeExpression> map;

  private final BooleanExpression ignoreCase;

  private final NumberExpression ignoreLength;

  private final BooleanExpression edit;

  private final NumberExpression distance;

  private final StringExpression ignoreChar;

  public TrieAction(WordListExpression list, Map<StringExpression, TypeExpression> map,
          BooleanExpression ignoreCase, NumberExpression ignoreLength, BooleanExpression edit,
          NumberExpression distance, StringExpression ignoreChar) {
    this.list = list;
    this.map = map;
    this.ignoreCase = ignoreCase;
    this.ignoreLength = ignoreLength;
    this.edit = edit;
    this.distance = distance;
    this.ignoreChar = ignoreChar;
  }

  @Override
  public void execute(RuleMatch match, TextMarkerRuleElement element, TextMarkerStream stream,
          InferenceCrowd crowd) {

    Map<String, Type> typeMap = new HashMap<String, Type>();
    for (StringExpression eachKey : map.keySet()) {
      String stringValue = eachKey.getStringValue(element.getParent());
      TypeExpression typeExpression = map.get(eachKey);
      if (typeExpression != null) {
        Type typeValue = typeExpression.getType(element.getParent());
        typeMap.put(stringValue, typeValue);
      }
    }
    boolean ignoreCaseValue = ignoreCase.getBooleanValue(element.getParent());
    int ignoreLengthValue = ignoreLength.getIntegerValue(element.getParent());
    boolean editValue = edit.getBooleanValue(element.getParent());
    double distanceValue = distance.getDoubleValue(element.getParent());
    String ignoreCharValue = ignoreChar.getStringValue(element.getParent());

    TextMarkerWordList wl = list.getList(element.getParent());
    Collection<AnnotationFS> found = wl.find(stream, typeMap, ignoreCaseValue, ignoreLengthValue,
            editValue, distanceValue, ignoreCharValue);

    if (found != null) {
      for (AnnotationFS annotation : found) {
        TextMarkerBasic anchor = stream.getFirstBasicInWindow(annotation);
        stream.addAnnotation(anchor, annotation);
        stream.getCas().addFsToIndexes(annotation);
      }
    }

  }

  public WordListExpression getList() {
    return list;
  }

  public Map<StringExpression, TypeExpression> getMap() {
    return map;
  }

  public BooleanExpression getIgnoreCase() {
    return ignoreCase;
  }

  public NumberExpression getIgnoreLength() {
    return ignoreLength;
  }

  public BooleanExpression getEdit() {
    return edit;
  }

  public NumberExpression getDistance() {
    return distance;
  }

  public StringExpression getIgnoreChar() {
    return ignoreChar;
  }

}