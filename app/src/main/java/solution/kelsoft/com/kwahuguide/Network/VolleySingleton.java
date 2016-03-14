package solution.kelsoft.com.kwahuguide.Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Rukuvwe on 3/12/2016.
 */
public class VolleySingleton {
    //Assign Volley to Instance  of null
    private static VolleySingleton sInstance = null;

    //Initializing RequestQueue and Imageloader
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    //Constructor handing request and capturing image using Volley
    private VolleySingleton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    //Method for getting an Instance of Volley with if statement
    public static VolleySingleton getInstance() {
        if (sInstance == null) {
            sInstance = new VolleySingleton();
        }

        return sInstance;
    }

    //Method for returning Request
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    //Method to return Imageloader
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
