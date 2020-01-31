package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.DevPlanDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Quintus Labs on 15/11/18.
 * www.quintuslabs.com
 */
public class ListDevPlanAdapter extends RecyclerView.Adapter<ListDevPlanAdapter.ViewHolder> {

    Context context;
    List<DevPlanDetail> steps;
    String title;
    DatePickerDialog  datePickerDialog;
    SimpleDateFormat dateFormatter;
    String isPOPUP="";

    EventListener listener;

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions, int action);
        void setDetail(List<DevPlanDetail> steps);
        List<DevPlanDetail> getDetail();

        boolean getUbah();
        void setUbah(boolean isUbah);

    }


    public ListDevPlanAdapter(List<DevPlanDetail> steps, String title, Context context,String isPOPUP,EventListener listener) {
        this.steps = steps;
        this.title = title;
        this.context = context;
        this.isPOPUP = isPOPUP;
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.idp_item_detail, viewGroup, false);
        return new ViewHolder(v);
    }
    private void showCalendar (EditText editText){

        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);


        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                try {
                    editText.setText(dateFormatter.format(newDate.getTime()));
                    Toast.makeText(context, "Tanggal dipilih : " + dateFormatter.format(newDate.getTime()), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                }
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int i) {

        int x = holder.getLayoutPosition();

        holder.tvNumber.setText((i+1)+". ");
        holder.tvIdpTitle.setText(steps.get(i).getDevplanMethodDesk());

        holder.imgBtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(holder.edtDueDate);
            }
        });

        holder.imgBtnExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,Integer.toString(listIDP.get(finalU1).getLnExpanded().get(finalY1).getVisibility()),Toast.LENGTH_LONG).show();
                                            if (holder.lnExpanded.getVisibility() == View.VISIBLE) {
                                                holder.lnExpanded.setVisibility(View.GONE);
                                                holder.imgBtnExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_down));

                                            } else {
                                                holder.lnExpanded.setVisibility(View.VISIBLE);
                                                holder.imgBtnExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_up));

                                            }
            }
        });

        holder.edtAchievementRecommendation.setText(steps.get(i).getDEVPLANACHIEVEMENT());

        holder.edtAchievementRecommendation.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);
                if( holder.edtAchievementRecommendation.getText().toString().length()>0) {
                    steps.get(i).setDEVPLANACHIEVEMENT(holder.edtAchievementRecommendation.getText().toString());
                }

//                    Configuration newConfig = new Configuration();
//                    // Checks whether a hardware keyboard is available
//                    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//                        Toast.makeText(context, "keyboard visible", Toast.LENGTH_SHORT).show();
//                    } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//                        Toast.makeText(context, "keyboard hidden", Toast.LENGTH_SHORT).show();
//                    }


            }
        });

        try {
            holder.edtDevActivities.setText(steps.get(i).getDEVPLANACTIVITIES().split(" ")[0]);
        }catch (Exception e){
            holder.edtDevActivities.setText(steps.get(i).getDEVPLANACTIVITIES());

        }
        holder.edtDevActivities.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);

                if( holder.edtDevActivities.getText().toString().length()>0) {
                    steps.get(i).setDEVPLANACTIVITIES(holder.edtDevActivities.getText().toString());
                }
            }
        });

        holder.edtDueDate.setText(steps.get(i).getDEVPLANDUEDATE());

        holder.edtDueDate.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);

                if( holder.edtDueDate.getText().toString().length()>0) {

                    steps.get(i).setDEVPLANDUEDATE(holder.edtDueDate.getText().toString());
                }
            }
        });


        holder.edtKPI.setText(steps.get(i).getDEVPLANKPI());

        holder.edtKPI.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);

                if( holder.edtKPI.getText().toString().length()>0) {
                    steps.get(i).setDEVPLANKPI(holder.edtKPI.getText().toString());
                }
            }
        });

        holder.edtMentor.setText(steps.get(i).getDEVPLANMENTOR());

        holder.edtMentor.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listener.setUbah(true);

                if( holder.edtMentor.getText().toString().length()>0) {
                    steps.get(i).setDEVPLANMENTOR(holder.edtMentor.getText().toString());
                }
            }
        });

        holder.cbIdp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    listener.setUbah(true);

                    steps.get(i).setCheckedCb(true);
                }
                else{
                    listener.setUbah(false);

                    steps.get(i).setCheckedCb(false);

                }
            }
        });



    }

    public List<DevPlanDetail> getStepList() {
        return steps;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imgBtnCalendar, imgBtnExpand;
        EditText edtDevActivities,edtKPI,edtDueDate,edtMentor,edtAchievementRecommendation;
        TextView tvNumber,tvIdpTitle,tvMentor,tvAchievementRecommendation;
        CheckBox cbIdp;
        LinearLayout lnExpanded;

        public ViewHolder(View itemView) {
            super(itemView);
            cbIdp = itemView.findViewById(R.id.checkBoxIdp);
            imgBtnCalendar = itemView.findViewById(R.id.btnCalendar);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvIdpTitle = itemView.findViewById(R.id.tvidp_title);
            edtDevActivities = itemView.findViewById(R.id.edtDevelopmentActivities);
            edtKPI = itemView.findViewById(R.id.edtKPI);
            edtDueDate = itemView.findViewById(R.id.edtDueDate);
            edtMentor = itemView.findViewById(R.id.edtMentor);
            edtAchievementRecommendation = itemView.findViewById(R.id.edtAchevementRecommendation);
            imgBtnExpand = itemView.findViewById(R.id.imgExpand);
            lnExpanded = itemView.findViewById(R.id.lnExpanded);
            tvMentor = itemView.findViewById(R.id.tvMentor);
            tvAchievementRecommendation = itemView.findViewById(R.id.tvAchevementRecommendation);

            if(isPOPUP.equals("DIALOG")){
                tvAchievementRecommendation.setVisibility(View.GONE);
                tvMentor.setVisibility(View.GONE);
                edtAchievementRecommendation.setVisibility(View.GONE);
                edtMentor.setVisibility(View.GONE);
            }
            //            add = itemView.findViewById(R.id.add);
//            remove = itemView.findViewById(R.id.remove);
//            step = itemView.findViewById(R.id.step);
//
//            remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    try {
//                        steps.remove(position);
//                        notifyItemRemoved(position);
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//
//            add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    try {
//                        steps.add(position + 1, "");
//                        notifyItemInserted(position + 1);
//                    } catch (ArrayIndexOutOfBoundsException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

//            step.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    steps.set(getAdapterPosition(), s.toString());
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                }
//            });
        }

    }
}
