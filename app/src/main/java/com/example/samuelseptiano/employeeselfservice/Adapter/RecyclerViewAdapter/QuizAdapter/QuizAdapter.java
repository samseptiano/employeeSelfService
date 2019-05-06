package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.QuizAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuHome.TrainingTemplate.HomeDetailFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.TablayoutFragment.TabLayoutFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserAnswerRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerSurvey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.Survey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.UserAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.CheckBoxQuizOption;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "QuestionAdapter";
    private LayoutInflater inflater;
    List<RadioGroup>RadioButtonList = new ArrayList<RadioGroup>();
    List<EditText> edtList = new ArrayList<EditText>();
    List<CheckBoxQuizOption> lcbOption;
    Survey surveyHeader;
    String instructorPhoto;

    String surveyID;
    String eventID;
    String eventType;
    String sessionId;
    String isFeedbackInstructor;

    Fragment fr,frr;
    FragmentManager fm,fmm;
    FragmentTransaction ft,ftt;


    private List<QuestionAnswerSurvey> questions;

    private Context context;

    private Activity activity;

    RadioButton rdbtn;
    int numbRadio;
    private LinearLayout linearLayoutContainer;
    CheckBox checkBox;


    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 2;
    private static final int TYPE_ITEM_ESSAY = 3;
    private static final int TYPE_ITEM_CHECK = 4;


    public QuizAdapter(Activity activity, Context context, List<QuestionAnswerSurvey> questions, int numbradio, List<CheckBoxQuizOption> lcbOptions, Survey surveyHeader, String instructorPhoto, String surveyID, String eventID, String sessionId, String isFeedbackInstructor, String eventType) {
        this.inflater = LayoutInflater.from(context);
        this.questions = questions;
        this.context = context;
        this.numbRadio = numbradio;
        this.lcbOption = lcbOptions;
        this.surveyHeader = surveyHeader;
        this.surveyID = surveyID;
        this.eventID = eventID;
        this.sessionId = sessionId;
        this.isFeedbackInstructor = isFeedbackInstructor;
        this.instructorPhoto = instructorPhoto;
        this.activity=activity;
        this.eventType = eventType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_multiple_list, parent, false);
            return new ItemViewHolder(itemView);
        }else if (viewType == TYPE_ITEM_ESSAY) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_essay_list, parent, false);
            return new ItemEssayViewHolder(itemView);
        }else if (viewType == TYPE_ITEM_CHECK) {
            //Inflating recycle view item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_checkbox, parent, false);
            return new ItemCheckBoxViewHolder(itemView);
        }else if (viewType == TYPE_HEADER) {
            //Inflating header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            //Inflating footer view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_footer, parent, false);
            return new FooterViewHolder(itemView);
        } else return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder instanceof HeaderViewHolder) {
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                headerHolder.headerTitle.setText(surveyHeader.getSurveyName());


                //=========================

                try {
                    byte [] encodeByte=Base64.decode(instructorPhoto,Base64.DEFAULT);
                    Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                    Display display = activity.getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);

                    if(size.x >1081 ){
                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();

                        //Display display = getActivity().getWindowManager().getDefaultDisplay();
                        //Point size = new Point();
                        display.getSize(size);
                        int width = size.x - (size.x/3);
                        int height = size.y - (size.y/3);

                        int newWidth = width; //this method should return the width of device screen.
                        float scaleFactor = (float)newWidth/(float)imageWidth;
                        int newHeight = (int)(imageHeight * scaleFactor);

                        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                        //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_LONG).show();
                    }
                    else{
                        int imageWidth = bitmap.getWidth();
                        int imageHeight = bitmap.getHeight();

                        //Display display = getActivity().getWindowManager().getDefaultDisplay();
                        //Point size = new Point();
                        display.getSize(size);
                        int width = size.x - (size.x/3);
                        int height = size.y - (size.y/3);

                        int newWidth = width; //this method should return the width of device screen.
                        float scaleFactor = (float)newWidth/(float)imageWidth;
                        int newHeight = (int)(imageHeight * scaleFactor);

                        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
                        //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_LONG).show();
                    }

                    ((HeaderViewHolder) holder).instructorPhoto.setImageBitmap(bitmap);



                } catch(Exception e) {
                    e.getMessage();
                    //return null;
                }

                //========================


//                headerHolder.headerBobot.setText("Bobot: " + surveyHeader.getSurveyBobot());

            } else if (holder instanceof FooterViewHolder) {
                final FooterViewHolder footerHolder = (FooterViewHolder) holder;

                UserAnswerRealmHelper userAnswerRealmHelper = new UserAnswerRealmHelper(context);
                UserRealmHelper userRealmHelper = new UserRealmHelper(context);

                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();
                String NIK = usr.get(0).getEmpNIK();
                String username = usr.get(0).getUsername();
                String RToken = usr.get(0).getToken();



                footerHolder.footerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(context, "HEY_HO", Toast.LENGTH_SHORT).show();

                        List<UserAnswer> luserAnswer = new ArrayList<>();

                        footerHolder.footertext.setFocusableInTouchMode(true);
                        footerHolder.footertext.requestFocus();

                       // linearLayoutContainer.setFocusableInTouchMode(true);
                       //linearLayoutContainer.requestFocus();

                        int answerr = 0;
                        int answerrEssay = 0;
                        int answerrCheckBox = 0;

                        for (int i = 0; i < questions.size(); i++) {
                            UserAnswer userAnswer = new UserAnswer();
                            if (questions.get(i).getQuestiontype().equals("M") && questions.get(i).getCheckedId() > 0) {
                                answerr++;

                                userAnswer.setAnswerID(Integer.toString(questions.get(i).getCheckedId()));
                                userAnswer.setAnswerEssay("");
                                userAnswer.setEmpNIK(NIK);
                                userAnswer.setEventId(eventID);
                                userAnswer.setLastUpdateBy(username);
                                userAnswer.setQuestionID(questions.get(i).getSurveyQuestionID());
                                userAnswer.setSurveyID(surveyID);
                                userAnswer.setSessionId(sessionId);
                                //Toast.makeText(context, ": "+userAnswer.getQuestionID(), Toast.LENGTH_SHORT).show();

                                //Toast.makeText(context, "id: "+questions.get(i).getOptions().get(questions.get(i).getCheckedId()-1), Toast.LENGTH_SHORT).show();
                            } else if (questions.get(i).getQuestiontype().equals("E") && questions.get(i).getChoice() != null) {
                                answerrEssay++;

                                userAnswer.setAnswerID("0");
                                userAnswer.setAnswerEssay(questions.get(i).getChoice());
                                userAnswer.setEmpNIK(NIK);
                                userAnswer.setEventId(eventID);
                                userAnswer.setLastUpdateBy(username);
                                userAnswer.setQuestionID(questions.get(i).getSurveyQuestionID());
                                userAnswer.setSurveyID(surveyID);
                                userAnswer.setSessionId(sessionId);

                                // Toast.makeText(context, ": "+userAnswer.getQuestionID(), Toast.LENGTH_SHORT).show();
                            } else if (questions.get(i).getQuestiontype().equals("C")) {
                                int ctr = 0;
//                            for(int j=0;j<lcbOption.get(i).getCbOptions().size();j++) {
//                                if (!lcbOption.get(i).getCbOptions().get(j).equals("")) {
//                                    answerrCheckBox++;
//                                    ctr++;
//                                    Toast.makeText(context, "Position: " + i + " : " + lcbOption.get(i).getCbOptions().get(j), Toast.LENGTH_SHORT).show();
//                                }
//                            }
                            }
                            luserAnswer.add(userAnswer);
                        }

                        String json = new Gson().toJson(luserAnswer);
                       // Toast.makeText(context, " : " + Integer.toString(luserAnswer.size()), Toast.LENGTH_LONG).show();

                        //======================
                        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                        Call<List<UserAnswer>> call = apiService.postUserAnswer(luserAnswer, "Bearer " + RToken);
                        call.enqueue(new Callback<List<UserAnswer>>() {
                            @Override
                            public void onResponse(Call<List<UserAnswer>> call, Response<List<UserAnswer>> response) {
                                Toast.makeText(context, response.errorBody() + ": All Answers has been submitted!!", Toast.LENGTH_SHORT).show();
                                callHomeDetail();
                            }

                            @Override
                            public void onFailure(Call<List<UserAnswer>> call, Throwable t) {
                                Log.e(TAG, t.toString());
                                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                               callHomeDetail();

                            }
                        });
                        //=========================

                    }
                });
            } else if (holder instanceof ItemViewHolder) {
                holder.setIsRecyclable(false);
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                QuestionAnswerSurvey current = questions.get(position - 1);
                itemViewHolder.setQuestion(current.getQuestion());
                itemViewHolder.setNumber(current.getQuestionSeq());
                itemViewHolder.setOptions(current, position - 1);
                //Log.e(TAG, position + " :onBindViewHolder: " + current.toString());
            } else if (holder instanceof ItemCheckBoxViewHolder) {
                holder.setIsRecyclable(false);
                final ItemCheckBoxViewHolder itemCheckBoxViewHolder = (ItemCheckBoxViewHolder) holder;
                QuestionAnswerSurvey current = questions.get(position - 1);
                itemCheckBoxViewHolder.setQuestion(current.getQuestion());
                itemCheckBoxViewHolder.setNumber(current.getQuestionSeq());
                itemCheckBoxViewHolder.setOptions(current, position - 1);
                //Log.e(TAG, position + " :onBindViewHolder: " + current.toString());
            } else if (holder instanceof ItemEssayViewHolder) {
                final ItemEssayViewHolder itemEssayViewHolder = (ItemEssayViewHolder) holder;
                QuestionAnswerSurvey current = questions.get(position - 1);
                itemEssayViewHolder.setQuestion(current.getQuestion());
                itemEssayViewHolder.setNumber(current.getQuestionSeq());
                itemEssayViewHolder.setAnswer(current, position - 1);
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
            if(questions.get(position-1).getQuestiontype().equals("M")){
                returned = TYPE_ITEM;
            }
            else if(questions.get(position-1).getQuestiontype().equals("E")){
                returned = TYPE_ITEM_ESSAY;
            }
            else if(questions.get(position-1).getQuestiontype().equals("C")){
                returned = TYPE_ITEM_CHECK;
            }
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
        TextView headerTitle;
        ImageView instructorPhoto;
//        TextView headerBobot;

        public HeaderViewHolder(View view) {
            super(view);
            headerTitle = (TextView) view.findViewById(R.id.tvHeaderTitle);
            instructorPhoto = (ImageView) view.findViewById(R.id.img_instructor);

            if(isFeedbackInstructor.equals("YES")){
                instructorPhoto.setVisibility(View.VISIBLE);
            }
//            headerBobot = (TextView) view.findViewById(R.id.tvBobot);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        Button footerButton;
        TextView footertext;

        public FooterViewHolder(View view) {
            super(view);

            footerButton = (Button) view.findViewById(R.id.btnSubmitSurvey);
            footertext = (TextView) view.findViewById(R.id.tvFooterTitle);
        }
    }

    private class ItemEssayViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewQuestion, textViewNumber;
        protected EditText edtAnswer;

        public ItemEssayViewHolder(View itemView) {
            super(itemView);
            linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container2);
//            textViewQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            //edtAnswer = (EditText) itemView.findViewById(R.id.edtAnswer);

            //EditText et = new EditText(context);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            p.leftMargin=55;
            edtAnswer = new EditText(context);
            edtAnswer.setWidth(50);
            //edtAnswer.setBackground(R.style.Widget_AppCompat_EditText);
            edtAnswer.setId(View.generateViewId());
            edtAnswer.setHint("Fill in the blank...");
            edtAnswer.setLayoutParams(p);
            edtList.add(edtAnswer);
            //editText.setPadding(20, 20, 20, 20);
            linearLayoutContainer.addView(edtAnswer);
        }

        public void setQuestion(String question) {
//            textViewQuestion.setText(question);
//            textViewQuestion.setVisibility(View.GONE);
            edtAnswer.setHint(question);
        }

        public void setNumber(long id) {
            textViewNumber.setText(Long.toString(id)+". ");
        }
        public void setAnswer(QuestionAnswerSurvey question, int position) {


            if(question.isAnswered()) {
               edtAnswer.setText(question.getChoice());

            } else {
                edtAnswer.setText("");
            }

            edtAnswer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        QuestionAnswerSurvey que = questions.get(position);
                        if(edtAnswer.getText().toString().length()>0) {
                            que.setAnswered(true);
                            que.setChoice(edtAnswer.getText().toString());
                        }
                    }
                }
            });
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer;
        private TextView textViewQuestion, textViewNumber;
        private RadioGroup radioGroupOptions;
        private RatingBar rating;

        public ItemViewHolder(View itemView) {
            super(itemView);
            linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container);
            textViewQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            radioGroupOptions = (RadioGroup) itemView.findViewById(R.id.radioGroup_score);
            rating = (RatingBar) itemView.findViewById(R.id.rating);

                    rating.setNumStars(numbRadio);
                    //rating.setRating(numbRadio);
                    rating.setStepSize(1);
                    rating.setIsIndicator(false);
                    //linearLayoutContainer.addView(rtb);


//                    int i=0;
//                    for (i = 1; i <= numbRadio; i++) {
//                        rdbtn = new RadioButton(context);
//                        rdbtn.setId(i);
//                        rdbtn.setText("Radio " + rdbtn.getId());
//                        rdbtn.findViewById(rdbtn.getId());
//                        radioGroupOptions.addView(rdbtn);
//                    }
//                    //radioGroupOptions.addView(ll);
//                    RadioButtonList.add(radioGroupOptions);

        }

        public void setQuestion(String question) {
            textViewQuestion.setText(question);
        }

//        public void setAnswer(String answer) {
//            textViewAnswer.setText(answer);
//        }

        public void setNumber(long id) {
            textViewNumber.setText(Long.toString(id)+". ");
        }

        public void setOptions(QuestionAnswerSurvey question, int position) {
//            radioGroupOptions.setTag(position);
//
//
//            for (int i = 0; i < radioGroupOptions .getChildCount(); i++) {
//                try{
//                    ((RadioButton) radioGroupOptions.getChildAt(i)).setText(question.getSurveyAnswers().get(i).getAnswer());
//                }
//                catch(Exception e){
//                    ((RadioButton) radioGroupOptions.getChildAt(i)).setVisibility(View.GONE);
//                }
//
//            }

            Log.e(TAG, position + " :setOptions: " + question.toString());

            if(question.isAnswered()) {
                rating.setRating(question.getCheckedId());
//                radioGroupOptions.check(question.getCheckedId());

            } else {
                rating.setRating(0);
//                radioGroupOptions.check(-1);
            }
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    QuestionAnswerSurvey que = questions.get(position);
                    que.setAnswered(true);
                    que.setCheckedId((int)rating);
                }
            });
//            radioGroupOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(RadioGroup group, int checkedId) {
//                    int pos = (int) group.getTag();
//                    QuestionAnswerSurvey que = questions.get(pos);
//
//                    int selectedId = group.getCheckedRadioButtonId();
//
//                    que.setAnswered(true);
//                    que.setCheckedId(checkedId);
//
//                    Log.e(TAG, pos + " :onCheckedChanged: " + que.toString());
//
//                }
//            });
        }
        }



    private class ItemCheckBoxViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayoutContainer;
        private ListView lv;
        private TextView textViewQuestion, textViewNumber;

        public ItemCheckBoxViewHolder(View itemView) {
            super(itemView);
            linearLayoutContainer = (LinearLayout) itemView.findViewById(R.id.linear_layout_container2);
            textViewQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            textViewNumber = (TextView) itemView.findViewById(R.id.tvNumber);
            //lv = (ListView) itemView.findViewById(R.id.listCheckbox);

            int i=0;
            for(i = 1; i <= numbRadio; i++) {
                TableRow row =new TableRow(context);
                //row.setId(i);
                //row.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                CheckBox checkBox = new CheckBox(context);
                //checkBox.setOnCheckedChangeListener(context);
                checkBox.setId(i);
                checkBox.setText("Checkbox: "+checkBox.getId());
                //row.addView(checkBox);
                linearLayoutContainer.addView(checkBox);
            }


        }

        public void setQuestion(String question) {
            textViewQuestion.setText(question);
        }


        public void setNumber(long id) {
            textViewNumber.setText(Long.toString(id)+". ");
        }

        public void setOptions(QuestionAnswerSurvey question, int position) {
            linearLayoutContainer.setTag(position);
             int i = 0;
            for (i = 0; i < linearLayoutContainer .getChildCount(); i++) {
                try{

                    View v = linearLayoutContainer.getChildAt(i);
                    if (v instanceof CheckBox) {
                        ((CheckBox) linearLayoutContainer.getChildAt(i)).setText(question.getSurveyAnswers().get(i).getAnswer());
                        int j = i;
                        if(!lcbOption.get(position).getCbOptions().get(i).equals("")) {
                            ((CheckBox) linearLayoutContainer.getChildAt(i)).setChecked(true);
                        } else {
                            ((CheckBox) linearLayoutContainer.getChildAt(i)).setChecked(false);
                         }

                        ((CheckBox) linearLayoutContainer.getChildAt(i)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked){
                                    lcbOption.get(position).getCbOptions().set(j, ((CheckBox) linearLayoutContainer.getChildAt(j)).getText().toString());
                                   // Toast.makeText(context, lcbOption.get(position).getCbOptions().get(j), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    lcbOption.get(position).getCbOptions().set(j,"");
                                    //Toast.makeText(context,"NOO", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                catch(Exception e){

                    View v = linearLayoutContainer.getChildAt(i);
                    if (v instanceof CheckBox) {
                        ((CheckBox) linearLayoutContainer.getChildAt(i)).setVisibility(View.GONE);
                    }
                    }
            }

            Log.e(TAG, position + " :setOptions: " + question.toString());


        }

    }

    private void callHomeDetail(){


        RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();

        routingHomeDetailInterface.routingHomeDetail(eventType,context,eventID);

        fr = new HomeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("ConnState", ConnectivityReceiver.isConnected());
        bundle.putString("id",eventID);
        fr.setArguments(bundle);
        fm = ((FragmentActivity)context).getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.fragment_frame, fr);
        ft.addToBackStack(null);
        ft.commit();
        Toast.makeText(context,"Home Detail Area",Toast.LENGTH_LONG).show();

        frr = new TabLayoutFragment();
        fmm = ((FragmentActivity)context).getSupportFragmentManager();
        ftt = fmm.beginTransaction();
        ftt.add(R.id.fragment_tablayout, frr);
        //ftt.add(frr);
        ftt.commit();
    }
}
