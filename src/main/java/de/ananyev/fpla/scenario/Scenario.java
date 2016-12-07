package de.ananyev.fpla.scenario;

import com.codahale.metrics.annotation.Timed;
import de.ananyev.fpla.domain.Script;
import de.ananyev.fpla.repository.ScriptRepository;
import de.ananyev.fpla.util.exception.ScriptNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Created by Ilya Ananyev on 24.11.16.
 */
@Component
@Configurable
public abstract class Scenario implements Runnable {
    @Autowired
    private ScriptRepository scriptRepository;

    protected final Logger log = LoggerFactory.getLogger(Scenario.class);
    protected int processesAmount;
    protected final ArrayList<Script> dependencyScripts = new ArrayList<>();
    protected final ArrayList<String> dependencyScriptNames = new ArrayList<>();

    protected Scenario(int processesAmount, List<String> dependencyScriptNameList) throws NullPointerException {
        this.processesAmount = processesAmount;
        this.dependencyScriptNames.addAll(dependencyScriptNameList);
    }

    @PostConstruct
    protected void initialize() {
        this.dependencyScriptNames.forEach(scriptName -> {
            dependencyScripts.add(Optional.ofNullable(scriptRepository.findOneByName(scriptName))
                .orElseThrow(NullPointerException::new));
        });
    }
}
