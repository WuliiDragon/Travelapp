package wlxy.com.travelapp.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wlxy.com.travelapp.R;

/**
 * Created by WLW on 2017/11/17.
 * 我的中心碎片
 * @author dragon
 *
 */

public class MineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine_layout, container, false);

    }
}
