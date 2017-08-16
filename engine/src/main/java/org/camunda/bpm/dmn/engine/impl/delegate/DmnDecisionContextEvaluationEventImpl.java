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
package org.camunda.bpm.dmn.engine.impl.delegate;

import java.util.Map;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionContextEvaluationEvent;
import org.camunda.bpm.engine.variable.value.TypedValue;

public class DmnDecisionContextEvaluationEventImpl implements DmnDecisionContextEvaluationEvent {

  protected DmnDecision decision;

  protected String outputName;
  protected TypedValue outputValue;

  private Map<String, TypedValue> contextEntryResults;
  
  protected long executedDecisionElements;

  public DmnDecision getDecision() {
    return decision;
  }

  public void setDecision(DmnDecision decision) {
    this.decision = decision;
  }

  public String getOutputName() {
    return outputName;
  }

  public void setOutputName(String outputName) {
    this.outputName = outputName;
  }

  public TypedValue getOutputValue() {
    return outputValue;
  }

  public void setOutputValue(TypedValue outputValue) {
    this.outputValue = outputValue;
  }

  public long getExecutedDecisionElements() {
    return executedDecisionElements;
  }

  public void setExecutedDecisionElements(long executedDecisionElements) {
    this.executedDecisionElements = executedDecisionElements;
  }
  
  public Map<String, TypedValue> getContextEntryResults() {
    return contextEntryResults;
  }
  
  public void setContextEntryResults(Map<String, TypedValue> contextEntryResults) {
    this.contextEntryResults = contextEntryResults;
  }

  @Override
  public String toString() {
    return "DmnDecisionContextEvaluationEventImpl [" +
        " key="+ decision.getKey() +
        ", name="+ decision.getName() +
        ", decisionLogic=" + decision.getDecisionLogic() +
        ", outputName=" + outputName +
        ", outputValue=" + outputValue +
        ", executedDecisionElements=" + executedDecisionElements +
        "]";
  }

}
