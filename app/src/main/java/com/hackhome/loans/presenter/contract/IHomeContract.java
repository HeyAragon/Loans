package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.ui.base.IBase;
import com.hackhome.loans.ui.base.IBaseView;

import io.reactivex.Observable;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
public interface IHomeContract {

    interface IHomeModel {
        Observable<DataBean> getHomeData(String type,int sort,int page,int ob);
    }

    interface IHomeView extends IBase {
        void showHomeData(DataBean dataBean,boolean isFromRefresh);

    }

}
