/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.ejb.communicator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import lychee.dote.server.extras.MultiTool;
import lychee.dote.server.storage.accountcreation.Contact;
import lychee.dote.server.storage.accountcreation.Profile;

/**
 *
 * @author Sely
 */
@Stateless
public class CommunicatorUpdaterBean implements CommunicatorUpdater {

    @PersistenceContext(unitName = "ServerStoragePU")
    private EntityManager em;

    @Override
    public void sendRequest(String senderPhoneNumber, String receiverPhoneNumber) {
        //Set Send Profile Contact as Send Request
        TypedQuery<Profile> query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", senderPhoneNumber);
        Profile profile = query.getSingleResult();
        List<Contact> contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(receiverPhoneNumber)){
                contact.setOldContactType("ESendRequest");
                contact.setNewContactType("ESendRequest");
            }
        }
        em.merge(profile);
        
        //Set Receive Profile Contact as Receive Request
        query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", receiverPhoneNumber);
        profile = query.getSingleResult();
        contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(senderPhoneNumber)){
                contact.setOldContactType("AReceiveRequest");
            }
        }
        em.merge(profile);

    }
    
    @Override
    public void sendAccept(String senderPhoneNumber, String receiverPhoneNumber) {
        //Set Send Profile Contact as Send Request
        TypedQuery<Profile> query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", senderPhoneNumber);
        Profile profile = query.getSingleResult();
        List<Contact> contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(receiverPhoneNumber)){
                contact.setOldContactType("CFriend");
                contact.setNewContactType("CFriend");
            }
        }
        em.merge(profile);
        
        //Set Receive Profile Contact as Receive Request
        query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", receiverPhoneNumber);
        profile = query.getSingleResult();
        contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(senderPhoneNumber)){
                contact.setNewContactType("CFriend");
            }
        }
        em.merge(profile);

    }
    
    @Override
    public void sendBlock(String senderPhoneNumber, String receiverPhoneNumber) {
        //Set Send Profile Contact as Send Request
        TypedQuery<Profile> query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", senderPhoneNumber);
        Profile profile = query.getSingleResult();
        List<Contact> contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(receiverPhoneNumber)){
                contact.setOldContactType("DMeBlocked");
                contact.setNewContactType("DMeBlocked");
            }
        }
        em.merge(profile);
        
        //Set Receive Profile Contact as Receive Request
        query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", receiverPhoneNumber);
        profile = query.getSingleResult();
        contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(senderPhoneNumber)){
                contact.setNewContactType("GFriendBlocked");
            }
        }
        em.merge(profile);

    }
    
    @Override
    public void sendUnblock(String senderPhoneNumber, String receiverPhoneNumber) {
        //Set Send Profile Contact as Send Request
        TypedQuery<Profile> query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", senderPhoneNumber);
        Profile profile = query.getSingleResult();
        List<Contact> contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(receiverPhoneNumber)){
                contact.setOldContactType("BFriendSuggest");
                contact.setNewContactType("BFriendSuggest");
            }
        }
        em.merge(profile);
        
        //Set Receive Profile Contact as Receive Request
        query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", receiverPhoneNumber);
        profile = query.getSingleResult();
        contacts = profile.getContacts();
        for (Contact contact : contacts) {
            if(contact.getPhoneName().equals(senderPhoneNumber)){
                contact.setNewContactType("BFriendSuggest");
            }
        }
        em.merge(profile);

    }
    
    //Set Online
    public int setProfileOnline(String profileNumber){
        Query query = em.createQuery("Update LocationStatus ls Set ls.onlineStatus='Online' Where ls.profile.phoneNumber=:profileNumber");
        query.setParameter("profileNumber", profileNumber);
        return query.executeUpdate();
    }
    
    //Set Offline
    public int setProfileOffline(String profileNumber){
        Query query = em.createQuery("Update LocationStatus ls Set ls.onlineStatus='Offline' Where ls.profile.phoneNumber=:profileNumber");
        query.setParameter("profileNumber", profileNumber);
        return query.executeUpdate();
    }

    //Add new Contact
    public void addContact(String profileNumber,String contactName,String contactNumber){
        Query query = em.createQuery("Select p From Profile p Where p.phoneNumber=:profileNumber");
        query.setParameter("profileNumber", profileNumber);
        Profile profile=(Profile) query.getSingleResult();
        MultiTool mt = new MultiTool();
        Timestamp ts = new Timestamp(mt.getUTCTimeInMilliseconds());
        Contact contact = new Contact(contactNumber, contactName, "NotInDote", "NotInDote", ts , false);
        //Check if the contact is already in doTe
        query = em.createQuery("Select p From Profile p Where p.phoneNumber=:=contactNumber");
        query.setParameter("contactNumber", contactNumber);
        if(!query.getResultList().isEmpty()){
            contact.setNewContactType("BFriendSuggest");
        }
        //Add the contact to Profile
        profile.getContacts().add(contact);
        //Save it to Database
        em.persist(profile);        
    }
    
    public void sendOneToOneMessage(String sendProfileNumber,String receiveProfileNumber,String textMessage, long senderDateTime,String messageType,String filePath){
        
    }
}
