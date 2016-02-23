package com.luruipeng.materialnews.news.view.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luruipeng.materialnews.R;
import com.luruipeng.materialnews.beans.NewsBean;
import com.luruipeng.materialnews.commons.Urls;
import com.luruipeng.materialnews.news.view.NewsView;
import com.luruipeng.materialnews.news.presenter.NewsPresenter;
import com.luruipeng.materialnews.news.presenter.NewsPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruipeng Lu on 2016/2/21 0021.
 */
public class NewsListFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManger;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<NewsBean> mData;
    private NewsPresenter mNewsPresenter;
    private int mType = NewsFragment.NEWS_TYPE_TOP;
    int pageIndex=0;
    NewsAdapter myAdapter;

    public static NewsListFragment newInstance(int type){
        NewsListFragment newsListFragment=new NewsListFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        newsListFragment.setArguments(bundle);
        return newsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsPresenter=new NewsPresenterImpl(getActivity(),this);
        mType=getArguments().getInt("type");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_list,null);
        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManger=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManger);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

        myAdapter=new NewsAdapter(getActivity().getApplicationContext());
        myAdapter.setOnItemClickListener(mOnItemClickListener);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setOnScrollListener(mOnScrollListener);
        onRefresh();
        return view;
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        private int lastVisibleItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManger.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == myAdapter.getItemCount()
                    && myAdapter.isShowFooter()) {
                //加载更多
                mNewsPresenter.loadNews(mType, pageIndex + Urls.PAZE_SIZE);
            }
        }
    };

    @Override
    public void onRefresh() {
        pageIndex=0;
        mNewsPresenter.loadNews(mType,pageIndex);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showNews(List<NewsBean> newsList) {
        mData=newsList;
        if(mData==null){
            mData=new ArrayList<NewsBean>();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>NewsBean>>>>>>>>>>>>>>"+mData.toString());
        myAdapter.setData(mData);
    }

    @Override
    public void addNews(List<NewsBean> newsList) {
        if(mData==null){
            mData=new ArrayList<NewsBean>();
        }
        mData.addAll(newsList);
        myAdapter.notifyDataSetChanged();
        pageIndex += Urls.PAZE_SIZE;
        //如果没有更多数据了,则隐藏footer布局
        if(newsList == null || newsList.size() == 0) {
            myAdapter.isShowFooter(false);
        }
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {
        Snackbar.make(mRecyclerView.getRootView(), getString(R.string.load_fail), Snackbar.LENGTH_SHORT).show();
    }

    private NewsAdapter.OnItemClickListener mOnItemClickListener=new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            NewsBean newsBean=myAdapter.getItem(position);
            Intent intent=new Intent(getActivity(),NewsDetailAvtivity.class);
            intent.putExtra("news",newsBean);

            View transitionView = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transitionView, getString(R.string.transition_news_img));

            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };

}
