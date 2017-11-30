package wlxy.com.travelapp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.main.LoginActivity;
import wlxy.com.travelapp.main.MineOrderActivity;
import wlxy.com.travelapp.main.UserCenterActivity;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.utils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by WLW on 2017/11/17.
 * 我的中心碎片
 *
 * @author dragon
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private NetworkImageView userImage;
    private TextView userName;
    private Button loginOut;
    private SharedPreferences sharedPreferences;
    private LinearLayout mineOrderLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.TAG = "MineFragment";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_layout, container, false);
        userImage = (NetworkImageView) view.findViewById(R.id.user_image);
        userName = (TextView) view.findViewById(R.id.user_name);
        mineOrderLayout = (LinearLayout) view.findViewById(R.id.mine_order);
        mineOrderLayout.setOnClickListener(this);
        userImage.setOnClickListener(this);
        getInfoFromLocal();
        return view;
    }

    private String userUid;
    private String userImageRes;
    private String userNameRes;

    private void getInfoFromLocal() {
        sharedPreferences = getActivity().getSharedPreferences("info", MODE_PRIVATE);
        userImageRes = sharedPreferences.getString("headImgPath", "");
        userNameRes = sharedPreferences.getString("account", "");
        userUid = sharedPreferences.getString("uid", "");
        userName.setText(userUid.equals("") ? "未登录，点击登录" : userNameRes);
        userImage.setImageUrl(utils.BASE + "/" + userImageRes, AppController.getInstance().getImageLoader());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image: {
                if (userUid.equals("")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), UserCenterActivity.class);
                    startActivity(intent);
                }
            }
            break;


            case R.id.mine_order: {

                Intent intent = new Intent(getActivity(), MineOrderActivity.class);
                startActivity(intent);


            }
            break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        userUid = sharedPreferences.getString("uid", "");
        userName.setText(userUid.equals("") ? "未登录，点击登录" : userNameRes);
        userImage.setImageUrl(utils.BASE + "/" + userImageRes, AppController.getInstance().getImageLoader());
    }


}

