package de.belmega.biohazard.auth.persistence.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ApplicationRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
