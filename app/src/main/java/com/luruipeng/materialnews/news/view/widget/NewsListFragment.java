package com.luruipeng.materialnews.news.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luruipeng.materialnews.R;

/**
 * Created by Ruipeng Lu on 2016/2/21 0021.
 */
public class NewsListFragment extends Fragment {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManger;
    String[] myDataSet;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news_list,null);
        mRecyclerView= (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManger=new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManger);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));

        myDataSet = new String[]{"JAVA", "Objective-C", "C", "C++", "Swift",
                "GO", "JavaScript", "Python", "Ruby", "HTML", "SQL", "Objective-C", "C", "C++", "Swift",
                "GO", "JavaScript", "Python", "Ruby", "HTML", "SQL"};
        MyAdapter myAdapter=new MyAdapter(getActivity(),myDataSet);
        mRecyclerView.setAdapter(myAdapter);
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private String[] mDataSet;

        public MyAdapter(Context context,String[]mDataSet) {
            this.mDataSet = mDataSet;
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView= (TextView) itemView.findViewById(R.id.textView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                String s="I love "+ mTextView.getText().toString();
                Snackbar.make(v,s,Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
            ViewHolder viewHolder=new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mDataSet[position]);
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }
}
