package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.R;

import java.util.List;

public class IconHomeAdapter extends RecyclerView.Adapter<IconHomeAdapter.MyViewHolder> {

    private List<IconModel> iconModelList;
    private List<ModuleRealmModel> mdl;

    private Context context;
    boolean isConnected;
    private Activity activity;

    EventListener listener;
    public interface EventListener {
        void callAction(String moduleCode);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIcon;
        public ImageButton imgBtnIcon;
        public ImageView imageComingSoon;
        LinearLayout lnIcon;

        public MyViewHolder(View view) {
            super(view);
            tvIcon = view.findViewById(R.id.tvIconHome);
            imgBtnIcon = view.findViewById(R.id.imgBtnIconHome);
            lnIcon = view.findViewById(R.id.lnIconHome);
            imageComingSoon = view.findViewById(R.id.imgComingSoonHome);
        }

    }


    public IconHomeAdapter(List<IconModel> iconModelList, Context context, Boolean isConnected, Activity activity, List<ModuleRealmModel>mdl, EventListener listener) {
        this.context = context;
        this.iconModelList = iconModelList;
        this.isConnected = isConnected;
        this.activity = activity;
        this.mdl = mdl;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_item_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IconModel iconModel = iconModelList.get(position);

        holder.tvIcon.setText(iconModel.getIconTitle());
        holder.imgBtnIcon.setImageResource(iconModel.getIconImage());

        if(iconModel.getIconStatus().equals("C")){
            holder.imageComingSoon.setVisibility(View.VISIBLE);
        }

        holder.lnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callAction(iconModel.getIconCode());
            }
        });

    }

    @Override
    public int getItemCount() {
        return iconModelList.size();
    }


}