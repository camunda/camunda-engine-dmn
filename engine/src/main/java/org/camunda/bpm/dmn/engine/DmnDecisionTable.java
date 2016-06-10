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
 * A decision table of the DMN Engine.
 *
 * <p>
 * Specific type of decision that returns {@code true} for {@link #isDecisionTable()}.
 * </p>
 */
public interface DmnDecisionTable extends DmnDecision {

  /**
   * The declared input columns.
   *
   * @return the input, can be empty if not set
   */
  List<? extends DmnDecisionTableInput> getInputs();

  /**
   * The declared output columns.
   *
   * @return the outputs, can be empty if not set
   */
  List<? extends DmnDecisionTableOutput> getOutputs();

  /**
   * The declared table rows, i.e. rules.
   *
   * @return the rules, can be empty if not set
   */
  List<? extends DmnDecisionTableRule> getRules();
}
