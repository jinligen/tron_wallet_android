package prochain.com.tronbox.utils;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;



import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import prochain.com.tronbox.R;
import prochain.com.tronbox.main.fancyBaseActivity;
import prochain.com.tronbox.utils.MessageBus.EventType;
import prochain.com.tronbox.utils.MessageBus.MessageEvent;

public class fancyWebView extends fancyBaseActivity {

    private WebView webview;

    private String webURL="";



    private final Set<String> offlineResources = new HashSet<>();

    //有限加载本地资源
    private void fetchOfflineResources() {
        AssetManager am = getAssets();
        try {
            String[] res = am.list("offline_res");
            if(res != null) {
                Collections.addAll(offlineResources, res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_fancy_web_view);


        EventBus.getDefault().register(this);


        fetchOfflineResources();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String strContentString = "";
        if (bundle!=null) {
            strContentString = bundle.getString("title");
            if (strContentString == null) {
                strContentString = "";
            }
            webURL = bundle.getString("url");
            if (webURL==null){
                webURL = "";
            }

        }
        initView();

        setTitleCenter(strContentString);




        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (webview.canGoBack()) {
                    webview.goBack();//返回上一页面
                }
                else
                {
                    finish();
                }
            }
        });

        webview = (WebView)findViewById(R.id.webview);



        Map<String, String > map = new HashMap<String, String>() ;




        webview.loadUrl(webURL,map);


        Log.d("alex_huang", "the web url is " + webURL);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);

        webview.setWebViewClient(new WebViewClient(){

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("WebViewActivity", "shouldInterceptRequest thread id: " + Thread.currentThread().getId());
                int lastSlash = url.lastIndexOf("/");
                if(lastSlash != -1) {
                    String suffix = url.substring(lastSlash + 1);
                    if(offlineResources.contains(suffix)) {
                        String mimeType;
                        if(suffix.endsWith(".js")) {
                            mimeType = "application/x-javascript";
                        } else if(suffix.endsWith(".css")) {
                            mimeType = "text/css";
                        } else {
                            mimeType = "text/html";
                        }
                        try {
                            InputStream is = getAssets().open("offline_res/" + suffix);
                            Log.i("shouldInterceptRequest", "use offline resource for: " + url);
                            return new WebResourceResponse(mimeType, "UTF-8", is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("shouldInterceptRequest", "load resource from internet, url: " + url);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {

                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("changeTitle")) {

                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        // 可以在协议上带有参数并传递到Android上
                        HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();
                        String title = uri.getQueryParameter("title");
                        if(title!=null) {
                            setTitleCenter(title);
                        }

                    }

                    return true;

                }
                else {

                    view.loadUrl(url);
                    return true;
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                //loading.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //loading.dismiss();

            }
        });



        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String s1, String s2, String s3, long l) {
                startDownLoad(url);
            }
        });



        //add javascript call native method list
        //webview.addJavascriptInterface(new WebAppInterface(),"Prochain");

    }

    void startDownLoad(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview.canGoBack()) {
                webview.goBack();//返回上一页面
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void initView()
    {
        super.initView();
    }



    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

    }






    // This method will be called when a MessageEvent is posted
    @Subscribe
    public void onMessageEvent(MessageEvent event) {




    }






}

