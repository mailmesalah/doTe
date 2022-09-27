package lychee.dote.server.network.communication.end;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.nio.ByteBuffer;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import lychee.dote.server.ejb.communicator.CommunicatorUpdater;

/**
 *
 * @author Sely
 */
@Stateful
@ServerEndpoint(value = "/communicator", encoders = {MessageEncoder.class}, decoders = {MessageDecoder.class})
public class CommunicatorWebsocket {

    @EJB
    private CommunicatorUpdater mCommunicatorUpdaterBean;

    private Session mClient;

    @OnMessage
    public void onMessage(Session client, Message message) {
        String contactNumber="";
        String type = message.getJson().getString("type");
        switch (type) {
            case "sendonetoonemessage":
                contactNumber=message.getJson().getString("contactnumber");
                break;
            case "sendclubmessage":
                break;
            case "sendrequest":
                //Parse Send Request from user and update the database                
                contactNumber=message.getJson().getString("contactnumber");
                mCommunicatorUpdaterBean.sendRequest((String) mClient.getUserProperties().get("phonenumber"), contactNumber);
                break;
            case "sendaccept":
                contactNumber=message.getJson().getString("contactnumber");
                mCommunicatorUpdaterBean.sendAccept((String) mClient.getUserProperties().get("phonenumber"), contactNumber);
                break;
            case "sendblock":
                contactNumber=message.getJson().getString("contactnumber");
                mCommunicatorUpdaterBean.sendBlock((String) mClient.getUserProperties().get("phonenumber"), contactNumber);
                break;
            case "sendunblock":
                contactNumber=message.getJson().getString("contactnumber");
                mCommunicatorUpdaterBean.sendUnblock((String) mClient.getUserProperties().get("phonenumber"), contactNumber);
                break;
            case "phonenumber":
                mClient.getUserProperties().put("phonenumber", message.getJson().getString("phonenumber",""));
                break;
            case "phonenumberchange":
                break;
            default:
                System.out.println("Not expected data");
                break;
        }

    }

    @OnMessage
    public void onMessage(ByteBuffer byteBuffer) {

    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig con) {
        mClient = session;
        System.out.println("Client Connected to Server");
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println(error.getMessage());
    }

}
