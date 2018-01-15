package com.hackhome.loans.bean;

import com.hackhome.loans.common.download.DownloadHelperT;

import java.io.Serializable;
import java.util.List;

/**
 * desc:
 * author: aragon
 * date: 2017/12/22 0022.
 */
public class DataBean implements Serializable{

    /**
     * success : true
     * Banners : [{"ID":1,"bannerName":"申请领红包活动","images":"http://image.bianxianmao.com/2017/11/22/33b3438998b4403cb9accb4fc52c1ed5.png","linkeUrl":"https://m.bianxianmao.com?appKey=c0f114048d3b476b80c029f510dcb46c&appType=app&appEntrance=1&business=money&i=__IMEI__&f=__IDFA__"},{"ID":2,"bannerName":"95金融","images":"http://image.bianxianmao.com/2017/10/25/4f7e0b7f194a46d0acd47cf9fda63673.jpg","linkeUrl":"https://mjseven.yongqianguanjia.com/?ref=jieqianmao1"}]
     * returnValue : [{"ID":1,"advertiser":"保街（上海）信息科技有限公司","productName":"借云借款","sortNumber":"0","startLoanMoney":"1000","endLoanMoney":"20000","interestRateDay":"0.03","successRate":"90","securedLoan":"59017","productCharacteristic":"1W-20W 额度高 放款快","productAndroidUrl":"http://daikuan.kanzhundai.com/m/daikuan/multiple/index.jsp?s=44&c=355","productIosUrl":"http://daikuan.kanzhundai.com/m/daikuan/multiple/index.jsp?s=44&c=355","productH5Url":"http://daikuan.kanzhundai.com/m/daikuan/multiple/index.jsp?s=44&c=354","productImg":"http://image.bianxianmao.com/2017/10/11/67315bad1ef147ceaf655372d4d78d31.png","productIntroduce":"申请资格\r\n\r\n芝麻粉580分以上\r\n年龄：22周岁-40周岁\r\n提供实名认证手机号，在网6个月以上\r\n大陆居民，征信良好","created":"2017-12-21 19:42:48","updated":"2017-12-22 10:38:26"},{"ID":2,"advertiser":"厦门可可家信息技术有限公司","productName":"小乔借款","sortNumber":"0","startLoanMoney":"500","endLoanMoney":"10000","interestRateDay":"0.5","successRate":"89","securedLoan":"53164","productCharacteristic":"芝麻分600  轻松借5000","productAndroidUrl":"https://mjseven.yongqianguanjia.com/?ref=jieqianmao2","productIosUrl":"https://mjseven.yongqianguanjia.com/?ref=jieqianmao2","productH5Url":"https://mjseven.yongqianguanjia.com/?ref=jieqianmao1","productImg":"http://image.bianxianmao.com/2017/10/11/fd2be85fd63e46acad3127afa470c450.png","productIntroduce":"申请资格\r\n\r\n年龄：22-40周岁\r\n职业：有稳定收入的上班族\r\n运营商要求：手机实名制\r\n\r\n产品介绍\r\n借款用途：现金分期\r\n到账方式：银行卡\r\n所属平台：小乔借款","created":"2017-12-21 20:07:18","updated":"2017-12-21 20:07:18"},{"ID":6,"advertiser":"信征（北京）科技有限公司","productName":"花花白卡","sortNumber":"0","startLoanMoney":"1000","endLoanMoney":"5000","interestRateDay":"0.05","successRate":"90","securedLoan":"71155","productCharacteristic":"借钱不求人  这里你是爷","productAndroidUrl":"http://hhbk.jjkswl.com/login/proxyReg.xhtm?proxyCode=A100150","productIosUrl":"http://hhbk.jjkswl.com/login/proxyReg.xhtm?proxyCode=A100150","productH5Url":"http://hhbk.jjkswl.com/login/proxyReg.xhtm?proxyCode=A100151","productImg":"http://image.bianxianmao.com/2017/10/20/f6ee7fae30d34d8ca24ef415a4aea04d.png","productIntroduce":"申请资格\r\n\r\n年龄：22-45周岁\r\n职业：有稳定收入的上班族\r\n运营商要求：手机实名制\r\n\r\n产品介绍\r\n借款用途：现金分期\r\n到账方式：银行卡\r\n所属平台：花花白卡","created":"2017-12-22 9:45:18","updated":"2017-12-22 10:40:20"}]
     */

    private int pages;
    private int pageNum;
    private int pageSize;
    private boolean success;
    private List<BannersBean> Banners;
    private List<ReturnValueBean> returnValue;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<BannersBean> getBanners() {
        return Banners;
    }

    public void setBanners(List<BannersBean> Banners) {
        this.Banners = Banners;
    }

    public List<ReturnValueBean> getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(List<ReturnValueBean> returnValue) {
        this.returnValue = returnValue;
    }

    public static class BannersBean implements Serializable{
        /**
         * ID : 1
         * bannerName : 申请领红包活动
         * images : http://image.bianxianmao.com/2017/11/22/33b3438998b4403cb9accb4fc52c1ed5.png
         * linkeUrl : https://m.bianxianmao.com?appKey=c0f114048d3b476b80c029f510dcb46c&appType=app&appEntrance=1&business=money&i=__IMEI__&f=__IDFA__
         */

        private int ID;
        private String bannerName;
        private String images;
        private String linkeUrl;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getBannerName() {
            return bannerName;
        }

        public void setBannerName(String bannerName) {
            this.bannerName = bannerName;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getLinkeUrl() {
            return linkeUrl;
        }

        public void setLinkeUrl(String linkeUrl) {
            this.linkeUrl = linkeUrl;
        }
    }

}
