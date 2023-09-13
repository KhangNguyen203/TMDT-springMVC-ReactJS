/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Store;
import com.phd.service.StaffService;
import com.phd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dat98
 */
@Controller
@RequestMapping("/staff")

public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StoreService storeService;

    @GetMapping("")
    public String staff(Model model) {
        model.addAttribute("pending", this.staffService.getStorePending());
        return "staff";
    }

    @PostMapping("")
    public String confirm(Model model, @ModelAttribute(value = "store") Store store) {

        if (this.staffService.confirmStore(store) == true) {
            return "redirect:/staff";
        }
        
        return "staff";
    }

    @GetMapping("/confirm/{id}")
    public String confirm(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("store", this.storeService.getStoreById(id));

        return "confirm";
    }
}
