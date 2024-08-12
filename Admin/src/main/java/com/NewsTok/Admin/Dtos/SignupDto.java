package com.NewsTok.Admin.Dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignupDto {

    @NotEmpty
    @Email
    private String email;

    private String name;

    @NotEmpty
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;

    public @NotEmpty @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotEmpty @Size(min = 6, message = "Minimum Password length is 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @Size(min = 6, message = "Minimum Password length is 6 characters") String password) {
        this.password = password;
    }
}
