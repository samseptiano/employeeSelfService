package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.NestedAdapters;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Model.NestedRecyclerViewModels.SectionDataModel;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.ArrayList;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    public RecyclerViewDataAdapter(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_grid_row, parent, false);
            return new RecyclerViewDataAdapter.ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final RecyclerViewDataAdapter.ItemViewHolder itemViewHolder = (RecyclerViewDataAdapter.ItemViewHolder) holder;
            final String sectionName = dataList.get(position).getHeaderTitle();
            ArrayList singleSectionItems = dataList.get(position).getAllItemsInSection();
            itemViewHolder.itemTitle.setText(sectionName);
            SectionListDataAdapter itemListDataAdapter = new SectionListDataAdapter(mContext, singleSectionItems, ConnectivityReceiver.isConnected());
            itemViewHolder.recycler_view_list.setHasFixedSize(true);
            itemViewHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            itemViewHolder.recycler_view_list.setAdapter(itemListDataAdapter);
            itemViewHolder.recycler_view_list.setNestedScrollingEnabled(false);
            //Log.e(TAG, position + " :onBindViewHolder: " + current.toString());
            }


    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) itemView.findViewById(R.id.recycler_view_list);
            this.btnMore= (Button) itemView.findViewById(R.id.btnMore);
            if(dataList.size()>6){
                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] cats = itemTitle.getText().toString().split(" ");
                        fr = new HomeCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("Category", cats[1]);
                        bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                        fr.setArguments(bundle);
                        fm = ((FragmentActivity)v.getContext()).getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_frame, fr);
                        ft.addToBackStack(null);
                        ft.commit();
                        Toast.makeText(v.getContext(),"Home Category Area",Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else{
                btnMore.setEnabled(false);
                btnMore.setVisibility(View.GONE);
            }


        }

    }

}