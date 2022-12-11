//package com.Tpp8888.Tpp9999;
//
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//import androidx.multidex.BuildConfig;
//
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.AutoCompleteTextView;
//import android.widget.RadioButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.Tpp8888.Tpp9999.tencentx5.X5WebViewActivity;
//import com.Tpp8888.Tpp9999.ui.ByWebViewActivity;
//import com.Tpp8888.Tpp9999.ui.CoordinatorWebActivity;
//import com.Tpp8888.Tpp9999.utils.StatusBarUtil;
//
//import me.jingbin.web.ByWebTools;
//
///**
// * Link to: https://github.com/youlookwhat/ByWebView
// * contact me: https://www.jianshu.com/u/e43c6e979831
// */
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//
//    // 是否开启了主页，没有开启则会返回主页
//    public static boolean isLaunch = false;
//    private AutoCompleteTextView etSearch;
//    private RadioButton rbSystem;
//    private int state = 0;
//    private TextView bt_baidu;
//    private TextView bt_movie;
//    private TextView bt_upload_photo;
//    private TextView bt_call;
//    private TextView bt_toolbar;
//    private  TextView bt_deeplink,bt_openUrl;
//    private  TextView bt_java_js;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0);
//        initView();
//        initOnclick();
//        isLaunch = true;
//    }
//
//
//
//    private void initView() {
//        bt_deeplink = findViewById(R.id.bt_deeplink);
//        bt_deeplink.setOnClickListener(this);
//        bt_openUrl = findViewById(R.id.bt_openUrl);
//        bt_openUrl.setOnClickListener(this);
//        bt_baidu = findViewById(R.id.bt_baidu);
//        bt_baidu.setOnClickListener(this);
//        bt_movie= findViewById(R.id.bt_movie);
//        bt_movie.setOnClickListener(this);
//        bt_upload_photo = findViewById(R.id.bt_upload_photo);
//        bt_upload_photo.setOnClickListener(this);
//        bt_call = findViewById(R.id.bt_call);
//        bt_call.setOnClickListener(this);
//        bt_java_js = findViewById(R.id.bt_java_js);
//        bt_java_js.setOnClickListener(this);
//        bt_toolbar = findViewById(R.id.bt_toolbar);
//        bt_toolbar.setOnClickListener(this);
//        rbSystem = findViewById(R.id.rb_system);
//        etSearch = findViewById(R.id.et_search);
//        rbSystem.setChecked(true);
//        TextView tvVersion = findViewById(R.id.tv_version);
//        tvVersion.setText(String.format("❤版本：v%s", BuildConfig.VERSION_NAME));
//        tvVersion.setOnClickListener(this);
//        /** 处理键盘搜索键 */
//        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    openUrl();
//                }
//                return false;
//            }
//        });
//    }
//
//
//    private void initOnclick() {
//        bt_baidu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state = 0;
//                String baiDuUrl = "http://www.baidu.com";
//                loadUrl(baiDuUrl, getString(R.string.text_baidu));
//            }
//        });
//
//        bt_movie.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state = 0;
//                String movieUrl = "https://sv.baidu.com/videoui/page/videoland?context=%7B%22nid%22%3A%22sv_5861863042579737844%22%7D&pd=feedtab_h5";
//                loadUrl(movieUrl, getString(R.string.text_movie));
//            }
//        });
//
//        bt_upload_photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state = 0;
//                String uploadUrl = "file:///android_asset/upload_photo.html";
//                loadUrl(uploadUrl, getString(R.string.text_upload_photo));
//            }
//        });
//
//        bt_call.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state = 1;
//                String callUrl = "file:///android_asset/callsms.html";
//                loadUrl(callUrl, getString(R.string.text_js));
//            }
//        });
//
//        bt_java_js.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                state = 2;
//                String javaJs = "file:///android_asset/java_js.html";
//                loadUrl(javaJs, getString(R.string.js_android));
//            }
//        });
////
////        bt_deeplink.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                state = 0;
////                String deepLinkUrl = "file:///android_asset/deeplink.html";
////                loadUrl(deepLinkUrl, getString(R.string.deeplink));
////            }
////        });
//
//    }
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.bt_openUrl:
//                openUrl();
//                break;
////            case R.id.bt_baidu:// 百度一下
////                state = 0;
////                String baiDuUrl = "http://www.baidu.com";
////                loadUrl(baiDuUrl, getString(R.string.text_baidu));
////                break;
////            case R.id.bt_movie:// 网络视频
////                state = 0;
////                String movieUrl = "https://sv.baidu.com/videoui/page/videoland?context=%7B%22nid%22%3A%22sv_5861863042579737844%22%7D&pd=feedtab_h5";
////                loadUrl(movieUrl, getString(R.string.text_movie));
////                break;
////            case R.id.bt_upload_photo:// 上传图片
////                state = 0;
////                String uploadUrl = "file:///android_asset/upload_photo.html";
////                loadUrl(uploadUrl, getString(R.string.text_upload_photo));
////                break;
////            case R.id.bt_call:// 打电话、发短信、发邮件、JS
////                state = 1;
////                String callUrl = "file:///android_asset/callsms.html";
////                loadUrl(callUrl, getString(R.string.text_js));
////                break;
////            case R.id.bt_java_js://  js与android原生代码互调
////                state = 2;
////                String javaJs = "file:///android_asset/java_js.html";
////                loadUrl(javaJs, getString(R.string.js_android));
////                break;
////            case R.id.bt_deeplink:// DeepLink通过网页跳入App
////                state = 0;
////                String deepLinkUrl = "file:///android_asset/deeplink.html";
////                loadUrl(deepLinkUrl, getString(R.string.deeplink));
////                break;
//            case R.id.bt_toolbar:// 与ToolBar联动，自定义WebView
//                CoordinatorWebActivity.loadUrl(this, "http://www.baidu.com", "百度一下", 0);
//                break;
//            case R.id.tv_version:
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("感谢");
//                builder.setMessage("开源不易，给作者一个star好吗？😊");
//                builder.setNegativeButton("已给", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(MainActivity.this, "感谢老铁~", Toast.LENGTH_LONG).show();
//                    }
//                });
//                builder.setPositiveButton("去star", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        state = 0;
//                        loadUrl("https://github.com/youlookwhat/ByWebView", "ByWebView");
//                    }
//                });
//                builder.show();
//                break;
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 打开网页
//     */
//    private void openUrl() {
//        state = 0;
//        String url = ByWebTools.getUrl(etSearch.getText().toString().trim());
//        loadUrl(!TextUtils.isEmpty(url) ? url : "https://github.com/youlookwhat/ByWebView", "ByWebView");
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.actionbar_update:
//                state = 0;
//                loadUrl("https://github.com/youlookwhat/download/raw/main/ByWebView.apk", "ByWebView.apk");
//                break;
//            case R.id.actionbar_about:
//                state = 0;
//                loadUrl("https://github.com/youlookwhat/ByWebView", "ByWebView");
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void loadUrl(String mUrl, String mTitle) {
//        if (rbSystem.isChecked()) {
////            WebViewActivity.loadUrl(this, mUrl, mTitle);
//            ByWebViewActivity.loadUrl(this, mUrl, mTitle, state);
//        } else {
//            X5WebViewActivity.loadUrl(this, mUrl, mTitle);
//        }
//    }
//
//    public static void start(Context context) {
//        context.startActivity(new Intent(context, MainActivity.class));
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        isLaunch = false;
//    }
//}
