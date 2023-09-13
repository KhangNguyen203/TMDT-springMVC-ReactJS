/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.phd.service;

import com.phd.pojo.Store;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dat98
 */
public interface StoreService {

    List<Object[]> getProdFromStore(int id, Map<String, String> params);

    Store getStoreById(int id);

    boolean addStore(Store store);

    List<Object[]> getProductByStoreId(Map<String, String> params);

    List<Object[]> getCateByStoreId();

    List<Object[]> getApiInfoStore(int id);

    int countProductByStore();

    List<Object[]> getApiCateByStoreId(int id);

    Store createStore(Store store);

    List<Object[]> getProdFromStoreAsc(int id, Map<String, String> params);

    List<Object[]> getProdFromStoreDesc(int id, Map<String, String> params);

    Store getStoreByUser();

    boolean updateStore(Store store);

}
