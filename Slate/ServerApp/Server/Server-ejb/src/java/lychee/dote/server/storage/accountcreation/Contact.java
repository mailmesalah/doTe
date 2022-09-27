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
import javax.persistence.ManyToOne;

/**
 *
 * @author Sely
 */
@Entity
public class Contact implements Serializable {

    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //Members
    private String phoneName;
    private String name;
    private String oldContactType;
    private String newContactType;
    private Timestamp addedOn;
    private boolean synchronised;

    @ManyToOne
    private Profile profile;

    public Contact() {
    }
    
    
    public Contact(String phone, String name,String oldContactType,String newContactType) {
        this.phoneName = phone;
        this.name = name;
        this.oldContactType=oldContactType;
        this.newContactType=newContactType;
    }

    public Contact(String phone, String name, String oldContactType,String newContactType, Timestamp addedOn, boolean synchronised) {
        this.phoneName = phone;
        this.name = name;
        this.oldContactType = oldContactType;
        this.newContactType=newContactType;
        this.addedOn = addedOn;
        this.synchronised = synchronised;
    }

    public String getNewContactType() {
        return newContactType;
    }

    public void setNewContactType(String newContactType) {
        this.newContactType = newContactType;
    }

    public Timestamp getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isSynchronised() {
        return synchronised;
    }

    public void setSynchronised(boolean synchronised) {
        this.synchronised = synchronised;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldContactType() {
        return oldContactType;
    }

    public void setOldContactType(String oldContactType) {
        this.oldContactType = oldContactType;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lychee.dote.server.storage.accountcreation.Contact[ id=" + id + " ]";
    }
    
}
