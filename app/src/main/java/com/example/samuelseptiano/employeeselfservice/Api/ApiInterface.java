package com.example.samuelseptiano.employeeselfservice.Api;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIStatusModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIUserAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIDashboard;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIStatusPostPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIUserAnswerListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.Survey;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Survey.UserAnswer;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.User;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.userBodyParameter;
import com.example.samuelseptiano.employeeselfservice.Model.DelImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLogin;
import com.example.samuelseptiano.employeeselfservice.Model.TokenAuthModel.UserLoginResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    //================================================ GET TOKEN =======================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice"})
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @POST("token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);
    //==================================================================================================================================

    //===================================
    //==          TRAINING             ==
    //===================================
    //================================================= TRAINING API ===================================================================
    //===========================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"}) //walaupun post, tetapi tetap return value biodata user
    @HTTP(method = "POST", path = "User", hasBody = true)
    Observable<Response<User>> getUserDetail(@Body userBodyParameter userId, @Header("Authorization") String auth);
    //===========================================================
    //===========================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Observable<List<Home>> getAllHomeNews(@Path("NIK") String nik,@Header("Authorization") String auth);
    @GET("event/page/{page}/{pagesize}")
    Call<List<Home>> getAllHomeNews(@Path("page") int page, @Path("pagesize") int pagesize,@Query("eventname") String eventName, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"}10.163.202.81
    @GET("event/{NIK}/{id}")
    Observable<List<Home>> getHomeNews(@Path("NIK") String NIK,@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("eventPeserta")
//    Call<EventUser> postUserEvents(@Body EventUser user_events, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("QuestionAnswer/{id}")
    Observable<QuestionAnswerResponse> getQuestionAnswerSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //=============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45459"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("survey/{id}")
    Observable<Survey> getSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventSession/{id}/{nik}")
    Observable<List<EventSession>> getEventSessionID(@Path("id") String id, @Path("nik") String nik, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("eventPesertaAbsen")
    Call<EventAbsentUser>postEventAbsentUser(@Body EventAbsentUser eventAbsentUser, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("eventPesertaAbsen/{id}/{page}/{pagesize}")
    Call<List<EventAbsentUser>>getEventAbsentUserID(@Path("id") String id, @Path("page") int page, @Path("pagesize") int pagesize, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("surveyAnswerPeserta")
    Call<List<UserAnswer>>postUserAnswer(@Body List<UserAnswer> lUserAnswer, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Streaming
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("certificate/{id}/{eventId}")
    Observable<String>getCertificate(@Path("id") String id, @Path("eventId") String eventId, @Header("Authorization") String auth);
    //============================================================
    //==================================================================================================================================


    //===================================
    //==    PENILAIAN KARYAWAN         ==
    //===================================
    //==================================================== PK API ======================================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("userList/PJ/{empNIK}")
    Observable<List<UserList>>getuserListPJ(@Path("empNIK") String positionId, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("PKTrans/{NIK}")
    Observable<KPIHeaderPJ> getKPIHeaderPJ(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
@Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans")
    Observable<Response<Void>> postKPIAnswerPJ(@Body KPIUserAnswerListPJ kpiUserAnswerListPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans/status")
    Call<Void> postKPIAnswerStatusPJ(@Body KPIStatusPostPJ kpiStatusPostPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans/delAtt")
    Observable<Response<Void>> delKPIAnswerAttPJ(@Body DelImageUploadModel delImageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("PKTrans/posAtt")
    Observable<Response<Void>> posKPIAnswerAttPJ(@Body ImageUploadModel imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("PKTrans/evidences/{transid}/{kpino}")
    Observable<List<ImageUploadModel>> getEvidences(@Path("transid") String transid, @Path("kpino") String kpino, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
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
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("userList/pa/{empNIK}/{tahun}")
    Call<List<KPIApproveList>>getuserList(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("paTrans/{NIK}/{tahun}")
    Call<KPIHeader> getKPIHeaderPA(@Path("NIK") String empNIk, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("paTrans/evidences/{PAID}/{KPIID}/{kpiType}/{semester}")
    Call<List<ImageUploadModelPA>> getKPIEvidencePA(@Path("PAID") String paid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType,@Path("semester") String semester, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paTrans/postEvidences")
    Call<ImageUploadModelPostPA> postKPIEvidencePA(@Body ImageUploadModelPostPA imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paTrans/deleteEvidences")
    Call<ImageUploadModelPostPA> delKPIEvidencePA(@Body ImageUploadModelPostPA imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paTrans/postKPI")
    Call<KPIAnswerList> postKPIAnswer(@Body KPIAnswerList lKpiAnswer, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("paTrans/statusPA")
    Call<KPIAnswerList> postStatusPA(@Body KPIAnswerList kpiAnswerList, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================


}
