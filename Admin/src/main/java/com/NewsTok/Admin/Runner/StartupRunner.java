package com.NewsTok.Admin.Runner;

import com.NewsTok.Admin.Models.Admin;
import com.NewsTok.Admin.Repositories.AdminRepository;
import com.NewsTok.Admin.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class StartupRunner implements CommandLineRunner {

    @Value("${UserToAdminAuthentication.email}")
    private String userEmail;
    @Value("${UserToAdminAuthentication.password}")
    private String userPassword;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private JwtService jwtService;




    @Override
    public void run(String... args) throws Exception {
        Admin appUser = new Admin();

        appUser.setEmail(userEmail);
        appUser.setCreatedAt(new Date());
        var bcryptEncoder = new BCryptPasswordEncoder();
        appUser.setPassword(bcryptEncoder.encode(userPassword));

        try {

            var otherUser = adminRepository.findByEmail(userEmail);
            if (otherUser != null) {
                System.out.println("already exist\n");
            }
            else {

                adminRepository.save(appUser);
                String JwtToken = jwtService.createToken(appUser);
                var response = new HashMap<String, Object>();
                response.put("token", JwtToken);
                response.put("user", appUser);
                System.out.println("User account created\n");
            }

        }
        catch (Exception ex) {
            System.out.println("There is an Exception: "+ ex.getMessage());
        }

    }


}
