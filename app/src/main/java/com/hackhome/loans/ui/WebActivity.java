package com.hackhome.loans.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hackhome.loans.R;
import com.hackhome.loans.common.eventbus.EventItem;
import com.hackhome.loans.common.utils.StatusBarUtil;
import com.hackhome.loans.dagger.component.ApplicationComponent;
import com.hackhome.loans.ui.base.BaseActivity;
import com.hackhome.loans.ui.mine.DownloadActivity;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * desc:
 * author: aragon
 * date: 2017/12/26 0026.
 */
public class WebActivity extends BaseActivity {

    private static final String TAG = "aragon";
    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.web_view)
    WebView mWebView;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.tool_bar_back_img)
    ImageView mBack;
    @BindView(R.id.tool_bar_title_txt)
    TextView mTitle;
    private int count;


    @Override
    public int getLayoutContentRes() {
        return R.layout.activity_web;
    }

    @Override
    public void loadData() {
        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");
        mWebView.loadUrl(url);
        mTitle.setText(name);
    }

    @Override
    public void initView() {

        mToolbar.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.VISIBLE);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.base_back),0);
        getSetting(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar == null) {
                    return;
                }
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }

    @OnClick(R.id.tool_bar_back_img)
    public void bindClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tool_bar_back_img:
                finish();
                break;
            default:
                break;
        }
    }

    private void getSetting(WebView webview) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setDisplayZoomControls(false);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//LOAD_CACHE_ELSE_NETWORK模式下，无论是否有网络，只要本地有缓存，都使用缓存。本地没有缓存时才从网络上获取
        webview.getSettings().setDomStorageEnabled(true);

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void initInjector(ApplicationComponent applicationComponent) {

    }

    @Override
    public <K>void showResponse(K t, int responseType) {

    }

//    @Subscribe
//    public void handleEvent(EventItem eventItem) {
//        count++;
//        KLog.i("aragon",DownloadActivity.class.getSimpleName()+count);
//    }
}
