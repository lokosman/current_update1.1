package solution.kelsoft.com.kwahuguide.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import solution.kelsoft.com.kwahuguide.Animation.AnimationUtils;
import solution.kelsoft.com.kwahuguide.Extras.Constants;
import solution.kelsoft.com.kwahuguide.Network.MyApplication;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.Pojo.KawhuHotel;
import solution.kelsoft.com.kwahuguide.R;

/**
 * Created by Rukuvwe on 3/21/2016.
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.MyViewHolder> {

    private ArrayList<KawhuHotel> hotels = new ArrayList<>();

    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int previousPosition = 0;

    public HotelAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setKawhuHotel(ArrayList<KawhuHotel> hotels) {
        this.hotels = hotels;
        notifyItemRangeChanged(0, hotels.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_hotel_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        KawhuHotel currentHotel = hotels.get(position);
        holder.txtHotelTitle.setText(currentHotel.getName());
        holder.txtHotelDesc.setText(currentHotel.getSynopsis());
        String ImgIcon = currentHotel.getIcon();
        loadImages(ImgIcon, holder);

        if (position > previousPosition) {

            AnimationUtils.animate(holder, true);

        } else {
            AnimationUtils.animate(holder, false);
        }
        previousPosition = position;

    }

    public void loadImages(String ImgIcon, final MyViewHolder holder) {

        if (!ImgIcon.equals(Constants.NA)) {
            imageLoader.get(ImgIcon, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.imgHotelView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyApplication.getAppContext(), "ImageError", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtHotelTitle;
        private TextView txtHotelDesc;
        private ImageView imgHotelView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtHotelTitle = (TextView) itemView.findViewById(R.id.txtHotelTitle);
            txtHotelDesc = (TextView) itemView.findViewById(R.id.txtDescHotel);
            imgHotelView = (ImageView) itemView.findViewById(R.id.imageViewHotel);
        }
    }
}
