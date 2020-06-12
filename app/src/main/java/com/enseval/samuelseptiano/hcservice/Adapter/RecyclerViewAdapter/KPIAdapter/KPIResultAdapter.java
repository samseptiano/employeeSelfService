package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.KPIAdapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIHintPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIResultScorePJ;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class KPIResultAdapter extends RecyclerView.Adapter<KPIResultAdapter.MyViewHolder> {

    private List<KPIResultScorePJ> kpiResultScorePJList;
    private Context context;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvKPIDesc,tvBobot,tvHasil;
        public MaterialRatingBar ratingScore;
        public MyViewHolder(View view) {
            super(view);
            tvKPIDesc = (TextView) view.findViewById(R.id.tv_kpiDesc);
            tvBobot = (TextView) view.findViewById(R.id.tv_bobot);
            tvHasil = (TextView) view.findViewById(R.id.tv_hasil);
            ratingScore = view.findViewById(R.id.ratingScore);

        }
    }

    public KPIResultAdapter(List<KPIResultScorePJ> kpiResultScorePJList, Context context, Activity activity) {
        this.context = context;
        this.kpiResultScorePJList = kpiResultScorePJList;
        this.activity = activity;

    }

    @Override
    public KPIResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pk_result_item, parent, false);

        return new KPIResultAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIResultAdapter.MyViewHolder holder, int position) {
        KPIResultScorePJ home = kpiResultScorePJList.get(position);
        holder.tvBobot.setText(home.getBobot()+"");
        holder.tvHasil.setText(home.getHasil()+"");
        holder.tvKPIDesc.setText(home.getKPIDesc()+"");
        holder.ratingScore.setRating(Float.parseFloat(home.getRatingScrore().replace(",",".")));
    }

    @Override
    public int getItemCount() {
        return kpiResultScorePJList.size();
    }


}