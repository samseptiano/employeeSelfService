package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.ChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatModel> dataSet = new ArrayList<>();
    Context mContext;

    UserRealmHelper userRealmHelper = new UserRealmHelper(mContext);
    ArrayList<UserRealmModel> usr;

    Activity activity;


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


    public ChatAdapter(List<ChatModel> data, Context context, Activity activity) {
        this.dataSet = data;
        this.mContext=context;
        this.activity=activity;
        usr = userRealmHelper.findAllArticle();

    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.right_chat_bubble, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = activity.getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(activity);
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        return new ChatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.MyViewHolder holder, int position) {
        ChatModel chatModel = dataSet.get(position);
        holder.setIsRecyclable(false);

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