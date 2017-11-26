package wlxy.com.travelapp.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_layout);
        this.TAG = "OrderActivity";


        orderOid = (TextView) findViewById(R.id.order_oid);
        orderStatus = (TextView) findViewById(R.id.order_status);
        orderCreateTime = (TextView) findViewById(R.id.order_createTime);
        orderUid = (TextView) findViewById(R.id.order_uid);
        orderBid = (TextView) findViewById(R.id.order_bid);
        orderTotalprice = (TextView) findViewById(R.id.order_totalprice);
        orderPay = (Button) findViewById(R.id.order_pay);


        orderOid.setText(getIntent().getStringExtra("oid"));
        orderStatus.setText(getIntent().getStringExtra("status"));
        orderCreateTime.setText(getIntent().getStringExtra("createTime"));
        orderUid.setText(getIntent().getStringExtra("uid"));
        orderBid.setText(getIntent().getStringExtra("bid"));
        orderTotalprice.setText(getIntent().getStringExtra("totalprice"));

        orderPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            String status = response.getString("status");
                            if (status.equals("200")) {
                                JSONObject data = response.getJSONObject("data");
                            } else {
                                String msg = response.getString("msg");
                                Toast.makeText(OrderActivity.this, msg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OrderActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
                AppController.getInstance().addToRequestQueue(request);
            }
        });
    }
}
