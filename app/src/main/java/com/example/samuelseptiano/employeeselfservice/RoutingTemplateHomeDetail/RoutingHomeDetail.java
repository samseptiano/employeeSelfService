package com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samuelseptiano.employeeselfservice.Fragment.ChatFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIApproveListFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIFillFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment.KPIKuantitatifFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIApproveListTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIApproveTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment.KPIFillTahunanFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.RequestTemplate.HomeRequestFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

public class RoutingHomeDetail implements RoutingHomeDetailInterface {

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    public void routingHomeDetail(String choice, Context context, String id){
        switch(choice) {

            case  "Training":
                // code block
                fr = new HomeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle.putString("id",id);
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("HomeDetailFragment");
                ft.commit();
                Toast.makeText(context,"Training Area",Toast.LENGTH_SHORT).show();
                break;
            case "Request":
                // code block
                fr = new HomeRequestFragment();
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("HomeRequestFragment");
                ft.commit();
                Toast.makeText(context,"Request Area",Toast.LENGTH_SHORT).show();
                break;
            default:
                // code block
        }

    }

    public void routingKPI(String choice, Context context, UserList userList, String type){
        switch(choice) {
            case "PA List tahunan":
                // code block
                fr = new KPIApproveListTahunanFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle3);
                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIApproveListTahunanFragment");
                ft.commit();
                Toast.makeText(context, "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                break;
            case "Approve PA tahunan":
                // code block
                fr = new KPIApproveTahunanFragment();

                Bundle bundle4 = new Bundle();
                bundle4.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle4.putSerializable("id", userList);
                bundle4.putString("KPIType",type);
               // bundle4.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle4);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIApproveTahunanFragment");
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity) context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(context, "KPI Area", Toast.LENGTH_SHORT).show();
                break;
            case "Fill PA tahunan":
                // code block
                fr = new KPIFillTahunanFragment();

                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle.putSerializable("id", userList);
                bundle.putString("KPIType",type);
                //bundle.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIFillTahunanFragment");
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity) context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(context, "KPI Area", Toast.LENGTH_SHORT).show();
                break;

             //=================================================
            case "PA List":
                // code block
                fr = new KPIApproveListFragment();
                Bundle bundle5 = new Bundle();
                bundle5.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle5);
                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIApproveListFragment");
                ft.commit();
                Toast.makeText(context, "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                break;
            case "Approve PA":
                // code block
                fr = new KPIKuantitatifFragment();

                Bundle bundle6 = new Bundle();
                bundle6.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle6.putSerializable("id", userList);
                bundle6.putString("KPIType",type);
                bundle6.putString("semester","1");
                // bundle4.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle6);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIKuantitatifFragment");
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity) context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(context, "KPI Area", Toast.LENGTH_SHORT).show();
                break;
            case "Fill PA":
                // code block
                fr = new KPIFillFragment();

                Bundle bundle7 = new Bundle();
                bundle7.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle7.putSerializable("id", userList);
                bundle7.putString("KPIType",type);
                //bundle.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle7);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("KPIFillFragment");
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity) context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(context, "KPI Area", Toast.LENGTH_SHORT).show();
                break;
            //==================================================

            default:
        }
    }

    public void routingChat(String roomName,String friendName, String friendNik, Context context){
        fr = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("room_name", roomName);
        bundle.putString("friend_name", friendName);
        bundle.putString("friend_nik", friendNik);
        bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        fr.setArguments(bundle);
        fm = ((FragmentActivity)context).getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.addToBackStack("fragmentChat");
        ft.commit();

        frr = new TabLayoutFragment();
        fmm = ((FragmentActivity) context).getSupportFragmentManager();
        ftt = fmm.beginTransaction();
        ft.replace(R.id.fragment_tablayout, frr);
        ftt.remove(frr);
        ftt.commit();
    }

    public void routingHome(String choice, Context context){
        switch(choice) {
            case "Event":
                // code block
                fr = new HomeCategoryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Category", "Event");
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("fragmentHome");
                ft.commit();
                break;
            case "Request":
                // code block
                fr = new HomeRequestFragment();
                Bundle bundle2 = new Bundle();
                //bundle2.putString("Category", "Request");
                bundle2.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle2);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("fragmentHome");
                ft.commit();
                break;
            case "Goal":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            case "Benefit":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            case "Survey":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            case "More":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            default:
                // code block
        }

    }

}
