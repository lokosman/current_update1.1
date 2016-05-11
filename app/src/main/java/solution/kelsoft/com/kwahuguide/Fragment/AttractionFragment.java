package solution.kelsoft.com.kwahuguide.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import solution.kelsoft.com.kwahuguide.Activities.Attraction_DetailsActivity;
import solution.kelsoft.com.kwahuguide.Adapter.AttractionAdapter;
import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.Extras.Constants;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.Pojo.KawhuAttraction;
import solution.kelsoft.com.kwahuguide.R;

import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_ADDRESS;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_ATTRACTIONLIST;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_DETAILS;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_ICON;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_ID;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_LATITUDE;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_LOCATION;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_LONGITUDE;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_NAME;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_SYNOPSIS;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_THUMBS;
import static solution.kelsoft.com.kwahuguide.Extras.AttractionKey.Attraction.KEY_TITLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AttractionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AttractionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_KAWHU = "state_kawhu";
    private final String url = "https://api.myjson.com/bins/3v2dm";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<KawhuAttraction> kawhuAttractionArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AttractionAdapter mattractionAdapter;
    private CardView cardView;
    private TextView txtVolleyError;


    public AttractionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //  * @param param1 Parameter 1.
     * //  * @param param2 Parameter 2.
     *
     * @return A new instance of fragment AttractionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AttractionFragment newInstance(String param1, String param2) {
        AttractionFragment fragment = new AttractionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    //Saving instancestate with parcable here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_KAWHU, kawhuAttractionArrayList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }

    //Method that SEND json here
    public void sendJSON() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                txtVolleyError.setVisibility(View.GONE);
                kawhuAttractionArrayList = parseJSONRESPONSE(response);
                mattractionAdapter.setKawhuAttraction(kawhuAttractionArrayList);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // CustomMessage.t(getActivity(),"Failed"+error);
                handleVolleyError(error);
            }
        });
        requestQueue.add(request);
    }
    //Handing each error volley have with a simple if statement
    public void handleVolleyError(VolleyError error) {

        txtVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            txtVolleyError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            txtVolleyError.setText(R.string.error_failure);

        } else if (error instanceof ServerError) {
            txtVolleyError.setText(R.string.error_server);
        } else if (error instanceof NetworkError) {
            txtVolleyError.setText(R.string.error_network);
        } else if (error instanceof ParseError) {
            txtVolleyError.setText(R.string.error_parse);
        }
    }

    //Only parsing JSON HERE
    private ArrayList<KawhuAttraction> parseJSONRESPONSE(JSONObject response) {
        ArrayList<KawhuAttraction> attractions = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                StringBuilder builder = new StringBuilder();
                JSONArray arrayAttraction = response.getJSONArray(KEY_ATTRACTIONLIST);
                for (int i = 0; i < arrayAttraction.length(); i++) {

                    int id = -1;
                    String name = Constants.NA;
                    String address = Constants.NA;
                    String location = Constants.NA;
                    String title = Constants.NA;
                    String synopsis = Constants.NA;
                    String details = Constants.NA;
                    String lat = Constants.NA;
                    String lng = Constants.NA;
                    String icon = Constants.NA;


                    JSONObject currentOBJ = arrayAttraction.getJSONObject(i);
                    //Initializing and getting each json fields

                    if (currentOBJ.has(KEY_ID) && !currentOBJ.isNull(KEY_ID)) {
                        id = currentOBJ.getInt(KEY_ID);
                    }

                    if (currentOBJ.has(KEY_NAME) && !currentOBJ.isNull(KEY_NAME)) {
                        name = currentOBJ.getString(KEY_NAME);
                    }

                    if (currentOBJ.has(KEY_ADDRESS) && !currentOBJ.isNull(KEY_ADDRESS)) {
                        address = currentOBJ.getString(KEY_ADDRESS);
                    }

                    if (currentOBJ.has(KEY_LOCATION) && !currentOBJ.isNull(KEY_LOCATION)) {
                        location = currentOBJ.getString(KEY_LOCATION);
                    }

                    if (currentOBJ.has(KEY_TITLE) && !currentOBJ.isNull(KEY_TITLE)) {
                        title = currentOBJ.getString(KEY_TITLE);
                    }

                    if (currentOBJ.has(KEY_SYNOPSIS) && !currentOBJ.isNull(KEY_SYNOPSIS)) {
                        synopsis = currentOBJ.getString(KEY_SYNOPSIS);
                    }

                    if (currentOBJ.has(KEY_DETAILS) && !currentOBJ.isNull(KEY_DETAILS)) {
                        details = currentOBJ.getString(KEY_DETAILS);
                    }

                    if (currentOBJ.has(KEY_LATITUDE) && !currentOBJ.isNull(KEY_LATITUDE)) {
                        lat = currentOBJ.getString(KEY_LATITUDE);
                    }

                    if (currentOBJ.has(KEY_LONGITUDE) && !currentOBJ.isNull(KEY_LONGITUDE)) {
                        lng = currentOBJ.getString(KEY_LONGITUDE);
                    }


                    JSONObject objectThumbs = currentOBJ.getJSONObject(KEY_THUMBS);
                    if (objectThumbs.has(KEY_ICON) && !currentOBJ.isNull(KEY_THUMBS)) {
                        if (objectThumbs != null
                                && objectThumbs.has(KEY_ICON)
                                && !objectThumbs.isNull(KEY_ICON))
                            icon = objectThumbs.getString(KEY_ICON);
                    }
                    //Passing and setting each field
                    KawhuAttraction mkawhuAttraction = new KawhuAttraction();
                    mkawhuAttraction.setName(name);
                    mkawhuAttraction.setId(id);
                    mkawhuAttraction.setAddress(address);
                    mkawhuAttraction.setLocation(location);
                    mkawhuAttraction.setTitle(title);
                    mkawhuAttraction.setSynopsis(synopsis);
                    mkawhuAttraction.setDetails(details);
                    mkawhuAttraction.setLatitude(lat);
                    mkawhuAttraction.setLongitude(lng);
                    mkawhuAttraction.setIcon(icon);

                    if (id != -1 && !name.equals(Constants.NA)) {
                        attractions.add(mkawhuAttraction);
                    }

                }

                //  CustomMessage.t(getActivity(),"Data load successfully" +builder.toString());

            } catch (JSONException e) {
                CustomMessage.t(getActivity(), "Error" + e);
            }
        }
        return attractions;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attraction, container, false);
        txtVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
        //  cardView = (CardView) view.findViewById(R.id.card);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mattractionAdapter = new AttractionAdapter(getActivity());
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mattractionAdapter);
        scaleAdapter.setFirstOnly(false);
        recyclerView.setAdapter(scaleAdapter);

        //Handling recyclerview Clicklistener here
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new Clicklistener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), Attraction_DetailsActivity.class);
                try {
                    intent.putExtra("kawhu_icon", kawhuAttractionArrayList.get(position).getIcon());
                    intent.putExtra("kawhu_lat", kawhuAttractionArrayList.get(position).getLatitude());
                    intent.putExtra("kawhu_lng", kawhuAttractionArrayList.get(position).getLongitude());
                    intent.putExtra("kawhu_location", kawhuAttractionArrayList.get(position).getLocation());
                    intent.putExtra("kawhu_details", kawhuAttractionArrayList.get(position).getDetails());
                    intent.putExtra("kawhu_title", kawhuAttractionArrayList.get(position).getTitle());
                    startActivity(intent);
                } catch (Exception ex) {
                    CustomMessage.t(getActivity(), "Error passing intent object");
                }

            }

        }));

        //Restoring parcable object here if save instance state is not null
        if (savedInstanceState != null) {
            kawhuAttractionArrayList = savedInstanceState.getParcelableArrayList(STATE_KAWHU);
            mattractionAdapter.setKawhuAttraction(kawhuAttractionArrayList);
        } else {

            sendJSON();
        }

        return view;

    }

    //class for handling recyclerTouchListener

    public interface Clicklistener {
        void onItemClick(View v, int position);
        //  public void onLongClick(View v, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private Clicklistener clicklistener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Clicklistener clicklistener) {
            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onItemClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onItemClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
