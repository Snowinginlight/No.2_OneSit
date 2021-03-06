package minework.onesit.util.camera;

/**
 * Created by 无知 on 2016/12/4.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.io.File;

/**
 * Created on 15/9/22.
 * 对拍照操作的代理
 */
public class CameraProxy {

    /**
     * 相机核心类
     */
    private CameraCore cameraCore;

    public CameraProxy(CameraCore.CameraResult cameraResult, Activity activity) {
        cameraCore = new CameraCore(cameraResult, activity);
    }

    /**
     * 拍照
     *
     * @param path
     * @Description:函数描述
     * @author Administrator
     * @date 2016年3月14日 下午1:55:45
     * @version V1.0.0
     */
    public void getPhoto2Camera(String path) {
        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2Camera(uri);
    }

    /**
     * 拍照截图
     *
     * @param path
     * @Description:函数描述
     * @author Administrator
     * @date 2016年3月14日 下午1:55:42
     * @version V1.0.0
     */
    public void getPhoto2CameraCrop(String path) {
        Uri uri = Uri.fromFile(new File(path));
        cameraCore.getPhoto2CameraCrop(uri);
    }

    /**
     * 选择照片
     *
     * @Description:函数描述
     * @author Administrator
     * @date 2016年3月14日 下午1:55:19
     * @version V1.0.0
     */
    public void getPhoto2Album() {
        cameraCore.getPhoto2Album();
    }

    /**
     * 选择照片，截图
     *
     * @Description:函数描述
     * @author Administrator
     * @date 2016年3月14日 下午1:55:26
     * @version V1.0.0
     */
    public void getPhoto2AlbumCrop() {
        cameraCore.getPhoto2AlbumCrop();
    }

    /**
     * 接受ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @Description:函数描述
     * @author Administrator
     * @date 2016年3月14日 下午1:55:29
     * @version V1.0.0
     */
    public void onResult(int requestCode, int resultCode, Intent data) {
        cameraCore.onResult(requestCode, resultCode, data);
    }


    public void onRestoreInstanceState(Bundle savedInstanceState) {
        cameraCore.onRestoreInstanceState(savedInstanceState);
    }

    public void onSaveInstanceState(Bundle outState) {
        cameraCore.onSaveInstanceState(outState);
    }
}