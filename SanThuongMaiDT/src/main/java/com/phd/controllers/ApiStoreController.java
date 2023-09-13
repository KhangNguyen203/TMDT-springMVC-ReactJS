/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Review;
import com.phd.pojo.Store;
import com.phd.service.ProductService;
import com.phd.service.ReviewService;
import com.phd.service.StoreService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dat98
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiStoreController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductService productService;

    @GetMapping(path = "/store-info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> infoStore(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.storeService.getApiInfoStore(id), HttpStatus.OK);
    }

//    @GetMapping("/store/{id}")
//    public ResponseEntity<?> store(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
//        return new ResponseEntity<>(this.storeService.getProdFromStore(id, params), HttpStatus.OK);
//    }
    
    @GetMapping("/store-product-desc/{id}")
    public ResponseEntity<?> storeProductDesc(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.storeService.getProdFromStoreDesc(id, params), HttpStatus.OK);
    }

    @GetMapping("/store-product-asc/{id}")
    public ResponseEntity<?> storeProductAsc(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.storeService.getProdFromStoreAsc(id, params), HttpStatus.OK);
    }

    @PostMapping(path = "/create-store/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        return new ResponseEntity<>(this.storeService.createStore(store), HttpStatus.CREATED);
    }

    @GetMapping("/store/{id}/reviews/")
    public ResponseEntity<List<Review>> listComments(@RequestParam Map<String, String> params, @PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.reviewService.getReviews(id, params), HttpStatus.OK);
    }

    @PostMapping(path = "/reviews/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Review> addComment(@RequestBody Review Review) {
        Review c = this.reviewService.addReview(Review);

        return new ResponseEntity<>(c, HttpStatus.CREATED);
    }

    @GetMapping("/store/{id}/avgStarReview/")
    public ResponseEntity<Double> getAverageStarReview(@PathVariable(value = "id") int id) {
        double averageStarReview = this.reviewService.avgStarReview(id);
        return new ResponseEntity<>(averageStarReview, HttpStatus.OK);
    }

    @GetMapping("/store/{id}/countProducts/")
    public ResponseEntity<Integer> countProductsInStore(@PathVariable(value = "id") int id) {
        int productCount = this.productService.countProductInStore(id);
        return new ResponseEntity<>(productCount, HttpStatus.OK);
    }

}
