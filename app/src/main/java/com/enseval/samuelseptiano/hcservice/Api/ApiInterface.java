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
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.ImageUploadModelPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.ImageUploadModelPostPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIAnswerListPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIApproveListPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.PJ.KPIResultScorePJ;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.EmailSentModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.KPISettingSettingResponse;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.MKPI;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.MKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PAEmployeeDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PASettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPA.PASetting.PaImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.MKPIPK;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKEmployeeDetail;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeader;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PKSettingHeaderKualitatif;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.SetupPK.PKSetting.PkImportModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.QuestionAnswerResponse;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.Survey;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.Survey.UserAnswer;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.User;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.User.userBodyParameter;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLogin;
import com.enseval.samuelseptiano.hcservice.Model.TokenAuthModel.UserLoginResponse;
import com.enseval.samuelseptiano.hcservice.Model.VersionModel;
import com.enseval.samuelseptiano.hcservice.Model.WhatsappLog;

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
    Observable<List<EmpOrgModel>>getEmpOrg(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
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
    Observable<List<EmpJobTtlModel>>getEmpJobTtl(@Path("empNIK") String empNIK, @Path("tahun") String tahun, @Header("Authorization") String auth);
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
    @POST("pa/api/paTrans/pakpisetting/post/one")
    Call<Void>postPAKPISettingHeaderOne(@Body List<PASettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
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
//    Call<Void>postPAKPISettingHeader(@Body List<PKSettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
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
    @POST("pa/api/paTrans/pakualitatifsetting/post/one")
    Call<Void>postPAKPISettingHeaderKualitatifOne(@Body List<PASettingHeaderKualitatif> paSettingHeaderList,@Header("Authorization") String auth);
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
    @POST("pa/api/paTrans/pakpiemployee/post/one")
    Call<Void>postPAKPIEmployeeOne(@Body List<PAEmployeeDetail> paEmployeeDetails, @Header("Authorization") String auth);
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
    @GET("api/distnormal/{empNIK}/{privilegeCode}/{tahun}")
    Call<List<DistNormalH>>getDistNormalH(@Path("empNIK") String empNIK,@Path("privilegeCode") String privilegeCode,@Path("tahun") String tahun, @Header("Authorization") String auth);
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
    @GET("api/distnormal/{dnid}/{page}/{pagesize}/{search}")
    Call<List<PerhitunganPAEMPModel>>getDistNormalDNonRX(@Path("dnid") String dnid, @Path("page") int page, @Path("pagesize") int pagesize,@Path("search") String search, @Header("Authorization") String auth);
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
    //============================================================
    @Streaming
    @GET("api/distnormal/export/{dnID}")
    Call<ResponseBody>exportDN(@Path("dnID") String dnID);
    //============================================================
    //============================================================
    @Multipart
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/distnormal/import/post/")
    Call<String>ImportDN(@Part MultipartBody.Part file,
                           @Part("fileName") RequestBody name,
                           @Part("extension") RequestBody ext,
                           @Part("DNID") RequestBody dnid,
                         @Part("username") RequestBody username);
    //============================================================
    //============================================================
    @Multipart
    @POST("api/activityplan/")
    Call<String>uploadAP(@Part MultipartBody.Part file,
                           @Part("fileName") RequestBody name,
                           @Part("extension") RequestBody ext,
                           @Part("PAID") RequestBody paid,
                           @Part("APID") RequestBody apid,
                           @Part("UPDUSER") RequestBody upduser,
                           @Part("folder") RequestBody folder);
    //============================================================


    //===================================
    //==          PK Karyawan           ==
    //===================================
    //=============================================== PK KARYAWAN API ===================================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/userList/{empNIK}/{periodeAwal}/{periodeAkhir}/{empType}")
    Call<List<KPIApproveListPJ>>getuserListPK(@Path("empNIK") String empNIK, @Path("periodeAwal") String periodeAwal, @Path("periodeAkhir") String periodeAkhir, @Path("empType") String empType, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/userList/pk/settingemployee/{empNIK}/{empType}")
    Call<List<KPIApproveListPJ>>getuserListPKWithSelf(@Path("empNIK") String empNIK, @Path("empType") String empType, @Header("Authorization") String auth);
    //============================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/{NIK}/")
    Observable<KPIHeaderPJ> getKPIHeaderPK(@Path("NIK") String empNIk, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/evidences/{PKID}/{KPIID}/{kpiType}/")
    Call<List<ImageUploadModelPK>> getKPIEvidencePK(@Path("PKID") String pkid, @Path("KPIID") String kpiid, @Path("kpiType") String kpiType, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/deleteEvidences")
    Call<Void> delKPIEvidencePK(@Body ImageUploadModelPostPK imageUploadModel, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/postKPI")
    Call<Void> postKPIAnswerPK(@Body KPIAnswerListPK lKpiAnswer, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/statusPK")
    Call<Void> postStatusPK(@Body KPIAnswerListPK kpiAnswerList, @Header("Authorization") String auth);
    //===========================================================================
    //===========================================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/resultPK/{PKID}")
    Call<List<KPIResultScorePJ>> getResultPK(@Path("PKID") String pkID, @Header("Authorization") String auth);
    //===========================================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/{empNIK}")
    Call<List<PKSettingHeader>>getPAKPISettingHeaderPK(@Path("empNIK") String empNIK, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/one/{tempKPIID}")
    Call<List<PKSettingHeader>>getPAKPISettingHeaderPKOne(@Path("tempKPIID") String id,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpisetting/post")
    Call<Void>postPAKPISettingHeaderPK(@Body List<PKSettingHeader> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpisetting/post/one")
    Call<Void>postPAKPISettingHeaderPKOne(@Body List<PKSettingHeader> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/mkpi/{tempKPIID}")
    Call<List<MKPI>>getPKMKPI(@Path("tempKPIID") String tempKPIID, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/mkpi/")
    Call<List<MKPI>>getPKMKPIALL(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpiemployee/{kpitype}/{pkid}")
    Call<List<PKEmployeeDetail>>getPKKPIEmployee(@Path("kpitype") String kpitype, @Path("pkid") String pkid, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpisetting/header/del")
    Call<Void>deletePAKPISettingHeaderPK(@Body List<PKSettingHeader> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpisetting/detail/del")
    Call<Void>deletePAKPISettingDetailPK(@Body List<PKSettingHeader> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpisetting/setting/insert")
    Call<List<KPISettingSettingResponse>>postPAKPISettingSettingPK(@Body List<PKSettingHeader> pkSettingHeaderList, @Header("Authorization") String auth);
    //============================================================
//============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pktrans/pkkpisetting/pic/mail/")
    Call<Void>sendEmailtoPICPK(@Body List<EmailSentModel> emailSentModels, @Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkualitatifsetting")
    Call<List<PKSettingHeaderKualitatif>>getPAKualitatifSettingHeaderPK(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkualitatifsetting/{tempCompID}")
    Call<List<PKSettingHeaderKualitatif>>getPAKualitatifSettingHeaderPKOne(@Path("tempCompID") String tempCompID,@Header("Authorization") String auth);
    //============================================================

//    //============================================================
////    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @POST("pa/api/paTrans/pakpisetting/post")
//    Call<Void>postPAKPISettingHeader(@Body List<PKSettingHeader> paSettingHeaderList,@Header("Authorization") String auth);
//    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkualitatifsetting/mkualitatif/{tempKualitatifID}")
    Call<List<MKualitatif>>getMKualitatifPK(@Path("tempKualitatifID") String tempKualitatifID, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkualitatifsetting/mkualitatif/")
    Call<List<MKualitatif>>getPKMKualitatifALL(@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkualitatifsetting/header/del")
    Call<Void>deletePAKPISettingHeaderKualitatifPK(@Body List<PKSettingHeaderKualitatif> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
    //    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkualitatifsetting/post")
    Call<Void>postPAKPISettingHeaderKualitatifPK(@Body List<PKSettingHeaderKualitatif> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
    //    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkualitatifsetting/post/one")
    Call<Void>postPAKPISettingHeaderKualitatifPKOne(@Body List<PKSettingHeaderKualitatif> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkualitatifsetting/detail/del")
    Call<Void>deletePAKPISettingDetailKualitatifPK(@Body List<PKSettingHeaderKualitatif> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkualitatifsetting/setting/insert")
    Call<List<KPISettingSettingResponse>>postPAKPISettingSettingKualitatifPK(@Body List<PKSettingHeaderKualitatif> pkSettingHeaderList,@Header("Authorization") String auth);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpiemployee/{kpitype}/{paid}")
    Call<List<PKEmployeeDetail>>getPAKPIEmployeePK(@Path("kpitype") String kpitype, @Path("paid") String paid, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpiemployee/post")
    Call<Void>postPAKPIEmployeePK(@Body List<PKEmployeeDetail> pkEmployeeDetails, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpiemployee/post/one")
    Call<Void>postPAKPIEmployeeOnePK(@Body List<PKEmployeeDetail> pkEmployeeDetails, @Header("Authorization") String auth);
    //============================================================
    //============================================================
    //    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/pkkpiemployee/delete")
    Call<Void>deletePAKPIEmployeePK(@Body List<PKEmployeeDetail> pkEmployeeDetails,@Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/pkTrans/importtemplate")
    Call<Void>importTemplatePK(@Body List<PkImportModel> pkImportModels, @Header("Authorization") String auth);
    //============================================================
    //============================================================

    @Multipart
    @POST("api/pkTrans/evidences/post")
    Call<String>uploadFilePK(@Part MultipartBody.Part file,
                           @Part("fileName") RequestBody name,
                           @Part("extension") RequestBody ext,
                           @Part("PKID") RequestBody pkid,
                           @Part("EMPNIK") RequestBody empNIK,
                           @Part("COMPID") RequestBody compId,
                           @Part("KPIID") RequestBody kpiId);
    //============================================================

    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/mkpi/{tempKPIID}")
    Call<List<MKPIPK>>getMKPIPK(@Path("tempKPIID") String tempKPIID, @Header("Authorization") String auth);
    //============================================================
    //============================================================
//    @Headers({ "Content-Type: application/json;charset=UTF-8","User-Agent: com.example.samuelseptiano.employeeselfservice", "Host: 10.163.202.81:45455"})
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("api/pkTrans/pkkpisetting/mkpi/")
    Call<List<MKPIPK>>getMKPIALLPK(@Header("Authorization") String auth);
    //============================================================

    //=========================== WHATSAPP API SEND MESSAGE =========================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("chat")
    Call<Void>sendWhatsappAPI(@Query("token") String token,@Query("uid") String uid,@Query("to") String to,@Query("custom_uid") String custom_uid, @Query("text") String text);
    //============================================================
    //============================================================
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/whatsapp/sendlog")
    Call<Void>sendWhatsappLog(@Body WhatsappLog whatsappLog, @Header("Authorization") String auth);
    //============================================================

}
