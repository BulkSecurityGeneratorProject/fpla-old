package de.ananyev.fpla.scenario;

import de.ananyev.fpla.domain.Schedule;
import de.ananyev.fpla.domain.enumeration.ScenarioEnum;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

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

    public void addSchedule(Schedule schedule) {
        Scenario scenario = schedule.getScenario().instance();
        scheduler.initialize();
        ScheduledFuture future = scheduler.schedule(new TestScenario(),
            new CronTrigger(schedule.getCronString()));
        runningScheduleList.add(future);
    }

    public void stopSchedule(Schedule schedule) {
//        //runningScheduleList.removeIf(item -> item.getId().equals(schedule.getId()) && !schedule.isActive());
//        runningScheduleList.forEach(it -> it.getId().equals(schedule.getId()));
//        scheduler.
//        scheduler.shutdown();
    }

}
