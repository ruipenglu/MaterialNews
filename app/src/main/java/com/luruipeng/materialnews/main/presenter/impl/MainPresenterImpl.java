package com.luruipeng.materialnews.main.presenter.impl;

import com.luruipeng.materialnews.R;
import com.luruipeng.materialnews.main.presenter.MainPresenter;
import com.luruipeng.materialnews.main.view.MainView;

/**
 * Created by Ruipeng Lu on 2016/2/21 0021.
 */
public class MainPresenterImpl implements MainPresenter {
    MainView mMainView;

    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;
    }

    @Override
    public void switchNavigation(int id) {

        if (id == R.id.navigation_item_about) {
            mMainView.switch2About();
        } else if (id == R.id.navigation_item_news) {
            mMainView.switch2News();
        }
    }
}
