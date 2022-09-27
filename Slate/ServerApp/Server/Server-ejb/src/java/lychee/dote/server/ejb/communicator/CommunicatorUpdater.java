/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.ejb.communicator;

import javax.ejb.Local;

/**
 *
 * @author Sely
 */
@Local
public interface CommunicatorUpdater {

    void sendRequest(String senderPhoneNumber, String receiverPhoneNumber);
    void sendAccept(String senderPhoneNumber, String receiverPhoneNumber);
    void sendBlock(String senderPhoneNumber, String receiverPhoneNumber);
    void sendUnblock(String senderPhoneNumber, String receiverPhoneNumber);
    
}
