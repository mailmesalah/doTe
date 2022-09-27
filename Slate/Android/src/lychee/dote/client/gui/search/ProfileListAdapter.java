package lychee.dote.client.gui.search;

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

public class ProfileListAdapter extends BaseAdapter {

	static final String PROFILE_NAME = "Name";
	static final String PROFILE_STATUS = "Status";
	static final String PHONE_NUMBER = "PhoneNumber";
	static final String PROFILE_TYPE = "Type";
	static final String PROFILE_IMAGE = "ProfileImage";

	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public ProfileListAdapter(Activity activity,
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

	public int getItemTypeCount(){
	     return 4;
	}


	public int getItemType(int position){
		int typePos=0;
	    String type=data.get(position).get("Type");
	    if(type.equals("FriendRequest")){
	    	typePos=0;
	    }else if(type.equals("FriendSuggest")){
	    	typePos=1;
	    }else if(type.equals("Friend")){
	    	typePos=2;
	    }else if(type.equals("BlockList")){
	    	typePos=3;
	    }
	    	    
	    return typePos;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		if (convertView == null) {
			int type = getItemType(position);
		    if(type == 0){
		    	vi = inflater.inflate(R.layout.request_list_row, parent);
		    	
		    	HashMap<String, String> rowData = data.get(position);		
				
				TextView profileName = (TextView) vi.findViewById(R.id.request_profile_name);
				TextView phoneNumber = (TextView) vi.findViewById(R.id.request_phonenumber);
				TextView status = (TextView) vi.findViewById(R.id.request_status);
				ImageView profileImage = (ImageView) vi
						.findViewById(R.id.request_profile_image);
				
				profileName.setText(rowData.get("Name"));
				phoneNumber.setText(rowData.get("PhoneNumber"));
				status.setText(rowData.get("Status"));
				
		    } else if(type == 1) {
		    	vi = inflater.inflate(R.layout.suggest_list_row, parent);
		    	
		    	HashMap<String, String> rowData = data.get(position);		
				
				TextView profileName = (TextView) vi.findViewById(R.id.suggest_profile_name);
				TextView phoneNumber = (TextView) vi.findViewById(R.id.suggest_phonenumber);
				TextView status = (TextView) vi.findViewById(R.id.suggest_status);
				ImageView profileImage = (ImageView) vi
						.findViewById(R.id.suggest_profile_image);
				
				profileName.setText(rowData.get("Name"));
				phoneNumber.setText(rowData.get("PhoneNumber"));
				status.setText(rowData.get("Status"));
				
		    } else if(type == 2) {
		    	vi = inflater.inflate(R.layout.friend_list_row, parent);
		    	
		    	HashMap<String, String> rowData = data.get(position);		
				
				TextView profileName = (TextView) vi.findViewById(R.id.friend_profile_name);
				TextView phoneNumber = (TextView) vi.findViewById(R.id.friend_phonenumber);
				TextView status = (TextView) vi.findViewById(R.id.friend_status);
				ImageView profileImage = (ImageView) vi
						.findViewById(R.id.friend_profile_image);
				
				profileName.setText(rowData.get("Name"));
				phoneNumber.setText(rowData.get("PhoneNumber"));
				status.setText(rowData.get("Status"));
				
		    } else if(type == 3) {
		    	vi = inflater.inflate(R.layout.block_list_row, parent);
		    	
		    	HashMap<String, String> rowData = data.get(position);		
				
				TextView profileName = (TextView) vi.findViewById(R.id.blocklist_profile_name);
				TextView phoneNumber = (TextView) vi.findViewById(R.id.blocklist_phonenumber);				
				ImageView profileImage = (ImageView) vi
						.findViewById(R.id.blocklist_profile_image);
				
				profileName.setText(rowData.get("Name"));
				phoneNumber.setText(rowData.get("PhoneNumber"));
				
		    }
			
		}

		
		

		return vi;
	}

}
