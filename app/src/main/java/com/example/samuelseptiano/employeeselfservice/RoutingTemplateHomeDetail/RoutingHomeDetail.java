package com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.RequestTemplate.HomeRequestFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;

public class RoutingHomeDetail implements RoutingHomeDetailInterface {

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    public void routingHomeDetail(String choice, Context context, String id){
        switch(choice) {
            case "Training":
                // code block
                fr = new HomeDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
                bundle.putString("id",id);
                fr.setArguments(bundle);
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();
                Toast.makeText(context,"Training Area",Toast.LENGTH_LONG).show();
                break;
            case "Request":
                // code block
                fr = new HomeRequestFragment();
                fm = ((FragmentActivity)context).getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.fragment_frame, fr);
                ft.addToBackStack(null);
                ft.commit();
                Toast.makeText(context,"Request Area",Toast.LENGTH_LONG).show();
                break;
            case "Assessment":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            case "Survey":
                // code block
                Toast.makeText(context,"This feature is not available right now",Toast.LENGTH_SHORT).show();
                break;
            default:
                // code block
        }

    }

}
