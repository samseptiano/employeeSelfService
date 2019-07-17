package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private List<InboxChatModel> homeList;
    private Context context;Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFrom,tvMessage, tvNewMessageNotification, tvtime;

        public MyViewHolder(View view) {
            super(view);
            tvFrom = (TextView) view.findViewById(R.id.tv_from);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            tvNewMessageNotification = (TextView) view.findViewById(R.id.tv_message_new);
            tvtime = (TextView) view.findViewById(R.id.tv_time);

        }

    }


    public InboxAdapter(List<InboxChatModel> homeList, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.homeList = homeList;
        this.isConnected = isConnected;
        this.activity = activity;

    }

    @Override
    public InboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_item_list, parent, false);

        return new InboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InboxAdapter.MyViewHolder holder, int position) {
        InboxChatModel home = homeList.get(position);
        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        List<UserRealmModel> usr = userRealmHelper.findAllArticle();

        String s1=home.getFrom();
        String[] words=s1.split("-");

        if(words[0].equals(usr.get(0).getEmpNIK())){
            holder.tvFrom.setText(words[1]);
        }
        else if(words[1].equals(usr.get(0).getEmpNIK())){
            holder.tvFrom.setText(words[0]);
        }

        try {
            holder.tvtime.setText(home.getTime().get(home.getMessage().size() - 1).substring(11,16));
            holder.tvNewMessageNotification.setText(Integer.toString(home.getMessage().size()));
            if (home.getMessage().get(home.getMessage().size() - 1).length() > 20) {
                holder.tvMessage.setText(home.getMessage().get(home.getMessage().size() - 1).substring(0, 20));
            } else {
                holder.tvMessage.setText(home.getMessage().get(home.getMessage().size() - 1).toString());
            }
        }
        catch (Exception e){
            holder.tvtime.setText("");
            holder.tvNewMessageNotification.setVisibility(View.GONE);
            holder.tvMessage.setText("");

        }




    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


}
