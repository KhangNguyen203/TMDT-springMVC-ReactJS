/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.User;
import com.phd.repository.UserRepository;
import com.phd.service.CategoryService;
import com.phd.service.ProductService;
import com.phd.service.StatsService;
import com.phd.service.StoreService;
import java.security.Principal;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author dat98
 */
@Controller
@ControllerAdvice
@PropertySource("classpath:configs.properties")
public class IndexController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService cateService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private Environment env;
    @Autowired
    private StatsService statsService;

    @ModelAttribute
    public void commonAttr(Model model, Principal principal, @RequestParam Map<String, String> params) {
        if (principal != null) {
            String username = principal.getName();
            User user = userRepository.getUserByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("categoriesByStore", this.storeService.getCateByStoreId());
            model.addAttribute("categoriesSelect", this.cateService.getCates());

            int count = this.productService.countProduct();
            model.addAttribute("count", count);
            int countProduct = this.storeService.countProductByStore();
            model.addAttribute("countProduct", countProduct);
            model.addAttribute("countProductByCate", this.statsService.statsNumberProductByCate());
            model.addAttribute("countCategory", this.cateService.countCategory());
            model.addAttribute("statsByMonthInStore", this.statsService.statsByMonthInStore());
            model.addAttribute("statsRevenueByStore", this.statsService.statsRevenueByStore());
            model.addAttribute("statsRevenueInEachStore", this.statsService.statsRevenueInEachStore());
            model.addAttribute("statsProductByCate", this.statsService.statsProductByCate());
        }

    }

    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {

        model.addAttribute("products", this.productService.getProducts(params));

        return "index";
    }

}
