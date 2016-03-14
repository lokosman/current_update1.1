package solution.kelsoft.com.kwahuguide.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.R;

import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_ATTRACTIONLIST;
import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_NAME;

public class MainActivity extends AppCompatActivity {
    private final String url = "https://api.myjson.com/bins/2p9rr";
    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;

    @Nullable
    private static void paseJSONList(JSONObject response) {
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            StringBuilder builder = new StringBuilder();
            JSONArray arrayAttraction = response.getJSONArray(KEY_ATTRACTIONLIST);
            for (int i = 0; i < arrayAttraction.length(); i++) {
                JSONObject currentOBJ = arrayAttraction.getJSONObject(i);
                String name = currentOBJ.getString(KEY_NAME);
                builder.append(name + "\n");
            }
            //  displayMessage.t(MyApplication.getAppContext(), builder.toString());
        } catch (JSONException e) {
            //  displayMessage.l("lokosMan", "JSONException");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initializing the widget RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

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
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJsonRequest();
    }

    //Making a jsonRequest

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    //JsonParse

    public void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                paseJSONList(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}

