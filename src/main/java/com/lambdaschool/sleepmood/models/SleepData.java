package com.lambdaschool.sleepmood.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",
                nullable = false)
    @JsonIgnoreProperties({"sleepdata", "hibernateLazyInitializer"})
    private User user;

    @Column(nullable = false)
    private LocalDateTime sleepdate;

    @Column(nullable = false)
    private LocalDateTime wakedate;

    @Column(nullable = false)
    private int sleepmood;

    @Column(nullable = false)
    private int wakemood;

    @Column(nullable = false)
    private int avgmood;

    public SleepData() {}

    public SleepData(User user, LocalDateTime sleepdate, LocalDateTime wakedate, int sleepmood, int wakemood, int avgmood) {
        this.user = user;
        this.sleepdate = sleepdate;
        this.wakedate = wakedate;
        this.sleepmood = sleepmood;
        this.wakemood = wakemood;
        this.avgmood = avgmood;
    }
    public SleepData(LocalDateTime sleepdate, LocalDateTime wakedate, int sleepmood, int wakemood, int avgmood) {
        this.sleepdate = sleepdate;
        this.wakedate = wakedate;
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

    public LocalDateTime getSleepdate() {
        return sleepdate;
    }

    public void setSleepdate(LocalDateTime sleepdate) {
        this.sleepdate = sleepdate;
    }

    public LocalDateTime getWakedate() {
        return wakedate;
    }

    public void setWakedate(LocalDateTime wakedate) {
        this.wakedate = wakedate;
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
