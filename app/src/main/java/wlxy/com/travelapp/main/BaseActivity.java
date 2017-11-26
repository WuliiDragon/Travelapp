package wlxy.com.travelapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * @author dragon
 * @date 2017/11/26
 * 监听活动的生命周期
 */

public class BaseActivity extends AppCompatActivity {


    public String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("活动监听：" + TAG, "onCreate is executed");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("活动监听：" + TAG, "onStart is executed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("活动监听：" + TAG, "onRestart is executed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("活动监听：" + TAG, "onResume is executed");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("活动监听：" + TAG, "onStop is executed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("活动监听：" + TAG, "onPause is executed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("活动监听：" + TAG, "onDestroy is executed");
    }
}
