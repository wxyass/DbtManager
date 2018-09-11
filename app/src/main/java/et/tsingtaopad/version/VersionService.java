package et.tsingtaopad.version;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.core.net.HttpUrl;
import com.core.net.RestClient;
import com.core.net.callback.IError;
import com.core.net.callback.IFailure;
import com.core.net.callback.ISuccess;
import com.core.utils.dbtutil.DbtLog;
import com.core.utils.dbtutil.FunUtil;
import com.core.utils.dbtutil.JsonUtil;
import com.core.utils.dbtutil.PrefUtils;
import com.core.utils.dbtutil.PropertiesUtil;

import cn.com.benyoyo.manage.Struct.RequestStructBean;
import cn.com.benyoyo.manage.Struct.ResponseStructBean;
import cn.com.benyoyo.manage.Struct.common.RequestHeadStc;
import et.tsingtaopad.R;
import et.tsingtaopad.login.domain.BsVisitEmpolyeeStc;
import et.tsingtaopad.main.ConstValues;
import et.tsingtaopad.utils.HttpParseJson;
import et.tsingtaopad.version.domain.ApkStc;

/**
 * 文件名：VersionService.java</br>
 */
public class VersionService {
    
    public static final String TAG = "VersionService";
    protected Context context;
    protected Handler handler;

    String apkUrl = "http://oss.wxyass.com/tscs2.4.3.1.0.apk";
    String apkName = "tscs2.4.3.1.0.apk";
    String downPath = "dbt";// apk的存放位置

    public VersionService(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    // 获取是否需要升级
    public void getUrlData( String departmentid, String userid,String usercode) {  // "usercode:'" + usercode + "'" +


        String content = "{" +
                "areaid:'" + departmentid + "'," +
                "softversion:'" + DbtLog.getVersion() + "'," +
                "usercode:'" + usercode + "'," +
                "creuser:'" + userid + "'" +
                "}";

        String optcode = "opt_update_version";

        // 组建请求Json
        RequestStructBean reqObj = new RequestStructBean();
        reqObj.getReqHead().setOptcode(PropertiesUtil.getProperties(optcode));
        reqObj.getReqBody().setContent(content);

        // 压缩请求数据
        String jsonZip = HttpParseJson.parseRequestJson(reqObj);

        RestClient.builder()
                .url(PropertiesUtil.getProperties("platform_ip"))
                .params("datatest", jsonZip)
                //.loader(getContext())// 滚动条
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ResponseStructBean resObj = HttpParseJson.parseRes(response);

                        if (ConstValues.SUCCESS.equals(resObj.getResHead().getStatus())) {
                            // 保存信息
                            String formjson = resObj.getResBody().getContent();
                            parseTableJson(formjson);

                        } else {
                            // Toast.makeText(context, resObj.getResHead().getContent(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
                    }
                })
                .builde()
                .post();
    }

    // 解析数据
    private void parseTableJson(String formjson) {
        if(TextUtils.isEmpty(formjson)){
            handler.sendEmptyMessage(ConstValues.WAIT6);
            // Toast.makeText(context, "已是最新版本,无需更新", Toast.LENGTH_SHORT).show();
        }else{
            ApkStc info = JsonUtil.parseJson(formjson, ApkStc.class);

            apkUrl = info.getSoftpath();
            apkName = info.getSoftversion()+".apk";

            // 下载apk
            /*Bundle bundle = new Bundle();
            bundle.putString("apkUrl", apkUrl);//
            bundle.putString("apkName", apkName);//
            DownApkFragment downApkFragment = new DownApkFragment();
            downApkFragment.setArguments(bundle);
            // downLoadApk();
            handler.sendEmptyMessage(ConstValues.WAIT5);*/

            Bundle bundle = new Bundle();
            bundle.putString("apkUrl", apkUrl);
            bundle.putString("apkName", apkName);
            Message msg = new Message();
            msg.what = ConstValues.WAIT5;//
            msg.setData(bundle);
            handler.sendMessage(msg);
        }

        // 下载apk
        /*Bundle bundle = new Bundle();
        bundle.putString("apkUrl", apkUrl);
        bundle.putString("apkName", apkName);
        Message msg = new Message();
        msg.what = ConstValues.WAIT5;//
        msg.setData(bundle);
        handler.sendMessage(msg);*/

    }



}
