package wlxy.com.travelapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import wlxy.com.travelapp.control.BaseActivity;
import wlxy.com.travelapp.control.Requester;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by guardian on 2017/10/15.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button login;
    private Button regist;
    private SharedPreferences sharedPreferences;
    private EditText Name;
    private EditText Password;
    //http://172.16.120.235:8080/signin/TeacherServlet?method=login&tnumber=?&tpass=?&tuuid=?
    public static final String BASE_URL = utils.BASE + "user/login.action";
    //手机的唯一标识符
    String IMEI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        login = (Button) findViewById(R.id.login);
        Name = (EditText) findViewById(R.id.name);
        Password = (EditText) findViewById(R.id.password);
//        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
//        Name.setText(sharedPreferences.getString("uname", "").equals("null") ? "" : sharedPreferences.getString("uname", ""));
//        Password.setText(sharedPreferences.getString("upass", "").equals("null") ? "" : sharedPreferences.getString("upass", ""));
        // stuPas.setText("2812134");
        login.setOnClickListener(this);
        IMEI = utils.getIMEI(getBaseContext());
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: {
//                if (!checkInput()) {
//                    Toast.makeText(LoginActivity.this, "工号或者密码长度不正确", Toast.LENGTH_SHORT).show();
//
//
//                    return;
//
//                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                String url = BASE_URL + "?uid=" + Name.getText().toString() + "&upass=" + Password.getText().toString();


                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int status = response.getInt("status");
                                    if (status == 200) {
                                        JSONObject data = response.getJSONObject("data");
                                        sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                                        //MODE_PRIVATE表示当指定同样文件名的时候，所写入的内容将会覆盖原文件中的内容
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("msg", true);
                                        editor.putString("uid", data.getString("uid"));
                                        editor.putString("uname", data.getString("uname"));
                                        editor.putString("upass", data.getString("upass"));
                                        //将数据提交
                                        editor.commit();
                                        //在LoginActivity这个活动中开启MainActivity
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        String msg = response.getString("msg");
                                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                        );
                Requester.getInstance(this).addToRequestQueue(jsObjRequest);

            }


        }
    }





    private boolean isExit;

    /**
     * 程序是否退出
     */
    private boolean isFinish;
    private final int CODE_NOT_EXIT = 0x000;
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CODE_NOT_EXIT:
                    // 程序退出后不再做操作
                    if (isFinish) break;
                    isExit = false;
                    break;

                default:
                    break;
            }
        }
    };
//    private boolean checkInput() {
//
//        if (Name.getText().length() == 10) {
//            if (Password.getText().length() <= 18 && Name.getText().length() >= 6) {
//                return true;
//            }
//        }
//        return false;
//    }
}
