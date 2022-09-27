package lychee.dote.client.gui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

import lychee.dote.client.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatListAdapter extends BaseAdapter {

	static final String PROFILE_ID = "ID";
	static final String PROFILE_NAME = "Name";
	static final String LAST_CHAT = "Message";
	static final String LAST_LOCATION = "lastLocation";
	static final String PROFILE_IMAGE = "profileImage";
	static final String SEEN = "Seen";
	static final String MESSAGE_TYPE = "MessageType";
	static final String MESSAGE_TIME = "TimenDate";


	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ChatListAdapter(Activity activity,
			ArrayList<HashMap<String, String>> data) {
		super();
		this.activity = activity;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

		return data.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.single_chat_list_row, null);
		}

		TextView profileName = (TextView) vi.findViewById(R.id.profile_name);
		TextView lastChat = (TextView) vi.findViewById(R.id.last_chat);
		TextView lastLocation = (TextView) vi.findViewById(R.id.last_location);
		ImageView profileImage = (ImageView) vi
				.findViewById(R.id.profile_image);

		HashMap<String, String> rowData = new HashMap<String, String>();
		rowData = data.get(position);

		// Setting all values in listview
		profileName.setText(rowData.get(PROFILE_NAME));
		lastChat.setText(rowData.get(LAST_CHAT));
		lastLocation.setText(rowData.get(LAST_LOCATION));

		return vi;
	}

}
