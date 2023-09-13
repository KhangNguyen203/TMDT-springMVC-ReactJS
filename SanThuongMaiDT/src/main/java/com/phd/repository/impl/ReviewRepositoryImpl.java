/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Product;
import com.phd.pojo.Review;
import com.phd.repository.ReviewRepository;
import java.text.DecimalFormat;
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
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dat98
 */
@Repository
@Transactional
public class ReviewRepositoryImpl implements ReviewRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Review> getReviews(int storeId, Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();

        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Review> q = b.createQuery(Review.class);
        Root root = q.from(Review.class);
        q.select(root);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(root.get("storeId"), storeId));

        if (params != null) {
            String star = params.get("star");
            if (star != null && !star.isEmpty()) {
                predicates.add(b.equal(root.get("star"), Integer.parseInt(star)));
            }
        }

        q.where(predicates.toArray(Predicate[]::new));

        q.orderBy(b.desc(root.get("id")));
        Query query = session.createQuery(q);

        return query.getResultList();
    }

    @Override
    public Review addReview(Review r) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.save(r);
            return r;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public double avgStarReview(int storeId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("select avg(CASE WHEN r.star <> 0 THEN r.star ELSE null END) from Review r where r.storeId.id=:id");
        q.setParameter("id", storeId);

        Double average = (Double) q.getSingleResult();
        if (average != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            return Double.parseDouble(df.format(average));
        } else {
            return 0.0;
        }
    }

}
