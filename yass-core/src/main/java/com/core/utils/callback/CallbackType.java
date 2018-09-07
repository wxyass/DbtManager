package com.core.utils.callback;

/**
 * Created by yangwenmin on 2017/12/23.
 * 全局回调,不同的回调事件,对应不同的标记
 */

public enum CallbackType {
    ON_CROP,// 裁剪成功的处理 对应的回调标记
    TAG_OPEN_PUSH,
    TAG_STOP_PUSH,
    ON_SCAN
}
