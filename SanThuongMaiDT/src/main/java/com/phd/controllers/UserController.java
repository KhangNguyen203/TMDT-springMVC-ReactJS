/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Store;
import com.phd.pojo.User;
import com.phd.service.StoreService;
import com.phd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author dat98
 */
@Controller
public class UserController {

    @Autowired
    private UserService userDetailsServer;
    @Autowired
    private StoreService storeService;

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }

    @GetMapping("/register")
    public String registerView(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute(value = "user") User user) {
        String errMsg = "";
        if (user.getPassword().equals(user.getConfirmPassword())) {
            if (this.userDetailsServer.addUser(user) == true) {
                return "redirect:/login";
            } else {
                errMsg = "loi1";
            }
        } else {
            errMsg = "Mật khẩu không khớp";
        }

        model.addAttribute("errMsg", errMsg);
        return "register";
    }

    //đăng ký cửa hàng
    @GetMapping("/register-store")
    public String registerStoreView(Model model) {
        model.addAttribute("store", new Store());
        return "register-store";
    }

    @PostMapping("/register-store")
    public String registerStore(Model model, @ModelAttribute(value = "store") Store store) {
        if (this.storeService.addStore(store) == true) {
            return "redirect:/";
        }
        return "register-store";
    }
}
