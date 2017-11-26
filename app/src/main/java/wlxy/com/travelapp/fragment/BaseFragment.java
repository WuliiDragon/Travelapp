package wlxy.com.travelapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by WLW on 2017/11/26.
 */

public class BaseFragment extends Fragment {
    public String TAG = "BaseFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("碎片监听" + TAG, "onAttach");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("碎片监听" + TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("碎片监听" + TAG, "onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("碎片监听" + TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("碎片监听" + TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("碎片监听" + TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("碎片监听" + TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("碎片监听" + TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("碎片监听" + TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("碎片监听" + TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("碎片监听" + TAG, "onDetach");
    }

}
