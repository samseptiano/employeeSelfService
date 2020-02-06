package com.example.samuelseptiano.employeeselfservice.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.IconPenilaianKinerjaAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.DevelopmentPlanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIStatusTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.SetupPA.SetupPAFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.ModuleRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ChatModel.InboxChatModel;
import com.example.samuelseptiano.employeeselfservice.Model.IconModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.ModuleRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class PenilaianKInerjaFragment extends Fragment implements IconPenilaianKinerjaAdapter.EventListener {

    View rootView;
    private DatabaseReference root;

    private String moduleNamePA="PNKJ-PA-";

    List<IconModel> iconModels = new ArrayList<>();

    Boolean connState;
    Boolean isPAExpand = true;
    Boolean isPJExpand = false;
    LinearLayout lnMyPA,lnMyPaArea, lnMyPJ, lnMyPjArea;
    ImageView imgExpandPA, imgExpandPJ;
    ImageButton btnStatusPA;
    ImageButton btnStartPJ;
    TextView tvNotification;

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    int count=0;

    ArrayList<UserRealmModel> usr = new ArrayList<>();
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());

    UserList userList = new UserList();
    //load icon
    private RecyclerView recyclerViewPA;
    private IconPenilaianKinerjaAdapter iconPenilaianKinerjaAdapter;

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Penilaian Kinerja");
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_penilaian_kinerja, container, false);
        lnMyPA = rootView.findViewById(R.id.lnMyPA);
        lnMyPaArea = rootView.findViewById(R.id.lnMyPAArea);
        imgExpandPA = rootView.findViewById(R.id.imgExpandPA);

        lnMyPJ = rootView.findViewById(R.id.lnMyPJ);
        lnMyPjArea = rootView.findViewById(R.id.lnMyPJArea);
        imgExpandPJ = rootView.findViewById(R.id.imgExpandPJ);

        btnStartPJ = rootView.findViewById(R.id.imgBtnStartPJ);
        tvNotification = rootView.findViewById(R.id.tv_message_count);

        btnStatusPA = rootView.findViewById(R.id.imgBtnStatusPA);

        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();

        //========== icons PA =================
        iconModels=new ArrayList<>();
        recyclerViewPA = (RecyclerView) rootView.findViewById(R.id.rv_icon_pa);
        recyclerViewPA.setLayoutManager(new GridLayoutManager(getContext(), 4));
        //prepareIcons();
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().contains(moduleNamePA) && mdl.get(i).getModuleCode().split("-").length==3) {
                IconModel im = new IconModel();
                im.setIconTitle(mdl.get(i).getModuleName());
                im.setIconCode(mdl.get(i).getModuleCode());
                im.setIconStatus(mdl.get(i).getModuleStatus());
                String name = mdl.get(i).getIcon();
                int id = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                im.setIconImage(id);
                iconModels.add(im);
            }
        }

        usr = userRealmHelper.findAllArticle();
        getAllRoomChat(usr.get(0).getEmpNIK(), mdl, iconModels);
        iconPenilaianKinerjaAdapter = new IconPenilaianKinerjaAdapter(iconModels,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,this);
        recyclerViewPA.setAdapter(iconPenilaianKinerjaAdapter);
        //===============================================



        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Penilaian Kinerja");

        userList = new UserList();

        lnMyPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPAExpand){
                    lnMyPaArea.setVisibility(View.GONE);
                    imgExpandPA.setImageResource(R.drawable.expand_down);
                    isPAExpand=false;
                }
                else {
                    imgExpandPA.setImageResource(R.drawable.expand_up);
                    lnMyPaArea.setVisibility(View.VISIBLE);
                    isPAExpand=true;
                }
            }
        });

        lnMyPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //================ COMING SOON ===============================
//                if(isPJExpand){
//                    lnMyPjArea.setVisibility(View.GONE);
//                    imgExpandPJ.setImageResource(R.drawable.expand_down);
//                    isPJExpand=false;
//                }
//                else {
//                    imgExpandPJ.setImageResource(R.drawable.expand_up);
//                    lnMyPjArea.setVisibility(View.VISIBLE);
//                    isPJExpand=true;
//                }
                //================ COMING SOON ===============================

            }
        });


        btnStartPJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("PA List", getContext(), userList, "",Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnStatusPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new KPIStatusTahunanFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("KPIStatusTahunanFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "PA Status Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });


        root = FirebaseDatabase.getInstance().getReference();
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllRoomChat(usr.get(0).getEmpNIK(),mdl,iconModels);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllRoomChat(usr.get(0).getEmpNIK(),mdl,iconModels);
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

    private void getAllRoomChat(String contains, List<ModuleRealmModel> mdl, List<IconModel>iconModelList){
//        lnProgress2.setVisibility(View.VISIBLE);
        List<InboxChatModel> lInbox = new ArrayList<>();
        root = FirebaseDatabase.getInstance().getReference();

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                    if(dataSnap.getKey().contains(contains)) {
                        InboxChatModel ic = new InboxChatModel();
                        ic.setFrom(dataSnap.getKey());

                        //==============================================
                        root = FirebaseDatabase.getInstance().getReference().child(dataSnap.getKey());
                        Query query = root.orderByChild("status-"+contains).equalTo("UNREAD");

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                List<String> message = new ArrayList<>();
                                List<String> time = new ArrayList<>();
                                List<String> fromPhoto = new ArrayList<>();
                                ic.setFrom(dataSnap.getKey());
                                try {
                                    for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {

                                        try {
                                            message.add(dataSnap.child("message").getValue().toString());
                                            time.add(dataSnap.child("sentTime").getValue().toString());
                                            fromPhoto.add(dataSnap.child("userPhoto").getValue().toString());
//                                            Toast.makeText(getContext(),dataSnap.getKey(),Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            message.add("");
                                            time.add("");
                                            fromPhoto.add("");
                                        }
                                    }
                                }
                                catch (Exception e){
                                    message.add("");
                                    time.add("");
                                    fromPhoto.add("");
                                }
                                ic.setTime(time);
                                ic.setMessage(message);
                                ic.setFromPhoto(fromPhoto);
                                lInbox.add(ic);
                                try {
                                    count=0;
                                    for(int i=0;i<lInbox.size();i++){
                                        if(lInbox.get(i).getMessage()!=null){
                                            count +=lInbox.get(i).getMessage().size();
                                        }
                                    }
                                    if(count==0){
                                        setCountMsg(count);
//                                        iconPenilaianKinerjaAdapter = new IconPenilaianKinerjaAdapter(iconModelList,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,PenilaianKInerjaFragment.this);
//                                        recyclerViewPA.setAdapter(iconPenilaianKinerjaAdapter);
                                            iconPenilaianKinerjaAdapter.notifyDataSetChanged();
                                    }
                                    else {
                                        setCountMsg(count);
//                                        iconPenilaianKinerjaAdapter = new IconPenilaianKinerjaAdapter(iconModelList,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,PenilaianKInerjaFragment.this);
//                                        recyclerViewPA.setAdapter(iconPenilaianKinerjaAdapter);
                                        iconPenilaianKinerjaAdapter.notifyDataSetChanged();

                                    }
                                    //IF DONE...
                                }
                                catch (Exception e){
                                    //Toast.makeText(getContext(),"Message error: "+e.toString(),Toast.LENGTH_LONG).show();

                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        //==============================================
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void callAction(String moduleCode) {
        switch(moduleCode) {
            case "PNKJ-PA-STP":
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new SetupPAFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("SetupPAFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Setup PA", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PNKJ-PA-IDP":
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new DevelopmentPlanFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("DevelopmentPlanFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "Development Plan Area", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PNKJ-PA-SLFAPPR":
                if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("Approve PA tahunan",getContext(),userList,"",Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PNKJ-PA-PATM":
                if(ConnectivityReceiver.isConnected()) {
                    RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                    routingHomeDetailInterface.routingKPI("PA List tahunan", getContext(), userList, "",Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PNKJ-PA-HCCHT":
                if(ConnectivityReceiver.isConnected()) {
                    // code block
                    fr = new ChatInboxFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                    fr.setArguments(bundle3);
                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack("ChatInboxFragment");
                    ft.commit();
                    Toast.makeText(getContext(), "HC Chat", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                }
                break;
            case "PNKJ-PA-FIN":
                //            if(ConnectivityReceiver.isConnected()) {
                //                    // code block
                //                    fr = new FinalPAFragment();
                //                    Bundle bundle3 = new Bundle();
                //                    bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                //                    fr.setArguments(bundle3);
                //                    fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
                //                    ft = fm.beginTransaction();
                //                    ft.replace(R.id.fragment_frame, fr);
                //                    ft.addToBackStack("FinalPAFragment");
                //                    ft.commit();
                //                    Toast.makeText(getContext(), "PA Status Area", Toast.LENGTH_SHORT).show();
                //                }
                //                else{
                //                    Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                //                }
                break;
            default:
                // code block
        }
    }

    @Override
    public int getCountMsg() {
        return count;
    }

    @Override
    public void setCountMsg(int countMsg) {
        count = countMsg;
    }
}
