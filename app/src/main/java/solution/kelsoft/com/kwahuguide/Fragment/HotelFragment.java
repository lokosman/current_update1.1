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

import solution.kelsoft.com.kwahuguide.Activities.Hotel_DetailsActivity;
import solution.kelsoft.com.kwahuguide.Adapter.HotelAdapter;
import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.Extras.Constants;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.Pojo.KawhuHotel;
import solution.kelsoft.com.kwahuguide.R;

import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTELSLIST;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_ADDRESS;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_CHECKIN;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_CHECKOUT;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_CLOSINGHOUR;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_DETAILS;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_FEATURES;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_ICON;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_ID;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_LATITUDE;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_LOCATION;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_LONGITUDE;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_NAME;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_OPENINGHOUR;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_PICTURE;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_POLICES;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_REGION;
import static solution.kelsoft.com.kwahuguide.Extras.HotelKey.Hotel.KEY_HOTEL_SYNOPSIS;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HotelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HotelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String STATE_HOTEL = "state_hotel";
    private final String url = "https://api.myjson.com/bins/3j6w6";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private TextView textVolleyError;
    private ArrayList<KawhuHotel> mKawhuHoteArrayList = new ArrayList<>();
    private HotelAdapter mHotelAdapter;
    private CardView cardView;
    private RecyclerView recyclerView;




    public HotelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * <p/>
     * //  * @param param1 Parameter 1.
     * //  * @param param2 Parameter 2.
     *
     * @return A new instance of fragment HotelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Saving instance state of parcleable here

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_HOTEL, mKawhuHoteArrayList);
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
    public void sendingJSON() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  textVolleyError.setVisibility(View.GONE);
                mKawhuHoteArrayList = parseJSONRESPONSE(response);
                mHotelAdapter.setKawhuHotel(mKawhuHoteArrayList);
                //  CustomMessage.t(getActivity(),"Success" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleVolleyError(error);

                //  CustomMessage.t(getActivity(),"Failed From Hotel" + error);

            }
        });
        requestQueue.add(request);
    }

    //Handing each error volley have with a smiple if statement
    public void handleVolleyError(VolleyError error) {

        textVolleyError.setVisibility(View.VISIBLE);
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            textVolleyError.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            textVolleyError.setText(R.string.error_failure);

        } else if (error instanceof ServerError) {
            textVolleyError.setText(R.string.error_server);
        } else if (error instanceof NetworkError) {
            textVolleyError.setText(R.string.error_network);
        } else if (error instanceof ParseError) {
            textVolleyError.setText(R.string.error_parse);
        }
    }

    private ArrayList<KawhuHotel> parseJSONRESPONSE(JSONObject response) {
        ArrayList<KawhuHotel> hotels = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                StringBuilder builder = new StringBuilder();
                JSONArray arrayHotel = response.getJSONArray(KEY_HOTELSLIST);
                for (int i = 0; i < arrayHotel.length(); i++) {

                    int id = -1;
                    String icon = Constants.NA;
                    String name = Constants.NA;
                    String synopsis = Constants.NA;
                    String lng = Constants.NA;
                    String lat = Constants.NA;
                    String location = Constants.NA;
                    String address = Constants.NA;
                    String region = Constants.NA;
                    String features = Constants.NA;
                    String openinghour = Constants.NA;
                    String closinghour = Constants.NA;
                    String checkin = Constants.NA;
                    String checkout = Constants.NA;


                    JSONObject currentHotel = arrayHotel.getJSONObject(i);

                    if (currentHotel.has(KEY_HOTEL_ID) && !currentHotel.isNull(KEY_HOTEL_ID)) {
                        id = currentHotel.getInt(KEY_HOTEL_ID);
                    }

                    //Parsing jsonObject here of picture
                    JSONObject objectPicture = currentHotel.getJSONObject(KEY_HOTEL_PICTURE);
                    if (objectPicture.has(KEY_HOTEL_ICON) && !currentHotel.isNull(KEY_HOTEL_PICTURE)) {
                        if (objectPicture != null
                                && objectPicture.has(KEY_HOTEL_ICON)
                                && !objectPicture.isNull(KEY_HOTEL_ICON))
                            icon = objectPicture.getString(KEY_HOTEL_ICON);
                    }


                    if (currentHotel.has(KEY_HOTEL_NAME) && !currentHotel.isNull(KEY_HOTEL_NAME)) {
                        name = currentHotel.getString(KEY_HOTEL_NAME);
                    }

                    if (currentHotel.has(KEY_HOTEL_SYNOPSIS) && !currentHotel.isNull(KEY_HOTEL_SYNOPSIS)) {
                        synopsis = currentHotel.getString(KEY_HOTEL_SYNOPSIS);
                    }

                    if (currentHotel.has(KEY_HOTEL_LATITUDE) && !currentHotel.isNull(KEY_HOTEL_LATITUDE)) {
                        lat = currentHotel.getString(KEY_HOTEL_LATITUDE);
                    }

                    if (currentHotel.has(KEY_HOTEL_LONGITUDE) && !currentHotel.isNull(KEY_HOTEL_LONGITUDE)) {
                        lng = currentHotel.getString(KEY_HOTEL_LONGITUDE);
                    }


                    JSONObject objectDetails = currentHotel.getJSONObject(KEY_HOTEL_DETAILS);
                    if (objectDetails.has(KEY_HOTEL_LOCATION) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_LOCATION)
                                && !objectDetails.isNull(KEY_HOTEL_LOCATION))
                            location = objectDetails.getString(KEY_HOTEL_LOCATION);
                    }


                    if (objectDetails.has(KEY_HOTEL_ADDRESS) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_ADDRESS)
                                && !objectDetails.isNull(KEY_HOTEL_ADDRESS))
                            address = objectDetails.getString(KEY_HOTEL_ADDRESS);
                    }

                    if (objectDetails.has(KEY_HOTEL_REGION) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_REGION)
                                && !objectDetails.isNull(KEY_HOTEL_REGION))
                            region = objectDetails.getString(KEY_HOTEL_REGION);
                    }


                    if (objectDetails.has(KEY_HOTEL_FEATURES) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_FEATURES)
                                && !objectDetails.isNull(KEY_HOTEL_FEATURES))
                            features = objectDetails.getString(KEY_HOTEL_FEATURES);
                    }


                    if (objectDetails.has(KEY_HOTEL_OPENINGHOUR) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_OPENINGHOUR)
                                && !objectDetails.isNull(KEY_HOTEL_OPENINGHOUR))
                            openinghour = objectDetails.getString(KEY_HOTEL_OPENINGHOUR);
                    }


                    if (objectDetails.has(KEY_HOTEL_CLOSINGHOUR) && !currentHotel.isNull(KEY_HOTEL_DETAILS)) {
                        if (objectDetails != null
                                && objectDetails.has(KEY_HOTEL_CLOSINGHOUR)
                                && !objectDetails.isNull(KEY_HOTEL_CLOSINGHOUR))
                            closinghour = objectDetails.getString(KEY_HOTEL_CLOSINGHOUR);
                    }


                    JSONObject objectPolices = currentHotel.getJSONObject(KEY_HOTEL_POLICES);

                    if (objectPolices.has(KEY_HOTEL_CHECKIN) && !currentHotel.isNull(KEY_HOTEL_POLICES)) {
                        if (objectPolices != null
                                && objectPolices.has(KEY_HOTEL_CHECKIN)
                                && !objectPolices.isNull(KEY_HOTEL_CHECKIN))
                            checkin = objectPolices.getString(KEY_HOTEL_CHECKIN);
                    }


                    if (objectPolices.has(KEY_HOTEL_CHECKOUT) && !currentHotel.isNull(KEY_HOTEL_POLICES)) {
                        if (objectPolices != null
                                && objectPolices.has(KEY_HOTEL_CHECKOUT)
                                && !objectPolices.isNull(KEY_HOTEL_CHECKOUT))
                            checkout = objectPolices.getString(KEY_HOTEL_CHECKOUT);
                    }

                    KawhuHotel mKawhuHotel = new KawhuHotel();
                    mKawhuHotel.setId(id);
                    mKawhuHotel.setIcon(icon);
                    mKawhuHotel.setName(name);
                    mKawhuHotel.setSynopsis(synopsis);
                    mKawhuHotel.setLatitude(lat);
                    mKawhuHotel.setLongitude(lng);
                    mKawhuHotel.setLocation(location);
                    mKawhuHotel.setAddress(address);
                    mKawhuHotel.setRegion(region);
                    mKawhuHotel.setLocation(location);
                    mKawhuHotel.setFeatures(features);
                    mKawhuHotel.setOpeninghour(openinghour);
                    mKawhuHotel.setClosinghour(closinghour);
                    mKawhuHotel.setCheckin(checkin);
                    mKawhuHotel.setCheckout(checkout);

                    if (id != -1 && !name.equals(Constants.NA)) {
                        hotels.add(mKawhuHotel);
                    }

                }
                CustomMessage.t(getActivity(), "Data Loaded Successfully" + builder.toString());
            } catch (JSONException e) {
                CustomMessage.t(getActivity(), "Error" + e);
            }
        }
        return hotels;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        textVolleyError = (TextView) view.findViewById(R.id.hotelError);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleHotel);
        cardView = (CardView) view.findViewById(R.id.cardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHotelAdapter = new HotelAdapter(getActivity());
        recyclerView.setAdapter(mHotelAdapter);

        //Handing hotel Recycleview click event here

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new Clicklistener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), Hotel_DetailsActivity.class);
                try {
                    intent.putExtra("hotel_icon", mKawhuHoteArrayList.get(position).getIcon());
                    intent.putExtra("hotel_lat", mKawhuHoteArrayList.get(position).getLatitude());
                    intent.putExtra("hotel_lng", mKawhuHoteArrayList.get(position).getLongitude());
                    intent.putExtra("hotel_location", mKawhuHoteArrayList.get(position).getLocation());
                    intent.putExtra("hotel_address", mKawhuHoteArrayList.get(position).getAddress());
                    intent.putExtra("hotel_region", mKawhuHoteArrayList.get(position).getRegion());
                    intent.putExtra("hotel_features", mKawhuHoteArrayList.get(position).getFeatures());
                    intent.putExtra("hotel_openinghour", mKawhuHoteArrayList.get(position).getOpeninghour());
                    intent.putExtra("hotel_closinghour", mKawhuHoteArrayList.get(position).getClosinghour());
                    startActivity(intent);
                } catch (Exception ex) {
                    CustomMessage.t(getActivity(), "Error passing intent object to next Activity");
                }
            }
        }));

        //Restoring Parcelable here if savedInstanceState is not equal to null (!=null)
        if (savedInstanceState != null) {
            mKawhuHoteArrayList = savedInstanceState.getParcelableArrayList(STATE_HOTEL);
            mHotelAdapter.setKawhuHotel(mKawhuHoteArrayList);
        } else {

            sendingJSON();
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
