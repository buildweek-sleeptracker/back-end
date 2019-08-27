package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.exceptions.ResourceNotFoundException;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.repository.SleepDataRepository;
import com.lambdaschool.sleepmood.repository.UserRepository;
import com.lambdaschool.sleepmood.utilities.SortSleepDataByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service(value = "sleepDataService")
public class SleepDataServiceImpl implements SleepDataService
{
    @Autowired
    SleepDataRepository sleeprepo;

    @Autowired
    UserRepository userrepo;

    @Override
    public List<SleepData> getAll(Pageable pageable, long userid) {

        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll(pageable).iterator().forEachRemaining(sd -> {
            if (sd.getUser().getUserid() == userid)
            {
                rtnList.add(sd);
            }
        });

        Collections.sort(rtnList, new SortSleepDataByDate());
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
    public SleepData save(SleepData sleepData, long userid)
    {

        User user = userrepo.findById(userid).orElseThrow(() -> new ResourceNotFoundException(Long.toString(userid)));

        SleepData newSleepData = new SleepData();

        newSleepData.setAvgmood(sleepData.getAvgmood());
        newSleepData.setSleepdate(sleepData.getSleepdate());
        newSleepData.setSleepmood(sleepData.getSleepmood());
        newSleepData.setUser(user);
        newSleepData.setWakedate(sleepData.getWakedate());
        newSleepData.setWakemood(sleepData.getWakemood());

        return sleeprepo.save(newSleepData);
    }

    @Override
    public SleepData update(SleepData sleepData, long id) {
        return null;
    }
}
