/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Category;
import com.phd.pojo.Product;
import com.phd.service.AdminService;
import com.phd.service.CategoryService;
import com.phd.service.StatsService;
import com.phd.service.UserService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dat98
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CategoryService cateService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatsService statsService;

    @GetMapping("/categories")
    public String categoryView(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("categories", this.cateService.getCates());
        return "categories";
    }

    @GetMapping("/add-categories")
    public String list(Model model) {
        model.addAttribute("categories", new Category());
        return "add-categories";
    }

    @GetMapping("/add-categories/{id}")
    public String update(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("categories", this.adminService.getCategoryById(id));
        return "add-categories";
    }

    @PostMapping("/add-categories")
    public String add(@ModelAttribute(value = "categories") @Valid Category c, BindingResult rs) {
        if (!rs.hasErrors()) {
            if (this.adminService.addOrUpdateCate(c) == true) {
                return "redirect:/admin/categories";
            }
        }

        return "add-categories";
    }

    @GetMapping("/users")
    public String userView(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("users", this.userService.getUser());
        return "users";
    }

    @GetMapping("/stats-admin")
    public String statsAdmin(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("statsRevenueByStoreAdmin", this.statsService.statsRevenueByStoreAdmin(params));
        return "stats-admin";
    }

}
