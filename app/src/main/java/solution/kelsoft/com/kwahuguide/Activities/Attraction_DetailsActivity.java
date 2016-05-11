package solution.kelsoft.com.kwahuguide.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.R;

public class Attraction_DetailsActivity extends AppCompatActivity {
    private TextView txtDetailLocation;
    private TextView txtDetails;
    private ImageView imageView;
    private GoogleMap googleMap;
    private MapView mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtDetailLocation = (TextView) findViewById(R.id.txtLocationInvisible);
        txtDetails = (TextView) findViewById(R.id.txtDescInvisible);
        display();
        imageload();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_overflow) {
            CustomMessage.t(this, "Setting selected");
            return true;
        }
        if (id == R.id.exit) {
            CustomMessage.t(this, "iwas clciked");
        }
        return super.onOptionsItemSelected(item);
    }


    public void imageload() {
        final String detailImg = getIntent().getExtras().getString("kawhu_icon");
        imageView = (ImageView) findViewById(R.id.attrImages);
        Glide.with(this)
                .load(detailImg)
                .centerCrop()
                .into(imageView);
    }

    public void display() {
        String location = getIntent().getExtras().getString("kawhu_location");
        txtDetailLocation.setText(location.toString());

        String details = getIntent().getExtras().getString("kawhu_details");
        txtDetails.setText(details.toString());

        String lat = getIntent().getExtras().getString("kawhu_lat");

        String lng = getIntent().getExtras().getString("kawhu_lng");

        String title = getIntent().getExtras().getString("kawhu_title");

        //Converting String value of lat and lng gotten from json to double
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lng);


        final LatLng latLngPosition = new LatLng(latitude, longitude);
        //Google map testing here 1st Time
        SupportMapFragment frag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        googleMap = frag.getMap();
        frag.getView().setClickable(false);
        // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngPosition, 15));
        //  googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 5000, null);

        //Trying out Something new here not too sure how it looks likes
        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(latitude, longitude)).zoom(10).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        Marker marker = googleMap.addMarker(new MarkerOptions().position(latLngPosition).title(title).snippet(" "));
        marker.setDraggable(true);
        marker.showInfoWindow();


    }
}
