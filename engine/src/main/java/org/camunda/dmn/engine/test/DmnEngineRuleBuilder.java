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

import org.apache.commons.lang3.builder.Builder;
import org.camunda.dmn.engine.DmnEngineConfiguration;
import org.camunda.dmn.engine.impl.DmnEngineConfigurationImpl;

public class DmnEngineRuleBuilder implements Builder<DmnEngineRule> {

  private boolean installAssertions;
  private boolean shutdownAssertions;
  private DmnEngineConfiguration configuration;

  public DmnEngineRuleBuilder(Object testInstance) {
    withoutAssertions();
    fromConfiguration(new DmnEngineConfigurationImpl());
  }

  /**
   * Activate installation and shutdown of assertions.
   * 
   * @return fluent instance.
   */
  public DmnEngineRuleBuilder withAssertions() {
    this.installAssertions = true;
    this.shutdownAssertions = true;

    return this;
  }

  /**
   * Activate installation and shutdown of assertions.
   * 
   * @return fluent instance.
   */
  public DmnEngineRuleBuilder withoutAssertions() {
    this.installAssertions = false;
    this.shutdownAssertions = false;

    return this;
  }

  /**
   * Should install fluent assertions on startup.
   * 
   * @param installAssertions
   *          if true will install.
   * @return fluent instance.
   */
  public DmnEngineRuleBuilder installAssertions(final boolean installAssertions) {
    this.installAssertions = installAssertions;
    return this;
  }

  /**
   * Should shutdown fluent assertions on shutdown.
   * 
   * @param shutdownAssertions
   *          if true will shutdown.
   * @return fluent instance.
   */
  public DmnEngineRuleBuilder shutdownAssertions(final boolean shutdownAssertions) {
    this.shutdownAssertions = shutdownAssertions;
    return this;
  }

  /**
   * Create engine using configuration.
   * 
   * @param configuration
   *          configuration to use.
   * @return fluent instance.
   */
  public DmnEngineRuleBuilder fromConfiguration(final DmnEngineConfiguration configuration) {
    this.configuration = configuration;
    return this;
  }

  @Override
  public DmnEngineRule build() {
    return new DmnEngineRule(configuration, installAssertions, shutdownAssertions);
  }

}