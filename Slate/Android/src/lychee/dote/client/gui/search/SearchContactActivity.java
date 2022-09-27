package lychee.dote.client.gui.search;

import java.util.ArrayList;
import java.util.HashMap;

import lychee.dote.client.R;
import lychee.dote.client.storage.DatabaseHandler;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SearchContactActivity extends Activity {

	ListView list;
    ProfileListAdapter adapter;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_contact);
		
		DatabaseHandler dh = DatabaseHandler.getInstance(this);
		ArrayList<HashMap<String, String>> data = dh.getAllContactList();
		adapter= new ProfileListAdapter(this, data);
		
		list=(ListView) findViewById(R.id.profile_list);
		list.setAdapter(adapter);
	}
}
