package com.south.worker.data;


import com.south.worker.data.bean.BannerBean;
import com.south.worker.data.bean.NewUrlBean;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;

import java.util.List;

import io.reactivex.Observable;


/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/9.
 */

public interface NewsDataSource {

    //首页新闻
    Observable<List<NewsBean>> getNews(int pageNum, int page,int type,String searchContent);

    //支部新闻
    Observable<List<PartActivityBean>> getPartNews(int pageNum, int page,int partId,int type,String searchContent);

    //首页banner
    Observable<List<BannerBean>> getNewsBanner();

    //支部Banner
    Observable<List<BannerBean>> getPartBanner(int partId);

    //查看新闻
    Observable<NewUrlBean> getNewsUrl(int newsId);
}
