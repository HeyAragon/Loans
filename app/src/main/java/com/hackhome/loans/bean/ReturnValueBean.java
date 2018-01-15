package com.hackhome.loans.bean;

import java.io.Serializable;

/**
 * desc:
 * author: aragon
 * date: 2017/12/22 0022.
 */
public class ReturnValueBean implements Serializable{

    /**
     * ID : 2
     * advertiser : 厦门可可家信息技术有限公司
     * productName : 小乔借款
     * sortNumber : 0
     * startLoanMoney : 500
     * endLoanMoney : 10000
     * interestRateDay : 0.5
     * successRate : 89
     * securedLoan : 53164
     * productCharacteristic : 芝麻分600  轻松借5000
     * productAndroidUrl : https://mjseven.yongqianguanjia.com/?ref=jieqianmao2
     * productIosUrl : https://mjseven.yongqianguanjia.com/?ref=jieqianmao2
     * productH5Url : https://mjseven.yongqianguanjia.com/?ref=jieqianmao1
     * productImg : http://image.bianxianmao.com/2017/10/11/fd2be85fd63e46acad3127afa470c450.png
     * productIntroduce : 申请资格

     年龄：22-40周岁
     职业：有稳定收入的上班族
     运营商要求：手机实名制

     产品介绍
     借款用途：现金分期
     到账方式：银行卡
     所属平台：小乔借款
     * created : 2017-12-21 20:07:18
     * updated : 2017-12-21 20:07:18
     */

    private int ID;

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

    public String getStartLoanTime() {
        return startLoanTime;
    }

    public void setStartLoanTime(String startLoanTime) {
        this.startLoanTime = startLoanTime;
    }

    public String getEndLoanTime() {
        return endLoanTime;
    }

    public void setEndLoanTime(String endLoanTime) {
        this.endLoanTime = endLoanTime;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(String sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getStartLoanMoney() {
        return startLoanMoney;
    }

    public void setStartLoanMoney(String startLoanMoney) {
        this.startLoanMoney = startLoanMoney;
    }

    public String getEndLoanMoney() {
        return endLoanMoney;
    }

    public void setEndLoanMoney(String endLoanMoney) {
        this.endLoanMoney = endLoanMoney;
    }

    public String getInterestRateDay() {
        return interestRateDay;
    }

    public void setInterestRateDay(String interestRateDay) {
        this.interestRateDay = interestRateDay;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getSecuredLoan() {
        return securedLoan;
    }

    public void setSecuredLoan(String securedLoan) {
        this.securedLoan = securedLoan;
    }

    public String getProductCharacteristic() {
        return productCharacteristic;
    }

    public void setProductCharacteristic(String productCharacteristic) {
        this.productCharacteristic = productCharacteristic;
    }

    public String getProductAndroidUrl() {
        return productAndroidUrl;
    }

    public void setProductAndroidUrl(String productAndroidUrl) {
        this.productAndroidUrl = productAndroidUrl;
    }

    public String getProductIosUrl() {
        return productIosUrl;
    }

    public void setProductIosUrl(String productIosUrl) {
        this.productIosUrl = productIosUrl;
    }

    public String getProductH5Url() {
        return productH5Url;
    }

    public void setProductH5Url(String productH5Url) {
        this.productH5Url = productH5Url;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductIntroduce() {
        return productIntroduce;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
