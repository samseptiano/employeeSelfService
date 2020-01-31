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

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.PenilaianKinerjaIconAdapter;
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


public class PenilaianKInerjaFragment extends Fragment implements PenilaianKinerjaIconAdapter.EventListener {

    View rootView;
    private DatabaseReference root;

    private String moduleNamePA="PNKJ-PA-";

    List<IconModel> iconModels = new ArrayList<>();

    Boolean connState;
    public Boolean getConnState() {
        return connState;
    }
    Boolean isPAExpand = true;
    Boolean isPJExpand = false;
    LinearLayout lnMyPA,lnMyPaArea, lnMyPJ, lnMyPjArea;
    ImageView imgExpandPA, imgExpandPJ;
    ImageButton btnStartPA, btnStatusPA, btnFinalPA, btnDevelopmentPlan, btnSelfAppraisal, btnHCChat, btnSetupPa;
    ImageButton btnStartPJ, btnStatusPJ, btnFinalPJ, btnDevelopmentPlanPJ;
    TextView tvNotification;

    LinearLayout lnSetupPA,lnHcChat,lnAP,lnTeamPA, lnSelfAppraisal,lnIDP,lnFinalPA;
    ImageView imgLockSetupPA,imgLockHcChat,imgLockTeamPA, imgLockSelfAppraisal,imgLockIDP;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    ArrayList<UserRealmModel> usr = new ArrayList<>();
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());

    UserList userList = new UserList();
    //load icon
    private RecyclerView recyclerViewPA;
    private PenilaianKinerjaIconAdapter penilaianKinerjaIconAdapter;

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
        btnSelfAppraisal = rootView.findViewById(R.id.imgBtnSelfAppraisal);
        btnStartPA = rootView.findViewById(R.id.imgBtnStartPA);
        btnStatusPA = rootView.findViewById(R.id.imgBtnStatusPA);
        btnFinalPA = rootView.findViewById(R.id.imgBtnFinalPA);
        btnDevelopmentPlan = rootView.findViewById(R.id.imgBtnDevelopmentPlan);
        btnHCChat = rootView.findViewById(R.id.imgBtnInstantFeedback);
        btnSetupPa = rootView.findViewById(R.id.imgBtnSetupPA);

        imgLockSetupPA = rootView.findViewById(R.id.imgLockSetupPA);
        imgLockHcChat = rootView.findViewById(R.id.imgLockHCChat);
        imgLockIDP = rootView.findViewById(R.id.imgLockIDP);
        imgLockSelfAppraisal = rootView.findViewById(R.id.imgLockSelfAppraisal);
        imgLockTeamPA = rootView.findViewById(R.id.imgLockStartPA);


        lnSetupPA = rootView.findViewById(R.id.lnSetupPA);
        lnSelfAppraisal = rootView.findViewById(R.id.lnSelfAppraisal);
        lnTeamPA = rootView.findViewById(R.id.lnPATeam);
        lnFinalPA = rootView.findViewById(R.id.lnFinalPA);
        lnIDP = rootView.findViewById(R.id.lnIdp);
        lnHcChat = rootView.findViewById(R.id.lnHcChat);
        lnAP = rootView.findViewById(R.id.lnAP);

        ModuleRealmHelper moduleRealmHelper = new ModuleRealmHelper(getContext());
        ArrayList<ModuleRealmModel> mdl;
        mdl = moduleRealmHelper.findAllArticle();


        //========== icons PA =================
        iconModels=new ArrayList<>();
        recyclerViewPA = (RecyclerView) rootView.findViewById(R.id.rv_icon_pa);
        recyclerViewPA.setLayoutManager(new GridLayoutManager(getContext(), 4));
        //prepareIcons();
        for(int i=0;i<mdl.size();i++) {
            if(mdl.get(i).getModuleCode().contains(moduleNamePA)) {
                IconModel im = new IconModel();
                im.setIconTitle(mdl.get(i).getModuleName());
                im.setIconCode(mdl.get(i).getModuleCode());
                String name = mdl.get(i).getIcon();
                int id = getResources().getIdentifier(name, "drawable", getContext().getPackageName());
                im.setIconImage(id);
                iconModels.add(im);
            }
        }

        penilaianKinerjaIconAdapter = new PenilaianKinerjaIconAdapter(iconModels,getContext(), ConnectivityReceiver.isConnected(), getActivity(), mdl,this);
        recyclerViewPA.setAdapter(penilaianKinerjaIconAdapter);
        //===============================================



        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Penilaian Kinerja");
        usr = userRealmHelper.findAllArticle();
//
        userList = new UserList();
        getAllRoomChat(usr.get(0).getEmpNIK());


        for(int i=0;i<mdl.size();i++) {

            if (mdl.get(i).getModuleCode().equals("PNKJ-PA-STP")) {
                lnSetupPA.setVisibility(View.VISIBLE);
                lnSetupPA.setAlpha((float) 1);
                btnSetupPa.setClickable(true);
                btnSetupPa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                    }
                });
                imgLockSetupPA.setVisibility(View.GONE);
            }
        }

        for(int i=0;i<mdl.size();i++) {
            if (mdl.get(i).getModuleCode().equals("PNKJ-PA-SLFAPPR")) {
                lnSelfAppraisal.setVisibility(View.VISIBLE);
                lnSelfAppraisal.setAlpha((float) 1);
                btnSelfAppraisal.setClickable(true);
                btnSelfAppraisal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ConnectivityReceiver.isConnected()) {
                            RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            routingHomeDetailInterface.routingKPI("Approve PA tahunan",getContext(),userList,"",Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        }
                        else{
                            Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                imgLockSelfAppraisal.setVisibility(View.GONE);
            }
        }

        for(int i=0;i<mdl.size();i++) {
            if (mdl.get(i).getModuleCode().equals("PNKJ-PA-PATM")) {
                lnTeamPA.setVisibility(View.VISIBLE);
                lnTeamPA.setAlpha((float) 1);
                btnStartPA.setEnabled(true);
                btnStartPA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ConnectivityReceiver.isConnected()) {
                            RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                            routingHomeDetailInterface.routingKPI("PA List tahunan", getContext(), userList, "",Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
                        }
                        else{
                            Toast.makeText(getContext(),"Sorry! Not connected to internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                imgLockTeamPA.setVisibility(View.GONE);
            }
        }

        for(int i=0;i<mdl.size();i++) {
            if (mdl.get(i).getModuleCode().equals("PNKJ-PA-IDP")) {
                lnIDP.setVisibility(View.VISIBLE);
                lnIDP.setAlpha((float) 1);
                btnDevelopmentPlan.setClickable(true);
                btnDevelopmentPlan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    }
                });
                imgLockIDP.setVisibility(View.GONE);
            }
        }


        for(int i=0;i<mdl.size();i++) {
            if (mdl.get(i).getModuleCode().equals("PNKJ-PA-HCCHT")) {
                lnHcChat.setVisibility(View.VISIBLE);
                lnHcChat.setAlpha((float) 1);
                btnHCChat.setEnabled(true);
                btnHCChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    }
                });
                imgLockHcChat.setVisibility(View.GONE);
            }
        }



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

        btnFinalPA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ConnectivityReceiver.isConnected()) {
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
            }
        });


        root = FirebaseDatabase.getInstance().getReference();

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllRoomChat(usr.get(0).getEmpNIK());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllRoomChat(usr.get(0).getEmpNIK());
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

    private void getAllRoomChat(String contains ){
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
                                    int count = 0;
                                    for(int i=0;i<lInbox.size();i++){
                                        if(lInbox.get(i).getMessage()!=null){
                                            count +=lInbox.get(i).getMessage().size();
                                        }
                                    }
                                    if(count==0){
                                        tvNotification.setVisibility(View.GONE);
                                    }
                                    else {
                                        tvNotification.setText(Integer.toString(count));
                                        tvNotification.setVisibility(View.VISIBLE);
                                    }
                                    //IF DONE...
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();

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
}
