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

package org.camunda.dmn.engine.test;

import org.camunda.dmn.engine.DmnDecision;
import org.camunda.dmn.engine.DmnEngine;
import org.junit.Before;
import org.junit.Rule;

public abstract class DmnDecisionTest {

  public static final String NO_INPUT_DMN = "org/camunda/dmn/engine/NoInput.dmn";
  public static final String ONE_RULE_DMN = "org/camunda/dmn/engine/OneRule.dmn";
  public static final String EXAMPLE_DMN = "org/camunda/dmn/engine/Example.dmn";

  @Rule
  public DmnEngineRule dmnEngineRule = new DmnEngineRuleBuilder(this).build();

  public DmnEngine engine;
  public DmnDecision decision;


  @Before
  public void initEngineAndDecision() {
    engine = dmnEngineRule.engine;
    decision = dmnEngineRule.decision;
  }

}
