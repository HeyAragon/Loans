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

    //temp
    private String packageName;
    private String advertiser;
    private String productName;
    private String sortNumber;
    private String startLoanMoney;
    private String endLoanMoney;
    private String interestRateDay;
    private String successRate;
    private String securedLoan;
    private String productCharacteristic;
    private String productAndroidUrl;
    private String productIosUrl;
    private String productH5Url;
    private String productImg;
    private String productIntroduce;
    private String created;
    private String updated;
    private String endLoanTime;
    private String startLoanTime;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


    @Generated(hash = 289186250)
    public DownloadRecordModel(Long id, int state, long total, int taskId,
            int btnPos, String name, String url, String path, String pkgName,
            String iconUrl, String subContent, boolean isInstalled,
            String packageName, String advertiser, String productName,
            String sortNumber, String startLoanMoney, String endLoanMoney,
            String interestRateDay, String successRate, String securedLoan,
            String productCharacteristic, String productAndroidUrl,
            String productIosUrl, String productH5Url, String productImg,
            String productIntroduce, String created, String updated,
            String endLoanTime, String startLoanTime) {
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
        this.packageName = packageName;
        this.advertiser = advertiser;
        this.productName = productName;
        this.sortNumber = sortNumber;
        this.startLoanMoney = startLoanMoney;
        this.endLoanMoney = endLoanMoney;
        this.interestRateDay = interestRateDay;
        this.successRate = successRate;
        this.securedLoan = securedLoan;
        this.productCharacteristic = productCharacteristic;
        this.productAndroidUrl = productAndroidUrl;
        this.productIosUrl = productIosUrl;
        this.productH5Url = productH5Url;
        this.productImg = productImg;
        this.productIntroduce = productIntroduce;
        this.created = created;
        this.updated = updated;
        this.endLoanTime = endLoanTime;
        this.startLoanTime = startLoanTime;
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

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAdvertiser() {
        return this.advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSortNumber() {
        return this.sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getStartLoanMoney() {
        return this.startLoanMoney;
    }

    public void setStartLoanMoney(String startLoanMoney) {
        this.startLoanMoney = startLoanMoney;
    }

    public String getEndLoanMoney() {
        return this.endLoanMoney;
    }

    public void setEndLoanMoney(String endLoanMoney) {
        this.endLoanMoney = endLoanMoney;
    }

    public String getInterestRateDay() {
        return this.interestRateDay;
    }

    public void setInterestRateDay(String interestRateDay) {
        this.interestRateDay = interestRateDay;
    }

    public String getSuccessRate() {
        return this.successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getSecuredLoan() {
        return this.securedLoan;
    }

    public void setSecuredLoan(String securedLoan) {
        this.securedLoan = securedLoan;
    }

    public String getProductCharacteristic() {
        return this.productCharacteristic;
    }

    public void setProductCharacteristic(String productCharacteristic) {
        this.productCharacteristic = productCharacteristic;
    }

    public String getProductAndroidUrl() {
        return this.productAndroidUrl;
    }

    public void setProductAndroidUrl(String productAndroidUrl) {
        this.productAndroidUrl = productAndroidUrl;
    }

    public String getProductIosUrl() {
        return this.productIosUrl;
    }

    public void setProductIosUrl(String productIosUrl) {
        this.productIosUrl = productIosUrl;
    }

    public String getProductH5Url() {
        return this.productH5Url;
    }

    public void setProductH5Url(String productH5Url) {
        this.productH5Url = productH5Url;
    }

    public String getProductImg() {
        return this.productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductIntroduce() {
        return this.productIntroduce;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getEndLoanTime() {
        return this.endLoanTime;
    }

    public void setEndLoanTime(String endLoanTime) {
        this.endLoanTime = endLoanTime;
    }

    public String getStartLoanTime() {
        return this.startLoanTime;
    }

    public void setStartLoanTime(String startLoanTime) {
        this.startLoanTime = startLoanTime;
    }

}
