/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Category;
import com.phd.pojo.User;
import com.phd.repository.UserRepository;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From User Where username=:un");
        q.setParameter("un", username);
        return (User) q.getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public boolean addUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(user);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(user);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public List<User> getUser() {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createQuery("From User");

        return q.getResultList();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);

        return this.passEncoder.matches(password, u.getPassword());
    }

    @Override
    public User addUser1(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(u);

        return u;
    }

    @Override
    public User getUserByMail(String mail) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", mail);
        List<User> u = q.getResultList();
        try {
            return u.get(0);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("SELECT COUNT(*) From User Where username=:un");
        q.setParameter("un", username);

        int count = Integer.parseInt(q.getSingleResult().toString());
        return count > 0;
    }
}
