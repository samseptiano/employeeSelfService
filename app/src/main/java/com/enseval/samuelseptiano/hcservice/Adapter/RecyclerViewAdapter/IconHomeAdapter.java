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
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.enseval.samuelseptiano.hcservice.Activity.MainActivity;
import com.enseval.samuelseptiano.hcservice.Helper.ModuleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.ModuleRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.IconModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.enseval.samuelseptiano.hcservice.Session.SessionManagement;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.shape.ShapeType;
import co.mobiwise.materialintro.view.MaterialIntroView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.content.Context.MODE_PRIVATE;

public class IconHomeAdapter extends RecyclerView.Adapter<IconHomeAdapter.MyViewHolder> {

    private List<IconModel> iconModelList;
    private List<ModuleRealmModel> mdl;
    SessionManagement session;

    int ctrShowCase=0;

    private Context context;
    boolean isConnected;
    private Activity activity;
    MaterialShowcaseSequence sequence;
    ShowcaseConfig config = new ShowcaseConfig(); //create the showcase config
    boolean ranBefore;

    EventListener listener;
    public interface EventListener {
        void callAction(String moduleCode);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvIcon;
        public ImageButton imgBtnIcon;
        public ImageView imageComingSoon;
        LinearLayout lnIcon;
        MaterialShowcaseView materialShowcaseView;

        public MyViewHolder(View view) {
            super(view);
            tvIcon = view.findViewById(R.id.tvIconHome);
            imgBtnIcon = view.findViewById(R.id.imgBtnIconHome);
            lnIcon = view.findViewById(R.id.lnIconHome);
            imageComingSoon = view.findViewById(R.id.imgComingSoonHome);
            //materialShowcaseView = new MaterialShowcaseView();
        }

    }


    public IconHomeAdapter(List<IconModel> iconModelList, Context context, Boolean isConnected, Activity activity, List<ModuleRealmModel>mdl, EventListener listener) {
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
                .inflate(R.layout.icon_item_home, parent, false);

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


        //if(activity.getPreferences(MODE_PRIVATE).getBoolean("RanBefore", false)) {
//            new MaterialIntroView.Builder(activity)
//                    .enableDotAnimation(true)
//                    .enableIcon(false)
//                    .setFocusGravity(FocusGravity.CENTER)
//                    .setFocusType(Focus.MINIMUM)
//                    .setDelayMillis(500)
//                    .enableFadeAnimation(true)
//                    .performClick(true)
//                    .setInfoText("Hi There! Click this card and see what happens.")
//                    //.setCustomShape(ShapeType.CIRCLE)
//                    .setTarget(holder.lnIcon)
//                    .setUsageId("intro_card") //THIS SHOULD BE UNIQUE ID
//                    .show();

//        if(iconModelList.get(position).isShowedCase()==true) {
//            new MaterialShowcaseView.Builder(activity) // instantiate the material showcase view using Builder
//                    .setTarget(holder.imgBtnIcon) // set what view will be pointed or highlighted
//                    .setTitleText("Single") // set the title of the tutorial
//                    .setDismissText("Mengerti") // set the dismiss text
//                    .setContentText("This is the choose option button") // set the content or detail text
//                    .setDelay(100) // set delay in milliseconds to show the tutor
//                    //.singleUse(iconModel.getIconCode()) // set the single use so it is shown only once using our create SHOWCASE_ID constant
//
//                    .show();
//            iconModelList.get(position).setShowedCase(false);
//        }
        //}

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
                        SharedPreferences preferences = activity.getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
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
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogLayout = inflater.inflate(R.layout.skip_showcase,
//                null);
//        final AlertDialog dialog = builder.create();
//        dialog.getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialog.setView(dialogLayout, 0, 0, 0, 0);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        WindowManager.LayoutParams wlmp = dialog.getWindow()
//                .getAttributes();
//        wlmp.gravity = Gravity.BOTTOM;
//
////        Button btnCamera = (Button) dialogLayout.findViewById(R.id.button_Camera);
////        Button btnGallery = (Button) dialogLayout.findViewById(R.id.button_Gallery);
////        Button btnDismiss = (Button) dialogLayout.findViewById(R.id.btnCancelCamera);
//
//        builder.setView(dialogLayout);
//
//        dialog.show();

       // Target viewTarget = new ViewTarget(R.id.lnIconHome, activity);  // Add the control you need to focus by the ShowcaseView

//        new ShowcaseView.Builder(activity)
//                .setTarget(new ViewTarget(holder.lnIcon))
//                .setContentTitle("ShowcaseView")
//                .setContentText("This is highlighting the Home button")
//                .hideOnTouchOutside()
//                .withMaterialShowcase()
//                //.setStyle(R.style.ShowcaseTheme)
//                .build().show();
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