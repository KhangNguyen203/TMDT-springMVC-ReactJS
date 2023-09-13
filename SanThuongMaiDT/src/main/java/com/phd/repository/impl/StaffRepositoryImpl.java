/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Store;
import com.phd.pojo.User;
import com.phd.repository.StaffRepository;
import com.phd.repository.StoreRepository;
import com.phd.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Repository
@Transactional
public class StaffRepositoryImpl implements StaffRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public boolean confirmStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();
        
        try {
            if (store.getId() != null) {
                s.update(store);
            }
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Store> getStorePending() {
        Session s = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = s.getCriteriaBuilder();
        CriteriaQuery<Store> q = b.createQuery(Store.class);
        Root store = q.from(Store.class);
        List<Predicate> predicates = new ArrayList<>();

        q.select(store);
        predicates.add(b.like(store.get("status"), "%pending%"));

        q.where(predicates.toArray(Predicate[]::new));
        Query query = s.createQuery(q);
        return query.getResultList();

    }

}
