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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FeelElContextFactory implements ElContextFactory {

  public static final FeelEngineLogger LOG = FeelLogger.ENGINE_LOGGER;

  private static List<FunctionHolder> functionHolderList = new ArrayList<FunctionHolder>();

  static class FunctionHolder {
    private String prefix;
    private String localName;
    private Method method;

    public FunctionHolder(String prefix, String localName, Method method) {
      this.prefix = prefix;
      this.localName = localName;
      this.method = method;
    }
  }

  public ELContext createContext(ExpressionFactory expressionFactory, VariableContext variableContext) {
    ELResolver elResolver = createElResolver();
    FunctionMapper functionMapper = createFunctionMapper();
    VariableMapper variableMapper = createVariableMapper(expressionFactory, variableContext);

    FeelElContext returnContext = new FeelElContext(elResolver, functionMapper, variableMapper);
    for (FunctionHolder holder : functionHolderList) {
      returnContext.setFunction(holder.prefix, holder.localName, holder.method);
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

  public void addCustomFunction(String prefix, String localName, Method method) {
    functionHolderList.add(new FunctionHolder(prefix, localName, method));
  }

}
