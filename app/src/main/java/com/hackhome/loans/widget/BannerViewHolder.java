package com.hackhome.loans.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.DataBean;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.imageloader.GlideRequest;
import com.hackhome.loans.common.utils.DensityUtil;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * desc:
 * author: aragon
 * date: 2017/12/22 0022.
 */
public class BannerViewHolder implements MZViewHolder<DataBean.BannersBean> {

    private ImageView mImageView;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        mImageView = (ImageView) view.findViewById(R.id.banner_item_img);
        return view;
    }

    @Override
    public void onBind(Context context, int position, DataBean.BannersBean data) {
        GlideRequest<Drawable> load = GlideApp.with(context)
                .load(data.getImages());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
            layoutParams.width = layoutParams.MATCH_PARENT;
            layoutParams.height = DensityUtil.dip2px(context,224);
            mImageView.setLayoutParams(layoutParams);
            ViewGroup.MarginLayoutParams layoutParams1 = (ViewGroup.MarginLayoutParams) mImageView.getLayoutParams();
            layoutParams1.setMargins(
                    DensityUtil.dip2px(context,4),
                    DensityUtil.dip2px(context,18),
                    DensityUtil.dip2px(context,4),
                    DensityUtil.dip2px(context,18)

                    );
            load.centerCrop().into(mImageView);
        } else {
            load.centerInside().into(mImageView);
        }

    }

}
