/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.custom;

import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelFunctionTransformer;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelTransform;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EndsWithFunctionTransformer extends FeelToJuelFunctionTransformer {

  public static final Pattern ENDS_WITH_PATTERN = Pattern.compile("^ends with\\((.+)\\)$");

  public static final String JUEL_ENDS_WITH = "endsWith";

  public EndsWithFunctionTransformer() {
    try {
      method  = EndsWithFunctionTransformer.class.getMethod(JUEL_ENDS_WITH, String.class, String.class);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

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

  @Override
  public String getLocalName() {
    return JUEL_ENDS_WITH;
  }

}
