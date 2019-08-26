package com.lambdaschool.sleepmood.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sleepdata")
public class SleepData {

    // Fields
    /*
    * id
    * user
    *
    * sleepdate
    * sleepmood
    *
    * wakedate
    * wakemood
    *
    * hoursslept
    *
    * averagemood
    */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonIgnoreProperties("sleepdata")
    private User user;

    @Column(nullable = false)
    private Date sleepdate;

    @Column(nullable = false)
    private Date wakedate;

    @Column(nullable = false)
    private int hoursslept;

    @Column(nullable = false)
    private int sleepmood;

    @Column(nullable = false)
    private int wakemood;

    @Column(nullable = false)
    private int avgmood;

    public SleepData() {}

    public SleepData(User user, Date sleepdate, Date wakedate, int hoursslept, int sleepmood, int wakemood, int avgmood) {
        this.user = user;
        this.sleepdate = sleepdate;
        this.wakedate = wakedate;
        this.hoursslept = hoursslept;
        this.sleepmood = sleepmood;
        this.wakemood = wakemood;
        this.avgmood = avgmood;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSleepdate() {
        return sleepdate;
    }

    public void setSleepdate(Date sleepdate) {
        this.sleepdate = sleepdate;
    }

    public Date getWakedate() {
        return wakedate;
    }

    public void setWakedate(Date wakedate) {
        this.wakedate = wakedate;
    }

    public int getHoursslept() {
        return hoursslept;
    }

    public void setHoursslept(int hoursslept) {
        this.hoursslept = hoursslept;
    }

    public int getSleepmood() {
        return sleepmood;
    }

    public void setSleepmood(int sleepmood) {
        this.sleepmood = sleepmood;
    }

    public int getWakemood() {
        return wakemood;
    }

    public void setWakemood(int wakemood) {
        this.wakemood = wakemood;
    }

    public int getAvgmood() {
        return avgmood;
    }

    public void setAvgmood(int avgmood) {
        this.avgmood = avgmood;
    }
}
