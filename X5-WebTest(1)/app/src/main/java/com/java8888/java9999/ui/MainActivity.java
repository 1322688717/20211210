//package com.java8888.java9999.ui;
//
//import android.Manifest;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.view.animation.AlphaAnimation;
//import android.webkit.ConsoleMessage;
//import android.webkit.DownloadListener;
//import android.webkit.SslErrorHandler;
//import android.webkit.ValueCallback;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//
//import com.alibaba.fastjson.JSONObject;
//import com.java8888.java9999.view.NTSkipView;
//import com.just.agentweb.AgentWeb;
//import com.java8888.java9999.R;
//import com.java8888.java9999.encrypt.AssetsUtils;
//import com.java8888.java9999.encrypt.UrlBean;
//import com.java8888.java9999.utils.CacheUtil;
//import com.java8888.java9999.utils.SharePerfenceUtils;
//import com.java8888.java9999.view.DragFloatActionButton;
//import com.java8888.java9999.view.PrivacyProtocolDialog;
//import com.tencent.mmkv.MMKV;
//
//import java.io.IOException;
//
//import me.jingbin.progress.WebProgress;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//
///**
// * WebView
// *
// * @author AlexTam
// * created at 2016/10/14 9:58
// */
//public class MainActivity extends Activity implements PrivacyProtocolDialog.ResponseCallBack {
//
//    private long mLastClickBackTime;//????????????back????????????
//    private DragFloatActionButton floatingButton;
//    private RelativeLayout mLayoutMain;
//    private FrameLayout mLayoutPrivacy;
//    private AgentWeb mAgentWeb;
//    private RelativeLayout mLayoutError;
//    private Button mBtnReload;
//    private String mIp;
//    private String urlSuffix;
//    private NTSkipView mTvSkip;
//    private View customView;
//    private FrameLayout fullVideo;
//    private android.webkit.WebView mWebView;
//    private ValueCallback<Uri[]> uploadFiles;
//
//    private ValueCallback<Uri> uploadFile;
//
//    private WebProgress mProgress;
//
//    //??????????????????????????????
//    public static final int REQUEST_EXTERNAL_STORAGE = 1;
//    private String[] PERMISSON = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INTERNET
//    };
//    private boolean bValue = true;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        //  ?????????????????????
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.white));
//            View decorView = window.getDecorView();
//            if (decorView != null) {
//                int vis = decorView.getSystemUiVisibility();
//                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//                decorView.setSystemUiVisibility(vis);
//            }
//        }
//        //  ?????????view
//        initViews();
//        init();
//        initProgess();
//        //requestPermission();
//        //  ?????????
//        CacheUtil.clearAllCache(this);
////        if (null != mAgentWeb) {
////            mAgentWeb.getWebCreator().getWebView().clearHistory();
////        }
//        //  ???????????????????????????
//        boolean isFirst = SharePerfenceUtils.getInstance(this).getFirst();
//        Boolean switchHasBackground = Boolean.valueOf(getResources().getString(R.string.switchHasBackground));
//        if (switchHasBackground) {
//            mLayoutMain.setBackgroundResource(R.mipmap.screen);
//            mLayoutPrivacy.setVisibility(View.GONE);
//            mWebView.setVisibility(View.GONE);
//            mProgress.setVisibility(View.GONE);
//        } else {
//            mLayoutMain.setBackgroundResource(R.color.white);
//            mLayoutPrivacy.setVisibility(View.VISIBLE);
//            mWebView.setVisibility(View.GONE);
//            mProgress.setVisibility(View.GONE);
//        }
//        Boolean showPrivacy = Boolean.valueOf(getResources().getString(R.string.showPrivacy));
//        if (isFirst && showPrivacy) {
//            new PrivacyProtocolDialog(this, R.style.protocolDialogStyle, this).show();
//        } else {
//            showSkip();
//        }
//    }
//
//    private void initProgess() {
//        mProgress.show(); // ??????
//        mProgress.setWebProgress(50);              // ????????????
//        mProgress.setColor("#D81B60");             // ????????????
//        mProgress.setColor("#00D81B60","#D81B60"); // ???????????????
//        mProgress.hide(); // ??????
//    }
//
//    private void requestPermission() {
//        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//
//        if (permission != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, PERMISSON, REQUEST_EXTERNAL_STORAGE);
//        }
//
//    }
//
//    private void showSkip(){
//        mTvSkip.setVisibility(View.VISIBLE);
//        initWebView();
//        CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                mTvSkip.setText(String.format("?????? %d", Math.round(millisUntilFinished / 1000f)));
//
//            }
//
//            @Override
//            public void onFinish() {
//                mTvSkip.setVisibility(View.GONE);
//                mLayoutMain.setBackgroundResource(R.color.default_bg);
//                mLayoutPrivacy.setVisibility(View.GONE);
//                mWebView.setAnimation(new AlphaAnimation(0, 100));
//                mWebView.setVisibility(View.VISIBLE);
//                initFloating();
//            }
//        };
//        countDownTimer.start();
//        mTvSkip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (countDownTimer != null) {
//                    countDownTimer.cancel();
//                }
//                mTvSkip.setVisibility(View.GONE);
//                mLayoutMain.setBackgroundResource(R.color.default_bg);
//                mLayoutPrivacy.setVisibility(View.GONE);
//                mWebView.setAnimation(new AlphaAnimation(0, 100));
//                mWebView.setVisibility(View.VISIBLE);
//                initFloating();
//            }
//        });
//    }
//
//    /**
//     * ?????????View
//     */
//    private void initViews() {
//        mProgress = findViewById(R.id.progressbar_view);
//        mLayoutMain = findViewById(R.id.layout_main);
//        mLayoutPrivacy = findViewById(R.id.layout_privacy);
//        floatingButton = findViewById(R.id.floatingButton);
//        mLayoutError = findViewById(R.id.layout_error);
//        mBtnReload = findViewById(R.id.btn_reload);
//        mTvSkip = findViewById(R.id.tv_skip);
//        fullVideo = findViewById(R.id.full_video);
//        mWebView = findViewById(R.id.web_view);
//    }
//
//    /**
//     * ?????????????????????
//     */
//    private void initFloating() {
//        final MMKV kv = MMKV.defaultMMKV();
//        Boolean showFloatButton = Boolean.valueOf(getResources().getString(R.string.showFloatButton));
//        if (showFloatButton) {
//            floatingButton.setVisibility(View.VISIBLE);
//            floatingButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    UrlBean urlBean = AssetsUtils.getUrlBeanFromAssets(MainActivity.this);
//                    if (urlBean != null) {
//                        if (!TextUtils.isEmpty(urlBean.getFloatUrl())) {
//                           // skipLocalBrowser(urlBean.getFloatUrl());
//
//                            if (bValue){
//                                kv.encode("bool", false);
//                                bValue = kv.decodeBool("bool");
//                                //skipLocalBrowser(mFloatUrl);
//                                initWeb(urlBean.getFloatUrl());
//                                //mWebView.loadUrl(urlBean.getFloatUrl());
//                                floatingButton.setBackgroundResource(R.mipmap.back);
//                            }else {
//                                kv.encode("bool", true);
//                                bValue = kv.decodeBool("bool");
//                                initWebView();
//                                floatingButton.setBackgroundResource(R.mipmap.icon_float);
//                            }
//
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//
//    /**
//     * ?????????webview
//     */
//    private void initWebView() {
//        Boolean getIpConfig = Boolean.valueOf(getResources().getString(R.string.getIpConfig));
//        if (getIpConfig) {
//            UrlBean urlBean = AssetsUtils.getUrlBeanFromAssets(MainActivity.this);
//            if (null != urlBean && null != urlBean.getIpUrl() && !TextUtils.isEmpty(urlBean.getIpUrl())) {
//                urlSuffix = urlBean.getUrlSuffix();
//                final OkHttpClient okHttpClient = new OkHttpClient();
//                Request.Builder builder = new Request.Builder();
//                final Request request = builder.url(urlBean.getIpUrl())
//                        .get()
//                        .build();
//
//                final Call call = okHttpClient.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                        skipError();
//                    }
//
//                    @Override
//                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        try {
//                            String json = response.body().string();
//                            JSONObject jsonObject = JSONObject.parseObject(json);
//                            mIp = jsonObject.getString("query");
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (mIp.contains("http")) {
//                                        initWeb(mIp + urlSuffix);
//                                    } else {
//                                        initWeb("http://" + mIp + urlSuffix);
//                                    }
//                                }
//                            });
//                        } catch (Exception e) {
//                            skipError();
//                        }
//                    }
//                });
//            }
//        } else {
//            initWeb("");
//        }
//
//    }
//
//    /**
//     * ??????????????????
//     */
//    public void skipLocalBrowser(String url) {
//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.VIEW");
//        Uri content_url = Uri.parse(url);
//        intent.setData(content_url);
//        MainActivity.this.startActivity(intent);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (null != mWebView && mWebView.canGoBack()) {
//                mWebView.goBack();
////                final MMKV kv = MMKV.defaultMMKV();
////                if (bValue){
////                    kv.encode("bool", false);
////                    bValue = kv.decodeBool("bool");
////                    floatingButton.setBackgroundResource(R.mipmap.back);
////                }else {
////                    kv.encode("bool", true);
////                    bValue = kv.decodeBool("bool");
////                    floatingButton.setBackgroundResource(R.mipmap.icon_float);
////                }
//                return false;
//            } else {
//                long curTime = System.currentTimeMillis();
//                if (curTime - mLastClickBackTime > 2000) {
//                    mLastClickBackTime = curTime;
//                    Toast.makeText(this, "??????????????????", Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//                finish();
//                super.onBackPressed();
//            }
//            return true;
//        } else {
//            return super.onKeyDown(keyCode, event);
//        }
//    }
//
//    @Override
//    public void agree() {
//        //  ??????????????????,??????????????????
//        SharePerfenceUtils.getInstance(this).setFirst(false);
//        showSkip();
//    }
//
//    @Override
//    public void disAgree() {
//        //  ????????????????????????,??????????????????
//        finish();
//    }
//
//    /**
//     * ?????????webView
//     *
//     * @param ip
//     */
//    public void initWeb(String ip) {
//        try {
//            UrlBean urlBean = AssetsUtils.getUrlBeanFromAssets(MainActivity.this);
//            String webUrl = "";
//            if (urlBean != null) {
//                if (!TextUtils.isEmpty(ip)) {
//                    webUrl = ip;
//                } else {
//                    webUrl = urlBean.getWebViewUrl();
//                }
//                mProgress.show();
//                mWebView.loadUrl(webUrl);
//
//
//            } else {
//                Toast.makeText(this, "????????????????????????", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void init() {
//        try {
//            android.webkit.WebSettings settings = mWebView.getSettings();
//            settings.setAllowFileAccess(true);
//            settings.setLoadWithOverviewMode(true);
//            // ??????WebView??????????????????JavaScript???????????????false???????????????
//            settings.setJavaScriptEnabled(true);
//            // ???????????????????????????????????????????????????false???????????????
//            settings.setJavaScriptCanOpenWindowsAutomatically(true);
//            // ??????WebView??????????????????????????????????????????????????????????????????????????????????????????false?????????????????????????????????
//            settings.setAllowContentAccess(true);
//            // ?????????WebView??????????????????????????????????????????????????????
//            settings.setAllowFileAccess(true);
//            // ??????WebView?????????????????????????????????????????????????????????????????????true
//            settings.setAllowUniversalAccessFromFileURLs(true);
//            // ??????WebView???????????????????????????????????????????????????????????????????????????????????????true
//            settings.setAllowFileAccessFromFileURLs(true);
//            // ??????Application??????API?????????????????????false????????????????????????????????????setAppCachePath(String path)??????
//            settings.setAppCacheEnabled(false);
//            // ?????????????????????????????????API???????????????false???????????????????????????setDatabasePath(String path)
//            settings.setDatabaseEnabled(true);
//            // ??????????????????DOM??????API???????????????false????????????????????????true???WebView????????????DOM
//            settings.setDomStorageEnabled(true);
//            // ??????WebView????????????viewport???????????????????????????false???????????????????????????????????????WebView???????????????
//            // ???????????????true?????????????????????viewport????????????????????????????????????????????????????????????????????????viewport???????????????????????????????????????????????????????????????????????????
//            settings.setUseWideViewPort(true);
//            // ??????WebView?????????????????????????????????WebChromeClient#onCreateWindow?????????false???????????????
//            settings.setSupportMultipleWindows(false);
//            // ??????WebView???????????????????????????????????????????????????????????????true??????????????????
//            settings.setSupportZoom(false);
//            // ??????WebView??????????????????????????????????????????????????????????????????????????????????????????false?????????????????????????????????
//            settings.setBuiltInZoomControls(true);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//            }
//            // ??????WebView?????????????????????????????????????????????UTF-8??????
//            settings.setDefaultTextEncodingName("UTF-8");
//            mWebView.setWebViewClient(new MyWebViewClient());
//            mWebView.setWebChromeClient(new MyWebChromeClient());
//            mWebView.setDownloadListener(new DownloadListener() {
//                @Override
//                public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
//                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public class MyWebViewClient extends WebViewClient {
//        String currentUrl;
//
//        @Override
//        public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) {
//            this.currentUrl = str;
//            if (str.startsWith("http") || str.startsWith("https")) {
//                return false;
//            }
//            try {
//                Uri.parse(str);
//                Intent parseUri = Intent.parseUri(str, Intent.URI_INTENT_SCHEME);
//                parseUri.addCategory("android.intent.category.BROWSABLE");
//                parseUri.setComponent(null);
//                startActivity(parseUri);
//            } catch (Exception unused) {
//            }
//            return true;
//        }
//
//        @Override
//        public void onReceivedSslError(android.webkit.WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//            sslErrorHandler.proceed();
//        }
//
//        @Override
//        public void onPageStarted(android.webkit.WebView webView, String str, Bitmap bitmap) {
//            super.onPageStarted(webView, str, bitmap);
//        }
//
//        @Override
//        public void onPageFinished(final android.webkit.WebView webView, String str) {
//            mProgress.hide();
//            super.onPageFinished(webView, str);
//        }
//
//        @Override
//        public void onReceivedError(android.webkit.WebView webView, int i, String str, String str2) {
//            AlertDialog create = new AlertDialog.Builder(MainActivity.this).create();
//            create.setTitle("????????????");
//            create.setMessage("??????????????????????????????????????????, ????????????, ??????????????????????????????!");
//            create.show();
//        }
//    }
//
//    class MyWebChromeClient extends android.webkit.WebChromeClient {
//
//        @Override
//        public void onHideCustomView() {
//            //????????????
//            if (customView == null){
//                return;
//            }
//            //???????????????????????????
//            fullVideo.removeView(customView);
//            fullVideo.setVisibility(View.GONE);
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//????????????
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//????????????
//
//        }
//
//        @Override
//        public void onShowCustomView(View view, CustomViewCallback callback) {
//            //????????????
//            customView = view;
//            fullVideo.setVisibility(View.VISIBLE);
//            fullVideo.addView(customView);
//            fullVideo.bringToFront();
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//????????????
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//????????????
//
//        }
//
//        @Override
//        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
//            mProgress.setWebProgress(newProgress);
//        }
//
//        @Override
//        public void onReceivedTitle(android.webkit.WebView webView, String str) {
//            super.onReceivedTitle(webView, str);
//        }
//
//        @Override
//        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//            return super.onConsoleMessage(consoleMessage);
//        }
//
//        @Override
//        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//            uploadFiles = valueCallback;
//            openFileChooseProcess();
//            return true;
//        }
//    }
//    public void openFileChooseProcess() {
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.addCategory("android.intent.category.OPENABLE");
//        intent.setType("*/*");
//        startActivityForResult(Intent.createChooser(intent, "????????????"), 0);
//    }
//
//
//    /**
//     * ???????????????
//     */
//    public void skipError() {
//        if (null == mLayoutError || null == mBtnReload) {
//            return;
//        }
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                mLayoutError.setVisibility(View.VISIBLE);
//                mBtnReload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //  ?????????
//                        CacheUtil.clearAllCache(MainActivity.this);
//                        startActivity(new Intent(MainActivity.this, MainActivity.class));
//                        finish();
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int i, int i2, Intent intent) {
//        super.onActivityResult(i, i2, intent);
//        if (i2 == -1) {
//            if (i == 0) {
//                if (uploadFile != null) {
//                    uploadFile.onReceiveValue((intent == null || i2 != -1) ? null : intent.getData());
//                    uploadFile = null;
//                }
//                if (uploadFiles != null) {
//                    uploadFiles.onReceiveValue(new Uri[]{(intent == null || i2 != -1) ? null : intent.getData()});
//                    uploadFiles = null;
//                }
//            }
//        } else if (i2 == 0) {
//            ValueCallback<Uri> valueCallback = this.uploadFile;
//            if (valueCallback != null) {
//                valueCallback.onReceiveValue(null);
//                uploadFile = null;
//            }
//            ValueCallback<Uri[]> valueCallback2 = this.uploadFiles;
//            if (valueCallback2 != null) {
//                valueCallback2.onReceiveValue(null);
//                uploadFiles = null;
//            }
//        }
//    }
//}
