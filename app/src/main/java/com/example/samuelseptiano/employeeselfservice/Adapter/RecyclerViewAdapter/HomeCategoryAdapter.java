package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder> {

    private List<Home> homeList;
    private Context context;
    private ArrayList<Home> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle,newsId,newsCategory;
        public ImageView newsPoster;

        public MyViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            newsId = (TextView) view.findViewById(R.id.tv_news_id);
            newsCategory = (TextView) view.findViewById(R.id.tv_news_category);
            newsPoster = (ImageView) view.findViewById(R.id.news_image);


        }

    }


    public HomeCategoryAdapter(List<Home> homeList, Context context, Boolean isConnected) {
        this.context = context;
        this.homeList = homeList;
        this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_grid_category_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Home home = homeList.get(position);
        holder.newsId.setText("ID: "+home.getEventId());
        holder.newsCategory.setText(home.getEventType());
        holder.newsTitle.setText(home.getEventName());
        Picasso.get()
                .load(home.getEventImage())
                .placeholder(R.drawable.imgalt)
                .error(R.drawable.imgalt)
                .into(holder.newsPoster);

//        Glide.with(context)
//                //MASIH HARDCODE!!!
//                .load(home.getNewsImage())
//                .dontAnimate()
//                .placeholder(R.drawable.user)
//                .into(holder.newsPoster);

        holder.newsPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fr = new HomeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", isConnected);
                bundle.putString("id",home.getEventId());
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();
                Toast.makeText(context,"Home Detail Area",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        homeList.clear();
        if (charText.length() == 0) {
            homeList.addAll(homeListFilter);
        }
        else
        {
            for (Home wp : homeListFilter) {
                if (wp.getEventName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    homeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}