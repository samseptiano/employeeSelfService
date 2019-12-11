package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment.QuizFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.DateFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventSessionAdapter extends RecyclerView.Adapter<EventSessionAdapter.MyViewHolder> {

    private List<EventSession> eventSessions;
    private int rowLayout;
    private Context context;
    private ArrayList<EventSession> eventSessionListFilter = new ArrayList<>();  // for loading  filter data

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    boolean isConnected;
    HomeRealmModel homie;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date date = new Date();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView instructorId,dateStart,dateEnd,place,sessionName,date;
        private Button btnfeedback;

        public MyViewHolder(View view) {
            super(view);
            instructorId = (TextView) view.findViewById(R.id.tvInstructorId);
            dateStart = (TextView) view.findViewById(R.id.tvDateStart);
            dateEnd = (TextView) view.findViewById(R.id.tvdateEnd);
            date = (TextView) view.findViewById(R.id.tvDate);
            place = (TextView) view.findViewById(R.id.tvPlace);
            sessionName = (TextView) view.findViewById(R.id.tvSessionName);
            btnfeedback = view.findViewById(R.id.btnInstructorFeedback);
        }
    }


    public EventSessionAdapter(List<EventSession> eventSessions, Context context, Boolean isConnected, HomeRealmModel homie) {
        this.context = context;
        this.eventSessions = eventSessions;
        this.eventSessionListFilter.addAll(eventSessions);
        this.isConnected = isConnected;
        this.homie = homie;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_session_list, parent, false);

        return new MyViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventSession eventSession = eventSessions.get(position);
        //String date = dateJoin.get(position);
        DateFormatter df = new DateFormatter();
        holder.date.setText("Tanggal: "+df.indonesiaDate(eventSession.getSessionDateStart())+" ");
        holder.instructorId.setText("Oleh: " + eventSession.getInstructorName());
        holder.dateStart.setText(eventSession.getSessionDateStart().substring(11));
        holder.dateEnd.setText(eventSession.getSessionDateEnd().substring(11));
        holder.place.setText("Lokasi: " + eventSession.getSessionPlace());
        holder.sessionName.setText(eventSession.getSessionName());

        if(eventSession.getFgHasSurveyYN().equals("Y") && eventSession.getFgSurveyDoneYN().equals("N")){
        //if(ConnectivityReceiver.isConnected()){
            holder.btnfeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fr = new QuizFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("surveyID", eventSession.getSurveyID());
                    bundle.putString("eventID", eventSession.getEventID());
                    bundle.putString("sessionID", eventSession.getSessionID());
                    bundle.putString("isFeedbackInstructor", "YES");
                    bundle.putString("instructorId", eventSession.getInstructorID());
                    bundle.putString("instructorPhoto", eventSession.getFileData());
                    bundle.putString("eventType", eventSession.getEventType());
                    fr.setArguments(bundle);
                    fm = ((FragmentActivity)context).getSupportFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_frame, fr);
                    ft.addToBackStack(null);
                    ft.commit();

                    frr = new TabLayoutFragment();
                    fmm = ((FragmentActivity)context).getSupportFragmentManager();
                    ftt = fmm.beginTransaction();
                    ft.replace(R.id.fragment_tablayout, frr);
                    ftt.remove(frr);
                    ftt.commit();

                    Toast.makeText(context, "Quiz Multiple Choice Area", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            holder.btnfeedback.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return eventSessions.size();
    }


}

