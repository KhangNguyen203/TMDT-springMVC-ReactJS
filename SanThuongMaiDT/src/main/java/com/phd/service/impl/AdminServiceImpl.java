/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.service.impl;

import com.phd.pojo.Category;
import com.phd.pojo.User;
import com.phd.repository.AdminRepository;
import com.phd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public boolean addOrUpdateCate(Category Category) {
        return this.adminRepository.addOrUpdateCate(Category);
    }

    @Override
    public Category getCategoryById(int id) {
        return this.adminRepository.getCategoryById(id);
    }

    @Override
    public boolean deleteCategory(int id) {
        return this.adminRepository.deleteCategory(id);
    }

    @Override
    public boolean deleteUser(int id) {
        return this.adminRepository.deleteUser(id);
    }

    @Override
    public User getUserById(int id) {
        return this.adminRepository.getUserById(id);
    }

  
}
