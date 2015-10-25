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

package org.camunda.bpm.dmn.engine;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.dmn.engine.context.DmnContextFactory;
import org.camunda.bpm.dmn.engine.handler.DmnElementHandlerRegistry;
import org.camunda.bpm.dmn.engine.hitpolicy.DmnHitPolicyHandler;
import org.camunda.bpm.dmn.engine.transform.DmnTransformFactory;
import org.camunda.bpm.dmn.engine.transform.DmnTransformListener;
import org.camunda.bpm.dmn.engine.transform.DmnTransformer;
import org.camunda.bpm.dmn.engine.type.DataTypeTransformerFactory;
import org.camunda.bpm.dmn.feel.FeelEngineProvider;
import org.camunda.bpm.model.dmn.HitPolicy;

public interface DmnEngineConfiguration {

  DmnContextFactory getDmnContextFactory();

  DmnTransformer getTransformer();

  DmnTransformFactory getTransformFactory();

  DmnElementHandlerRegistry getElementHandlerRegistry();

  DmnEngineMetricCollector getEngineMetricCollector();

  List<DmnTransformListener> getCustomPreDmnTransformListeners();

  List<DmnTransformListener> getCustomPostDmnTransformListeners();

  List<DmnDecisionTableListener> getCustomPreDmnDecisionTableListeners();

  List<DmnDecisionTableListener> getCustomDmnDecisionTableListeners();

  List<DmnDecisionTableListener> getCustomPostDmnDecisionTableListeners();

  Map<HitPolicy, DmnHitPolicyHandler> getHitPolicyHandlers();

  DmnScriptEngineResolver getScriptEngineResolver();

  FeelEngineProvider getFeelEngineProvider();

  DataTypeTransformerFactory getDataTypeTransformerFactory();

  String getDefaultAllowedValueExpressionLanguage();

  String getDefaultInputEntryExpressionLanguage();

  String getDefaultInputExpressionExpressionLanguage();

  String getDefaultOutputEntryExpressionLanguage();

  DmnEngine buildEngine();

}
