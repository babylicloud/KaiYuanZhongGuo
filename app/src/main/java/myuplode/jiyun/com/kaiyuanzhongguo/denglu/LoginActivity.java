package myuplode.jiyun.com.kaiyuanzhongguo.denglu;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.xstream.XStream;

import java.io.InputStream;

import myuplode.jiyun.com.kaiyuanzhongguo.R;
import myuplode.jiyun.com.kaiyuanzhongguo.bean.LoginBean;
import myuplode.jiyun.com.kaiyuanzhongguo.greendao.GreenDaoWork;

import myuplode.jiyun.com.kaiyuanzhongguo.greendao.LoginDao;
import myuplode.jiyun.com.kaiyuanzhongguo.news.App;
import myuplode.jiyun.com.kaiyuanzhongguo.news.RetrofitInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends Activity implements View.OnClickListener{
private EditText mzhanghao,mmima;
    private Button mdenglu,mzhuce;
    private TextView wangjimima;
    private String Name, Pwd;
    private GreenDaoWork dao = new GreenDaoWork();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initviews();
    }

    private void initviews() {
        mzhanghao= (EditText) findViewById(R.id.login_zhanghao);
        mmima= (EditText) findViewById(R.id.login_mima);
        mdenglu= (Button) findViewById(R.id.login_denglu);
        mzhuce= (Button) findViewById(R.id.login_zhuce);
        mdenglu.setOnClickListener(this);
        mzhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_denglu:

//             if (TextUtils.isEmpty(mzhanghao.getText().toString()) || TextUtils.isEmpty(mmima.getText().toString())) {
//                   Toast.makeText(this, "账号密码为空！", Toast.LENGTH_SHORT).show();
//                } else {
                    Login();
//               }

                break;
        }
    }

    private void Login() {
        String name = mzhanghao.getText().toString();
        String psd = mmima.getText().toString();
        this.Name=name;
        this.Pwd=psd;
        if (name.isEmpty()&&psd.isEmpty()){
            Toast.makeText(this, "请输入账号或密码 ", Toast.LENGTH_SHORT).show();

        }else {
            getRetrofit(name, psd, "1");

        }
    }

    private void getRetrofit(String name, String psd, String s) {
        Retrofit retrofit = App.getMyRetrofit();
        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> login=retrofitInterface.Login(name,psd,s);
        login.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream inputstream =response.body().byteStream();
                XStream xstream=new XStream();
                xstream.alias("oschina", LoginBean.class);

                    LoginBean login = (LoginBean) xstream.fromXML(inputstream);
              //  String s1 = Log.i("数据是", login.getResult().getErrorCode()) + login.getResult().getErrorMessage();

                LoginDao logindao = new LoginDao(null, Name, Pwd, null);

                long insert = dao.insert(logindao);
                if (insert > 0) {
                    Toast.makeText(LoginActivity.this, "插入成功", Toast.LENGTH_SHORT).show();
                }

                switch (login.getResult().getErrorCode()){
                    case "1":
                        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "0":
                        Toast.makeText(LoginActivity.this, "登录失败,请输入正确的账号，密码", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
