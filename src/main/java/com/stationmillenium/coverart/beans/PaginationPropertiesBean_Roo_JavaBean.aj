// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.stationmillenium.coverart.beans;

import com.stationmillenium.coverart.beans.PaginationPropertiesBean;
import java.util.List;
import java.util.Map;

privileged aspect PaginationPropertiesBean_Roo_JavaBean {
    
    public List<Integer> PaginationPropertiesBean.getPaginationOptions() {
        return this.paginationOptions;
    }
    
    public void PaginationPropertiesBean.setPaginationOptions(List<Integer> paginationOptions) {
        this.paginationOptions = paginationOptions;
    }
    
    public Map<PaginationList, Integer> PaginationPropertiesBean.getDefaultPagination() {
        return this.defaultPagination;
    }
    
    public void PaginationPropertiesBean.setDefaultPagination(Map<PaginationList, Integer> defaultPagination) {
        this.defaultPagination = defaultPagination;
    }
    
}
