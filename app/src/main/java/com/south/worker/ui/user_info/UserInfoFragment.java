package com.south.worker.ui.user_info;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.utils.TimeUtils;
import com.baseres.CustomDialog;
import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.R;
import com.south.worker.constant.LocalFileConfig;
import com.south.worker.constant.NetConfig;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.image.LocalImageUtils;
import com.south.worker.image.UriUtils;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.EditActivity;
import com.south.worker.ui.login.LoginActivity;
import com.south.worker.ui.login.ResetPassordActivity;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

@RuntimePermissions
public class UserInfoFragment extends BaseFragment implements UserInfoContact.View {

    public static final int REQUEST_CODE_START_CAMERA = 0x01;
    private static final int REQUEST_CODE_FORM_PICK = 0x02;
    @BindView(R.id.ivHeadImg)
    ImageView ivHeadImg;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvSign)
    TextView tvSign;
    Unbinder unbinder;

    UserInfoContact.Presenter mPresenter;

    String tempImageFilePath;

    AlertDialog alertDialog;
    public static UserInfoFragment newInstance() {

        Bundle args = new Bundle();
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        mPresenter.getUserInfo(getUserId());
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.red));

        mPresenter.getUserInfo(getUserId());


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llSign, R.id.llEditInfo, R.id.llResetPassword, R.id.llCheckUpdate, R.id.llSuggestion, R.id.llLogout, R.id.ivHeadImg})
    public void onViewClicked(View view) {

        Intent intent ;

        switch (view.getId()) {
            case R.id.llSign:

                EditActivity.startEditSign(getContext(),tvSign.getText().toString());
                break;
            case R.id.llEditInfo:
                intent = new Intent(getActivity(),EditUserInfoActivity.class);
                startActivity(intent);

                break;
            case R.id.llResetPassword:
                intent = new Intent(getActivity(),ResetPassordActivity.class);
                startActivity(intent);
                break;
            case R.id.llCheckUpdate:
                Toast.makeText(getContext(), "已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
            case R.id.llSuggestion:
                EditActivity.startEditSuggestion(getContext());
                break;
            case R.id.llLogout:
                intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ivHeadImg:

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_upload_headimg, null);
                builder.setView(dialogView);

                dialogView.findViewById(R.id.tvAlbum).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        UserInfoFragmentPermissionsDispatcher.openAlbumWithPermissionCheck(UserInfoFragment.this);
                    }
                });

                dialogView.findViewById(R.id.tvCamera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        UserInfoFragmentPermissionsDispatcher.takePhotoWithPermissionCheck(UserInfoFragment.this);
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                break;
        }
    }

    @Override
    public void setPresenter(UserInfoContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showUserInfo(UserInfoBean bean) {

        if(!TextUtils.isEmpty(bean.HeadPortrait)){
            String imageUrl = NetConfig.IMAGE_HEADIMG_PREFIXX + bean.HeadPortrait;
            GlideApp.with(getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.list_default)
                    .error(R.drawable.list_default)
                    .fitCenter()
                    .into(ivHeadImg);
        }
        tvSign.setText(bean.Autograph);
        tvUserName.setText(bean.RealName +"  " + bean.GenderName +"  "+ bean.EducationName);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserInfoFragmentPermissionsDispatcher.onRequestPermissionsResult(UserInfoFragment.this, requestCode, grantResults);
    }


    /**
     * 打开相册选择
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void openAlbum() {
        LocalImageUtils.openAlbum(UserInfoFragment.this, REQUEST_CODE_FORM_PICK);
    }

    /**
     * 调用相机拍照
     */
    @NeedsPermission({Manifest.permission.CAMERA})
    public void takePhoto() {
        String localFileName = TimeUtils.getCurrentDate("yyyyMMdd-HHmmssSSS") + ".png";
        tempImageFilePath = UriUtils.getFilePath(LocalFileConfig.IMAGE_ROOT_PATH, localFileName);
        LocalImageUtils.openCamera(UserInfoFragment.this, LocalFileConfig.IMAGE_ROOT_PATH, localFileName, REQUEST_CODE_START_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                //相机返回
                case REQUEST_CODE_START_CAMERA:
                    break;
                //选择相册返回
                case REQUEST_CODE_FORM_PICK:

                    Uri imageUri = data.getData();


                    if (imageUri != null) {
                        tempImageFilePath = UriUtils.getRealFilePath(getContext(), imageUri);
                    }

                    break;
            }
            // 获取图片
            try {
                if (!TextUtils.isEmpty(tempImageFilePath)) {
                    Bitmap bit = BitmapFactory.decodeStream(new FileInputStream(new File(tempImageFilePath)));
                    ivHeadImg.setImageBitmap(bit);
                    mPresenter.uploadHeadImg(getUserId(), tempImageFilePath);
                } else {
                    showTipDialog("上传的图片为空");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
