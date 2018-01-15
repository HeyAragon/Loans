package com.hackhome.loans.widget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.hackhome.loans.R;

/**
 * desc:
 * author: aragon
 * date: 2017/12/25 0025.
 */
public class MyLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
