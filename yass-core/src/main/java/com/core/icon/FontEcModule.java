package com.core.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * 添加自定义图标
 * 1 阿里矢量图
 * 2 记录每个图标的转义标记(\ue670), 新建EcIcons类统计
 * 3 下载ttf 放到assets中
 * 4 创建这个FontEcModule类
 * Created by yangwenmin on 2017/10/14.
 */

public class FontEcModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return EcIcons.values();
    }
}
