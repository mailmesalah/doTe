package lychee.dote.client.network.communication.end;

import org.json.JSONException;

public interface AccountCreatorListener {

	//Account Creation State Flow Constants
	public static final int STATE_CREATE=0;
	public static final int STATE_CONTACTS_UPLOAD=1;
	public static final int STATE_IMAGE_UPLOAD=2;
	public static final int STATE_IMAGE_UPLOADED=3;
	
	public void onServerReply(boolean success,String reason, int state) throws JSONException;
	public void onOpen();
	public void onMessage();
	public void onError(String e);
}
