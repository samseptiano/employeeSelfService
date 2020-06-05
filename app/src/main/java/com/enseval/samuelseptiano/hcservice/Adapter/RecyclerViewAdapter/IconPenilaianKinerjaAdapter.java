package com.enseval.samuelseptiano.hcservice.Adapter.RecyclerViewAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;

import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.content.Context.MODE_PRIVATE;

public class IconPenilaianKinerjaAdapter extends RecyclerView.Adapter<IconPenilaianKinerjaAdapter.MyViewHolder> {

    private List<IconModel> iconModelList;
    private List<ModuleRealmModel> mdl;
    int ctrShowCase=0;

    MaterialShowcaseSequence sequence;
    ShowcaseConfig config = new ShowcaseConfig(); //create the showcase config
    SessionManagement session;
    int count=0;

    private Context context;
    boolean isConnected;
    private Activity activity;
    boolean ranBefore;
    EventListener listener;
    public interface EventListener {
        void callAction(String moduleCode);
        int getCountMsg();
        void setCountMsg(int countMsg);

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIcon, tvNotifMessage;
        public ImageButton imgBtnIcon;
        public ImageView imageComingSoon;
        LinearLayout lnIcon;

        public MyViewHolder(View view) {
            super(view);
            tvIcon = view.findViewById(R.id.tvIcon);
            tvNotifMessage = view.findViewById(R.id.tv_notifMessage);
            imgBtnIcon = view.findViewById(R.id.imgBtnIcon);
            lnIcon = view.findViewById(R.id.lnIcon);
            imageComingSoon = view.findViewById(R.id.imgComingSoon);
        }

    }


    public IconPenilaianKinerjaAdapter(List<IconModel> iconModelList, Context context, Boolean isConnected, Activity activity, List<ModuleRealmModel>mdl, EventListener listener) {
        this.context = context;
        this.iconModelList = iconModelList;
        this.isConnected = isConnected;
        this.activity = activity;
        this.mdl = mdl;
        this.listener=listener;
        session = new SessionManagement(context);

        if(session.isranBefore()) {
            ShowcaseConfig config = new ShowcaseConfig(); //create the showcase config
            config.setDelay(300); //set the delay of each sequence using millis variable
            sequence = new MaterialShowcaseSequence(activity, iconModelList.get(0).getIconCode()); // create the material showcase sequence
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        IconModel iconModel = iconModelList.get(position);

        holder.tvIcon.setText(iconModel.getIconTitle());
        holder.imgBtnIcon.setImageResource(iconModel.getIconImage());

        if(iconModel.getIconStatus().equals("C")){
            holder.imageComingSoon.setVisibility(View.VISIBLE);
        }
        else{
            holder.imageComingSoon.setVisibility(View.GONE);
        }
//        Toast.makeText(context,listener.getCountMsg()+"",Toast.LENGTH_LONG).show();
        if(iconModel.getIconCode().equals("HCCHT") && listener.getCountMsg()>0){
            holder.tvNotifMessage.setText(Integer.toString(listener.getCountMsg()));
            holder.tvNotifMessage.setVisibility(View.VISIBLE);
        }
        else{
            holder.tvNotifMessage.setVisibility(View.GONE);
        }

        holder.lnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callAction(iconModel.getIconCode());
            }
        });


        if(session.isranBefore() == false && position == ctrShowCase){
            sequence = new MaterialShowcaseSequence(activity, iconModelList.get(ctrShowCase).getIconCode()); // create the material showcase sequence

//            MaterialShowcaseView ms = new MaterialShowcaseView.Builder(activity) // instantiate the material showcase view using Builder
//                    .setTarget(holder.lnIcon) // set what view will be pointed or highlighted
//                    .setTitleText("Single") // set the title of the tutorial
//                    .setDismissText("GOT IT") // set the dismiss text
//                    .setContentText("This is the choose option button") // set the content or detail text
//                    .setDelay(300) // set delay in milliseconds to show the tutor
//                    .singleUse(iconModelList.get(0).getIconCode()) // set the single use so it is shown only once using our create SHOWCASE_ID constant
//                    .show();




            sequence.setOnItemDismissedListener(new MaterialShowcaseSequence.OnSequenceItemDismissedListener() {
                @Override
                public void onDismiss(MaterialShowcaseView itemView, int position) {

                    ctrShowCase++;
                    notifyDataSetChanged();

//
                }
            });

            sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
                @Override
                public void onShow(MaterialShowcaseView itemView, int position) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = activity.getLayoutInflater();
                    View dialogLayout = inflater.inflate(R.layout.skip_showcase,
                            null);

                    final AlertDialog dialog = builder.create();
                    dialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    dialog.setView(dialogLayout, 0, 0, 0, 0);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setCancelable(true);
                    WindowManager.LayoutParams wlmp = dialog.getWindow()
                            .getAttributes();
                    wlmp.gravity = Gravity.BOTTOM;


                    TextView tvSkip = (TextView) dialogLayout.findViewById(R.id.tvSkip);


                    tvSkip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            session.updateRanBefore(true);
                            //listener.callAction("skipGuideline");
                            dialog.dismiss();
                            ctrShowCase++;
                            itemView.hide();
                            notifyDataSetChanged();

                        }
                    });
                    builder.setView(dialogLayout);

                    dialog.show();
                }
            });

            sequence.setConfig(config);
            //set the showcase config to the sequence.
            sequence.addSequenceItem(holder.lnIcon, iconModelList.get(ctrShowCase).getIconGuideline(), "MENGERTI"); // add view for the first sequence, in this case it is a button.
            //sequence.addSequenceItem(holder.lnIcon, iconModel.getIconGuideline(), "MENGERTI"); // add view for the first sequence, in this case it is a button.
            //if(position==iconModelList.size()-1){
            sequence.start();
            //}


        }

        holder.lnIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.callAction(iconModel.getIconCode());
            }
        });


    }

    @Override
    public int getItemCount() {
        return iconModelList.size();
    }


}