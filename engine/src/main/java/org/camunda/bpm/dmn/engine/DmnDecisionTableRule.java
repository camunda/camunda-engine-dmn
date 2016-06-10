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

package org.camunda.bpm.dmn.engine;

import java.util.List;

/**
 * Single row definition - i.e. rule - of a decision table.
 *
 * @see DmnDecisionTable
 */
public interface DmnDecisionTableRule {

  /**
   * The condition expressions for the respective input columns on a decision table row if exist.
   *
   * @return condition cell expressions or null if not set
   * @see DmnDecisionTable#getInputs()
   */
  List<? extends DmnExpression> getConditions();

  /**
   * The result expressions for the respective output columns on a decision table row if exist.
   *
   * @return result cell expressions or null if not set
   * @see DmnDecisionTable#getOutputs()
   */
  List<? extends DmnExpression> getConclusions();

}
