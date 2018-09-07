package et.tsingtaopad.utils;


import com.core.utils.dbtutil.CheckUtil;
import com.core.utils.dbtutil.GZIP;
import com.core.utils.dbtutil.JsonUtil;
import com.core.utils.dbtutil.PropertiesUtil;

import java.io.IOException;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import cn.com.benyoyo.manage.Struct.RequestStructBean;
import cn.com.benyoyo.manage.Struct.ResponseStructBean;
import et.tsingtaopad.main.ConstValues;

/**
 * Created by yangwenmin on 2017/12/7.
 */

public class HttpParseJson {

    public static final String TAG = "HttpParseJson";

    /**
     * 压缩请求json
     *
     * @param reqObj
     * @return
     */
    public static String parseRequestJson(RequestStructBean reqObj) {
        String jsonZip = "";

        try {
            // 不压缩
            // jsonZip = JsonUtil.toJson(reqObj);

            // 先压缩,在BASE64加密
            jsonZip = new BASE64Encoder().encode(GZIP.compress(JsonUtil.toJson(reqObj)).getBytes("UTF-8"));

        } catch (Exception e1) {
            //Log.e(TAG, "压缩上传数据JSON失败", e1);
        }
        return jsonZip;
    }

    /**
     * 解压、解析网络请求返回结果
     *
     * @param resContent 网络请求返回结果
     * @return
     */
    public static ResponseStructBean parseRes(String resContent) {
        ResponseStructBean resObj = new ResponseStructBean();
        //判断字符串是否为空或者null
        if (!CheckUtil.isBlankOrNull(resContent)) {
            String json;
            try {
                // 先BASE64解密,再解压
                json = GZIP.unCompress(new String(new BASE64Decoder().decodeBuffer(resContent), "UTF-8"));
                resObj = JsonUtil.parseJson(json, ResponseStructBean.class);
                // 促使内存释放
                json = null;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            resObj = new ResponseStructBean();
            resObj.getResHead().setStatus(ConstValues.ERROR);
            resObj.getResBody().setContent(PropertiesUtil.getProperties("msg_err_netfail"));
        }
        return resObj;
    }

}
