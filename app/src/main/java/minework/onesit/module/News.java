package minework.onesit.module;

import com.maxleap.MLClassName;
import com.maxleap.MLObject;

import org.joda.time.DateTime;

import minework.onesit.util.MyTimeUtil;

/**
 * Created by 无知 on 2016/12/21.
 */
@MLClassName("OneSit_News")
public class News extends MLObject {

    public News() {

    }

    //发送者
    public String getSend_user_id() {
        return getString("send_user_id");
    }

    public void setSend_user_id(String send_user_id) {
        put("send_user_id",send_user_id);
    }

    //接受者
    public String getReceive_user_id() {
        return getString("receive_user_id");
    }

    public void setReceive_user_id(String receive_user_id) {
        put("receive_user_id",receive_user_id);
    }

    //日期
    public String getNews_date(){
        return MyTimeUtil.convertCommTimeToString1(getDate("news_date"));
    }
    public void setNews_date(){
        put("news_date", DateTime.now().toDate());
    }
    //对话
    public String getNews_message(){
        return getString("news_message");
    }
    public void setNews_message(String news_message){
        put("news_message",news_message);
    }
    //是否接收
    public boolean getNews_obtain(){
        return getBoolean("news_obtain");
    }
    public void setNews_obtain(boolean hasObtain){
        put("news_obtain",hasObtain);
    }
}
