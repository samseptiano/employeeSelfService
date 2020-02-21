package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.DistribusiNormalAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.SortDNModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.MyViewHolder> {

    List<PerhitunganPAEMPModel>perhitunganPAEMPModelList;
    private List<SortDNModel> sortDNModelList;
    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;
    List<String> sortBy;
    List<String> orderBy;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MaterialSpinner spinnerSortBy, spinnerOrderBy;
        public TextView tvSortby;
        public MyViewHolder(View view) {
            super(view);
            spinnerOrderBy = view.findViewById(R.id.spinnerOrderby);
            spinnerOrderBy.setBackgroundResource(R.drawable.shapedropdown);
            spinnerOrderBy.setPadding(25, 10, 25, 10);

            spinnerSortBy = view.findViewById(R.id.spinnerSortBy);
            spinnerSortBy.setBackgroundResource(R.drawable.shapedropdown);
            spinnerSortBy.setPadding(25, 10, 25, 10);
            tvSortby = view.findViewById(R.id.tvSortTitle);

            orderBy = new ArrayList<String>();
            orderBy.add("A-Z");
            orderBy.add("Z-A");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, orderBy);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerOrderBy.setAdapter(dataAdapter);

            sortBy = new ArrayList<String>();
            sortBy.add("Emp Name");
            sortBy.add("Score");
            sortBy.add("DN");
            sortBy.add("DN Adjustment");
            sortBy.add("Job Title");

            dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, sortBy);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSortBy.setAdapter(dataAdapter);
        }

    }


    public SortAdapter(List<SortDNModel> sortDNModelList, Context context, Boolean isConnected, Activity activity,List<PerhitunganPAEMPModel>perhitunganPAEMPModelList) {
        this.context = context;
        this.sortDNModelList = sortDNModelList;
        this.isConnected = isConnected;
        this.activity = activity;
        this.perhitunganPAEMPModelList = perhitunganPAEMPModelList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sort_dn_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SortDNModel sortDNModel = sortDNModelList.get(position);
        if(position>0){
            holder.tvSortby.setText("Then by");
        }

        if(sortDNModel.getOrder().equals("A-Z")){
        holder.spinnerOrderBy.setSelectedIndex(0);
        }
        else{
        holder.spinnerOrderBy.setSelectedIndex(1);
        }

        for (int i=0;i<sortBy.size();i++){
            if(sortBy.get(i).equals(sortDNModel.getSortBy())){
                holder.spinnerSortBy.setSelectedIndex(i);
            }
        }

        holder.spinnerSortBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int pos, long id, Object item) {
                sortDNModelList.get(position).setSortBy(item.toString());
            }
        });
        holder.spinnerOrderBy.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int pos, long id, Object item) {
                sortDNModelList.get(position).setOrder(item.toString());
            }
        });


    }

    @Override
    public int getItemCount() {
        return sortDNModelList.size();
    }


}