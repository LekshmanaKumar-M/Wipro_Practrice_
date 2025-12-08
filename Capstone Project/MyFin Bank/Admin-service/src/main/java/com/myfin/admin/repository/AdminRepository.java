package com.myfin.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.myfin.admin.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
