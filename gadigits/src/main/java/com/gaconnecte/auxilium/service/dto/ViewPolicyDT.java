/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaconnecte.auxilium.service.dto;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hannibaal
 */
public class ViewPolicyDT {
    private int draw = 1;
    private Long recordsTotal;
    private Long recordsFiltered;
    private List<ViewPolicyDTO> data = new LinkedList<>();

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<ViewPolicyDTO> getData() {
        return data;
    }

    public void setData(List<ViewPolicyDTO> data) {
        this.data = data;
    }

}
