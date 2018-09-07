package com.core.utils.file;

import android.os.Environment;

/**
 * Created by gui on 2017/11/2.
 */

public class SDCardUtil {
    private static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();

    public static String getSDCardPath() {
        return SDCARD_DIR;
    }
}
