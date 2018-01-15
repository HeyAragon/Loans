package com.hackhome.loans.widget;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * desc:
 * author: aragon
 * date: 2017/12/25 0025.
 */
public class CardViewItemDecoration extends RecyclerView.ItemDecoration {

    private boolean mSkipPos0;

    public CardViewItemDecoration() {
    }

    public CardViewItemDecoration(boolean skipPos0) {
        mSkipPos0 = skipPos0;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int layoutOrientation = getOrientation(parent);

        if (mSkipPos0) {
            if (parent.getLayoutManager().getPosition(view) != 0) {
                if (layoutOrientation == LinearLayoutManager.VERTICAL) {
                    outRect.top = 10;
                    outRect.left = 15;
                    outRect.right = 15;
                    outRect.bottom = 10;
                } else if (layoutOrientation == LinearLayoutManager.HORIZONTAL) {
                    outRect.left = 5;
                }
            }
        } else {
            if (layoutOrientation == LinearLayoutManager.VERTICAL) {
                outRect.top = 20;
                outRect.left = 15;
                outRect.right = 15;
            } else if (layoutOrientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = 5;
            }
        }

    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else throw new IllegalStateException("DividerItemDecoration can only be used with a LinearLayoutManager.");
    }
}
