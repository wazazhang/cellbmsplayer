
package com.cell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Vector;

import com.cell.reflect.Parser;



/**
 * utility string or char function</br>
 * @author yifeizhang 张翼飞
 * @since 2006-12-1 
 * @version 1.0
 */
@SuppressWarnings("unused")
public class CUtil extends CObject
{
	@SuppressWarnings("unchecked")
	public static<T> T cloneObject(T src)
	{
		if (src == null) {
			return src;
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(baos);
			os.writeObject(src);

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream is = new ObjectInputStream(bais);
			T dst = (T) is.readObject();
			
			os.close();
			is.close();
			
			baos	= null;
			os		= null;
			bais	= null;
			is		= null;
			
			return dst;
		} 
		catch (Throwable err) {
			err.printStackTrace();
		}
		return null;
	}
	
	public static void printError(String message) {
		try{
			throw new Exception(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

//	--------------------------------------------------------------------------------------------------------------------------
//	Array
//	--------------------------------------------------------------------------------------------------------------------------
	
	private static void ARRAY_START(){}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static <T> void arrayCopy(
			T[] src, int src_pos, 
			T[] dst, int dst_pos, 
			int length) {
		System.arraycopy(src, src_pos, dst, dst_pos, length);
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int arrayFirstIndexOf(int[] array, int value) 
	{
		for (int i=0; i<array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static <T> boolean arrayEquals(T[] array1, T[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i].equals(array2[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> boolean arrayEquals(List<T> array1, List<T> array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.size() != array2.size()) {
			return false;
		}
		for (int i = 0; i < array1.size(); i++) {
			if (array1.get(i).equals(array2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	
	public static boolean arrayEquals(byte[] array1, byte[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	public static boolean arrayEquals(short[] array1, short[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	public static boolean arrayEquals(int[] array1, int[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	public static boolean arrayEquals(long[] array1, long[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	public static boolean arrayEquals(float[] array1, float[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean arrayEquals(double[] array1, double[] array2) {
		if (array1 == array2) {
			return true;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == (array2[i])) {
				return false;
			}
		}
		return true;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	public static<T> T[] stringToArray(String text, String separator, Class<T> cls) {
		text = text.trim();
		if (text.endsWith(separator)) {
			text = text.substring(0, text.length() - separator.length());
		}
		String[] splits = CUtil.splitString(text, separator);
		T[] ret = (T[])Array.newInstance(cls, splits.length);
		for (int i=0; i<splits.length; i++) {
			String v = splits[i].trim();
			ret[i] = Parser.stringToObject(v, cls);
		}
		return ret;
	}
	
	public static<T> ArrayList<T> stringToArrayList(String text, String separator, Class<T> cls) {
		text = text.trim();
		if (text.endsWith(separator)) {
			text = text.substring(0, text.length() - separator.length());
		}
		String[] splits = CUtil.splitString(text, separator);
		ArrayList<T> ret = new ArrayList<T>(splits.length);
		for (int i=0; i<splits.length; i++) {
			String v = splits[i].trim();
			ret.add(Parser.stringToObject(v, cls));
		}
		return ret;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static <T> String arrayToString(List<T> array, String separator, String end) {
		if (array == null) return "null";
		StringBuffer sb = new StringBuffer();
		int count = array.size();
		for (int i = 0; i < count; i++) {
			if (i != count - 1) {
				sb.append(array.get(i) + separator);
			} else {
				sb.append(array.get(i) + end);
			}
		}
		return sb.toString();
	}
	
	public static<T> String arrayToString(T[] array, String separator, String end){
		if (array == null) return "null";
		StringBuffer sb = new StringBuffer();
		int count = array.length;
		for (int i = 0; i < count; i++) {
			if (i != count - 1) {
				sb.append(array[i] + separator);
			} else {
				sb.append(array[i] + end);
			}
		}
		return sb.toString();
	}

	public static<T> String arrayToString(List<T> array, String separator){
		return arrayToString(array, separator, separator);
	}
	
	public static <T> String arrayToString(T[] array, String separator) {
		return arrayToString(array, separator, separator);
	}

	public static<T> String arrayToString(List<T> array){
		return arrayToString(array, ",");
	}
	
	public static<T> String arrayToString(T[] array){
		return arrayToString(array, ",");
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static String arrayToString(byte[] array){
		if (array==null) return "null";
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<array.length; i++) {
			sb.append(array[i]+",");
		}
		return sb.toString();
	}
	
	public static String arrayToString(short[] array){
		if (array==null) return "null";
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<array.length; i++) {
			sb.append(array[i]+",");
		}
		return sb.toString();
	}
	
	public static String arrayToString(int[] array){
		if (array==null) return "null";
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<array.length; i++) {
			sb.append(array[i]+",");
		}
		return sb.toString();
	}
	
	public static String arrayToString(long[] array){
		if (array==null) return "null";
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<array.length; i++) {
			sb.append(array[i]+",");
		}
		return sb.toString();
	}
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public static<T> String[] arrayToStringArray(List<T> array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.size()];
			for (int i=array.size()-1; i>=0; --i) {
				arrays[i] = "" + array.get(i);
			}
			return arrays;
		}
	}
	
	public static<T> String[] arrayToStringArray(T[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(byte[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(short[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(int[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(long[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(float[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
	public static String[] arrayToStringArray(double[] array){
		if (array==null) return null;
		synchronized (array) {
			String[] arrays = new String[array.length];
			for (int i=array.length-1; i>=0; --i) {
				arrays[i] = "" + array[i];
			}
			return arrays;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public static <T> T[] arrayCovert(Object[] src, T[] dst) {
		for (int i=src.length-1; i>=0; --i) {
			dst[i] = (T)src[i];
		}
		return dst;
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final int[] toIntArray(Collection<Integer> array) {
		if (array == null) {
			return null;
		}
		return toIntArray(array.toArray(new Integer[array.size()]));
	}
	
	public static final int[] toIntArray(Integer[] array) {
		if (array == null) {
			return null;
		}
		int[] ints = new int[array.length];
		for (int qi=0; qi<array.length; qi++) {
			ints[qi] = array[qi];
		}
		return ints;
	}
	public static final Integer[] toIntArray(int[] array) {
		if (array == null) {
			return null;
		}
		Integer[] ints = new Integer[array.length];
		for (int qi=0; qi<array.length; qi++) {
			ints[qi] = array[qi];
		}
		return ints;
	}
	
	public static final ArrayList<Integer> toIntCollection(int ... array) {
		ArrayList<Integer> ret = new ArrayList<Integer>(array.length);
		for (int i : array) {
			ret.add(i);
		}
		return ret;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int[] arrayAddElement(int[] array, int e, boolean isDuplicate){
		if (array == null){
			int[] na = new int[1];
			na[0] = e;
			return na;
		}
		if (!isDuplicate){
			for (int o:array){
				if (0==e)return array;
			}
		}
		int[] na = new int[array.length+1];
		System.arraycopy(array, 0, na, 0, array.length);
		na[array.length] = e;
		return na;
	}
	
	public static int[] arrayRemoveElement(int[] array, int e){
		if (array == null){
			return new int[0];
		}
		int[] na = new int[array.length];
		int n = 0;
		for (int o:array){
			if (o!=e){
				na[n++] = o;
			}
		}
		int[] nb = new int[n];
		System.arraycopy(na, 0, nb, 0, n);
		return nb;
	}
	
	public static long[] arrayAddElement(long[] array, long e, boolean isDuplicate){
		if (array == null){
			long[] na = new long[1];
			na[0] = e;
			return na;
		}
		if (!isDuplicate){
			for (long o:array){
				if (0==e)return array;
			}
		}
		long[] na = new long[array.length+1];
		System.arraycopy(array, 0, na, 0, array.length);
		na[array.length] = e;
		return na;
	}
	
	public static long[] arrayRemoveElement(long[] array, long e){
		if (array == null){
			return new long[0];
		}
		long[] na = new long[array.length];
		int n = 0;
		for (long o:array){
			if (o!=e){
				na[n++] = o;
			}
		}
		long[] nb = new long[n];
		System.arraycopy(na, 0, nb, 0, n);
		return nb;
	}
	
	public static boolean arrayIsContain(int[] array, int e){
		for (int o:array){
			if (o==e){
				return true;
			}
		}
		return false;
	}
	
	public static boolean arrayIsContain(long[] array, long e){
		for (long o:array){
			if (o==e){
				return true;
			}
		}
		return false;
	}	

	private static void ARRAY_END(){}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static char FormatKey = '\b';
	
	public static void setDefaultFormatStringKey(char key){
		FormatKey = key;
	}
	
	public static String formatString(String src, String[] args)
	{
		return formatString(src, FormatKey, args);
	}
	
	/**
	 * formatString("abcde abcde",'a',new String[]{"A"}) == "Abcde abcde";
	 * formatString("abcde abcde",'a',new String[]{"A","ABC"}) == "Abcde ABCbcde";
	 * 
	 * @param src
	 * @param key
	 * @param args
	 * @return
	 */
	public static String formatString(String src, char key, String[] args)
	{
		if(args==null || args.length<=0)
		{
			return src;
		}
		else
		{
			int argIndex = 0;
			
			StringBuffer sb = new StringBuffer();
			int count = src.length();
			
			for (int i = 0; i < count; i++)
			{
				char c = src.charAt(i);
				
				if(c==key)
				{
					if(argIndex<args.length)
					{
						sb.append(args[argIndex]);
						argIndex++;
					}
					else if(i<count-1)
					{
						sb.append(src.substring(i+1));
					}
				}
				else
				{
					sb.append(c);
				}
			}
			
			return sb.toString();
		}
	}
	
	
	
	/**
	 * get [key : value] form str <\b> 
	 * 
	 * str = "FORCE=A"
	 * key = "FORCE="
	 * value = "A"
	 * 
	 * @param str
	 * @param key
	 * @param value
	 */
	static public String getStringValueFromKey(String str, String key)
	{
		int index = str.indexOf(key);
		if(index >= 0)
		{
			return str.substring(index + key.length());
		}
		else
		{
			return null;
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static String getHexDump(byte[] data)
	{
		String dump = "";
		for (int i=0; i<data.length; i++){
			if (i%16==0) dump += "\n";
			String hex = Integer.toString(data[i], 16);
			if (hex.length()<2){
				hex = "0"+hex;
			}
			dump += hex + " ";
		}
		return dump;
	}
	
	public static String toHex(byte[] data)
	{
		String dump = "";
		for (int i=0; i<data.length; i++){
			String hex = Integer.toString(0x00ff & data[i], 16);
			if (hex.length()<2){
				hex = "0"+hex;
			}
			dump += hex;
		}
		return dump;
	}
	
	public static byte[] fromHex(String text)
	{
		int size = text.length() / 2;
		byte[] data = new byte[size];
		for (int i=0; i<size; i++) {
			int v = Integer.parseInt(text.substring(i*2, i*2+2), 16);
			data[i] = (byte)v;
		}
		return data;
	}
	

	public static String getBytesSizeString(long bytes)
	{
		long b  = bytes;
		long kb = b>>10;
		long mb = kb>>10;
		long gb = mb>>10;
		long tb = gb>>10;
		
		if ( tb > 10) {
			return tb + "." + gb%1024 + "t";
		}
		if ( gb > 10) {
			return gb + "." + mb%1024 + "g";
		}
		if ( mb > 10) {
			return mb + "." + kb%1024 + "m";
		}
		if ( kb > 10) {
			return kb + "." + b%1024 + "k";
		}
		
		return b + "b";

	}
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	static public int toStripColorGR(int value, int max, int maxColor, int minColor)
	{
		int hp = Math.min(value, max);
		int halfWear = max / 2;
		
		int G = (hp >= halfWear) ? 0xff : (hp*2);
		int R = (hp <= halfWear) ? 0xff : ((max-hp)*2);

		int Color = 0xff000000 | 
		(((maxColor >>  0) & G) <<  0) | 
		(((maxColor >>  8) & G) <<  8) |
		(((maxColor >> 16) & G) << 16) |
		(((minColor >>  0) & R) <<  0) | 
		(((minColor >>  8) & R) <<  8) |
		(((minColor >> 16) & R) << 16) 
		;
		
		return Color;
	}
	
	static public String toColorKey(int argb) {
		String text = Long.toString(0x00000000ffffffff & argb, 16);
		if (text.startsWith("0x")) {
			return text.substring(2);
		}
		return text;
	}
	
	static public int toColor(int r, int g, int b)
	{
		return 0xff000000 | 
		((r>0xff?0xff:r)<<16) | 
		((g>0xff?0xff:g)<<8 ) | 
		((b>0xff?0xff:b))
		;
	}
	
	
	
	static public int toColor(int a, int r, int g, int b)
	{
		return 
		((a>0xff?0xff:a)<<24) |
		((r>0xff?0xff:r)<<16) | 
		((g>0xff?0xff:g)<<8 ) | 
		((b>0xff?0xff:b));
	}
	
	static public int toColor(int a, int rgb)
	{
		return 
		((a>0xff?0xff:a)<<24) | (0x00ffffff&rgb);
	}
	
	
	//-------------------------------------------------------------------------------------------------------------
	
	static StringCompare default_string_compare = new StringCompare();
	
	static public interface ICompare<T1,T2>
	{
		/**
		 * @param a i
		 * @param b i+1
		 * @return if ret < 0  q[b] -> q[a]
		 */
		public int compare(T1 a, T2 b);
	}
	
	static public class StringCompare implements ICompare<String, String>, Comparator<String>
	{
		public int compare(String a, String b) {
			int len = Math.min(a.length(), b.length());
			for (int i=0; i<len; i++) {
				char ca = a.charAt(i);
				char cb = b.charAt(i);
				if (ca != cb) {
					return cb - ca;
				}
			}
			return b.length() - a.length();
		}
	}
	
	static public StringCompare getStringCompare() {
		return default_string_compare;
	}
	
//	public static void main(String[] args)
//	{
//		StringCompare sc = new StringCompare();
//		
//		String[] array = new String[] { 
//				"a", "b", "e", "c", "f", "d", 
//				"ab", "ac", "bb", "eb", };
//		sort(array, sc);
//		
//		System.out.println(arrayToString(array));
//	}
	
	/**
	 * current sort rule is popo sort
	 * @param list
	 * @param compare
	 */
	static public <T> T[] sort(T list[], ICompare<T,T> compare){
		T temp;
		boolean tag = true;
		for (int i = list.length - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if(compare.compare(list[j],list[j + 1])<0){
					temp = list[j];
					list[j] = list[j + 1];
					list[j + 1] = temp;
					tag = true;
				}
			}
			if(tag==false)break;
		}
		return list;
	}

	
	/**
	 * current sort rule is popo sort
	 * @param list
	 * @param compare
	 */
	static public<T> List<T> sort(List<T> list, ICompare<T,T> compare){
		T temp2;
		T temp1;
		boolean tag = true;
		for (int i = list.size() - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if(compare.compare(list.get(j),list.get(j + 1))<0){
					temp1 = list.get(j);
					temp2 = list.get(j + 1);
					list.set(j, temp2);
					list.set(j + 1, temp1);
					tag = true;
				}
			}
			if(tag==false)break;
		}
		return list;
	}
	//-------------------------------------------------------------------------------------------------------------
	
//	static public void main(String args[]) {
//		System.out.println(link(new byte[]{1,2,3,4}, new byte[]{5,6,7,8,}, new byte[]{10,11,12,} ));
//		
//		
//	}
	
	static public int[] link(int[]... arrays){
		int count = 0;
		for (int[] array : arrays) {
			if (array!=null) count += array.length;
		}
		int[] ret = new int[count];
		count = 0;
		for (int[] array : arrays) {
			if (array!=null) {
				System.arraycopy(array, 0, ret, count, array.length);
				 count += array.length;
			}
		}
		return ret;
	}
	
	static public short[] link(short[]... arrays){
		int count = 0;
		for (short[] array : arrays) {
			if (array!=null) count += array.length;
		}
		short[] ret = new short[count];
		count = 0;
		for (short[] array : arrays) {
			if (array!=null) {
				System.arraycopy(array, 0, ret, count, array.length);
				 count += array.length;
			}
		}
		return ret;
	}
	
	static public byte[] link(byte[]... arrays){
		int count = 0;
		for (byte[] array : arrays) {
			if (array!=null) count += array.length;
		}
		byte[] ret = new byte[count];
		count = 0;
		for (byte[] array : arrays) {
			if (array!=null) {
				System.arraycopy(array, 0, ret, count, array.length);
				 count += array.length;
			}
		}
		return ret;
	}
	
	static public Object[] link(Object[] ... arrays){
		int count = 0;
		for (Object[] array : arrays) {
			if (array!=null) count += array.length;
		}
		Object[] ret = new Object[count];
		count = 0;
		for (Object[] array : arrays) {
			if (array!=null) {
				System.arraycopy(array, 0, ret, count, array.length);
				 count += array.length;
			}
		}
		return ret;
	} 
	
	static public<T> Vector<T> linkv(T[] ...arrays){
		int count = 0;
		for (Object[] array : arrays) {
			if (array!=null) count += array.length;
		}
		Vector<T> retv = new Vector<T>(count);
		for (T[] array : arrays) {
			if (array!=null) {
				for (T o : array) {
					retv.addElement(o);
				}
			}
		}
		return retv;
	}

	
	//-------------------------------------------------------------------------------------------------------------
	
	static public String toHexString(byte[] data)
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<data.length;i++){
			if(i>0 && i%16==0)sb.append('\n');
			String ch = Integer.toString(0x000000ff & data[i], 16);
			if(ch.length()<2) ch = "0" + ch;
			sb.append(ch);
		}
		return sb.toString();
	}
	
	
	/**
	 * @param i
	 * @param radix
	 * @param len bits
	 * @return
	 */
	static public String intToSting(int i, int radix, int len, String before){
		String str = Integer.toString(i,radix);
		if(str.length()>len){
			str = str.substring(str.length()-len);
		}
		else if(str.length()<len){
			int l = len-str.length();
			for(int d=0;d<l;d++){
				str = before + str;
			}
		}
		return str;
	}
	

	static public String getSameCharBlock(String src, int start, char ch)
	{
		String str = "";
		
		for (int i = src.indexOf(ch, start); i>=0 && i < src.length(); i++){
			if(src.charAt(i)==ch){
				str += ch;
			}else{
				break;
			}
		}
		
		return str;
	}
	
	/**
	 * @param text
	 * @param s
	 * @param d
	 * @return
	 */
	static public String replaceString(String text, String s, String d, int limit)
	{
		int count = 0;
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < text.length(); i++)
		{
			if (count < limit) {
				int dst = text.indexOf(s, i);
				if (dst >= 0) {
					sb.append(text.substring(i, dst) + d);
					i = dst + s.length() - 1;
					count++;
				} else {
					sb.append(text.substring(i));
					break;
				}
			} else {
				sb.append(text.substring(i));
				break;
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * @param text
	 * @param s
	 * @param d
	 * @return
	 */
	static public String replaceString(String text, String s, String d)
	{
		return replaceString(text, s, d, Integer.MAX_VALUE);
	}
	
	/**
	 * @author 
	 * split string 
	 * @param text
	 * @param separator
	 * @return
	 */
	static public String[] splitString(String text, String separator, int limit) {
		int count = 0;
		try {
			Vector<String> lines = new Vector<String>();
			for (int i = 0; i < text.length(); i++) {
				if (count < limit) {
					int dst = text.indexOf(separator, i);
					if (dst >= 0) {
						lines.addElement(text.substring(i, dst));
						i = dst + separator.length() - 1;
						count ++;
					} else {
						lines.addElement(text.substring(i, text.length()));
						break;
					}
				} else {
					lines.addElement(text.substring(i, text.length()));
					break;
				}
			}
			String[] texts = new String[lines.size()];
			lines.copyInto(texts);
			lines = null;
			return texts;
		} catch (Exception err) {
			err.printStackTrace();
			return new String[] { "(Error !)" };
		}
	}
	
	static public String[] splitString(String text, String separator) {
		return splitString(text, separator, Integer.MAX_VALUE);
	}
	

	
	
	
	
    /**
     * 
     * @param src
     * @param des
     * @param start
     * @return
     */
    public static int charArrayIndexOf(char[] src,char des,int start){
        for(int i=start;i<src.length;i++){
            if(src[i] == des)return i;
        }
        return -1;
    }
    
    /**
     * 
     * @param src
     * @param des
     * @param start
     * @return
     */
    public static int charArrayIndexOf(char[] src,char[] des,int start){
        for(int i=start;i<src.length;i++){
            if(charArrayCMP(src,start,des.length,des,0,des.length))return i;
        }
        return -1;
    }
    
    /**
     * 
     * @param src
     * @param des
     * @param start
     * @return
     */
    public static int charArrayIndexOf(char[] src,char des,int start,int len){
        for(int i=start;i<start+len;i++){
            if(src[i] == des)return i;
        }
        return -1;
    }
    
    /**
     * 
     * @param src
     * @param des
     * @param start
     * @return
     */
    public static int charArrayIndexOf(char[] src,char[] des,int start,int len){
        for(int i=start;i<start+len;i++){
            if(charArrayCMP(src,start,des.length,des,0,des.length))return i;
        }
        return -1;
    }
    
    /**
     * 
     * @param src
     * @param des
     * @return
     */
    public static boolean charArrayCMP(char[] src,char[] des){
        if(src.length != des.length)return false;
        for(int i=src.length-1;i>=0;i--){
            if(src[i] != des[i])return false;
        }
        return true;
    }
    
    /**
     * 
     * @param src
     * @param src_start
     * @param src_len
     * @param des
     * @param des_start
     * @param des_len
     * @return
     */
    public static boolean charArrayCMP(
            char[] src,int src_start,int src_len,
            char[] des,int des_start,int des_len){
        if(src_len != des_len)return false;
        if(src_start+src_len>src.length)return false;
        if(des_start+des_len>des.length)return false;
        for(int i=src_len-1;i>=0;i--){
            if(src[src_start+i] != des[des_start+i])return false;
        }
        return true;
    }

    /**
     * <summary> 
     * \r 
     * \n  
     * \f 
     * \t 
     * \b 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsBlank(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch <= ' ') {
                return value;
            }else{
                value++;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsNotWordNum(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                return value;
            }else{
                value++;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsWord(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsWordNum(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsDigitHex(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'f' ||
                ch >= 'A' && ch <= 'F'  ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayIsDigit(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str[i];
            if (ch >= '0' && ch <= '9') {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayHexToInt(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            value=value*0x10;
            switch(str[i]){
            case '0':value+=0x00;break;
            case '1':value+=0x01;break;
            case '2':value+=0x02;break;
            case '3':value+=0x03;break;
            case '4':value+=0x04;break;
            case '5':value+=0x05;break;
            case '6':value+=0x06;break;
            case '7':value+=0x07;break;
            case '8':value+=0x08;break;
            case '9':value+=0x09;break;
            case 'a':case 'A':value+=0x0a;break;
            case 'b':case 'B':value+=0x0b;break;
            case 'c':case 'C':value+=0x0c;break;
            case 'd':case 'D':value+=0x0d;break;
            case 'e':case 'E':value+=0x0e;break;
            case 'f':case 'F':value+=0x0f;break;
            default:return value;
            }
        }
        return value;
    }

    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayDigitToInt(char[] str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            value=value*10;
            switch(str[i]){
            case '0':value+=0x00;break;
            case '1':value+=0x01;break;
            case '2':value+=0x02;break;
            case '3':value+=0x03;break;
            case '4':value+=0x04;break;
            case '5':value+=0x05;break;
            case '6':value+=0x06;break;
            case '7':value+=0x07;break;
            case '8':value+=0x08;break;
            case '9':value+=0x09;break;
            default:return value;
            }
        }
        return value;
    }

    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int charArrayDecimalToInt(char[] str,int start,int len,int mul){
        int v = 0;
        int f = 0;
        int m = mul;
        boolean p = false;
        
        for(int i=start;i<start+len;i++){
            int bit = -1;
            switch(str[i]){
            case '0':bit=0;break;
            case '1':bit=1;break;
            case '2':bit=2;break;
            case '3':bit=3;break;
            case '4':bit=4;break;
            case '5':bit=5;break;
            case '6':bit=6;break;
            case '7':bit=7;break;
            case '8':bit=8;break;
            case '9':bit=9;break;
            case '.':
                if(!p){
                    p=true;
                    bit=0;
                }
                break;
            default:
                break;
            }
            if(bit<0)break;
            if(p){
                f+=bit*m;
                m=m/10;
            }else{
                v+=bit;
                v=v*10;
            }
        }
        v=v/10;
//        println("v="+v+" f="+f);
        return v*mul+f;
    }
    
    
//--------------------------------------------------------------------------------------------------------    

    /**
     * 将等宽字符向左对齐，并且长度维持在 length
     * @param src
     * @param length
     * @return
     */
    public static String snapStringRightSize(String src, int length, char blank) 
    {
    	if (src.length()<length) {
    		for (int i=length-src.length()-1; i>=0; --i) {
    			src += blank;
    		}
    	}
    	return src;
    }
    
    /**
     * 将等宽字符向左对齐，并且长度维持在 length
     * @param src
     * @param length
     * @return
     */
    public static String snapStringRL(Object[] left, Object[] right, char blank, String split) 
    {
    	int lc = 0;
    	for (Object o : left) {
    		lc = Math.max(lc, o.toString().length());
    	}
    	StringBuilder sb = new StringBuilder();
    	for (int i=0; i<left.length; i++) {
    		int lb = lc - left[i].toString().length();
    		for (int b=0; b<lb; b++) {
    			sb.append(blank);
    		}
    		sb.append(left[i] + split + right[i] + "\n");
    	}
    	return sb.toString();
    }
    
//--------------------------------------------------------------------------------------------------------    

    /**
     * <summary>  
     * \r  
     * \n  
     * \f  
     * \t  
     * \b  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringIsBlank(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch <= ' ') {
                return value;
            }else{
                value++;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringIsNotWordNum(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                return value;
            }else{
                value++;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringIsWord(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringIsWordNum(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'z' ||
                ch >= 'A' && ch <= 'Z' ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringIsDigitHex(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9' ||
                ch >= 'a' && ch <= 'f' ||
                ch >= 'A' && ch <= 'F'  ) {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * <summary>  
     * @param str
     * @param start
     * @param len
     * @return
     */
    public static int stringIsDigit(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                value++;
            }else{
                return value;
            }
        }
        return value;
    }
    
    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringHexToInt(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            value=value*0x10;
            switch(str.charAt(i)){
            case '0':value+=0x00;break;
            case '1':value+=0x01;break;
            case '2':value+=0x02;break;
            case '3':value+=0x03;break;
            case '4':value+=0x04;break;
            case '5':value+=0x05;break;
            case '6':value+=0x06;break;
            case '7':value+=0x07;break;
            case '8':value+=0x08;break;
            case '9':value+=0x09;break;
            case 'a':case 'A':value+=0x0a;break;
            case 'b':case 'B':value+=0x0b;break;
            case 'c':case 'C':value+=0x0c;break;
            case 'd':case 'D':value+=0x0d;break;
            case 'e':case 'E':value+=0x0e;break;
            case 'f':case 'F':value+=0x0f;break;
            default:return value;
            }
        }
        return value;
    }

    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringDigitToInt(String str,int start,int len){
        int value = 0;
        for(int i=start;i<start+len;i++){
            value=value*10;
            switch(str.charAt(i)){
            case '0':value+=0x00;break;
            case '1':value+=0x01;break;
            case '2':value+=0x02;break;
            case '3':value+=0x03;break;
            case '4':value+=0x04;break;
            case '5':value+=0x05;break;
            case '6':value+=0x06;break;
            case '7':value+=0x07;break;
            case '8':value+=0x08;break;
            case '9':value+=0x09;break;
            default:return value;
            }
        }
        return value;
    }
    
    /**
     * 
     * @param str
     * @param start
     * @param end
     * @return
     */
    public static int stringDecimalToInt(String str,int start,int len,int mul){
        int v = 0;
        int f = 0;
        int m = mul;
        boolean p = false;
        
        for(int i=start;i<start+len;i++){
            int bit = -1;
            switch(str.charAt(i)){
            case '0':bit=0;break;
            case '1':bit=1;break;
            case '2':bit=2;break;
            case '3':bit=3;break;
            case '4':bit=4;break;
            case '5':bit=5;break;
            case '6':bit=6;break;
            case '7':bit=7;break;
            case '8':bit=8;break;
            case '9':bit=9;break;
            case '.':
                if(!p){
                    p=true;
                    bit=0;
                }
                break;
            default:
                break;
            }
            if(bit<0)break;
            if(p){
                f+=bit*m;
                m=m/10;
            }else{
                v+=bit;
                v=v*10;
            }
        }
        v=v/10;
//        println("v="+v+" f="+f);
        return v*mul+f;
    }
    
    /**
     * <summary>  
     * @param src
     * @param key
     * @return
     */
	final static public int stringKeyValue(String src,String key){
		try{
			int pos = src.indexOf(key) + key.length();
			return Integer.parseInt(src.substring(pos, pos+stringIsDigit(src, pos, src.length()-pos)), 10);
		}catch(Exception err){
			return -1;
		}
	}
	
//----------------------------------------------------------------------------------------------------   
    
    /** 
     * <summary> save byte to data (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
    public static void write8(byte[] data, int p, byte val)
    {
        data[p++] = val;
    }
    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
    public static void write16(byte[] data, int p, short val)
    {
        data[p+0] = (byte)(0x00ff & (val >> 0 ));
        data[p+1] = (byte)(0x00ff & (val >> 8 ));
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
    public static void write24(byte[] data, int p, int val)
    {
        data[p+0] = (byte)(0x00ff & (val >> 0 ));
        data[p+1] = (byte)(0x00ff & (val >> 8 ));
        data[p+2] = (byte)(0x00ff & (val >> 16));
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
    public static void write32(byte[] data, int p, int val)
    {
        data[p+0] = (byte)(0x00ff & (val >> 0 ));
        data[p+1] = (byte)(0x00ff & (val >> 8 ));
        data[p+2] = (byte)(0x00ff & (val >> 16));
        data[p+3] = (byte)(0x00ff & (val >> 24));
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
    public static void write64(byte[] data, int p, long val)
    {
        data[p+0] = (byte)(0x00ff & (val >> 0 ));
        data[p+1] = (byte)(0x00ff & (val >> 8 ));
        data[p+2] = (byte)(0x00ff & (val >> 16));
        data[p+3] = (byte)(0x00ff & (val >> 24));
        data[p+4] = (byte)(0x00ff & (val >> 32));
        data[p+5] = (byte)(0x00ff & (val >> 40));
        data[p+6] = (byte)(0x00ff & (val >> 48));
        data[p+7] = (byte)(0x00ff & (val >> 56));
    }
    
    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param  
     * @param  
	 * @return  
	 */ 
    public static byte read8(byte[] data, int p)
    {
        return data[p++];
    }

    /** 
     * <summary> (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
    public static short read16(byte[] data, int p)
    {
        short val = (short)(
		        (data[p+0]<< 0)&0x00ff|
		        (data[p+1]<< 8)&0xff00
		);

        return val;
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */  
    public static int read24(byte[] data, int p)
    {
        int value = (
		        (data[p+0]<< 0)&0x000000ff|
		        (data[p+1]<< 8)&0x0000ff00|
		        (data[p+2]<<16)&0x00ff0000
		);
        return value;
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
    public static int read32(byte[] data, int p)
    {
        int value = (
		        (data[p+0]<< 0)&0x000000ff|
		        (data[p+1]<< 8)&0x0000ff00|
		        (data[p+2]<<16)&0x00ff0000|
		        (data[p+3]<<24)&0xff000000
		);
        return value;
    }

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
    public static long read64(byte[] data, int p)
    {
        long value = (
        		0|
		        (data[p+0]<< 0)|
		        (data[p+1]<< 8)|
		        (data[p+2]<<16)|
		        (data[p+3]<<24)|
		        (data[p+4]<<32)|
		        (data[p+5]<<40)|
		        (data[p+6]<<48)|
		        (data[p+7]<<56)
		);
        return value;
    }
    
    //-----------------------------------------------------------------------------------------------

    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
     * @param		 
     * @param		 
	 * @param   	 
	 */ 
	public static void writeMSB8(byte[] data,int p,byte  val)
	{
		data[p++] = val;
	}
    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
     * @param		 
     * @param		 
	 * @param   	 
	 */
	public static void writeMSB16(byte[] data,int p,short val)
	{
	    data[p+0] = (byte)(0x00ff & (val >> 8 ));
        data[p+1] = (byte)(0x00ff & (val >> 0 ));
	}
    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
     * @param		 
     * @param		 
	 * @param   	 
	 */
	public static void writeMSB24(byte[] data,int p,int val)
	{
	    data[p+0] = (byte)(0x00ff & (val >> 16));
        data[p+1] = (byte)(0x00ff & (val >> 8 ));
        data[p+2] = (byte)(0x00ff & (val >> 0 ));
	}
    /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
     * @param		 
     * @param		 
	 * @param   	 
	 */
	public static void writeMSB32(byte[] data,int p,int val)
	{
	    data[p+0] = (byte)(0x00ff & (val >> 24));
        data[p+1] = (byte)(0x00ff & (val >> 16));
        data[p+2] = (byte)(0x00ff & (val >> 8 ));
        data[p+3] = (byte)(0x00ff & (val >> 0 ));
	}

	 /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
	public static byte  readMSB8(byte[] data,int p)
	{
		return data[p++] ;
	}

	 /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
	public static short readMSB16(byte[] data,int p)
	{
		short val = (short)(
		        (data[p+1]<<16)&0x00ff0000|
		        (data[p+0]<<24)&0xff000000
		);
		return val ;
	}

	 /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
	public static int readMSB24(byte[] data,int p)
	{
		int value = (
		        (data[p+2]<< 8)&0x0000ff00|
		        (data[p+1]<<16)&0x00ff0000|
		        (data[p+0]<<24)&0xff000000
		);
		return value ;
	}

	 /** 
     * <summary>  (0xaa,0xbb,0xcc,0xdd = 0xddccbbaa)
     * @param		 
     * @param		 
	 * @return   	 
	 */ 
	public static int readMSB32(byte[] data,int p)
	{
		int value = (
		        (data[p+3]<< 0)&0x000000ff|
		        (data[p+2]<< 8)&0x0000ff00|
		        (data[p+1]<<16)&0x00ff0000|
		        (data[p+0]<<24)&0xff000000
		);
		return value ;
	}


//---------------------------------------------------------------------------------------------

	/** 
	 *  <summary> save a value to data with Variable Length Queue (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 *  @param	  data[]     
	 *  @param   p          
	 *  @param   vaule      
	 *  @return             
	 */
	public static int writeVLQ(byte[] data,int p,int vaule)
	{
		byte[] temp = new byte[4];
		int length = 0;
		for(int i=0;i<4;i++)
		{
			temp[i] = (byte)(vaule>>(7*i));
			length++;

			if((vaule>>(7*(i+1)))<=0)
			{
				temp[i] = (byte)(temp[i]&0x7f);
				break;
			}
			else
			{
				temp[i] = (byte)(temp[i]|0x80);
			}
		}
			
		for(int i=0;i<length;i++)
		{
			data[p++] = temp[(length-1)-i];
		}

		return length;
	}

	/** 
	 * <summary> read a value to data with Variable Length Queue (0xaa,0xbb,0xcc,0xdd = 0xaabbccdd)
	 * @param	data[]     
	 * @param	p          
	 * @return	int[0] how many bytes ;int[1] value
	 */
	public static int[] readVLQ(byte[] data,int p)
	{
		int Vaule = 0;
		int length = 0;
		for(int i=0;i<4;i++)
		{
			byte temp = data[p++];
			Vaule = (Vaule<<7)|(temp&0x7f);
			length++;

			if((temp&0x80)==0)
			{
				break;
			}
		}	
		int[] ret = new int[2];
		ret[0] = length;
		ret[1] = Vaule;
		return ret;
	}	

//	--------------------------------------------------------------------------------------------------------------------------
//	Random
//	--------------------------------------------------------------------------------------------------------------------------
	private static void RANDOM_START(){}

	public static final double 	getRandom(java.util.Random random, double min, double max)
	{
		if (max == min) {
			return min;
		} 
		if (min > max) {
			double temp = min;
			min = max;
			max = temp;
		}
		double delta = max - min;
		
		return min + Math.abs(random.nextDouble() * delta);
		
	}
	public static final float 	getRandom(java.util.Random random, float min, float max)
	{
		if (max == min) {
			return min;
		} 
		if (min > max) {
			float temp = min;
			min = max;
			max = temp;
		}
		float delta = max - min;

		return min + Math.abs(random.nextFloat() * delta);
		
	}
	public static final int 	getRandom(java.util.Random random, int min, int max)
	{
		if (max == min) {
			return min;
		} 
		if (min > max) {
			int temp = min;
			min = max;
			max = temp;
		}
		int delta = max - min;
		
		return min + Math.abs(random.nextInt() % delta);
	}
	public static final long 	getRandom(java.util.Random random, long min, long max)
	{
		if (max == min) {
			return min;
		} 
		if (min > max) {
			long temp = min;
			min = max;
			max = temp;
		}
		long delta = max - min;
		
		return min + Math.abs(random.nextLong() % delta);
	}

//	public static void main(String args[]) 
//	{
//		java.util.Random random = new java.util.Random();
//		for (int i=0; i<10; i++) {
////			System.out.println(random.nextDouble());
//			System.out.println(getRandom(random, -2.0d, 2d));
//		}
//	}
	
//	--------------------------------------------------------------------------------------------------------------------------

	/** Get a random number in the specified range
	 * @param min The minimun number
	 * @param max The maximum number
	 * @return Get the random number out
	 */
	public static final int getRandomEqual(int min, int max)
	{
		return (max-min)==0?max:(Math.abs(getRandom().nextInt() % (max-min+1)) + min);
	}
	
	/** Get a random number in the specified range
	 * @param min The minimun number
	 * @param max The maximum number
	 * @return Get the random number out
	 */
	public static final int getRandom(int min, int max)
	{
		return (max-min)==0?max:(Math.abs(Random.nextInt() % (max-min)) + min);
	}
	
	public static final byte getRandom(byte[] array)
	{
		return array[Math.abs(Random.nextInt()%array.length)] ;
	}
	public static final short getRandom(short[] array)
	{
		return array[Math.abs(Random.nextInt()%array.length)] ;
	}
	public static final int getRandom(int[] array)
	{
		return array[Math.abs(Random.nextInt()%array.length)] ;
	}
	
	public static final long getRandom(long[] array)
	{
		return array[Math.abs(Random.nextInt()%array.length)] ;
	}	
	
	public static final<T> T getRandom(T[] array)
	{
		if (array==null || array.length==0) return null;
		
		return array[Math.abs(Random.nextInt()%array.length)] ;
	}
	
	public static final<T> T getRandom(List<T> array)
	{
		if (array==null || array.size()==0) return null;
		return array.get(Math.abs(Random.nextInt()%array.size())) ;
	}
	
	@SuppressWarnings("unchecked")
	public static final<T> T getRandom(Collection<T> array)
	{
		if (array==null || array.size()==0) return null;
		return (T)getRandom(array.toArray());
	}
	
//	@SuppressWarnings("unchecked")
	public static final<E extends Enum<E>> E getRandomEnum(Class<E> enums)
	{
		return (E)getRandom(EnumSet.allOf(enums));
	}
	private static void RANDOM_END(){}
//	--------------------------------------------------------------------------------------------------------------------------
//	
//	--------------------------------------------------------------------------------------------------------------------------

}
