package com.luruipeng.materialnews.news.model;

import com.luruipeng.materialnews.utils.OkHttpUtils;

/**
 * Created by Ruipeng Lu on 2016/2/22 0022.
 */
public class NewsModelImpl implements NewsModel {
    @Override
    public void loadNews(String url, OkHttpUtils.ResultCallback callback) {
        System.out.println(">>>>>>>>>>>>>>>>>>>NewsModel>>>>>>>>>>>>>>");
        OkHttpUtils.get(url, callback);
    }

    @Override
    public void loadNewsDetail(String url, OkHttpUtils.ResultCallback callback) {
        OkHttpUtils.get(url,callback);
    }
}
