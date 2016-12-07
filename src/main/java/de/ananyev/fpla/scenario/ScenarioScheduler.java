package de.ananyev.fpla.scenario;

import de.ananyev.fpla.domain.Schedule;
import de.ananyev.fpla.domain.enumeration.ScenarioEnum;
import de.ananyev.fpla.util.exception.ScriptNotFoundException;
//import
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by Ilya Ananyev on 24.11.16.
 */
@Component
public class ScenarioScheduler {
    final private ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    final private ArrayList<ScheduledFuture> runningScheduleList = new ArrayList<>();

    @Inject
    private TestScenario testScenario;
    @Inject
    private CheckIpScenario checkIpScenario;

    public void addSchedule(Schedule schedule) throws ScriptNotFoundException {
        try {
            Scenario scenario = (schedule.getScenario() == ScenarioEnum.checkIpScenario)
                ? checkIpScenario : testScenario;
            scheduler.initialize();
            ScheduledFuture future = scheduler.schedule(scenario,
                new CronTrigger(schedule.getCronString()));
            runningScheduleList.add(future);

        } catch (NullPointerException e) {
            throw new ScriptNotFoundException();
        }
    }

    public void stopSchedule(Schedule schedule) {
        /* */
    }

}
