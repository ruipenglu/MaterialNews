package com.luruipeng.materialnews.news.view.widget;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.luruipeng.materialnews.R;
import com.luruipeng.materialnews.beans.NewsBean;
import com.luruipeng.materialnews.commons.Urls;
import com.luruipeng.materialnews.news.view.NewsView;
import com.luruipeng.materialnews.news.view.presenter.NewsPresenter;
import com.luruipeng.materialnews.news.view.presenter.NewsPresenterImpl;

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
    MyAdapter myAdapter;

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

        myAdapter=new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);
        return view;
    }

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
        pageIndex+= Urls.PAZE_SIZE;
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<NewsBean> mData;
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        public MyAdapter() {}

        public void setData(List<NewsBean> mData) {
            this.mData = mData;
            this.notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if(position+1==getItemCount()) {
                return TYPE_FOOTER;
            }
            else {
                return TYPE_ITEM;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType==TYPE_ITEM){
                View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
                ItemViewHolder itemViewHolder=new ItemViewHolder(v);
                return itemViewHolder;
            }
            else {
                View view = LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.footer, null);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                return new FooterViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof ItemViewHolder) {

                NewsBean news = mData.get(position);
                if(news == null) {
                    return;
                }
                ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
                ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
                Uri uri = Uri.parse(news.getImgsrc());
                ((ItemViewHolder) holder).mNewsImg.setImageURI(uri);
            }
        }

        @Override
        public int getItemCount() {
            if(mData == null) {
                return 1;
            }
            return mData.size() + 1;
        }

        class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView mTitle;
            public TextView mDesc;
            public SimpleDraweeView mNewsImg;

            public ItemViewHolder(View itemView) {
                super(itemView);
                mTitle= (TextView) itemView.findViewById(R.id.tvTitle);
                mDesc= (TextView) itemView.findViewById(R.id.tvDesc);
                mNewsImg= (SimpleDraweeView) itemView.findViewById(R.id.ivNews);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String text = "I Love " + mTitle.getText() + ".";
                Snackbar.make(v, text, Snackbar.LENGTH_SHORT).show();
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {
            public FooterViewHolder(View view) {
                super(view);
            }
        }
    }
}
