/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Category;
import com.phd.pojo.Product;
import com.phd.pojo.User;
import com.phd.repository.AdminRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Repository
@Transactional

public class AdminRepositoryImpl implements AdminRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;

    @Override
    public boolean addOrUpdateCate(Category c) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (c.getId() == null) {
                s.save(c);
            } else {
                s.update(c);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Category getCategoryById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Category.class, id);
    }

    @Override
    public boolean deleteCategory(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Category c = this.getCategoryById(id);
        try {
            s.delete(c);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public boolean deleteUser(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        User u = this.getUserById(id);
        try {
            s.delete(u);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
