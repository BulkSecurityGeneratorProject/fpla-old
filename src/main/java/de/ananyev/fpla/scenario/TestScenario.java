package de.ananyev.fpla.scenario;

import de.ananyev.fpla.repository.ScriptRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by Ilya Ananyev on 29.11.16.
 */
@Component
@Configurable
public class TestScenario extends Scenario {
    private static int processesAmount = 1;
    private static ArrayList<String> dependenciesNames = new ArrayList<>();
    static {
        dependenciesNames.add("checkIp");
        dependenciesNames.add("test");
    }

    public TestScenario() {
        super(processesAmount, dependenciesNames);
    }

    @Override
    public void run() {
        log.info("Testing scenario");
    }
}
