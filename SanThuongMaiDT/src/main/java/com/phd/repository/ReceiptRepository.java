/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.repository;

import com.phd.pojo.Cart;
import java.util.Map;

/**
 *
 * @author dat98
 */
public interface ReceiptRepository {

    boolean addReceipt(Map<String, Cart> carts);
    
}
