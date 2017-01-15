/*
 * COPYRIGHT: FREQUENTIS AG. All rights reserved.
 *            Registered with Commercial Court Vienna,
 *            reg.no. FN 72.115b.
 */
package org.custom;

import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransform;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransformer;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelFunctionTransformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@FeelFunctionTransformer
public class StartsWithFunctionTransformer implements FeelToJuelTransformer {

  public static final Pattern STARTS_WITH_PATTERN = Pattern.compile("^starts with\\((.+)\\)$");

  public static final String JUEL_STARTS_WITH = "startsWith";

  public boolean canTransform(String feelExpression) {
    Matcher startsWithMatcher   = STARTS_WITH_PATTERN.matcher(feelExpression);

    return startsWithMatcher.matches();
  }

  @Override
  public String transform(FeelToJuelTransform transform, String feelExpression, String inputName) {
    Matcher startsWithMatcher   = STARTS_WITH_PATTERN.matcher(feelExpression);

    if (startsWithMatcher.matches()) {
      return JUEL_STARTS_WITH + "(" + inputName + ", " + startsWithMatcher.group(1) + ")";
    } else {
      return feelExpression;
    }
  }

  public static boolean startsWith(final String input, final String match) {
    if (input != null) {
      return input.startsWith(match);
    }
    return false;
  }

}
