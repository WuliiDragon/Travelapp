package wlxy.com.travelapp.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by guardian on 2017/11/22.
 */

public class UserCenterActivity extends AppCompatActivity implements View.OnClickListener{
    private NetworkImageView User_center_image;
    private TextView User_center_name;
    private TextView User_center_phone;
    private TextView User_center_sex;
    private Button User_center_commit;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_center);

        User_center_image= (NetworkImageView) findViewById(R.id.user_center_image);
        User_center_name= (TextView) findViewById(R.id.user_center_name);
        User_center_phone= (TextView) findViewById(R.id.user_center_phone);
        User_center_sex= (TextView) findViewById(R.id.user_center_sex);
        User_center_commit= (Button) findViewById(R.id.user_center_commit);

        getInfoFromLocal();

        User_center_name.setOnClickListener(this);
        User_center_sex.setOnClickListener(this);
        User_center_phone.setOnClickListener(this);





    }
    private String  userImageRes;
    private String  userNameRes;
    private String  userSexRes;
    private String  userPhoneRes;
    private void getInfoFromLocal(){
        sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
        userImageRes=sharedPreferences.getString("headImgPath","");
        userNameRes=sharedPreferences.getString("account","");
        userSexRes=sharedPreferences.getString("sex","");
        userPhoneRes=sharedPreferences.getString("phone","");


        User_center_image.setImageUrl(utils.BASE + "/"+userImageRes, AppController.getInstance().getImageLoader());
        User_center_name.setText(userNameRes.equals("null") ? "未设置" : userNameRes);
        User_center_phone.setText(userPhoneRes.equals("null") ? "未设置" : userPhoneRes);
        User_center_sex.setText(userSexRes.equals("null") ? "未设置" : userSexRes);


    }
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.user_center_sex:{
                final String items[] = new String[]{"女", "男"};
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("性别").setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userSexRes = items[which];
                        Log.v("select", "" + which);
                        User_center_sex.setText(userSexRes);
                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
                break;

            case R.id.user_center_name:{
                LayoutInflater factory = LayoutInflater.from(this);//提示框
                final View views = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
                final EditText edit = (EditText) views.findViewById(R.id.editText1);//获得输入框对象
                edit.setText(userNameRes.equals("null") ? "" : userNameRes);
                new AlertDialog.Builder(this)
                        .setTitle("输入姓名")//提示框标题
                        .setView(views)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        User_center_name.setText(edit.getText().toString());
                                        userNameRes = edit.getText().toString();
                                    }
                                }).setNegativeButton("取消", null).create().show();

            }



                break;
            case R.id.user_center_phone:{
                LayoutInflater factory = LayoutInflater.from(this);//提示框
                final View views = factory.inflate(R.layout.editbox_layout, null);//这里必须是final的
                final EditText edit = (EditText) views.findViewById(R.id.editText1);//获得输入框对象
                edit.setText(userPhoneRes.equals("null") ? "" : userPhoneRes);
                new AlertDialog.Builder(this)
                        .setTitle("请输入电话")//提示框标题
                        .setView(views)
                        .setPositiveButton("确定",//提示框的两个按钮
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        User_center_phone.setText(edit.getText().toString());
                                        userPhoneRes = edit.getText().toString();
                                    }
                                }).setNegativeButton("取消", null).create().show();

            }



                break;
            case R.id.user_center_image:{

            }



                break;
            case R.id.user_center_commit:{

            }
                break;


        }

    }
}
