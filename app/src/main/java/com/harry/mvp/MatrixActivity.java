package com.harry.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.harry.R;
import com.harry.adapter.AfRecyclerAdapter;
import com.harry.adapter.AfViewHolder;
import com.harry.mvp.model.ColorData;
import com.harry.util.AppUtils;
import com.harry.view.ColorView;
import com.harry.view.RefreshRecyclerView;
import com.harry.view.RoundButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2017/5/11.
 */

public class MatrixActivity extends Activity {
    @BindView(R.id.myColorView)
    ColorView colorView;
    @BindView(R.id.lv)
    RefreshRecyclerView lv;
    
    List<ColorData> colors = new ArrayList<>();
    
    ListAdapter mAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        ButterKnife.bind(this);
        initColorData();
        lv.setRefreshEnable(false);
        lv.setLinearLayoutManager(LinearLayout.HORIZONTAL);
        mAdapter = new ListAdapter(this);
        mAdapter.setDataList(colors);
        lv.setAdapter(mAdapter);
    }
    
    private void initColorData() {
        //宝丽来彩色[Polaroid Color]
        ColorData cd = new ColorData();
        cd.title = "宝丽来彩色";
        cd.array
            = new float[]{1.438f, -0.062f, -0.062f, 0, 0, -0.122f, 1.378f, -0.122f, 0, 0, -0.016f, -0.016f, 1.483f, 
            0, 0,
            -0.03f, 0.05f, -0.02f, 1, 0};
        colors.add(cd);
        //怀旧效果
        cd = new ColorData();
        cd.title = "怀旧效果";
        cd.array
            = new float[]{0.393f, 0.769f, 0.189f, 0, 0, 0.349f, 0.686f, 0.168f, 0, 0, 0.272f, 0.534f, 0.131f, 0, 0,
            0, 0, 0, 1, 0};
        colors.add(cd);
        //泛红
        cd = new ColorData();
        cd.title = "泛红";
        cd.array = new float[]{2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
        colors.add(cd);
        //泛绿（荧光绿）
        cd = new ColorData();
        cd.title = "泛绿（荧光绿）";
        cd.array = new float[]{1, 0, 0, 0, 0, 0, 1.4f, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
        colors.add(cd);
        //泛蓝（宝石蓝）
        cd = new ColorData();
        cd.title = "泛蓝（宝石蓝）";
        cd.array = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1.6f, 0, 0, 0, 0, 0, 1, 0};
        colors.add(cd);
        //泛黄
        cd = new ColorData();
        cd.title = "泛黄";
        cd.array = new float[]{1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
        colors.add(cd);
    }
    
    private class PagerViewAdapter extends PagerAdapter {
        
        @Override
        public int getCount() {
            return colors.size();
        }
        
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            RoundButton view = (RoundButton) View.inflate(MatrixActivity.this, R.layout.layout_tag, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(MatrixActivity.this,50),
                ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            view.setText(colors.get(position).title);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorView.setColorArray(colors.get(position).array);
                }
            });
            container.addView(view);
            return view;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    
    private class ListAdapter extends AfRecyclerAdapter<ColorData, MyViewHolder> {
        
        public ListAdapter(Context context) {
            super(context);
        }
        
        @Override
        public int getLayout() {
            return R.layout.layout_tag;
        }
        
        @Override
        public MyViewHolder onBindViewHolder(View view) {
            return new MyViewHolder(view);
        }
        
        @Override
        public void onUpdateView(MyViewHolder holder, final ColorData data, int position) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(MatrixActivity.this,150),
                ViewGroup.LayoutParams.MATCH_PARENT);
            holder.title.setLayoutParams(params);
            holder.title.setText(data.title);
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorView.setColorArray(data.array);
                }
            });
        }
    }
    
    private class MyViewHolder extends AfViewHolder {
        RoundButton title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title =(RoundButton) itemView;
        }
    }
    
    public static void go(Context context) {
        Intent intent = new Intent(context, MatrixActivity.class);
        context.startActivity(intent);
    }
}
