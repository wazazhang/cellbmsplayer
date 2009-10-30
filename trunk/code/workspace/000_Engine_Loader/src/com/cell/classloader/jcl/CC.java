package com.cell.classloader.jcl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import javax.crypto.*;

public class CC 
{
	private Cipher e, d;

	CC(String strKey) throws Exception 
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = gg(strKey.getBytes());
		e = Cipher.getInstance("DES");
		e.init(Cipher.ENCRYPT_MODE, key);
		d = Cipher.getInstance("DES");
		d.init(Cipher.DECRYPT_MODE, key);
	}

	byte[] ee(byte[] ab) throws Exception {
		return e.doFinal(ab);
	}

	byte[] dd(byte[] ab) throws Exception {
		return d.doFinal(ab);
	}

	Key gg(byte[] at) throws Exception {
		byte[] ab = new byte[8];
		for (int i = 0; i < at.length && i < ab.length; i++) {
			ab[i] = at[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(ab, "DES");
		return key;
	}

	static String pp(byte[] test) {
		StringBuffer sb = new StringBuffer();
		for (byte b : test) {
			sb.append(b+",");
		}
		return sb.toString();
	}
	
//*
	public static void main(String[] args) {
		try {
			FileInputStream src = new FileInputStream(new File(args[0]));
			byte[] src_data = new byte[src.available()];
			src.read(src_data);
			src.close();
			FileOutputStream dst = new FileOutputStream(new File(args[1]));
			byte[] dst_data = new CC(args[2]).ee(src_data);
			dst.write(dst_data);
			dst.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
		}			
		System.exit(1);
	}
//*/
	
/*
	public static void main(String[] args) 
	{
		try {
			FileInputStream fis = new FileInputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system.jar"));
			byte[] data = new byte[fis.available()];
			fis.read(data);
			fis.close();
			
			CC des = new CC("");
			
			System.out.println("read " + data.length + " bytes");
			{
				long start_time = System.currentTimeMillis();
				data = des.ee(data);
				System.out.println("enc to " + data.length + " bytes");
				long end_time = System.currentTimeMillis();
				System.out.println("use " + (end_time - start_time) + " ms");
		
				FileOutputStream fos = new FileOutputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system_enc.jar"));
				fos.write(data);
				fos.close();
			}
			{
				long start_time = System.currentTimeMillis();
				data = des.dd(data);
				System.out.println("dec to " + data.length + " bytes");
				long end_time = System.currentTimeMillis();
				System.out.println("use " + (end_time - start_time) + " ms");
				
				FileOutputStream fos = new FileOutputStream(new File("D:/EatWorld/trunk/eatworld/data/edit/lib/system_dec.jar"));
				fos.write(data);
				fos.close();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}			
		System.exit(1);
	}
//*/
	
}
