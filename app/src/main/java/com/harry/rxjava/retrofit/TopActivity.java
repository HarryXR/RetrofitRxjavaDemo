/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.rxjava.retrofit;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.R;
import com.harry.adapter.AfRecyclerAdapter;
import com.harry.adapter.AfViewHolder;
import com.harry.refresh.SwipeRefreshLayoutDirection;
import com.harry.rv.rxretrofit.controller.MovieController;
import com.harry.rv.rxretrofit.model.MovieResponse;
import com.harry.rv.rxretrofit.retrofit.MovieRequest;
import com.harry.util.AppUtils;
import com.harry.view.FlowLayout;
import com.harry.view.RefreshRecyclerView;
import com.harry.view.RoundButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/9/19.
 */
public class TopActivity extends Activity implements MovieController.LoadListener,
    RefreshRecyclerView.OnRefreshListener {

    @BindView(R.id.lv)
    RefreshRecyclerView mLv;

    MovieController mController;

    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        ButterKnife.bind(this);
        mLv.setOnRefreshListener(this);
        mLv.setRefreshDirection(SwipeRefreshLayoutDirection.BOTH);
        mLv.setPageSize(5);
        mAdapter = new ListAdapter(this);
        mLv.setAdapter(mAdapter);
        mController = new MovieController(this,this);
        load();
    }

    private void load() {
        MovieRequest request = new MovieRequest();
        request.start = (mLv.getCurrentPage() - 1) * 5;//初始值start=0
        mController.load(request);
    }

    @Override
    public void onSuccess(List<MovieResponse> out) {
        mLv.onLoadFinish(out);
    }

    @Override
    public void onError(Throwable error) {
        Log.e("TopActivity", error.getMessage());
    }

    @Override
    public void onComplete() {
        Log.e("TopActivity", "onCompleted");
    }

    @Override
    public void onRefresh() {
        load();
    }

    @Override
    public void onLoadMore() {
        load();
    }

    private class ListAdapter extends AfRecyclerAdapter<MovieResponse, MyViewHolder> {

        public ListAdapter(Context context) {
            super(context);
        }

        @Override
        public int getLayout() {
            return R.layout.list_item_movie;
        }

        @Override
        public MyViewHolder onBindViewHolder(View view) {
            return new MyViewHolder(view);
        }

        @Override
        public void onUpdateView(MyViewHolder holder, MovieResponse data, int position) {
            holder.fl.removeAllViews();
            holder.iv.setImageURI(Uri.parse(data.getImages().getLarge()));
            holder.title.setText(data.getTitle());
            for (String genre : data.getGenres()) {
                RoundButton tag = null;
                if (tag == null) {
                    tag = (RoundButton) View.inflate(TopActivity.this, R.layout.layout_tag, null);
                }
                else {
                    tag = (RoundButton) holder.fl.getTag();
                }
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(TopActivity.this,47),
                    AppUtils.dp2px(TopActivity.this,23));
                tag.setLayoutParams(params);
                holder.fl.setTag(tag);
                tag.setText(genre);
                holder.fl.addView(tag);
            }
        }
    }

    private class MyViewHolder extends AfViewHolder {

        SimpleDraweeView iv;
        TextView title;
        FlowLayout fl;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (SimpleDraweeView) itemView.findViewById(R.id.iv);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            fl = (FlowLayout) itemView.findViewById(R.id.fl);
        }
    }
}
