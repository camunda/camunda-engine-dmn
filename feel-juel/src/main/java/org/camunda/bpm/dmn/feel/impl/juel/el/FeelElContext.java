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

import de.odysseus.el.util.SimpleContext;
import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineLogger;
import org.camunda.bpm.dmn.feel.impl.juel.FeelLogger;

import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

public class FeelElContext extends SimpleContext {

  public static final FeelEngineLogger LOG = FeelLogger.ENGINE_LOGGER;

  protected ELResolver elResolver;
  protected FunctionMapper functionMapper;
  protected VariableMapper variableMapper;

  public FeelElContext(ELResolver elResolver, FunctionMapper functionMapper, VariableMapper variableMapper) {
    this.elResolver = elResolver;
    this.functionMapper = functionMapper;
    this.variableMapper = variableMapper;

    initializeJUELFunctions();
  }

  public ELResolver getELResolver() {
    return elResolver;
  }

  public FunctionMapper getFunctionMapper() {
    return functionMapper;
  }

  public VariableMapper getVariableMapper() {
    return variableMapper;
  }

  /**
   * Initializes any custom JUEL functions
   */
  protected void initializeJUELFunctions() {
    try {
      this.setFunction("", "startsWith", FeelFunctionMapper.class.getMethod("startsWith", String.class, String.class));
    } catch (NoSuchMethodException ex) {
      throw LOG.unableToFindMethod(ex, "startsWith", String.class, String.class);
    }
  }

}
