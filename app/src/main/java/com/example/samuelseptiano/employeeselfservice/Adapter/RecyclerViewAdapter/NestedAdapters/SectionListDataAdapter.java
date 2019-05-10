package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.NestedAdapters;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.samuelseptiano.employeeselfservice.Model.NestedRecyclerViewModels.SingleItemModel;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;


import java.util.ArrayList;
import java.util.List;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.SingleItemRowHolder> {

    private ArrayList<SingleItemModel> itemsList;
    protected Context mContext;
    protected List<String> id;
    protected Boolean isConnected;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    public SectionListDataAdapter(Context context, ArrayList<SingleItemModel> itemsList, Boolean isConn) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.isConnected = isConn;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_horizontal_row, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        SingleItemModel singleItem = itemsList.get(i);

        holder.tvTitle.setText(singleItem.getName());

        Glide.with(mContext)
                .load(singleItem.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.imgalt)
                .into(holder.itemImage);

        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(v.getContext(),singleItem.getPosterPath(),Toast.LENGTH_SHORT).show();

                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();

                routingHomeDetailInterface.routingHomeDetail(singleItem.getType(),mContext,singleItem.getId());

//                fr = new HomeDetailFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putBoolean("ConnState", isConnected);
//                    bundle.putString("id",singleItem.getId());
//                    fr.setArguments(bundle);
//                    fm = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
//                    ft = fm.beginTransaction();
//                    ft.replace(R.id.fragment_frame, fr);
//                    ft.addToBackStack(null);
//                    ft.commit();
//                    Toast.makeText(v.getContext(),"Home Detail Area",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemsList != null){
           if(itemsList.size()<10){
               return  itemsList.size();
           }
        }
        else{
            return 0;
        }
        return (null != itemsList ? itemsList.size() : 0);

    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView tvTitle;

        protected ImageView itemImage;


        public SingleItemRowHolder(View view) {
            super(view);

            this.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);


//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
////                    fr = new HomeDetailFragment();
////                    Bundle bundle = new Bundle();
////                    bundle.putBoolean("ConnState", isConnected);
////                    bundle.putInt("id",Integer.parseInt(id));
////                    fr.setArguments(bundle);
////                    fm = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
////                    ft = fm.beginTransaction();
////                    ft.replace(R.id.fragment_frame, fr);
////                    ft.addToBackStack(null);
////                    ft.commit();
////                    Toast.makeText(v.getContext(),"Home Detail Area",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(v.getContext(), id.get(), Toast.LENGTH_SHORT).show();
//
//                }
//            });


        }

    }

}