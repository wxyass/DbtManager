package et.tsingtaopad.main;

import android.os.Handler;

import com.core.initbase.InitValues;
import com.core.initbase.KvStc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统常量配置
 */
public class ConstValues extends InitValues {


    // 当前有效的Handler
    public static Handler msgHandler;
    public static Handler handler;

    // 删除new LoginSession()  登录Session
    // public static LoginSession loginSession = new LoginSession();


    // KV表, K:表名， V：kv对象
    // public static Map<String, MstSynckvM> kvMap  = new HashMap<String, MstSynckvM>();

    // 数据字典
    public static Map<String, List<KvStc>> dataDicMap  = new HashMap<String, List<KvStc>>();

    // 省市县
    public static List<KvStc> provLst = new ArrayList<KvStc>();

    // 所属线路
    // public static List<MstRouteM> lineLst = new ArrayList<MstRouteM>();

    // 我品经销商供货关系
    public static List<KvStc> agencyMineLst = new ArrayList<KvStc>();

    // 竞品经销商供货关系
    public static List<KvStc> agencyVieLst = new ArrayList<KvStc>();

    // 次渠道列表
    public static List<KvStc> secSellLst = new ArrayList<KvStc>();

    // 指标、指标值关联关系
    public static List<KvStc> indexLst = new ArrayList<KvStc>();

    // 可拜访经销
    public static List<KvStc> agencyVisitLst = new ArrayList<KvStc>();

    //连接mq服务器创建的连接
    // public static IMqttClient mqttClient = null;

    //连接mq服务器创建的连接
    public static HashMap<String, Integer> AuthorityMap = new HashMap<String, Integer>();
    
}
