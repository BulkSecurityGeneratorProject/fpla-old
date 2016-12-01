package de.ananyev.fpla.domain;

import de.ananyev.fpla.domain.enumeration.ScenarioEnum;
import de.ananyev.fpla.scenario.Scenario;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    //example: "0 15 9-17 * * MON-FRI"
//    @Pattern(regexp = "[a-z]")
    @NotNull
    @Column(name = "cron_string", nullable = false)
    private String cronString;

    @Column(name = "active")
    private Boolean active;

    @Enumerated(EnumType.STRING)
    @Column(name = "scenario")
    private ScenarioEnum scenario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Schedule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Schedule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCronString() {
        return cronString;
    }

    public Schedule cronString(String cronString) {
        this.cronString = cronString;
        return this;
    }

    public void setCronString(String cronString) {
        this.cronString = cronString;
    }

    public Boolean isActive() {
        return active;
    }

    public Schedule active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ScenarioEnum getScenario() {
        return scenario;
    }

    public Schedule scenario(ScenarioEnum scenario) {
        this.scenario = scenario;
        return this;
    }

    public void setScenario(ScenarioEnum scenario) {
        this.scenario = scenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        if(schedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", cronString='" + cronString + "'" +
            ", active='" + active + "'" +
            ", scenario='" + scenario + "'" +
            '}';
    }
}
