package com.hackhome.loans.bean;

import java.io.Serializable;

/**
 * desc:
 * author: aragon
 * date: 2017/12/28 0028.
 */
public class UpdateBean implements Serializable{

    /**
     * ver : 1.0
     * vcode : 1
     * url : http://xxxxxx
     */

    private String ver;
    private String vcode;
    private String url;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
