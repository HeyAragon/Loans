package com.hackhome.loans.common.utils;

import com.hackhome.loans.BuildConfig;

import okhttp3.HttpUrl;

/**
 * desc:
 * author: aragon
 * date: 2018/1/4 0004.
 */
public class AppConfig {

    private static class ConfigHolder{
        private final static AppConfig instance = new AppConfig();
    }

    private AppConfig(){
    }

    public static AppConfig getInstance() {
        return ConfigHolder.instance;
    }

    private HttpUrl currentHttpUrl;

    private String CurrentVersion = BuildConfig.VERSION_NAME;

    private int CurrentVersionCode = BuildConfig.VERSION_CODE;

    private String newVersion;

    private String updateMsg;

    private String updateUrl;

    private int newVersionCode;

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }



    public String getCurrentVersion() {
        return CurrentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        CurrentVersion = currentVersion;
    }

    public int getCurrentVersionCode() {
        return CurrentVersionCode;
    }

    public void setCurrentVersionCode(int currentVersionCode) {
        CurrentVersionCode = currentVersionCode;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public int getNewVersionCode() {
        return newVersionCode;
    }

    public void setNewVersionCode(int newVersionCode) {
        this.newVersionCode = newVersionCode;
    }

    public HttpUrl getCurrentHttpUrl() {
        return currentHttpUrl;
    }

    public void setCurrentHttpUrl(HttpUrl currentHttpUrl) {
        this.currentHttpUrl = currentHttpUrl;
    }



}
