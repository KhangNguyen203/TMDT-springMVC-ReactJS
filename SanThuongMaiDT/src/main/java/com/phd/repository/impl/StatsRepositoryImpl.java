/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository.impl;

import com.phd.pojo.Category;
import com.phd.pojo.OrderDetails;
import com.phd.pojo.Orders;
import com.phd.pojo.Product;
import com.phd.pojo.Store;
import com.phd.repository.StatsRepository;
import com.phd.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    public SimpleDateFormat f;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Object[]> countProductByCate() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rP = q.from(Product.class);
        Root rC = q.from(Category.class);

        q.multiselect(rC.get("id"), rC.get("name"), b.count(rP.get("id")));

        q.where(b.equal(rC.get("id"), rP.get("categoryId")));
        q.groupBy(rC.get("id"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //thống kê doanh thu các sản phẩm
    @Override
    public List<Object[]> statsByProduct(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rProduct = q.from(Product.class);
        Root rStote = q.from(Store.class);
        Root rOrder = q.from(Orders.class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStote.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rOrderDetail.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rStote.get("id"), rProduct.get("storeId")));
        predicates.add(b.equal(rOrderDetail.get("orderId"), rOrder.get("id")));

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("year");
            if (year != null && !year.isEmpty()) {
                predicates.add(b.equal(b.function("YEAR", Integer.class, rOrder.get("orderDate")), Integer.parseInt(year)));
                predicates.add(b.equal(b.function("QUARTER", Integer.class, rOrder.get("orderDate")), Integer.parseInt(quarter)));
            }
        }

        String month = params.get("month");
        if (month != null && !month.isEmpty()) {
            predicates.add(b.equal(b.function("MONTH", Integer.class, rOrder.get("orderDate")), Integer.parseInt(month)));
        }

        String prodName = params.get("prodName");
        if (prodName != null && !prodName.isEmpty()) {
            predicates.add(b.like(rProduct.get("name"), "%" + prodName + "%"));
        }
        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rProduct.get("id"), rProduct.get("name"), b.sum(b.prod(rOrderDetail.get("unitPrice"), rOrderDetail.get("quantity"))));

        q.groupBy(rOrderDetail.get("productId"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //thống kê doanh thu các sản phẩm
    @Override
    public List<Object[]> statsByCate(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rProduct = q.from(Product.class);
        Root rStote = q.from(Store.class);
        Root rOrder = q.from(Orders.class);
        Root rC = q.from(Category.class);

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStote.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rOrderDetail.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rStote.get("id"), rProduct.get("storeId")));
        predicates.add(b.equal(rOrderDetail.get("orderId"), rOrder.get("id")));
        predicates.add(b.equal(rC.get("id"), rProduct.get("categoryId")));

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("year");
            if (year != null && !year.isEmpty()) {
                predicates.add(b.equal(b.function("YEAR", Integer.class, rOrder.get("orderDate")), Integer.parseInt(year)));
                predicates.add(b.equal(b.function("QUARTER", Integer.class, rOrder.get("orderDate")), Integer.parseInt(quarter)));
            }
        }

        String month = params.get("month");
        if (month != null && !month.isEmpty()) {
            predicates.add(b.equal(b.function("MONTH", Integer.class, rOrder.get("orderDate")), Integer.parseInt(month)));
        }

        String cateName = params.get("cateName");
        if (cateName != null && !cateName.isEmpty()) {
            predicates.add(b.like(rProduct.get("categoryId").get("name"), "%" + cateName + "%"));
        }

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rC.get("id"), rC.get("name"), b.sum(b.prod(rOrderDetail.get("unitPrice"), rOrderDetail.get("quantity"))));

        q.groupBy(rC.get("name"));
        q.groupBy(rC.get("id"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //thống kê theo tháng của cửa hàng
    @Override
    public List<Object[]> statsByMonthInStore() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rProduct = q.from(Product.class);
        Root rStote = q.from(Store.class);
        Root rOrder = q.from(Orders.class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStote.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rOrderDetail.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rStote.get("id"), rProduct.get("storeId")));
        predicates.add(b.equal(rOrderDetail.get("orderId"), rOrder.get("id")));

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(b.function("MONTH", Integer.class, rOrder.get("orderDate")), b.sum(b.prod(rOrderDetail.get("unitPrice"), rOrderDetail.get("quantity"))));

        q.groupBy(b.function("MONTH", Integer.class, rOrder.get("orderDate")));
        q.orderBy(b.asc(b.function("MONTH", Integer.class, rOrder.get("orderDate"))));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //store số sản phẩm của thể loại
    @Override
    public List<Object[]> statsNumberProductByCate() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rStr = q.from(Store.class);
        Root rProduct = q.from(Product.class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStr.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rStr.get("id"), rProduct.get("storeId")));
        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rProduct.get("categoryId").get("id"), rProduct.get("categoryId").get("name"), b.count(rProduct));
        q.groupBy(rProduct.get("categoryId"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

    //admin thống kê doanh thu mỗi cửa hàng
    @Override
    public List<Object[]> statsRevenueInEachStore() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rOrders = q.from(Orders.class);
        Root rProduct = q.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rOrders.get("id"), rOrderDetail.get("orderId")));
        predicates.add(b.equal(rProduct.get("id"), rOrderDetail.get("productId")));

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rProduct.get("storeId").get("name"), b.sum(b.prod(rOrderDetail.get("quantity"), rOrderDetail.get("unitPrice"))));
        q.groupBy(rProduct.get("storeId"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

//    thống kê tổng doanh thu cửa hàng
    @Override
    public List<Object[]> statsRevenueByStore() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rProduct = q.from(Product.class);
        Root rStote = q.from(Store.class);
        Root rOrder = q.from(Orders.class);
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStote.get("userId"), this.userRepository.getUserByUsername(authentication.getName()).getId()));
        predicates.add(b.equal(rStote.get("id"), rProduct.get("storeId")));
        predicates.add(b.equal(rOrderDetail.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rOrderDetail.get("orderId"), rOrder.get("id")));

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(b.sum(b.prod(rOrderDetail.get("unitPrice"), rOrderDetail.get("quantity"))), rStote.get("id"));
        q.groupBy(rProduct.get("storeId"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

//    thống kê doanh thu cửa hàng trong admin
    @Override
    public List<Object[]> statsRevenueByStoreAdmin(Map<String, String> params) {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rOrderDetail = q.from(OrderDetails.class);
        Root rProduct = q.from(Product.class);
        Root rStote = q.from(Store.class);
        Root rOrder = q.from(Orders.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rStote.get("id"), rProduct.get("storeId")));
        predicates.add(b.equal(rOrderDetail.get("productId"), rProduct.get("id")));
        predicates.add(b.equal(rOrderDetail.get("orderId"), rOrder.get("id")));

        String quarter = params.get("quarter");
        if (quarter != null && !quarter.isEmpty()) {
            String year = params.get("year");
            if (year != null && !year.isEmpty()) {
                predicates.add(b.equal(b.function("YEAR", Integer.class, rOrder.get("orderDate")), Integer.parseInt(year)));
                predicates.add(b.equal(b.function("QUARTER", Integer.class, rOrder.get("orderDate")), Integer.parseInt(quarter)));
            }
        }

        String month = params.get("month");
        if (month != null && !month.isEmpty()) {
            predicates.add(b.equal(b.function("MONTH", Integer.class, rOrder.get("orderDate")), Integer.parseInt(month)));
        }

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rStote.get("name"), b.sum(rOrderDetail.get("unitPrice")));
        q.groupBy(rStote.get("id"), rStote.get("name"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }

//    số lượng sản phẩm mỗi thể loại admin
    @Override
    public List<Object[]> statsProductByCate() {
        Session session = this.factory.getObject().getCurrentSession();
        CriteriaBuilder b = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> q = b.createQuery(Object[].class);
        Root rCategory = q.from(Category.class);
        Root rProduct = q.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(b.equal(rCategory.get("id"), rProduct.get("categoryId")));

        q.where(predicates.toArray(Predicate[]::new));

        q.multiselect(rCategory.get("name"), b.count(rProduct.get("categoryId")));
        q.groupBy(rProduct.get("categoryId"));

        Query query = session.createQuery(q);
        return query.getResultList();
    }
}
