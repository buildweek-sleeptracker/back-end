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
    public SleepData getSingle(long sleepid, long userid) throws ResourceNotFoundException
    {

        SleepData rtnSleepData = sleeprepo.findById(sleepid).orElseThrow(() -> new ResourceNotFoundException(Long.toString(sleepid)));
        if (rtnSleepData.getUser().getUserid() != userid)
        {
            throw new ResourceNotFoundException("SleepData does not belong to current user");
        }
        return rtnSleepData;
    }

    @Override
    public void delete(long id, long userid) throws ResourceNotFoundException
    {
        // Checking to make sure SleepData exists AND belongs to currently logged in user
        SleepData newSleepData = sleeprepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (newSleepData.getUser().getUserid() != userid)
        {
            throw new ResourceNotFoundException("SleepData does not belong to current user");
        }

        sleeprepo.deleteById(id);
    }

    @Override
    public SleepData save(SleepData sleepData, long userid) throws ResourceNotFoundException
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
    public SleepData update(SleepData sleepData, long id, long userid) throws ResourceNotFoundException
    {
        SleepData newSleepData = sleeprepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if (newSleepData.getUser().getUserid() != userid)
        {
            throw new ResourceNotFoundException("SleepData does not belong to current user");
        }

        if (sleepData.getAvgmood() != 0)
        {
            newSleepData.setAvgmood(sleepData.getAvgmood());
        }
        if (sleepData.getSleepdate() != null) {
            newSleepData.setSleepdate(sleepData.getSleepdate());
        }
        if (sleepData.getSleepmood() != 0) {
            newSleepData.setSleepmood(sleepData.getSleepmood());
        }
        if (sleepData.getWakedate() != null) {
            newSleepData.setWakedate(sleepData.getWakedate());
        }
        if (sleepData.getWakemood() != 0) {
            newSleepData.setWakemood(sleepData.getWakemood());
        }

        return sleeprepo.save(newSleepData);
    }
}
