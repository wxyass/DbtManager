package com.core.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by yangwenmin on 2017/10/14.
 */

public enum EcIcons implements Icon{

    icon_scan('\ue670'),
    icon_ali_pay('\ue74f');


    private  char character;
    EcIcons(char character){
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
