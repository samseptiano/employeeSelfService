package com.enseval.samuelseptiano.hcservice.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.ChatAdapter.InboxAdapter;
import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.ChatModel.InboxChatModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.facebook.shimmer.ShimmerFrameLayout;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatInboxFragment extends Fragment {

    View rootView;
    ArrayList<UserRealmModel> usr = new ArrayList<>();
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
    private DatabaseReference root;
    RecyclerView recyclerViewInbox;
    InboxAdapter inboxChatAdapter;
    private List<KPIApproveList> kpiApproveLists = new ArrayList<>();
    LinearLayout lnProgress;
    ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_chat_inbox, container, false);
        recyclerViewInbox = rootView.findViewById(R.id.rv_chat_inbox);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewInbox.setLayoutManager(mLayoutManager);
        lnProgress = rootView.findViewById(R.id.linlaChatProgress);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);

        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("HC Chat");

        //mShimmerViewContainer.startShimmerAnimation();
        lnProgress.setVisibility(View.VISIBLE);
        ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
        Call<List<KPIApproveList>> call = apiService.getuserList(usr.get(0).getEmpNIK(),Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<KPIApproveList>>() {
            @Override
            public void onResponse(Call<List<KPIApproveList>> call, Response<List<KPIApproveList>> response) {
                int statusCode = response.code();
                kpiApproveLists = new ArrayList<>();
                if(response.body()!=null) {
                    KPIApproveList kpiApproveList = new KPIApproveList();
                    kpiApproveList.setEmpName(usr.get(0).getNamaAtasanLangsung());
                    kpiApproveList.setNIK(usr.get(0).getNikAtasanLangsung());
                    kpiApproveList.setJobTitle(usr.get(0).getJobTitleAtasan1());
                    kpiApproveList.setEmpPhoto(usr.get(0).getFotoAtasanLangsung());
                    kpiApproveLists.add(kpiApproveList);

                    kpiApproveList = new KPIApproveList();
                    kpiApproveList.setEmpName(usr.get(0).getNamaAtasanTakLangsung());
                    kpiApproveList.setNIK(usr.get(0).getNikAtasanTakLangsung());
                    kpiApproveList.setJobTitle(usr.get(0).getJobTitleAtasan2());
                    kpiApproveList.setEmpPhoto(usr.get(0).getFotoAtasanTakLangsung());
                    kpiApproveLists.add(kpiApproveList);

                    kpiApproveLists.addAll(response.body());
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                    getAllRoomChat(usr.get(0).getEmpNIK(), kpiApproveLists);
//                    }
//                }, 1000);
                }
                else{
                    KPIApproveList kpiApproveList = new KPIApproveList();
                    kpiApproveList.setEmpName(usr.get(0).getNamaAtasanLangsung());
                    kpiApproveList.setNIK(usr.get(0).getNikAtasanLangsung());
                    kpiApproveList.setJobTitle(usr.get(0).getJobTitleAtasan1());
                    kpiApproveList.setEmpPhoto(usr.get(0).getFotoAtasanLangsung());
                    kpiApproveLists.add(kpiApproveList);

                    kpiApproveList = new KPIApproveList();
                    kpiApproveList.setEmpName(usr.get(0).getNamaAtasanTakLangsung());
                    kpiApproveList.setNIK(usr.get(0).getNikAtasanTakLangsung());
                    kpiApproveList.setJobTitle(usr.get(0).getJobTitleAtasan2());
                    kpiApproveList.setEmpPhoto(usr.get(0).getFotoAtasanTakLangsung());
                    kpiApproveLists.add(kpiApproveList);

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                    getAllRoomChat(usr.get(0).getEmpNIK(), kpiApproveLists);
                }
                lnProgress.setVisibility(View.GONE);
                //mShimmerViewContainer.stopShimmerAnimation();
                //mShimmerViewContainer.setVisibility(View.GONE);

            }
            @Override
            public void onFailure(Call<List<KPIApproveList>> call, Throwable t) {
                Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                lnProgress.setVisibility(View.GONE);
                //mShimmerViewContainer.stopShimmerAnimation();
               // mShimmerViewContainer.setVisibility(View.GONE);

            }
        });
        root = FirebaseDatabase.getInstance().getReference();

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                getAllRoomChat(usr.get(0).getEmpNIK(), kpiApproveLists);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getAllRoomChat(usr.get(0).getEmpNIK(), kpiApproveLists);
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


    private void getAllRoomChat(String contains, List<KPIApproveList> kpiApproveLists){
//        lnProgress2.setVisibility(View.VISIBLE);
        lnProgress.setVisibility(View.VISIBLE);
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
                                    for(int i=0;i<kpiApproveLists.size();i++){
                                        for (int j=0;j<lInbox.size();j++){
                                            if(lInbox.get(j).getFrom().contains(kpiApproveLists.get(i).getNIK())){
                                                kpiApproveLists.get(i).setMessage(lInbox.get(j).getMessage());
                                                kpiApproveLists.get(i).setTime(lInbox.get(j).getTime());
                                            }
                                        }
                                    }
                                    inboxChatAdapter = new InboxAdapter(lInbox,kpiApproveLists, getContext(), ConnectivityReceiver.isConnected(), getActivity());
                                    recyclerViewInbox.setAdapter(inboxChatAdapter);
                                    lnProgress.setVisibility(View.GONE);
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                                    lnProgress.setVisibility(View.GONE);

                                }

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                lnProgress.setVisibility(View.GONE);
                            }
                        });
                        //==============================================
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                lnProgress.setVisibility(View.GONE);
            }
        });
        inboxChatAdapter = new InboxAdapter(lInbox,kpiApproveLists, getContext(), ConnectivityReceiver.isConnected(), getActivity());
        recyclerViewInbox.setAdapter(inboxChatAdapter);
        lnProgress.setVisibility(View.GONE);
    }

}
