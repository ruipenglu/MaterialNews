package com.luruipeng.materialnews.news.view.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.luruipeng.materialnews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ruipeng Lu on 2016/2/21 0021.
 */
public class NewsFragment extends Fragment {

    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;

    TabLayout mTabLayout;
    ViewPager mViewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,null);
        mTabLayout= (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager= (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(3);
        initViewPager();
        initTabLayout();
        return view;
    }

    private void initTabLayout() {
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.top));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.nba));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.cars));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.jokes));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initViewPager() {
        MyPagerAdapter mAdapter=new MyPagerAdapter(getChildFragmentManager());
        mAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_TOP), getString(R.string.top));
        mAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_NBA), getString(R.string.nba));
        mAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_CARS), getString(R.string.cars));
        mAdapter.addFragment(NewsListFragment.newInstance(NEWS_TYPE_JOKES), getString(R.string.jokes));
        mViewPager.setAdapter(mAdapter);
    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> mFragmentList=new ArrayList<Fragment>();
        private List<String> mTitles=new ArrayList<String>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment,String title){
            mFragmentList.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
