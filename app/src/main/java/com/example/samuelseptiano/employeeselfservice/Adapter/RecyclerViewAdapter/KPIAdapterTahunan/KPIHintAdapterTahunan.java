package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHint;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.List;

public class KPIHintAdapterTahunan extends RecyclerView.Adapter<KPIHintAdapterTahunan.MyViewHolder> {

    private List<KPIHint> hint;
    private Context context;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHint;
        public TextView tvHintDesc;
        public RatingBar ratingHint;

        public MyViewHolder(View view) {
            super(view);
            tvHint = (TextView) view.findViewById(R.id.tvKPIHint);
            tvHintDesc = (TextView) view.findViewById(R.id.tvKPIHintDesc);
            ratingHint = (RatingBar) view.findViewById(R.id.ratingKPIHint);
        }
    }

    public KPIHintAdapterTahunan(List<KPIHint> hint, Context context, Activity activity) {
        this.context = context;
        this.hint = hint;
        this.activity = activity;

    }

    @Override
    public KPIHintAdapterTahunan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_hint_dialog_list, parent, false);

        return new KPIHintAdapterTahunan.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIHintAdapterTahunan.MyViewHolder holder, int position) {
        KPIHint home = hint.get(position);
        holder.tvHint.setText(home.getKpiGradeName());
        holder.ratingHint.setRating((float) hint.size()-position);
        switch (hint.size()-position) {
            case 1:
                holder.tvHintDesc.setText("Unacceptable");
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 2:
                holder.tvHintDesc.setText("Below Expectation");
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case 3:
                holder.tvHintDesc.setText("Meet Expectation");
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case 4:
                holder.tvHintDesc.setText("Exceed Expectation");
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.darkGreen));
                break;
            case 5:
                holder.tvHintDesc.setText("Excelllent");
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.blue));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return hint.size();
    }


}