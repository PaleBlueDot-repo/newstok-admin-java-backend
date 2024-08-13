package com.NewsTok.Admin.Repositories;

import com.NewsTok.Admin.Models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    public Admin findByEmail(String email);
}



