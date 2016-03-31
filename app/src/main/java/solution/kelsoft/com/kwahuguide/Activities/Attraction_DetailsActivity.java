package solution.kelsoft.com.kwahuguide.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.R;

public class Attraction_DetailsActivity extends AppCompatActivity {
    private TextView txtDetailLocation;
    private TextView txtDetails;
    private ImageView imageView;
    private ImageLoader loader;
    private VolleySingleton singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDetailLocation = (TextView) findViewById(R.id.txtLocationInvisible);
        txtDetails = (TextView) findViewById(R.id.txtDescInvisible);
        imageView = (ImageView) findViewById(R.id.attrImg);

        volleyPass();
        display();

    }


    public void volleyPass() {
        singleton = VolleySingleton.getInstance();
        loader = singleton.getImageLoader();
    }

    public void display() {
        String location = getIntent().getExtras().getString("kawhu_location");
        txtDetailLocation.setText(location.toString());

        String details = getIntent().getExtras().getString("kawhu_details");
        txtDetails.setText(details.toString());

        String lat = getIntent().getExtras().getString("kawhu_lat");

        String lng = getIntent().getExtras().getString("kawhu_lng");

        final String detailImg = getIntent().getExtras().getString("kawhu_icon");
        loader.get(detailImg, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (detailImg != null) {
                    imageView.setImageBitmap(response.getBitmap());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                CustomMessage.t(Attraction_DetailsActivity.this, "OOps wrong code bro" + error);
            }
        });
    }


}
