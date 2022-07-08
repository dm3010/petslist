package com.example.petslist.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    @Column(unique = true)
    private String name;
    private String birthday;
    private PetType type;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username", scope = User.class)
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    public Pet() {
    }

    public Pet(String name, String birthday, PetType type) {
        this.name = name;
        this.birthday = birthday;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        user.getPets().add(this);
        this.user = user;
    }

}
