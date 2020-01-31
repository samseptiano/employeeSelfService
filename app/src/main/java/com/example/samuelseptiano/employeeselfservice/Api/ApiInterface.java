package com.example.samuelseptiano.employeeselfservice.Api;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.EventSession.EventSession;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.ImportDevPlanModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.PostApproveDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpJobLvlModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpLocationModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpOrgModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.PAPeriodeModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.TextEvidenceModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIDashboard;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIStatusPostPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIUserAnswerListPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.KPISettingSettingResponse;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.MKPI;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.MKualitatif;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
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
import com.example.samuelseptiano.employeeselfservice.Model.VersionModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;


public interface ApiInterface {

    //================================================ GET TOKEN =======================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @POST("pa/api/token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);
    //==================================================================================================================================
    //================================================ GET TOKEN =======================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @POST("pa/api/version")
    Call<List<VersionModel>> getVersion(@Body UserLogin userLogin);
    //==================================================================================================================================

    //===================================
    //==          TRAINING             ==
    //===================================
    //================================================= TRAINING API ===================================================================
    //===========================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"}) //walaupun post, tetapi tetap return value biodata user
    @HTTP(method = "POST", path = "api/User", hasBody = true)
    Observable<Response<User>> getUserDetail(@Body userBodyParameter userId, @Header("Authorization") String auth);
    //===========================================================
    //===========================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("event/{NIK}")
//    Observable<List<Home>> getAllHomeNews(@Path("NIK") String nik,@Header("Authorization") String auth);
    @GET("api/event/page/{page}/{pagesize}")
    Call<List<Home>> getAllHomeNews(@Path("page") int page, @Path("pagesize") int pagesize,@Query("eventname") String eventName, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/event/{NIK}/{id}")
    Observable<List<Home>> getHomeNews(@Path("NIK") String NIK,@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: localhost:18471"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("eventPeserta")
//    Call<EventUser> postUserEvents(@Body EventUser user_events, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/QuestionAnswer/{id}")
    Observable<QuestionAnswerResponse> getQuestionAnswerSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //=============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45459"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/survey/{id}")
    Observable<Survey> getSurveyID(@Path("id") String id, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/eventSession/{id}/{nik}")
    Observable<List<EventSession>> getEventSessionID(@Path("id") String id, @Path("nik") String nik, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/eventPesertaAbsen")
    Call<EventAbsentUser>postEventAbsentUser(@Body EventAbsentUser eventAbsentUser, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/eventPesertaAbsen/{id}/{page}/{pagesize}")
    Call<List<EventAbsentUser>>getEventAbsentUserID(@Path("id") String id, @Path("page") int page, @Path("pagesize") int pagesize, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/surveyAnswerPeserta")
    Call<List<UserAnswer>>postUserAnswer(@Body List<UserAnswer> lUserAnswer, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Streaming
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/certificate/{id}/{eventId}")
    Observable<String>getCertificate(@Path("id") String id, @Path("eventId") String eventId, @Header("Authorization") String auth);
    //============================================================
    //==================================================================================================================================


    //===================================
    //==    PENILAIAN KARYAWAN         ==
    //===================================
    //==================================================== PK API ======================================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/userList/PJ/{empNIK}")
    Observable<List<UserList>>getuserListPJ(@Path("empNIK") String positionId, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/PKTrans/{NIK}")
    Observable<KPIHeaderPJ> getKPIHeaderPJ(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
@Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/PKTrans")
    Observable<Response<Void>> postKPIAnswerPJ(@Body KPIUserAnswerListPJ kpiUserAnswerListPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/PKTrans/status")
    Call<Void> postKPIAnswerStatusPJ(@Body KPIStatusPostPJ kpiStatusPostPJ, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/PKTrans/delAtt")
    Observable<Response<Void>> delKPIAnswerAttPJ(@Body DelImageUploadModel delImageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/PKTrans/posAtt")
    Observable<Response<Void>> posKPIAnswerAttPJ(@Body ImageUploadModel imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/PKTrans/evidences/{transid}/{kpino}")
    Observable<List<ImageUploadModel>> getEvidences(@Path("transid") String transid, @Path("kpino") String kpino, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/PKTrans/dashboard/{aprempnik}")
    Call<KPIDashboard> getKPIDashboardPJ(@Path("aprempnik") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //==================================================================================================================================

    //===================================
    //==          PA Tahunan           ==
    //===================================
    //=============================================== PA TAHUNAN API ===================================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/pa/{empNIK}/{tahun}")
    Call<List<KPIApproveList>>getuserList(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/pa/settingemployee/{empNIK}/{tahun}")
    Call<List<KPIApproveList>>getuserListWithSelf(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //============================================================

    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/{NIK}/{tahun}")
    Observable<KPIHeader> getKPIHeaderPA(@Path("NIK") String empNIk, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/evidences/{PAID}/{KPIID}/{kpiType}/{semester}")
    Call<List<ImageUploadModelPA>> getKPIEvidencePA(@Path("PAID") String paid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType,@Path("semester") String semester, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/textEvidences/{PAID}/{KPIID}/{kpiType}/{semester}")
    Call<List<TextEvidenceModel>> getKPITextEvidencePA(@Path("PAID") String paid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType, @Path("semester") String semester, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/postEvidences")
    Call<ImageUploadModelPostPA> postKPIEvidencePA(@Body ImageUploadModelPostPA imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/deleteEvidences")
    Call<ImageUploadModelPostPA> delKPIEvidencePA(@Body ImageUploadModelPostPA imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/postKPI")
    Call<KPIAnswerList> postKPIAnswer(@Body KPIAnswerList lKpiAnswer, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/statusPA")
    Call<KPIAnswerList> postStatusPA(@Body KPIAnswerList kpiAnswerList, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/emporg/{empNIK}/{tahun}")
    Call<List<EmpOrgModel>>getEmpOrg(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/location/{empNIK}/{tahun}")
    Call<List<EmpLocationModel>>getEmpLocation(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/joblevel/")
    Call<List<EmpJobLvlModel>>getEmpJobLvl( @Header("Authorization") String auth);
    //===========================================================================
//===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/jobtitle/{empNIK}/{tahun}")
    Call<List<EmpJobTtlModel>>getEmpJobTtl(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
    //===========================================================================


    @Multipart
    @POST("pa/api/paTrans/evidences/post")
    Call<String>uploadFile(@Part MultipartBody.Part file,
                           @Part("fileName") RequestBody name,
                           @Part("extension") RequestBody ext,
                           @Part("PAID") RequestBody paid,
                           @Part("SEMESTER") RequestBody semester,
                           @Part("EMPNIK") RequestBody empNIK,
                           @Part("COMPID") RequestBody compId,
                           @Part("KPIID") RequestBody kpiId);

    @Streaming
    @GET("pa/api/paTrans/evidences/dl/{fileName}/{ext}")
    Call<ResponseBody>getFile(@Path("fileName") String filename, @Path("ext") String ext);


    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/pa/devplan/{empNIK}")
    Call<List<UserListDevPlan>>getuserListDevPlan(@Path("empNIK") String empNIK, @Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/userList/pa/devplan/import")
    Call<Void>postImportDevPlan(@Body ImportDevPlanModel ImportDevPlanModel, @Header("Authorization") String auth);
    //============================================================


    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/userList/pa/devplan/{empNIK}/{nikbawahan}")
    Call<List<UserListDevPlan>>getuserListDevPlanOne(@Path("empNIK") String empNIK,@Path("nikbawahan") String nikbawahan, @Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/userList/pa/devplan/approve")
    Call<Void>approveDevPlan(@Body PostApproveDevPlan postApproveDevPlan, @Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakpisetting/{empNIK}")
    Call<List<PASettingHeader>>getPAKPISettingHeader(@Path("empNIK") String empNIK,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakpisetting/one/{tempKPIID}")
    Call<List<PASettingHeader>>getPAKPISettingHeaderOne(@Path("tempKPIID") String id,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpisetting/post")
    Call<Void>postPAKPISettingHeader(@Body List<PASettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakpisetting/mkpi/{tempKPIID}")
    Call<List<MKPI>>getMKPI(@Path("tempKPIID") String tempKPIID,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakpisetting/mkpi/")
    Call<List<MKPI>>getMKPIALL(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpisetting/header/del")
    Call<Void>deletePAKPISettingHeader(@Body List<PASettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
   //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpisetting/detail/del")
    Call<Void>deletePAKPISettingDetail(@Body List<PASettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpisetting/setting/insert")
    Call<List<KPISettingSettingResponse>>postPAKPISettingSetting(@Body List<PASettingHeader> paSettingHeaderList, @Header("Authorization") String auth);
    //============================================================
//============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/patrans/pakpisetting/pic/mail/")
    Call<Void>sendEmailtoPIC(@Body List<EmailSentModel> emailSentModels, @Header("Authorization") String auth);
    //============================================================

    //==================================================================================================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakualitatifsetting")
    Call<List<PASettingHeaderKualitatif>>getPAKualitatifSettingHeader(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakualitatifsetting/{tempCompID}")
    Call<List<PASettingHeaderKualitatif>>getPAKualitatifSettingHeaderOne(@Path("tempCompID") String tempCompID,@Header("Authorization") String auth);
    //============================================================

//    //============================================================
////    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("pa/api/paTrans/pakpisetting/post")
//    Call<Void>postPAKPISettingHeader(@Body List<PASettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
//    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakualitatifsetting/mkualitatif/{tempKualitatifID}")
    Call<List<MKualitatif>>getMKualitatif(@Path("tempKualitatifID") String tempKualitatifID,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakualitatifsetting/mkualitatif/")
    Call<List<MKualitatif>>getMKualitatifALL(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakualitatifsetting/header/del")
    Call<Void>deletePAKPISettingHeaderKualitatif(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
    //    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakualitatifsetting/post")
    Call<Void>postPAKPISettingHeaderKualitatif(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
 //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakualitatifsetting/detail/del")
    Call<Void>deletePAKPISettingDetailKualitatif(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakualitatifsetting/setting/insert")
    Call<Void>postPAKPISettingSettingKualitatif(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/pakpiemployee/{kpitype}/{paid}")
    Call<List<PAEmployeeDetail>>getPAKPIEmployee(@Path("kpitype") String kpitype, @Path("paid") String paid, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpiemployee/post")
    Call<Void>postPAKPIEmployee(@Body List<PAEmployeeDetail> paEmployeeDetails, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    //    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/pakpiemployee/delete")
    Call<Void>deletePAKPIEmployee(@Body List<PAEmployeeDetail> paEmployeeDetails,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/paperiode")
    Call<List<PAPeriodeModel>>getPAPeriode(@Header("Authorization") String auth);
    //============================================================
//    //============================================================
////    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("pa/api/paTrans/importkpi")
//    Call<String>importTemplatekpi(@Body List<TEMPKPIIMPORT> tempkpiimportList,@Header("Authorization") String auth);
//    //============================================================
//    //============================================================
////    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("pa/api/paTrans/importkualitatif")
//    Call<String>importTemplatekualitatif(@Body List<TEMPKPIIMPORT> tempkpiimportList,@Header("Authorization") String auth);
//    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pa/api/paTrans/importtemplate")
    Call<Void>importTemplate(@Body List<PaImportModel> paImportModels, @Header("Authorization") String auth);
    //============================================================
}
