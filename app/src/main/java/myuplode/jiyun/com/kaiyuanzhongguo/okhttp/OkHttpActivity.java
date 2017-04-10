package myuplode.jiyun.com.kaiyuanzhongguo.okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import myuplode.jiyun.com.kaiyuanzhongguo.R;
import myuplode.jiyun.com.kaiyuanzhongguo.okhttp.OkHttpUtils;
import okhttp3.OkHttpClient;

import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by INS7566 on 2017/4/7.
 */

public class OkHttpActivity extends Activity {
    private TextView mtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        mtext = (TextView) findViewById(R.id.Okhttp_TextView);
        App.activity = this;
        String url = "http://shopapi.yasite.net/index.php/goodController/getGoodList";
        OkHttpUtils.getInstance().post("", null, new MyCallBack() {
            @Override
            public void onsuccess(String jsonData) {
                //数据回来了并在主线程中
            }

            @Override
            public void onError(String message) {

            }
        });
    }

}
