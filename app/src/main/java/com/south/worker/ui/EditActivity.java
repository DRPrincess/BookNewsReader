package com.south.worker.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.ui.user_info.AddSuggestFragment;
import com.south.worker.ui.user_info.EditSignFragment;

public class EditActivity extends BaseActivity {

    public  final static String  Intent_Key_Type = "type";
    public  final static String  Intent_Key_TEXT = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if(getIntent() == null){
            return;
        }

        String type = getIntent().getStringExtra(Intent_Key_Type);
        String text = getIntent().getStringExtra(Intent_Key_TEXT);

        Fragment fragment = null;

        if(TextUtils.isEmpty(type)){
            return;
        }
        switch (type){
            case "sign":
                fragment = EditSignFragment.newInstance(text);
                break;
            case "suggustion":
                fragment = AddSuggestFragment.newInstance();
                break;
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.fragment_container);
    }


    public static void startEditSign(Context context,String text){

        Intent intent = new Intent();
        intent.putExtra(Intent_Key_Type,"sign");
        intent.putExtra(Intent_Key_TEXT,text);
        intent.setClass(context,EditActivity.class);
        context.startActivity(intent);
    }

    public static void startEditThinking(Context context,String text,int bookId,String bookName){

        Intent intent = new Intent();
        intent.putExtra(Intent_Key_Type,"thinking");
        intent.putExtra(Intent_Key_TEXT,text);
        intent.setClass(context,EditActivity.class);
        context.startActivity(intent);
    }
    public static void startEditSuggestion(Context context){

        Intent intent = new Intent();
        intent.putExtra(Intent_Key_Type,"suggustion");
        intent.setClass(context,EditActivity.class);
        context.startActivity(intent);
    }
}
