package lychee.dote.client.service;

import lychee.dote.client.utility.SwissArmyKnife;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class CommunicatorService extends Service {

	//Websocket Members
	private static final WebSocketConnection mWebSocket = new WebSocketConnection();
	private static final String mWebSocketURL="ws://192.168.0.102:8080/Server-war/communicator";
	private static final int CONNECTED=0;
	private static final int DISCONNECTED=1;
	private static int mWebSocketStatus=DISCONNECTED;
	
	
	//Network connection checker
	private final BroadcastReceiver networkNotification = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals("android.net.conn.CONNECTIVITY_CHANGE")){
				//Network change
				connectWebSocket();
			}
						
		}
	};
	
	@Override
	public void onCreate(){
		//Register Broadcast Receiver for receiving Network connectivity broadcast
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(networkNotification, filter);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if(SwissArmyKnife.isNetworkAvailable(this) && mWebSocketStatus==DISCONNECTED){
			//Internet is available and websocket is disconnected
			//then connect again.
			connectWebSocket();
		
		}
		
		
		
		
		return Service.START_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		if(SwissArmyKnife.isNetworkAvailable(this) && mWebSocketStatus==DISCONNECTED){
			//Internet is available and websocket is disconnected
			//then connect again.
			connectWebSocket();
		}
		return null;
	}
	
	@Override
	public void onDestroy(){
		unregisterReceiver(networkNotification);
	}	
	
	private static void connectWebSocket(){
		try {
			mWebSocket.connect(mWebSocketURL, new WebSocketHandler(){
				
				@Override
				public void onOpen() {
					mWebSocketStatus=CONNECTED;
				}

				@Override
				public void onBinaryMessage(byte[] payload) {
					
				}

				@Override			
				public void onTextMessage(String string) {

					
				}
				
				@Override
				public void onRawTextMessage(byte[] payload) {

				}


				@Override
				public void onClose(int code, String reason) {
					mWebSocketStatus=DISCONNECTED;
				}

			});
		} catch (WebSocketException e) {
			
		}

	}

}
