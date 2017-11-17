package wlxy.com.travelapp.control;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guardian on 2017/10/15.
 */

public class ActivityController {
    public static List<Activity> activities = new ArrayList<Activity>();

    public  static void  addActivity  (Activity activity){
        activities.add(activity);
    }

    public  static void  removeActivity  (Activity activity){
        activities.remove(activity);
    }

    public  static void  finshAll  (){
        for (Activity activity : activities){
            if (!activity.isFinishing()) activity.finish();

        }
    }
}
