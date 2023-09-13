/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Cart;
import com.phd.pojo.OrderDetails;
import com.phd.pojo.Orders;
import com.phd.repository.ProductRepository;
import com.phd.repository.ReceiptRepository;
import com.phd.repository.UserRepository;
import java.util.Date;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProductRepository productRepositoy;
    
    @Autowired
    private LocalSessionFactoryBean factory;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addReceipt(Map<String, Cart> carts) {
        Session s = this.factory.getObject().getCurrentSession();
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        
        try {
            Orders order = new Orders();
            order.setUserId(this.userRepository.getUserByUsername(authentication.getName()));
            order.setOrderDate(new Date());
            s.save(order);
            
            for (Cart c : carts.values()) {
                OrderDetails d = new OrderDetails();
                d.setUnitPrice(c.getUnitPrice());
                d.setQuantity(c.getQuantity());
                d.setOrderId(order);
                d.setProductId(this.productRepositoy.getProductById(c.getId()));
                s.save(d);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
