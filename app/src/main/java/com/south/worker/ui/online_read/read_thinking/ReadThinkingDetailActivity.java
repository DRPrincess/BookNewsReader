package com.south.worker.ui.online_read.read_thinking;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baselib.utils.ActivityUtils;
import com.south.worker.R;
import com.south.worker.data.bean.ReadThinkingBean;
import com.south.worker.ui.BaseActivity;
import com.south.worker.ui.BaseFragment;
import com.south.worker.ui.EditActivity;
import com.south.worker.ui.online_read.OnlineReadFragment;

public class ReadThinkingDetailActivity extends BaseActivity {

    private  final static String ARG_THINKING_ID = "thinking_id";
    private  final static String ARG_BOOKNAME = "book_name";
    private  final  static String ARG_CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        if(getIntent() == null){
            return;
        }

        int thinkingId = getIntent().getIntExtra(ARG_THINKING_ID,-1);
        String bookName = getIntent().getStringExtra(ARG_BOOKNAME);
        String content = getIntent().getStringExtra(ARG_CONTENT);

        ReadThinkingDetailFragment fragment = ReadThinkingDetailFragment.newInstance(thinkingId,bookName,content);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment ,R.id.fragment_container);

    }

    public static void startReadThinkingDetailActivity(Context context,  int thinkingId,String bookName,String content){

        Intent intent = new Intent();
        intent.putExtra(ARG_THINKING_ID,thinkingId);
        intent.putExtra(ARG_BOOKNAME,bookName);
        intent.putExtra(ARG_CONTENT,content);
        intent.setClass(context,ReadThinkingDetailActivity.class);
        context.startActivity(intent);
    }


}
