package com.lambdaschool.sleepmood.repository;

import com.lambdaschool.sleepmood.models.SleepData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SleepDataRepository extends PagingAndSortingRepository<SleepData, Long>
{
//    @Transactional
//    @Query(value = SELECT)
}
