package minework.onesit.util;

/**
 * Created by 无知 on 2016/11/11.
 */

public class MyUtil {
    public static final void sleep(long times){
        try {
            Thread.sleep(times);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
