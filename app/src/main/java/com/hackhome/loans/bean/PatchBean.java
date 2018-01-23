package com.hackhome.loans.bean;

import java.io.Serializable;

/**
 * desc:
 * author: aragon
 * date: 2018/1/22 0022.
 */
public class PatchBean implements Serializable {

    private String patchName;
    private String patchUrl;
    private String patchVersion;

    public String getPatchVersion() {
        return patchVersion;
    }

    public void setPatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    public String getPatchName() {
        return patchName;
    }

    public void setPatchName(String patchName) {
        this.patchName = patchName;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }
}
