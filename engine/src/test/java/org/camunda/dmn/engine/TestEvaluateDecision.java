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

package org.camunda.dmn.engine;

import static org.camunda.dmn.engine.test.asserts.DmnAssertions.decision;

import org.camunda.dmn.engine.test.DecisionResource;
import org.camunda.dmn.engine.test.DmnDecisionTest;
import org.camunda.dmn.engine.test.DmnEngineRule;
import org.camunda.dmn.engine.test.DmnEngineRuleBuilder;
import org.junit.Rule;
import org.junit.Test;

public class TestEvaluateDecision {

  @Rule
  public DmnEngineRule rule = new DmnEngineRuleBuilder(this).withAssertions().build();
  
  @Test
  @DecisionResource(resource = DmnDecisionTest.NO_INPUT_DMN)
  public void shouldEvaluateRuleWithoutInput() {
    decision().hasResult("ok");
  }

  @Test
  @DecisionResource(resource = DmnDecisionTest.ONE_RULE_DMN)
  public void shouldEvaluateSingleRule() {
    decision()
      .evaluates("input", "ok")
      .hasResult("ok");

    decision()
      .evaluates("input", "ok")
      .hasResult(null, "ok");

    decision()
      .evaluates("input", "notok")
      .hasEmptyResult();
  }

  @Test
  @DecisionResource(resource = DmnDecisionTest.EXAMPLE_DMN)
  public void shouldEvaluateExample() {
    decision()
      .evaluates("status", "bronze", "sum", 200)
      .hasResult()
        .hasSingleOutput()
          .hasEntry("result", "notok")
          .hasEntry("reason", "work on your status first, as bronze you're not going to get anything");

    decision()
      .evaluates("status", "silver", "sum", 200)
      .hasResult()
        .hasSingleOutput()
          .hasEntry("result", "ok")
          .hasEntry("reason", "you little fish will get what you want");

    decision()
      .evaluates("status", "silver", "sum", 1200)
      .hasResult()
        .hasSingleOutput()
          .hasEntry("result", "notok")
          .hasEntry("reason", "you took too much man, you took too much!");

    decision()
      .evaluates("status", "gold", "sum", 200)
      .hasResult()
        .hasSingleOutput()
          .hasEntry("result", "ok")
          .hasEntry("reason", "you get anything you want");
  }

}
