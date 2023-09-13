/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.controllers;

import com.phd.pojo.Product;
import com.phd.pojo.Store;
import com.phd.service.CategoryService;
import com.phd.service.ProductService;
import com.phd.service.StatsService;
import com.phd.service.StoreService;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/store")
@PropertySource("classpath:configs.properties")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StatsService statsService;
    
    @Autowired
    private Environment env;

    //trang san pham
    @GetMapping("/products")
    public String productView(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("storeProduct", this.storeService.getProductByStoreId(params));

        int pageSize = Integer.parseInt(this.env.getProperty("PAGE_SIZE"));
        int count = this.storeService.countProductByStore();
        model.addAttribute("counter", Math.ceil(count * 1.0 / pageSize));
        return "products";
    }

    //thêm san pham
    @GetMapping("/add-products")
    public String list(Model model) {
        model.addAttribute("product", new Product());

        return "add-products";
    }

    @PostMapping("/add-products")
    public String add(@ModelAttribute(value = "product") @Valid Product p,
            BindingResult rs) {
        if (!rs.hasErrors()) {
            if (this.productService.addOrUpdateProduct(p) == true) {
                return "redirect:/store/products";
            }
        }

        return "add-products";
    }

    @GetMapping("/add-products/{id}")
    public String update(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("product", this.productService.getProductById(id));
        return "add-products";
    }

    @GetMapping("/info-store")
    public String infoStore(Model model) {
        model.addAttribute("storeInfo", this.storeService.getStoreByUser());
        return "info-store";
    }

    @PostMapping("/info-store")
    public String updateStore(@ModelAttribute(value = "storeInfo") Store s) {
        System.out.println(this.storeService.updateStore(s));
        if (this.storeService.updateStore(s) == true) {
            return "redirect:/store/storeInfo";
        }

        return "info-store";
    }

    @GetMapping("/stats-product")
    public String statsProd(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("statsProduct", this.statsService.statsByProduct(params));

        return "stats-product";
    }

    @GetMapping("/stats-categories")
    public String statsCate(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("statsByCate", this.statsService.statsByCate(params));

        return "stats-categories";
    }
}
