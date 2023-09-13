/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Cart;
import com.phd.service.ReceiptService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dat98
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiReceiptController {
    @Autowired
    private ReceiptService receiptService;
    
    @PostMapping("/pay/")
    @ResponseStatus(HttpStatus.OK)
    public void addReceipt(@RequestBody Map<String, Cart> carts){
        this.receiptService.addReceipt(carts);
    }
}
