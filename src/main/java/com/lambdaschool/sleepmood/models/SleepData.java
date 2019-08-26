package com.lambdaschool.sleepmood.models;

import javax.persistence.*;

@Entity
@Table(name = "sleepdata")
public class SleepData {

    // Fields
    /*
    * id
    * userid
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
    *
    */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;



}
