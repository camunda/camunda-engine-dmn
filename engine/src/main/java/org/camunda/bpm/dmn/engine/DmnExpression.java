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

public interface DmnExpression {

  /**
   * The associated value type's name of the expression if exists.
   *
   * @return the type name or null if not set
   */
  String getTypeName();

  /**
   * The expression or script language of the expression if exists.
   *
   * @return the language or null if not set
   */
  String getExpressionLanguage();

  /**
   * The actual expression or script if exists.
   *
   * @return the expression/script or null if not set
   * @see #getExpressionLanguage()
   */
  String getExpression();

}
