package com.lambdaschool.sleepmood;

import com.lambdaschool.sleepmood.models.Role;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.models.UserRoles;
import com.lambdaschool.sleepmood.services.RoleService;
import com.lambdaschool.sleepmood.services.SleepDataService;
import com.lambdaschool.sleepmood.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

//@Transactional
//@Component
public class SeedData implements CommandLineRunner
{
    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    SleepDataService sleepDataService;

    public int randomNumber(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("data");

        roleService.save(r1);
        roleService.save(r2);
        roleService.save(r3);

        // SleepData
//        ArrayList<SleepData> sleepArrayU1 = new ArrayList<>();
//        for (int i = 1; i <= 29; i++)
//        {
//            LocalDateTime sleep = LocalDateTime.of(2019, 7, i, 20, 0, 0);
//            LocalDateTime wake = LocalDateTime.of(2019, 7, i+1, 6, 0, 0);
//
//            sleepArrayU1.add(new SleepData(sleep, wake, 2, 5, 3));
//        }
//        for (SleepData zz : sleepArrayU1)
//        {
//            sleepDataService.save(zz);
//        }

        // admin, data, user
        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
        admins.add(new UserRoles(new User(), r2));
        admins.add(new UserRoles(new User(), r3));
        User u1 = new User("Admi", "En", "ad@min.com", "password", admins);

        for (int i = 1; i <= 29; i++)
        {
            LocalDateTime sleep = LocalDateTime.of(2019, 7, i, randomNumber(20, 23), randomNumber(0, 53), 0);
            LocalDateTime wake = LocalDateTime.of(2019, 7, i+1, randomNumber(5, 9), randomNumber(5, 35), 0);

            u1.getSleepdata().add(new SleepData(u1, sleep, wake, randomNumber(1, 4), randomNumber(1, 4), randomNumber(1, 4)));
        }

        userService.save(u1);




        // data, user
        ArrayList<UserRoles> datas = new ArrayList<>();
        datas.add(new UserRoles(new User(), r3));
        datas.add(new UserRoles(new User(), r2));
        User u2 = new User("Shinna", "Mon", "c@min.com", "1234567", datas);
        userService.save(u2);

        // user
        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u3 = new User("Barn", "Barn", "barn@barn.com", "ILuvM4th!", users);
        userService.save(u3);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u4 = new User("Bob", "Whitman", "b@min.com", "password", users);
        userService.save(u4);

        users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));
        User u5 = new User("Jane", "Doe", "j@doe.com", "password", users);
        userService.save(u5);


    }
}