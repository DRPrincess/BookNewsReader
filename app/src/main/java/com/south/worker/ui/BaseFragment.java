package com.south.worker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baselib.utils.SharedPreferencesUtil;
import com.baseres.CustomDialog;
import com.south.worker.R;
import com.south.worker.constant.IntentConfig;
import com.south.worker.constant.SharedPreferencesConfig;
import com.south.worker.ui.login.LoginActivity;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/5/31.
 */

public class BaseFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showTipDialog(String message){

        if(TextUtils.isEmpty(message)){
            return;
        }
        final CustomDialog dialog = new CustomDialog(getContext());
        dialog.setTitleText("提示");
        dialog.setContentText(message);
        dialog.setLeftText("我知道了");
        dialog.setLeftClickListener(new CustomDialog.OnCustomDialogClickListener() {
            @Override
            public void onClick(CustomDialog customDialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void showToast(String message){

        if(TextUtils.isEmpty(message)){
            return;
        }
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }



    /**
     * 获取用户ID
     * @return
     */
    public  int getUserId(){
        int userId = SharedPreferencesUtil.getInt(getContext(), SharedPreferencesConfig.SHARED_KEY_USER_ID, -1);
        if(userId == -1){
            goLogin();
        }
        return  userId;
    }

    /**
     * 获取党支部ID
     * @return
     */
    public  int getPartId(){
        int partId = SharedPreferencesUtil.getInt(getContext(), SharedPreferencesConfig.SHARED_KEY_USER_PART_ID, -1);
        if(partId == -1){
            goLogin();
        }
        return  partId;
    }

    /**
     * 获取用户名称
     * @return
     */
    public String getUserName(){
        String userName = SharedPreferencesUtil.getString(getContext(), SharedPreferencesConfig.SHARED_KEY_USER_NAME, "");

        if(TextUtils.isEmpty(userName)){
            goLogin();
        }
        return userName;
    }



    /**
     * 超时，重新登录
     */
    public void goLogin(){
        SharedPreferencesUtil.clearData(getActivity(),SharedPreferencesConfig.SHARED_KEY_USER_ID);
        SharedPreferencesUtil.clearData(getActivity(),SharedPreferencesConfig.SHARED_KEY_USER_PART_ID);
        SharedPreferencesUtil.clearData(getActivity(),SharedPreferencesConfig.SHARED_KEY_USER_HEADER_IMG);
        Bundle bundle = new Bundle();
        bundle.putString(IntentConfig.INTENT_KEY_LOGIN, "tokenRunOut");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
