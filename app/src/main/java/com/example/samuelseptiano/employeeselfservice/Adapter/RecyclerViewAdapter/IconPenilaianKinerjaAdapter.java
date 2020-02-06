package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Model.IconModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.ModuleRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.List;

public class IconPenilaianKinerjaAdapter extends RecyclerView.Adapter<IconPenilaianKinerjaAdapter.MyViewHolder> {

    private List<IconModel> iconModelList;
    private List<ModuleRealmModel> mdl;

    int count=0;

    private Context context;
    boolean isConnected;
    private Activity activity;

    EventListener listener;
    public interface EventListener {
        void callAction(String moduleCode);
        int getCountMsg();
        void setCountMsg(int countMsg);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIcon, tvNotifMessage;
        public ImageButton imgBtnIcon;
        public ImageView imageComingSoon;
        LinearLayout lnIcon;

        public MyViewHolder(View view) {
            super(view);
            tvIcon = view.findViewById(R.id.tvIcon);
            tvNotifMessage = view.findViewById(R.id.tv_notifMessage);
            imgBtnIcon = view.findViewById(R.id.imgBtnIcon);
            lnIcon = view.findViewById(R.id.lnIcon);
            imageComingSoon = view.findViewById(R.id.imgComingSoon);
        }

    }


    public IconPenilaianKinerjaAdapter(List<IconModel> iconModelList, Context context, Boolean isConnected, Activity activity, List<ModuleRealmModel>mdl, EventListener listener) {
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
                .inflate(R.layout.icon_item, parent, false);

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
        else{
            holder.imageComingSoon.setVisibility(View.GONE);
        }
//        Toast.makeText(context,listener.getCountMsg()+"",Toast.LENGTH_LONG).show();
        if(iconModel.getIconCode().equals("PNKJ-PA-HCCHT") && listener.getCountMsg()>0){
            holder.tvNotifMessage.setText(Integer.toString(listener.getCountMsg()));
            holder.tvNotifMessage.setVisibility(View.VISIBLE);
        }
        else{
            holder.tvNotifMessage.setVisibility(View.GONE);
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