package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.models.SleepData;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface SleepDataService
{
    List<SleepData> getAll(long userid);

    List<SleepData> getMonth(long userid, long yearid, long monthid);

    List<SleepData> getWeek(long userid, long yearid, long monthid, long weekid);

    SleepData getDay(long userid, long yearid, long monthid, long dayid);

    SleepData getSingle(long sleepid, long userid);

    void delete(long id, long userid);

    SleepData save(SleepData sleepData, long userid);

    SleepData update(SleepData sleepData, long id, long userid);
}
