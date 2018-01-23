package com.hackhome.loans.common.utils;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.Constants;
import com.hackhome.loans.common.tinker.TinkerLoanApplication;

/**
 * desc:
 * author: aragon
 * date: 2018/1/19 0019.
 */
public class CacheUtils {

    /**
     * 缓存HomeFragment数据
     * @param dataBean
     */
    public static void cacheHomeData(DataBean dataBean) {
        new Thread(() -> ACache.get(TinkerLoanApplication.getLoanApplication(), Constants.CacheInfo.CACHE_DIR_NAME)
                .put(Constants.CacheInfo.HOME_CACHE, dataBean)).start();
    }

    /**
     * 获取HomeFragment缓存数据
     * @return DataBean
     */
    public static DataBean getHomeDataCache() {
        return (DataBean) ACache.get(TinkerLoanApplication.getLoanApplication(),
                Constants.CacheInfo.CACHE_DIR_NAME)
                .getAsObject(Constants.CacheInfo.HOME_CACHE);
    }

    /**
     * 缓存LoanFragment数据
     * @param dataBean
     */
    public static void cacheLoanData(DataBean dataBean) {
        new Thread(() -> ACache.get(TinkerLoanApplication.getLoanApplication(), Constants.CacheInfo.CACHE_DIR_NAME)
                .put(Constants.CacheInfo.LOAN_CACHE, dataBean)).start();
    }

    /**
     * 获取缓存LoanFragment数据
     * @return  dataBean
     */
    public static DataBean getLoanDataCache() {
        return (DataBean) ACache.get(TinkerLoanApplication.getLoanApplication(),
                Constants.CacheInfo.CACHE_DIR_NAME)
                .getAsObject(Constants.CacheInfo.LOAN_CACHE);
    }


}
