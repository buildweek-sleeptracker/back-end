package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.exceptions.BadRequestException;
import com.lambdaschool.sleepmood.exceptions.ResourceNotFoundException;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.repository.SleepDataRepository;
import com.lambdaschool.sleepmood.repository.UserRepository;
import com.lambdaschool.sleepmood.utilities.SortSleepDataByDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "sleepDataService")
public class SleepDataServiceImpl implements SleepDataService
{
    @Autowired
    SleepDataRepository sleeprepo;

    @Autowired
    UserRepository userrepo;

    @Override
    public List<SleepData> getAll(long userid) {

        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(sd -> {
            if (sd.getUser().getUserid() == userid)
            {
                rtnList.add(sd);
            }
        });

        Collections.sort(rtnList, new SortSleepDataByDate());
        return rtnList;
    }

    @Override
    public List<SleepData> getMonth(long userid, long yearid, long monthid)
    {
        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(sd -> {
            if (sd.getUser().getUserid() == userid &&
                sd.getWakedate().getYear() == yearid &&
                sd.getWakedate().getMonthValue() == monthid)
            {
                rtnList.add(sd);
            }
        });

        Collections.sort(rtnList, new SortSleepDataByDate());
        return rtnList;
    }

    @Override
    public List<SleepData> getWeek(long userid, long yearid, long monthid, long weekid)
    {
        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(sd -> {
            if (sd.getUser().getUserid() == userid &&
                    sd.getWakedate().getYear() == yearid &&
                    sd.getWakedate().getMonthValue() == monthid)
            {
                if (sd.getWakedate().getDayOfMonth() == weekid ||
                        sd.getWakedate().getDayOfMonth() == weekid +1 ||
                        sd.getWakedate().getDayOfMonth() == weekid +2 ||
                        sd.getWakedate().getDayOfMonth() == weekid +3 ||
                        sd.getWakedate().getDayOfMonth() == weekid +4 ||
                        sd.getWakedate().getDayOfMonth() == weekid +5 ||
                        sd.getWakedate().getDayOfMonth() == weekid +6)
                {
                    rtnList.add(sd);
                }
            }
        });

        Collections.sort(rtnList, new SortSleepDataByDate());
        return rtnList;
    }

    @Override
    public SleepData getDay(long userid, long yearid, long monthid, long dayid) throws ResourceNotFoundException
    {
        List<SleepData> rtnList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(sd -> {
            if (sd.getUser().getUserid() == userid &&
                    sd.getWakedate().getYear() == yearid &&
                    sd.getWakedate().getMonthValue() == monthid &&
                    sd.getWakedate().getDayOfMonth() == dayid)
            {
                rtnList.add(sd);
            }
        });

        if (rtnList.get(0) == null)
        {
            throw new ResourceNotFoundException("Could not find any sleep data linked to " + monthid + "/" + dayid + "/" + yearid + ".");
        }

        return rtnList.get(0);
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
    public SleepData save(SleepData sleepData, long userid) throws ResourceNotFoundException, BadRequestException
    {

        // Error handling:
        // User exists?
        User user = userrepo.findById(userid).orElseThrow(() -> new ResourceNotFoundException(Long.toString(userid)));

        // Sleep Date and Wake Date cannot be the same?


        // Sleep Data doesn't already exist?
        List<SleepData> checkList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(checkList::add);
        for (SleepData sd : checkList)
        {
            if (sd.getSleepdate().getYear() == sleepData.getSleepdate().getYear() &&
                    sd.getSleepdate().getMonthValue() == sleepData.getSleepdate().getMonthValue() &&
                    sd.getSleepdate().getDayOfMonth() == sleepData.getSleepdate().getDayOfMonth())
            {
                throw new BadRequestException("Sleep Entry already exists with the sleep date:" + sleepData.getSleepdate());
            }
            if (sd.getWakedate().getYear() == sleepData.getWakedate().getYear() &&
                    sd.getWakedate().getMonthValue() == sleepData.getWakedate().getMonthValue() &&
                    sd.getWakedate().getDayOfMonth() == sleepData.getWakedate().getDayOfMonth())
            {
                throw new BadRequestException("Sleep Entry already exists with the wake date:" + sleepData.getWakedate());
            }
        }

        SleepData newSleepData = new SleepData();

        newSleepData.setDaymood(sleepData.getDaymood());
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

        // Sleep Data doesn't already exist?
        List<SleepData> checkList = new ArrayList<>();
        sleeprepo.findAll().iterator().forEachRemaining(checkList::add);
        for (SleepData sd : checkList)
        {
            if (sleepData.getSleepdate() != null &&
                    (sleepData.getSleepdate().getYear() != newSleepData.getSleepdate().getYear() ||
                    sleepData.getSleepdate().getMonthValue() != newSleepData.getSleepdate().getMonthValue() ||
                    sleepData.getSleepdate().getDayOfMonth() != newSleepData.getSleepdate().getDayOfMonth())) {
                if (sd.getSleepdate().getYear() == sleepData.getSleepdate().getYear() &&
                        sd.getSleepdate().getMonthValue() == sleepData.getSleepdate().getMonthValue() &&
                        sd.getSleepdate().getDayOfMonth() == sleepData.getSleepdate().getDayOfMonth()) {
                    throw new BadRequestException("Sleep Entry already exists with the sleep date:" + sleepData.getSleepdate());
                }
            }
            if (sleepData.getWakedate() != null &&
                    (sleepData.getWakedate().getYear() != newSleepData.getWakedate().getYear() ||
                    sleepData.getWakedate().getMonthValue() != newSleepData.getWakedate().getMonthValue() ||
                    sleepData.getWakedate().getDayOfMonth() != newSleepData.getWakedate().getDayOfMonth())) {
                if (sd.getWakedate().getYear() == sleepData.getWakedate().getYear() &&
                        sd.getWakedate().getMonthValue() == sleepData.getWakedate().getMonthValue() &&
                        sd.getWakedate().getDayOfMonth() == sleepData.getWakedate().getDayOfMonth()) {
                    throw new BadRequestException("Sleep Entry already exists with the wake date:" + sleepData.getWakedate());
                }
            }
        }

        if (sleepData.getDaymood() != 0)
        {
            newSleepData.setDaymood(sleepData.getDaymood());
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
