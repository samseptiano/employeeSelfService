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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EventAbsentHistoryAdapter extends RecyclerView.Adapter<EventAbsentHistoryAdapter.MyViewHolder> {

    private List<EventAbsentUser> homeList;
    private Context context;
    private ArrayList<EventAbsentUser> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventId,eventName,eventDate;
        public LinearLayout lnHistory;

        public MyViewHolder(View view) {
            super(view);
            eventId = (TextView) view.findViewById(R.id.tv_eventId);
            eventName = (TextView) view.findViewById(R.id.tv_eventName);
            eventDate = (TextView) view.findViewById(R.id.tv_eventDate);
            lnHistory = view.findViewById(R.id.lnAbsenHistory);
        }
    }

    public EventAbsentHistoryAdapter(List<EventAbsentUser> homeList, Context context, Boolean isConnected) {
        this.context = context;
        this.homeList = homeList;
        this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.absent_history_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventAbsentUser home = homeList.get(position);
        holder.eventId.setText("ID: "+home.getEventID());
        holder.eventName.setText(home.getEventName());
        holder.eventDate.setText(home.getEventDate());
        holder.lnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                routingHomeDetailInterface.routingHomeDetail(home.getEventType(),context,home.getEventID());


//                fr = new HomeDetailFragment();
//                Bundle bundle = new Bundle();
//                bundle.putBoolean("ConnState", isConnected);
//                bundle.putString("id",home.getEventID());
//                fr.setArguments(bundle);
//                fm = ((FragmentActivity)context).getSupportFragmentManager();
//                ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_frame, fr);
//                ft.addToBackStack(null);
//                ft.commit();
//                Toast.makeText(context,"Home Detail Area",Toast.LENGTH_LONG).show();
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
            for (EventAbsentUser wp : homeListFilter) {
                if (wp.getEventName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    homeList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}
