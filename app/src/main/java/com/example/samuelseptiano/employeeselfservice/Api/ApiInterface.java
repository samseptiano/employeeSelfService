package com.example.samuelseptiano.employeeselfservice.Api;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSessionResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventUser.EventUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.HomeResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.InstructorPhoto;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.Survey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.UserAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLogin;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLoginResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Header;


public interface ApiInterface {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);

//==============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    //@GET("User/1")
    @HTTP(method = "POST", path = "User", hasBody = true)
    Call<User> getUserDetail(@Body userBodyParameter userId, @Header("Authorization") String auth);
//===========================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/{NIK}")
    Call<List<Home>> getAllHomeNews(@Path("NIK") String nik,@Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/{NIK}/{id}")
    Call<List<Home>> getHomeNews(@Path("NIK") String NIK,@Path("id") String id, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("eventPeserta")
    Call<EventUser> postUserEvents(@Body EventUser user_events, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("QuestionAnswer/{id}")
    Call<QuestionAnswerResponse> getQuestionAnswerSurveyID(@Path("id") String id, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("survey/{id}")
    Call<Survey> getSurveyID(@Path("id") String id, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventSession/{id}/{nik}")
    Call<List<EventSession>> getEventSessionID(@Path("id") String id, @Path("nik") String nik, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("eventPesertaAbsen")
    Call<EventAbsentUser>postEventAbsentUser(@Body EventAbsentUser eventAbsentUser, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventPesertaAbsen/{id}")
    Call<List<EventAbsentUser>>getEventAbsentUserID(@Path("id") String id, @Header("Authorization") String auth);
//============================================================

//=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("surveyAnswerPeserta")
    Call<List<UserAnswer>>postUserAnswer(@Body List<UserAnswer> lUserAnswer, @Header("Authorization") String auth);
//============================================================

}
