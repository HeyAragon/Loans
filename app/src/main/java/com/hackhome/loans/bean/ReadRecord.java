package com.hackhome.loans.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * desc:
 * author: aragon
 * date: 2018/1/8 0008.
 */
@Entity
public class ReadRecord {
    @Id
    private Long ID;

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
    @Generated(hash = 28293028)
    public ReadRecord(Long ID, String packageName, String advertiser,
            String productName, String sortNumber, String startLoanMoney,
            String endLoanMoney, String interestRateDay, String successRate,
            String securedLoan, String productCharacteristic,
            String productAndroidUrl, String productIosUrl, String productH5Url,
            String productImg, String productIntroduce, String created,
            String updated, String endLoanTime, String startLoanTime) {
        this.ID = ID;
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
    @Generated(hash = 1191129215)
    public ReadRecord() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
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
