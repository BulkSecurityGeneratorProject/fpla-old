package de.ananyev.fpla.domain.enumeration;

import de.ananyev.fpla.scenario.CheckIpScenario;
import de.ananyev.fpla.scenario.Scenario;
import de.ananyev.fpla.scenario.TestScenario;

/**
 * The Scenario enumeration.
 */
// TODO test
public enum ScenarioEnum {

    testScenario {
        @Override
        public Scenario instance() {
            return new TestScenario();
        }
    }, checkIpScenario {
        @Override
        public Scenario instance() {
            return new CheckIpScenario();
        }
    };

    public abstract Scenario instance();
}
