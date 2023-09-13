/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.service;

import com.phd.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dat98
 */
public interface UserService extends UserDetailsService {

    User getUserByUn(String username);

    boolean authUser(String username, String password);

    boolean addUser(User user);

    List<User> getUser();

    User addUser1(Map<String, String> params, MultipartFile avatar);

    User getUserByMail(String mail);

    User getUserByUsername(String username);

    boolean existsByUsername(String username);
}
