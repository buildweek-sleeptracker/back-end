package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.repository.SleepDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "sleepDataService")
public class SleepDataServiceImpl implements SleepDataService
{
    @Autowired
    SleepDataRepository sleeprepo;

    @Override
    public List<SleepData> getAll(Pageable pageable, long userid) {

        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public List<SleepData> getInterval(Date start, Date end, long userid) {
        return null;
    }

    @Override
    public SleepData getSingle(Date date, long userid) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public SleepData save(SleepData sleepData) {
        return null;
    }

    @Override
    public SleepData update(SleepData sleepData, long id) {
        return null;
    }
}
