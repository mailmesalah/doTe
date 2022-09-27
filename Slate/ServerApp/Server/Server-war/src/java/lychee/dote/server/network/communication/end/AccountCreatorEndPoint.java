package lychee.dote.server.network.communication.end;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lychee.dote.server.accountcreation.ProfileCreator;
import lychee.dote.server.extras.MultiTool;

/**
 *
 * @author Sely
 */
@Stateful
@ServerEndpoint(value = "/accountcreator", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class AccountCreatorEndPoint {

    private Session client;

    @EJB
    ProfileCreator profileCreatorBean;

    @OnMessage
    public void onMessage(Session client, Message message) {
        String type = message.getJson().getString("type");
        if (type.equals("profile")) {
            String name = message.getJson().getString("name");
            String about = message.getJson().getString("about");
            String phone = message.getJson().getString("phone");
            String password = message.getJson().getString("password");

            try {

                if (profileCreatorBean.isPhoneNumberAvailable(phone)) {
                    //Create the profile
                    if (profileCreatorBean.createBasicProfile(name, about, phone, password)) {

                        client.getUserProperties().put("phone", phone);
                        Message msg = new Message(Json.createObjectBuilder()
                                .add("type", "serverreply")
                                .add("success", true)
                                .add("reason", "")
                                .build());
                        //Send response to the client
                        this.client.getBasicRemote().sendObject(msg);
                    } else {
                        Message msg = new Message(Json.createObjectBuilder()
                                .add("type", "serverreply")
                                .add("success", false)
                                .add("reason", "Creating Profile failed")
                                .build());
                        //Send response to the client
                        this.client.getBasicRemote().sendObject(msg);
                    }
                } else {
                    Message msg = new Message(Json.createObjectBuilder()
                            .add("type", "serverreply")
                            .add("success", false)
                            .add("reason", "Phone number not available")
                            .build());
                    //Send response to the client
                    this.client.getBasicRemote().sendObject(msg);
                }

            } catch (IOException | EncodeException ex) {
                System.out.println(ex.getMessage());
            }

        } else if (type.equals("contacts")) {

            //Retrieve the contacts and save it in database            
            String userPhone = (String) client.getUserProperties().get("phone");

            Map<String, String> contacts = new HashMap();
            JsonObject phones = message.getJson();

            for (String phone : phones.keySet()) {
                contacts.put(phone, phones.getString(phone));

            }
            Message msg = new Message(profileCreatorBean.saveContactsAndSuggestFriends(userPhone, contacts));

            try {
                this.client.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(AccountCreatorEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (type.equals("imageupload")) {
            String ext = message.getJson().getString("fileext");
            if (!ext.equals("")) {
                client.getUserProperties().put("imageextension", ext);
            } else {
                client.getUserProperties().put("imageextension", "JPG");
            }

            Message msg = new Message(Json.createObjectBuilder()
                    .add("type", "imageuploadstart")
                    .add("success", true)
                    .add("reason", "")
                    .build());
            try {
                this.client.getBasicRemote().sendObject(msg);
            } catch (IOException | EncodeException ex) {
                Logger.getLogger(AccountCreatorEndPoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Not expected data");
        }

    }

    @OnMessage
    public void onMessage(ByteBuffer byteBuffer) {
        /*
        Saving profile image to profile folder with phone number
         */
        try {
            File dir = new File("/Resources/Images/Profiles");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, client.getUserProperties().get("phone") + ".jpg");
            ImageIO.write(MultiTool.getImageFromByteArray(byteBuffer.array()), "jpg", file);
            Message msg = new Message(Json.createObjectBuilder()
                    .add("type", "imageuploadend")
                    .add("success", true)
                    .add("reason", "")
                    .build());
            this.client.getBasicRemote().sendObject(msg);
        } catch (IOException | EncodeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig con) {
        this.client = session;
        System.out.println("Client Connected to Server");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(error.getMessage());
    }

}
