package com.luruipeng.materialnews.news.view.widget;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.luruipeng.materialnews.R;
import com.luruipeng.materialnews.beans.NewsBean;

/**
 * Created by Ruipeng Lu on 2016/2/23 0023.
 */
public class NewsDetailAvtivity extends AppCompatActivity {

    NewsBean mNewsBean;

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

        mNewsBean= (NewsBean) getIntent().getSerializableExtra("news");
//        mNewsBean=new NewsBean();
//        mNewsBean.setTitle("123123");
        CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mNewsBean.getTitle());
    }
}
