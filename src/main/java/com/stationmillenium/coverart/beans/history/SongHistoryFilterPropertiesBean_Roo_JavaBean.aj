// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.stationmillenium.coverart.beans.history;

import com.stationmillenium.coverart.beans.history.SongHistoryFilterPropertiesBean;
import java.util.List;

privileged aspect SongHistoryFilterPropertiesBean_Roo_JavaBean {
    
    public List<String> SongHistoryFilterPropertiesBean.getForbiddenKeywords() {
        return this.forbiddenKeywords;
    }
    
    public void SongHistoryFilterPropertiesBean.setForbiddenKeywords(List<String> forbiddenKeywords) {
        this.forbiddenKeywords = forbiddenKeywords;
    }
    
    public int SongHistoryFilterPropertiesBean.getMinimalLength() {
        return this.minimalLength;
    }
    
    public void SongHistoryFilterPropertiesBean.setMinimalLength(int minimalLength) {
        this.minimalLength = minimalLength;
    }
    
}