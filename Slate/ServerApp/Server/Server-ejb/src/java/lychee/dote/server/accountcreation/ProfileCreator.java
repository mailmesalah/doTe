/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lychee.dote.server.accountcreation;

import java.util.Map;
import javax.ejb.Local;
import javax.json.JsonObject;

/**
 *
 * @author Sely
 */
@Local
public interface ProfileCreator {

    public boolean isPhoneNumberAvailable(String mphone);    
    public boolean createBasicProfile(String name, String aboutMe,String phone,String password);
    public JsonObject saveContactsAndSuggestFriends(String userPhone,Map<String,String> contacts); 
    
}
