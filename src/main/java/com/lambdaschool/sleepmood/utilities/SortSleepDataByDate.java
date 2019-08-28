package com.lambdaschool.sleepmood.utilities;

import com.lambdaschool.sleepmood.models.SleepData;

import java.util.Comparator;

public class SortSleepDataByDate implements Comparator<SleepData>
{
    public int compare(SleepData a, SleepData b)
    {
        return a.getSleepdate().compareTo(b.getSleepdate());
    }
}
