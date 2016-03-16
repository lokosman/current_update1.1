package solution.kelsoft.com.kwahuguide.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.R;

import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_ATTRACTIONLIST;
import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_ID;
import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_NAME;
import static solution.kelsoft.com.kwahuguide.Extras.Key.Attraction.KEY_SYNOPSIS;

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
    private final String url = "https://api.myjson.com/bins/1pe9b";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        sendJSON();
    }

    //Method that SEND json here
    public void sendJSON() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJSONRESPONSE(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CustomMessage.t(getActivity(), "Failed" + error);
            }
        });
        requestQueue.add(request);
    }

    //Only parsing JSON HERE
    public void parseJSONRESPONSE(JSONObject response) {
        if (response == null || response.length() == 0) {
            return;
        }
        try {
            StringBuilder builder = new StringBuilder();
            JSONArray arrayAttraction = response.getJSONArray(KEY_ATTRACTIONLIST);
            for (int i = 0; i < arrayAttraction.length(); i++) {
                JSONObject currentOBJ = arrayAttraction.getJSONObject(i);
                String name = currentOBJ.getString(KEY_NAME);
                String id = currentOBJ.getString(KEY_ID);
                String synopsis = currentOBJ.getString(KEY_SYNOPSIS);
                //  String address = currentOBJ.getString(KEY_ADDRESS);
                //  String location = currentOBJ.getString(KEY_LOCATION);
                //  String lat = currentOBJ.getString(KEY_LATITUDE);
                //  String lng = currentOBJ.getString(KEY_LONGITUDE);

                builder.append(id + " " + name + " " + synopsis + "\n");
            }
            CustomMessage.t(getActivity(), builder.toString());

        } catch (JSONException e) {
            CustomMessage.t(getActivity(), " " + e);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attraction, container, false);
    }

}
