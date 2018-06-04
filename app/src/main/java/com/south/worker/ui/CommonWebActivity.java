package com.south.worker.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.utils.LogUtils;
import com.south.worker.R;
import com.south.worker.constant.IntentConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述   ：webview 统一加载类
 * <p>
 * 作者   ：Created by DuanRui on 2017/9/15.
 */

public class CommonWebActivity extends BaseActivity {

    public static final String URL = "url";
    public static final String TITLE = "title";
    Context mContext;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvClose)
    TextView tvClose;
    @BindView(R.id.tvRight)
    TextView tvRight;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webview;
    @BindView(R.id.tv_retry)
    TextView tvRetry;
    @BindView(R.id.ll_retry)
    LinearLayout llRetry;
    @BindView(R.id.tv_loading)
    TextView tvLoading;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private boolean loadSuccess;
    private String url;
    private String title;

    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent();
        intent.putExtra(CommonWebActivity.URL, url);
        intent.putExtra(CommonWebActivity.TITLE, title);
        intent.setClass(context, CommonWebActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);

        ButterKnife.bind(this);
        mContext = this;
        url = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);

        //初始化View
        initView();

        //进度条初始化
        initProgressBar();

        //webview初始化
        initWebView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (webview != null && webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

    private void initView() {
        if ( TextUtils.isEmpty(url)) {
            Toast.makeText(mContext, "数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
            finish();
        }
        tvTitle.setText(title);
        webview.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);

        LogUtils.d("url->" + url);


    }


    private void initProgressBar() {
        progressBar.setMax(100);
    }

    private void initWebView() {


        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String _url) {
                url = _url;
                view.loadUrl(url);
                LogUtils.d("requestUrl->" + url);
                return true;
            }

            @TargetApi(21)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                url = request.getUrl().toString();
                view.loadUrl(url);
                LogUtils.d("requestUrl:" + url);
                LogUtils.d("requestHeader:" + request.getRequestHeaders());

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadSuccess = true;
                completeRefresh();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }


            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                LogUtils.d("--onReceivedError----errorCode:" + errorCode + ", description:" + description);
                loadSuccess = false;
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                loadSuccess = false;
            }


        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

        webview.loadUrl(url);


    }

    private void onLoadSuccess() {
        webview.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);
    }

    private void onError() {
        webview.stopLoading();
        webview.setVisibility(View.INVISIBLE);
        llRetry.setVisibility(View.VISIBLE);
    }

    private void completeRefresh() {
        LogUtils.d("页面加载完成:" + webview);
        if (null == webview) {
            return;
        }
        tvLoading.setVisibility(View.GONE);
        tvClose.setVisibility(webview.canGoBack() ? View.VISIBLE : View.GONE);
        if (loadSuccess) {
            onLoadSuccess();
        } else {
            onError();
        }
    }


    @OnClick({R.id.ivBack, R.id.tvClose, R.id.tvRight, R.id.ll_retry})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.tvClose:
                finish();
            case R.id.tvRight:
                break;
            case R.id.ll_retry:
                loadSuccess = true;
                llRetry.setVisibility(View.GONE);
                webview.reload();
                break;
        }
    }
}
