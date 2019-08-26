package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.models.SleepData;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SleepDataService
{
    List<SleepData> getAll(Pageable pageable, long userid);

    List<SleepData> getInterval(Date start, Date end, long userid);

    SleepData getSingle(Date date, long userid);

    void delete(long id);

    SleepData save(SleepData sleepData);

    SleepData update(SleepData sleepData, long id);
}
