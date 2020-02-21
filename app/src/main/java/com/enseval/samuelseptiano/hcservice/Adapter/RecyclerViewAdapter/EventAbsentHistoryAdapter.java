package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.utils.DateFormatter;

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
        public TextView eventName,eventDate;
        public ImageView imgAbsent;
        public LinearLayout lnHistory;

        public MyViewHolder(View view) {
            super(view);
            eventName = (TextView) view.findViewById(R.id.tv_eventName);
            eventDate = (TextView) view.findViewById(R.id.tv_eventDate);
            lnHistory = view.findViewById(R.id.lnAbsenHistory);
            imgAbsent = view.findViewById(R.id.imgAbsent);
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
        holder.eventName.setText(home.getEventName());

        DateFormatter df = new DateFormatter();
        holder.eventDate.setText(df.indonesiaDate(home.getEventDate())+" "+home.getEventDate().substring(11,19));

        Glide.with(context)
                .load(R.drawable.event_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .error(R.drawable.imgalt)
                .into(holder.imgAbsent);

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
//                Toast.makeText(context,"Home Detail Area",Toast.LENGTH_SHORT).show();
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
