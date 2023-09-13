/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.repository;

import com.phd.pojo.Store;
import java.util.List;

/**
 *
 * @author dat98
 */
public interface StaffRepository {
    boolean confirmStore(Store store);
    List<Store> getStorePending();
}
