package myuplode.jiyun.com.kaiyuanzhongguo.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import myuplode.jiyun.com.kaiyuanzhongguo.R;
import myuplode.jiyun.com.kaiyuanzhongguo.bean.Student;
import myuplode.jiyun.com.kaiyuanzhongguo.adapter.DefaultURL;
import myuplode.jiyun.com.kaiyuanzhongguo.adapter.MyDefault;
import myuplode.jiyun.com.kaiyuanzhongguo.zonghe.SearchActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends Activity {
    private PullToRefreshRecyclerView mRecyclerView;
    private List<Student> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private int index;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.Main_EditText);
        mEditText.setFocusable(false);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });



        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.Main_PullRecycler);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        manager.setOrientation(LinearLayoutManager.VERTICAL);

        mAdapter = new MyAdapter(MainActivity.this,R.layout.recycler_item, mList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        //记录存储当前页面信息
        mShared = getSharedPreferences("data", MODE_PRIVATE);
        mEditor = mShared.edit();
        index = mShared.getInt("index", 1);
        //解析网络数据，每次解析展示出十条信息，
        getRetrofit("1", String.valueOf(index), "10");
        index++;
        mEditor.putInt("index", index);
        mEditor.commit();
//是否开启下拉刷新功能
        mRecyclerView.setPullRefreshEnabled(true);
//是否开启上拉加载功能
        mRecyclerView.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        mRecyclerView.displayLastRefreshTime(true);
        //设置刷新回调
        mRecyclerView.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setRefreshComplete();
                        //模拟没有数据的情况
                        mList.clear();
                        //刷新数据，让每次加载数据时++，加载另一个页面
                        getRetrofit("1", String.valueOf(index), "10");
                        index++;
                        mEditor.putInt("index", index);
                        mEditor.commit();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setLoadMoreComplete();
                        getRetrofit("1", String.valueOf(index), "5");
                        index++;
                        mEditor.putInt("index", index);
                        mEditor.commit();
                    }
                }, 2000);
            }
        });
        //主动触发下拉刷新操作
        //pullToRefreshRV.onRefresh();


    }

    public void getRetrofit(String catalog, String pageIndex, String pageSize) {
        Retrofit retrofit = App.getMyRetrofit();
//          Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.oschina.net/")
//                  .build();
        RetrofitInterface anInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = anInterface.getLogin(catalog, pageIndex, pageSize);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String ss = response.body().string();
                        StringReader reader = new StringReader(ss);
                        InputSource source = new InputSource(reader);
                        SAXParserFactory spf = SAXParserFactory.newInstance();
                        try {
                            SAXParser paeser = spf.newSAXParser();
                            XMLReader xr = paeser.getXMLReader();
                            MyDefault handler = new MyDefault();
                            xr.setContentHandler(handler);
                            xr.parse(source);
                            ArrayList<Student> mListStu = handler.getmList();
                            for (Student student : mListStu) {
                                Log.i("这是", student.toString());
                            }
                            if (mListStu != null) {
                                mList.addAll(mListStu);
                            }

                            if (mAdapter != null) {
                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        } catch (SAXException e) {
                            e.printStackTrace();
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    class MyAdapter extends BaseAdapter<Student> {

        public MyAdapter(Context context,int layoutId, List<Student> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder holder, final Student student) {
            holder.setText(R.id.Recycler_Item_Body, student.getBody());
            holder.setText(R.id.Recycler_Item_Title, student.getTitle());
            holder.setOnclickListener(R.id.Item_LayoutLayout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = student.getId();

                    getMyUrl(id);


                }
            });

        }
    }

    // TODO: 2017/4/6 解析网址
    public void getMyUrl(String id) {
//        Retrofit retrofit = App.getMyRetrofit();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.oschina.net/")
                .build();
        RetrofitInterface anInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = anInterface.getLoginId(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                if (response.isSuccessful()) {
                    String web = null;
                    try {
                        web = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (web != null) {
                        StringReader reader = new StringReader(web);
                        InputSource source = new InputSource(reader);
                        SAXParserFactory spf = SAXParserFactory.newInstance();

                        try {
                            SAXParser sp = spf.newSAXParser();

                            XMLReader sr = sp.getXMLReader();

                            DefaultURL handler = new DefaultURL();
                            sr.setContentHandler(handler);
                            try {
                                sr.parse(source);
                                String strUrl = handler.getmUrl();
                                if (strUrl != null) {
                                    Intent intent = new Intent(MainActivity.this, MapViewActivity.class);
                                    intent.putExtra("id", strUrl);
                                    startActivity(intent);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (ParserConfigurationException e) {


                        } catch (SAXException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


}
