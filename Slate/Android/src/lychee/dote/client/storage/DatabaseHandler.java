package lychee.dote.client.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables

	private static DatabaseHandler database;

	private DatabaseHandler(Context context) {
		super(context, "Storage", null, 1);
	}

	public static synchronized DatabaseHandler getInstance(Context context) {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		if (database == null) {
			database = new DatabaseHandler(context.getApplicationContext());
		}
		return database;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Contacts
		db.execSQL("Create Table Contact(ID Integer Primary Key autoincrement, Name Text,PhoneNumber Text,ProfileImage Blob,ProfileChangedOn Text,Status Text,StatusChangedOn Text, ContactType Text Not Null Unique(PhoneNumber));");
		// Location
		db.execSQL("Create Table Location(ID Integer Primary Key autoincrement, Name Text,PhoneNumber Text,Latitude Real,Longitude Real,TimenDate Text Unique(PhoneNumber));");
		// FriendsChat
		db.execSQL("Create Table FriendsChat(ID Integer Primary Key autoincrement, Name Text,PhoneNumber Text,TimenDate Text,Direction Text,SendTime Text,ServerReceiptTime Text,DeliveryTime Text,SeenTime Text,MessageType Text,DataPath Text,TextMessage Text,IsDownloaded Integer);");
		// FriendsChatHeader
		db.execSQL("Create Table FriendsChatHeader(ID Integer Primary Key autoincrement, Name Text,TimenDate Text,Seen Integer,MessageType Text,TextMessage Text);");
		Log.d("Database : ", "Database and Table Created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("Drop Table If Exists Contact;");
		db.execSQL("Drop Table If Exists Friend;");
		db.execSQL("Drop Table If Exists SuggestFriend;");
		db.execSQL("Drop Table If Exists FriendRequest;");
		db.execSQL("Drop Table If Exists BlockList;");
		db.execSQL("Drop Table If Exists Location;");
		db.execSQL("Drop Table If Exists FriendsChat;");
		db.execSQL("Drop Table If Exists FriendsChatHeader;");

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	public ArrayList<HashMap<String, String>> getLastFriendChatList() {
		ArrayList<HashMap<String, String>> lastChatList = new ArrayList<>();
		SQLiteDatabase rdb = database.getReadableDatabase();
		Cursor cur = rdb.query("FriendsChatHeader", new String[] { "ID",
				"Name", "TimenDate", "Seen", "MessageType", "TextMessage" },
				null, null, null, null, " TimenDate Asc");
		if (cur.moveToFirst()) {
			do {
				HashMap<String, String> row = new HashMap<>();
				row.put("ID", cur.getString(0));
				row.put("Name", cur.getString(1));
				row.put("TimenDate", cur.getString(2));
				row.put("Seen", cur.getString(3));
				row.put("MessageType", cur.getString(4));
				row.put("TextMessage", cur.getString(5));
				lastChatList.add(row);
			} while (cur.moveToNext());
		}
		return lastChatList;
	}

	public ArrayList<HashMap<String, String>> getAllContactList() {
		ArrayList<HashMap<String, String>> contactList = new ArrayList<>();
		SQLiteDatabase rdb = database.getReadableDatabase();

		Cursor cur = rdb.query("Contact", new String[] { "ID", "Name",
				"PhoneNumber", "ProfileImage", "Status", "ContactType" }, null,
				null, null, null, "ContactType,Name Asc");
		if (cur.moveToFirst()) {
			do {
				HashMap<String, String> row = new HashMap<>();
				row.put("ID", cur.getString(0));
				row.put("Name", cur.getString(1));
				// row.put("TimenDate", );
				row.put("Status", cur.getString(3));
				row.put("Type", cur.getString(4));
				contactList.add(row);
			} while (cur.moveToNext());
		}

		return contactList;
	}

	public boolean saveFriendSuggests(JSONObject friendSuggestList) {
		// Remove 'type' entry which is not a phone number
		friendSuggestList.remove("type");

		String numbers = "'";
		for (Iterator<String> iterator = friendSuggestList.keys(); iterator
				.hasNext();) {
			String phone = (String) iterator.next();
			numbers = numbers + phone + "','";
		}
		numbers = numbers + "'";

		SQLiteDatabase db = database.getWritableDatabase();
		String sql = "Update Contact Set ContactType='BFriendSuggest' Where PhoneNumber In("
				+ numbers + ");";
		db.execSQL(sql);
		db.close();

		return true;
	}

	public boolean saveContacts(Map<String, String> contacts) {
		SQLiteDatabase db = database.getWritableDatabase();
		
		try {
			db.beginTransaction();
			for (String phone : contacts.keySet()) {
				ContentValues row = new ContentValues();
				row.put("Name", contacts.get(phone));
				row.put("PhoneNumber", phone);
				row.put("ContactType", "None");
				db.insert("Contact", null, row);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// Roll back if setTransactionSuccessful() is not called
			db.endTransaction();
			return false;
		}
		return true;
	}

}