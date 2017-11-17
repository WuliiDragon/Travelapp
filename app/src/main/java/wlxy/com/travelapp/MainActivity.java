package wlxy.com.travelapp;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.AndroidCharacter;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import wlxy.com.travelapp.control.BaseActivity;
import wlxy.com.travelapp.fragment.HeaderFragment;
import wlxy.com.travelapp.fragment.MycenterFragment;
import wlxy.com.travelapp.fragment.MycollectFragment;
import wlxy.com.travelapp.fragment.MypurchaseFragment;
import wlxy.com.travelapp.fragment.SouvenirFragment;
import wlxy.com.travelapp.model.Model;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        HeaderFragment headerFragment = new HeaderFragment();
        FragmentManager fragmentManagerheader= getFragmentManager();
        FragmentTransaction transaction=fragmentManagerheader.beginTransaction();
        transaction.replace(R.id.frameLayoutId, headerFragment);
        transaction.commit();
        ImageView headerImage= (ImageView) findViewById(R.id.header_Image);
        TextView headerText= (TextView) findViewById(R.id.header_text);
        headerImage.setImageResource(R.drawable.header);
        headerText.setTextColor(getResources().getColor(R.color.theBule));

        LinearLayout headerll = (LinearLayout)findViewById(R.id.headerTouch);
        LinearLayout jinainll = (LinearLayout)findViewById(R.id.jinianThouch);
        LinearLayout collectll = (LinearLayout)findViewById(R.id.collectTouch);
        LinearLayout tickesll = (LinearLayout)findViewById(R.id.tickesThouch);
        LinearLayout mycenterll = (LinearLayout)findViewById(R.id.mycenterThouch);
        headerll.setOnClickListener(this);
        jinainll.setOnClickListener(this);
        collectll.setOnClickListener(this);
        mycenterll.setOnClickListener(this);
        tickesll.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ImageView headerImage = (ImageView)findViewById(R.id.header_Image);
        ImageView jinianImage = (ImageView)findViewById(R.id.jinian_Image);
        ImageView collectImage = (ImageView)findViewById(R.id.collect_Image);
        ImageView tickesImage = (ImageView)findViewById(R.id.tickes_Image);
        ImageView mycenterImage = (ImageView)findViewById(R.id.mycenter_Image);


        TextView headerText = (TextView)findViewById(R.id.header_text);
        TextView jinianText = (TextView)findViewById(R.id.jinian_text);
        TextView collectText = (TextView)findViewById(R.id.collect_text);
        TextView mycenterText = (TextView)findViewById(R.id.mycenter_text);
        TextView tickesText = (TextView)findViewById(R.id.tickes_text);

        switch (v.getId()){
            case  R.id.headerTouch:
                headerImage.setImageResource(R.drawable.header);
                jinianImage.setImageResource(R.drawable.unjinian);
                collectImage.setImageResource(R.drawable.uncollect);
                tickesImage.setImageResource(R.drawable.untickes);
                mycenterImage.setImageResource(R.drawable.unmy);

                headerText.setTextColor(getResources().getColor(R.color.theBule));
                jinianText.setTextColor(getResources().getColor(R.color.theGray));
                collectText.setTextColor(getResources().getColor(R.color.theGray));
                mycenterText.setTextColor(getResources().getColor(R.color.theGray));
                tickesText.setTextColor(getResources().getColor(R.color.theGray));


                HeaderFragment headerFragment = new HeaderFragment();
                FragmentManager fragmentManager= getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayoutId, headerFragment);
                transaction.commit();

            case R.id.jinianThouch:
                headerImage.setImageResource(R.drawable.unheader);
                jinianImage.setImageResource(R.drawable.jinian);
                collectImage.setImageResource(R.drawable.uncollect);
                tickesImage.setImageResource(R.drawable.untickes);
                mycenterImage.setImageResource(R.drawable.unmy);

                headerText.setTextColor(getResources().getColor(R.color.theGray));
                jinianText.setTextColor(getResources().getColor(R.color.theBule));
                collectText.setTextColor(getResources().getColor(R.color.theGray));
                mycenterText.setTextColor(getResources().getColor(R.color.theGray));
                tickesText.setTextColor(getResources().getColor(R.color.theGray));

                SouvenirFragment souvenirFragment = new SouvenirFragment();
                FragmentManager fragmentManagerjinian= getFragmentManager();
                FragmentTransaction transactionjinian=fragmentManagerjinian.beginTransaction();
                transactionjinian.replace(R.id.frameLayoutId, souvenirFragment);
                transactionjinian.commit();

            case R.id.collectTouch:
                headerImage.setImageResource(R.drawable.unheader);
                jinianImage.setImageResource(R.drawable.unjinian);
                collectImage.setImageResource(R.drawable.collect);
                tickesImage.setImageResource(R.drawable.untickes);
                mycenterImage.setImageResource(R.drawable.unmy);

                headerText.setTextColor(getResources().getColor(R.color.theGray));
                jinianText.setTextColor(getResources().getColor(R.color.theGray));
                collectText.setTextColor(getResources().getColor(R.color.theBule));
                mycenterText.setTextColor(getResources().getColor(R.color.theGray));
                tickesText.setTextColor(getResources().getColor(R.color.theGray));

                MycollectFragment mycollectFragment = new MycollectFragment();
                FragmentManager fragmentManagercollect= getFragmentManager();
                FragmentTransaction transactioncollect=fragmentManagercollect.beginTransaction();
                transactioncollect.replace(R.id.frameLayoutId, mycollectFragment);
                transactioncollect.commit();

            case R.id.tickesThouch:
                headerImage.setImageResource(R.drawable.unheader);
                jinianImage.setImageResource(R.drawable.unjinian);
                collectImage.setImageResource(R.drawable.uncollect);
                tickesImage.setImageResource(R.drawable.tickes);
                mycenterImage.setImageResource(R.drawable.unmy);

                headerText.setTextColor(getResources().getColor(R.color.theGray));
                jinianText.setTextColor(getResources().getColor(R.color.theGray));
                collectText.setTextColor(getResources().getColor(R.color.theGray));
                mycenterText.setTextColor(getResources().getColor(R.color.theGray));
                tickesText.setTextColor(getResources().getColor(R.color.theBule));

                MypurchaseFragment mypurchaseFragment = new MypurchaseFragment();
                FragmentManager fragmentManagertickes= getFragmentManager();
                FragmentTransaction transactiontickes=fragmentManagertickes.beginTransaction();
                transactiontickes.replace(R.id.frameLayoutId, mypurchaseFragment);
                transactiontickes.commit();

            case R.id.mycenterThouch:
                headerImage.setImageResource(R.drawable.unheader);
                jinianImage.setImageResource(R.drawable.unjinian);
                collectImage.setImageResource(R.drawable.uncollect);
                tickesImage.setImageResource(R.drawable.untickes);
                mycenterImage.setImageResource(R.drawable.my);

                headerText.setTextColor(getResources().getColor(R.color.theGray));
                jinianText.setTextColor(getResources().getColor(R.color.theGray));
                collectText.setTextColor(getResources().getColor(R.color.theGray));
                mycenterText.setTextColor(getResources().getColor(R.color.theBule));
                tickesText.setTextColor(getResources().getColor(R.color.theGray));

                MycenterFragment mycenterFragment = new MycenterFragment();
                FragmentManager fragmentManagermycenter= getFragmentManager();
                FragmentTransaction transactionmycenter=fragmentManagermycenter.beginTransaction();
                transactionmycenter.replace(R.id.frameLayoutId, mycenterFragment);
                transactionmycenter.commit();
                break;
                default:break;

        }

        }
    }





