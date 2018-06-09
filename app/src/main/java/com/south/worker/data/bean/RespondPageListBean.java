package com.south.worker.data.bean;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/10.
 */

public class RespondPageListBean {

    public ResultBean Result;

    public  class ResultBean {
        public JsonArray Items;
        public String pageNum;
        public String NumPerPage;
        public String TotalCount;
    }

}
