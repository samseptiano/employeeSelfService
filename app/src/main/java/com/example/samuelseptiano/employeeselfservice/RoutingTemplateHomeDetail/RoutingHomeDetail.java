package com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIApproveFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIApproveListFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIFillFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIKuantitatifFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.HomeCategoryFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.RequestTemplate.HomeRequestFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
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
                ft.addToBackStack("fragmentHome");
                ft.commit();
                Toast.makeText(context,"Training Area",Toast.LENGTH_SHORT).show();
                break;
            case "Request":
                // code block
                fr = new HomeRequestFragment();
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack("fragmentHome");
                ft.commit();
                Toast.makeText(context,"Request Area",Toast.LENGTH_SHORT).show();
                break;
            default:
                // code block
        }

    }

    public void routingKPI(String choice, Context context, String empId, String type){
        switch(choice) {
            case "PA List":
                // code block
                fr = new KPIApproveListFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                fr.setArguments(bundle3);
                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();
                Toast.makeText(context, "Approvee KPI Area", Toast.LENGTH_SHORT).show();
                break;
            case "Approve PA":
                // code block
                fr = new KPIApproveFragment();

                Bundle bundle4 = new Bundle();
                bundle4.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle4.putString("id", empId);
                bundle4.putString("KPIType",type);
               // bundle4.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle4);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
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

                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle.putString("id", empId);
                bundle.putString("KPIType",type);
                //bundle.putString("jenisKPI","Kuantitatif");
                fr.setArguments(bundle);

                fm = ((FragmentActivity) context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();

                frr = new TabLayoutFragment();
                fmm = ((FragmentActivity) context).getSupportFragmentManager();
                ftt = fmm.beginTransaction();
                ft.replace(R.id.fragment_tablayout, frr);
                ftt.remove(frr);
                ftt.commit();

                Toast.makeText(context, "KPI Area", Toast.LENGTH_SHORT).show();
                break;
            default:
        }
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
