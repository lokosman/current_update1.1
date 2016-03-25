package solution.kelsoft.com.kwahuguide.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import solution.kelsoft.com.kwahuguide.Extras.Constants;
import solution.kelsoft.com.kwahuguide.Network.VolleySingleton;
import solution.kelsoft.com.kwahuguide.Pojo.KawhuAttraction;
import solution.kelsoft.com.kwahuguide.R;

/**
 * Created by Rukuvwe on 3/14/2016.
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    //Creating an ArrayList of type kawhuAttraction
    private ArrayList<KawhuAttraction> attractions = new ArrayList<>();


    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;


    public AttractionAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();

    }

    public void setKawhuAttraction(ArrayList<KawhuAttraction> attraction) {
        this.attractions = attraction;
        notifyItemRangeChanged(0, attraction.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_attraction_list, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        KawhuAttraction currentAttraction = attractions.get(position);
        holder.AttractionTxtTitle.setText(currentAttraction.getName());
        holder.AttractionTxtDesc.setText(currentAttraction.getSynopsis());
        String ImgIcon = currentAttraction.getIcon();
        loadImages(ImgIcon, holder);
    }

    public void loadImages(String ImgIcon, final ViewHolder holder) {

        if (!ImgIcon.equals(Constants.NA)) {
            imageLoader.get(ImgIcon, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.AttractionImageView.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    //      Toast.makeText(MyApplication.getAppContext(),"ImageError",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView AttractionImageView;
        private TextView AttractionTxtTitle;
        private TextView AttractionTxtDesc;

        public ViewHolder(View itemView) {
            super(itemView);


            AttractionImageView = (ImageView) itemView.findViewById(R.id.imageView);
            AttractionTxtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            AttractionTxtDesc = (TextView) itemView.findViewById(R.id.txtDesc);

        }
    }
}

