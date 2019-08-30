package com.lambdaschool.sleepmood.services;

import com.lambdaschool.sleepmood.exceptions.BadRequestException;
import com.lambdaschool.sleepmood.exceptions.ResourceNotFoundException;
import com.lambdaschool.sleepmood.models.SleepData;
import com.lambdaschool.sleepmood.models.User;
import com.lambdaschool.sleepmood.models.UserRoles;
import com.lambdaschool.sleepmood.repository.RoleRepository;
import com.lambdaschool.sleepmood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService
{

    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByEmail(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthority());
    }

    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public User findUserByEmail(String email)
    {
        User currentUser = userrepos.findByEmail(email);

        if (currentUser != null)
        {
            return currentUser;
        }
        else
        {
            throw new ResourceNotFoundException(email);
        }
    }

    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent())
        {
            userrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user) throws BadRequestException
    {
        if (userrepos.findByEmail(user.getEmail()) != null)
        {
            throw new BadRequestException("Username is already taken");
        }

        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);

        for (SleepData sd : user.getSleepdata())
        {
            newUser.getSleepdata().add(new SleepData(newUser,
                                                     sd.getSleepdate(),
                                                     sd.getWakedate(),
                                                     sd.getSleepmood(),
                                                     sd.getWakemood(),
                                                     sd.getDaymood())
                                                    );
        }

        return userrepos.save(newUser);
    }


    @Transactional
    @Override
    public User update(User user, long id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userrepos.findByEmail(authentication.getName());

        if (currentUser != null)
        {
            if (id == currentUser.getUserid())
            {
                if (user.getFirstname() != null)
                {
                    currentUser.setFirstname(user.getFirstname());
                }

                if (user.getLastname() != null)
                {
                    currentUser.setLastname(user.getLastname());
                }

                if (user.getEmail() != null)
                {
                    currentUser.setEmail(user.getEmail());
                }

                if (user.getPassword() != null)
                {
                    currentUser.setPasswordNoEncrypt(user.getPassword());
                }

                if (user.getUserRoles().size() > 0)
                {
                    // with so many relationships happening, I decided to go
                    // with old school queries
                    // delete the old ones
                    rolerepos.deleteUserRolesByUserId(currentUser.getUserid());

                    // add the new ones
                    for (UserRoles ur : user.getUserRoles())
                    {
                        rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
                    }
                }

                return userrepos.save(currentUser);
            } else
            {
                throw new ResourceNotFoundException(id + " Not current user");
            }
        } else
        {
            throw new ResourceNotFoundException(authentication.getName());
        }

    }
}
