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

package org.camunda.bpm.dmn.engine.impl.hitpolicy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.camunda.bpm.dmn.engine.delegate.DmnDecisionTableEvaluationEvent;
import org.camunda.bpm.dmn.engine.delegate.DmnEvaluatedDecisionRule;
import org.camunda.bpm.dmn.engine.delegate.DmnEvaluatedOutput;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl;
import org.camunda.bpm.dmn.engine.impl.DmnLogger;
import org.camunda.bpm.dmn.engine.impl.delegate.DmnDecisionTableEvaluationEventImpl;
import org.camunda.bpm.dmn.engine.impl.spi.hitpolicy.DmnHitPolicyHandler;
import org.camunda.bpm.model.dmn.HitPolicy;

public class PriorityHitPolicyHandler implements DmnHitPolicyHandler {

  public static final DmnHitPolicyLogger LOG = DmnLogger.HIT_POLICY_LOGGER;
  protected static final HitPolicyEntry HIT_POLICY = new HitPolicyEntry(HitPolicy.PRIORITY, null);

  @Override
  public DmnDecisionTableEvaluationEvent apply(DmnDecisionTableEvaluationEvent decisionTableEvaluationEvent) {
    List<DmnEvaluatedDecisionRule> matchingRules = decisionTableEvaluationEvent.getMatchingRules();

    DmnDecisionTableImpl decisionTable = (DmnDecisionTableImpl) decisionTableEvaluationEvent.getDecisionTable().getDecisionLogic();
    DmnDecisionTableOutputImpl output = decisionTable.getOutputs().get(0);
    
    DmnEvaluatedDecisionRule machingRule = getRuleWithHighestPriority(output.getOutputValues(), matchingRules);
    
    if (machingRule != null)
    {
      matchingRules = Collections.singletonList(machingRule);
    }
    else
    {
      matchingRules = Collections.emptyList();
    }
    
    ((DmnDecisionTableEvaluationEventImpl) decisionTableEvaluationEvent).setMatchingRules(matchingRules);
    
    return decisionTableEvaluationEvent;
  }
  
  private DmnEvaluatedDecisionRule getRuleWithHighestPriority(List<String> outputValues, List<DmnEvaluatedDecisionRule> matchingRules)
  {
    for (String outputValue : outputValues) {
      for (DmnEvaluatedDecisionRule matchingRule : matchingRules)
      {
        // TODO verify that there is only one output value
        Collection<DmnEvaluatedOutput> evaluatedOutputs = matchingRule.getOutputEntries().values();
        DmnEvaluatedOutput evaluatedOutput = evaluatedOutputs.iterator().next();
        
        // TODO support more value types
        Object evaluatedOutputValue = evaluatedOutput.getValue().getValue();
        if (evaluatedOutputValue instanceof String)
        {
          String stringValue = "\"" + evaluatedOutputValue + "\"";
          if (stringValue.equals(outputValue))
          {
            return matchingRule;
          }
        }
        else if (evaluatedOutputValue instanceof Boolean)
        {
          String booleanValue = Boolean.toString((Boolean) evaluatedOutputValue);
          if (booleanValue.equals(outputValue))
          {
            return matchingRule;
          }
        }
      }
    }
    return null;
  }

  @Override
  public HitPolicyEntry getHitPolicyEntry() {
    return HIT_POLICY;
  }

  @Override
  public String toString() {
    return "PriorityHitPolicyHandler{}";
  }

}
