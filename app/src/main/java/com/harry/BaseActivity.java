package com.harry;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jakewharton.rxbinding.widget.RxAdapterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.listview)
    ListView lv;

    Class[] cls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cls = getActivities();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1,
            getItems());
        lv.setAdapter(adapter);
        RxAdapterView.itemClicks(lv).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                selectItem(integer);
            }
        });
        setWindowAnimation();
    }

    private void setWindowAnimation() {
//        Slide slide=new Slide();
//        slide.setDuration(500);
//        slide.setSlideEdge(Gravity.START);
//        getWindow().setReenterTransition(slide);
//        getWindow().setExitTransition(slide);
    }
    
    private void selectItem(int position) {
        Class cl = cls[position];
        Intent i = new Intent(BaseActivity.this, cl);
        //noinspection unchecked
        startActivity(i, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    protected String[] getItems() {
        String[] items = new String[cls.length];
        for (int i = 0; i < cls.length; i++) {
            String name = cls[i].getSimpleName();
            items[i] = name.replace("Activity", "");
        }
        return items;
    }

    protected Class[] getActivities() {
        return new Class[]{};
    }
}
