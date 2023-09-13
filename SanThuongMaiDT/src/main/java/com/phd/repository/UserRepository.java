/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.repository;

import com.phd.pojo.User;
import java.util.List;

/**
 *
 * @author dat98
 */
public interface UserRepository {

    User getUserByUsername(String username);

    User getUserById(int id);

    boolean addUser(User user);

    boolean updateUser(User user);

    List<User> getUser();

    boolean authUser(String username, String password);

    User addUser1(User user);

    User getUserByMail(String mail);

    boolean existsByUsername(String username);
}
