package com.example.samuelseptiano.employeeselfservice.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.ChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ChatAdapter extends RecyclerView.Adapter<com.example.samuelseptiano.employeeselfservice.Adapter.ChatAdapter.MyViewHolder> {

    private List<ChatModel> dataSet = new ArrayList<>();
    Context mContext;

    UserRealmHelper userRealmHelper = new UserRealmHelper(mContext);
    ArrayList<UserRealmModel> usr;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTime, txtTime2, tvDate;
        TextView txtMessage,txtMessage2;
        LinearLayout lnLeftChat, lnRightChat;

        public MyViewHolder(View view) {
            super(view);
            txtMessage = (TextView) view.findViewById(R.id.txt_message);
            txtTime = (TextView) view.findViewById(R.id.txt_time);
            txtMessage2 = (TextView) view.findViewById(R.id.txt_message2);
            txtTime2 = (TextView) view.findViewById(R.id.txt_time2);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            lnLeftChat = (LinearLayout) view.findViewById(R.id.lnLeftChat);
            lnRightChat = (LinearLayout) view.findViewById(R.id.lnRightChat);
        }

    }


    public ChatAdapter(List<ChatModel> data, Context context) {
        this.dataSet = data;
        this.mContext=context;

        usr = userRealmHelper.findAllArticle();

    }

    @Override
    public com.example.samuelseptiano.employeeselfservice.Adapter.ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.right_chat_bubble, parent, false);

        return new com.example.samuelseptiano.employeeselfservice.Adapter.ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.samuelseptiano.employeeselfservice.Adapter.ChatAdapter.MyViewHolder holder, int position) {
        ChatModel chatModel = dataSet.get(position);

        if(position ==0){
            holder.tvDate.setText(chatModel.getSentTime().substring(0,10));
        }
        else if( position>0 && chatModel.getSentTime().substring(0,10).equals(dataSet.get(position-1).getSentTime().substring(0,10))) {
            holder.tvDate.setVisibility(View.GONE);
        }
        else{
            holder.tvDate.setText(chatModel.getSentTime().substring(0,10));
        }

        if(chatModel.getUser().equals(usr.get(0).getUsername())) {
            holder.txtMessage.setText(chatModel.getMessage());
            holder.txtTime.setText(chatModel.getSentTime().substring(11,16));
            holder.lnLeftChat.setVisibility(View.GONE);
        }
        else{
            holder.txtMessage2.setText(chatModel.getMessage());
            holder.txtTime2.setText(chatModel.getSentTime().substring(11,16));
            holder.lnRightChat.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}