
/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.mvp;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.harry.R;
import com.harry.adapter.AfRecyclerAdapter;
import com.harry.adapter.AfViewHolder;
import com.harry.databinding.ListItemMovieBinding;
import com.harry.mvp.model.TopController;
import com.harry.mvp.presenter.TopPresenterImpl;
import com.harry.mvp.view.ITopView;
import com.harry.refresh.SwipeRefreshLayoutDirection;
import com.harry.rv.rxretrofit.model.MovieResponse;
import com.harry.rv.rxretrofit.retrofit.MovieRequest;
import com.harry.util.AppUtils;
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
public class TopViewActivity extends Activity implements RefreshRecyclerView.OnRefreshListener,
    ITopView<List<MovieResponse>> {
    
    @BindView(R.id.lv)
    RefreshRecyclerView mLv;
    
    TopController mController;
    
    private ListAdapter mAdapter;
    TopPresenterImpl presenter;
    
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
        mController = new TopController(this);
        presenter = new TopPresenterImpl(this, mController);
        load();
    }
    
    private void load() {
        MovieRequest request = new MovieRequest();
        request.start = (mLv.getCurrentPage() - 1) * 5;//初始值start=0
        presenter.load(request);
    }
    
    @Override
    public void onRefresh() {
        load();
    }
    
    @Override
    public void onLoadMore() {
        load();
    }
    
    @Override
    public void onSuccess(List<MovieResponse> out) {
        mLv.onLoadFinish(out);
    }
    
    @Override
    public void onError() {
        
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
            holder.bind(data);
            holder.binding.fl.removeAllViews();
            holder.binding.iv.setImageURI(Uri.parse(data.getImages().getLarge()));
//            holder.title.setText(data.title);
            if(data.getGenres() == null){
                return;
            }
            for (String genre : data.getGenres()) {
                RoundButton tag = null;
                if (tag == null) {
                    tag = (RoundButton) View.inflate(TopViewActivity.this, R.layout.layout_tag, null);
                }
                else {
                    tag = (RoundButton) holder.binding.fl.getTag();
                }
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(TopViewActivity.this, 47),
                    AppUtils.dp2px(TopViewActivity.this, 23));
                tag.setLayoutParams(params);
                holder.binding.fl.setTag(tag);
                tag.setText(genre);
                holder.binding.fl.addView(tag);
            }
        }
    }
    
    private class MyViewHolder extends AfViewHolder {
        
//        SimpleDraweeView iv;
//        TextView title;
//        FlowLayout fl;
        ListItemMovieBinding binding;
        public MyViewHolder(View itemView) {
            super(itemView);
            binding=DataBindingUtil.bind(itemView);
//            iv = (SimpleDraweeView) itemView.findViewById(R.id.iv);
//            title = (TextView) itemView.findViewById(R.id.tv_title);
//            fl = (FlowLayout) itemView.findViewById(R.id.fl);
        }
        public void bind(MovieResponse data){
            binding.setMovie(data);
        }
    }
}