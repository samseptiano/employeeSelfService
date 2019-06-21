package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.R;

import java.util.List;

public class KPIHintAdapter extends RecyclerView.Adapter<KPIHintAdapter.MyViewHolder> {

    private List<String> hint;
    private Context context;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHint;

        public MyViewHolder(View view) {
            super(view);
            tvHint = (TextView) view.findViewById(R.id.tvKPIHint);
        }
    }

    public KPIHintAdapter(List<String> hint, Context context, Activity activity) {
        this.context = context;
        this.hint = hint;
        this.activity = activity;

    }

    @Override
    public KPIHintAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_hint_dialog_list, parent, false);

        return new KPIHintAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIHintAdapter.MyViewHolder holder, int position) {
        String home = hint.get(position);
        holder.tvHint.setText(home);
    }

    @Override
    public int getItemCount() {
        return hint.size();
    }


}