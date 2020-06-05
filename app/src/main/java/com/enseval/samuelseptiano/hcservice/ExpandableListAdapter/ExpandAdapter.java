package com.enseval.samuelseptiano.hcservice.ExpandableListAdapter;


import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.DistNormalH;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

public class ExpandAdapter extends RecyclerView.Adapter<ExpandAdapter.MyViewHolder> {

    private List<ExpandModel> expandModelList;
    private ArrayList<ExpandModel> expandModelListFilter = new ArrayList<>();  // for loading  filter data

    private Context context;
    private Activity activity;

    EventListener listener;

    public interface EventListener{
         void setItem(String item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvItem;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }

    public ExpandAdapter(List<ExpandModel> expandModelList, Context context, Activity activity, EventListener listener) {
        this.context = context;
        this.expandModelList = expandModelList;
        this.activity = activity;
        this.expandModelListFilter.addAll(expandModelList);
        this.listener = listener;

    }

    @Override
    public ExpandAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ExpandAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpandAdapter.MyViewHolder holder, int position) {
        ExpandModel em = expandModelList.get(position);
        if(position>0 && expandModelList.get(position-1).getTitle().equals(expandModelList.get(position).getTitle())){
            holder.tvTitle.setVisibility(View.GONE);
        }
        else {
            holder.tvTitle.setText(em.getTitle());
        }
        holder.tvItem.setText(em.getItem());
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setItem(holder.tvItem.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return expandModelList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        expandModelList.clear();
        if (charText.length() == 0) {
            expandModelList.addAll(expandModelListFilter);
        }
        else
        {
            for (ExpandModel wp : expandModelListFilter) {
                if (wp.getItem().toLowerCase(Locale.getDefault()).contains(charText)) {
                    expandModelList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}