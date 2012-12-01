// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.stationmillenium.coverart.domain.alert;

import com.stationmillenium.coverart.domain.alert.AlertEmail;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AlertEmail_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager AlertEmail.entityManager;
    
    public static final EntityManager AlertEmail.entityManager() {
        EntityManager em = new AlertEmail().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AlertEmail.countAlertEmails() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AlertEmail o", Long.class).getSingleResult();
    }
    
    public static List<AlertEmail> AlertEmail.findAllAlertEmails() {
        return entityManager().createQuery("SELECT o FROM AlertEmail o", AlertEmail.class).getResultList();
    }
    
    public static AlertEmail AlertEmail.findAlertEmail(Long id) {
        if (id == null) return null;
        return entityManager().find(AlertEmail.class, id);
    }
    
    public static List<AlertEmail> AlertEmail.findAlertEmailEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AlertEmail o", AlertEmail.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AlertEmail.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AlertEmail.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AlertEmail attached = AlertEmail.findAlertEmail(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AlertEmail.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AlertEmail.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AlertEmail AlertEmail.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AlertEmail merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}