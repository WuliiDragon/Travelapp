package wlxy.com.travelapp.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by guardian on 2017/11/19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button loginBtn;
    private SharedPreferences sharedPreferences;
    private EditText phoneNumber;
    private EditText passWordInput;
    private Button turnToRegister;
    private ProgressDialog progressDialog;

    public static final String BASE_URL = utils.BASE + "/user/login.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.TAG = "LoginActivity";
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_layout);
        loginBtn = (Button) findViewById(R.id.login_btn);
        turnToRegister = (Button) findViewById(R.id.turn_to_register);

        phoneNumber = (EditText) findViewById(R.id.Number);
        passWordInput = (EditText) findViewById(R.id.Password);

        loginBtn.setOnClickListener(this);
        turnToRegister.setOnClickListener(this);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("登录中");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn: {
                if (phoneNumber.getText().length() <= 1 && passWordInput.getText().length() <= 1) {
                    Toast.makeText(LoginActivity.this, "账号或者密码长度不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.show();

                String url = BASE_URL + "?phone=" + phoneNumber.getText().toString() + "&pass=" + passWordInput.getText().toString();
                HttpUtils httpUtils = new HttpUtils
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int status = response.getInt("status");
                                    if (status == 200) {

                                        JSONObject data = response.getJSONObject("data");
                                        SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString("uid", data.getString("uid"));
                                        editor.putString("phone", data.getString("phone"));
                                        editor.putString("account", data.getString("account"));
                                        editor.putString("signinTime", data.getString("signinTime"));
                                        editor.putString("pass", data.getString("pass"));
                                        editor.putString("sex", data.getString("sex"));
                                        editor.putString("token", data.getString("token"));
                                        editor.putString("headImgPath", data.getString("headImgPath"));

                                        editor.commit();
                                        progressDialog.dismiss();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        finish();
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
                        });
                AppController.getInstance().addToRequestQueue(httpUtils);
            }
            break;
            case R.id.turn_to_register: {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
