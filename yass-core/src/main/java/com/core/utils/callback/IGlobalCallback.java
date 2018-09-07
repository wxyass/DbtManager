package com.core.utils.callback;

import android.support.annotation.Nullable;

/**
 * Created by yangwenmin on 2017/12/23.
 */

public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);

}
