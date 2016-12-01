package de.ananyev.fpla.scenario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Ilya Ananyev on 24.11.16.
 */
public abstract class Scenario implements Runnable {
    protected final Logger log = LoggerFactory.getLogger(Scenario.class);
}
