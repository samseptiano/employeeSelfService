package com.example.samuelseptiano.employeeselfservice.Fragment.QuizFragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.QuizAdapter.QuizAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerSurvey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.Survey;
import com.example.samuelseptiano.employeeselfservice.Model.CheckBoxQuizOption;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class QuizFragment extends Fragment {

    View rootView;
    Context context;
    LinearLayout lnProgress;

    private RecyclerView recyclerViewQuestions;
    //private List<Questions> questions = new ArrayList<>();
    private List<QuestionAnswerSurvey> questions = new ArrayList<>();
    private Survey surveyHeader;
    private List<CheckBoxQuizOption> cbOption = new ArrayList<>();

    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getIsFeedbackInstructor() {
        return isFeedbackInstructor;
    }

    public void setIsFeedbackInstructor(String isFeedbackInstructor) {
        this.isFeedbackInstructor = isFeedbackInstructor;
    }

    String isFeedbackInstructor = "NO";

    String surveyID;

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    String eventtype;

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    String eventID;

    public String getInstructorPhoto() {
        return instructorPhoto;
    }

    public void setInstructorPhoto(String instructorPhoto) {
        this.instructorPhoto = instructorPhoto;
    }

    String instructorPhoto;

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    String sessionID = "0";

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    String instructorId = "";

    static int numbRadio;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setSurveyID(bundle.getString("surveyID"));
            setEventID(bundle.getString("eventID"));
            setSessionID(bundle.getString("sessionID"));
            setIsFeedbackInstructor(bundle.getString("isFeedbackInstructor"));
            setInstructorId(bundle.getString("instructorId"));
            setInstructorPhoto(bundle.getString("instructorPhoto"));
            setEventtype(bundle.getString("eventType"));
        }

        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        lnProgress= rootView.findViewById(R.id.linlaHeaderProgressQuiz);

        final ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.quiz_hint_icon);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("Survey");

        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               showDialogHint();
            }
        });

        recyclerViewQuestions = (RecyclerView) rootView.findViewById(R.id.recyclerViewQuiz);
        lnProgress.setVisibility(View.VISIBLE);
        prepareQuestions();
        lnProgress.setVisibility(View.GONE);

        return rootView;
    }

    private void prepareQuestions() {

        //Jenis Soal
        //Multiple
        //Essay
        //Checkbox

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String id = getSurveyID();
        //String id = "1";
        //Toast.makeText(getContext(),"id survey: "+id,Toast.LENGTH_SHORT).show();

        Call<QuestionAnswerResponse> call = apiService.getQuestionAnswerSurveyID(id,"Bearer "+RToken);
        call.enqueue(new Callback<QuestionAnswerResponse>() {
            @Override
            public void onResponse(Call<QuestionAnswerResponse> call, Response<QuestionAnswerResponse> response) {
                int statusCode = response.code();
                try{
                    questions = response.body().getQuestionAnswers();
                    //Toast.makeText(context,Integer.toString(response.body().getQuestionAnswers().get(1).getSurveyAnswers().size()),Toast.LENGTH_SHORT).show();

                    for(int i=0;i<questions.size();i++){
                        if(numbRadio < questions.get(i).getSurveyAnswers().size()) {
                            numbRadio = questions.get(i).getSurveyAnswers().size();
                        }
                    }

                    Call<Survey> call2 = apiService.getSurveyID(id,"Bearer "+RToken);
                    call2.enqueue(new Callback<Survey>() {
                        @Override
                        public void onResponse(Call<Survey> call, Response<Survey> response) {

                            try{
                                surveyHeader = response.body();
                                initQuestionsAdapter(numbRadio);

                            }
                            catch(Exception e){

                            }
                        }

                        @Override
                        public void onFailure(Call<Survey> call, Throwable t) {
                            Log.e(TAG, t.toString());
                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<QuestionAnswerResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });



//        for (int i = 0; i < 30; i++) {
//            List<String> a= new ArrayList<String>();
//            List<String> b= new ArrayList<>();
//            a.clear();
//            b.clear();
//            CheckBoxQuizOption cb = new CheckBoxQuizOption();
//            Questions question = new Questions();
//            question.setId(i + 1);
//            question.setQuestion("Question #" + (i + 1));
//            question.setQuestionType("Essay");
//            if (i % 2 == 1) {
//                question.setQuestionType("Essay");
//                a.add(0, "No Option: ");
//                question.setOptions(a);
//                question.setCorrectOption(new Random().nextInt(3));
//                question.setAnswer("Answer is: " + question.getCorrectOption());
//                b.add("");
//            } else {
//
//                if(i<10){
//                    question.setQuestionType("Checkbox");
//                    for (int j = 0; j < 6; j++) {
//                        a.add(j, "Option: "+ (i + 1) );
//                        b.add("");
//                    }
//                }
//                else {
//                    question.setQuestionType("Checkbox");
//                    for (int j = 0; j < 2; j++) {
//                        a.add(j, "Option: " + (i + 1));
//                        b.add("");
//                    }
//                }
//                cb.setCbOptions(b);
//                question.setOptions(a);
//
//                question.setCorrectOption(new Random().nextInt(3));
//                question.setAnswer("Answer is: " + question.getCorrectOption());
//                if(numbRadio < a.size()) {
//                    numbRadio = a.size();
//                }
//            }
//            cbOption.add(cb);
//            questions.add(question);
//        }
//            initQuestionsAdapter(numbRadio);


    }

    private void initQuestionsAdapter(int numbRadio) {
        recyclerViewQuestions.setLayoutManager(new LinearLayoutManager(context));
        //recyclerViewQuestions.setHasFixedSize(true);
        QuizAdapter questionAdapter = new QuizAdapter(getActivity(), context, questions, numbRadio, cbOption, surveyHeader,getInstructorPhoto(),getSurveyID(),getEventID(),getSessionID(),getIsFeedbackInstructor(),getEventtype());
        recyclerViewQuestions.setAdapter(questionAdapter);
    }

    private void showDialogHint(){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.survey_hint_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvContentHint = dialog.findViewById(R.id.tvHintContent);
        tvContentHint.setText("This Is Survey Hint Content...");
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
