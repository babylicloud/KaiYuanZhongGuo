package myuplode.jiyun.com.kaiyuanzhongguo.zonghe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.androidkun.callback.PullToRefreshListener;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import myuplode.jiyun.com.kaiyuanzhongguo.R;
import myuplode.jiyun.com.kaiyuanzhongguo.bean.Student;
import myuplode.jiyun.com.kaiyuanzhongguo.news.App;
import myuplode.jiyun.com.kaiyuanzhongguo.news.RetrofitInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TwoActivity extends Activity {
    private ArrayList<Student> stuList = new ArrayList<>();
    private ListAdapter adapter;
    private PullToRefreshRecyclerView mRecyclerView;
    private int index;
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_search);
        mRecyclerView = (PullToRefreshRecyclerView) findViewById(R.id.Two_Search_RecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        getRefresh();

    }

    public void getRefresh() {
        Intent intent = getIntent();
        name = intent.getStringExtra("search");
        getRetrofit("blog", name, "0", "10");
        //记录存储当前页面信息
//        mShared = getSharedPreferences("data", MODE_PRIVATE);
//        mEditor = mShared.edit();
//        index = mShared.getInt("index", 1);
        //解析网络数据，每次解析展示出十条信息，
//        index++;
//        mEditor.putInt("index", index);
//        mEditor.commit();
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
                        stuList.clear();
                        //刷新数据，让每次加载数据时++，加载另一个页面
                        getRetrofit("blog", name, "0", "5");

                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.setLoadMoreComplete();
                        getRetrofit("blog", name, "0", "10");

                    }
                }, 2000);
            }
        });


    }


    public void getRetrofit(String catalog, String content, String pageIndex, String pageSize) {
        Retrofit retrofit = App.getMyRetrofit();
        RetrofitInterface anInterface = retrofit.create(RetrofitInterface.class);
        Call<ResponseBody> call = anInterface.getSerarch(catalog, content, pageIndex, pageSize);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String string = response.body().string();
                        StringReader reader = new StringReader(string);
                        InputSource source = new InputSource(reader);
                        SAXParserFactory spf = SAXParserFactory.newInstance();
                        SAXParser parser = spf.newSAXParser();
                        XMLReader xr = parser.getXMLReader();
                        SearchDefault handler = new SearchDefault();
                        xr.setContentHandler(handler);
                        xr.parse(source);
                        ArrayList<Student> mList = handler.getmList();
                        for (Student student : mList) {
                            Log.i("这是", student.toString());
                        }
                        if (mList != null) {
                            stuList.addAll(mList);
                        }
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }


                        adapter = new ListAdapter(TwoActivity.this, mList);
                        mRecyclerView.setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    class ListAdapter extends BaseAdapter<Student> {


        public ListAdapter(Context context, List<Student> datas) {
            super(context, R.layout.search_item, datas);
        }

        @Override
        public void convert(ViewHolder holder, Student student) {
            holder.setText(R.id.Search_TextOne, student.getAuthor());
            holder.setText(R.id.Search_TextTwo, student.getDescription());
        }
    }

}
