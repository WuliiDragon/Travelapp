package wlxy.com.travelapp.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.toolbox.NetworkImageView;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.main.LoginActivity;
import wlxy.com.travelapp.main.UserCenterActivity;
import wlxy.com.travelapp.model.MerChantModel;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.utils;

import static android.content.Context.MODE_PRIVATE;
import static wlxy.com.travelapp.utils.utils.BASE;

/**
 * Created by WLW on 2017/11/17.
 * 我的中心碎片
 * @author dragon
 *
 */

public class MineFragment extends Fragment implements View.OnClickListener {
    private NetworkImageView userImage;
    private EditText userName;
    private Button loginOut;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_layout, container, false);
        userImage= (NetworkImageView) view.findViewById(R.id.user_image);
        userName= (EditText) view.findViewById(R.id.user_name);
        loginOut= (Button) view.findViewById(R.id.loginout);


        userImage.setOnClickListener(this);
        loginOut.setOnClickListener(this);
        getInfoFromLocal();
        return view;

    }
    private String  userImageRes;
    private String  userNameRes;
    private void getInfoFromLocal(){
        sharedPreferences = getActivity().getSharedPreferences("info",MODE_PRIVATE);
        userImageRes=sharedPreferences.getString("headImgPath","");
        userNameRes=sharedPreferences.getString("account","");
        userImage.setImageUrl(utils.BASE + "/"+userImageRes, AppController.getInstance().getImageLoader());
        Log.v("headImgPath","");
        userName.setText(userNameRes.equals("null") ? "未设置" : userNameRes);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_image: {
                Intent intent=new Intent(getActivity(),UserCenterActivity.class);
                startActivity(intent);
            }

            break;
            case R.id.loginout: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("退出");
                builder.setMessage("真的要退出账号吗？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("info", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("account");
                        editor.remove("headImgPath");
                        editor.remove("uid");

                        editor.commit();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        dialog.dismiss();
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

               }
             break;
        }


    }
}
