package com.lambdaschool.sleepmood.repository;

import com.lambdaschool.sleepmood.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByEmail(String email);

}
