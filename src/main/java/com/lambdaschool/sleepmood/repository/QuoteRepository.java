package com.lambdaschool.sleepmood.repository;

import com.lambdaschool.starthere.models.Quote;
import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long>
{

}
