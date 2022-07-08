package com.example.petslist.model;

import javax.persistence.*;

@Entity
public class Attempts {
    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.MERGE)
    @MapsId
    @JoinColumn(name = "id")
    private User user;
    private Long time;
    private Integer count = 0;

    public Attempts() {
    }

    public Attempts(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
