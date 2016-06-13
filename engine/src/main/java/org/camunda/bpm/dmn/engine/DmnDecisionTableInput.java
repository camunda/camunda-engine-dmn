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

/**
 * Single input column definition of a decision table.
 *
 * @see DmnDecisionTable
 */
public interface DmnDecisionTableInput {

  /**
   * The human readable name of the input if exists.
   *
   * @return the name or null if not set
   */
  String getName();

  /**
   * The expression of the input if exists.
   *
   * @return the input expression or null if not set
   */
  DmnExpression getExpression();

}
