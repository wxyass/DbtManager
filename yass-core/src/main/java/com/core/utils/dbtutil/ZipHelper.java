package com.core.utils.dbtutil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class ZipHelper {
	//protected final static Log log =LogFactory.getLog(ZipHelper.class);
    private final static int CacheSize = 1024;

    /**
     * 压缩Zip
     * @param data
     * @return
     */
    public static byte[] zipByte(byte[] data) {
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        byte result[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(1);
        try {
            byte[] buf = new byte[CacheSize];
            int got = 0;
            while (!compresser.finished()) {
                got = compresser.deflate(buf);
                o.write(buf, 0, got);
            }

            result = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            compresser.end();
        }
        return result;
    }

    /***
     * 压缩String
     * @param data
     * @return
     */
    public static byte[] zipString(String data) {
        byte[] input = new byte[0];
        try {
            input = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        byte[] result = ZipHelper.zipByte(input);
        return result;
    }

    /**
     * 解压Zip
     * @param data
     * @return
     */
    public static byte[] unZipByte(byte[] data) {
    	
        Inflater decompresser = new Inflater();
        decompresser.reset(); 
        //decompresser.setInput(data);
        decompresser.setInput(data, 0, data.length);
        byte result[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(1);
        try {
            byte[] buf = new byte[CacheSize];
            int got = 0;
            while (!decompresser.finished()) {
                got = decompresser.inflate(buf);
                o.write(buf, 0, got);
            }
            result = o.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            decompresser.end();
        }
        return result;
    }


    /**
     * 解压Zip数据为String
     * @param data
     * @return
     */
    public static String unZipByteToString(byte[] data) {
        byte[] result = unZipByte(data);
        String outputString = null;
        try {
            outputString = new String(result, 0, result.length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return outputString;
    }
    
    @SuppressWarnings("unused")
    public static void main(String [] args) throws UnsupportedEncodingException {
        String test = "aaaa测试";
        byte[] byte1 = ZipHelper.zipString(test);
        byte[] byte2 = new String(ZipHelper.zipString(test)).getBytes("ISO-8859-1");
        byte[] byte3 = new String(ZipHelper.zipString(test), "ISO-8859-1").getBytes("ISO-8859-1");
        
        // out.println(new String(ZipHelper.zipString(test), "ISO-8859-1"));
        String str = new String(byte1, "ISO-8859-1");
        // byte[] byteTest = {120, -100, 75, 76, 76, 76, 124, -74, -75, -5, -59, -6, -87, 0, 28, -80, 5, -41};
           byte[] byteTest = {120, 63, 75, 76, 76, 76, 124, 63, 63, 63, 63, 63, 0, 28, 63, 5, 63};
        String strUnZip = unZipByteToString(byte3);
        //log.warn(strUnZip);
        // zipHelperTest();
    }
    
    /**
     * 解压Zip
     * 
     * @param data
     * @return
     */
    public static byte[] unZipByte(String tabName,byte[] data) {
    	DbtLog.write(tabName+"into unZipByte");
        Inflater decompresser = new Inflater();
        decompresser.setInput(data);
        byte result[] = new byte[0];
        ByteArrayOutputStream o = new ByteArrayOutputStream(1);
        try {
            //byte[] buf = new byte[CacheSize];
            byte[] buf = new byte[2048];
            int got = 0;
            while (!decompresser.finished()) {
                got = decompresser.inflate(buf);
                o.write(buf, 0, got);
                //Log.e("DbtLog", tabName+":"+got+"---"+result.length);
            }
            result = o.toByteArray();
        } catch (Exception e) {
        	DbtLog.write(tabName+"unZipByte"+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
            	DbtLog.write(tabName+"unZipByte-finally");
                o.close();
            } catch (IOException e) {
            	DbtLog.write(tabName+"unZipByte-finally"+e.getMessage());
                e.printStackTrace();
            }
            decompresser.end();
        }
        return result;
    }
    
    /**
     * 解压Zip数据为String
     * 
     * @param data
     * @return
     */
    public static String unZipByteToString(String tabName,byte[] data) {
    	DbtLog.write(tabName+"into unZipByteToString");
        byte[] result = unZipByte(tabName,data);
    	DbtLog.write(tabName+"result==null:"+(result==null));
        String outputString = null;
        try {
        	DbtLog.write(tabName+"new string 开始:"+result.length);
            DbtLog.write(tabName+"new string 开始:"+result.length/1024d/1024d);
            outputString = new String(result, 0, result.length, "UTF-8");
            DbtLog.write(tabName+"new string 结束:");
        } catch (UnsupportedEncodingException e) {
        	DbtLog.write(tabName+"e:"+(e.getMessage()));
            e.printStackTrace();
        }
        DbtLog.write(tabName+"outputString == null:"+(outputString ==null));
        DbtLog.write(tabName+"outputString == 空:"+("".equals(outputString.trim())));
        try{
        	DbtLog.write(tabName+"outputString:哈哈哈哈");
        	DbtLog.write(tabName+"outputString.length():"+outputString.length());
//        	DbtLog.write(tabName+"outputString:"+outputString);
        }catch(Exception e){
       	 	DbtLog.write(tabName+"e:"+e.getMessage());
        }
    	DbtLog.write(tabName+"outputString:hohohoho");
    	result = null;
        return outputString;
    }
    
    /**
     * 解压缩
     *
     * @param data 待压缩的数据
     * @return byte[] 解压缩后的数据
     */
    public static byte[] unZipByte1(byte[] data) {
        byte[] output = new byte[0];

        Inflater decompresser = new Inflater();
        decompresser.reset();
        decompresser.setInput(data, 0, data.length);
        //decompresser.setInput(data);

        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!decompresser.finished()) {
                int i = decompresser.inflate(buf);
                o.write(buf, 0, i);
            }
            output = o.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        decompresser.end();
        return output;
    }
}
