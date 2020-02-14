package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter.ChatAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.enseval.samuelseptiano.hcservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.ChatModel.InboxChatModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Samuel Septiano on 17,June,2019
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private List<InboxChatModel> homeList;
    private List<KPIApproveList> kpiApproveLists;
    private Context context;
    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;
    boolean isConnected;
    private Activity activity;
    UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    ArrayList<UserRealmModel> usr;
    String kualitatif = "";
    String kuantitatif="";
    String friendName = "";
    String foto = "";
    String friendNIK = "";
    String friendBranchName = "";
    String friendDept = "";
    String friendJobTitle = "";
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFrom,tvMessage, tvNewMessageNotification, tvtime;
        LinearLayout lnInbox;
        CircleImageView empPhoto;
        View hrLine;


        public MyViewHolder(View view) {
            super(view);
            tvFrom = (TextView) view.findViewById(R.id.tv_from);
            tvMessage = (TextView) view.findViewById(R.id.tv_message);
            tvNewMessageNotification = (TextView) view.findViewById(R.id.tv_message_new);
            tvtime = (TextView) view.findViewById(R.id.tv_time);
            lnInbox = view.findViewById(R.id.lnInbox);
            empPhoto = view.findViewById(R.id.empPhotoInbox);
            hrLine = view.findViewById(R.id.HrLine2);
        }

    }


    public InboxAdapter(List<InboxChatModel> homeList, List<KPIApproveList> kpiApproveLists, Context context, Boolean isConnected, Activity activity) {
        this.context = context;
        this.homeList = homeList;
        this.isConnected = isConnected;
        this.activity = activity;
        this.kpiApproveLists = kpiApproveLists;

    }

    @Override
    public InboxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_item_list, parent, false);

        return new InboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InboxAdapter.MyViewHolder holder, int position) {
        //InboxChatModel home = homeList.get(position);
        UserRealmHelper userRealmHelper = new UserRealmHelper(context);
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String NIK = usr.get(0).getEmpNIK();

        KPIApproveList kpiApproveList = kpiApproveLists.get(position);

        if(position==1){
            holder.hrLine.setVisibility(View.VISIBLE);
        }

        try {
            holder.tvtime.setText(kpiApproveList.getTime().get(kpiApproveList.getMessage().size() - 1).substring(11,16));
            holder.tvNewMessageNotification.setText(Integer.toString(kpiApproveList.getMessage().size()));
            if (kpiApproveList.getMessage().get(kpiApproveList.getMessage().size() - 1).length() > 20) {
                holder.tvMessage.setText(kpiApproveList.getMessage().get(kpiApproveList.getMessage().size() - 1).substring(0, 20));
            } else {
                holder.tvMessage.setText(kpiApproveList.getMessage().get(kpiApproveList.getMessage().size() - 1).toString());
            }
        }
        catch (Exception e){
            //Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            holder.tvtime.setText("");
            holder.tvNewMessageNotification.setVisibility(View.GONE);
            holder.tvMessage.setText("");
        }

        holder.lnInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            try {
                                holder.lnInbox.setEnabled(false);
                                List <String> KPIItems = new ArrayList<>();

                                KPIItems.add("Overall Performance");
//                        for(int i=0;i< questions.size();i++){
//                            KPIItems.add(questions.get(i).getKPIDesc());
//                        }

                                String nik = usr.get(0).getEmpNIK();
                                String nama = usr.get(0).getEmpName().replace(".", "");
                                String semester = "1";
                                String tahun =  Integer.toString(Calendar.getInstance().get(Calendar.YEAR));



                                //user berperan sebagai atasan langsung
                                if(usr.get(0).getEmpNIK().equals(kpiApproveList.getNIKAtasan1())){
//                                friendName = kpiHeader.getAtasanTakLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanTakLangsung();
                                    friendName = kpiApproveList.getEmpName().replace(".", "");
                                    friendNIK = kpiApproveList.getNIK();
                                    friendBranchName = kpiApproveList.getBranchName();
                                    friendDept = kpiApproveList.getDept();
                                    friendJobTitle = kpiApproveList.getJobTitle();
                                    foto = kpiApproveList.getEmpPhoto();



                                }
                                //user berperan sebagai atasan tak langsung
                                else if(usr.get(0).getEmpNIK().equals(kpiApproveList.getNIKAtasan2())){
//                                friendName = kpiHeader.getAtasanLangsung();
//                                friendNIK = kpiHeader.getNIKAtasanLangsung();
                                    friendName = kpiApproveList.getEmpName().replace(".", "");
                                    friendNIK = kpiApproveList.getNIK();
                                    friendBranchName = kpiApproveList.getBranchName();
                                    friendDept = kpiApproveList.getDept();
                                    friendJobTitle = kpiApproveList.getJobTitle();
                                    foto = kpiApproveList.getEmpPhoto();

                                }
                                // user berperan sebagai karyawan
                                else if(!usr.get(0).getEmpNIK().equals(kpiApproveList.getNIKAtasan1()) && !usr.get(0).getEmpNIK().equals(kpiApproveList.getNIKAtasan2())){
                                    if(kpiApproveList.getEmpName().equals(usr.get(0).getNamaAtasanLangsung())) {
                                        friendName = usr.get(0).getNamaAtasanLangsung().replace(".", "");
                                        friendNIK = usr.get(0).getNikAtasanLangsung();
                                        friendBranchName = usr.get(0).getJobTitleAtasan1();
                                        friendDept = usr.get(0).getJobTitleAtasan1();
                                        friendJobTitle = usr.get(0).getJobTitleAtasan1();
                                        foto = kpiApproveList.getFotoAtasan1();
                                    }
                                    else if(kpiApproveList.getEmpName().equals(usr.get(0).getNamaAtasanTakLangsung())) {
                                        friendName = usr.get(0).getNamaAtasanTakLangsung().replace(".", "");
                                        friendNIK = usr.get(0).getNikAtasanTakLangsung();
                                        friendBranchName = usr.get(0).getJobTitleAtasan2();
                                        friendDept = usr.get(0).getJobTitleAtasan2();
                                        friendJobTitle = usr.get(0).getJobTitleAtasan2();
                                        foto = kpiApproveList.getFotoAtasan2();
                                    }

                                }

//                      FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
//                      Toast.makeText(context, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

                                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                                        if (dataSnapshot.hasChild(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR")) {
                                            RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                            routingHomeDetailInterface.routingChat(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",friendName, friendNIK,semester,tahun,kualitatif,kuantitatif,context, KPIItems,friendBranchName,friendDept,friendJobTitle,foto,"INBOX");

                                        }
                                        else if(dataSnapshot.hasChild(friendNIK+"-"+nik+"-"+friendName+"-"+nama+"-YEAR")){
                                            RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                            routingHomeDetailInterface.routingChat(friendNIK +"-"+nik+"-"+friendName+"-"+nama+"-YEAR",friendName,friendNIK,semester,tahun,kualitatif,kuantitatif,context,KPIItems,friendBranchName,friendDept,friendJobTitle,foto,"INBOX");
                                        }
                                        else{
                                            Map<String,Object> map = new HashMap<String,Object>();
                                            map.put(nik+"-"+ friendNIK+"-"+nama+"-"+friendName+"-YEAR",""); //diganti room name random uniqueID
                                            root.updateChildren(map);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            catch (Exception e){
                                //jika roomChat belum ada, buat room

                                String nik = usr.get(0).getEmpNIK();
                                String nikLawan = kpiApproveList.getNIKAtasan1();

                                Map<String,Object> map = new HashMap<String,Object>();
                                map.put(nik+"-"+ friendNIK+"-"+usr.get(0).getEmpName()+"-"+friendName+"-YEAR",""); //diganti room name random uniqueID
                                root.updateChildren(map);

                            }
            }
        });


//        String s1=home.getFrom();
//        String[] words=s1.split("-");
//        String year = "";

//        if(words.length>3){
//            year = "(PA)";
//        }
//
//        if(words[0].equals(usr.get(0).getEmpNIK())){
//
//            if(words[2].equals(usr.get(0).getEmpName())){
//                holder.tvFrom.setText(words[3]+" "+year);
//            }
//            else if(words[3].equals(usr.get(0).getEmpName())){
//                holder.tvFrom.setText(words[2]+" "+year);
//            }
//        }
//        else if(words[1].equals(usr.get(0).getEmpNIK())){
//            if(words[2].equals(usr.get(0).getEmpName())){
//                holder.tvFrom.setText(words[3]+" "+year);
//            }
//            else if(words[3].equals(usr.get(0).getEmpName())){
//                holder.tvFrom.setText(words[2]+" "+year);
//            }
//        }
        Picasso.get()
                .load(kpiApproveList.getEmpPhoto())
                .placeholder(R.drawable.user)
                .error(R.drawable.imgalt)
                .into(holder.empPhoto);
        holder.tvFrom.setText(kpiApproveList.getEmpName());

        //Toast.makeText(context, home.getTime().size()+"",Toast.LENGTH_LONG).show();





    }

    @Override
    public int getItemCount() {
        return kpiApproveLists.size();
    }


}
