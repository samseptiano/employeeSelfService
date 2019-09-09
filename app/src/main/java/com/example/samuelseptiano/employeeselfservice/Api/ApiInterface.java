package com.example.samuelseptiano.employeeselfservice.Api;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventUser.EventUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIDashboard;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIStatusPostPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIUserAnswerListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.Survey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.UserAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLogin;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLoginResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Header;
import retrofit2.http.Streaming;


public interface ApiInterface {

    //================================================ GET TOKEN =======================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice"})
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
    @POST("token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);
    //==================================================================================================================================

    //===================================
    //==          TRAINING             ==
    //===================================
    //================================================= TRAINING API ===================================================================
    //===========================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"}) //walaupun post, tetapi tetap return value biodata user
    @HTTP(method = "POST", path = "User", hasBody = true)
    Call<User> getUserDetail(@Body userBodyParameter userId, @Header("Authorization") String auth);
    //===========================================================
    //===========================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/{NIK}")
    Call<List<Home>> getAllHomeNews(@Path("NIK") String nik,@Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/{NIK}/{id}")
    Call<List<Home>> getHomeNews(@Path("NIK") String NIK,@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("eventPeserta")
    Call<EventUser> postUserEvents(@Body EventUser user_events, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("QuestionAnswer/{id}")
    Call<QuestionAnswerResponse> getQuestionAnswerSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("survey/{id}")
    Call<Survey> getSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventSession/{id}/{nik}")
    Call<List<EventSession>> getEventSessionID(@Path("id") String id, @Path("nik") String nik, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("eventPesertaAbsen")
    Call<EventAbsentUser>postEventAbsentUser(@Body EventAbsentUser eventAbsentUser, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventPesertaAbsen/{id}")
    Call<List<EventAbsentUser>>getEventAbsentUserID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("surveyAnswerPeserta")
    Call<List<UserAnswer>>postUserAnswer(@Body List<UserAnswer> lUserAnswer, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Streaming
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("certificate/{id}/{eventId}")
    Call<ResponseBody>getCertificate(@Path("id") String id, @Path("eventId") String eventId, @Header("Authorization") String auth);
    //============================================================
    //==================================================================================================================================


    //===================================
    //==    PENILAIAN KARYAWAN         ==
    //===================================
    //==================================================== PK API ======================================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("userList/PJ/{positionId}")
    Call<List<UserList>>getuserListPJ(@Path("positionId") String positionId, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("PKTrans/{NIK}")
    Call<KPIHeaderPJ> getKPIHeaderPJ(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
@Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans")
    Call<Void> postKPIAnswerPJ(@Body KPIUserAnswerListPJ kpiUserAnswerListPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans/status")
    Call<Void> postKPIAnswerStatusPJ(@Body KPIStatusPostPJ kpiStatusPostPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("PKTrans/dashboard/{aprempnik}")
    Call<KPIDashboard> getKPIDashboardPJ(@Path("aprempnik") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================

    //===================================
    //==          PA Tahunan           ==
    //===================================
    //=============================================== PA TAHUNAN API ===================================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("userList/{positionId}")
    Call<List<UserList>>getuserList(@Path("positionId") String positionId, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/{NIK}")
    Call<KPIHeader> getKPIHeader(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("event")
    Call<KPIUserAnswerList> postKPIAnswer(@Body KPIUserAnswerList kpiUserAnswerList, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("event/pk/")
    Call<KPIDashboard> getKPIDashboard(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================


}
