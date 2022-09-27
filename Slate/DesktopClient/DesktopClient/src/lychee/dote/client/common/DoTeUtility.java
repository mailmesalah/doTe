/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lychee.dote.client.common;

import java.util.List;
import lychee.dote.servernclient.Profile;

/**
 *
 * @author Sely
 */
public class DoTeUtility {
    /*
    Checks if the GPS Service is available
    */
    public static boolean isGPSServiceActive(){
        return true;
    }
    
    /*
    Checks if the App run for the first Time
    */
    public static boolean isAppFirstTime(){
        return true;
    }
        
    public class Contacts{
        /*
        Get All Contacts From Client
        */
        public List<Profile> getDeviceContacts(){
            return null;
            
        }
        
        /*
        Updates App Contacts
        */
        public void updateAppContacts(){
            
        }
    }
}
