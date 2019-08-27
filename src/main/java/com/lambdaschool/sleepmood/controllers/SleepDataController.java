package com.lambdaschool.sleepmood.controllers;

import com.lambdaschool.sleepmood.exceptions.ResourceNotFoundException;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.services.SleepDataService;
import com.lambdaschool.sleepmood.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sleep")
public class SleepDataController
{

    long findLoggedInUser(Authentication auth) throws ResourceNotFoundException
    {
        User user = userService.findUserByName(auth.getName());

        if (user == null)
        {
            throw new ResourceNotFoundException("No User logged in");
        }
        return user.getUserid();
    }

    private static final Logger logger = LoggerFactory.getLogger(SleepDataController.class);

    @Autowired
    SleepDataService sleepDataService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<?> getAll(HttpServletRequest request, @PageableDefault(size = 30) Pageable pageable, Authentication authentication)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(authentication);

        List<SleepData> rtnList = sleepDataService.getAll(pageable, userid);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/month/{yearid}/{monthid}", produces = {"application/json"})
    public ResponseEntity<?> getByMonth(HttpServletRequest request, @PathVariable int yearid, @PathVariable int monthid, Authentication auth)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(auth);

//        List<SleepData> rtnList = sleepDataService.getAll(userid);
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping(value = "/id/{id}", produces = {"application/json"})
    public ResponseEntity<?> getSingleById(HttpServletRequest request, @PathVariable long id, Authentication auth)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(auth);

        SleepData rtnSleepData = sleepDataService.getSingle(id, userid);
        return new ResponseEntity<>(rtnSleepData, HttpStatus.OK);
    }

    @PostMapping(value = "/new", consumes = {"application/json"})
    public ResponseEntity<?> addNewSleepData(HttpServletRequest request, @Valid @RequestBody SleepData sleepData, Authentication auth)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(auth);

        sleepData = sleepDataService.save(sleepData, userid);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newInstructorURI = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/sleep/{id}").buildAndExpand(sleepData.getId()).toUri();
        responseHeaders.setLocation(newInstructorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}", consumes = {"application/json"})
    public ResponseEntity<?> updateSleepData(HttpServletRequest request, @Valid @RequestBody SleepData sleepData, @PathVariable long id, Authentication auth)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(auth);

        sleepDataService.update(sleepData, id, userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteSleepData(HttpServletRequest request, @PathVariable long id, Authentication auth)
    {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");
        long userid = findLoggedInUser(auth);

        sleepDataService.delete(id, userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
