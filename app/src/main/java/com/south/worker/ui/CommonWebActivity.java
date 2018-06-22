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
import com.south.worker.data.NewsReposity;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.network.LoadingSubscriber;

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
    public static final String Content = "content";
    public static final String NEWSID = "news_id";
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
    private String content;
    private int newsId = -1;


    public static void startUrlWebActivity(Context context, String title, String url) {
        Intent intent = new Intent();
        intent.putExtra(CommonWebActivity.URL, url);
        intent.putExtra(CommonWebActivity.TITLE, title);
        intent.setClass(context, CommonWebActivity.class);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, int newsId) {
        Intent intent = new Intent();
        intent.putExtra(CommonWebActivity.NEWSID, newsId);
        intent.setClass(context, CommonWebActivity.class);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, String title, String Content) {
        Intent intent = new Intent();
        intent.putExtra(CommonWebActivity.Content, Content);
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
        content = getIntent().getStringExtra(Content);
        newsId = getIntent().getIntExtra(NEWSID,-1);

        //初始化View
        initView();

        if(newsId >-1){
            getNewsUrl(newsId);
        }else{
            //进度条初始化
            initProgressBar();
            //webview初始化
            initWebView();
        }



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
        tvTitle.setText(TextUtils.isEmpty(title)?"标题正在加载":title);

        webview.setVisibility(View.VISIBLE);
        llRetry.setVisibility(View.GONE);

    }


    private void initProgressBar() {
        progressBar.setMax(100);
    }

    private void initWebView() {
        if ( TextUtils.isEmpty(url) && TextUtils.isEmpty(content) ) {
            Toast.makeText(mContext, "数据异常，请稍后再试", Toast.LENGTH_SHORT).show();
            finish();
        }

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setDefaultTextEncodingName("UTF-8") ;
        //控制webView不要出现横向滚动条
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setBuiltInZoomControls(false); // 设置显示缩放按钮
        webview.getSettings().setSupportZoom(false); // 支持缩放
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);

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

        if(!TextUtils.isEmpty(url)){
            webview.loadUrl(url);
        }


        if(!TextUtils.isEmpty(content)){




            String css = "<style type=\"text/css\"> img {" +
                    "width:100%;" +//限定图片宽度填充屏幕
                    "height:auto;" +//限定图片高度自动
                    "}" +
                    "body {" +
                    "word-wrap:break-word;"+//允许自动换行(汉字网页应该不需要这一属性,这个用来强制英文单词换行,类似于word/wps中的西文换行)
                    "}" +
                    "</style>";



            String str1 = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<meta charset=\"utf-8\">\n" + "<style>img{max-width:360px !important;}</style>" +
                    "\t\t<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1, user-scalable=no\"></head><body>\n";
            String str2 = "</body><html>";


            String html = "<html><header>" + css + "</header>" + content + "</html>";

            StringBuffer str = new StringBuffer();
            str.append(str1)
                    .append(content)
                    .append(str2);
            webview.loadDataWithBaseURL(null,html, "text/html",  "utf-8", null);
//            webview.loadData(str.toString(), "text/html; charset=UTF-8", null);

        }


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


    /**
     * 通过新闻ID获取新闻内容
     * @param newsId
     */
    public void getNewsUrl(final int newsId) {
        NewsReposity.getInstance()
                .getNewsUrl(newsId)
                .subscribe(new LoadingSubscriber<NewUrlBean>(mContext,mContext.getString(R.string.msg_loading),false) {

                    @Override
                    public void onNext(NewUrlBean bean) {

                        content = bean.Content;
                        title = bean.Title;
                        tvTitle.setText(title);
                        //进度条初始化
                        initProgressBar();
                        //webview初始化
                        initWebView();

                    }
                    @Override
                    public void onSubscriberError(String errorMsg)
                    {
                        Toast.makeText(mContext, "errorMsg", Toast.LENGTH_SHORT).show();
                    }


                });
    }
}
