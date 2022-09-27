package lychee.dote.client.gui.dashboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import lychee.dote.client.R;
import lychee.dote.client.R.layout;
import lychee.dote.client.storage.DatabaseHandler;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


public class ChatFragment extends Fragment {

	ListView list;
    ChatListAdapter adapter;
    
	public ChatFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		final View rootView = inflater.inflate(R.layout.fragment_chat,
				container, false);
		
		list=(ListView)rootView.findViewById(R.id.chatlist);
		 
        // Getting adapter by passing data ArrayList		
		DatabaseHandler dh = DatabaseHandler.getInstance(getActivity());		
        adapter=new ChatListAdapter(getActivity(), dh.getLastFriendChatList());        
        list.setAdapter(adapter);
 
        // Click event for single list row
        list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
			}
 
            
        });
    
        Button bSearch=(Button) rootView.findViewById(R.id.button_searchFriend);
        bSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		return rootView;
	}

}
