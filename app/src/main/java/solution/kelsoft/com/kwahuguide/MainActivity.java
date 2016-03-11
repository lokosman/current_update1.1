package solution.kelsoft.com.kwahuguide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {
    private final String url = "http://google.com";
    Toolbar toolbar;
    MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the widget toolbar and setting it to display
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing searchView and using setting searchView Listener once user click
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });
        searchView.setVoiceSearch(false);

        //creating suggestions for user depending on the text enter by them
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));


        //Volley Request network connection here
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                displayMessage.t(MainActivity.this, "Success" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                displayMessage.t(MainActivity.this, "failed" + error);
            }
        });
        // mRequestQueue.add(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }
}
