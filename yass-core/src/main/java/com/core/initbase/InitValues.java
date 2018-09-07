package com.core.initbase;

import android.Manifest;

import java.io.Serializable;

/**
 * 系统常量配置
 */
public class InitValues implements Serializable {

    // 版本序列号
    private static final long serialVersionUID = 3714072522003250341L;

    // 是否上线版本  ture：发布线上版本，不记录日志  |  false：测试版本，记录日志
    public static boolean isOnline = true;

    // 是否正在上传新增终端失败的终端
    public static boolean isUploadTermAdd = false;

    //今日要事是否允许提醒
    public static boolean isDayThingWarn = false;

    // 常用标识
    public static final String FLAG_0 = "0";
    public static final String FLAG_1 = "1";
    public static final String FLAG_2 = "2";
    public static final String FLAG_3 = "3";
    public static final String FLAG_4 = "4";

    // 成功、失败消息字符串常量
    public static final String SUCCESS = "M";
    public static final String ERROR = "E";

    // 同步数据
    public static final String SYNCDATA = "syncdata";

    // handler wait
    public static final int WAIT0 = 0;
    public static final int WAIT1 = 1;
    public static final int WAIT2 = 2;
    public static final int WAIT3 = 3;
    public static final int WAIT4 = 4;
    public static final int WAIT5 = 5;
    public static final int WAIT6 = 6;
    public static final int WAIT7 = 7;
    public static final int WAIT8 = 8;

    // 指标标删除状态（解决重复指标）
    public static final String delFlag = "8";

    // 登录信息缓存Key
    public static final String LOGINSESSIONKEY = "loginSesion";

    // 权限相关 ↓--------------------------------------------------------------------------
    /**
     * 权限常量相关
     */
    public static final int WRITE_READ_EXTERNAL_CODE = 0x01;// 读写内存卡权限请求码
    public static final int HARDWEAR_CAMERA_CODE = 0x02;// 相机权限请求码
    public static final int LOCAL_CODE = 0x03;// 定位请求码
    public static final int WRITE_LOCAL_CODE = 0x04;// 拍照+ 读写

    // 读写内存卡权限
    public static final String[] WRITE_READ_EXTERNAL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    // 相机权限
    public static final String[] HARDWEAR_CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    // 定位权限
    public static final String[] LOCAL_PERMISSION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    // 读写内存卡权限,相机权限,定位权限
    public static final String[] WRITE_EXTERNAL_CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    // 权限相关 ↑--------------------------------------------------------------------------

}
