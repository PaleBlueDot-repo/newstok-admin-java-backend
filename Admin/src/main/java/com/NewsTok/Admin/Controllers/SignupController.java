package com.NewsTok.Admin.Controllers;


import com.NewsTok.Admin.Dtos.SignupDto;
import com.NewsTok.Admin.Models.Admin;
import com.NewsTok.Admin.Repositories.AdminRepository;
import com.NewsTok.Admin.Services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/admin")

public class SignupController {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/testadmin")
    public String testadmin(){
        return "Hello I'm Admin";
    }

    @GetMapping("/testToken")
    public String testToken(){
        return "Hello I'm Testing Token";
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> register(
            @Valid @RequestBody SignupDto registerDto
            , BindingResult result){

        if (result.hasErrors()) {

            var errorsList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for (int i = 0; i < errorsList.size(); i++) {
                var error = (FieldError) errorsList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        Admin appUser = new Admin();
        appUser.setEmail(registerDto.getEmail());
        appUser.setCreatedAt(new Date());
        var bcryptEncoder = new BCryptPasswordEncoder();
        appUser.setPassword(bcryptEncoder.encode(registerDto.getPassword()));

        try {

            var otherUser = adminRepository.findByEmail(registerDto.getEmail());
            if (otherUser != null) {
                return ResponseEntity.badRequest().body("Email address already used");
            }

            adminRepository.save(appUser);
            String JwtToken=jwtService.createToken(appUser);
            var response=new HashMap<String,Object>();
            response.put("token",JwtToken);
            response.put("user",appUser);
            return  ResponseEntity.ok(response);
        }
        catch (Exception ex) {

            System.out.println("There is an Exception:");
            ex.printStackTrace();

        }



        return ResponseEntity.badRequest().body("Error");
    }



}
