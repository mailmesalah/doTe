/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.accountcreation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lychee.dote.server.storage.accountcreation.Contact;
import lychee.dote.server.storage.accountcreation.Profile;

/**
 *
 * @author Sely
 */
@Stateless
public class ProfileCreatorBean implements ProfileCreator {

    @PersistenceContext(unitName = "ServerStoragePU")
    EntityManager em;

    @Override
    public boolean isPhoneNumberAvailable(String mphone) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Profile> cq = cb.createQuery(Profile.class);
        Root<Profile> pet = cq.from(Profile.class);
        cq.where(cb.equal(pet.get("phone"), mphone));

        TypedQuery<Profile> q = em.createQuery(cq);
        List<Profile> results = q.getResultList();
        return results.isEmpty();
    }

    @Override
    public boolean createBasicProfile(String name, String aboutMe, String phone, String password) {
        Profile profile = new Profile(name, aboutMe, phone, password);
        try {
            em.persist(profile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public JsonObject saveContactsAndSuggestFriends(String userPhone, Map<String,String> phones) {        
        List<Contact> contacts = new ArrayList<>();
        TypedQuery<Profile> query = em.createNamedQuery("findProfileByPhone", Profile.class);
        query.setParameter("phone", userPhone);
        Profile profile = query.getSingleResult();
        System.out.println(profile.getName());
        
        //Remove 'type' entry which is not a phone number
        phones.remove("type");
        
        
        String numbers="'";
        for (String phone : phones.keySet()) {
            Contact c= new Contact(phone, phones.get(phone),"FNone","FNone");
            c.setProfile(profile);
            contacts.add(c);
            numbers=numbers+phone+"','";
            //Save Contact
            em.persist(c);            
        }
        numbers=numbers+"'";
        profile.setContacts(contacts);
        //Save the updated profile
        em.persist(profile);
        
        //Get Suggested friends
        query = em.createNamedQuery("findProfilesByPhoneNumbers", Profile.class);
        query.setParameter("phonenumbers", numbers);
        List<Profile> friendsList = query.getResultList();
        
        //Convert Profiles to Json
        JsonObjectBuilder jsonObj = Json.createObjectBuilder();
        jsonObj.add("type", "friendsuggest");
        jsonObj.add("success", true);
        jsonObj.add("reason", "");
        for (Profile pro : friendsList) {
            jsonObj.add(pro.getPhoneNumber(), pro.getName());
            
        }
        return jsonObj.build();
    }

}
