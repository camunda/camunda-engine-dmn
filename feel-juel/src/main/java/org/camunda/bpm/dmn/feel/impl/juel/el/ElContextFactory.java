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

package org.camunda.bpm.dmn.feel.impl.juel.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import org.camunda.bpm.engine.variable.context.VariableContext;

import java.lang.reflect.Method;

public interface ElContextFactory {

  /**
   * Create a {@link ELContext} for the given {@link ExpressionFactory} and {@link VariableContext}.
   *
   * @param expressionFactory the {@link ExpressionFactory} to use
   * @param variableContext the {@link VariableContext} to use
   * @return the {@link ELContext} instance
   */
  ELContext createContext(ExpressionFactory expressionFactory, VariableContext variableContext);

  /**
   *
   * @param prefix
   * @param localName
   * @param method
     */
  void addCustomFunction(String prefix, String localName, Method method);

}
