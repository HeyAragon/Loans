package com.hackhome.loans.ui.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hackhome.loans.R;
import com.hackhome.loans.bean.ReturnValueBean;
import com.hackhome.loans.common.imageloader.GlideApp;
import com.hackhome.loans.common.utils.DensityUtil;
import com.hackhome.loans.common.utils.FormatUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * desc:
 * author: aragon
 * date: 2017/12/21 0021.
 */
public class CommonAdapter extends BaseQuickAdapter<ReturnValueBean,BaseViewHolder> {

    public static final int READ_RECORD = 0;

    private int from = -1;

    public CommonAdapter() {
        super(R.layout.base_item_layout);
    }

    public void setFrom(int from) {
        this.from = from;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReturnValueBean item) {
        ImageView icon = helper.getView(R.id.base_item_icon);

        if (from == READ_RECORD) {
            helper.addOnLongClickListener(R.id.base_item_root);
        }
        GlideApp.with(mContext)
                .load(item.getProductImg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new RoundedCorners(DensityUtil.dip2px(mContext, 8)))
                .into(icon);

        SpannableStringBuilder maxLoanBuilder = new SpannableStringBuilder();

        String maxLoan = FormatUtils.format(FormatUtils.MAX_LIMIT, item.getEndLoanMoney());
        maxLoanBuilder
                .append(maxLoan)
                .setSpan(new ForegroundColorSpan(Color.BLUE),4,maxLoan.length()-1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        helper.setText(R.id.base_item_name, item.getProductName());
        helper.setText(R.id.base_item_loan_limit, maxLoanBuilder);
        helper.setText(R.id.base_item_loan_interest, FormatUtils.format(FormatUtils.DAY_INTEREST,item.getInterestRateDay()));
        helper.setText(R.id.base_item_loan_description, item.getProductCharacteristic());
        helper.setText(R.id.base_item_loan_people_num, FormatUtils.format(FormatUtils.LOAN_PEOPLE_NUM, item.getSecuredLoan()));
        helper.addOnClickListener(R.id.base_item_root);
        helper.setTag(R.id.base_item_root,item);

    }
}
