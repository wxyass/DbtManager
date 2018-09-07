package com.core.utils.dbtutil;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.TypeReference;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    
    /**
     * 私有化构造方法
     */
    private JsonUtil() {
        
    }
    
    /**
     * 将Object转换成JSON串
     * 
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        String json = "";
        if (obj != null) {
            try {
                json = JSON.toJSONString(obj);
            } catch (Exception e) {
            }
        }
        return json;
    }
    
    /**
     * 将JSON字符串转换成对象
     * 
     * @param json  要解析的JSON字符串
     * @param cls   要解析成的对象类型
     * @return
     */
    public static <T> T parseJson(String json, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(json, cls);
        } catch (Exception e) {
            Log.e("message", "yichang"+e.getMessage());
        }
        return t;
    }
    
    /**
     * 将JSON转成List数据集
     * 
     * @param json  JSON字符串
     * @param cls  要解析成的对象类型
     * @return
     */
    public static <T> List<T>parseList(String json, Class<T> cls) {
        List<T> lst = new ArrayList<T>();
        try {
            
            lst = (List<T>) JSON.parseArray(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
    
    /**
     * 将JSON串解析成List<Map<String, Object>>
     * 
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> parseListMap(String jsonString) {
        List<Map<String, Object>> lst = new ArrayList<Map<String, Object>>();
        try {
            
            lst = JSON.parseObject(jsonString, new
                         TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
        }
        return lst;
    }

    /**
     * 获取json中指定的值
     * 
     * @param json  JSON字符串
     * @param key   要获取的值对应的key
     * @return
     */
    public static Object getByKey(String json, String key) {
        
        try {
            
            Map<String, Object> jsonMap = JSON.parseObject(
                    json, new TypeReference<Map<String, Object>>() {});
            
            return jsonMap.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取json对应的Map对应
     * 
     * @param json  JSON字符串
     * @return
     */
    public static Map<String, Object> parseMap(String json) {
        
        try {
            
            return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 同步数据Json特殊处理（JSON太大）
     * 
     * @param json
     * @return
     */
    public static Map<String, Object> parseSyncDate(String json) {
        Map<String, Object> valMap = new HashMap<String, Object>();
        if (!CheckUtil.isBlankOrNull(json)) {
            JSONReader reader = new JSONReader(new StringReader(json));
            reader.startObject();
            while (reader.hasNext()) {
                valMap.put(reader.readString(), reader.readObject(String.class));
            }
            reader.endObject();
            reader.close();
        }
        return valMap;
    }
    public static void main(String[] args) {
    
        System.out.println(Integer.parseInt("66666677"));
        /*
         * 
         
        // Json转对象
        String strJson = "{\"areacode\":\"2\",\"areaname\":\"test2\"}";
        CmmAreaM objArea2 = JsonUtil.parseJson(strJson, CmmAreaM.class);
        System.out.println("测试 Json转对象:" + objArea2.toString());
        
        // List对象转Json
        List<CmmAreaM> lstCmmAreaMs = new ArrayList<CmmAreaM>();
        
        CmmAreaM objArea3 = new CmmAreaM();
        objArea3.setAreacode("3");
        objArea3.setAreaname("test3");
        lstCmmAreaMs.add(objArea3);
        
        CmmAreaM objArea4 = new CmmAreaM();
        objArea4.setAreacode("4");
        objArea4.setAreaname("test4");
        lstCmmAreaMs.add(objArea4);
        String strResult2 = JsonUtil.toJson(lstCmmAreaMs);
        System.out.println("测试 List对象转Json: " + strResult2);
        
        // Json转List
        String strListJson = "[{\"areacode\":\"3\",\"areaname\":\"test3\"},{\"areacode\":\"4\",\"areaname\":\"test4\"}]";
        List<CmmAreaM> lstCmmAreaMs2 = JsonUtil.parseList(strListJson, CmmAreaM.class);
        System.out.println("测试 Json转List 1: " + lstCmmAreaMs2);
        
        String strListJson2 = "['abc','def']";
        System.out.println("测试 Json转List 2: " + JsonUtil.parseList(strListJson2, String.class));
        
        // Map转Json
        Map<String, CmmAreaM> mapCmmAreaM = new HashMap<String, CmmAreaM>();
        mapCmmAreaM.put("002", objArea2);
        mapCmmAreaM.put("003", objArea3);
        mapCmmAreaM.put("004", objArea4);
        String strResult3 = JsonUtil.toJson(mapCmmAreaM);
        System.out.println("测试 Map转Json: " + strResult3);
        
        // Json转Map
        String strMap = "{\"002\":{\"areacode\":\"2\",\"areaname\":\"test2\"},\"003\":{\"areacode\":\"3\",\"areaname\":\"test3\"},\"004\":{\"areacode\":\"4\",\"areaname\":\"test4\"}}";
        @SuppressWarnings("rawtypes")
        Map mapObj = parseMap(strMap);
        System.out.println("测试 Json转Map:" + mapObj);
        
        // 得到Json中的结点
        String strJson2 = "{\"areacode\":\"3\",\"areaname\":\"test,3\"}";
        System.out.println("测试 得到Json中的结点: " + JsonUtil.getByKey(strJson2, "areaname"));
        
        // 测试其他
        String strTest = "{\"departmentid\":\"1,-4NWI\",\"gridId\":\"1-4I5U9R\",\"gridName\":\"柏乡镇\",\"loginDate\":\"2014-03-06 10:37:36\",\"userid\":\"90,01237\",\"username\":\"杨雪敏\"}";
        
        BsVisitEmpolyeeStc bsVisitEmpolyeeStc = JsonUtil.parseJson(strTest, BsVisitEmpolyeeStc.class);
        System.out.println(bsVisitEmpolyeeStc);
        System.out.println(JsonUtil.toJson(bsVisitEmpolyeeStc));
        
        BsVisitEmpolyeeStc stc = new BsVisitEmpolyeeStc();
        stc.setpDiscs("1-lLKD,2-ADF");
        stc.setDepartmentid("1-lLKD2-ADF");
        String json = JsonUtil.toJson(stc);
        System.out.println("测试逗号： " + json);
        
        // 将JSON串解析成List<Map<String, Object>>
        String strListMap = "[{\"002\":{\"areacode\":\"2\",\"areaname\":\"test2\"}},{\"003\":{\"areacode\":\"3\",\"areaname\":\"test3\"}},{\"004\":{\"areacode\":\"4\",\"areaname\":\"test4\"}}]";
        List<Map<String, Object>> lstMap = JsonUtil.parseListMap(strListMap);
        System.out.println("测试 将JSON串解析成List<Map<String, Object>>:" + lstMap);
        */
    }
}
