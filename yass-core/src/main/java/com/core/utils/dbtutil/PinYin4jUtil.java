package com.core.utils.dbtutil;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * 拼音工具类
 */
public class PinYin4jUtil {


    /**
     * 汉字转换位汉语拼音首字母，英文字符不变，特殊字符丢失 支持多音字，
     * 生成方式如（长沙市长:cssc,zssz,zssc,cssz）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToFirstSpell(String chines) {
        String pinyin = "";
        try {
            pinyin = PinyinHelper.getShortPinyin(chines);
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return pinyin;
    }


    /**
     * 汉字转换位汉语全拼，英文字符不变，特殊字符丢失
     * 支持多音字，生成方式如（重当参:zhongdangcen,zhongdangcan,chongdangcen
     * ,chongdangshen,zhongdangshen,chongdangcan）
     *
     * @param chines
     *            汉字
     * @return 拼音
     */
    public static String converterToSpell(String chines) {
        String pinyin = "";
        try {
            //pinyin =PinyinHelper.convertToPinyinString(chines, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
            pinyin =PinyinHelper.convertToPinyinString(chines, "", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
        } catch (PinyinException e) {
            e.printStackTrace();
        }
        return pinyin;
    }

    /**
     * 去除多音字重复数据
     * 
     * @param theStr
     * @return
     */
    private static List<Map<String, Integer>> discountTheChinese(String theStr) {
        // 去除重复拼音后的拼音列表
        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
        // 用于处理每个字的多音字，去掉重复
        Map<String, Integer> onlyOne = null;
        String[] firsts = theStr.split(" ");
        // 读出每个汉字的拼音
        for (String str : firsts) {
            onlyOne = new Hashtable<String, Integer>();
            String[] china = str.split(",");
            // 多音字处理
            for (String s : china) {
                Integer count = onlyOne.get(s);
                if (count == null) {
                    onlyOne.put(s, Integer.valueOf(1));
                } else {
                    onlyOne.remove(s);
                    count++;
                    onlyOne.put(s, count);
                }
            }
            mapList.add(onlyOne);
        }
        return mapList;
    }

    /**
     * 解析并组合拼音，对象合并方案(推荐使用)
     * 
     * @return
     */
    private static String parseTheChineseByObject(
            List<Map<String, Integer>> list) {
        Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
        // 遍历每一组集合
        for (int i = 0; i < list.size(); i++) {
            // 每一组集合与上一次组合的Map
            Map<String, Integer> temp = new Hashtable<String, Integer>();
            // 第一次循环，first为空
            if (first != null) {
                // 取出上次组合与此次集合的字符，并保存
                for (String s : first.keySet()) {
                    for (String s1 : list.get(i).keySet()) {
                        String str = s + s1;
                        temp.put(str, 1);
                    }
                }
                // 清理上一次组合数据
                if (temp != null && temp.size() > 0) {
                    first.clear();
                }
            } else {
                for (String s : list.get(i).keySet()) {
                    String str = s;
                    temp.put(str, 1);
                }
            }
            // 保存组合数据以便下次循环使用
            if (temp != null && temp.size() > 0) {
                first = temp;
            }
        }
        String returnStr = "";
        if (first != null) {
            // 遍历取出组合字符串
            for (String str : first.keySet()) {
                returnStr += (str + ",");
            }
        }
        if (returnStr.length() > 0) {
            returnStr = returnStr.substring(0, returnStr.length() - 1);
        }
        return returnStr;
    }

}
