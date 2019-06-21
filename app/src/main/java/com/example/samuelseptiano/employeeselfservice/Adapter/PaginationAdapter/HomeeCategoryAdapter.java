package com.example.samuelseptiano.employeeselfservice.Adapter.PaginationAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;

public class HomeeCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

Context context;
    private List<Home> mPostItems;
    private ArrayList<Home> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;



    public HomeeCategoryAdapter(List<Home> homeList, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.mPostItems = homeList;
        this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;
        this.activity = activity;
        homeListFilter.addAll(homeList);
        //Toast.makeText(context,Integer.toString(homeList.size()),Toast.LENGTH_LONG).show();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_category_row, parent, false));
            case VIEW_TYPE_LOADING:
                return new FooterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
        //Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == mPostItems.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        }
        if(position == mPostItems.size()){
            return 0;


        }
        else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return mPostItems == null ? 0 : mPostItems.size();
    }

    public void add(Home response) {
        mPostItems.add(response);
        notifyItemInserted(mPostItems.size() - 1);
    }

    public void addAll(List<Home> postItems) {
        for (Home response : postItems) {
            add(response);
        }
        Toast.makeText(context,Integer.toString(postItems.size()),Toast.LENGTH_LONG).show();

    }


    private void remove(Home postItems) {
        int position = mPostItems.indexOf(postItems);
        if (position > -1) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void addLoading() {
        isLoaderVisible = true;
        add(new Home());
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = mPostItems.size() - 1;
        Home item = getItem(position);
        if (item != null) {
            mPostItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void removeLoadingLast() {
        isLoaderVisible = false;
        //getItemViewType(40);
       // Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    Home getItem(int position) {
        return mPostItems.get(position);
    }


    public class ViewHolder extends BaseViewHolder {
        public TextView newsTitle,newsCategory;
        public ImageView newsPoster;
        LinearLayout lnEvent;



        ViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_news_title);
            newsCategory = (TextView) itemView.findViewById(R.id.tv_news_category);
            newsPoster = (ImageView) itemView.findViewById(R.id.news_image);
            lnEvent = itemView.findViewById(R.id.lnEventCategory);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);
            Home item = mPostItems.get(position);
            //Toast.makeText(context,Integer.toString(mPostItems.size()),Toast.LENGTH_LONG).show();

            newsCategory.setText("Kategori: "+item.getEventType());
            newsTitle.setText(item.getEventName());
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
                byte [] encodeByte=Base64.decode(item.getEventImage(),Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

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

                newsPoster.setImageBitmap(bitmap);



            } catch(Exception e) {
                e.getMessage();
                //return null;
            }

            lnEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingHomeDetail(item.getEventType(),context,item.getEventId());
                }
            });

        }
    }

    public class FooterHolder extends BaseViewHolder {

        ProgressBar mProgressBar;


        FooterHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }

        public void hideProgressBar(){
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        protected void clear() {

        }

    }

}
