/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.client.network.communication.end;

/**
 *
 * @author Sely
 *
 * Contact upload download actions are done here
 */
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class ContactUpdaterEnd {

    private Session server;
    private static final Logger log = Logger.getLogger(ContactUpdaterEnd.class.getName());

    @OnOpen
    public void onOpen(Session session, EndpointConfig con) {
        server = session;
        System.out.println("Session Opened");
        log.log(Level.INFO, "Session Opened");
        sendContactToServer();
    }

    @OnMessage
    public void onMessage(String text,Session session) {
        System.out.println("WebSocket message Received! : "+text);
        log.log(Level.INFO, "Message Received");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Session Closed");
        log.log(Level.INFO, "Session Closed :{0}", reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("Error : "+error.getMessage());
        log.log(Level.SEVERE, "Error Occured :{0}", error.getMessage());
    }

    public void connectToServer() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://localhost:8080/doTeWebServer/ContactUpdater");
            container.connectToServer(ContactUpdaterEnd.class, uri);
            System.out.println("Connect to Server Initiated");
            log.log(Level.INFO, "Connect to Server Initiated");

        } catch (DeploymentException | IOException ex) {
            log.log(Level.SEVERE, "Error Occured :{0}", ex.getMessage());
        }
    }
    
    public void sendContactToServer(){
        try {
            
            server.getBasicRemote().sendText("Helloasdsads");
            
            Thread.sleep(1000);
            
            server.getBasicRemote().sendText("Helloasdsads");
            
            System.out.println("Send a hello");
        } catch (IOException ex) {
            Logger.getLogger(ContactUpdaterEnd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ContactUpdaterEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String arg[]){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            try {
                URI uri = URI.create("ws://localhost:8080/doTeWebServer/ContactUpdater");
                container.connectToServer(ContactUpdaterEnd.class, uri);
                System.out.println("Connect to Server Initiated");
                log.log(Level.INFO, "Connect to Server Initiated");
                
            } catch (DeploymentException | IOException ex) {
                log.log(Level.SEVERE, "Error Occured :{0}", ex.getMessage());
            }
            //ContactUpdaterClientEnd ce = new ContactUpdaterEnd();
            //ce.sendContactToServer();
            
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ContactUpdaterEnd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
