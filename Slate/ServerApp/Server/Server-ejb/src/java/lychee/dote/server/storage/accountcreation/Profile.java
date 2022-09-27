/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lychee.dote.server.storage.accountcreation;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Sely
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name="findProfileByPhone",
                query="Select p From Profile p Where p.phoneNumber=:phone"
        ),
        
        @NamedQuery(
                name="findProfilesByPhoneNumbers",
                query="Select p From Profile p Where p.phoneNumber In (:phonenumbers) "
        )
})
    

public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    
    //members
    private String name;
    private String about;
    private String phoneNumber;
    private String profilePassword;
    private Timestamp addedOn;
    private String profileImage;
    
    @OneToMany(cascade=ALL,mappedBy = "profile")
    private List<Contact> contacts;
    

    
    public Profile() {
        this.contacts = new ArrayList<>();
    }

    public Profile(String name, String about, String phone, String profilePassword) {       
        this.name = name;
        this.about = about;
        this.phoneNumber = phone;
        this.profilePassword = profilePassword;
        
        this.contacts = new ArrayList<>();
    }

    public Profile(String name, String about, String phoneNumber, String profilePassword, Timestamp addedOn, String profileImage) {
        this.name = name;
        this.about = about;
        this.phoneNumber = phoneNumber;
        this.profilePassword = profilePassword;
        this.addedOn = addedOn;
        this.profileImage = profileImage;
    }

    public Timestamp getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Timestamp addedOn) {
        this.addedOn = addedOn;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePassword() {
        return profilePassword;
    }

    public void setProfilePassword(String profilePassword) {
        this.profilePassword = profilePassword;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
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
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "lychee.dote.server.storage.accountcreation.Profile[ id=" + id + " ]";
    }
    
}
