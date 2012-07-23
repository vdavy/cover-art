// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.stationmillenium.coverart.domain;

import com.stationmillenium.coverart.domain.SongHistory;
import com.stationmillenium.coverart.domain.SongHistoryDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect SongHistoryDataOnDemand_Roo_DataOnDemand {
    
    declare @type: SongHistoryDataOnDemand: @Component;
    
    private Random SongHistoryDataOnDemand.rnd = new SecureRandom();
    
    private List<SongHistory> SongHistoryDataOnDemand.data;
    
    public SongHistory SongHistoryDataOnDemand.getNewTransientSongHistory(int index) {
        SongHistory obj = new SongHistory();
        setArtist(obj, index);
        setPlayedDate(obj, index);
        setTitle(obj, index);
        return obj;
    }
    
    public void SongHistoryDataOnDemand.setArtist(SongHistory obj, int index) {
        String artist = "artist_" + index;
        if (artist.length() > 200) {
            artist = artist.substring(0, 200);
        }
        obj.setArtist(artist);
    }
    
    public void SongHistoryDataOnDemand.setPlayedDate(SongHistory obj, int index) {
        Calendar playedDate = Calendar.getInstance();
        obj.setPlayedDate(playedDate);
    }
    
    public void SongHistoryDataOnDemand.setTitle(SongHistory obj, int index) {
        String title = "title_" + index;
        if (title.length() > 200) {
            title = title.substring(0, 200);
        }
        obj.setTitle(title);
    }
    
    public SongHistory SongHistoryDataOnDemand.getSpecificSongHistory(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        SongHistory obj = data.get(index);
        Long id = obj.getId();
        return SongHistory.findSongHistory(id);
    }
    
    public SongHistory SongHistoryDataOnDemand.getRandomSongHistory() {
        init();
        SongHistory obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return SongHistory.findSongHistory(id);
    }
    
    public boolean SongHistoryDataOnDemand.modifySongHistory(SongHistory obj) {
        return false;
    }
    
    public void SongHistoryDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = SongHistory.findSongHistoryEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'SongHistory' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<SongHistory>();
        for (int i = 0; i < 10; i++) {
            SongHistory obj = getNewTransientSongHistory(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
