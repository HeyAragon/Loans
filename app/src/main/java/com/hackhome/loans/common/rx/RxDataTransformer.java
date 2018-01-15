package com.hackhome.loans.common.rx;

import com.hackhome.loans.common.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * desc:
 * author: aragon
 * date: 2017/12/19 0019.
 */
public class RxDataTransformer {

//    public static <T> ObservableTransformer<BaseDataBean<T>, T> transformerData() {
//        return new ObservableTransformer<BaseDataBean<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(@NonNull Observable<BaseDataBean<T>> upstream) {
//                return upstream.flatMap(new Function<BaseDataBean<T>, ObservableSource<T>>() {
//                    @Override
//                    public ObservableSource<T> apply(@NonNull final BaseDataBean<T> tBaseDataBean) throws Exception {
//                        if (tBaseDataBean.isSuccess()) {
//                            return Observable.create(new ObservableOnSubscribe<T>() {
//                                @Override
//                                public void subscribe(@NonNull ObservableEmitter<T> emitter) throws Exception {
//                                    try {
//                                        emitter.onNext(tBaseDataBean.getData());
//                                        emitter.onComplete();
//                                    } catch (Exception e) {
//                                        emitter.onError(e);
//                                    }
//                                }
//                            });
//                        } else {
//                            return Observable.error(new ApiException(tBaseDataBean.getCode(),tBaseDataBean.getMsg()));
//                        }
//
//                    }
//                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

}
