package com.enseval.samuelseptiano.hcservice.Api;

import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApprDNModell;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ApproveRejectDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.DistNormalH;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PerhitunganPAEMPModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.PostDistNormalD;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.DistribusiNormal.ProcessDNModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventAbsentUser.EventAbsentUser;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.EventSession.EventSession;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Home.Home;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan.ImportDevPlanModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan.PostApproveDevPlan;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.DevelopmentPlan.UserListDevPlan;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobLvlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpJobTtlModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpLocationModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIAnswerList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.KPIHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.PAPeriodeModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PA.TextEvidenceModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIDashboard;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIStatusPostPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIUserAnswerListPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.KPISettingSettingResponse;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.MKPI;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.MKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.Survey;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.UserAnswer;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.User;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.UserList;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.userBodyParameter;
import com.enseval.samuelseptiano.hcservice.Model.DelImageUploadModel;
import com.enseval.samuelseptiano.hcservice.Model.ImageUploadModel;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLogin;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLoginResponse;
import com.enseval.samuelseptiano.hcservice.Model.VersionModel;

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
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.enseval.samuelseptiano.hcservice"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @POST("pa/api/token")
    Call<UserLoginResponse> getToken(@Body UserLogin userLogin);
    //==================================================================================================================================
    //================================================ GET TOKEN =======================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.enseval.samuelseptiano.hcservice"})
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
    Call<List<Home>> getAllHomeNews(@Path("page") int page, @Path("pagesize") int pagesize, @Query("eventname") String eventName, @Header("Authorization") String auth);
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
@Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.enseval.samuelseptiano.hcservice", "Host: 10.163.202.81:45455"})
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
    Call<List<ImageUploadModelPA>> getKPIEvidencePA(@Path("PAID") String paid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType, @Path("semester") String semester, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pa/api/paTrans/textEvidences/{PAID}/{KPIID}/{kpiType}/{semester}")
    Call<List<TextEvidenceModel>> getKPITextEvidencePA(@Path("PAID") String paid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType, @Path("semester") String semester, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.enseval.samuelseptiano.hcservice", "Host: 10.163.202.81:45455"})
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
    Call<List<EmpJobLvlModel>>getEmpJobLvl(@Header("Authorization") String auth);
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
    Call<List<PASettingHeader>>getPAKPISettingHeader(@Path("empNIK") String empNIK, @Header("Authorization") String auth);
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
    Call<List<MKPI>>getMKPI(@Path("tempKPIID") String tempKPIID, @Header("Authorization") String auth);
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
    Call<List<MKualitatif>>getMKualitatif(@Path("tempKualitatifID") String tempKualitatifID, @Header("Authorization") String auth);
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
    Call<List<KPISettingSettingResponse>>postPAKPISettingSettingKualitatif(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
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


        //============================= SERVER TESTING DISTRIBUSI NORMAL ==================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/distnormal/{empNIK}/{privilegeCode}")
    Call<List<DistNormalH>>getDistNormalH(@Path("empNIK") String empNIK,@Path("privilegeCode") String privilegeCode, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/distnormal/{dnid}/{page}/{pagesize}")
    Observable<List<PerhitunganPAEMPModel>>getDistNormalD(@Path("dnid") String dnid, @Path("page") int page, @Path("pagesize") int pagesize, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/distnormal/{dnid}/")
    Call<List<ApprDNModell>>getDistNormarAppr(@Path("dnid") String dnid, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/distnormal/process/")
    Call<Void>processDN(@Body ProcessDNModel processDNModel, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/distnormal/updatedistnormald/")
    Call<Void>updateDistNormalD(@Body PostDistNormalD postDistNormalD, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/distnormal/approvedistnormal/")
    Call<Void>approveDistNormal(@Body ApproveRejectDNModel approveRejectDNModel, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/distnormal/rejectdistnormal/")
    Call<Void>rejectDistNormal(@Body ApproveRejectDNModel approveRejectDNModel, @Header("Authorization") String auth);
    //============================================================
}
