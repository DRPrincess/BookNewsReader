package com.south.worker.image;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.widget.Toast;


import com.south.worker.BuildConfig;

import java.io.File;

/**
 * 描述   ：获取本地图片工具类
 * <p>
 * 作者   ：Created by DuanRui on 2017/11/15.
 */

public class LocalImageUtils {



    public enum CameraType {
        System,//系统相机
    }
    public enum AlbumType {
        System,//系统相册
    }

    /**
     * 通知手机扫描这个文件，以便相册可以看到图片
     * @param context
     * @param fileName
     */
    public static void notifySystemAlbum(Context context, String fileName) {
        //通知相册扫描这个文件夹
        Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(fileName)));
        context.sendBroadcast(localIntent);
    }

    /**
     * Fragment中调用系统相机拍照
     *
     * @param fragment
     * @param fileFolder
     * @param fileName
     * @param requestCode
     * @return
     */
    public static Uri openCamera(@NonNull Fragment fragment, @NonNull String fileFolder, @NonNull String fileName, int requestCode) {

        Context context = fragment.getContext();
        Intent intent = getCameraIntent(context, fileFolder, fileName, CameraType.System);
        if (intent == null) {
            return null;
        }
        fragment.startActivityForResult(intent, requestCode);

        return intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);

    }




    /**
     * Activity中调用系统相机拍照
     *
     * @param activity
     * @param fileFolder
     * @param fileName
     * @param requestCode
     * @return
     */
    public static Uri openCamera(@NonNull Activity activity, @NonNull String fileFolder, @NonNull String fileName, int requestCode) {

        Context context = activity;
        Intent intent = getCameraIntent(context, fileFolder, fileName, CameraType.System);
        if (intent == null) {
            return null;
        }
        activity.startActivityForResult(intent, requestCode);
        return intent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);

    }


    /**
     * 打开相册
     *
     * @param activity
     * @param requestCode
     */
    public static void openAlbum(@NonNull Activity activity, int requestCode) {
        Context context = activity;
        Intent intent = getAlbumIntent(context,AlbumType.System);
        if(intent == null){
            return;
        }
        activity.startActivityForResult(intent, requestCode);



        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/png");
        activity.startActivityForResult(intentToPickPic, requestCode);



    }



    /**
     * 打开相册
     *
     * @param fragment
     * @param requestCode
     */
    public static void openAlbum(@NonNull Fragment fragment, int requestCode) {

        Context context = fragment.getContext();
        Intent intent = getAlbumIntent(context,AlbumType.System);
        if(intent == null){
            return;
        }
        fragment.startActivityForResult(intent, requestCode);




//        Matisse.from(fragment)
//                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
//                .theme(R.style.Matisse_Dracula)
//                .countable(true)
//                .maxSelectable(1)
//                .gridExpectedSize(fragment.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                .thumbnailScale(0.85f)
//                .imageEngine(new GlideEngine())
//                .forResult(requestCode);

    }

    ;


    private static Intent getCameraIntent(@NonNull Context context, @NonNull String fileFolder, @NonNull String fileName, CameraType cameraType) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "未设置相机权限", Toast.LENGTH_SHORT).show();
            return null;
        }
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, "未设置存储权限", Toast.LENGTH_SHORT).show();
//            return null;
//        }

        if (TextUtils.isEmpty(fileFolder) || TextUtils.isEmpty(fileName)) {
            return null;
        }

        if (!new File(fileFolder).exists()) {
            new File(fileFolder).mkdirs();
        }
        File tempFile = new File(fileFolder, fileName);

        Intent intent = null;
        switch (cameraType) {
            case System:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                break;
        }


        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
        } else {
            uri = Uri.fromFile(tempFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        return intent;

    }


    private static Intent getAlbumIntent(Context context,AlbumType cameraType){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "未设置存储读权限", Toast.LENGTH_SHORT).show();
            return null ;
        } else if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "未设置存储写权限", Toast.LENGTH_SHORT).show();
            return null ;
        }

        Intent intent = null;
        switch (cameraType) {
            case System:
                intent = new Intent(Intent.ACTION_PICK, null);
                // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                break;
        }


        return intent;

    }
}
