// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.stationmillenium.coverart.domain.alert;

import com.stationmillenium.coverart.domain.alert.AlertActivation;
import com.stationmillenium.coverart.domain.alert.AlertType;

privileged aspect AlertActivation_Roo_JavaBean {
    
    public boolean AlertActivation.isEnableAlert() {
        return this.enableAlert;
    }
    
    public void AlertActivation.setEnableAlert(boolean enableAlert) {
        this.enableAlert = enableAlert;
    }
    
    public AlertType AlertActivation.getAlertType() {
        return this.alertType;
    }
    
    public void AlertActivation.setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }
    
}