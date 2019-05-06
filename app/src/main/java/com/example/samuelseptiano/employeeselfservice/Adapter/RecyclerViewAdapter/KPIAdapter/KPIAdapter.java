package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.HomeCategoryAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.QuizAdapter.QuizAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIApproveListFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment.KPIKuantitatifFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerSurvey;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class KPIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "KPIAdapter";
    private LayoutInflater inflater;
    KPIHeader kpiHeader;

    private static int currentPosition = 0;

    List<ImageUploadModel> aaa = new ArrayList<ImageUploadModel>();

    Activity activity;

    KPIUserAnswerList kpiUserAnswerList;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;

    boolean isVisible = false;

    String id,type;
    String isEditable="N";
    String Jenis = "";


    private List<KPIQuestions> questions;

    private Context context;

    private LinearLayout linearLayoutContainer;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;


    EventListener listener;

    public interface EventListener {
        void onEvent(int position, List<KPIQuestions> questions);
        List <ImageUploadModel> onResult();
        void onActivityResult(int requestCode, int resultCode, Intent data);
        void createRV();
    }

    public KPIAdapter(Activity activity, Context context, KPIHeader kpiHeader, String id, String type,  EventListener listener, String Jenis) {
        this.inflater = LayoutInflater.from(context);
        this.questions = kpiHeader.getKpiQuestionsList();
        this.context = context;
        this.activity=activity;
        this.kpiHeader=kpiHeader;
        this.id=id;
        this.type=type;
        this.listener = listener;
        this.Jenis = Jenis;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_list, parent, false);
            return new com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.ItemViewHolder(itemView);
        }else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_header, parent, false);
            return new com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kpi_footer, parent, false);
            return new com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.FooterViewHolder(itemView);
        } else return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.HeaderViewHolder) {
                com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.HeaderViewHolder headerHolder = (com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.HeaderViewHolder) holder;
                headerHolder.semester.setText(": "+kpiHeader.getSemester());
                headerHolder.status.setText(": "+kpiHeader.getStatus());
                headerHolder.tahun.setText(": "+kpiHeader.getTahun());
                headerHolder.namaKaryawan.setText(": "+kpiHeader.getEmpName());
                headerHolder.nik.setText(": "+kpiHeader.getNIK());
                headerHolder.dept.setText(": "+kpiHeader.getDept());

                if(id.length()== 0 && !type.equals("Approve")){
                    ((HeaderViewHolder) holder).dept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).namaKaryawan.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).nik.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnDept.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNIK.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).lnNama.setVisibility(View.GONE);
                    ((HeaderViewHolder) holder).editBtn.setVisibility(View.GONE);
                }
                ((HeaderViewHolder) holder).editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isEditable.equals("N")) {
                            isEditable = "Y";
                            Toast.makeText(context, "Edit: ON", Toast.LENGTH_SHORT).show();

                        }
                        else if(isEditable.equals("Y")){
                            isEditable = "N";
                            Toast.makeText(context, "Edit: OFF", Toast.LENGTH_SHORT).show();

                        }
                        notifyDataSetChanged();
                    }
                });


            } else if (holder instanceof com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.FooterViewHolder) {
                final com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.FooterViewHolder footerHolder = (com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.FooterViewHolder) holder;

                final int requestCode = 20;

                if(id.length()>0 && type.equals("Approve")){
                    ((FooterViewHolder) holder).footerButton.setVisibility(View.GONE);
                }
                else{
                    ((FooterViewHolder) holder).btnApprove.setVisibility(View.GONE);
                }

                if(isEditable.equals("N") && type.equals("Approve")){

                    footerHolder.edtRencanaTindaklanjut.setEnabled(false);
                    footerHolder.edtKelebihan.setEnabled(false);
                    footerHolder.edtkekurangan.setEnabled(false);
                }
                else if(isEditable.equals("Y") && type.equals("Approve")){
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                }
                else{
                    footerHolder.edtRencanaTindaklanjut.setEnabled(true);
                    footerHolder.edtKelebihan.setEnabled(true);
                    footerHolder.edtkekurangan.setEnabled(true);
                }
                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String NIK = usr.get(0).getEmpNIK();

                footerHolder.btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(type.equals("Approve")){
                          submitKPI(kpiHeader.getNIK(),footerHolder);
                        }

                    }
                });

                footerHolder.footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!type.equals("Approve")){
                            submitKPI(NIK,footerHolder);
                        }
                    }
                });
            } else if (holder instanceof com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter.ItemViewHolder) {
                holder.setIsRecyclable(false);

                ((ItemViewHolder) holder).linearLayoutContainer2.setVisibility(View.GONE);

                //if the position is equals to the item position which is to be expanded

                if (currentPosition == position) {
                    //creating an animation
                    Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.anim_drop_down_recycler);

//                    if(isVisible == false){
                        //toggling visibility
                        ((ItemViewHolder) holder).linearLayoutContainer2.setVisibility(View.VISIBLE);
                        //adding sliding effect
                        ((ItemViewHolder) holder).linearLayoutContainer2.startAnimation(slideDown);
//                        isVisible = true;
//                    }
//                    else if(isVisible == true){
//                        ((ItemViewHolder) holder).linearLayoutContainer2.setVisibility(View.GONE);
//                        isVisible=false;
//                    }
                }

                ((ItemViewHolder) holder).tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //getting the position of the item to expand it
                        currentPosition = position;
                        //reloding the list

                        notifyDataSetChanged();

                    }
                });

                final KPIAdapter.ItemViewHolder itemViewHolder = (KPIAdapter.ItemViewHolder) holder;
                KPIQuestions current = questions.get(position - 1);
                itemViewHolder.setQuestion(current.getKPIDesc());
                itemViewHolder.setNumber(position);
                itemViewHolder.setOptions(current, position - 1);
                itemViewHolder.setBobot(current.getBobot());
                itemViewHolder.setCategory(current.getKPIcategory(),current.getPerspective(),position-1);
                itemViewHolder.setUpload(current.getKPIcategory(),current, position-1);
                itemViewHolder.setAnswer(current,position-1);
            }
        }
        catch (Exception e){

        }
    }

    @Override
    public int getItemViewType(int position) {
        int returned = 0;
        if (position == 0) {
            returned = TYPE_HEADER;
        }
        else if (position == questions.size()+1) {
            returned = TYPE_FOOTER;
        }
        else {
            returned = TYPE_ITEM;
        }
        return returned;
    }


    @Override
    public int getItemCount() {
        if (questions == null) {
            return 0;
        } else {
            return questions.size()+2;
        }
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tahun,namaKaryawan;
        TextView semester,dept;
        TextView status,nik;
        ImageView editBtn;
        LinearLayout lnNama,lnNIK,lnDept;

        public HeaderViewHolder(View view) {
            super(view);
            tahun = (TextView) view.findViewById(R.id.tvTahun);
            semester = (TextView) view.findViewById(R.id.tvSemester);
            status = (TextView) view.findViewById(R.id.tvStatus);
            dept = (TextView) view.findViewById(R.id.tvDept);
            nik = (TextView) view.findViewById(R.id.tvNIK);
            namaKaryawan = (TextView) view.findViewById(R.id.tvEmpName);
            lnDept = (LinearLayout) view.findViewById(R.id.lnDept);
            lnNama = (LinearLayout) view.findViewById(R.id.lnNama);
            lnNIK = (LinearLayout) view.findViewById(R.id.lnNIK);
            editBtn = (ImageView) view.findViewById(R.id.editBtn);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton, btnApprove;
        EditText edtKelebihan, edtkekurangan, edtRencanaTindaklanjut;
        TextView footertext;
        LinearLayout lnKualitatif;

        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitKPI);
            btnApprove = (Button) view.findViewById(R.id.btnApproveKPI);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);
            edtkekurangan = view.findViewById(R.id.edtKekurangan);
            edtKelebihan = view.findViewById(R.id.edtKelebihan);
            edtRencanaTindaklanjut = view.findViewById(R.id.edtRencanaTindaklanjut);

            lnKualitatif = view.findViewById(R.id.lnKualitatif);
            if(type.equals("Approve") && isEditable.equals("N")){

                edtRencanaTindaklanjut.setEnabled(false);
                edtKelebihan.setEnabled(false);
                edtkekurangan.setEnabled(false);
            }
            else{
                edtRencanaTindaklanjut.setEnabled(true);
                edtKelebihan.setEnabled(true);
                edtkekurangan.setEnabled(true);
            }
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer,linearLayoutContainer2;
        private TextView textViewQuestion, textViewNumber, tvIndicator,tvBobot, tvCategory, tvName, tvPerspective;
        private RatingBar rating;
        private ImageButton imgBtnHint;
        private EditText  edtPendukung,edtPenghambat,edtCatatan;
        private View hrLine, hrLine2;
        Button btnUpload;
        LinearLayout lnKuantitatif;
        RecyclerView recyclerViewImg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            btnUpload = itemView.findViewById(R.id.btnUploadImage);
            lnKuantitatif = itemView.findViewById(R.id.lnKuantitatif);
           linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container);
            linearLayoutContainer2 = (LinearLayout) itemView.findViewById(R.id.linear_layout_container2);
            textViewQuestion = (TextView) itemView.findViewById(R.id.tvKPIDesc);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            tvIndicator = (TextView) itemView.findViewById(R.id.tvRatingDesc);
            tvBobot = (TextView) itemView.findViewById(R.id.tvBobot);
            edtPendukung = itemView.findViewById(R.id.edtPendukung);
            edtPenghambat = itemView.findViewById(R.id.edtPenghambat);
            imgBtnHint = (ImageButton) itemView.findViewById(R.id.imgBtnHint);
            edtCatatan = itemView.findViewById(R.id.edtCatatan);
            tvCategory = (TextView) itemView.findViewById(R.id.tvKPICategory);
            hrLine = itemView.findViewById(R.id.HrLine);
            hrLine2 = itemView.findViewById(R.id.HrLine2);
            tvName = itemView.findViewById(R.id.textViewName);
            tvPerspective = itemView.findViewById(R.id.tvPerspective);
            recyclerViewImg = itemView.findViewById(R.id.recycler_view_image_upload);

            rating.setNumStars(5);
            //rating.setRating(numbRadio);
            rating.setStepSize(1);
            if(type.equals("Approve") && isEditable.equals("N")){
                rating.setIsIndicator(true);
                edtCatatan.setEnabled(false);
                edtPendukung.setEnabled(false);
                edtPenghambat.setEnabled(false);
            }
            else if(type.equals("Approve") && isEditable.equals("Y")){
                rating.setIsIndicator(false);
                edtCatatan.setEnabled(true);
                edtPendukung.setEnabled(true);
                edtPenghambat.setEnabled(true);
            }

        }

        public void setUpload(String category, KPIQuestions question, int position){

            KPIQuestions que = questions.get(position);
            List<String> tempImg = new ArrayList<>();

            if(category.equals("Kualitatif")){
                btnUpload.setVisibility(View.GONE);
                edtCatatan.setVisibility(View.GONE);
                edtPendukung.setVisibility(View.GONE);
                edtPenghambat.setVisibility(View.GONE);
                }

            if(question.getPhotoCapture()!=null) {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(question.getPhotoCapture(), context, activity);
                recyclerViewImg.setAdapter(adapter);
            }

            btnUpload.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onEvent(position, questions);
                    question.setPhotoCapture(listener.onResult());
                }
            });

        }

        public void setQuestion(String question) {
            textViewQuestion.setText(question);
        }

        public void setBobot(String bobot) {
            tvBobot.setText(bobot);
        }

        public void setAnswer(KPIQuestions question, int position) {

            //======== problem ===========
            if(question.getAnsweredCatatan()) {
                edtCatatan.setText(question.getCatatanLain());
                //Toast.makeText(context,"hit here",Toast.LENGTH_LONG).show();
            } else {
                edtCatatan.setText("");
                //Toast.makeText(context,"hit over here",Toast.LENGTH_LONG).show();

            }
            //=====================

            edtCatatan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestions que = questions.get(position);
                        if(edtCatatan.getText().toString().length()>0) {
                            que.setAnsweredCatatan(true);
                            que.setCatatanLain(edtCatatan.getText().toString());
                        }
                    }
                }
            });

            if(question.getAnsweredPenghambat()) {
                edtPenghambat.setText(question.getPenghambat());

            } else {
                edtPenghambat.setText("");
            }

            edtPenghambat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestions que = questions.get(position);
                        if(edtPenghambat.getText().toString().length()>0) {
                            que.setAnsweredPenghambat(true);
                            que.setPenghambat(edtPenghambat.getText().toString());
                        }
                    }
                }
            });

            if(question.getAnsweredPendukung()) {
                edtPendukung.setText(question.getPendukung());

            } else {
                edtPendukung.setText("");
            }

            edtPendukung.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        KPIQuestions que = questions.get(position);
                        if(edtPendukung.getText().toString().length()>0) {
                            que.setAnsweredPendukung(true);
                            que.setPendukung(edtPendukung.getText().toString());
                        }
                    }
                }
            });
        }

        public void setCategory(String category, String perspective, int position) {

            if(position != 0) {
                if (questions.get(position - 1).getKPIcategory().equals(category)) {
                    tvCategory.setVisibility(View.GONE);
                    hrLine.setVisibility(View.GONE);
                } else {
                    tvCategory.setText(category);
                }

                if(questions.get(position - 1).getPerspective().equals(perspective)){
                    tvPerspective.setVisibility(View.GONE);
                    hrLine2.setVisibility(View.GONE);
                }
                else {
                    tvPerspective.setText(perspective);
                }
            }
            else{
                tvCategory.setText(category);
                tvPerspective.setText(perspective);
            }

//            Toast.makeText(context,Integer.toString(position)+" "+questions.get(position-1).getKPIcategory()+" "+category,Toast.LENGTH_SHORT).show();
        }


        public void setNumber(long id) {
            textViewNumber.setText(Long.toString(id)+". ");
        }

        public void setOptions(KPIQuestions question, int position) {
//

            Log.e(TAG, position + " :setOptions: " + question.toString());

                                                                                                                                         if(question.isAnswered()) {
                rating.setRating(question.getCheckedId());
                switch(question.getCheckedId()){
                    case 1:
                        tvIndicator.setText("BAD");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                        break;
                    case 2:
                        tvIndicator.setText("GOOD");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                        break;
                    case 3:
                        tvIndicator.setText("ACHIEVE");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                        break;
                    case 4:
                        tvIndicator.setText("ABOVE");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                        break;
                    case 5:
                        tvIndicator.setText("EXCELLENT");
                        tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                        break;
                }

            } else {
                rating.setRating(0);
                    tvIndicator.setText("-");
            }

            imgBtnHint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHintKPI(questions.get(position).getHint());
                }
            });

            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    KPIQuestions que = questions.get(position);
                    que.setAnswered(true);
                    que.setCheckedId((int)rating);
//                    Toast.makeText(context,questions.get(position+7).getCheckedId(),Toast.LENGTH_SHORT).show();
                    switch((int)rating){
                        case 1:
                            tvIndicator.setText("BAD");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.red));
                            break;
                        case 2:
                            tvIndicator.setText("GOOD");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.orange));
                            break;
                        case 3:
                            tvIndicator.setText("ACHIEVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.yellow));
                            break;
                        case 4:
                            tvIndicator.setText("ABOVE");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.blue));
                            break;
                        case 5:
                            tvIndicator.setText("EXCELLENT");
                            tvIndicator.setTextColor(context.getResources().getColor(R.color.green));
                            break;
                    }
                }
            });
        }
    }

    private void showHintKPI(List<String> hint){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kpi_hint_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);

        RecyclerView recyclerViewKPIHint = (RecyclerView) dialog.findViewById(R.id.recycler_view_kpi_hint);
        recyclerViewKPIHint.setLayoutManager(new LinearLayoutManager(context));
        RecyclerView.Adapter kpiAdapter = new KPIHintAdapter(hint, context, activity);
        recyclerViewKPIHint.setAdapter(kpiAdapter);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void submitKPI(String NIK, FooterViewHolder footerHolder){
//        kpiUserAnswerList = new KPIUserAnswerList();
//        List<String> tempImg = new ArrayList<>();
//        int i = 0;
//
//        if(listener.onResult().size()>0) {
//            for (i = 0; i < aaa.size(); i++) {
//                tempImg.add(aaa.get(i).getImgString());
//            }
//        }
//
//        kpiUserAnswerList.setDept(kpiHeader.getDept());
//        kpiUserAnswerList.setSemester(kpiHeader.getSemester());
//        kpiUserAnswerList.setStatus(kpiHeader.getStatus());
//        kpiUserAnswerList.setEmpNIK(NIK);
//        kpiUserAnswerList.setKPIType(Jenis);
//        kpiUserAnswerList.setKpiUserAnswerList(questions);
//
//        kpiUserAnswerList.setImgCapture(tempImg);
//
//
//        if (Jenis.equals("Kuantitatif")) {
////            kpiUserAnswerList.setPendukung(footerHolder.edtPendukung.getText().toString());
////            kpiUserAnswerList.setPenghambat(footerHolder.edtPenghambat.getText().toString());
////            kpiUserAnswerList.setCatatanLain(footerHolder.edtCatatan.getText().toString());
//            kpiUserAnswerList.setRencanaTindaklanjut("");
//            kpiUserAnswerList.setKekurangan("");
//            kpiUserAnswerList.setKelebihan("");
//        }
//        else if (Jenis.equals("Kualitatif")) {
//            kpiUserAnswerList.setKelebihan(footerHolder.edtKelebihan.getText().toString());
//            kpiUserAnswerList.setKekurangan(footerHolder.edtkekurangan.getText().toString());
//            kpiUserAnswerList.setRencanaTindaklanjut(footerHolder.edtRencanaTindaklanjut.getText().toString());
//            kpiUserAnswerList.setPendukung("");
//            kpiUserAnswerList.setPenghambat("");
//            kpiUserAnswerList.setCatatanLain("");
//        }

        Toast.makeText(context,"Submitted!",Toast.LENGTH_LONG).show();
    }

}
