package lychee.dote.client.gui.firsttime;

import java.io.File;
import java.util.Map;

import lychee.dote.client.R;
import lychee.dote.client.network.communication.end.AccountCreatorListener;
import lychee.dote.client.network.communication.end.AccountCreatorWebsocket;
import lychee.dote.client.storage.DatabaseHandler;
import lychee.dote.client.utility.GlobalData;
import lychee.dote.client.utility.StartUp;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import de.tavendo.autobahn.WebSocketException;

public class AccountCreatorActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_creator);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ProfileCreator1Fragment())
					.addToBackStack(null).commit();
		}

		try {
			new AccountCreatorWebsocket(this);
			AccountCreatorWebsocket.openServerConnection();
			AccountCreatorWebsocket.addListener(new AccountCreatorListener() {

				@Override
				public void onServerReply(boolean success, String reasonIfFail,
						int state) throws JSONException {
					// Check if the number provided is taken or available
					if (!success) {
						Toast.makeText(AccountCreatorActivity.this,
								"Failed : " + reasonIfFail, Toast.LENGTH_SHORT)
								.show();
						return;
					} else {

						if (state == AccountCreatorListener.STATE_CREATE) {
							// Successfully Account Created
							Toast.makeText(AccountCreatorActivity.this,
									"Success on Server reply",
									Toast.LENGTH_SHORT).show();
							Map<String, String> contacts = StartUp
									.getContacts(AccountCreatorActivity.this);
							
							//save contacts in sqlite
							DatabaseHandler db = DatabaseHandler.getInstance(AccountCreatorActivity.this);
							db.saveContacts(contacts);							
							//Send contacts to server
							JSONObject json = new JSONObject();
							json.put("type", "contacts");
							for (String phone : contacts.keySet()) {
								json.put(phone, contacts.get(phone));
							}

							// Send all contacts to Server
							AccountCreatorWebsocket.sentMessage(json.toString());

						} else if (state == AccountCreatorListener.STATE_CONTACTS_UPLOAD) {
							// Set success Message for Launch Activity for
							// account
							// creation
							Intent returnIntent = new Intent();
							setResult(RESULT_OK, returnIntent);

							// Move to next Frame for Profile Image upload
							ProfileCreator2Fragment newFragment = new ProfileCreator2Fragment();
							FragmentTransaction transaction = getFragmentManager()
									.beginTransaction();

							// Replace whatever is in the fragment_container
							// view
							// with this fragment,
							// and add the transaction to the back stack
							transaction.replace(R.id.container, newFragment);
							transaction.addToBackStack(null);
							// Commit the transaction
							transaction.commit();

						} else if (state == AccountCreatorListener.STATE_IMAGE_UPLOAD) {
							
							AccountCreatorWebsocket.sendImage(GlobalData.profileImage);
							
						} else if (state == AccountCreatorListener.STATE_IMAGE_UPLOADED) {
							Toast.makeText(AccountCreatorActivity.this,
									"Profile Image Saved",
									Toast.LENGTH_SHORT).show();

						}

					}

				}

				@Override
				public void onOpen() {
					Toast.makeText(AccountCreatorActivity.this, "Opened",
							Toast.LENGTH_SHORT).show();

				}

				@Override
				public void onMessage() {

				}

				@Override
				public void onError(String e) {

					Toast.makeText(AccountCreatorActivity.this, "Failed : " + e,
							Toast.LENGTH_LONG).show();
				}
			});
		} catch (WebSocketException e) {
			Toast.makeText(AccountCreatorActivity.this, "Failed : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Profile Details Creator Part 1
	 */
	public static class ProfileCreator1Fragment extends Fragment {

		public ProfileCreator1Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_profile_creator1, container, false);

			rootView.findViewById(R.id.profilecreatornextbutton)
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							try {

								EditText nameText = (EditText) getActivity()
										.findViewById(R.id.name);

								String name = nameText.getText().toString()
										.trim();
								if (name.equals("")) {
									Toast.makeText(getActivity(),
											"Name is not given.",
											Toast.LENGTH_SHORT).show();
									return;
								}

								EditText aboutText = (EditText) getActivity()
										.findViewById(R.id.aboutme);
								String about = aboutText.getText().toString();

								EditText phoneText = (EditText) getActivity()
										.findViewById(R.id.phone);
								String phone = phoneText.getText().toString()
										.trim();
								if (phone.equals("")) {
									Toast.makeText(getActivity(),
											"Phone is not given.",
											Toast.LENGTH_SHORT).show();
									return;
								}

								EditText passwordText = (EditText) getActivity()
										.findViewById(R.id.password);
								String password = passwordText.getText()
										.toString().trim();

								if (password.equals("")) {
									Toast.makeText(getActivity(),
											"Password is not given.",
											Toast.LENGTH_SHORT).show();
									return;
								}

								EditText confirmpText = (EditText) getActivity()
										.findViewById(R.id.confirmp);
								String confirmp = confirmpText.getText()
										.toString().trim();

								if (!password.equals(confirmp)) {
									Toast.makeText(getActivity(),
											"Passwords don't match.",
											Toast.LENGTH_SHORT).show();
									return;
								}

								// Send Profile details to server
								// ProfileCreator1Data pcd = new
								// ProfileCreator1Data(
								// name, about, phone, password);

								JSONObject json = new JSONObject();
								json.put("type", "profile");
								json.put("name", name);
								json.put("about", about);
								json.put("phone", phone);
								json.put("password", password);

								AccountCreatorWebsocket.sentMessage(json
										.toString());

							} catch (Exception e) {
								Toast.makeText(getActivity(),
										"Failed : " + e.getMessage(),
										Toast.LENGTH_SHORT).show();
							}

						}

					});

			return rootView;
		}

	}

	/**
	 * Profile Details Creator Part 2
	 */
	public static class ProfileCreator2Fragment extends Fragment {

		public ProfileCreator2Fragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_profile_creator2, container, false);

			// Register button event

			// Browse Image for profile
			rootView.findViewById(R.id.button_browseImage).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// select a image
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(Intent.createChooser(intent,
									"Select Profile Image"), 1);

						}
					});

			// Save Image
			rootView.findViewById(R.id.button_saveImage).setOnClickListener(
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							
							try {
								//Send Image details
								JSONObject json = new JSONObject();
								json.put("type", "imageupload");
								json.put("fileext", "JPG");
								AccountCreatorWebsocket.sentMessage(json
										.toString());
							} catch (JSONException e) {
								System.out.println(e.getMessage());
							}
							

							
						}
					});

			rootView.findViewById(R.id.profilecreatorfinishbutton)
					.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							getActivity().finish();

						}
					});

			return rootView;
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode,
				Intent data) {
			// Toast.makeText(getActivity(), "Returned",
			// Toast.LENGTH_SHORT).show();
			if (resultCode == RESULT_OK) {
				if (requestCode == 1) {
					Uri selectedImageUri = data.getData();
					String selectedImagePath;
					String filemanagerstring;
					// OI FILE Manager
					filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					selectedImagePath = getPath(selectedImageUri);

					// Update the Image View with the right Image
					ImageView imgV=(ImageView)getActivity().findViewById(R.id.imageView_profileImage);
					if (selectedImagePath != null) {						
						Bitmap pImg=loadImage(selectedImagePath);
						if(pImg!=null){
							imgV.setImageBitmap(pImg);
							GlobalData.profileImage=pImg;
						}
					} else {
						Bitmap pImg=loadImage(filemanagerstring);
						if(pImg!=null){
							imgV.setImageBitmap(pImg);
							GlobalData.profileImage=pImg;
						}
					}					
				}
			}
		}

		private Bitmap loadImage(String imagePath) {
			File imgFile = new File(imagePath);
			Bitmap profileImage = null;
			if (imgFile.exists()) {

				profileImage = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());

			}

			return profileImage;
		}

		

		// UPDATED!
		public String getPath(Uri uri) {
			String[] projection = { MediaStore.Images.Media.DATA };
			Cursor cursor = getActivity().getContentResolver().query(uri,
					projection, null, null, null);
			if (cursor != null) {
				// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
				// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE
				// MEDIA
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				return cursor.getString(column_index);
			} else
				return null;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Propagating to Fragment
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AccountCreatorWebsocket.disconnect();
	}

}
