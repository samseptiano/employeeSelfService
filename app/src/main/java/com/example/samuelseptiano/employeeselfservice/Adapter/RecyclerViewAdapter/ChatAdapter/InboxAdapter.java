package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.List;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private List<InboxChatModel> homeList;
    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFrom,tvMessage, tvNewMessageNotification, tvtime;
        LinearLayout lnInbox;

        public MyViewHolder(View view) {
            super(view);
            tvFrom = (TextView) view.findViewById(R.id.tv_from);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            tvNewMessageNotification = (TextView) view.findViewById(R.id.tv_message_new);
            tvtime = (TextView) view.findViewById(R.id.tv_time);
            lnInbox = view.findViewById(R.id.lnInbox);

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
        String year = "";

        if(words.length>3){
            year = "(tahunan)";
        }

                                                                                                                                                                                         if(words[0].equals(usr.get(0).getEmpNIK())){

            if(words[2].equals(usr.get(0).getEmpName())){
                holder.tvFrom.setText(words[3]+" "+year);
            }
            else if(words[3].equals(usr.get(0).getEmpName())){
                holder.tvFrom.setText(words[2]+" "+year);
            }
        }
        else if(words[1].equals(usr.get(0).getEmpNIK())){
            if(words[2].equals(usr.get(0).getEmpName())){
                holder.tvFrom.setText(words[3]+" "+year);
            }
            else if(words[3].equals(usr.get(0).getEmpName())){
                holder.tvFrom.setText(words[2]+" "+year);
            }
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

        holder.lnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(words[0].equals(usr.get(0).getEmpNIK())){
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    //routingHomeDetailInterface.routingChat(home.getFrom(),words[2], words[1],"","","","",context);
                }
                else{
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    //routingHomeDetailInterface.routingChat(home.getFrom(),words[2], words[0],"","","","",context);
                }


            }
        });




    }

    @Override
    public int getItemCount() {
        return homeList.size();
    }


}
