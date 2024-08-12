package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.loginDto;
import com.NewsTok.Admin.Models.Admin;
import com.NewsTok.Admin.Repositories.AdminRepository;
import com.NewsTok.Admin.Services.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/admin")
public class LoginController {
   @Autowired
   private AdminRepository adminRepository;

   @Autowired
   private JwtService jwtService;

   @Autowired
   private AuthenticationManager authenticationManager;

   @PostMapping("/login")
   public ResponseEntity<Object> login(
           @Valid @RequestBody loginDto loginDto
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

      try {
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         loginDto.getEmail(),
                         loginDto.getPassword()
                 )
         );

         Admin appUser = adminRepository.findByEmail(loginDto.getEmail());

         String jwtToken = jwtService.createToken(appUser);

         var response = new HashMap<String, Object>();
         response.put("token", jwtToken);
         response.put("user", appUser);

         return ResponseEntity.ok(response);

      } catch (Exception ex) {
         System.out.println("There is an Exception:");
         ex.printStackTrace();
      }

      return  ResponseEntity.badRequest().body("Bad username or password");
   }


}