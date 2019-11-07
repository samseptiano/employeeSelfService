package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter.ChatAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.ChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private String user_name ,room_name, friend_name,friend_nik, user_nik, kualitatif, kuantitatif, tahun, semester;
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
            kualitatif = bundle.getString("kualitatif");
            kuantitatif = bundle.getString("kuantitatif");
            tahun = bundle.getString("tahun");
            semester = bundle.getString("semester");
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_send_msg = (Button) rootView.findViewById(R.id.button);
        input_msg = (EditText)rootView.findViewById(R.id.editText);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cchat);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);
//        ((GridLayoutManager) mLayoutManager).setStackFromEnd(true);

        //((GridLayoutManager) mLayoutManager).setReverseLayout(true);



        readChat();

        //setTitle("Room - "+room_name);

        root = FirebaseDatabase.getInstance().getReference().child(room_name);
        //root.orderByChild("sentTime");



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
                    map2.put("kuantitatif",kuantitatif);
                    map2.put("kualitatif",kualitatif);
                    map2.put("tahun",tahun);
                    map2.put("semester",semester);

                    message_root.updateChildren(map2);
                }
                input_msg.setText("");
                closeKeyboard();


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
            chatModel.setKualtatif((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setKuantitatif((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setMessage((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setSemester((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setSentTime((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setStatus1((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setStatus2((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setTahun((String) ((DataSnapshot)i.next()).getValue());
            chatModel.setUser((String) ((DataSnapshot)i.next()).getValue());

            lChat.add(chatModel);

            chat.add(chat_user_name + " : " + chat_msg);
            //Collections.reverse(lChat);
            hAdapter = new ChatAdapter(lChat, getContext(), getActivity());
            recyclerView.setAdapter(hAdapter);
            recyclerView.scrollToPosition(hAdapter.getItemCount() - 1);

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

    private void closeKeyboard(){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                //Find the currently focused view, so we can grab the correct window token from it.
                View view = getActivity().getCurrentFocus();
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (view == null) {
                    view = new View(getActivity());
                }
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
