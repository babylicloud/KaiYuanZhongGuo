package myuplode.jiyun.com.kaiyuanzhongguo.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import myuplode.jiyun.com.kaiyuanzhongguo.R;


public class MapViewActivity extends Activity {
    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        mWebview = (WebView) findViewById(R.id.WebView_activity);
        Intent intent = getIntent();
        String str = intent.getStringExtra("id");
        Log.i("网站是", str);
        if (str != null) {
            mWebview.loadUrl(str);
        }
        mWebview.setWebViewClient(new WebViewClient());

    }

}
