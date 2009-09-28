package com.cell.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import com.cell.CIO;
import com.cell.CObject;


/**
 * MD5的算法在RFC1321 中定义
 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 * 
 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 * 
 */
public class MD5
 {

	final public static int COVERT_TYPE_MD5 = 5;
	
	final public static int COVERT_TYPE_MD6 = 6;
	
	
	public static final String getMD5cyc(String str) {
		return getMD6(str);
	}
	
	public static final String getMD5cyc(byte[] data) {
		return getMD6(data);
	}
	
	public static final String getMDX(byte[] data, int type) {
		if (type==COVERT_TYPE_MD5) {
			return getMD5(data);
		}else if (type==COVERT_TYPE_MD6) {
			return getMD5cyc(data);
		}else{
			return null;
		}
	}
	
	public static final String getMDX(String data, int type) {
		if (type==COVERT_TYPE_MD5) {
			return getMD5(data);
		}else if (type==COVERT_TYPE_MD6) {
			return getMD5cyc(data);
		}else{
			return null;
		}
	}
	
//	------------------------------------------------------------------------------------------------------------------------------


	public static void main(String args[]) 
    {
		try {
			
			String usage = 
				"***********************************************************************\n" +
				"* Old functions                                                       *\n" +
				"***********************************************************************\n" +
				"-md5(-md6)(:v)\n" +
				" output type md5 or md6.\n" +
				" :v 是否在最后显示原,例如:\n" +
				" 输出的字符为\"103a813a4961ceaeebe1af7866a2d124 : ./reg2.png\"那么\".reg2.png\"为原\n" +
				" 如果不加:v,那么输出格式为\"103a813a4961ceaeebe1af7866a2d124\"\n" +
				"\n" + 
				"-md5(-md6)(:v) -txt:<字符串>\n" +
				" 输入字符串,输出该字符串的md5(md6)值\n" +
				"\n" +
				"-md5(-md6)(:v) -f:<文件名>\n" +
				" 输入二进制文件,输出该文件的md5(md6)值\n" +
				"\n" +
				"-md5(-md6)(:v) -fn:<文本文件名> (-enc:<可选,文本编码,默认为ASCII>) (-fout:<可选,输出到文件,默认到控制台>)\n" +
				" 输入文本文件,输出文本文件的每行的md5(md6)值\n" +
				"\n" + 
				"-md5(-md6)(:v) -dir:<文件夹名> (-fout:<可选,输出到文件,默认到控制台>)\n" +
				" 输入一个文件夹,输出该文件夹内所有文件的md5(md6)值\n" +
				" 忽略文件夹和输出文件和隐藏文件\n" +
				"\n" +
				"-md5(-md6)(:v) -cmp:<文件(文件夹)名> (-fout:<可选,输出到文件,默认到控制台>)\n" +
				" 输入一个文件(文件夹),输出一个md5(md6)值,一般用于比较2个文件(文件夹)内容是否相等\n" +
				"\n" + 
				
				"***********************************************************************\n" +
				"* New functions  所有方法将全局忽略输出文件和隐藏文件                          *\n" +
				"***********************************************************************\n" +
				"--md5(--md6)\n" +
				"	使用md5或md6方式\n\n" +
				
				"-verbos\n" +
				"	是否连同原一起输出\n\n" +
				
				"-srcText:<字符串>\n" +
				"	输入字符串\n\n" +
				
				"-srcTextFile:<文件名>\n" +
				"	输入文本文件,计算每行的md5值\n\n" +
				
				"-srcTextFileEnc:<编码方式>\n" +
				"	输入文件的编码方式\n\n" +
				
				"-srcFile:<文件名>\n" +
				"	输入文件,如果为目录的话,将计算该目录下每个文件的md5值\n\n" +
				
				"-dstFile:<输出文件名>\n" +
				"	输出文件名,将输出文本文件\n\n" +
				
				"-dstAppend\n" +
				"	是否附加到输出文件的末尾\n\n" +
				
				"-dstEnc:<编码方式>\n" +
				"	输出文件的编码方式\n\n" +
				"";
			
			
			if (args != null) 
			{
				if (args.length > 1 && args[0].startsWith("--"))
				{
					int CoverType		= 0;
					boolean IsOutputSrc	= false;
					
					String	srcText		= null;
					File	srcFile		= null;
					File	srcTextFile	= null;
					String	srcTextFileEncoding	= null;
					
					File 	dstFile		= null;
					boolean	dstAppend	= false;
					String	dstEncoding	= null;
					
					for (int i=0; i<args.length; i++) 
					{
						if (args[i].toLowerCase().startsWith("--md5")){
							CoverType = COVERT_TYPE_MD5;
						}
						else if (args[i].toLowerCase().startsWith("--md6")) {
							CoverType = COVERT_TYPE_MD6;
						}
						else if (args[i].toLowerCase().startsWith("-verbos")){
							IsOutputSrc = true;
						}
						
						else if (args[i].toLowerCase().startsWith("-srctext:")){
							srcText = args[i].substring("-srctext:".length());
						}
						else if (args[i].toLowerCase().startsWith("-srcfile:")){
							srcFile = new File(args[i].substring("-srcfile:".length()));
						}
						else if (args[i].toLowerCase().startsWith("-srctextfile:")){
							srcTextFile = new File(args[i].substring("-srctextfile:".length()));
						}
						else if (args[i].toLowerCase().startsWith("-srctextfileenc:")){
							srcTextFileEncoding = args[i].substring("-srctextfileenc:".length());
						}
						
						else if (args[i].toLowerCase().startsWith("-dstfile:")){
							dstFile = new File(args[i].substring("-dstfile:".length()));
						}
						else if (args[i].toLowerCase().startsWith("-dstappend")){
							dstAppend = true;
						}
						else if (args[i].toLowerCase().startsWith("-dstenc:")){
							dstEncoding = args[i].substring("-dstenc:".length());
						}
					}
					
					// process
					{
						String output = "";
						
						if (srcText!=null) 
						{
							String dst = getMDX(srcText, CoverType) + (IsOutputSrc ? " : " + srcText : "");
							System.out.println(dst);
							output += dst + "\n";
						}
						
						if (srcFile!=null) 
						{
							if (!srcFile.exists())
							{
								System.err.println("src file is not exist!");
							}
							else if (srcFile.isDirectory()) 
							{
								File[] files = srcFile.listFiles();
								
								for (int l=0; l<files.length; l++)
								{
									if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(dstFile)){
										continue;
									}
									FileInputStream fis = new FileInputStream(files[l]);
									byte[] data = CIO.readStream(fis);
									String dst = MD5.getMDX(data, CoverType) + (IsOutputSrc ? " : " + files[l].getPath() : "");
									System.out.println(dst);
									output += dst + "\n";
								}
							}
							else
							{
								FileInputStream fis = new FileInputStream(srcFile);
								byte[] data = CIO.readStream(fis);
								String dst = getMDX(data, CoverType) + (IsOutputSrc ? " : " + srcFile.getPath() : "");
								System.out.println(dst);
								output += dst + "\n";
							}
						}
						
						if (srcTextFile!=null) 
						{
							if (!srcTextFile.exists()) 
							{
								System.err.println("src textfile is not exist!");
							}
							else if (srcTextFile.isDirectory())
							{
								System.err.println("src textfile can not be a directory!!");
							}
							else 
							{
								FileInputStream fis = new FileInputStream(srcTextFile);
								byte[] data = CIO.readStream(fis);
								String text = null;
								if (srcTextFileEncoding!=null) {
									text = new String(data, srcTextFileEncoding);
								}else{
									text = new String(data);
								}
								String[] lines = text.split("\n");
								
								for (int l=0; l<lines.length; l++)
								{
									String src = lines[l].trim();
									String dst = getMDX(src, CoverType)+(IsOutputSrc ? " : " + src : "");
									System.out.println(dst);
									output += dst + "\n";
								}
							}
						}
						
						if (dstFile!=null) 
						{
							if (dstFile.exists()==false) {
								dstFile.createNewFile();
								dstAppend = false;
							}
							
							FileOutputStream fos = new FileOutputStream(dstFile, dstAppend);
							
							try {
								if (dstEncoding == null) {
									fos.write(output.getBytes());
								} else {
									fos.write(output.getBytes(dstEncoding));
								}
								fos.flush();
							} catch (Exception err) {
								err.printStackTrace();
							} finally {
								fos.close();
							}
							
						}
						
						
					}
					
					return;
				}
				
				
				
				
				
				
				else if (args.length > 1 && args[0].length()>="-md5".length())
				{
					boolean isShowSrc = false;
					if (args[0].length()>"-md5:".length()) {
						String p = args[0].substring("-md5:".length());
						if (p.startsWith("v")) isShowSrc = true;
					}
					
					if (args[1].startsWith("-txt:")) 
					{
						String src = args[1].substring("-txt:".length());
						
						if (args[0].startsWith("-md5")) {
							System.out.println(MD5.getMD5(src)
									+(isShowSrc ? " : " + src : "")
									);
						} else {
							System.out.println(MD5.getMD5cyc(src)
									+(isShowSrc ? " : " + src : "")
									);
						}
						return;
					} 
					else if (args[1].startsWith("-f:")) 
					{
						File file = new File(args[1].substring("-f:".length()));
						FileInputStream fs = new FileInputStream(file);
						byte[] data = CIO.readStream(fs);

						if (args[0].startsWith("-md5")) {
							System.out.println(MD5.getMD5(data) + (isShowSrc ? " : " + file.getPath() : ""));
						} else {
							System.out.println(MD5.getMD5cyc(data) + (isShowSrc ? " : " + file.getPath() : ""));
						}
						return;
					} 
					else if (args[1].startsWith("-fn:"))
					{
						File file = new File(args[1].substring("-fn:".length()));
						FileInputStream fs = new FileInputStream(file);
						byte[] data = CIO.readStream(fs);
						
						String str = "";
						String enc = null;
						
						if (args.length > 2 && args[2].startsWith("-enc:")){
							enc = args[2].substring("-enc:".length());
							str = new String(data, enc);
						}else{
							str = new String(data);
						}
						
						String[] lines = str.split("\n");
						
						String outputs = "";
						
						for (int l=0; l<lines.length; l++)
						{
							String src = lines[l].trim();
							String dst = "";
							if (args[0].startsWith("-md5")) {
								dst = MD5.getMD5(src)+(isShowSrc ? " : " + src : "");
							} else {
								dst = MD5.getMD5cyc(src)+(isShowSrc ? " : " + src : "");
							}
							System.out.println(dst);
							outputs += dst + "\n";
						}
						
						String outfile = null;
						if ((enc!=null && args.length > 3 && args[3].startsWith("-fout:"))){
							outfile = args[3].substring("-fout:".length());
						}
						else if ((enc==null && args.length > 2 && args[2].startsWith("-fout:"))){
							outfile = args[2].substring("-fout:".length());
						}
						if (outfile!=null) {
							File fout = new File(outfile);
							fout.createNewFile();
							FileOutputStream fos = new FileOutputStream(fout);
							if (enc==null){
								fos.write(outputs.getBytes());
							}else{
								fos.write(outputs.getBytes(enc));
							}
							fos.flush();
							fos.close();
						}
						
						return;
					}
					else if (args[1].startsWith("-dir:"))
					{
						File dir = new File(args[1].substring("-dir:".length()));
						File[] files = dir.listFiles();
						
						File fout = null;
						if (args.length > 2 && args[2].startsWith("-fout:")){
							fout = new File(args[2].substring("-fout:".length()));
						}
						
						String outputs = "";
						
						for (int l=0; l<files.length; l++)
						{
							if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(fout)){
								continue;
							}
							
							FileInputStream fs = new FileInputStream(files[l]);
							byte[] data = CIO.readStream(fs);
							
							String dst = "";
							if (args[0].startsWith("-md5")) {
								dst = MD5.getMD5(data)+(isShowSrc ? " : " + files[l].getPath() : "");
							} else {
								dst = MD5.getMD5cyc(data)+(isShowSrc ? " : " + files[l].getPath() : "");
							}
							System.out.println(dst);
							outputs += dst + "\n";
						}
						
						if (fout!=null) {
							fout.createNewFile();
							FileOutputStream fos = new FileOutputStream(fout);
							fos.write(outputs.getBytes());
							fos.flush();
							fos.close();
						}
						
						return;
						
					}
					
					else if (args[1].startsWith("-cmp:"))
					{
						File cmp = new File(args[1].substring("-cmp:".length()));
						
						File fout = null;
						if (args.length > 2 && args[2].startsWith("-fout:")){
							fout = new File(args[2].substring("-fout:".length()));
						}
						
						String outputs = "";
						
						if (cmp.isDirectory())
						{
							File[] files = cmp.listFiles();
							
							for (int l=0; l<files.length; l++)
							{
								if (files[l].isHidden() || files[l].isDirectory() || files[l].equals(fout)){
									continue;
								}
								
								FileInputStream fs = new FileInputStream(files[l]);
								byte[] data = CIO.readStream(fs);
								
								if (args[0].startsWith("-md5")) {
									outputs += MD5.getMD5(data);
								} else {
									outputs += MD5.getMD5cyc(data);
								}
							}
							
							if (args[0].startsWith("-md5")) {
								outputs = MD5.getMD5(outputs);
							} else {
								outputs = MD5.getMD5cyc(outputs);
							}
						}
						else 
						{
							FileInputStream fs = new FileInputStream(cmp);
							byte[] data = CIO.readStream(fs);

							if (args[0].startsWith("-md5")) {
								outputs += MD5.getMD5(data);
							} else {
								outputs += MD5.getMD5cyc(data);
							}
						}
						
						outputs += (isShowSrc ? " : " + cmp.getPath() : "");
						
						System.out.println(outputs);
						
						if (fout!=null) {
							fout.createNewFile();
							FileOutputStream fos = new FileOutputStream(fout);
							fos.write(outputs.getBytes());
							fos.flush();
							fos.close();
						}
						
						return;
						
					}
				}
			}
			
			System.out.println(usage);
			
			
		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	
//	------------------------------------------------------------------------------------------------------------------------------

	// 用来将字节转换成 16 进制表示的字符
	final static char hexDigits[] = { 
			'0', '1', '2', '3', 
			'4', '5', '6', '7',
			'8', '9', 'a', 'b', 
			'c', 'd', 'e', 'f' };
	
	private static String md5(byte[] source)
	{
		String s = null;

		try
		{
			java.security.MessageDigest md = java.security.MessageDigest .getInstance("MD5");
			
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	
	}
	
	public static String getMD5(byte[] source)
	{
		return md5(source);
	}
	
    public static String getMD5(String str) {
    	return md5(str.getBytes());
    }
    
    public static String getMD5(String str, String encode) {
    	try {
			return md5(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    
    private static String md6(byte[] source)
    {
    	String str = md5(source);
    	
    	if (str!=null) 
    	{
    		String str_ret = "";
    		int count = str.length();
    		if (count == 32) {
    			for (int i = 0; i < count; i += 2) {
    				str_ret += str.charAt(i);
    			}
    			for (int i = 1; i < count; i += 2) {
    				str_ret += str.charAt(i);
    			}
    		} 
    		return str_ret;
    	}
    	
    	return str;
    }
    
	public static String getMD6(byte[] source){
		return md6(source);
	}
	
    public static String getMD6(String str) {
    	return md6(str.getBytes());
    }
    
    public static String getMD6(String str, String encode) {
    	try {
			return md6(str.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    
    }
	
}

