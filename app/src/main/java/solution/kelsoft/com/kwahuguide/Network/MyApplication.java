package solution.kelsoft.com.kwahuguide.Network;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rukuvwe on 3/12/2016.
 */
public class MyApplication extends Application {
    //Creating an instance of android application class
    private static MyApplication sInstance;

    //Returning instance
    public static MyApplication getInstance() {
        return sInstance;
    }

    //Getting application context
    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    //onCreate method
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
