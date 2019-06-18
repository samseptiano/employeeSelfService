package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.ChatAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.ChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class ChatFragment extends Fragment {

    View rootView;

    private Button btn_send_msg;
    private EditText input_msg;
    private String user_name ,room_name, friend_name,friend_nik, user_nik;
    private DatabaseReference root;
    private String temp_key;
    RecyclerView recyclerView;
    ChatAdapter hAdapter;

    protected boolean mIsVisibleToUser;

    List<ChatModel> lChat = new ArrayList<>();
    List<String> chat = new ArrayList<>();

    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    ArrayList<UserRealmModel> usr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usr = userRealmHelper.findAllArticle();
        user_name = usr.get(0).getUsername();
        user_nik = usr.get(0).getEmpNIK();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            room_name = bundle.getString("room_name");
            friend_name = bundle.getString("friend_name");
            friend_nik = bundle.getString("friend_nik");
        }



    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;

//        lnProgressbar.setVisibility(View.VISIBLE);
        if (isResumed()) { // fragment have created
            if (mIsVisibleToUser) {
               readChat();
            } else {


            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle(friend_name);

        btn_send_msg = (Button) rootView.findViewById(R.id.button);
        input_msg = (EditText)rootView.findViewById(R.id.editText);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cchat);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);

        readChat();

        //setTitle("Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);


        btn_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!input_msg.getText().toString().equals("")) {
                Map<String,Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();

                    DatabaseReference message_root = root.child(temp_key);
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("user", user_name);
                    map2.put("message", input_msg.getText().toString());
                    map2.put("sentTime", dateFormat.format(date).toString());
                    map2.put("status-"+user_nik, "READ");
                    map2.put("status-"+friend_nik, "UNREAD");

                    message_root.updateChildren(map2);
                }
                input_msg.setText("");



            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                append_chat_conversatin(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                append_chat_conversatin(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    private String chat_msg, chat_user_name, chat_time, chat_status;

    private void append_chat_conversatin(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext())
        {
            ChatModel chatModel = new ChatModel();
            chatModel.setMessage((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setSentTime((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setStatus1((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setStatus2((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setUser((String) ((DataSnapshot)i.next()).getValue());
            lChat.add(chatModel);

            chat.add(chat_user_name + " : " + chat_msg);
            hAdapter = new ChatAdapter(lChat, getContext());
            recyclerView.setAdapter(hAdapter);
        }

//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot ds : dataSnapshot.getChildren()) {
//                    ChatModel chatModel = new ChatModel();
//                    chatModel.setMessage((String) ds.child("message").getValue());
//                    chatModel.setSentTime((String) ds.child("sentTime").getValue());
//                    chatModel.setStatus1((String)  ds.child("status-181201101").getValue());
//                    chatModel.setStatus2((String) ds.child("status-381201101").getValue());
//                    chatModel.setUser((String) ds.child("user").getValue());
//                    lChat.add(chatModel);
//
//                    chat.add(chat_user_name + " : " + chat_msg);
//                    hAdapter = new ChatAdapter(lChat, getContext());
//                    recyclerView.setAdapter(hAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {}
//        };
//        root.addListenerForSingleValueEvent(eventListener);

    }

    private void readChat(){
        FirebaseDatabase.getInstance()
                .getReference()
                .child(room_name)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                            root.child(dataSnap.getKey()).child("status-"+user_nik).setValue("READ");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

}
