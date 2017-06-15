
/*
 * Copyright (C) 20015 MaiNaEr All rights reserved
 */
package com.harry.slide;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.harry.R;
import com.harry.slide.transformer.MTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类/接口描述
 *
 * @author Harry
 * @date 2016/10/28.
 */
public class ViewPagerActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.vp)
    SuperViewPager mVp;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.start)
    Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ButterKnife.setDebug(true);
        ButterKnife.bind(this);
        PagerViewAdapter adapter=new PagerViewAdapter();
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(5);
        mVp.setPageTransformer(true,new MTransformer());
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String num=et.getText().toString();
        mVp.setCurrentItem(Integer.parseInt(num));
    }
    
    private class PagerViewAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 1000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view= View.inflate(ViewPagerActivity.this,R.layout.list_item_common,null);
            SimpleDraweeView iv=(SimpleDraweeView) view.findViewById(R.id.iv);
            iv.setImageURI(Uri.parse("http://img-test.mainaer.com/uploads/product/2017-04-01/58df095ac8725.jpg"));
            TextView tv=(TextView) view.findViewById(R.id.tv);
            tv.setText("第"+position+"题");
            
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
    
}