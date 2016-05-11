package solution.kelsoft.com.kwahuguide.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import solution.kelsoft.com.kwahuguide.R;

public class Hotel_DetailsActivity extends AppCompatActivity {

    private ImageView ImageView;
    private TextView tAddress;
    private TextView tLocation;
    private TextView tRegion;
    private TextView tFeatures;
    private TextView tOpeninghour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initializing The above created above
        ImageView = (android.widget.ImageView) findViewById(R.id.hotelImg);
        tAddress = (TextView) findViewById(R.id.hotelAddressVisible);
        tLocation = (TextView) findViewById(R.id.hotelLocationVisible);
        tRegion = (TextView) findViewById(R.id.hotelRegionVisible);
        tFeatures = (TextView) findViewById(R.id.hotelFeatureVisible);
        tOpeninghour = (TextView) findViewById(R.id.hotelOpeningHourVisible);
        display();

    }

    public void display() {
        //Retriveing field pass from Attraction_DetailsActivity through intent and assigning each to a variable
        String latitude = getIntent().getExtras().getString("hotel_lat");
        String longitude = getIntent().getExtras().getString("hotel_lng");
        String location = getIntent().getExtras().getString("hotel_location");
        String address = getIntent().getExtras().getString("hotel_address");
        String region = getIntent().getExtras().getString("hotel_region");
        String features = getIntent().getExtras().getString("hotel_features");
        String openinghr = getIntent().getExtras().getString("hotel_openinghour");
        String closinghr = getIntent().getExtras().getString("hotel_closinghour");

        //Setting each value to the required textView
        tLocation.setText(location.toString());
        tAddress.setText(address.toString());
        tRegion.setText(region.toString());
        tFeatures.setText(features.toString());
        tOpeninghour.setText(openinghr.toString());

        //Converting latitude and longitude to Double
        double lat = Double.parseDouble(latitude);
        double lng = Double.parseDouble(longitude);


    }

}
