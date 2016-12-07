package de.ananyev.fpla.scenario;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by Ilya Ananyev on 29.11.16.
 */
@Component
public class CheckIpScenario extends Scenario {
    private static int processesAmount = 1;
    private static ArrayList<String> dependenciesNames = new ArrayList<>();
    static {
        dependenciesNames.add("checkIp");
    }

    public CheckIpScenario() {
        super(processesAmount, dependenciesNames);
    }

    @Override
    public void run() {
        log.info("Checking IP");
    }
}
