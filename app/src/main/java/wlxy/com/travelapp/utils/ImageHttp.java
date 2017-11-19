package wlxy.com.travelapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by DT on 2017/11/19.
 */

public class ImageHttp {
    public Bitmap getImageBitMap(String imgPath) {
        try {
            URL url = new URL(utils.BASE + imgPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Bitmap bf = BitmapFactory.decodeStream(in);
            return bf;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
