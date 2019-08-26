package com.lambdaschool.sleepmood.repository;

import com.lambdaschool.sleepmood.models.SleepData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SleepDataRepository extends PagingAndSortingRepository<SleepData, Long>
{

}
