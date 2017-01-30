/*
 * COPYRIGHT: FREQUENTIS AG. All rights reserved.
 *            Registered with Commercial Court Vienna,
 *            reg.no. FN 72.115b.
 */
package org.custom;

import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransform;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndsWithFunctionTransformer implements FeelToJuelTransformer {

  public static final Pattern ENDS_WITH_PATTERN = Pattern.compile("^ends with\\((.+)\\)$");

  public static final String JUEL_ENDS_WITH = "endsWith";

  public boolean canTransform(String feelExpression) {
    Matcher startsWithMatcher   = ENDS_WITH_PATTERN.matcher(feelExpression);

    return startsWithMatcher.matches();
  }

  @Override
  public String transform(FeelToJuelTransform transform, String feelExpression, String inputName) {
    Matcher startsWithMatcher   = ENDS_WITH_PATTERN.matcher(feelExpression);

    if (startsWithMatcher.matches()) {
      return JUEL_ENDS_WITH + "(" + inputName + ", " + startsWithMatcher.group(1) + ")";
    } else {
      return feelExpression;
    }
  }

  public static boolean endsWith(final String input, final String match) {
    if (input != null) {
      return input.endsWith(match);
    }
    return false;
  }

}
