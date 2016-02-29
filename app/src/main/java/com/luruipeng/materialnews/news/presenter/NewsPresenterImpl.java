package com.luruipeng.materialnews.news.presenter;

import android.content.Context;

import com.luruipeng.materialnews.beans.NewsBean;
import com.luruipeng.materialnews.commons.Urls;
import com.luruipeng.materialnews.news.view.NewsJsonUtils;
import com.luruipeng.materialnews.news.model.NewsModel;
import com.luruipeng.materialnews.news.model.NewsModelImpl;
import com.luruipeng.materialnews.news.view.NewsView;
import com.luruipeng.materialnews.news.view.widget.NewsFragment;
import com.luruipeng.materialnews.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by Ruipeng Lu on 2016/2/22 0022.
 */
public class NewsPresenterImpl implements NewsPresenter {
    Context mContext;
    NewsView mNewsView;
    NewsModel mNewsModel;

    public NewsPresenterImpl(Context mContext,NewsView newsView) {
        this.mContext = mContext;
        mNewsView=newsView;
        mNewsModel=new NewsModelImpl();
    }

    @Override
    public void loadNews(final int type, final int page) {
        String url = getUrl(type, page);
        //只有第一页的或者刷新的时候才显示刷新进度条
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>"+url);  success
        if(page == 0) {
            mNewsView.showProgress();
        }
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+response);
                List<NewsBean> newsBeanList = NewsJsonUtils.readJsonNewsBeans(response, getID(type));
                mNewsView.hideProgress();
                if(page == 0) {
                    mNewsView.showNews(newsBeanList);
                } else {
                    mNewsView.addNews(newsBeanList);
                }
            }

            @Override
            public void onFailure(Exception e) {
                mNewsView.hideProgress();
            }
        };
        System.out.println(">>>>>>>>>>>>>>>>>>>loadNews>>>>>>>>>>>>>>");
        mNewsModel.loadNews(url, loadNewsCallback);
    }

    private String getUrl(int type, int pageIndex) {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(pageIndex).append(Urls.END_URL);
        return sb.toString();
    }

    private String getID(int type) {
        String id;
        switch (type) {
            case NewsFragment.NEWS_TYPE_TOP:
                id = Urls.TOP_ID;
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                id = Urls.NBA_ID;
                break;
            case NewsFragment.NEWS_TYPE_CARS:
                id = Urls.CAR_ID;
                break;
            case NewsFragment.NEWS_TYPE_JOKES:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }

}
