package et.tsingtaopad.login;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.core.net.RestClient;
import com.core.net.callback.IError;
import com.core.net.callback.IFailure;
import com.core.net.callback.ISuccess;
import com.core.utils.dbtutil.CheckUtil;
import com.core.utils.dbtutil.DateUtil;
import com.core.utils.dbtutil.FunUtil;
import com.core.utils.dbtutil.JsonUtil;
import com.core.utils.dbtutil.PrefUtils;
import com.core.utils.dbtutil.PropertiesUtil;

import java.util.Date;

import cn.com.benyoyo.manage.Struct.RequestStructBean;
import cn.com.benyoyo.manage.Struct.ResponseStructBean;
import et.tsingtaopad.R;
import et.tsingtaopad.login.domain.BsVisitEmpolyeeStc;
import et.tsingtaopad.main.ConstValues;
import et.tsingtaopad.utils.HttpParseJson;


/**
 *
 */
public class LoginService {

    private final String TAG = "LoginService";
    private final boolean TOMAINACTIVITY = false;// 直接进入MainActivity

    private Context context;
    private Handler handler;

    public LoginService(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    /**
     *
     */
    public LoginService() {
    }

    /**
     * 登录 - 判断用户输入是否正确
     *
     * @param uid     用户名
     * @param pwd     密码
     * @param version
     * @return
     */
    public void login(final String uid, final String pwd, String version) {

        int msgId = -1;

        String userGongHao = PrefUtils.getString(context, "userGongHao", "");

        if (CheckUtil.isBlankOrNull(uid)) {
            msgId = R.string.login_msg_invaluid;

        } else if (CheckUtil.isBlankOrNull(pwd)) {
            msgId = R.string.login_msg_invalpwd;

        } else if (!uid.equals(userGongHao) && userGongHao.length() > 0) {
            msgId = R.string.login_msg_invaluser;
        }

        // 弹出提示信息
        if (msgId != -1) {
            this.sendMsg(msgId, false, "登录失败不需要改密码");
        } else {

            // 获取PAD设备序列号，并拼接登录请求参数
            // final String padId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            final String padId = android.os.Build.SERIAL + "";
            StringBuffer buffer = new StringBuffer();
            buffer.append("{userid:'").append(uid);
            buffer.append("', password:'").append(pwd);
            buffer.append("', padid:'").append(padId);
            buffer.append("', version:'").append(version).append("'}");

            // 请求网络
            //toLogin("opt_get_login", uid, pwd, padId, buffer.toString());
            toLogin("opt_get_zglogin", uid, pwd, padId, buffer.toString());
        }
    }

    /**
     * 登录接口
     *
     * @param optCode  请求码
     * @param username
     * @param pwd
     * @param content  请求json
     */
    void toLogin(final String optCode, final String username, final String pwd, final String padId, String content) {

        RequestStructBean reqObj = new RequestStructBean();
        reqObj.getReqHead().setOptcode(PropertiesUtil.getProperties(optCode));
        reqObj.getReqBody().setContent(content);

        // 压缩请求数据
        String jsonZip = HttpParseJson.parseRequestJson(reqObj);

        RestClient.builder()
                .url(PropertiesUtil.getProperties("platform_ip"))
                //.params("datatest", jsonZip)
                .params("data", jsonZip)
                // .loader(LoginActivity.this)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ResponseStructBean resObj = HttpParseJson.parseRes(response);
                        // 保存登录信息
                        if (ConstValues.SUCCESS.equals(resObj.getResHead().getStatus())) {
                            // 保存登录者信息
                            BsVisitEmpolyeeStc emp = JsonUtil.parseJson(resObj.getResBody().getContent(), BsVisitEmpolyeeStc.class);
                            if (emp == null) {
                                sendMsg(R.string.login_msg_usererror, false, "登录失败不需要改密码");//服务器回应的内容为空时界面会收到   用户信息异常，不能正常登录
                            } else {
                                // 保存登录信息到PrefUtils
                                saveLoginSession(emp, pwd, padId);
                                // 跳转到平台主界面  //Isrepassword:剩余多少天修改密码 2393版本返回null
                                sendMsg(R.string.login_msg_offline_manager, true, emp.getIsrepassword());
                            }

                        } else {
                            String msg = FunUtil.isNullSetSpace(resObj.getResBody().getContent());
                            if (msg.contains("用户已离职或冻结")) {
                                PrefUtils.putString(context, "userStatus", ConstValues.FLAG_1);
                            } else if (msg.contains("设备已冻结")) {
                                PrefUtils.putString(context, "devStatus", ConstValues.FLAG_1);
                            }
                            sendMsg(msg, false, "登录失败不需要改密码");
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        // 离线逻辑
                        //offLineLogin(username, pwd);
                        sendMsg(R.string.msg_err_netfail, false, "登录失败不需要改密码");
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        // 离线逻辑
                        //offLineLogin(username, pwd);
                        sendMsg(R.string.msg_err_netfail, false, "登录失败不需要改密码");
                    }
                })
                .builde()
                .post();
    }

    // 离线逻辑
    private void offLineLogin(String username, String pwd) {
        if (TOMAINACTIVITY) {
            // 离线登录
            sendMsg2(R.string.login_msg_offline_manager, true, "11000");//离线登录成功
        } else {
            // 网络原因登录失败后，用离线方式登录
            if (ConstValues.FLAG_1.equals(PrefUtils.getString(context, "userStatus", ""))) {
                //状态为1时的情况   该用户已离职或冻结！
                sendMsg(R.string.login_msg_userice, false, "登录失败不需要改密码");
            } else {
                if (ConstValues.FLAG_1.equals(PrefUtils.getString(context, "devStatus", ""))) {
                    // 设置冻结状态 : 0:未冻结，1:已冻结
                    sendMsg(R.string.login_msg_device, false, "登录失败不需要改密码");
                } else {
                    if (CheckUtil.isBlankOrNull(PrefUtils.getString(context, "userCode", ""))) {
                        // 如果缓存的用户名是空，视为第一次登录
                        sendMsg(R.string.msg_err_netfail, false, "登录失败不需要改密码");
                    } else {
                        // 离线登录 判定与上次登录密码是否一至, 是则跳转平台主界面
                        if (username.equals(PrefUtils.getString(context, "userGongHao", "")) && pwd.equals(PrefUtils.getString(context, "userPwd", ""))) {
                            PrefUtils.putString(context, "loginDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
                            sendMsg(R.string.login_msg_offline, true, "11000");//离线登录成功
                        } else {
                            sendMsg(R.string.login_msg_pwdfail, false, "登录失败不需要改密码");
                        }
                    }
                }
            }
        }

    }

    /**
     * 登录成功(包含离线登录成功) 向界面发送提示消息
     *
     * @param msg
     * @param isSuccess    是否跳转
     * @param isrepassword 是否修改密码 0:不需要 1:需要
     */
    private void sendMsg(Object msg, boolean isSuccess, String isrepassword) {
        Bundle bundle = new Bundle();
        Message message = new Message();
        bundle.putBoolean("isSuccess", isSuccess);
        bundle.putString("isrepassword", isrepassword);
        if (msg instanceof Integer) {
            bundle.putString("msg", context.getString(Integer.valueOf(msg.toString())));
        } else {
            bundle.putString("msg", String.valueOf(msg));
        }
        message.what = ConstValues.WAIT1;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    // 直接进入MainActivity
    private void sendMsg2(Object msg, boolean isSuccess, String isrepassword) {
        Bundle bundle = new Bundle();
        Message message = new Message();
        bundle.putBoolean("isSuccess", isSuccess);
        bundle.putString("isrepassword", isrepassword);
        if (msg instanceof Integer) {
            bundle.putString("msg", context.getString(Integer.valueOf(msg.toString())));
        } else {
            bundle.putString("msg", String.valueOf(msg));
        }
        message.what = ConstValues.WAIT2;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    /**
     * 记录登录者信息
     *
     * @param emp 登录成功后，返回的当前用户信息
     */
    private void saveLoginSession(BsVisitEmpolyeeStc emp, String pwd, String padId) {
        if (emp != null) {
            // 因为老是报错,怀疑loginsession保存出错,重新保存 修改  20170317
            PrefUtils.putString(context, "userCode", emp.getUserid());
            PrefUtils.putString(context, "padId", padId);
            PrefUtils.putString(context, "userName", emp.getUsername());
            PrefUtils.putString(context, "userGongHao", emp.getUserengname());
            PrefUtils.putString(context, "userPwd", pwd);
            PrefUtils.putString(context, "disId", emp.getDepartmentid());
            PrefUtils.putString(context, "gridId", emp.getGridId());
            PrefUtils.putString(context, "gridName", emp.getGridName());
            PrefUtils.putString(context, "loginDate", emp.getLoginDate());
            PrefUtils.putString(context, "pDiscs", emp.getpDiscs());
            PrefUtils.putString(context, "isDel", emp.getIsDel());
            PrefUtils.putString(context, "tel", emp.getTel());
            PrefUtils.putString(context, "userStatus", ConstValues.FLAG_0);
            PrefUtils.putString(context, "devStatus", ConstValues.FLAG_0);

            // 保存用户权限到缓存
            PrefUtils.putString(context, "bfgl", emp.getBfgl());
            PrefUtils.putString(context, "yxgl", emp.getYxgl());
            PrefUtils.putString(context, "xtgl", emp.getXtgl());
        }
    }
}
