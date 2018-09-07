package com.core.utils.dbtutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;


import com.core.app.Latte;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;


/**
 *
 */
public class FileUtil {
	/**
	 * 配合测试把巡店拜访的json数据写入文件中（因为数据太大，无法打印全部数据）
	 * 
	 * @param requestData
	 * @param filePath
	 */
	public static void writeTxt(String requestData, String filePath) {
		String json = requestData + "\n";
		File file = new File(filePath); // 要写入的文件
		BufferedWriter writer = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new BufferedWriter(new FileWriter(file, true));
			writer.newLine();
			writer.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	/**
	 * 获取sd卡路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

		if (sdCardExist) // 如果SD卡存在，则获取跟目录
		{
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			Log.e("FileUtil", "要删除的图片文件不存在！");
		}
	}

	/**
	 * 将图片文件转成字符串
	 * 
	 * @param path 文件路径
	 * @return
	 */
	public static String file2String(String path) {
		String imgStr = "";
		try {

			// 将图片转换成字符串

			File f = new File(path);

			FileInputStream fis = new FileInputStream(f);

			byte[] bytes = new byte[fis.available()];
			
			//byte[] data = compress(bytes);
			//String json = new String(Base64.encodeToString(data, Base64.DEFAULT)); 

			fis.read(bytes);

			fis.close();

			// 生成字符串

			//imgStr = byte2hex(bytes);
			imgStr = new String(Base64.encodeToString(bytes, Base64.DEFAULT)); 

			//System.out.println(imgStr);

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}
		return imgStr;
	}
	
	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) // 
	{
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * 字符串转二进制
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) { // 字符串转二进制
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 字符串转成图片
	 * 
	 * @param liu
	 */
	public static void string2File(String liu) {

		try {

			// 读取
			OutputStream o = new FileOutputStream(new File("sdcard/adc.jpg"));

			// 将字符串转换成二进制，用于显示图片

			// 将上面生成的图片格式字符串 imgStr，还原成图片显示

			byte[] imgByte = hex2byte(liu);

			InputStream in = new ByteArrayInputStream(imgByte);

			byte[] b = new byte[1024];

			int nRead = 0;

			while ((nRead = in.read(b)) != -1) {

				o.write(b, 0, nRead);

			}

			o.flush();

			o.close();

			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

		}

	}
	
	/**
	 * 创建文件夹
	 */
	public static void createphotoFile(File file) {
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	/**
	 * 把bitmap转换成String
	 * 
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
		byte[] b = baos.toByteArray();
		
		return Base64.encodeToString(b, Base64.DEFAULT);
		
	}
	
	/**
	 * 根据路径获得突破并压缩返回bitmap用于显示
	 * 
	 * @param filePath
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}
	
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}
	
	/** 
     * 对byte[]进行压缩 
     *  
     * @param data
     * @return 压缩后的数据 
     */  
    public static byte[] compress(byte[] data) {  
        System.out.println("before:" + data.length);  
          
        GZIPOutputStream gzip = null ;  
        ByteArrayOutputStream baos = null ;  
        byte[] newData = null ;  
          
        try {  
            baos = new ByteArrayOutputStream() ;  
            gzip = new GZIPOutputStream(baos);  
              
            gzip.write(data);  
            gzip.finish();  
            gzip.flush();  
              
            newData = baos.toByteArray() ;  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                gzip.close();  
                baos.close() ;  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
          
        System.out.println("after:" + newData.length);  
        return newData ;  
    } 
    
    // 压缩字符串  
    public static String compress(String str) throws IOException {   
        if (str == null || str.length() == 0) {   
         return str;   
       }   
        ByteArrayOutputStream out = new ByteArrayOutputStream();   
        GZIPOutputStream gzip = new GZIPOutputStream(out);   
        gzip.write(str.getBytes());   
        gzip.close();   
       return out.toString("ISO-8859-1");   
      }   
    
	// 将文件转为String
	public static String fileToString(String path) throws Exception {
		File file = new File(path);
		InputStream inStream = new FileInputStream(file);
		byte[] buffer = new byte[2048];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		//compress(data);
		//compress(new String(data)).getBytes();
		//byte[] encode = Base64.encode(data,0,data.length, Base64.DEFAULT);
		
		// 压缩 加密(3种不同的压缩方式使用第2种 1yangjava 2wangjava 3startjva)
		//String s = new String(Base64.encode(compress(new String(data)).getBytes(), Base64.DEFAULT));
		String s = new String(Base64.encode(gZip(data), Base64.DEFAULT));
		//String s = new String(Base64.encode(compress(data), Base64.DEFAULT));
		// 加密
		//String s = new String(Base64.encode(data, Base64.DEFAULT));
		//String b = compress(s);
		outStream.close();
		inStream.close();
		return s;
	}

	// 把string转成文件
	public static File stringToFile(String res) throws Exception {
		byte[] data = Base64.decode(res, Base64.DEFAULT);

		String dir = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ File.separator
				+ "rjcache"
				+ File.separator + "chatRecord";
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		File distFile = File.createTempFile("recRecord", ".amr", dirFile);

		distFile = byteToFile(data, distFile.getAbsolutePath());

		return distFile;
	}

	//将byte写入文件
	public static File byteToFile(byte[] buf, String filePath) throws Exception {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		fos = new FileOutputStream(file);
		bos = new BufferedOutputStream(fos);
		bos.write(buf);
		if (bos != null) {
			bos.close();
		}
		if (fos != null) {
			fos.close();
		}
		return file;
	}

	/***
	  * 压缩GZip
	  * 
	  * @param data
	  * @return
	  */
	public static byte[] gZip(byte[] data) {
		//System.out.println("======before:" + data.length);
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("========after:" + b.length);
		return b;
	}
	
	/**
     * 生成文件
     * 
	 * @param json
	 * @return 
	 */
	public static void savFileFike(String json,String name) {
		
		String sdcardPath = Environment.getExternalStorageDirectory() + "";
        String DbtPATH = sdcardPath + "/dbt/et.tsingtaopad";
        String BUGPATH = DbtPATH + "/log/";

		File txt = new File(BUGPATH+name+".txt");
		if (!txt.exists()) {
			try {
				txt.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		byte bytes[] = new byte[2048];
		bytes = json.getBytes(); // 新加的
		int b = json.length(); // 改
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(txt);
			fos.write(bytes, 0, b);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	// 操作日志文件夹路径 /storage/emulated/0/Android/data/包名/files/log/
	public static String getLogPath(){
		String path = Latte.getApplicationContext().getExternalFilesDir("log").getPath()+ File.separator ;
		return path;
	}

	// 崩溃日志文件夹路径 /storage/emulated/0/Android/data/包名/files/bug/
	public static String getBugPath(){
		String path = Latte.getApplicationContext().getExternalFilesDir("bug").getPath()+ File.separator ;
		return path;
	}

	// 图片文件夹路径 /storage/emulated/0/Android/data/包名/files/photo/
	public static String getPhotoPath(){
		String path = Latte.getApplicationContext().getExternalFilesDir("photo").getPath()+ File.separator ;
		return path;
	}

	// 压缩文件夹路径 /storage/emulated/0/Android/data/包名/files/zip/
	public static String getZipPath(){
		String path = Latte.getApplicationContext().getExternalFilesDir("zip").getPath()+ File.separator ;
		return path;
	}
	
	/**
	 * 获取操作日志文件夹路径(不以/结尾)
	 * @return  返回: /storage/emulated/0/dbt/et.tsingtaopad/log
	 */
	public static String getLogPathNoX(){
		String sdcardPath = getSDPath() + "";
		String DbtPATH = sdcardPath + "/dbt/et.tsingtaopad";
		String logPATH = DbtPATH + "/log";

		File dir = new File(logPATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		return logPATH;
	}


}
