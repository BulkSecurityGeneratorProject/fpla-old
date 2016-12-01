package de.ananyev.fpla.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserAccount.
 */
@Entity
@Table(name = "user_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "userAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Script> scripts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserAccount name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Script> getScripts() {
        return scripts;
    }

    public UserAccount scripts(Set<Script> scripts) {
        this.scripts = scripts;
        return this;
    }

    public UserAccount addScript(Script script) {
        scripts.add(script);
        script.setUserAccount(this);
        return this;
    }

    public UserAccount removeScript(Script script) {
        scripts.remove(script);
        script.setUserAccount(null);
        return this;
    }

    public void setScripts(Set<Script> scripts) {
        this.scripts = scripts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAccount userAccount = (UserAccount) o;
        if(userAccount.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userAccount.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
