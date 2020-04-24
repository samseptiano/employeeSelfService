package com.enseval.samuelseptiano.hcservice.MVVM.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.enseval.samuelseptiano.hcservice.Helper.EventAbsentUserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.enseval.samuelseptiano.hcservice.utils.DateFormatter;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

public class ItemAdapter extends PagedListAdapter<EventAbsentUser, ItemAdapter.ItemViewHolder> {

    private Context mCtx;
    private List<EventAbsentUser> homeList;
    private Context context;
//    private ArrayList<Home> homeListFilter = new ArrayList<>();  // for loading  filter data
    //    private ArrayList<EventAbsentUser> homeListFilter = new ArrayList<>();  // for loading  filter data
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    EventAbsentUserRealmHelper eventAbsentUserRealmHelper;


    public ItemAdapter(Context mCtx,List<EventAbsentUser> homeList, Context context, Boolean isConnected) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
       this.context = context;
        this.homeList = homeList;
//        this.homeListFilter.addAll(homeList);
        this.isConnected = isConnected;
        eventAbsentUserRealmHelper = new EventAbsentUserRealmHelper(mCtx);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.absent_history_list, parent, false);
//        for (int i=0;i<getItemCount();i++) {
//            eventAbsentUserRealmHelper.addArticle(getItem(i));
            //Toast.makeText(mCtx,Integer.toString(getItemCount()),Toast.LENGTH_LONG).show();
//        }
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        EventAbsentUser home = getItem(position);
        //eventAbsentUserRealmHelper.addArticle(home);

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
            }
        });

    }


    private static DiffUtil.ItemCallback<EventAbsentUser> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<EventAbsentUser>() {
                @Override
                public boolean areItemsTheSame(EventAbsentUser oldItem, EventAbsentUser newItem) {
                    return oldItem.getEaid() == newItem.getEaid();
                }

                @Override
                public boolean areContentsTheSame(EventAbsentUser oldItem, EventAbsentUser newItem) {
                    return oldItem.equals(newItem);
                }
            };


    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName,eventDate;
        public ImageView imgAbsent;
        public LinearLayout lnHistory;

        public ItemViewHolder(View view) {
            super(view);
            eventName = (TextView) view.findViewById(R.id.tv_eventName);
            eventDate = (TextView) view.findViewById(R.id.tv_eventDate);
            lnHistory = view.findViewById(R.id.lnAbsenHistory);
            imgAbsent = view.findViewById(R.id.imgAbsent);
        }

    }
}
