package com.core.initbase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * kvStc
 */
public class KvStc implements Serializable {

    private static final long serialVersionUID = 3322789047184484841L;

    private String key;
    
    private String value;
    
    private String parentKey;
    
    private String isDefault;
    
    private List<KvStc> childLst = new ArrayList<KvStc>();
    
    public KvStc() {
        
    }
    
    public KvStc(String key, String value, String parentKey) {
        this.key = key;
        this.value = value;
        this.parentKey = parentKey;
    }
    
    public KvStc(String key, String value, String parentKey, String isDefault) {
        this.key = key;
        this.value = value;
        this.parentKey = parentKey;
        this.isDefault = isDefault;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public List<KvStc> getChildLst() {
        return childLst;
    }

    public void setChildLst(List<KvStc> childLst) {
        this.childLst = childLst;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
