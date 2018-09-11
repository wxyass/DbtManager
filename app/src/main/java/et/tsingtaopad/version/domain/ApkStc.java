package et.tsingtaopad.version.domain;

import java.io.Serializable;

/**
 * 项目名称：营销移动智能工作平台 </br>
 * 日期      原因  BUG号    修改人 修改版本</br>
 */
public class ApkStc implements Serializable{
    
    private static final long serialVersionUID = 354445646363065993L;

    private String softpath;
    private String softversion;


    public String getSoftpath() {
        return softpath;
    }

    public void setSoftpath(String softpath) {
        this.softpath = softpath;
    }

    public String getSoftversion() {
        return softversion;
    }

    public void setSoftversion(String softversion) {
        this.softversion = softversion;
    }
}
