package com.luruipeng.materialnews.news.view.presenter;

import android.content.Context;

/**
 * Created by Ruipeng Lu on 2016/2/22 0022.
 */
public class NewsPresenterImpl implements NewsPresenter {
    Context mContext;

    public NewsPresenterImpl(Context mContext) {
        this.mContext = mContext;
    }
    
    @Override
    public void loadNews() {

    }
}
