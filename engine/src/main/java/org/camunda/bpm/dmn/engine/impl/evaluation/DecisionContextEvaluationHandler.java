/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.dmn.engine.impl.evaluation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionResultEntries;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionLogicEvaluationEvent;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionContextEntryImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionContextImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionResultEntriesImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionResultImpl;
import org.camunda.bpm.dmn.engine.impl.DmnExpressionImpl;
import org.camunda.bpm.dmn.engine.impl.DmnVariableImpl;
import org.camunda.bpm.dmn.engine.impl.delegate.DmnDecisionContextEvaluationEventImpl;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.context.VariableContext;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class DecisionContextEvaluationHandler implements DmnDecisionLogicEvaluationHandler {

  protected final ExpressionEvaluationHandler expressionEvaluationHandler;

  protected final String literalExpressionLanguage;

  public DecisionContextEvaluationHandler(DefaultDmnEngineConfiguration configuration) {
    expressionEvaluationHandler = new ExpressionEvaluationHandler(configuration);

    literalExpressionLanguage = configuration.getDefaultLiteralExpressionLanguage();
  }

  @Override
  public DmnDecisionLogicEvaluationEvent evaluate(DmnDecision decision, VariableContext variableContext) {
    DmnDecisionContextEvaluationEventImpl evaluationResult = new DmnDecisionContextEvaluationEventImpl();
    evaluationResult.setDecision(decision);
    evaluationResult.setExecutedDecisionElements(1);

    DmnDecisionContextImpl dmnDecisionContext = (DmnDecisionContextImpl) decision.getDecisionLogic();
    DmnVariableImpl variable = dmnDecisionContext.getVariable();
    
    Map<String, TypedValue> results = new HashMap<String, TypedValue>();
    for (DmnDecisionContextEntryImpl contextEntry : dmnDecisionContext.getContextEntries()) {
      
      DmnVariableImpl contextEntryVariable = contextEntry.getVariable();

      // TODO this can be also something different
      Object evaluateExpression = evaluateLiteralExpression(contextEntry.getExpression(), variableContext);
      TypedValue typedValue = contextEntryVariable.getTypeDefinition().transform(evaluateExpression);
      
      results.put(contextEntryVariable.getName(), typedValue);
    }
    evaluationResult.setContextEntryResults(results);

    // TODO set output value if available
    evaluationResult.setOutputName(variable.getName());

    return evaluationResult;
  }

  protected Object evaluateLiteralExpression(DmnExpressionImpl expression, VariableContext variableContext) {
    String expressionLanguage = expression.getExpressionLanguage();
    if (expressionLanguage == null) {
      expressionLanguage = literalExpressionLanguage;
    }
    return expressionEvaluationHandler.evaluateExpression(expressionLanguage, expression, variableContext);
  }

  @Override
  public DmnDecisionResult generateDecisionResult(DmnDecisionLogicEvaluationEvent event) {
    DmnDecisionContextEvaluationEventImpl evaluationEvent = (DmnDecisionContextEvaluationEventImpl) event;

    DmnDecisionResultEntriesImpl result = new DmnDecisionResultEntriesImpl();
    result.putValue(evaluationEvent.getOutputName(), evaluationEvent.getOutputValue());
    
    if (evaluationEvent.getOutputValue() == null)
    {
      // TODO dirty hack to build a map of maps 
      Map<String, Object> values = new HashMap<String, Object>();
      for (Entry<String, TypedValue> entry : evaluationEvent.getContextEntryResults().entrySet()) {
        
        values.put(entry.getKey(), entry.getValue().getValue());
      }
      
      result.putValue(evaluationEvent.getOutputName(), Variables.untypedValue(values));
    }
    
    return new DmnDecisionResultImpl(Collections.<DmnDecisionResultEntries> singletonList(result));
  }

}
