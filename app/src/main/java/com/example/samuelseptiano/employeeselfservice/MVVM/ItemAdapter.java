package com.example.samuelseptiano.employeeselfservice.MVVM;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends PagedListAdapter<Home, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<Home> homeList;
    private Context context;
    private ArrayList<Home> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;

    public ItemAdapter(Context mCtx, List<Home> homeList, Context context, Boolean isConnected, Activity activity) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
//        this.homeList = homeList;
       // this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.home_grid_category_row, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        Home home = getItem(position);
        holder.newsCategory.setText("Kategori: "+home.getEventType());
        holder.newsTitle.setText(home.getEventName());
//        Picasso.get()
//                .load(home.getEventImage())
//                .placeholder(R.drawable.imgalt)
//                .error(R.drawable.imgalt)
//                .into(holder.newsPoster);

//        Glide.with(context)
//                //MASIH HARDCODE!!!
//                .load(home.getEventImage())
//                .dontAnimate()
//                .error(R.drawable.imgalt)
//                .placeholder(R.drawable.imgalt)
//                .into(holder.newsPoster);


        try {
            byte [] encodeByte= Base64.decode(home.getEventImage(),Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if(size.x >1081 ){
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/3);
                int height = size.y - (size.y/3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }
            else{
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/3);
                int height = size.y - (size.y/3);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }

            holder.newsPoster.setImageBitmap(bitmap);



        } catch(Exception e) {
            e.getMessage();
            //return null;
        }

        holder.lnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingHomeDetail(home.getEventType(),mCtx,home.getEventId());
            }
        });

    }


    private static DiffUtil.ItemCallback<Home> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Home>() {
                @Override
                public boolean areItemsTheSame(Home oldItem, Home newItem) {
                    return oldItem.getEventId() == newItem.getEventId();
                }

                @Override
                public boolean areContentsTheSame(Home oldItem, Home newItem) {
                    return oldItem.equals(newItem);
                }
            };


    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView newsTitle,newsCategory;
        public ImageView newsPoster;
        LinearLayout lnEvent;

        public ItemViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            newsCategory = (TextView) view.findViewById(R.id.tv_news_category);
            newsPoster = (ImageView) view.findViewById(R.id.news_image);
            lnEvent = view.findViewById(R.id.lnEventCategory);

        }

    }
}
