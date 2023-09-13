/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.service.impl;

import com.phd.pojo.Store;
import com.phd.pojo.User;
import com.phd.repository.StaffRepository;
import com.phd.repository.UserRepository;
import com.phd.service.StaffService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dat98
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Store> getStorePending() {
        return this.staffRepository.getStorePending();
    }

    @Override
    public boolean confirmStore(Store store) {

        User user = store.getUserId();
        user.setUserRole(User.setRoleStore());
       
        store.setStatus("confirm");
        this.userRepository.updateUser(user);
        return this.staffRepository.confirmStore(store);

    }

}
