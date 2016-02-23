package com.luruipeng.materialnews.news.view.widget;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.luruipeng.materialnews.R;
import com.luruipeng.materialnews.beans.NewsBean;
import com.luruipeng.materialnews.news.view.NewsDetailView;
import com.luruipeng.materialnews.news.view.presenter.NewsDetailPresenter;
import com.luruipeng.materialnews.news.view.presenter.NewsDetailPresenterImpl;
import com.luruipeng.materialnews.utils.ImageLoaderUtils;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Ruipeng Lu on 2016/2/23 0023.
 */
public class NewsDetailAvtivity extends AppCompatActivity implements NewsDetailView{

    NewsBean mNewsBean;

    NewsDetailPresenter mNewsDetailPresenter;
    HtmlTextView mHtmlTextView;
    ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mHtmlTextView= (HtmlTextView) findViewById(R.id.htNewsContent);
        mProgressBar= (ProgressBar) findViewById(R.id.progress);

        mNewsBean= (NewsBean) getIntent().getSerializableExtra("news");
//        mNewsBean=new NewsBean();
//        mNewsBean.setTitle("123123");
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mNewsBean.getTitle());
        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mNewsBean.getImgsrc());

        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
        mNewsDetailPresenter.loadNewsDetail(mNewsBean.getDocid());
    }

    @Override
    public void showNewsDetialContent(String newsDetailContent) {
        mHtmlTextView.setHtmlFromString(newsDetailContent, new HtmlTextView.LocalImageGetter());

    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }
}
