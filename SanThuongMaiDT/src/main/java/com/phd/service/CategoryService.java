/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.service;

import com.phd.pojo.Category;
import java.util.List;

/**
 *
 * @author dat98
 */
public interface CategoryService {
    List<Category> getCates();
    int countCategory();
}
