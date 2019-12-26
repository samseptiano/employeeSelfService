package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.SetupPAAdapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupEmployeeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.SetupPositionModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class PositionSetupPAAdapter extends RecyclerView.Adapter<PositionSetupPAAdapter.MyViewHolder> {

    private List<SetupPositionModel> KPIList;
    private Context context;
    private Activity activity;
    private ArrayList<SetupPositionModel> KPIListFilter = new ArrayList<>();

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtemplateName, tvYear;
        Button btnSetting;

        public MyViewHolder(View view) {
            super(view);
            tvtemplateName = (TextView) view.findViewById(R.id.tv_templateName);
            tvYear = (TextView) view.findViewById(R.id.tv_year);
            btnSetting = view.findViewById(R.id.btnSetting);
        }
    }

    public PositionSetupPAAdapter(List<SetupPositionModel> KPIList, Context context, Activity activity) {
        this.context = context;
        this.KPIList = KPIList;
        this.activity = activity;
        this.KPIListFilter.addAll(KPIList);
    }

    @Override
    public PositionSetupPAAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setup_pa_kuantitatif_position_item, parent, false);

        return new PositionSetupPAAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PositionSetupPAAdapter.MyViewHolder holder, int position) {
        SetupPositionModel setupPositionModel = KPIList.get(position);
        holder.tvtemplateName.setText(setupPositionModel.getPositionName());
        holder.tvYear.setText(setupPositionModel.getYear());

        holder.btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return KPIList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        KPIList.clear();
        if (charText.length() == 0) {
            KPIList.addAll(KPIListFilter);
        }
        else
        {
            for (SetupPositionModel wp : KPIListFilter) {
                if (wp.getPositionName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    KPIList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}