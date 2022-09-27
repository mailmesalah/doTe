/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.storage.accountcreation;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Sely
 */
@Entity
public class LocationStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //Members
    private Profile profile;
    private Timestamp LocationDateTime;
    private boolean onlineStatus;
    private float xCord;
    private float yCord;

    public LocationStatus() {
    }

    public LocationStatus(Profile profile, Timestamp LocationDateTime, boolean onlineStatus) {
        this.profile = profile;
        this.LocationDateTime = LocationDateTime;
        this.onlineStatus = onlineStatus;
    }

    public LocationStatus(Profile profile, Timestamp LocationDateTime, boolean onlineStatus, float xCord, float yCord) {
        this.profile = profile;
        this.LocationDateTime = LocationDateTime;
        this.onlineStatus = onlineStatus;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Timestamp getLocationDateTime() {
        return LocationDateTime;
    }

    public void setLocationDateTime(Timestamp LocationDateTime) {
        this.LocationDateTime = LocationDateTime;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public float getxCord() {
        return xCord;
    }

    public void setxCord(float xCord) {
        this.xCord = xCord;
    }

    public float getyCord() {
        return yCord;
    }

    public void setyCord(float yCord) {
        this.yCord = yCord;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocationStatus)) {
            return false;
        }
        LocationStatus other = (LocationStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lychee.dote.server.storage.accountcreation.LocationStatus[ id=" + id + " ]";
    }
    
}
