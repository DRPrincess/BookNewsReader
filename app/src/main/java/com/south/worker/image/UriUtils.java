package com.south.worker.image;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.south.worker.BuildConfig;

import java.io.File;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DuanRui on 2017/11/17.
 */

public class UriUtils {

    /**
     * 得到文件的Uri
     * @param context
     * @param file
     * @return
     */
    public static Uri getUri(Context context, File file){
        if(context == null ||file == null){
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return  FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else {
            return  Uri.fromFile(file);
        }
    }

    public static String getFilePath(String fileFolder, String fileName){
        if(TextUtils.isEmpty(fileFolder)){
            return null;
        }
        if(TextUtils.isEmpty(fileName)){
            return null;
        }
         if(!new File(fileFolder).exists()){
             new File(fileFolder).mkdirs();
         }
        return  new File(fileFolder,fileName).getAbsolutePath();

    }

    public static Uri getUri(Context context, String filePath){
        if(context == null || TextUtils.isEmpty(filePath)){
            return null;
        }
        return   getUri(context,new File(filePath));

    }

    public static Uri getUri(Context context, String fileFolder, String fileName){
        if(context == null || TextUtils.isEmpty(fileFolder) || TextUtils.isEmpty(fileName)){
            return null;
        }
        return  getUri(context,new File(fileFolder,fileName)) ;

    }


    /**
     * 通过 Uri 得到文件的绝对路径
     * 目前测试通过路径为
     * content://media/external/images/media/29097（相册选择返回）
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
