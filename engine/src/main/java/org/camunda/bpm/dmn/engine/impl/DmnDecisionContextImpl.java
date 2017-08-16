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
package org.camunda.bpm.dmn.engine.impl;

import java.util.List;

import org.camunda.bpm.dmn.engine.DmnDecisionLogic;

public class DmnDecisionContextImpl implements DmnDecisionLogic {

  protected DmnVariableImpl variable;
  protected List<DmnDecisionContextEntryImpl> entries;

  public DmnVariableImpl getVariable() {
    return variable;
  }
  
  public void setVariable(DmnVariableImpl variable) {
    this.variable = variable;
  }
  
  public List<DmnDecisionContextEntryImpl> getContextEntries() { 
    return entries;
  }
  
  public void setContextEntries(List<DmnDecisionContextEntryImpl> entries) {
    this.entries = entries;
  }

  @Override
  public String toString() {
    return "DmnDecisionContextImpl [variable=" + variable + ", contextEntries=" + entries + "]";
  }

}
