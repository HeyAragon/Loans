package com.hackhome.loans.presenter.contract;

import com.hackhome.loans.bean.PatchBean;
import com.hackhome.loans.bean.ResponseBean;
import com.hackhome.loans.bean.UpdateBean;
import com.hackhome.loans.bean.UserInfo;
import com.hackhome.loans.ui.base.IBase;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * desc:
 * author: aragon
 * date: 2018/1/3 0003.
 */
public interface IMainContract {

    interface IMainModel{

        Observable<UpdateBean> checkNewVersion(String type);

        Observable<PatchBean> checkPatch(String type);
    }

    interface IMainView extends IBase{

    }


}
