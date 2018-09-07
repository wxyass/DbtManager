package com.core.utils.dbtutil;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 常用校验工具类
 *
 */
public class CheckUtil {
    
    /**
     * 判断数组是否为空
     * 
     * @param 
     * @return true 为空 false 不为空
     */
    public static boolean IsEmpty(Object[] ObjIn){
        
        return (ObjIn == null || ObjIn.length == 0);
    }

    /**
     * 判断数组是否为空
     * 
     * @param 
     * @return true 为空 false 不为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean IsEmpty(Collection colIn){
        
        return (colIn == null || colIn.isEmpty());
    }
    
    /**
     * 判断Map是否为空
     * 
     * @param 
     * @return true 为空 false 不为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean IsEmpty(Map mapIn){
        
        return (mapIn == null || mapIn.isEmpty());
    }
    
    /**
     * 判断字符串是否为空或者null
     * 
     * @param strParam  验证参数
     * @return 为空或null时返回True
     */
    public static boolean isBlankOrNull(String strParam) {
        return (strParam == null) || (strParam.trim().length() == 0);
    }

    /**
     * 判断是否为整数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isInteger(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^-?\\d+$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为（非零的）正整数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isPosInteger(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为（非零的）负整数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNegInteger(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^\\-[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为非负整数（正整数 + 0）  
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNonNegInteger(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^\\d+$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为非正整数（负整数 + 0）
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNonPosInteger(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^((-\\d+)|(0+))$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为浮点数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isFloat(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为非负浮点数（正浮点数 + 0）
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNonNegFloat(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^\\d+(\\.\\d+)?$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为正浮点数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isPosFloat(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为非正浮点数（负浮点数 + 0）
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNonPosFloat(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否为负浮点数
     * 
     * @param strNum 待查字符串
     * @return 是则返回true
     */
    public static boolean isNegFloat(String strNum) {
        
        // 空串不为数字
        if (null == strNum || "".equals(strNum))
            return false;
        Pattern pattern = Pattern.compile("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$");
        Matcher isNum = pattern.matcher(strNum);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为数值
     * 包含 正负号和小数点
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        char c;
        int pointNum = 0;
        for (int i = str.length(); --i >= 0;) {
            c = str.charAt(i);
            // 是否为数字
            if ((c <= 0x0039 && c >= 0x0030) == false)
                // 是否为小数点
                if (c == 0x002e) {
                    // 是否只有一个小数点
                    if (pointNum == 0) {
                        pointNum++;
                    } else {
                        // 超过一个小数点时出错
                        return false;
                    }
                } else {
                    // 是否为减号或加号且在最开头
                    if (c != 0x002d && c != 0x002b || i != 0) {
                        // 都不是时出错
                        return false;
                    }
                }
        }
        return true;
    }
   
    
    /**
     * 判断是否为日期
     * 
     * @param strDate 需要解析的日期字符串
     * @return 合法日期返回true
     */
    public static boolean isDate(String strDate) {
        if (null == strDate || "".equals(strDate)) return false;
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 判断是否日期一是日期二的未来日或同一时刻                                         <br />
     *                                                      <br />
     * @param iType     日期类型                                                                            <br />
     * ==================================================== <br />
     *   Type       格式                                                                                                 <br />
     *     0        yyyyMMddHHmmss                          <br />
     *     1        yyyyMMdd HH:mm:ss                       <br />
     *     2        yyyyMMdd HH:mm                          <br />
     *     3        yyyyMMdd                                <br />
     * ==================================================== <br />
     * @param strDate1 日期字符串一                                                                     <br />
     * @param strDate2 日期字符串二                                                                     <br />
     * @return 是则返回true                                  <br />
     */
    @SuppressLint("SimpleDateFormat")
	public static boolean checkDateDiff(int iType, 
            String strDate1, String strDate2) throws Exception {
        
        // 将字符串转为日期类型
        SimpleDateFormat oriDateFormat = new SimpleDateFormat();
        
        switch (iType) {
        case 0:
            oriDateFormat.applyPattern("yyyyMMddHHmmss");
            break;
        case 1:
            oriDateFormat.applyPattern("yyyyMMdd HH:mm:ss");
            break;
        case 2:
            oriDateFormat.applyPattern("yyyyMMdd HH:mm");
            break;
        case 3:
            oriDateFormat.applyPattern("yyyyMMdd");
            break;
        default:
            oriDateFormat.applyPattern("yyyyMMdd");
        }
        Date date1;
        Date date2;
        
        try {
            date1 = oriDateFormat.parse(strDate1);
        } catch (Exception e) {
            Log.e("checkDateDiff", "无法将[" + strDate1 
                    		+ "]转为[yyyyMMdd HH:mm:ss]格式的日期", e);
            throw e;
        }
        
        try {
            date2 = oriDateFormat.parse(strDate2);
            return (date1.getTime() - date2.getTime()) >= 0;
        } catch (Exception e1) {
        	Log.e("checkDateDiff", "无法将[" + strDate2 
                    		+ "]转为[yyyyMMdd HH:mm:ss]格式的日期", e1);
            throw e1;
        }
    }
    
    /**
     * 判断是否为指定格式的日期
     * 
     * @param strDate 需要解析的日期字符串
     * @param pattern 日期字符串的格式，默认为“yyyy-MM-dd”的形式
     * @return 合法日期返回true
     */
    @SuppressLint("SimpleDateFormat")
	public static boolean checkDateFormat(String strDate, String pattern) {
        
        // 将字符串转为日期类型
        SimpleDateFormat sdf = new SimpleDateFormat();
        if (CheckUtil.isBlankOrNull(pattern)) {
        	sdf.applyPattern("yyyy-MM-dd");
        }
        sdf.setLenient(false);
        try {
            if (strDate.length() == sdf.toPattern().length()) {
                sdf.parse(strDate);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        
    }
    
    /**
     * 判断字符串长度是否合法
     * 
     * @param str 待检查字符串
     * @param length 要求的长度
     * @return 符合长度要求返回true
     */
    public static boolean checkStrLength(String str, int length) {
        if (null == str) str = "";
        return str.length() <= length;
    }
    
    /**
     * 判断字符串范围是否合法
     * 
     * @param strNum 待检查字符串
     * @param min 最小值
     * @param max 最大值
     * @return 符合范围返回true
     */
    public static boolean checkStrRange(String strNum, int min, int max) {
        if (null == strNum) strNum = "";
        int intNum;
        try {
            intNum = Integer.parseInt(strNum);
            return intNum >= min && intNum <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 判断性别是否与身份证号中的信息匹配
     * 
     * @param strGender 性别 0：男 1：女
     * @param strId 身份证号码
     * @return 是则返回true
     */
    public static boolean checkGenderInIDCard(String strGender, String strId) {
        if (null == strGender || null == strId 
                || "".equals(strGender) || strId.length() < 15)
            
            return false;
        
        // 获取性别 
        String id17 = strId.substring(16, 17);
        String strIDGender = "";
        if (Integer.parseInt(id17) % 2 != 0) {    
            strIDGender = "0";    
        } else {    
            strIDGender = "1";
        }
        return strIDGender.equals(strGender);
    }
    
    /**
     * 判断出生日期是否与身份证号中的信息匹配
     * 
     * @param strDate 出生日期 格式 “yyyyMMdd”
     * @param strId 身份证号码
     * @return 是则返回true
     */
    @SuppressLint("SimpleDateFormat")
	public static boolean checkBirthIDCard(String strDate, String strId) {
        if (null == strDate || null == strId 
                || "".equals(strDate) || strId.length() < 15)
            
            return false;
        
        // 获取出生日期
        String birthday = strId.substring(6, 14);
        try {
            Date date = new SimpleDateFormat("yyyyMMdd").parse(strDate);
            Date birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
            return (birthdate.getTime() - date.getTime()) == 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
	 * 正则过滤
	 * 
	 * @param str
	 * @return 返回过滤后的字符串
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		//String regEx = "[/\\:*?<>|\"\n\t]";
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）_——+|{}【】‘；：”“’。，、？✘×√‖]"; 
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("");
		
	}
}
