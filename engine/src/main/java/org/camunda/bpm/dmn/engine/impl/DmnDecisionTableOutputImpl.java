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

package org.camunda.bpm.dmn.engine.impl;

import org.camunda.bpm.dmn.engine.DmnDecisionTableOutput;
import org.camunda.bpm.dmn.engine.impl.spi.type.DmnTypeDefinition;

public class DmnDecisionTableOutputImpl implements DmnDecisionTableOutput {

  protected String id;
  protected String name;
  protected String outputName;
  protected DmnTypeDefinition typeDefinition;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOutputName() {
    return outputName;
  }

  public void setOutputName(String outputName) {
    this.outputName = outputName;
  }

  public DmnTypeDefinition getTypeDefinition() {
    return typeDefinition;
  }

  public void setTypeDefinition(DmnTypeDefinition typeDefinition) {
    this.typeDefinition = typeDefinition;
  }

  public String getTypeName() {
    if (typeDefinition == null) {
      return null;
    }
    return typeDefinition.getTypeName();
  }

  @Override
  public String toString() {
    return "DmnDecisionTableOutputImpl{" +
      "id='" + id + '\'' +
      ", name='" + name + '\'' +
      ", outputName='" + outputName + '\'' +
      ", typeDefinition=" + typeDefinition +
      '}';
  }

}
