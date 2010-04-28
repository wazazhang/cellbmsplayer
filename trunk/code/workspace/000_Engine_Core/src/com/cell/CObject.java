
package com.cell;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
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
import com.cell.gfx.AScreen;
import com.cell.gfx.IGfxBridge;
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
		File rms_file;
		
		public NullStorage() {
			try {
				rms_file = File.createTempFile("null", "rms");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public byte[] load(String name, int id) {
			return null;
		}
		public int save(String name,int id, byte[] datas) {
			return FILE_FAILE;
		}
		public int delete(String name, int id) {
			return FILE_FAILE;
		}
		public int getIdCount(String name) {
			return 0; 
		}

		public byte[] syncReadBytesFromURL(String url, int timeOut)
		{
			URLConnection c = null;
			InputStream is = null;
			
			URL Url = null;
			try {
				Url = new URL(url);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
				return null;
			}

			try {
				c = Url.openConnection();
				c.setConnectTimeout(timeOut);
				c.setReadTimeout(timeOut);
				c.connect();

				is = c.getInputStream();

				int len = (int) c.getContentLength();
				if (len > 0) {
					int actual = 0;
					int bytesread = 0;
					byte[] data = new byte[len];
					while ((bytesread != len) && (actual != -1)) {
						actual = is.read(data, bytesread, len - bytesread);
						bytesread += actual;
					}
					is.close();
					return data;
				}
			} catch (IOException err){
				err.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return null;
		}

		public boolean beginReadBytesFromURL(final String url,final IReadListener listener, final int timeOut)
		{
			try{
				Thread t = new Thread(new LoadTask(url, listener, timeOut));
				t.start();
				return true;
			}catch(Exception err){
				err.printStackTrace();
				return false;
			}
		}
		
		class LoadTask implements Runnable
		{
			final String 		url;
			final IReadListener	listener;
			final int 			timeOut;
			
			public LoadTask(final String url,final IReadListener listener, final int timeOut)
			{
				this.url		= url;
				this.listener	= listener;
				this.timeOut	= timeOut;
			}
			
			public void run()
			{
				byte data[] = syncReadBytesFromURL(url, timeOut);
				if (listener != null) {
					if (data != null) {
						listener.notifyReadAction(IReadListener.ACTION_COMPLETE, url, data);
					} else {
						listener.notifyReadAction(IReadListener.ACTION_ERROR, url, data);
					}
				}
			}
		}
	}
	
	static class NullAppBridge implements IAppBridge
	{
		final Hashtable<String, String>	Propertys = new Hashtable<String, String>();
		final ClassLoader				m_ClassLoader;
		final Class<?>					m_RootClass;
		
		public NullAppBridge() {
			m_ClassLoader	= CObject.class.getClass().getClassLoader();
			m_RootClass		= CObject.class.getClass();
		}
		
		public Thread createTempThread() {
			return new Thread("Temp-Thread");
		}

		public Thread createTempThread(Runnable run) {
			return new Thread(run, "Temp-Thread");
		}

		public Thread createServiceThread() {
			return new Thread("Service-Thread");
		}

		public Thread createServiceThread(Runnable run) {
			return new Thread(run, "Service-Thread");
		}
		
		public ClassLoader getClassLoader() {
			return m_ClassLoader;
		}
		
		public InputStream	getResource(String file) 
		{
			InputStream is = m_ClassLoader.getResourceAsStream(file);
			if (is == null) {
				is = m_RootClass.getResourceAsStream(file);
			}
			if (is == null) {
				is = m_RootClass.getClassLoader().getResourceAsStream(file);
			}
			if (is == null) {
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			}
			return is;
		}
		
		public String getAppProperty(String key) {
			return Propertys.get(key);
		}
		
		public String setAppProperty(String key, String value) {
			return Propertys.put(key, value);
		}

		//
		public void 	setClipboardText(String str){
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        StringSelection text = new StringSelection(str);
	        clipboard.setContents(text,null);
		}
		
		public String 	getClipboardText(){
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable contents = clipboard.getContents(this);
	        DataFlavor flavor = DataFlavor.stringFlavor;
	        if(contents.isDataFlavorSupported(flavor)){
	            try{
	                return (String)contents.getTransferData(flavor);
	            }catch(Exception ee){ee.printStackTrace();}    
	        }
	        return "";
		}
		
		public String getMacAddr() {
			try {
				return InetAddress.getLocalHost().toString();
			} catch (java.io.IOException e) {
				System.err.println("IOException " + e.getMessage());
				return System.currentTimeMillis() + "";
			}
		}

		public int getPing(String host, int bufferSize){
			return -1;
		}
}
//	-------------------------------------------------------------------------------------------------------------------------

	
	static public IStorage 		Storage			= new NullStorage();
	static public IAppBridge	AppBridge		= new NullAppBridge();
	static public Random 		Random 			= new Random();
	static public String		ProductVersion	= "0.0.0";
	static public String		ENCODING		= "UTF-8";
	static public  Locale		CurLocale		= Locale.getDefault();
	
	static private String 		DateFormat 		= "YYYY-MM-DD hh:mm:ss";
	static private String[]		DateFormats 	= new String[] {"YYYY", "MM", "DD", "W", "hh", "mm", "ss", };
	static private Date			CurDate			= new Date(System.currentTimeMillis());
	static private Calendar		CurCalendar		= Calendar.getInstance();
	
//	-------------------------------------------------------------------------------------------------------------------------
	
	static public void initSystem(IStorage file, IAppBridge appBridge)
	{
		initSystem(file, appBridge, Locale.getDefault());
	}
	
	static public void initSystem(IStorage file, IAppBridge appBridge, Locale local)
	{
		Storage 	= file;
		AppBridge	= appBridge;
		CurLocale	= local;
		CurDate		= new Date(System.currentTimeMillis());
		CurCalendar	= Calendar.getInstance(local);
		
		if (AppBridge instanceof IGfxBridge) {
			AScreen.GfxAdapter = (IGfxBridge)AppBridge;
		}
		
		TimeZone.getAvailableIDs();
		
		try {
			CurCalendar.setTime(CurDate);
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		System.out.println(
				"CObject : System initialized !\n" + 
					"\tIStorage   = " + Storage.getClass().getName() + "\n" + 
					"\tIAppBridge = " + AppBridge.getClass().getName() + "\n" +
					"\tIGfxBridge = " + AScreen.GfxAdapter + "\n" +
					"\tLocale     = " + CurLocale  + "\n" +
					"");
		
	}
	
	public static String getEncoding(){
		return ENCODING;
	}

//	 ------------------------------------------------------

	/**
	 * debug console print, System.out.print();</br>
	 * @param str 
	 */
	static public void print(String str) {
		System.out.print(str);
	}

	/**
	 * debug console println, System.out.println();</br>
	 * @param str 
	 */
	static public void println(String str) {
		System.out.println(str);
	}
	
	/**
	 * @param fmt default is "YYYY-MM-DD hh:mm:ss"
	 */
	static public void setTimeFormat(String fmt)
	{
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
