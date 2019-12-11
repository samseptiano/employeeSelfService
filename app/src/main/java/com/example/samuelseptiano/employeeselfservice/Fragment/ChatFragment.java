package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter.ChatAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.ChatAdapter.InboxAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.ChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ChatFragment extends Fragment implements ChatAdapter.EventListener{

    View rootView;

    List<String> KPIhistories = new ArrayList<>();
    List<String> KPIhistoriesLastYear = new ArrayList<>();
    List<String> KPIhistoriesLastTwoYear = new ArrayList<>();

    ArrayAdapter<String> dataAdapter;

    private List<String> KPIItems;
    private Button btn_send_msg;
    private EditText input_msg;
    private String user_name ,room_name, friend_name,friend_nik, friend_branchName,friend_dept,friend_job_title, user_nik, kualitatif, kuantitatif, semester;

    private String tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
    private DatabaseReference root,root2;
    private String temp_key;
    RecyclerView recyclerView;
    ChatAdapter hAdapter;
    TextView tvEmpName, tvJobTitle, tvDept;
    ImageView imgPhoto;
    private MaterialSpinner spinnerKPIItems;

    private String kpiItem="Overall Performance";

    private static final int MENU_THIS_YEAR = Menu.FIRST;
    private static final int MENU_ALL = Menu.FIRST + 1;
    private int MENU_ALL_INC=0;
    private int MENU_ALL_INC2=0;


    protected boolean mIsVisibleToUser;

    List<ChatModel> lChat = new ArrayList<>();
    List<String> chat = new ArrayList<>();

    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    ArrayList<UserRealmModel> usr;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        usr = userRealmHelper.findAllArticle();
        user_name = usr.get(0).getEmpName();
        user_nik = usr.get(0).getEmpNIK();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            room_name = bundle.getString("room_name");
            friend_name = bundle.getString("friend_name");
            friend_nik = bundle.getString("friend_nik");
            friend_branchName = bundle.getString("branchName");
            friend_dept = bundle.getString("dept");
            friend_job_title = bundle.getString("job_title");

            kualitatif = bundle.getString("kualitatif");
            kuantitatif = bundle.getString("kuantitatif");
            tahun = bundle.getString("tahun");
            semester = bundle.getString("semester");
            KPIItems = (List<String>) bundle.getSerializable("KPIItems");
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
                if(kuantitatif != ""){
                    spinnerKPIItems.setSelectedIndex(dataAdapter.getPosition(kuantitatif));
                }
                else if( kualitatif != ""){
                    spinnerKPIItems.setSelectedIndex(dataAdapter.getPosition(kualitatif));
                }

            } else {


            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
//        MenuCompat.setGroupDividerEnabled(menu, true);
        super.onCreateOptionsMenu(menu, inflater);

//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//
//        MenuItem item = menu.findItem(R.id.menu_spinner1);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.fruits, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.clear();

//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//
//        MenuItem item = menu.findItem(R.id.menu_spinner1);
//        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.fruits, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);
//
//         item = menu.findItem(R.id.menu_spinner1);
//         spinner = (Spinner) MenuItemCompat.getActionView(item);
//
//        adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.fruits, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spinner.setAdapter(adapter);

        //==================================================================================

        Set<String> set = new HashSet<>(KPIhistories);
        KPIhistories.clear();
        KPIhistories.addAll(set);
        Set<String> set2 = new HashSet<>(KPIhistoriesLastYear);
        KPIhistoriesLastYear.clear();
        KPIhistoriesLastYear.addAll(set2);
        Set<String> set3 = new HashSet<>(KPIhistoriesLastTwoYear);
        KPIhistoriesLastTwoYear.clear();
        KPIhistoriesLastTwoYear.addAll(set3);
        SubMenu sub = menu.addSubMenu(0, MENU_THIS_YEAR, Menu.CATEGORY_SYSTEM, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
            //menu.add(0, MENU_THIS_YEAR, Menu.NONE, Integer.toString(Calendar.getInstance().get(Calendar.YEAR))).setIcon(R.drawable.user);
            sub.add(0, MENU_ALL, Menu.NONE, "All");
            for(int i=1;i<KPIhistories.size()+1;i++){
                sub.add(0, MENU_ALL+i, Menu.NONE, KPIhistories.get(i-1));
                MENU_ALL_INC = MENU_ALL+i;
            }
        SubMenu subLYear = menu.addSubMenu(1, MENU_ALL_INC+1, Menu.NONE, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1));
            subLYear.add(1, MENU_ALL_INC+1, Menu.NONE, "All");
            for(int i=0;i<KPIhistoriesLastYear.size();i++){
                subLYear.add(1, MENU_ALL_INC+1, Menu.NONE, KPIhistoriesLastYear.get(i));
            }
        SubMenu subLTwoYear = menu.addSubMenu(2, MENU_ALL_INC+1, Menu.NONE, Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2));
        subLTwoYear.add(2, MENU_ALL_INC+1, Menu.NONE, "All");
        for(int i=0;i<KPIhistoriesLastTwoYear.size();i++){
            subLTwoYear.add(2, MENU_ALL_INC+1, Menu.NONE, KPIhistoriesLastTwoYear.get(i));
        }

        //return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

//        switch (item.getItemId()) {
//            case MENU_THIS_YEAR:
//                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
//                kpiItem = "Overall Performance";
//                break;
//            case MENU_ALL:
//                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
//                kpiItem = "Overall Performance";
//                break;
//
//        }
        if(item.getGroupId() == 0) {
            if (item.getItemId() == MENU_ALL) {
                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                kpiItem = "Overall Performance";
//                Toast.makeText(getContext(), kpiItem, Toast.LENGTH_LONG).show();
                MENU_ALL_INC2 = MENU_ALL;
                hAdapter.notifyDataSetChanged();

            }
            for (int i = 1; i <= KPIhistories.size(); i++) {
                if (item.getItemId() == MENU_ALL + i) {
                    tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                    kpiItem = KPIhistories.get(i - 1);
                    Toast.makeText(getContext(), KPIhistories.get(i - 1), Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), tahun, Toast.LENGTH_LONG).show();
                    MENU_ALL_INC2 = MENU_ALL + i;
                    hAdapter.notifyDataSetChanged();
                }
            }
        }
        if(item.getGroupId() == 1) {
            tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1);
            int items=0;
            if (item.getTitle().equals("All")) {
                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1);
                kpiItem = "Overall Performance";
                Toast.makeText(getContext(), kpiItem, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), tahun, Toast.LENGTH_LONG).show();
                items= item.getItemId();

                hAdapter.notifyDataSetChanged();
            }
            for (int i = 0; i < KPIhistoriesLastYear.size(); i++) {
                if (item.getTitle().equals(KPIhistoriesLastYear.get(i))) {
                    tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR) - 1);
                    kpiItem = KPIhistoriesLastYear.get(i);
                    Toast.makeText(getContext(), KPIhistoriesLastYear.get(i), Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), tahun, Toast.LENGTH_LONG).show();
                    hAdapter.notifyDataSetChanged();
                }
            }
        }
        if(item.getGroupId() == 2) {
            tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2);
            int itemss=0;
            if (item.getTitle().equals("All")) {
                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2);
                kpiItem = "Overall Performance";
                Toast.makeText(getContext(), kpiItem, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), tahun, Toast.LENGTH_LONG).show();
                itemss= item.getItemId();
                hAdapter.notifyDataSetChanged();

            }
            for (int i = 0; i < KPIhistoriesLastTwoYear.size(); i++) {
                if (item.getTitle().equals(KPIhistoriesLastTwoYear.get(i))) {
                    tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR) - 2);
                    kpiItem = KPIhistoriesLastTwoYear.get(i);
                    Toast.makeText(getContext(), KPIhistoriesLastTwoYear.get(i), Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), tahun, Toast.LENGTH_LONG).show();
                    hAdapter.notifyDataSetChanged();
                }
            }
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_send_msg = (Button) rootView.findViewById(R.id.button);
        input_msg = (EditText)rootView.findViewById(R.id.editText);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_cchat);
        spinnerKPIItems = rootView.findViewById(R.id.spinnerKPIItems);
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        tvJobTitle = rootView.findViewById(R.id.tv_job_title);
        tvDept = rootView.findViewById(R.id.tv_dept);
        tvEmpName = rootView.findViewById(R.id.tv_employeeName);
        imgPhoto = rootView.findViewById(R.id.imgEmpPhoto);

        tvEmpName.setText(friend_name);
        tvJobTitle.setText(friend_job_title);
        tvDept.setText(friend_branchName);


        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, KPIItems);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerKPIItems.setAdapter(dataAdapter);

        if(kuantitatif != ""){
            int spinnerPosition = dataAdapter.getPosition(kuantitatif);
            spinnerKPIItems.setSelectedIndex(spinnerPosition);
        }
        if( kualitatif != ""){
            int spinnerPosition2 = dataAdapter.getPosition(kualitatif);
            spinnerKPIItems.setSelectedIndex(spinnerPosition2);
        }

        spinnerKPIItems.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                kpiItem = spinnerKPIItems.getItems().get(spinnerKPIItems.getSelectedIndex()).toString();
                hAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);

//        ((GridLayoutManager) mLayoutManager).setStackFromEnd(true);

        //((GridLayoutManager) mLayoutManager).setReverseLayout(true);



        readChat();
        getAllRoomChat();

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
            hAdapter = new ChatAdapter(lChat, getContext(), getActivity(),this);
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

    @Override
    public String getKPIItem() {
        return kpiItem;
    }

    @Override
    public String getTahun() {
        return tahun;
    }


    private void getAllRoomChat(){
        root2 = FirebaseDatabase.getInstance().getReference();
        root2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    if(dataSnap.getKey().contains(usr.get(0).getEmpNIK())) {
                        //Toast.makeText(getContext(),dataSnap.getKey(),Toast.LENGTH_LONG).show();
                        //==============================================
                        root2 = FirebaseDatabase.getInstance().getReference().child(dataSnap.getKey());

                        Query queryLastYear = root2.orderByChild("tahun").equalTo(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1));
                        queryLastYear.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                        try {
                                            if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1)) && dataSnap.child("kuantitatif").getValue().toString().equals("")){
                                                KPIhistoriesLastYear.add(dataSnap.child("kualitatif").getValue().toString());
                                            }
                                            else if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1)) && dataSnap.child("kualitatif").getValue().toString().equals("")){
                                                KPIhistoriesLastYear.add(dataSnap.child("kuantitatif").getValue().toString());
                                            }
//                                            Toast.makeText(getContext(),dataSnap.child("kuantitatif").getValue().toString(),Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                                catch (Exception e){
                                }
                                try {
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        Query query = root2.orderByChild("tahun").equalTo(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                        try {
                                            if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))) && dataSnap.child("kuantitatif").getValue().toString().equals("")){
                                                KPIhistories.add(dataSnap.child("kualitatif").getValue().toString());
                                            }
                                            else if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR))) && dataSnap.child("kualitatif").getValue().toString().equals("")){
                                                KPIhistories.add(dataSnap.child("kuantitatif").getValue().toString());
                                            }
//                                            Toast.makeText(getContext(),dataSnap.child("kuantitatif").getValue().toString(),Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                                catch (Exception e){
                                }
                                try {
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });



                        Query queryLastTwoYear = root2.orderByChild("tahun").equalTo(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2));
                        queryLastTwoYear.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                try {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                                        try {
                                            if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2)) && dataSnap.child("kuantitatif").getValue().toString().equals("")){
                                                KPIhistoriesLastTwoYear.add(dataSnap.child("kualitatif").getValue().toString());
                                            }
                                            else if(dataSnap.child("tahun").getValue().equals(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-2)) && dataSnap.child("kualitatif").getValue().toString().equals("")){
                                                KPIhistoriesLastTwoYear.add(dataSnap.child("kuantitatif").getValue().toString());
                                            }
//                                            Toast.makeText(getContext(),dataSnap.child("kuantitatif").getValue().toString(),Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                                catch (Exception e){
                                }
                                try {
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }




}
