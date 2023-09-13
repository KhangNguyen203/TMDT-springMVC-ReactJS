/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Category;
import com.phd.pojo.Product;
import com.phd.pojo.Store;
import com.phd.pojo.User;
import com.phd.repository.StoreRepository;
import com.phd.repository.UserRepository;
import java.util.ArrayList;
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

/**
 *
 * @author dat98
 */
@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class StoreRepositoryImpl implements StoreRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;

    @Override
    public List<Object[]> getProductByStoreId(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        Root rP = q.from(Product.class);
        Root rStr = q.from(Store.class);

        if (params != null) {
            predicates.add(b.equal(rStr.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
            predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(rP.get("categoryId"), Integer.parseInt(cateId)));
            }
        }
        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rP.get("id"), rP.get("image"), rP.get("name"), rP.get("price"));

        Query query = session.createQuery(q);

        if (params != null) {
            String page = params.get("page");
            if (page != null && !page.isEmpty()) {
                int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
                query.setMaxResults(pageSize);
                query.setFirstResult((Integer.parseInt(page) - 1) * pageSize);
            }
        }

        return query.getResultList();
    }

    @Override
    public List<Object[]> getCateByStoreId() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        Root rP = q.from(Product.class);
        Root rStr = q.from(Store.class);
        Root rC = q.from(Category.class);

        predicates.add(b.equal(rStr.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        predicates.add(b.equal(rP.get("categoryId"), rC.get("id")));

        q.where(predicates.toArray(Predicate[]::new));
        q.groupBy(rP.get("categoryId"));
        q.multiselect(rC.get("id"), rC.get("name"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public int countProductByStore() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Long> q = b.createQuery(Long.class);

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        Root rP = q.from(Product.class);
        Root rStr = q.from(Store.class);

        predicates.add(b.equal(rStr.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        q.where(predicates.toArray(Predicate[]::new));
        q.select(b.count(rP.get("id")));
        return Integer.parseInt(session.createQuery(q).getSingleResult().toString());

    }

    @Override
    public boolean addStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();

        try {
            s.save(store);
            return true;
        } catch (HibernateException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();
        s.update(store);
        return true;
    }

    @Override
    public Store createStore(Store store) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(store);
        return store;
    }

    @Override
    public Store getStoreById(int id) {
        Session s = this.factory.getObject().getCurrentSession();

        return s.get(Store.class,
                 id);
    }

    @Override
    public Store getStoreByUser() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Store> q = b.createQuery(Store.class
        );
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        Root rStr = q.from(Store.class
        );

        predicates.add(b.equal(rStr.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));

        q.where(predicates.toArray(Predicate[]::new));

        Query query = session.createQuery(q);
        List<Store> stores = query.getResultList();
        if (!stores.isEmpty()) {
            return stores.get(0);
        } else {
            return null;
        }
    }

    //------------Api---------------------
    @Override
    public List<Object[]> getApiCateByStoreId(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class
        );

        List<Predicate> predicates = new ArrayList<>();
        Root rP = q.from(Product.class
        );
        Root rStr = q.from(Store.class
        );
        Root rCate = q.from(Category.class
        );

        predicates.add(b.equal(rStr.get("id"), id));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        predicates.add(b.equal(rCate.get("id"), rP.get("categoryId")));

        q.where(predicates.toArray(Predicate[]::new));
        q.groupBy(rP.get("categoryId"));
        q.multiselect(rCate.get("id"), rCate.get("name"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getApiInfoStore(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class
        );
        Root rStr = q.from(Store.class
        );
        Root rU = q.from(User.class
        );

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStr.get("id"), id));
        predicates.add(b.equal(rStr.get("userId"), rU.get("id")));

        q.where(predicates.toArray(Predicate[]::new));
        q.multiselect(rStr.get("name"), rU.get("avatar"), rStr.get("description"), rStr.get("location"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    @Override
    public List<Object[]> getProdFromStore(int id, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class
        );
        Root rP = q.from(Product.class
        );
        Root rStr = q.from(Store.class
        );
        Root rCate = q.from(Category.class
        );
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStr.get("id"), id));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        predicates.add(b.equal(rCate.get("id"), rP.get("categoryId")));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(rP.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(rP.get("price"), Long.parseLong(fromPrice)));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(rP.get("price"), Long.parseLong(toPrice)));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(rP.get("categoryId"), Integer.parseInt(cateId)));
            }
        }
        q.where(predicates.toArray(Predicate[]::new));
        q.multiselect(rP.get("id"), rP.get("image"), rP.get("name"), rP.get("price"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //sắp xếp giá tăng dần
    @Override
    public List<Object[]> getProdFromStoreAsc(int id, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class
        );
        Root rP = q.from(Product.class
        );
        Root rStr = q.from(Store.class
        );
        Root rCate = q.from(Category.class
        );
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStr.get("id"), id));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        predicates.add(b.equal(rCate.get("id"), rP.get("categoryId")));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(rP.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(rP.get("price"), Long.parseLong(fromPrice)));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(rP.get("price"), Long.parseLong(toPrice)));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(rP.get("categoryId"), Integer.parseInt(cateId)));
            }
        }

        q.orderBy(b.asc(rP.get("price")));

        q.where(predicates.toArray(Predicate[]::new));
        q.multiselect(rP.get("id"), rP.get("image"), rP.get("name"), rP.get("price"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //giá giảm dần
    @Override
    public List<Object[]> getProdFromStoreDesc(int id, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class
        );
        Root rP = q.from(Product.class
        );
        Root rStr = q.from(Store.class
        );
        Root rCate = q.from(Category.class
        );
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStr.get("id"), id));
        predicates.add(b.equal(rP.get("storeId"), rStr.get("id")));
        predicates.add(b.equal(rCate.get("id"), rP.get("categoryId")));

        if (params != null) {
            String kw = params.get("kw");
            if (kw != null && !kw.isEmpty()) {
                predicates.add(b.like(rP.get("name"), String.format("%%%s%%", kw)));
            }

            String fromPrice = params.get("fromPrice");
            if (fromPrice != null && !fromPrice.isEmpty()) {
                predicates.add(b.greaterThanOrEqualTo(rP.get("price"), Long.parseLong(fromPrice)));
            }

            String toPrice = params.get("toPrice");
            if (toPrice != null && !toPrice.isEmpty()) {
                predicates.add(b.lessThanOrEqualTo(rP.get("price"), Long.parseLong(toPrice)));
            }

            String cateId = params.get("cateId");
            if (cateId != null && !cateId.isEmpty()) {
                predicates.add(b.equal(rP.get("categoryId"), Integer.parseInt(cateId)));
            }
        }
        q.orderBy(b.desc(rP.get("price")));

        q.where(predicates.toArray(Predicate[]::new));
        q.multiselect(rP.get("id"), rP.get("image"), rP.get("name"), rP.get("price"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

}
