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

package org.camunda.dmn.engine.test;

import org.camunda.dmn.engine.DmnDecision;
import org.camunda.dmn.engine.DmnEngine;
import org.camunda.dmn.engine.DmnEngineConfiguration;
import org.camunda.dmn.engine.test.asserts.DmnAssertions;
import org.camunda.dmn.engine.test.asserts.DmnEngineAssertion;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class DmnEngineRule extends TestWatcher {

  public static final String DMN_SUFFIX = "dmn";

  protected DmnEngine engine;
  protected DmnEngineConfiguration configuration;
  protected DmnDecision decision;
  private boolean installAssertions;
  private boolean shutdownAssertions;

  public DmnEngineRule(final DmnEngineConfiguration configuration, final boolean installAssertions, final boolean shutdownAssertions) {
    this.configuration = configuration;
    this.installAssertions = installAssertions;
    this.shutdownAssertions = shutdownAssertions;

  }

  @Override
  protected void starting(Description description) {

    DmnEngineAssertion assertion = null;
    
    if (engine == null) {
      initializeDmnEngine();
      if (installAssertions) {
        assertion = DmnAssertions.assertThat(engine);
      }
    }
    
    decision = loadDecision(description);
    
    if (installAssertions && (assertion != null)) {
      assertion.evaluates(decision);
    }
  }

  @Override
  protected void finished(Description description) {
    if (shutdownAssertions) {
      DmnAssertions.reset();
    }

  }

  protected void initializeDmnEngine() {
    engine = configuration.buildEngine();
  }

  protected DmnDecision loadDecision(Description description) {
    DecisionResource decisionResource = description.getAnnotation(DecisionResource.class);

    if (decisionResource != null) {

      String resourcePath = decisionResource.resource();

      resourcePath = expandResourcePath(description, resourcePath);

      String decisionId = decisionResource.decisionId();

      if (decisionId == null || decisionId.isEmpty()) {
        return engine.parseDecision(resourcePath);
      } else {
        return engine.parseDecision(resourcePath, decisionId);
      }
    } else {
      return null;
    }
  }

  protected String expandResourcePath(Description description, String resourcePath) {
    if (resourcePath.contains("/")) {
      // already expanded path
      return resourcePath;
    } else {
      Class<?> testClass = description.getTestClass();
      if (resourcePath.isEmpty()) {
        // use test class and method name as resource file name
        return testClass.getName().replace(".", "/") + "." + description.getMethodName() + "." + DMN_SUFFIX;
      } else {
        // use test class location as resource location
        return testClass.getPackage().getName().replace(".", "/") + "/" + resourcePath;
      }
    }
  }

}
