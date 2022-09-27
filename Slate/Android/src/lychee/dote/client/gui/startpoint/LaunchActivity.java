package lychee.dote.client.gui.startpoint;

import lychee.dote.client.R;
import lychee.dote.client.gui.dashboard.DashboardActivity;
import lychee.dote.client.gui.firsttime.AccountCreatorActivity;
import lychee.dote.client.utility.StartUp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LaunchActivity extends Activity {

	static final int ACCOUNTCREATOR=0;
	static final int DASHBOARD=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Checks if loading the application for the first time
		if(StartUp.isFirstTime(this)){
			//Call AccountCreator Activity
			Intent i = new Intent(this, AccountCreatorActivity.class);
			startActivityForResult(i, ACCOUNTCREATOR);		
		}
		
		//Load Dashboard
		Intent i = new Intent(this, DashboardActivity.class);
		startActivityForResult(i, DASHBOARD);	
		
		this.finish();
							
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (requestCode == ACCOUNTCREATOR) {
	        if(resultCode == RESULT_OK){
	            //String result=data.getStringExtra("result");	            
	            Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
	            
	            //Setting Already Registered
	            StartUp.setNotFirstTime(this);
	        }
	        
	        if (resultCode == RESULT_CANCELED) {
	        	finish();
	        }
	    }
	}
}
