package wlxy.com.travelapp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.fragment.HomeFragment;
import wlxy.com.travelapp.fragment.MineFragment;


/**
 * Created by WLW on 2017/11/17.
 * @author dragon
 * 主活动
 */

public class HomeActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        fragments = new ArrayList<>();

        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.navigation_bar);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.header, "首页"))
                .addItem(new BottomNavigationItem(R.drawable.my, "我的"))
                .setActiveColor(R.color.colorPrimary)
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);


        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new MineFragment());


        //设置默认的碎片
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.fragment_content, fragments.get(0));
        transaction.show(fragments.get(0));
        transaction.commit();
    }



    //点击item时跳转不同的碎片
    @Override
    public void onTabSelected(int position) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        for (Fragment f :manager.getFragments()){
            System.out.print(f.toString());
        }

        if (position==0){
            if (!manager.getFragments().contains(fragments.get(0))){
                ft.add(R.id.fragment_content,fragments.get(0));
            }

            ft.hide(fragments.get(1));
            ft.show(fragments.get(0));
            ft.commit();
        }
        if (position==1){
            if (!manager.getFragments().contains(fragments.get(1))){
                ft.add(R.id.fragment_content,fragments.get(1));
            }
            ft.hide(fragments.get(0));
            ft.show(fragments.get(1));
            ft.commit();
        }




    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
