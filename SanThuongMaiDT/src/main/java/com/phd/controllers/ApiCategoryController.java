/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Category;
import com.phd.service.AdminService;
import com.phd.service.CategoryService;
import com.phd.service.StoreService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dat98
 */
@RestController
@RequestMapping("/api")
public class ApiCategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private StoreService storeService;

    @GetMapping("/categories")
    @CrossOrigin

    public ResponseEntity<List<Category>> list() {
        return new ResponseEntity<>(this.categoryService.getCates(), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(value = "id") int id) {
        this.adminService.deleteCategory(id);
    }

    @GetMapping(path = "/store-categories/{id}")
    @CrossOrigin
    public ResponseEntity<?> listStoreCategories(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.storeService.getApiCateByStoreId(id), HttpStatus.OK);
    }
}
