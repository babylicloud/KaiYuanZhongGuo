package myuplode.jiyun.com.kaiyuanzhongguo.news;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.jiyun.com.day07_greendao.DaoMaster;
import com.jiyun.com.day07_greendao.DaoSession;

import retrofit2.Retrofit;
public class App extends Application {
    private static DaoSession daoSession;
    public static Activity activity;
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://www.oschina.net/")
            .build();

    @Override
    public void onCreate() {
        super.onCreate();
        getupDatabase();
    }
    public static Retrofit getMyRetrofit(){

        return retrofit;
    }
    private void getupDatabase(){
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"kaiyuanzhongguo.db",null);
        //创建数据库
        SQLiteDatabase database = helper.getWritableDatabase();

        //创建数据库对象
        DaoMaster daoMaster = new DaoMaster(database);

        //获取Dao对象管理者
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoInstant (){
        return daoSession;
    }

}
