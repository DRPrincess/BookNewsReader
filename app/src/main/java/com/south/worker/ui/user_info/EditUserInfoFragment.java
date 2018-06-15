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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baselib.utils.TimeUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.south.worker.R;
import com.south.worker.constant.LocalFileConfig;
import com.south.worker.constant.NetConfig;
import com.south.worker.data.bean.UserInfoBean;
import com.south.worker.image.LocalImageUtils;
import com.south.worker.image.UriUtils;
import com.south.worker.ui.BaseFragment;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class EditUserInfoFragment extends BaseFragment  implements EditUserInfoContact.View{

    public static final int REQUEST_CODE_START_CAMERA = 0x01;
    private static final int REQUEST_CODE_FORM_PICK = 0x02;
    EditUserInfoContact.Presenter mPresenter;
    @BindView(R.id.tvMidTitle)
    TextView tvMidTitle;
    @BindView(R.id.ivHeadImg)
    ImageView ivHeadImg;
    @BindView(R.id.edtUserName)
    EditText edtUserName;
    @BindView(R.id.tvSex)
    TextView tvSex;
    @BindView(R.id.tvBirth)
    TextView tvBirth;
    @BindView(R.id.tvIntoPart)
    TextView tvIntoPart;
    @BindView(R.id.tvEducation)
    TextView tvEducation;
    @BindView(R.id.edtHoppy)
    EditText edtHoppy;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtWeixin)
    EditText edtWeixin;
    Unbinder unbinder;


    OptionsPickerView educationPickerView;
    OptionsPickerView genderPickerView;

    TimePickerView birthPickerView;
    TimePickerView intoPartPickerView;

    List<String> genderNames;
    List<Integer> genderIds;

    String tempImageFilePath;
    AlertDialog alertDialog;

    public static EditUserInfoFragment newInstance() {

        Bundle args = new Bundle();
        EditUserInfoFragment fragment = new EditUserInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        tvMidTitle.setText("修改资料");

        mPresenter.getUserInfo(getUserId());
        mPresenter.getEducationList();


        edtUserName.setText("王爱国");
        edtHoppy.setText("阅读，篮球");
        edtPhone.setText("15356871263");
        edtWeixin.setText("15356871263");


        //条件选择器
        genderNames = new ArrayList<>();
        genderIds = new ArrayList<>();
        genderNames.add("女");
        genderNames.add("男");
        genderIds.add(0);
        genderIds.add(1);

        genderPickerView = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                tvSex.setText(genderNames.get(options1));
                mPresenter.setGender(genderIds.get(options1));
            }
        }).build();
        genderPickerView.setPicker(genderNames);


        birthPickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String text = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)+ "-" + (date.getDay());
                tvBirth.setText(text);
                mPresenter.setBirthTime(text);
            }
        }).setRange(1999, Calendar.getInstance().get(Calendar.YEAR)).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();


        intoPartPickerView = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String text = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)+ "-" + (date.getDay());
                tvIntoPart.setText(text);
                mPresenter.setIntoPartTime(text);
            }
        }).setRange(1999, Calendar.getInstance().get(Calendar.YEAR)).setType(TimePickerView.Type.YEAR_MONTH_DAY).build();





        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ivHeadImg, R.id.llLeft, R.id.btnSubmit, R.id.layoutGender, R.id.layoutBirth, R.id.layoutIntoPart, R.id.layoutEducation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLeft:
                getActivity().onBackPressed();
                break;
            case R.id.layoutGender:

                if(genderPickerView != null){
                    genderPickerView.show();
                }
                break;
            case R.id.layoutBirth:

                if(birthPickerView != null){
                    birthPickerView.show();
                }
                break;
            case R.id.layoutIntoPart:
                if(intoPartPickerView != null){
                    intoPartPickerView.show();
                }

                break;
            case R.id.layoutEducation:
                if(educationPickerView != null){
                    educationPickerView.show();
                }

                break;
            case R.id.btnSubmit:
               mPresenter.editUserInfo(getUserId(),getPartId());
                break;
            case R.id.ivHeadImg:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_upload_headimg, null);
                builder.setView(dialogView);

                dialogView.findViewById(R.id.tvAlbum).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        EditUserInfoFragmentPermissionsDispatcher.openAlbumWithPermissionCheck(EditUserInfoFragment.this);
                    }
                });

                dialogView.findViewById(R.id.tvCamera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                        EditUserInfoFragmentPermissionsDispatcher.takePhotoWithPermissionCheck(EditUserInfoFragment.this);
                    }
                });

                alertDialog = builder.create();
                alertDialog.show();

                break;
        }
    }

    @Override
    public void setPresenter(EditUserInfoContact.Presenter presenter) {

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
        edtUserName.setText(bean.RealName);
        edtHoppy.setText(bean.Hobby);
        tvEducation.setText(bean.EducationName);
        tvBirth.setText(bean.BirthTimeString);
        tvIntoPart.setText(bean.PartyTimeString);
        tvSex.setText(bean.GenderName);
        edtPhone.setText(bean.Phone);
        edtWeixin.setText(bean.WeChat);

    }

    @Override
    public void showEducationList(final List<String> educationNames, final List<Integer> educationIds) {
        educationPickerView = new OptionsPickerView.Builder(getContext(), new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                tvEducation.setText(educationNames.get(options1));
                mPresenter.setEducation(educationIds.get(options1));
            }
        }).build();
        educationPickerView.setPicker(educationNames);




    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EditUserInfoFragmentPermissionsDispatcher.onRequestPermissionsResult(EditUserInfoFragment.this, requestCode, grantResults);
    }


    /**
     * 打开相册选择
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE})
    public void openAlbum() {
        LocalImageUtils.openAlbum(EditUserInfoFragment.this, REQUEST_CODE_FORM_PICK);
    }

    /**
     * 调用相机拍照
     */
    @NeedsPermission({Manifest.permission.CAMERA})
    public void takePhoto() {
        String localFileName = TimeUtils.getCurrentDate("yyyyMMdd-HHmmssSSS") + ".png";
        tempImageFilePath = UriUtils.getFilePath(LocalFileConfig.IMAGE_ROOT_PATH, localFileName);
        LocalImageUtils.openCamera(EditUserInfoFragment.this, LocalFileConfig.IMAGE_ROOT_PATH, localFileName, REQUEST_CODE_START_CAMERA);
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

    @Override
    public String getName() {
        return edtUserName.getText().toString();
    }

    @Override
    public String getHobby() {
        return edtHoppy.getText().toString();
    }

    @Override
    public String getPhone() {
        return edtPhone.getText().toString();
    }

    @Override
    public String getWeixin() {
        return edtWeixin.getText().toString();
    }
}
