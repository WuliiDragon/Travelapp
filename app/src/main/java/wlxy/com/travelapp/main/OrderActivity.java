package wlxy.com.travelapp.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import wlxy.com.travelapp.R;
import wlxy.com.travelapp.utils.AppController;
import wlxy.com.travelapp.utils.HttpUtils;
import wlxy.com.travelapp.utils.utils;

/**
 * @author dragon
 * @date 2017/11/25
 */

public class OrderActivity extends BaseActivity {

    private TextView orderOid;
    private TextView orderStatus;
    private TextView orderCreateTime;
    private TextView orderUid;
    private TextView orderBid;
    private TextView orderTotalprice;
    private Button orderPay;
    private Button orderBack;
    private String OrderCreateTime;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        this.TAG = "OrderActivity";
        progressDialog = new ProgressDialog(OrderActivity.this);
        progressDialog.setMessage("加载中");
        progressDialog.setCanceledOnTouchOutside(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }


        orderOid = (TextView) findViewById(R.id.order_oid);
        orderStatus = (TextView) findViewById(R.id.order_status);
        orderCreateTime = (TextView) findViewById(R.id.order_createTime);
        orderUid = (TextView) findViewById(R.id.order_uid);
        orderBid = (TextView) findViewById(R.id.order_bid);
        orderTotalprice = (TextView) findViewById(R.id.order_totalprice);
        orderPay = (Button) findViewById(R.id.order_pay);
        orderBack = (Button) findViewById(R.id.order_back);

        orderOid.setText(getIntent().getStringExtra("oid"));
        orderStatus.setText(getIntent().getStringExtra("status"));


        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String createTime = getIntent().getStringExtra("createTime");
        orderCreateTime.setText(formatter.format(new Date(Long.parseLong(createTime))));

        orderUid.setText(getIntent().getStringExtra("uid"));
        orderBid.setText(getIntent().getStringExtra("bid"));
        orderTotalprice.setText(getIntent().getStringExtra("totalprice"));

        orderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                SharedPreferences sharedPreferences = getSharedPreferences("info", MODE_PRIVATE);
                StringBuffer sb = new StringBuffer();
                sb.append("?oid=" + getIntent().getStringExtra("oid"));
                sb.append("&uid=" + sharedPreferences.getString("uid", ""));
                sb.append("&phone=" + sharedPreferences.getString("phone", ""));
                sb.append("&token=" + sharedPreferences.getString("token", ""));
                HttpUtils request = new HttpUtils(utils.BASE + "/order/payOrder.action" + sb.toString(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");
                            if (status == utils.RIGHTSTATUS) {
                                Intent intent = new Intent(OrderActivity.this, OrderResActivity.class);
                                startActivity(intent);
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.hide();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                    }
                });
                AppController.getInstance().addToRequestQueue(request);
            }
        });

        orderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
