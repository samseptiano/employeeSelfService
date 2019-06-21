package com.example.samuelseptiano.employeeselfservice.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxAdapter extends RecyclerView.Adapter<com.example.samuelseptiano.employeeselfservice.Adapter.InboxAdapter.MyViewHolder> {

    private List<InboxChatModel> homeList;
    private Context context;Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFrom,tvMessage, tvNewMessageNotification;

        public MyViewHolder(View view) {
            super(view);
            tvFrom = (TextView) view.findViewById(R.id.tv_from);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            tvNewMessageNotification = (TextView) view.findViewById(R.id.tv_message_new);
        }

    }


    public InboxAdapter(List<InboxChatModel> homeList, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.homeList = homeList;
        this.isConnected = isConnected;
        this.activity = activity;

    }

    @Override
    public com.example.samuelseptiano.employeeselfservice.Adapter.InboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_item_list, parent, false);

        return new com.example.samuelseptiano.employeeselfservice.Adapter.InboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.samuelseptiano.employeeselfservice.Adapter.InboxAdapter.MyViewHolder holder, int position) {
        InboxChatModel home = homeList.get(position);
        holder.tvFrom.setText(home.getFrom());
        holder.tvNewMessageNotification.setText(Integer.toString(home.getTotalMessageNew()));
        holder.tvMessage.setText(home.getMessage());



    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


}
