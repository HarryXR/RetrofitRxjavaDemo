/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.slide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.harry.R;
import com.harry.util.AppUtils;
import com.harry.view.RoundButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/28.
 */
public class ViewPagerActivity extends Activity {
    @BindView(R.id.vp)
    BounceViewPager mVp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.bind(this);
        PagerViewAdapter adapter=new PagerViewAdapter();
        mVp.setAdapter(adapter);
    }

    private class PagerViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            RoundButton view=(RoundButton) View.inflate(ViewPagerActivity.this,R.layout.layout_tag,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(AppUtils.dp2px(ViewPagerActivity.this,47),
                AppUtils.dp2px(ViewPagerActivity.this,23));
            view.setLayoutParams(params);
            view.setText("犯罪"+position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
