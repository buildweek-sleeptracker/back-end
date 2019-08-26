package com.lambdaschool.sleepmood.controllers;

import com.lambdaschool.sleepmood.exceptions.ResourceNotFoundException;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.services.SleepDataService;
import com.lambdaschool.sleepmood.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sleep")
public class SleepDataController
{
    @Autowired
    SleepDataService sleepDataService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAll(@PageableDefault(size = 30) Pageable pageable, Authentication authentication) throws ResourceNotFoundException
    {
        User user = userService.findUserByName(authentication.getName());

        if (user == null)
        {
            throw new ResourceNotFoundException("No User logged in");
        }

        long userid = user.getUserid();
        System.out.println(userid);
        List<SleepData> rtnList = sleepDataService.getAll(pageable, userid);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }
}
