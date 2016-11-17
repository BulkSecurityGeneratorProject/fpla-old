package de.ananyev.fpla.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Script.
 */
@Entity
@Table(name = "script")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    //TODO change to Lob
    @Column(name = "text")
    private String text;

    @ManyToOne
    private UserAccount userAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Script name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public Script text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public Script userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Script script = (Script) o;
        if(script.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, script.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Script{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", text='" + text + "'" +
            '}';
    }
}
