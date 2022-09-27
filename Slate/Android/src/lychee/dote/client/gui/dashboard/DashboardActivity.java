package lychee.dote.client.gui.dashboard;

import lychee.dote.client.R;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

public class DashboardActivity extends Activity {
	
		// Declaring our tabs and the corresponding fragments.
	    ActionBar.Tab mapTab,chatTab, clubTab;
	    Fragment mapFragmentTab = new MapFragment();
	    Fragment chatFragmentTab = new ChatFragment();
	    Fragment clubFragmentTab = new ClubFragment();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		// Asking for the default ActionBar element that our platform supports.
		        android.app.ActionBar actionBar = getActionBar();
		
		        // Screen handling while hiding ActionBar icon.
		        actionBar.setDisplayShowHomeEnabled(false);
		        
		        // Screen handling while hiding Actionbar title.		
		        actionBar.setDisplayShowTitleEnabled(false);
		
		        // Creating ActionBar tabs.		
		        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		        // Setting custom tab icons.		
		        mapTab = actionBar.newTab();//.setIcon(R.drawable.bmw_logo);	
		        chatTab = actionBar.newTab();//.setIcon(R.drawable.toyota_logo);
		        clubTab = actionBar.newTab();//.setIcon(R.drawable.ford_logo);
		
		        // Setting tab listeners.
		        mapTab.setTabListener(new TabListener(mapFragmentTab));
		        chatTab.setTabListener(new TabListener(chatFragmentTab));
		        clubTab.setTabListener(new TabListener(clubFragmentTab));
		
		        // Adding tabs to the ActionBar.
		        actionBar.addTab(mapTab);
		        actionBar.addTab(chatTab);
		        actionBar.addTab(clubTab);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
