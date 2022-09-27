/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lychee.dote.server.extras;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.imageio.ImageIO;

/**
 *
 * @author Sely
 */
public class MultiTool {

    public static byte[] getImageAsByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        baos.flush();
        return baos.toByteArray();
    }

    public static BufferedImage getImageFromByteArray(byte[] imgByte) throws IOException {
        InputStream in = new ByteArrayInputStream(imgByte);
        return ImageIO.read(in);
    }
    
    public long getUTCTimeInMilliseconds(){
		Calendar curDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		System.out.println("Current Date in UTC "+curDate.getTime());
		return curDate.getTimeInMillis();		
	}
	
	public String getLocalDatenTime(String dateString,String pattern) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date=sdf.parse(dateString);
		sdf.setTimeZone(TimeZone.getDefault());		
		return sdf.format(date);
	}
}
