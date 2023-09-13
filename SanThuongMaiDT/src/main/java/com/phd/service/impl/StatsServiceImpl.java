/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.phd.service.impl;

import com.phd.repository.StatsRepository;
import com.phd.service.StatsService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dat98
 */
@Service
public class StatsServiceImpl implements StatsService{

    @Autowired
    private StatsRepository statsRepository;
    
    @Override
    public List<Object[]> statsByProduct(Map<String, String> params) {
        return this.statsRepository.statsByProduct(params);
    }

    @Override
    public List<Object[]> statsByCate(Map<String, String> params) {
        return this.statsRepository.statsByCate(params);
    }

    @Override
    public List<Object[]> statsNumberProductByCate() {
        return this.statsRepository.statsNumberProductByCate();
    }

    @Override
    public List<Object[]> statsRevenueInEachStore() {
        return this.statsRepository.statsRevenueInEachStore();
    }

    @Override
    public List<Object[]> statsByMonthInStore() {
        return this.statsRepository.statsByMonthInStore();
    }

    @Override
    public List<Object[]> statsRevenueByStore() {
        return this.statsRepository.statsRevenueByStore();
    }

    @Override
    public List<Object[]> statsRevenueByStoreAdmin(Map<String, String> params) {
        return this.statsRepository.statsRevenueByStoreAdmin(params);
    }

    @Override
    public List<Object[]> statsProductByCate() {
        return this.statsRepository.statsProductByCate();
    }
    
}
