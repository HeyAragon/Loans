package com.hackhome.loans.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * desc:
 * author: aragon
 * date: 2017/11/9 0009.
 */
@Entity
public class DownloadRecordModel {
    @Id
    private Long id;
    private int state;
    private long total;
    private int taskId;
    private int btnPos;
    private String name;
    private String url;
    private String path;
    private String pkgName;
    private String iconUrl;
    private String subContent;
    private boolean isInstalled;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    @Generated(hash = 225489115)
    public DownloadRecordModel(Long id, int state, long total, int taskId,
            int btnPos, String name, String url, String path, String pkgName,
            String iconUrl, String subContent, boolean isInstalled) {
        this.id = id;
        this.state = state;
        this.total = total;
        this.taskId = taskId;
        this.btnPos = btnPos;
        this.name = name;
        this.url = url;
        this.path = path;
        this.pkgName = pkgName;
        this.iconUrl = iconUrl;
        this.subContent = subContent;
        this.isInstalled = isInstalled;
    }

    @Generated(hash = 342392163)
    public DownloadRecordModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getTaskId() {
        return this.taskId;
    }
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    public int getBtnPos() {
        return this.btnPos;
    }
    public void setBtnPos(int btnPos) {
        this.btnPos = btnPos;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public int getState() {
        return this.state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public String getPkgName() {
        return this.pkgName;
    }
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
    public String getIconUrl() {
        return this.iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public boolean getIsInstalled() {
        return this.isInstalled;
    }
    public void setIsInstalled(boolean isInstalled) {
        this.isInstalled = isInstalled;
    }
    public String getSubContent() {
        return this.subContent;
    }
    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

}
