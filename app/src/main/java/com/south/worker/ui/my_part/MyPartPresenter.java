package com.south.worker.ui.my_part;

import android.content.Context;

import com.south.worker.R;
import com.south.worker.data.bean.NewsBean;
import com.south.worker.data.bean.PartActivityBean;
import com.south.worker.ui.home.HomeContact;

import java.util.ArrayList;
import java.util.Random;

/**
 * 描述   ：
 * <p>
 * 作者   ：Created by DR on 2018/6/2.
 */

public class MyPartPresenter implements MyPartContact.Presenter {

    Context mContext;
    MyPartContact.View mView;


    int[] imageIds = {R.drawable.show_data_news_weixin1,
            R.drawable.show_data_news_weixin2,
            R.drawable.show_data_news_weixin3,
            R.drawable.show_data_news_weixin4,
            R.drawable.show_data_news_weixin5,
            R.drawable.show_data_news_weixin6,
            R.drawable.show_data_news_weixin7,
            R.drawable.show_data_news_weixin8,
            R.drawable.show_data_news_weixin9,
            R.drawable.show_data_news_weixin10,
            R.drawable.show_data_news_weixin11,
            R.drawable.show_data_news_weixin12,
            R.drawable.show_data_news_weixin13,
            R.drawable.show_data_news_weixin14
    };


    String[] urls = {
            "https://mp.weixin.qq.com/s/zewcR8nURlXHYCtjBgBGaQ",
            "https://mp.weixin.qq.com/s/U5g4AMf0z0U9yNIoA28QGw",
            "https://mp.weixin.qq.com/s/mpLY4p4yrTMm5CALUkajQg",
            "https://mp.weixin.qq.com/s/_zH6d6VwIwwabXVEQPpYgw",
            "https://mp.weixin.qq.com/s/qlY5--XIZt9T_x46qQHzxQ",
            "https://mp.weixin.qq.com/s/rpd0OsVs2usO0-1cL4t77A",
            "https://mp.weixin.qq.com/s/Ca50q6yUPWRv3z62GycWFw",
            "https://mp.weixin.qq.com/s/aXFOVBPbkt82xIPxyi_7KQ",
            "https://mp.weixin.qq.com/s/gftEL8LYwrV5zv05lOUBaQ",
            "https://mp.weixin.qq.com/s/XEb_YB0SbEKbopPNb3sa2Q",
            "https://mp.weixin.qq.com/s/MeZpX-3X-wr4gokRDVOW8g",
            "https://mp.weixin.qq.com/s/xMP3u9ETfiGE3EgvMhKIrw",
            "https://mp.weixin.qq.com/s/8PFEc7QD15zz8Gm-aap14A",
            "https://mp.weixin.qq.com/s/MmKP9EWPA8lhEvGUaKotew"
    };

    String[] titles = {
            "【一线风采】夏夜施工忙",
            "【南工故事】高铁时代即将来临 人员提前介入保安全",
            " 安全生产月丨一组漫画送给你，轻松接受安全知识",
            "【六一“铁娃”说爸妈】“六一”啦！铁爸铁妈们，这里有一群“铁娃”的留言，请注意查收！",
            "【南工故事】你的平安，我的责任",
            "【企业文化三年工程·传统传承】一声汽笛 鸣奏百年交响",
            "【一线风采】风里雨里 线路上等你",
            "【干部课堂】党员干部应知名词100解",
            "【聚焦媒体】南阳工务段：“集中修”紧张进行时",
            "【集中修特刊】穿行在深山的集中修“战车”",
            "【集中修特刊】天气很“任性”，他们从不“Care”",
            "【集中修特刊】穿行在深山的集中修“战车”",
            "新时代·铁路榜样｜徐前凯：5秒钟的决定，拯救一条生命！",
            "【南工风采】每一个铁路人都是\"特种兵\"",
            "【聚焦媒体】中原男儿好气势！ “集中修战队”顺利“起航”"};

    String[] contents = {
            "平西桥梁车间紧密围绕“质量提升年”工作主题，扎实推进安全管理工作，健全完善安全管理体系，严格落实“一施工三方案”，提前召开方案制定会、方案布置会，卡控作业安全风险点",
            "郑州至重庆万州的高速铁路，预计2019年底开通，届时，一日重庆往返不再是传说",
            "安全生产，从我开始，咱们走起来！",
            "今天是“六一”国际儿童节，小编特邀了几位小朋友讲述他们眼中的铁爸铁妈。下面，我们把话筒递给孩子，看看这群“铁娃”们怎么说！",
            "如果你经常乘坐火车那你一定不会忘记在你不经意间会自动开启“黑白”切换模式，这是列车经过隧道时产生的别样“特效”",
            "郑州，一座火车拉来的城市，因京汉铁路开通而繁荣兴盛。但是，在旧中国，京汉铁路辛勤劳作的工人却始终生活在水深火热之中。",
            "唐河线路车间进一步夯实安全基础，组织干部职工进行整理道床作业，避免大雨之后道床不实造成病害。",
            "中国共产党是中国工人阶级的先锋队，同时是中国人民和中华民族的先锋队，是中国特色社会主义事业的领导核心，代表中国先进生产力的发展要求，代表中国先进文化的前进方向，代表中国最广大人民的根本利益。",
            "4月份以来，郑州局集团有限公司南阳工务段管内进入了“集中修”施工阶段，针对集中修工期紧、任务重的情况，该段严格贯彻“依靠技术进步，促进生产发展”方针，充分发挥大型养路机械优势",
            "雨色朦朦，在伏牛山深处的宁西线上却是人头济济。随着一声长笛，线路大型捣固车有力的驶入K267+900米处施工地点，嘈杂的机车轰鸣声和大伙儿的吆喝声打断了伏牛山清晨的寂静",
            "可是，有那么一群人，无论刮风下雨还是酷暑高温,都不会动摇他们守护‘这条路’的心",
            "用一条腿去换取一个人的生命，他值得我们所有人学习。眼泪止不住落下，他是我们的榜样，是我们铁路的骄傲",
            "南阳工务段平顶山西综合维修车间组织干部职工240人对平顶山西站40#道岔进行清筛施工",
            "在南阳西站的西岔区，几百个“黄马褂”拥簇着三台大型挖掘机械齐聚在152-158号复式交分道岔上"};

    public MyPartPresenter(Context context, MyPartContact.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getData(int page, int pageNum, String type, String searchContent) {

        ArrayList<PartActivityBean> data = new ArrayList<>();

        switch (type) {
            case "0":
                for (int i = 0; i < 5; i++) {

                    PartActivityBean bean = new PartActivityBean(
                            titles[i],
                            contents[i],
                            imageIds[i],
                            urls[i]);
                    data.add(bean);
                }
                break;
            case "1":
                for (int i = 5; i < 10; i++) {

                    PartActivityBean bean = new PartActivityBean(
                            titles[i],
                            contents[i],
                            imageIds[i],
                            urls[i]);
                    data.add(bean);
                }
                break;
            case "2":
                for (int i = 10; i < 14; i++) {

                    PartActivityBean bean = new PartActivityBean(
                            titles[i],
                            contents[i],
                            imageIds[i],
                            urls[i]);
                    data.add(bean);
                }
                break;
        }


        mView.showData(data);
    }
}
