package wlxy.com.travelapp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * Created by guardian on 2017/11/20.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phoneNumberInput;
    private EditText verificationCodeInput;
    private EditText passInput;
    private EditText verificationPassInput;
    private Button sendVerificationCodeBtn;
    private Button registerBtn;
    private String returnCode;
    private ImageView registerback;

    private String REGISTERURL = utils.BASE + "/user/regist.action";
    private String CODEURL = utils.BASE + "/SendMessage/registCode.action";


    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            sendVerificationCodeBtn.setText(millisUntilFinished / 1000 + "秒后重发");
        }

        @Override
        public void onFinish() {
            sendVerificationCodeBtn.setClickable(true);
            sendVerificationCodeBtn.setText("发送验证码");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_layout);

        phoneNumberInput = (EditText) findViewById(R.id.register_phone_number);
        verificationCodeInput = (EditText) findViewById(R.id.register_code);
        passInput = (EditText) findViewById(R.id.register_onepass);
        verificationPassInput = (EditText) findViewById(R.id.register_towpass);
        sendVerificationCodeBtn = (Button) findViewById(R.id.register_send);
        registerBtn = (Button) findViewById(R.id.register);
        registerback = (ImageView) findViewById(R.id.register_back);

        sendVerificationCodeBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        registerback.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_send: {
                String Phonenumber = phoneNumberInput.getText().toString();
                if (Phonenumber.isEmpty()) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    String codeurl = CODEURL + "?to=" + phoneNumberInput.getText().toString();

                    HttpUtils httpUtils = new HttpUtils
                            (Request.Method.GET, codeurl, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        int status = response.getInt("status");
                                        if (status == 200) {
                                            returnCode = response.getString("data");
                                            sendVerificationCodeBtn.setClickable(false);
                                            timer.start();
                                        } else {
                                            String msg = response.getString("msg");
                                            Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(RegisterActivity.this, "错误", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                            );

                    AppController.getInstance().addToRequestQueue(httpUtils);
                }
            }
            break;
            case R.id.register: {
                String Code = verificationCodeInput.getText().toString();
                String Onepass = passInput.getText().toString();
                String Twopass = verificationPassInput.getText().toString();
                if (Code.length() < 6) {
                    Toast.makeText(this, "验证码长度不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Code.equals(returnCode)) {
                    Toast.makeText(this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Onepass.length() < 6 || Twopass.length() < 6) {
                    Toast.makeText(this, "请输入正确密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Onepass.equals(Twopass)) {
                    Toast.makeText(this, "两次密码输入不相同", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Map<String, String> par = new HashMap<String, String>(2);
                    par.put("phone", phoneNumberInput.getText().toString());
                    par.put("pass", passInput.getText().toString());


                    HttpUtils httpUtils = new HttpUtils(Request.Method.POST, REGISTERURL, par, new Response.Listener<JSONObject>() {
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
                                    editor.putString("phone", phoneNumberInput.getText().toString());
                                    editor.putString("pass", passInput.getText().toString());

                                    editor.commit();
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    startActivity(intent);


                                } else {
                                    String msg = response.getString("msg");
                                    Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });

                    AppController.getInstance().addToRequestQueue(httpUtils);
                }
            }
            break;
            case R.id.register_back: {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
            break;
        }

    }

}
