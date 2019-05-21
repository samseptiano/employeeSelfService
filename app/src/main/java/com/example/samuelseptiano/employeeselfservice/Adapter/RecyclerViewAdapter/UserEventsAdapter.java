package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventUser.EventUser;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UserEventsAdapter extends RecyclerView.Adapter<UserEventsAdapter.MyViewHolder> {

    private List<EventUser> userEvents;
    private int rowLayout;
    private Context context;
    private ArrayList<EventUser> userEventsListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView newsTitle, dateJoin;
        public ImageView newsPoster;

        public MyViewHolder(View view) {
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.tv_news_title);
            newsPoster = (ImageView) view.findViewById(R.id.news_image);
            dateJoin = (TextView) view.findViewById(R.id.tv_date_join);

        }
    }


    public UserEventsAdapter(List<EventUser> userEvents, Context context, Boolean isConnected) {
        this.context = context;
        this.userEvents = userEvents;
        this.userEventsListFilter.addAll(userEvents);
        this.isConnected = isConnected;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.events_grid_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventUser user_events = userEvents.get(position);
        //String date = dateJoin.get(position);
        holder.newsTitle.setText(user_events.getEventID());
        Glide.with(context)
                //MASIH HARDCODE!!!
                .load(user_events.getEPID())
                .placeholder(R.drawable.user)
                .dontAnimate()
                .into(holder.newsPoster);

        holder.dateJoin.setText(user_events.getLastUpdate());

        holder.newsPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fr = new HomeDetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("ConnState", isConnected);
//                bundle.putInt("id",home.getId());
//                fr.setArguments(bundle);
//                fm = ((FragmentActivity)context).getSupportFragmentManager();
//                ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_frame, fr);
//                ft.addToBackStack(null);
//                ft.commit();
               //Toast.makeText(context,user_events.getUserEvents().getEventName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userEvents.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        userEvents.clear();
        if (charText.length() == 0) {
            userEvents.addAll(userEventsListFilter);
        }
        else
        {
            for (EventUser wp : userEventsListFilter) {
                if (wp.getEmpNIK().toLowerCase(Locale.getDefault()).contains(charText)) {
                    userEvents.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
