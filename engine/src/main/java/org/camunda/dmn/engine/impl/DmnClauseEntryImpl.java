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

package org.camunda.dmn.engine.impl;

import org.camunda.dmn.engine.DmnClause;
import org.camunda.dmn.engine.DmnClauseEntry;

public class DmnClauseEntryImpl extends DmnExpressionImpl implements DmnClauseEntry {

  protected DmnClause clause;

  public DmnClause getClause() {
    return clause;
  }

  public void setClause(DmnClause clause) {
    this.clause = clause;
  }

  public String toString() {
    String clauseKey = null;
    if (clause != null) {
      clauseKey = clause.getKey();
    }
    return "DmnClauseEntryImpl{" +
      "key='" + key + '\'' +
      ", name='" + name + '\'' +
      ", expressionLanguage='" + expressionLanguage + '\'' +
      ", expression='" + expression + '\'' +
      ", clauseKey=" + clauseKey +
      '}';
  }

}
