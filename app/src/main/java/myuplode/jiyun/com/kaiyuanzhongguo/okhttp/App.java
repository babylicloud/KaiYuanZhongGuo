package myuplode.jiyun.com.kaiyuanzhongguo.okhttp;

import android.app.Activity;
import android.app.Application;

import retrofit2.Retrofit;


public class App extends Application {
    public  static Activity activity;

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.oschina.net/")
            .build();
    @Override
    public void onCreate() {
        super.onCreate();

    }
    public static Retrofit getMyRetrofit(){

        return retrofit;
    }


}
