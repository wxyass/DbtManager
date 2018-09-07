package com.core.utils.dbtutil;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 常用工具类
 */
public class FunUtil {

    /**
     * 密码加密
     * @param str
     * @return
     */
    public static String EncoderByMd5(String str) {
        
         try{
	         //此 MessageDigest 类为应用程序提供信息摘要算法的功能，必须用try,catch捕获
	         MessageDigest md5 = MessageDigest.getInstance("MD5");
	         str = md5.digest(str.getBytes("utf-8")).toString();//转换为MD5码
         } catch(Exception ex) {
             ex.printStackTrace();
         }
         return str;
    }
    /**
     * 密码加密
     * @param strOldPwd 密码原文
     * @return strNewPwd 加密后密码
     */
    public static String pwdEncodeMD5(String strOldPwd) {
        
        String strTempPwd = FunUtil.EncoderByMd5(strOldPwd);
        String strNewPwd = EncoderByMd5(strTempPwd + "sino2012soft");
        return strNewPwd;
    }
    
    /**
     * 替换文本内容
     * 
     * @param strContext    待替换文本
     * @param param         替换参数
     * @return              替换后结果
     */
    public static String replaceParam(String strContext,String[] param){

        //返回值
        String strResult = strContext;
        if (param.length < 0) return strResult; 
        
        //替换文本
        for (int i = 0;i < param.length;i++) {
            strResult = strResult.replace("{" + i + "}", param[i]);
        }
        
        //返回值
        return strResult;
    }
	   /**
  * List后转换为 '','',''
  * 
  * @param lstSource
  * @return
  */
 public static String brackReplace(List<String> lstSource) {
     String strResult = "";
     String strSource = lstSource.toString();
     strResult = strSource.replaceAll("\\[|\\]", "");
     strResult = strResult.replaceAll(",", "','");
     strResult = strResult.replaceAll(" ", "");
     strResult = "'" + strResult + "'";
     return strResult;
 }
    
    /**
     * 随机产生定长字符
     * 
     * @param size
     * @return
     */
    public static String getRandomString(int size) {// 随机字符串
        
        char[] c = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'q',
                'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
                'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
        Random random = new Random(); // 初始化随机数产生器
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            sb.append(c[Math.abs(random.nextInt()) % c.length]);
        }
        return sb.toString();
    }
    
    /**
     * 如果为null就为空否则保持不变
     * @param strParam
     * @return
     */
    public static String isNullSetSpace(String strParam) {
        
        String strResult = "";
        
        // 判断是否为null
        strResult = (strParam == null) ? "" : strParam;
        return strResult;
    }
    /**
     * 读取数据时  如果为null就为"请选择日期"否则保持不变
     * @param strParam
     * @return
     */
    public static String isNullSetDate(String strParam) {
    	
    	String strResult = "";
    	
    	// 判断是否为null    // 1451525045000L -> 2015-12-11
    	//strResult = (strParam == null) ? "请选择日期" : strParam; 
    	strResult = (TextUtils.isEmpty(strParam)||"null".equals(strParam)) ? "请选择日期" : todate(strParam); 
    	return strResult;
    }
    
    // 1451525045000L -> 2015-12-11
    public static String todate(String strParam){
    	String abc = new SimpleDateFormat( "yyyy-MM-dd").format( new Date(Long.parseLong(strParam)));;
		return abc;
    }
    
    /**
     * 保存到数据库中时  如果为"请选择日期"就null, 否则保持不变
     * @param strParam
     * @return
     */
    public static String isDateString(String strParam) {
    	
    	String strResult = "";
    	
    	// 判断是否为null  // 2015-12-11   -> 1451525045000L
    	//strResult = ("请选择日期".equals(strParam)) ? null : strParam; 
    	strResult = ("请选择日期".equals(strParam)) ? null : ""+DateUtil.parse (strParam , "yyyy-MM-dd" ).getTime(); 
    	return strResult;
    }
    
    /**
     * 如果为null就为空否则保持不变
     * @param ojb
     * @return
     */
    public static Object isNullSetSpace(Object ojb) {
        
        Object object;
        
        // 判断是否为null
        object = (ojb == null) ? "" : ojb;
        return object;
    }
    
    /**
     * 如果为null就为空否则保持不变
     * @param ojb
     * @return
     */
    public static Object[] isNullSetSpace(Object[] ojb) {
        
        Object[] objResult = new Object[ojb.length];
        Object object;
        if (ojb != null) {
            for (int i = 0; i < ojb.length; i++) {
                object = ojb[i];
                objResult[i] = (object == null) ? "" : object;
            }
        }

        // 判断是否为null
        return objResult;
    }
    
    /**
     * 为空时为0
     * @param iParam
     * @return
     */
    public static long isNullSetZero (Object iParam) {
        
        long iResult = 0l;
        iResult = (iParam == null) ? 0 : Long.parseLong(iParam.toString());
        return iResult;
    }
    
    /**
     * 为空时为0
     * @param iParam
     * @return
     */
    public static double isZeroSetZero (double iParam) {
        
    	if(0.0 == iParam){
    		iParam = 0;
    	}
    	
    	double iResult = 0l;
        iResult = (iParam == 0) ? 0 : iParam;
        return iResult;
    }
    
    /**
     * 为null时为0
     * @param iParam
     * @return
     */
    public static Double isNullToZero (Double iParam) {
        
    	if(iParam==null){
    		iParam = 0.0;
    	}
    	
    	double iResult = 0l;
        iResult = (iParam == 0.0) ? 0 : iParam;
        return iResult;
    }
    
    /**
     * 对空串转换为NULL串处理
     * @param strIn 待处理的字符串
     * @return 字符串<BR>
     */
    public static String changeToNull(String strIn) {
        
        String strTemp = "";
        if (strIn == null) {
            return strIn;
        } else {
            strTemp = strIn.trim();
            if (strTemp.equals("")) {
                return null;
            }
            return strTemp;
        }
    }
    
    /**
     * 字符串内的减号替换为空
     * 
     * @param strValue  参数
     * @return
     */
    public static String replaceMinus(String strValue) {
        
        if (!"".equals(strValue) && strValue != null) {
            return strValue.replace("-", "");
        } else {
            return "";
        }
    }
    
    /**
     * 移除List中重复的数据
     * @param list
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List removeDuplicate(List list) {
        
        HashSet h = new HashSet(list);      
        list.clear();
        list.addAll(h);
        return list;
    }
    
    /**
     * 不足位缺省位补零
     * @return
     */
    public static String digitZero(String strSource, int iLength) {
        
        if (strSource.length() > iLength) {
            return strSource;
        }
        for (int i = strSource.length(); i < iLength; i++) {
            strSource = "0" + strSource;
        }
        return strSource;
    }
    
    /**
     * 不足位缺省位补零-方法重载
     * @param iSource
     * @param iLength
     * @return
     */
    public static String digitZero(int iSource, int iLength) {
        
        return digitZero(String.valueOf(iSource), iLength);
    }
    
    /**
     * 全角转换成半角
     * @param input 原始字符串
     * @return 转换后的字符串
     */
    public static String QtoB(String input) {
        
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * 半角转换成全角
     * @param input 原始字符串
     * @return 转换后的字符串
     */
    public static String BtoQ(String input){
        
        char c[] = input.toCharArray();
        for ( int i=0; i<c.length;i++ ) {
            if (c[i] ==' ') {
                c[i] = '\u3000';
            } else if (c[i]<'\177') {
                c[i]= (char) (c[i]+65248);
            }
        }
        return new String(c);
    }
    
    /**
     * 根据要求替换字符串
     * 
     * @param strSrc                源字符串
     * @param strAwaitSrc           待替换字符
     * @param strTargetSrc          替换待替换字符
     * @return
     */
    public static String Interconversion(String strSrc, 
                        String strAwaitSrc, String strTargetSrc) {
        
        return strSrc.replaceAll(strAwaitSrc, strTargetSrc);
    }
    
    /**
     * 将字符串转为数字
     * 
     * @param strValue 原值
     * @return 数字
     * @throws Exception
     */
    public static int parseInteger(String strValue) throws Exception {
        
        int intResult;
        try {
            intResult = Integer.parseInt(strValue);
        } catch (Exception e) {
            Log.e("parseInteger", "无法将[" + strValue + "]转为数字", e);
            throw e;
        }
        return intResult;
    }
    
    /**
     * 将字符串转为数字
     * 
     * @param strValue 原值
     * @return 数字
     * @throws Exception
     */
    public static String NonIntegerToInt(String strValue) throws Exception {

        BigDecimal bd = new BigDecimal(strValue);
        // 设置小数位数为0
        bd = bd.setScale(0, BigDecimal.ROUND_DOWN);
        
        // 转化为字符串输出
        String OutString = bd.toString();
        return OutString;
    }
    
    /**
     * 根据保留最长字符串
     * 
     * @param strSource     源字符串
     * @param iMaxLength    保留最大长度
     * @return              截取后的字符串
     */
    public static String interceptiString(String strSource, int iMaxLength) {

        // 如果为空返回自己
        if (CheckUtil.isBlankOrNull(strSource)) return strSource;
        
        // 返回值
        String Result = strSource;
        
        // 当字符串超过最大长度时截取
        if (strSource.length() > iMaxLength) {
            Result = Result.substring(0, iMaxLength);
        }
        
        // 返回结果
        return Result;
    }
    
    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     * 
     * @param datestr 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为“yyyyMMdd”的形式
     * @return 解析后的日期
     */
    @SuppressLint("SimpleDateFormat")
	public static Date parseDate(String datestr, String pattern) {
        if (null == datestr) return null;
        
        Date date = null;
        if (null == pattern || "".equals(pattern)) {
        	pattern = "yyyyMMdd";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
			date = dateFormat.parse(datestr);
		} catch (ParseException e) {
			Log.e("FunUtil.parseDate", "转换日期出错", e);
		}
        return date;
    }
    
    
    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     * 
     * @param jsonObj 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为“yyyyMMdd”的形式
     * @return 解析后的日期
     */
    @SuppressLint("SimpleDateFormat")
	public static Date parseDate(JSONObject jsonObj, String pattern) {
    	
        if (null == jsonObj) {
        	
        	return null;
        } else {
        	
        	return FunUtil.parseDate(jsonObj.optString("$"), pattern);
        }
        
    }
    
    /**
     * 以list形式返回objLst里的指定属性的数据集合
     * 
     * @param objLst        数据源list
     * @param fieldName     要获取的objLst里的属性名称
     * @param cls           返回的数据类型
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> List<T> getPropertyByName(List objLst, String fieldName, Class<T> cls) {
        List<T> tempLst = new ArrayList<T>();
        if (!CheckUtil.IsEmpty(objLst)) {
            for (Object item : objLst) {
                tempLst.add((T)ReflectUtil.getFieldValueByName(fieldName, item));
            }
        }
        return tempLst;
    }
    
    /**
     * 获取UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    /**
     * 如果是Null转换成0
     * @param obj
     * @return
     */
    public static String isNullToZero(Object obj) {
        return isBlankOrNullTo(obj, "0");
    }
    
    /**
     * 可用泛型来扩展
     * 
     * @param obj
     * @param defalut
     * @return
     */
    public static String isBlankOrNullTo(Object obj, String defalut) {
        String str = defalut;
        if (obj == null || "".equals(obj.toString())) {
            str = defalut;
            
        } else if (obj instanceof String) {
            str = String.valueOf(obj).trim();
            if (str.getBytes().length == 0) str = defalut;
            
        }else if (obj instanceof BigDecimal) {
            str = String.valueOf(((BigDecimal)obj).doubleValue());
            
        }else if (obj instanceof Double) {
            str = String.valueOf((Double)obj);
            
        }else if (obj instanceof Editable) {
            str = obj.toString();
            if ("".equals(str)) str = defalut;
            
        } else {
            str = obj.toString();
        }
        return str;
    }
    
    // 为空为null时  返回null
    public static Double isBlankOrNullToDouble(String obj) {
    	Double dou = null;
        if (obj == null || "".equals(obj.toString())|| "null".equals(obj.toString())) {
        	dou = null;
            
        } else if (obj instanceof String) {
            
            dou = Double.parseDouble(obj);
        }
        return dou;
    }
    
    
    
    /**
     * 分隔成字符串
     * 
     * @param source        源字符串
     * @param separator     分隔符
     * @return
     */
    public static List<String> splitToList(String source, String separator) {
        List<String> lst = new ArrayList<String>();
        if (!CheckUtil.isBlankOrNull(source)) {
            String[] temp = source.split(separator);
            for (String item : temp) {
                lst.add(item);
            }
        }
        return lst;
    }
    
    /**
     * 将字符串转换成Bigdecimal
     * 
     * @param str   待转字符
     * @param def   默认值
     * @return
     */
    public static BigDecimal strToBigDecimal(String str, String def) {
        BigDecimal bigDecimal = null;
        if (CheckUtil.isBlankOrNull(str)) {
            if (!CheckUtil.isBlankOrNull(def)) {
                try {
                    bigDecimal = new BigDecimal(def);
                } catch (Exception e) {
                    bigDecimal = null;
                }
            }
        } else {
            try {
                bigDecimal = new BigDecimal(str);
            } catch (Exception e) {
                bigDecimal = strToBigDecimal(null, def);
            }
        }
        return bigDecimal;
    }
    
 // 保留小数后两位  数字型字符串 -> 小数字符串
    public static String getDecimalsData(String content) {
        String data = "0.0";
        if (".".equals(content)) {//
            data = "0.0";
        } else if ("0.0".equals(content)) {
            data = "0.0";
        } else if (content.length() > 1 && content.endsWith(".")) {
            data = content + "0";
        } else if (content.length() > 1 && content.startsWith(".")) {
            // 以小数点开头,截取小数点后两位
            String realnum = "0" + content;
            if(realnum.length()>4){
            	data = realnum.substring(0, 4);
            }else{
            	data = realnum;
            }
            
        } else if ("".equals(content)) {
            data = "0";
        } else {
            if (content != null && (!"".equals(content))) {
                Double d = Double.parseDouble(content);
                d = d + 0.000001;
                String[] split = String.valueOf(d).split("\\.");
                data = split[0] + "." + split[1].substring(0, 2);
            } else {
                data = content;
            }
        }
        return data;
    }
    
    /**
	 * 压缩bitmap
	 * @param bitmap
	 * @param cleardata 要压到的大小
	 * @return
	 */
	public static Bitmap compressBitmap(Bitmap bitmap,int cleardata){
		//DbtLog.logUtils(TAG, "compressBitmap()-压缩bitmap照片");
			
			FileOutputStream fos = null;
			try {
				
				ByteArrayOutputStream bos = new ByteArrayOutputStream();  
		        // 图片压缩  把压缩后的数据存放到baos中
				int definition = 100;
		        bitmap.compress(Bitmap.CompressFormat.JPEG, definition, bos);    
		        // 图片大于50K继续压缩  
		        while ( bos.toByteArray().length / 1024 > cleardata) {  
		            // 重置baos即清空baos 
		        	bos.reset();  
		            // 每次都减少1
		        	definition -= 1;  
		            bitmap.compress(Bitmap.CompressFormat.JPEG, definition, bos);  
		        }  
				
			} catch (Exception e) {
	
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		
			return bitmap;
		}
	/**
	 * 生成文字水印
	 * 
	 * @param src
	 *            原始图片
	 * @param mstrTitle
	 *            需要添加的文字
	 * @return
	 */
	public static  Bitmap createWateBitmap1(Bitmap src, String mstrTitle, int weith, int heigh, String mstrTitle2, int weith2,
			int heigh2, String mstrTitle3, int weith3, int heigh3, String mstrTitle4, int weith4, int heigh4) {
		int w = src.getWidth();
		int h = src.getHeight();
		// 创建一个新的和SRC长度宽度一样的位图
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		// 相当于创建一个和原图大小相等的画板
		Canvas cv = new Canvas(newb);
		// 在 0，0坐标开始画入src
		cv.drawBitmap(src, 0, 0, null);
		// 画笔
		Paint p = new Paint();
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		p.setColor(Color.RED);
		p.setTypeface(font);
		p.setTextSize(22);
		cv.drawText(mstrTitle, w - 206, h - 70, p);
		cv.drawText(mstrTitle2, w - 206, h - 50, p);
		cv.drawText(mstrTitle3, w - 206, h - 30, p);
		cv.drawText(mstrTitle4, w - 206, h - 10, p);
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		cv.restore();// 存储

		return newb;
	}

	/**
	 * 保存图片到本地()
	 * 
	 * @param filePath
	 *            图片保存路径 (如: sdcard/)
	 * @param bitmap
	 *            图片数据源
	 * @param name
	 *            图片名称 (如: 201511121138.jpg)
	 * @param definition
	 *            图片质量 (1最差 100最好)(图片最大压缩到50k以下)
	 * @param cleardata
	 *            图片最高大小 (1最差 100最好)(图片最大压缩到50k以下)
	 */
	public static void saveHeadImg(String filePath, Bitmap bitmap, String name, int definition, int cleardata) {
		//DbtLog.logUtils(TAG, "saveHeadImg()-保存图片到本地");
		// 存储路径处理
		File dir = new File(filePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// 文件保存
		File file = null;
		if (name == null) {
			file = new File(filePath, "ywm" + ".jpg");// 名字可用随意
		} else {
			file = new File(filePath, name);
		}
		FileOutputStream fos = null;
		try {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// 图片压缩 把压缩后的数据存放到baos中
			bitmap.compress(Bitmap.CompressFormat.JPEG, definition, bos);
			// 图片大于50K继续压缩
			while (bos.toByteArray().length / 1024 > cleardata) {
				// 重置baos即清空baos
				bos.reset();
				// 每次都减少1
				definition -= 1;
				bitmap.compress(Bitmap.CompressFormat.JPEG, definition, bos);
			}
			// 生成文件
			fos = new FileOutputStream(file);
			fos.write(bos.toByteArray());
			fos.flush();
			fos.close();

		} catch (Exception e) {

		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * bitmap正旋转180°
	 * 
	 * @param a
	 * @return
	 */
	public static Bitmap convert(Bitmap a) {

		int w = a.getWidth();
		int h = a.getHeight();

		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		Matrix m = new Matrix();
		m.postScale(1, -1); // 镜像垂直翻转

		// m.postScale(-1, 1); //镜像水平翻转
		// m.postRotate(-90); //旋转-90度

		Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
		cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, w, h), null);

		return newb;

	}
	
	public static Bitmap convertToBitmap(String path, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		// 设置为ture只获取图片大小
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.ARGB_8888;
		// 返回为空
		BitmapFactory.decodeFile(path, opts);
		int width = opts.outWidth;
		int height = opts.outHeight;
		float scaleWidth = 0.f, scaleHeight = 0.f;
		if (width > w || height > h) {
			// 缩放
			scaleWidth = ((float) width) / w;
			scaleHeight = ((float) height) / h;
		}
		opts.inJustDecodeBounds = false;
		float scale = Math.max(scaleWidth, scaleHeight);
		opts.inSampleSize = (int)scale;
		WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
		return Bitmap.createScaledBitmap(weak.get(), w, h, true);
	}
	
	/**  
	 *  处理图片   
	 * @param bm 所要转换的bitmap  
	 * @param newWidth 新的宽
	 * @param newHeight 新的高
	 * @return 指定宽高的bitmap  
	 */  
	 public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){    
	    // 获得图片的宽高    
	    int width = bm.getWidth();    
	    int height = bm.getHeight();    
	    // 计算缩放比例    
	    float scaleWidth = ((float) newWidth) / width;    
	    float scaleHeight = ((float) newHeight) / height;    
	    // 取得想要缩放的matrix参数    
	    Matrix matrix = new Matrix();    
	    matrix.postScale(scaleWidth, scaleHeight);    
	    // 得到新的图片    
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);    
	    return newbm;    
	}
	 
	 /**
	     * 根据给定的宽和高进行拉伸
	     *
	     * @param origin    原图
	     * @param newWidth  新图的宽
	     * @param newHeight 新图的高
	     * @return new Bitmap
	     */
	 public static Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
	        if (origin == null) {
	        	DbtLog.logUtils("FunUtil", "Bitmap为null");
	            return null;
	        }
	        int height = origin.getHeight();
	        int width = origin.getWidth();
	        DbtLog.logUtils("FunUtil", "获取Bitmap长宽成功");
	        float scaleWidth = ((float) newWidth) / width;
	        float scaleHeight = ((float) newHeight) / height;
	        Matrix matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
	        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
	        if (!origin.isRecycled()) {
	            origin.recycle();
	            origin = null;
	        }
	        return newBM;
	 }
	 
	 /**
	     * 通过尝试打开相机的方式判断有无拍照权限（在6.0以下使用拥有root权限的管理软件可以管理权限）
	     *
	     * @return
	     */
	    public static boolean cameraIsCanUse() { 
	        boolean isCanUse = true;
	        Camera mCamera = null;
	        try {
	            mCamera = Camera.open();
	            Camera.Parameters mParameters = mCamera.getParameters();
	            mCamera.setParameters(mParameters);
	        } catch (Exception e) {
	            isCanUse = false;
	        }

	        if (mCamera != null) {
	            try {
	                mCamera.release();
	            } catch (Exception e) {
	                e.printStackTrace();
	                return isCanUse;
	            }
	        }
	        return isCanUse;
	    }
	    
	    public static int doStoI(String str)  
	    {  
	        int len = str.length();  
	        if(len==0)  
	        {  
	            System.out.println("输入整数型字符串为空！");  
	            System.exit(0);  
	        }  
	          
	        char[] s = str.toCharArray();//s用来存放字符串的单个字符；  
	        int[] a = new int[len];//a用来存放字符串每个字符转换成的单个整数；  
	          
	        //result用来存放转换成的整数，即最后的结果；  
	        //由于要考虑溢出的情况，所以把result先定义为long型；  
	        long result=0;  
	        int begin=0;//begin是字符串数组中开始转换成整型的开始下标；  
	        boolean sign = true;//输入字符串是否是正数的标志，初始化为是正数；  
	        if(s[0]=='+')  
	            begin=1;//若输入字符串前面有正负号，则从下一位开始转换为数字  
	        else if(s[0]=='-')  
	        {  
	            begin = 1;  
	            sign = false;//输入字符串是负数；  
	        }  
	         for(;begin<len;begin++)  
	         {  
	             if(s[begin]>='0'&&s[begin]<='9')//非法字符除外；  
	             {  
	                 a[begin]=s[begin]-'0';  
	                 result=result*10+a[begin];  
	             }  
	             else  
	             {  
	                 System.out.println("出现非法字符，请重新输入！");  
	                 System.exit(0);  
	             }  
	         }  
	           
	         if(!sign)  
	         {  
	             result*=-1;  
	         }  
	           
	        //int只能存32位，超出部分作溢出处理；  
	         if(result>Math.pow(2, 31)-1||result<-Math.pow(2, 31))  
	         {  
	             System.out.println("溢出，请重新输入！");  
	             System.exit(0);  
	         }  
	           
	        System.out.println("the corresponding integer is:");  
	        return (int)result;//强制转换long型的结果为int；  
	    }
	    
	    public static String[] deleteFirst(String[] arr) {
			String[] temp = new String[arr.length - 1];
			// 原数组  复制从指定的位置开始 新数组 
	        System.arraycopy(arr, 1, temp, 0, temp.length);
	        return temp;
	    }
}
