package lychee.dote.client.utility;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SwissArmyKnife {
	public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 0, outputStream);		
		return outputStream.toByteArray();
	}
	
	public static Bitmap getBitmapFromByteArray(byte[] imgByte){		 
		return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
	}
	
	public static long getUTCTimeInMilliseconds(){
		Calendar curDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		System.out.println("Current Date in UTC "+curDate.getTime());
		return curDate.getTimeInMillis();		
	}
	
	public static String getLocalDatenTime(String dateString,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date=sdf.parse(dateString);
		sdf.setTimeZone(TimeZone.getDefault());		
		return sdf.format(date);
	}
	
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
