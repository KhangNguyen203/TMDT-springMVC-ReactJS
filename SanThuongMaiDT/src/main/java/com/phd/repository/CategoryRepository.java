/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.repository;

import com.phd.pojo.Category;
import java.util.List;

/**
 *
 * @author dat98
 */
public interface CategoryRepository {
    List<Category> getCates();
    int countCategory();
}
