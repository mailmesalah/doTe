package lychee.dote.client.network.communication.end;

import java.io.File;
import java.io.StringReader;

import lychee.dote.client.utility.SwissArmyKnife;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.widget.Toast;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class AccountCreatorWebsocket {

	public static final WebSocketConnection webSConnection = new WebSocketConnection();

	private static final String url="ws://192.168.0.102:8080/Server-war/accountcreator";
	private static AccountCreatorListener mListener;
	private static Context context;
	
	
	public AccountCreatorWebsocket(Context context){
		this.context=context;
	}
	public static void addListener(AccountCreatorListener listener){
		mListener=listener;		
	}
	public static void openServerConnection()
			throws WebSocketException {
		
	
		webSConnection.connect(url, new WebSocketHandler()  {
			@Override
			public void onOpen() {
				mListener.onOpen();
			}

			@Override
			public void onBinaryMessage(byte[] payload) {
				
			}

			@Override			
			public void onTextMessage(String string) {

				mListener.onMessage();
				try {
					JSONObject json = new JSONObject(string);
					String value =json.getString("type");
					boolean success=json.getBoolean("success");
					String reason=json.getString("reason");
					
					
					switch(value){
					
						case "serverreply":
							
							//broadcast the success message to the activity
							mListener.onServerReply(success, reason,AccountCreatorListener.STATE_CREATE);							
							
							break;
							
						case "friendsuggest":
							
							mListener.onServerReply(success, reason,AccountCreatorListener.STATE_CONTACTS_UPLOAD);
							
							break;
							
						case "imageuploadstart":
							mListener.onServerReply(success, reason,AccountCreatorListener.STATE_IMAGE_UPLOAD);							
							break;
						case "imageuploadend":
							mListener.onServerReply(success, reason,AccountCreatorListener.STATE_IMAGE_UPLOADED);
							break;
						default :;
					}
					
				} catch (JSONException e) {
				}
				
			}
			
			@Override
			public void onRawTextMessage(byte[] payload) {

			}


			@Override
			public void onClose(int code, String reason) {
				mListener.onError(reason);
			}
									

		});

	}
	
	public static void sentMessage(String msg){
		webSConnection.sendTextMessage(msg);
	}
		

	public static void sendImage(Bitmap image){		
		byte[] bImage=SwissArmyKnife.getBitmapAsByteArray(image);
		webSConnection.sendBinaryMessage(bImage);
	}
	
	
	public static void disconnect(){
		webSConnection.disconnect();
	}

}
