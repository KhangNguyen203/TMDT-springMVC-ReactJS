/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.repository;

import com.phd.pojo.Category;
import com.phd.pojo.User;

/**
 *
 * @author dat98
 */
public interface AdminRepository {

    boolean addOrUpdateCate(Category Category);

    Category getCategoryById(int id);

    boolean deleteCategory(int id);

    boolean deleteUser(int id);

    User getUserById(int id);
}
