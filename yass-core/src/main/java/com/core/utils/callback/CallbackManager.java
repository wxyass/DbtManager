package com.core.utils.callback;

import java.util.WeakHashMap;

/**
 * Created by yangwenmin on 2017/12/23.
 */

public class CallbackManager {

    private static final WeakHashMap<Object, IGlobalCallback> CALLBACK = new WeakHashMap<>();

    // 单例
    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    // 在集合中添加一个IGlobalCallback
    public CallbackManager addCallback(Object key,IGlobalCallback callback) {
        CALLBACK.put(key,callback);
        return this;
    }

    // 从集合中根据key取出IGlobalCallback
    public IGlobalCallback getCallback(Object key){
        return CALLBACK.get(key);
    }

}
