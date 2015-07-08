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

package org.camunda.dmn.engine.test.asserts;

import org.assertj.core.api.Assertions;
import org.camunda.dmn.engine.DmnDecisionOutput;
import org.camunda.dmn.engine.DmnDecisionResult;
import org.camunda.dmn.engine.DmnEngine;

public class DmnAssertions extends Assertions {

  static ThreadLocal<DmnEngineAssertion> dmnDecisionEngineAssertionThreadLocal = new ThreadLocal<DmnEngineAssertion>();

  public static DmnEngineAssertion assertThat(DmnEngine engine) {
    final DmnEngineAssertion dmnEngineAssertion = new DmnEngineAssertion(engine);
    DmnAssertions.dmnDecisionEngineAssertionThreadLocal.set(dmnEngineAssertion);
    return dmnEngineAssertion;
  }

  public static DmnDecisionResultAssertion assertThat(DmnDecisionResult result) {
    return new DmnDecisionResultAssertion(result);
  }

  public static DmnDecisionOutputAssertion assertThat(DmnDecisionOutput output) {
    DmnDecisionOutputAssertion dmnDecisionOutputAssertion = new DmnDecisionOutputAssertion(output);
    return dmnDecisionOutputAssertion;
  }

  public static DmnEngineAssertion decision() {
    final DmnEngineAssertion dmnEngineAssertion = DmnAssertions.dmnDecisionEngineAssertionThreadLocal.get();
    return dmnEngineAssertion;
  }

  public static void reset() {
    DmnAssertions.dmnDecisionEngineAssertionThreadLocal.remove();
  }

}
