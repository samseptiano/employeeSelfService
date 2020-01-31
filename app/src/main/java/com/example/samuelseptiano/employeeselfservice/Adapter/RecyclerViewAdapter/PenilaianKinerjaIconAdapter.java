package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Fragment.PenilaianKInerjaFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.IconModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.ModuleRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Locale;

public class PenilaianKinerjaIconAdapter extends RecyclerView.Adapter<PenilaianKinerjaIconAdapter.MyViewHolder> {

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
        LinearLayout lnIcon;

        public MyViewHolder(View view) {
            super(view);
            tvIcon = view.findViewById(R.id.tvIcon);
            imgBtnIcon = view.findViewById(R.id.imgBtnIcon);
            lnIcon = view.findViewById(R.id.lnIcon);
        }

    }


    public PenilaianKinerjaIconAdapter(List<IconModel> iconModelList, Context context, Boolean isConnected, Activity activity, List<ModuleRealmModel>mdl, EventListener listener) {
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