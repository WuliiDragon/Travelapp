package wlxy.com.travelapp.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

public class CarouselAdapter extends PagerAdapter {
    private List<ImageView> data;
    Context context;

    public CarouselAdapter(List<ImageView> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        //返回一个无穷大的值，
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {

        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //注意，这里什么也不做!!!

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView image = data.get(position % data.size());
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = image.getParent();
        if (vp != null) {
            ViewGroup vg = (ViewGroup) vp;
            vg.removeView(image);
        }
        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("123", "click");
                //Toast.makeText(context, "点击了图片", 0).show();
            }
        });
        container.addView(data.get(position % data.size()));
        return data.get(position % data.size());
    }


}