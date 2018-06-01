package com.south.worker.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.south.worker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.rbtnHome:
                        break;

                    case R.id.rbtnOnlineRead:
                        break;
                    case R.id.rbtnMyPart:
                        break;
                    case R.id.rbtnUserInfo:
                        break;

                }


            }
        });



    }
}
