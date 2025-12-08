package com.myfin.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfin.admin.exception.AdminNotFoundException;
import com.myfin.admin.model.Admin;
import com.myfin.admin.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    public Admin register(Admin admin) {
        return repo.save(admin);
    }

    
    public List<Admin> getAllAdmins() {
        return repo.findAll();
    }


    public Admin login(String username, String password) {

        Admin admin = repo.findByUsername(username);

        if (admin == null) {
            throw new AdminNotFoundException("Admin username not found");
        }

        if (!admin.getPassword().equals(password)) {
            throw new AdminNotFoundException("Incorrect password");
        }

        return admin;
    }

}
