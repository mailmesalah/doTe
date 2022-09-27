package lychee.dote.client.utility;

import java.util.Hashtable;
import java.util.Map;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.widget.Toast;

public class StartUp {
	
	public static boolean isGPSOn(Context pContext){
		//Checks if the GPS device is available and enabled with the current context
		LocationManager locationManager = (LocationManager) pContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	public static void showGPSDisabledAlertToUser(final Context pContext){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(pContext);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
        .setCancelable(false)
        .setPositiveButton("Goto Settings Page To Enable GPS",
                new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                pContext.startActivity(callGPSSettingIntent);                
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                dialog.cancel();
                //Exits the app because there is no GPS Activated
                System.exit(0);
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
	
	/*
	 * Checks if the app is loading for the first time
	 */
	public static boolean isFirstTime(Context context){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getBoolean("FirstTime", true);
	}
	
	/*
	 * Sets already Loaded once
	 */
	public static void setNotFirstTime(Context context){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("FirstTime", false);
		editor.commit();
	}
	
	/*
	 * Gets all the contacts in the device
	 */
	public static Map<String, String> getContacts(Context context){
		Map<String, String> contacts = new Hashtable<String, String>(); 
		ContentResolver cr =  context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                  String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                  String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                  if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                     Cursor pCur = cr.query(
                               ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                               null,
                               ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                               new String[]{id}, null);
                     while (pCur.moveToNext()) {
                         String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                         contacts.put(phoneNo, name);                         
                     }
                    pCur.close();
                }
            }
        }
		return contacts;
	}
}
