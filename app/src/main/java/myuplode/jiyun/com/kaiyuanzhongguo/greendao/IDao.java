package myuplode.jiyun.com.kaiyuanzhongguo.greendao;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Administrator on 2017/4/9.
 */

public interface IDao {
    long insert(LoginDao dao);
    List<LoginDao> queryAll();
}
