package solution.kelsoft.com.kwahuguide;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Rukuvwe on 3/14/2016.
 */
public class CustomMessage {
    public static void t(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void l(String tag, String text) {
        Log.d(tag, text);
    }
}
