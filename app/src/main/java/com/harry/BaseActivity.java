package com.harry;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1,
            getItems());
        lv.setAdapter(adapter);
        setWindowAnimation();
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
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
