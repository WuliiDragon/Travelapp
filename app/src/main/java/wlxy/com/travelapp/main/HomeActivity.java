package wlxy.com.travelapp.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.fragment.HomeFragment;
import wlxy.com.travelapp.fragment.MineFragment;


/**
 * Created by WLW on 2017/11/17.
 *
 * @author dragon
 *         主活动
 */

public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    private List<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = "HomeActivity";

        setContentView(R.layout.main_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }


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
        for (Fragment f : manager.getFragments()) {
            System.out.print(f.toString());
        }

        if (position == 0) {
            if (!manager.getFragments().contains(fragments.get(0))) {
                ft.add(R.id.fragment_content, fragments.get(0));
            }

            ft.hide(fragments.get(1));
            ft.show(fragments.get(0));
            ft.commit();
        }
        if (position == 1) {
            if (!manager.getFragments().contains(fragments.get(1))) {
                ft.add(R.id.fragment_content, fragments.get(1));
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
