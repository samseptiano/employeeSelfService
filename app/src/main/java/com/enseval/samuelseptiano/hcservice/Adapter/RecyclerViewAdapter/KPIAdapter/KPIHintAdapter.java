package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapter;


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

import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIHintAdapterTahunan;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHint;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIHintPJ;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

public class KPIHintAdapter extends RecyclerView.Adapter<KPIHintAdapter.MyViewHolder> {

    private List<KPIHintPJ> hint;
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

    public KPIHintAdapter(List<KPIHintPJ> hint, Context context, Activity activity) {
        this.context = context;
        this.hint = hint;
        this.activity = activity;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_hint_dialog_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KPIHintPJ home = hint.get(position);
        holder.tvHint.setText(home.getKpiGradeName());
        holder.ratingHint.setRating((float) hint.size()-position);
        switch (hint.size()-position) {
            case 1:
                holder.tvHintDesc.setText("Unacceptable");
                LayerDrawable stars = (LayerDrawable) holder.ratingHint.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case 2:
                holder.tvHintDesc.setText("Below Expectation");
                stars = (LayerDrawable) holder.ratingHint.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.yellow));
                break;
            case 3:
                holder.tvHintDesc.setText("Meet Expectation");
                stars = (LayerDrawable) holder.ratingHint.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.green), PorterDuff.Mode.SRC_ATOP);
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case 4:
                holder.tvHintDesc.setText("Exceed Expectation");
                stars = (LayerDrawable) holder.ratingHint.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.darkGreen), PorterDuff.Mode.SRC_ATOP);
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.darkGreen));
                break;
            case 5:
                holder.tvHintDesc.setText("Excelllent");
                stars = (LayerDrawable) holder.ratingHint.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(context.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);
                holder.tvHintDesc.setTextColor(context.getResources().getColor(R.color.blue));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return hint.size();
    }


}