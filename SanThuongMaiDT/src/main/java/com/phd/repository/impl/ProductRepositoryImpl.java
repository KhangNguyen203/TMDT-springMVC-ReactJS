/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Product;
import com.phd.pojo.Store;
import com.phd.repository.ProductRepository;
import com.phd.repository.UserRepository;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Product> getProducts(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Product> q = b.createQuery(Product.class);
        Root root = q.from(Product.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("price"), Long.parseLong(fromPrice)));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.get("price"), Long.parseLong(toPrice)));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("categoryId"), Integer.parseInt(cateId)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }

        Query query = session.createQuery(q);
        List<Product> products = query.getResultList();
        Collections.shuffle(products);
        return products;
    }

    @Override
    public List<Product> getProductAsc(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Product> q = b.createQuery(Product.class);
        Root root = q.from(Product.class);
        q.select(root);

        if (params != null) {
            List<Predicate> predicates = new ArrayList<>();

            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(root.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(root.get("price"), Long.parseLong(fromPrice)));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(root.get("price"), Long.parseLong(toPrice)));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(root.get("categoryId"), Integer.parseInt(cateId)));
            }

            q.where(predicates.toArray(Predicate[]::new));
        }
        q.orderBy(b.asc(root.get("price")));

        Query query = session.createQuery(q);

        return query.getResultList();
    }

    @Override
    public int countProduct() {
        Session session = this.factory.getObject().getCurrentSession();
        Query q = session.createQuery("SELECT Count(*) FROM Product");
        return Integer.parseInt(q.getSingleResult().toString());
    }

    @Override
    public boolean addOrUpdateProduct(Product p) {
        Session s = this.factory.getObject().getCurrentSession();

        //lấy id store của user đang đăng nhập
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        Store store = this.userRepository.getUserByUsername(authentication.getName())
                .getStoreSet().iterator().next();
        try {
            if (p.getId() == null) {
                p.setStoreId(store);
                s.save(p);
            } else {
                s.update(p);
                p.setStoreId(store);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Product getProductById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Product.class, id);
    }

    @Override
    public boolean deleteProduct(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Product p = this.getProductById(id);
        try {
            s.delete(p);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public int countProductInStore(int storeId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("select Count(*) from Product where storeId.id=:id");
        q.setParameter("id", storeId);
        return Integer.parseInt(q.getSingleResult().toString());
    }
}
