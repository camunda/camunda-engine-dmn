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

package org.camunda.bpm.dmn.feel.impl.juel.transform;

import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineLogger;
import org.camunda.bpm.dmn.feel.impl.juel.FeelLogger;

import java.util.ArrayList;
import java.util.List;

public class FeelToJuelTransformImpl implements FeelToJuelTransform {

  public static final FeelEngineLogger LOG = FeelLogger.ENGINE_LOGGER;

  public static final FeelToJuelTransformer NOT_TRANSFORMER = new NotTransformer();
  public static final FeelToJuelTransformer HYPHEN_TRANSFORMER = new HyphenTransformer();
  public static final FeelToJuelTransformer LIST_TRANSFORMER = new ListTransformer();
  public static final FeelToJuelTransformer INTERVAL_TRANSFORMER = new IntervalTransformer();
  public static final FeelToJuelTransformer COMPARISON_TRANSFORMER = new ComparisonTransformer();
  public static final FeelToJuelTransformer EQUAL_TRANSFORMER = new EqualTransformer();
  public static final FeelToJuelTransformer ENDPOINT_TRANSFORMER = new EndpointTransformer();
  public static final List<FeelToJuelTransformer> FUNCTION_TRANSFORMERS = new ArrayList<FeelToJuelTransformer>();

  public String transformSimpleUnaryTests(String simpleUnaryTests, String inputName) {
    simpleUnaryTests = simpleUnaryTests.trim();
    String juelExpression;
    if (HYPHEN_TRANSFORMER.canTransform(simpleUnaryTests)) {
      juelExpression = HYPHEN_TRANSFORMER.transform(this, simpleUnaryTests, inputName);
    }
    else if (NOT_TRANSFORMER.canTransform(simpleUnaryTests)) {
      juelExpression = NOT_TRANSFORMER.transform(this, simpleUnaryTests, inputName);
    }
    else {
      juelExpression = transformSimplePositiveUnaryTests(simpleUnaryTests, inputName);
    }

    return "${" + juelExpression + "}";
  }

  public String transformSimplePositiveUnaryTests(String simplePositiveUnaryTests, String inputName) {
    simplePositiveUnaryTests = simplePositiveUnaryTests.trim();
    for (FeelToJuelTransformer functionTransformer : FUNCTION_TRANSFORMERS) {
      if (functionTransformer.canTransform(simplePositiveUnaryTests)) {
        return functionTransformer.transform(this, simplePositiveUnaryTests, inputName);
      }
    }
    if (LIST_TRANSFORMER.canTransform(simplePositiveUnaryTests)) {
      return LIST_TRANSFORMER.transform(this, simplePositiveUnaryTests, inputName);
    }
    else {
      return transformSimplePositiveUnaryTest(simplePositiveUnaryTests, inputName);
    }
  }

  public String transformSimplePositiveUnaryTest(String simplePositiveUnaryTest, String inputName) {
    for (FeelToJuelTransformer functionTransformer : FUNCTION_TRANSFORMERS) {
      if (functionTransformer.canTransform(simplePositiveUnaryTest)) {
        return functionTransformer.transform(this, simplePositiveUnaryTest, inputName);
      }
    }
    if (INTERVAL_TRANSFORMER.canTransform(simplePositiveUnaryTest)) {
      return INTERVAL_TRANSFORMER.transform(this, simplePositiveUnaryTest, inputName);
    }
    else if (COMPARISON_TRANSFORMER.canTransform(simplePositiveUnaryTest)) {
      return COMPARISON_TRANSFORMER.transform(this, simplePositiveUnaryTest, inputName);
    }
    else {
      return EQUAL_TRANSFORMER.transform(this, simplePositiveUnaryTest, inputName);
    }
  }

  public String transformEndpoint(String endpoint, String inputName) {
    endpoint = endpoint.trim();
    return ENDPOINT_TRANSFORMER.transform(this, endpoint, inputName);
  }

  @Override
  public void addFunctionTransformer(FeelToJuelTransformer feelToJuelTransformer) {
    FUNCTION_TRANSFORMERS.add(feelToJuelTransformer);
  }
}
