package myuplode.jiyun.com.kaiyuanzhongguo.okhttp;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by INS7566 on 2017/4/7.
 */

public class OkHttpUtils {
    private OkHttpClient okHttpClient;//=new OkHttpClient.Builder().build();
    private static OkHttpUtils okHttpUtils=null;
        private OkHttpUtils(){
             okHttpClient=new OkHttpClient.Builder().build();
    }
    public synchronized static OkHttpUtils getInstance(){
        if(okHttpUtils==null)
            okHttpUtils = new OkHttpUtils();
            return okHttpUtils;

    }
    public void get(){

    }
    public void post(String url, Map<String ,String> params, final MyCallBack callback){
//    OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        FormBody.Builder builder=new FormBody.Builder();
        if (params!=null&& params.size()>0){
            Set<String> keys=params.keySet();
            for (String key : keys) {
                String value= params.get(key);
                builder.add(key,value);
            }
        }
        ///  FormBody formBody=new FormBody.Builder().add("key","value").build();
        Request request=new Request.Builder()
                .url("http://shopapi.yasite.net/index.php/goodController/getGoodList")
                .post(builder.build())
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }

            @Override
            public void onResponse(Call call,final Response response)  {

                //
                App.activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callback.onsuccess(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
    private  void test(String url, Map<String ,String> params){

    }

}
