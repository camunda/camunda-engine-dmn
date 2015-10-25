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

package org.camunda.bpm.dmn.engine.el;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.camunda.bpm.dmn.engine.util.DmnExampleVerifier.assertExample;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnEngineConfigurationImpl;
import org.camunda.bpm.dmn.engine.test.DecisionResource;
import org.camunda.bpm.dmn.engine.test.DmnDecisionTest;
import org.camunda.bpm.dmn.feel.FeelEngine;
import org.camunda.bpm.dmn.feel.FeelEngineProvider;
import org.camunda.bpm.dmn.feel.FeelException;
import org.camunda.bpm.dmn.feel.impl.FeelEngineProviderImpl;
import org.junit.Test;

public class FeelIntegrationTest extends DmnDecisionTest {

  protected FeelEngine feelEngine;

  @Override
  public DmnEngineConfiguration createDmnEngineConfiguration() {
    DmnEngineConfigurationImpl dmnEngineConfiguration = new DmnEngineConfigurationImpl();
    dmnEngineConfiguration.setFeelEngineProvider(new TestFeelEngineProvider());
    return dmnEngineConfiguration;
  }

  @Test
  public void testDefaultEngineFeelInvocation() {
    int numberOfInputEntries = 5;
    int numberOfExampleInvocations = 4;
    assertExample(engine);

    verify(feelEngine, times(numberOfInputEntries * numberOfExampleInvocations)).evaluateSimpleUnaryTests(anyString(), anyString(), anyMapOf(String.class, Object.class));
  }

  @Test
  public void testFeelAlternativeName() {
    int numberOfInputEntries = 5;
    int numberOfExampleInvocations = 4;

    DmnEngineConfigurationImpl dmnEngineConfiguration = (DmnEngineConfigurationImpl) createDmnEngineConfiguration();
    dmnEngineConfiguration.setDefaultInputEntryExpressionLanguage("feel");
    DmnEngine dmnEngine = dmnEngineConfiguration.buildEngine();

    assertExample(dmnEngine);

    verify(feelEngine, times(numberOfInputEntries * numberOfExampleInvocations)).evaluateSimpleUnaryTests(anyString(), anyString(), anyMapOf(String.class, Object.class));
  }

  @Test
  public void testFeelInputExpressions() {
    DmnEngineConfigurationImpl configuration = (DmnEngineConfigurationImpl) createDmnEngineConfiguration();
    configuration.setDefaultInputExpressionExpressionLanguage(DmnEngineConfigurationImpl.FEEL_EXPRESSION_LANGUAGE);
    DmnEngine engine = configuration.buildEngine();

    try {
      assertExample(engine);
      fail("Expression expected as FEEL input expressions are not supported.");
    }
    catch (UnsupportedOperationException e) {
      assertThat(e).hasMessageStartingWith("FEEL-01016");
      verify(feelEngine).evaluateSimpleExpression(anyString(), anyMapOf(String.class, Object.class));
    }
  }

  @Test
  public void testFeelOutputEntry() {
    DmnEngineConfigurationImpl configuration = (DmnEngineConfigurationImpl) createDmnEngineConfiguration();
    configuration.setDefaultOutputEntryExpressionLanguage(DmnEngineConfigurationImpl.FEEL_EXPRESSION_LANGUAGE);
    DmnEngine engine = configuration.buildEngine();

    try {
      assertExample(engine);
      fail("Exception expected as FEEL output entries are not supported.");
    }
    catch (UnsupportedOperationException e) {
      assertThat(e).hasMessageStartingWith("FEEL-01016");
      verify(feelEngine).evaluateSimpleExpression(anyString(), anyMapOf(String.class, Object.class));
    }
  }

  @Test
  @DecisionResource(resource = "org/camunda/bpm/dmn/engine/el/ExpressionLanguageTest.script.dmn")
  public void testFeelExceptionDoesNotContainJuel() {
    try {
      assertExample(engine, decision);
      fail("Exception expected as invalid FEEL is used.");
    }
    catch (FeelException e) {
      assertThat(e).hasMessageStartingWith("FEEL-01015");
      assertThat(e.getMessage()).doesNotContain("${");
    }
  }

  public class TestFeelEngineProvider implements FeelEngineProvider {

    public TestFeelEngineProvider() {
      FeelEngineProviderImpl feelEngineProvider = new FeelEngineProviderImpl();
      feelEngine = spy(feelEngineProvider.createInstance());
    }

    public FeelEngine createInstance() {
      return feelEngine;
    }

  }

}
