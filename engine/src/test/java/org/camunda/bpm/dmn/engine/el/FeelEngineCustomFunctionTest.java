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

package org.camunda.bpm.dmn.engine.el;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.camunda.bpm.dmn.feel.impl.FeelEngine;
import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineFactoryImpl;
import org.camunda.bpm.dmn.feel.impl.juel.transform.FeelToJuelFunctionTransformer;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.custom.EndsWithFunctionTransformer;
import org.custom.StartsWithFunctionTransformer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FeelEngineCustomFunctionTest {

  public static final String INPUT_VARIABLE = "input";

  public static DmnEngine dmnEngine;
  public static FeelEngine feelEngine;

  public VariableMap variables;

  @Before
  public void initEngine() {
    variables = Variables.createVariables();
    DefaultDmnEngineConfiguration configuration = new DefaultDmnEngineConfiguration();
    configuration.setFeelEngineFactory(new TestFeelEngineFactory());
    dmnEngine = configuration.buildEngine();
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

  /**
   * The custom Test Feel Engine Factory
   * which needs to be created in order to hook custom functions into the DMN FEEL Engine.
   */
  public class TestFeelEngineFactory extends FeelEngineFactoryImpl {

    public FeelEngine createInstance() {
      //Only needed for making this unit test easier...
      FeelEngineCustomFunctionTest.feelEngine = super.createInstance();
      return FeelEngineCustomFunctionTest.feelEngine;
    }

    @Override
    protected List<FeelToJuelFunctionTransformer> getFunctionTransformers() {
      //Adding the custom extensions here -> The details how to add where and when is hidden in the Factory itself.
      List<FeelToJuelFunctionTransformer> functionTransformers = new ArrayList<FeelToJuelFunctionTransformer>(2);
      functionTransformers.add(new StartsWithFunctionTransformer());
      functionTransformers.add(new EndsWithFunctionTransformer());
      return functionTransformers;
    }
  }
}
