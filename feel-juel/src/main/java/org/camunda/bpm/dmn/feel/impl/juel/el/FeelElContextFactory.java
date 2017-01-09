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
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

import org.camunda.bpm.dmn.feel.impl.juel.FeelEngineLogger;
import org.camunda.bpm.dmn.feel.impl.juel.FeelLogger;
import org.camunda.bpm.engine.variable.context.VariableContext;

import de.odysseus.el.util.SimpleResolver;

public class FeelElContextFactory implements ElContextFactory {

  private static Class<? extends FeelElContext> FEEL_EL_CONTEXT_CLASS = FeelElContext.class;
  public static final FeelEngineLogger LOG = FeelLogger.ENGINE_LOGGER;

  public ELContext createContext(ExpressionFactory expressionFactory, VariableContext variableContext) {
    ELContext returnContext;
    ELResolver elResolver = createElResolver();
    FunctionMapper functionMapper = createFunctionMapper();
    VariableMapper variableMapper = createVariableMapper(expressionFactory, variableContext);

    try {
      returnContext = (ELContext) FEEL_EL_CONTEXT_CLASS.getConstructors()[0].newInstance(elResolver, functionMapper, variableMapper);
    } catch (Exception ex) {
      throw LOG.unableToInitializeFeelEngineContext(ex);
    }

    return returnContext;
  }

  public ELResolver createElResolver() {
    return new SimpleResolver(true);
  }

  public FunctionMapper createFunctionMapper() {
    CompositeFunctionMapper functionMapper = new CompositeFunctionMapper();
    functionMapper.add(new FeelFunctionMapper());
    return functionMapper;
  }

  public VariableMapper createVariableMapper(ExpressionFactory expressionFactory, VariableContext variableContext) {
    return new FeelTypedVariableMapper(expressionFactory, variableContext);
  }

  /**
   * Sets a new "custom" ELContext Class.
   * This new custom ELContext can be modified to support custom JUEL Methods for instance.
   * @param elContextClassName The name of the custom class
   * @throws ClassNotFoundException if the class can not be found
     */
  public static void setFeelElContextClass(final String elContextClassName) throws ClassNotFoundException {
    final Class elContextClass = Class.forName(elContextClassName);
    if (elContextClass.isAssignableFrom(FeelElContext.class)) {
      FEEL_EL_CONTEXT_CLASS = elContextClass;
    }
  }

}
