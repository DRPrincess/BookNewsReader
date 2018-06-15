package com.south.worker.constant;

import android.os.Environment;

import com.south.worker.App;


/**
 *
 *描述   ：本地存储文件的配置文件
 *
 *作者   ：Created by DR on 2018/4/20.
 */


public class LocalFileConfig {


    //App内部存储路径  /data/data/com.my.app/files
    public static final String APK_ROOT_PATH = App.getInstance().getFilesDir().getPath();

    //会随着APP的卸载而删除 外部图片存储路径 /mnt/sdcard/Android/data/packageName/files/Pictures
    public static final String IMAGE_ROOT_PATH = App.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath();





}
