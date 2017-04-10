package myuplode.jiyun.com.kaiyuanzhongguo.news;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface RetrofitInterface {
    @GET("action/api/news_list")
    Call<ResponseBody> getLogin(@Query("catalog") String catalog, @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);

    @GET("action/api/news_detail")
        //解析网址
    Call<ResponseBody> getLoginId(@Query("id") String id);

    @GET("action/api/search_list?catalog=blog")
    Call<ResponseBody> getSerarch(@Query("catalog") String catalog, @Query("content") String content,
                                  @Query("pageIndex") String pageIndex, @Query("pageSize") String pageSize);
    @FormUrlEncoded()
    @POST("action/api/login_validate")
    Call<ResponseBody> Login(@Field("username") String username, @Field("pwd") String pwd, @Field("keep_login") String keep_login);

}
