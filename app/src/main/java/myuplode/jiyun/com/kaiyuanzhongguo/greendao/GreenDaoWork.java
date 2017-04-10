package myuplode.jiyun.com.kaiyuanzhongguo.greendao;

import java.util.List;
import myuplode.jiyun.com.kaiyuanzhongguo.news.App;

/**
 * Created by Administrator on 2017/4/9.
 */

public class GreenDaoWork implements IDao {
    @Override
    public long insert(LoginDao dao) {
        long l=App.getDaoInstant().getLoginDaoDao().insertOrReplace(dao);
        return l;
    }

    @Override
    public List<LoginDao> queryAll() {
        List<LoginDao> list;
        list = App.getDaoInstant().getLoginDaoDao().loadAll();
        return list;
    }
//    @Override
//    public long insert(LoginDao dao) {
//        long l = App.getDaoInstant().getLoginDaoDao().insertOrReplace(dao);
//        return l;
//    }
//
//    @Override
//    public List<LoginDao> queryAll() {
//        List<LoginDao> list;
//        list = App.getDaoInstant().getLoginDaoDao().loadAll();
//
//        return list;
//    }
}
