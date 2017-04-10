package myuplode.jiyun.com.kaiyuanzhongguo.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/4/9.
 */
@Entity
public class LoginDao {
    @Id
    private Long id;
    private String username;
    private String pwd;
    private String keep_login;
    @Generated(hash = 1959061626)
    public LoginDao(Long id, String username, String pwd, String keep_login) {
        this.id = id;
        this.username = username;
        this.pwd = pwd;
        this.keep_login = keep_login;
    }
    @Generated(hash = 1277520868)
    public LoginDao() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getKeep_login() {
        return this.keep_login;
    }
    public void setKeep_login(String keep_login) {
        this.keep_login = keep_login;
    }

   
}
