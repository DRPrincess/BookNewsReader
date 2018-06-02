package com.south.worker.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baselib.utils.LogUtils;
import com.jaeger.library.StatusBarUtil;
import com.south.worker.App;
import com.south.worker.R;
import com.south.worker.ui.BaseActivity;
import com.south.worker.ui.home.HomeFragment;
import com.south.worker.ui.home.HomePresenter;
import com.south.worker.ui.my_part.MyPartFragment;
import com.south.worker.ui.my_part.MyPartPresenter;
import com.south.worker.ui.online_read.OnlineReadFragment;
import com.south.worker.ui.online_read.OnlineReadPresenter;
import com.south.worker.ui.user_info.UserInfoFragment;
import com.south.worker.ui.user_info.UserInfoPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @BindView(R.id.rbtnHome)
    RadioButton rbtnHome;
    @BindView(R.id.rbtnOnlineRead)
    RadioButton rbtnOnlineRead;
    @BindView(R.id.rbtnMyPart)
    RadioButton rbtnMyPart;
    @BindView(R.id.rbtnUserInfo)
    RadioButton rbtnUserInfo;
    @BindView(R.id.rgMain)
    RadioGroup rgMain;



    HomeFragment mHomeFragment;
    MyPartFragment mMyPartFragment;
    OnlineReadFragment mOnlineReadFragment;
    UserInfoFragment mUserInfoFragment;


    //当前Fragent的下标
    private int currentIndex = -1;

    long mCurrentTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //RadioGroup选中事件监听 改变fragment
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                LogUtils.d("rgMain"+checkedId);

                switch (checkedId){

                    case R.id.rbtnHome:
                        setIndexSelected(0);
                        break;
                    case R.id.rbtnOnlineRead:
                        setIndexSelected(1);
                        break;
                    case R.id.rbtnMyPart:
                        setIndexSelected(2);
                        break;
                    case R.id.rbtnUserInfo:
                        setIndexSelected(3);
                        break;

                }


            }
        });

        rbtnHome.setChecked(true);


    }


    //设置Fragment页面
    private void setIndexSelected(int index) {
        if (currentIndex == index) {
            return;
        }
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;

        switch (index){
            case 0:
                if(mHomeFragment == null){
                    mHomeFragment = HomeFragment.newInstance();
                    HomePresenter presenter = new HomePresenter(this,mHomeFragment);
                    mHomeFragment.setPresenter(presenter);
                }
                fragment = mHomeFragment;
                break;
            case 1:
                if(mOnlineReadFragment== null){
                    mOnlineReadFragment = OnlineReadFragment.newInstance();
                    OnlineReadPresenter presenter = new OnlineReadPresenter(this,mOnlineReadFragment);
                    mOnlineReadFragment.setPresenter(presenter);
                }
                fragment = mOnlineReadFragment;

                break;
            case 2:
                if(mMyPartFragment == null){
                    mMyPartFragment = MyPartFragment.newInstance();
                    MyPartPresenter presenter = new MyPartPresenter(this,mMyPartFragment);
                    mMyPartFragment.setPresenter(presenter);
                }
                fragment = mMyPartFragment;
                break;
            case 3:
                if(mUserInfoFragment == null){
                    mUserInfoFragment = UserInfoFragment.newInstance();
                    UserInfoPresenter presenter = new UserInfoPresenter(this,mUserInfoFragment);
                    mUserInfoFragment.setPresenter(presenter);
                }
                fragment = mUserInfoFragment;
                break;
        }




        //隐藏当前Fragment
        if(mHomeFragment != null){
            ft.hide(mHomeFragment);
        }
        if(mOnlineReadFragment != null){
            ft.hide(mOnlineReadFragment);
        }
        if(mMyPartFragment != null){
            ft.hide(mMyPartFragment);
        }
        if(mUserInfoFragment != null){
            ft.hide(mUserInfoFragment);
        }


        //判断Fragment是否已经添加
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment_container, fragment).show(fragment);
        } else {
            //显示新的Fragment
            ft.show(fragment);
        }



        ft.commit();
        currentIndex = index;
    }


    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mCurrentTime) < 2000) {
            App.getInstance().finishProgram();
        } else {
            Toast.makeText(this, getResources().getString(R.string.msg_exit_app_tips), Toast.LENGTH_SHORT).show();
            mCurrentTime = System.currentTimeMillis();
        }
    }

}
