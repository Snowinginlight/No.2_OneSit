package minework.onesit.module;

import com.maxleap.MLObject;
import com.maxleap.MLClassName;
/**
 * Created by 无知 on 2016/11/11.
 */
@MLClassName("OneSit_User")
public class User extends MLObject {

    public User() {}
    //用户名
    public String getUser_id() {return getString("user_id");}

    public void setUser_id(String user_id){put("user_id",user_id);}
    //密码
    public String getUser_password() {return getString("user_password");}

    public void setUser_password(String user_password) {put("user_password",user_password);}
    //邮箱
    public String getUser_email() {
        return getString("user_email");
    }

    public void setUser_email(String user_email) {put("user_email",user_email);}

}
