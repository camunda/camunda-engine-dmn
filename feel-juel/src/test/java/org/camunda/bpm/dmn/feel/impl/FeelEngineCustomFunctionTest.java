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

package org.camunda.bpm.dmn.feel.impl;

import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineFactoryImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.custom.EndsWithFunctionTransformer;
import org.custom.StartsWithFunctionTransformer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class FeelEngineCustomFunctionTest {

  public static final String INPUT_VARIABLE = "input";

  public static FeelEngine feelEngine;

  public VariableMap variables;

  @BeforeClass
  public static void initFeelEngine() throws Exception {
    feelEngine = new FeelEngineFactoryImpl().createInstance();

    // ADD CUSTOM FUNCTIONs to engine
    Method startsWithMethod = StartsWithFunctionTransformer.class.getMethod("startsWith", String.class, String.class);
    Method endsWithMethod = EndsWithFunctionTransformer.class.getMethod("endsWith", String.class, String.class);

    feelEngine.addCustomFunction("", "startsWith", startsWithMethod);
    feelEngine.addCustomFunction("endsWith", endsWithMethod);
  }

  @Before
  public void initVariables() {
    variables = Variables.createVariables();
  }

  @Test
  public void testStringStartsWith() {
    assertEvaluatesToTrue("foobar", "starts with(\"foo\")");
    assertEvaluatesToFalse("foobar", "starts with(\"foa\")");
    assertEvaluatesToFalse("foobar", "starts with(\"afoo\")");
    assertEvaluatesToTrue("foobar", "starts with(\"foobar\")");
    assertEvaluatesToFalse("", "starts with(\"foobar\")");
    assertEvaluatesToFalse(null, "starts with(\"foobar\")");
  }

  @Test
  public void testStringEndsWith() {
    assertEvaluatesToTrue("foobar", "ends with(\"bar\")");
    assertEvaluatesToFalse("foobar", "ends with(\"boa\")");
    assertEvaluatesToFalse("foobar", "ends with(\"afoo\")");
    assertEvaluatesToTrue("foobar", "ends with(\"foobar\")");
    assertEvaluatesToFalse("", "ends with(\"foobar\")");
    assertEvaluatesToFalse(null, "ends with(\"foobar\")");
  }

  public void assertEvaluatesToTrue(Object input, String feelExpression) {
    boolean result = evaluateFeel(input, feelExpression);
    assertThat(result).isTrue();
  }

  public void assertEvaluatesToFalse(Object input, String feelExpression) {
    boolean result = evaluateFeel(input, feelExpression);
    assertThat(result).isFalse();
  }

  public boolean evaluateFeel(Object input, String feelExpression) {
    variables.putValue(INPUT_VARIABLE, input);
    return feelEngine.evaluateSimpleUnaryTests(feelExpression, INPUT_VARIABLE, variables.asVariableContext());
  }

}
