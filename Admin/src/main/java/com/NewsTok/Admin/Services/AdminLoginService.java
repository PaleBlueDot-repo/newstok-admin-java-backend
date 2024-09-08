package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.Admin;
import com.NewsTok.Admin.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService implements UserDetailsService {

    @Autowired
    private AdminRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin appUser = repo.findByEmail(email);

        if (appUser != null) {
            UserDetails springUser= User.withUsername(appUser.getEmail())
                    .password(appUser.getPassword())
                    .build();
            return springUser;

        }

        return null;
    }



}
