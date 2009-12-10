
package com.cell;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Vector;

import com.cell.exception.NotImplementedException;
import com.cell.gfx.IImage;


/**
 * base class object, all other class entends this </br>
 * @author WAZA
 * @since 2006-11-28
 * @version 1.0
 */
public class CObject
{
	static class NullStorage implements IStorage
	{
		public byte[] load(String name,int id) {
			throw new NotImplementedException();
		}
		public int save(String name,int id, byte[] datas) {
			throw new NotImplementedException();
		}
		public int delete(String name,int id) {
			throw new NotImplementedException();
		}
		public int getIdCount(String name) {
			throw new NotImplementedException();
		}
		public byte[] syncReadBytesFromURL(String url, int timeOut) {
			throw new NotImplementedException();
		}
		public boolean beginReadBytesFromURL(final String url,final IReadListener listener, final int timeOut){
			throw new NotImplementedException();
		}
	}
	
	static class NullAppBridge implements IAppBridge
	{
		public Thread createTempThread() {
			throw new NotImplementedException();
		}

		public Thread createTempThread(Runnable run) {
			throw new NotImplementedException();
		}

		public Thread createServiceThread() {
			throw new NotImplementedException();
		}

		public Thread createServiceThread(Runnable run) {
			throw new NotImplementedException();
		}

		public ClassLoader getClassLoader() {
			return this.getClassLoader();
		}
		
		//
		public InputStream getResource(String file) {
			throw new NotImplementedException();
		}

		public String getAppProperty(String key) {
			throw new NotImplementedException();
		}

		public String setAppProperty(String key, String value) {
			throw new NotImplementedException();
		}

//		public void addAppStateListener(IAppStateListener listener) {
//			throw new NotImplementedException();
//		}
//
//		public void removeAppStateListener(IAppStateListener listener) {
//			throw new NotImplementedException();
//		}
//
//		public Collection<IAppStateListener> getAppListeners() {
//			throw new NotImplementedException();
//		}
		
		public IImage createImage(InputStream is) {
			throw new NotImplementedException();
		}

		public IImage createImage(int w, int h) {
			throw new NotImplementedException();
		}

		public IImage createImage(int argb, int w, int h) {
			throw new NotImplementedException();
		}

		public IImage createImage(String filename) {
			throw new NotImplementedException();
		}

		//
		public void setClipboardText(String str) {
			throw new NotImplementedException();
		}

		public String getClipboardText() {
			throw new NotImplementedException();
		}

		public String getMacAddr() {
			throw new NotImplementedException();
		}

		public int getPing(String host, int bufferSize) {
			throw new NotImplementedException();
		}

		public void openBrowser(String url) {
			throw new NotImplementedException();
		}

		public String getAppSavePath() {
			throw new NotImplementedException();
		}

	}
//	-------------------------------------------------------------------------------------------------------------------------

	
	static public IStorage 		Storage			= new NullStorage();
	static public IAppBridge	AppBridge		= new NullAppBridge();
	static public boolean 		IsDebug 		= false;
	static public Random 		Random 			= new Random();
	static public String		ProductVersion	= "0.0.0";
	static public String		ENCODING		= "UTF-8";
	
	static private int 			Timer 			= 1;
	static private String 		DateFormat 		= "YYYY-MM-DD hh:mm:ss";
	static private String[] DateFormats 		= new String[] {"YYYY", "MM", "DD", "W", "hh", "mm", "ss", };
	static private Date			CurDate;
	static private Calendar		CurCalendar;
	static public	Locale		CurLocale;
	
//	-------------------------------------------------------------------------------------------------------------------------

	static public void initSystem(IStorage file, IAppBridge appBridge)
	{
		initSystem(file, appBridge, Locale.getDefault());
	}
	
	/**
	 * init all system
	 * @param file
	 * @param appBridge
	 */
	static public void initSystem(IStorage file, IAppBridge appBridge, Locale local)
	{
		if (Storage instanceof NullStorage || AppBridge instanceof NullAppBridge)
		{
			Storage 		= file;
			AppBridge	= appBridge;
			CurLocale	= local;
			CurDate		= new Date(System.currentTimeMillis());
			CurCalendar	= Calendar.getInstance(local);
			
			TimeZone.getAvailableIDs();
			
			try
			{
				CurCalendar.setTime(CurDate);
			}
			catch(Exception err)
			{
				err.printStackTrace();
			}
			
			System.out.println(
					"CObject : System initialized !\n" + 
						"\t" + Storage.getClass().getName() + "\n" + 
						"\t" + AppBridge.getClass().getName() + "\n" +
						"\t" + CurLocale  + "\n" +
						"");
		}
		
	}
	
	public static String getEncoding(){
		return ENCODING;
	}

	/**
	 * tick frame timer
	 */
	static public void tickTimer() {
		Timer++;
	}
	/**
	 * reset frame timer
	 */
	static public void resetTimer() {
		Timer = 1;
	}
	/**
	 * get current frame timer
	 * @return 
	 */
	static public int getTimer() {
		return Timer;
	}

	
	
//	 ------------------------------------------------------

	/**
	 * debug console print, System.out.print();</br>
	 * @param str 
	 */
	static public void print(String str) {
//#ifdef _DEBUG
			System.out.print(str);
//#endif
	}

	/**
	 * debug console println, System.out.println();</br>
	 * @param str 
	 */
	static public void println(String str) {
//#ifdef _DEBUG
			System.out.println(str);
//#endif
	}

	
	/**
	 * @param fmt default is "YYYY-MM-DD hh:mm:ss"
	 */
	static public void setTimeFormat(String fmt){
		DateFormat = fmt;
		
		DateFormats[0] = CUtil.getSameCharBlock(fmt,0,'Y');
		DateFormats[1] = CUtil.getSameCharBlock(fmt,0,'M');
		DateFormats[2] = CUtil.getSameCharBlock(fmt,0,'D');
		DateFormats[3] = CUtil.getSameCharBlock(fmt,0,'W');
		DateFormats[4] = CUtil.getSameCharBlock(fmt,0,'h');
		DateFormats[5] = CUtil.getSameCharBlock(fmt,0,'m');
		DateFormats[6] = CUtil.getSameCharBlock(fmt,0,'s');
		
	}

	static public String getTimeFormat(){
		return DateFormat;
	}

	static public Calendar getCalendar(long timeInMillis){
		CurDate.setTime(timeInMillis);
		CurCalendar.setTime(CurDate);
		return CurCalendar;
	}
	
	static public String timeToString(long timeInMillis)
	{
		String ret = DateFormat.toString();
		
		CurDate.setTime(timeInMillis);
		CurCalendar.setTime(CurDate);
		
		int[] dsts = new int[]{
				CurCalendar.get(Calendar.YEAR),
				CurCalendar.get(Calendar.MONTH)+1,
				CurCalendar.get(Calendar.DAY_OF_MONTH),
				CurCalendar.get(Calendar.DAY_OF_WEEK),
				CurCalendar.get(Calendar.HOUR_OF_DAY),
				CurCalendar.get(Calendar.MINUTE),
				CurCalendar.get(Calendar.SECOND),
		};
		
		for(int i=0;i<DateFormats.length;i++)
		{
			if(DateFormats[i]!=null && DateFormats[i].length()>0)
			{
				ret = CUtil.replaceString(
						ret, 
						DateFormats[i], 
						CUtil.intToSting(dsts[i], 10, DateFormats[i].length(), "0")
						);
			}
		}
		
		return ret;
	}
	
	static public String timesliceToStringHour(long timesliceInMillis){

		long time = timesliceInMillis;
		
		int ss = (int)(time / 1000 % 60);
		int mm = (int)(time / 1000 / 60 % 60);
		int hh = (int)(time / 1000 / 60 / 60);
	
		String ret = hh + ":" + CUtil.intToSting(mm,10,2,"0") + ":" + CUtil.intToSting(ss,10,2,"0") ;

		return ret;
	}

	
//	--------------------------------------------------------------------------------------------------------------------------------------

}
