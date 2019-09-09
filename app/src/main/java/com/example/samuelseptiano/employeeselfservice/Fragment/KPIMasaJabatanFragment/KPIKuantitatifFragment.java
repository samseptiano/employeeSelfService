package com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIQuestionsPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PJ.KPIStatusPostPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.User.UserList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.FilePath;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KPIKuantitatifFragment extends Fragment implements KPIAdapter.EventListener{

    File photoFile;
    private String mCameraFileName;
    String mCurrentPhotoPath;


    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapter kpiAdapter;
    List<KPIQuestionsPJ> lKPIQuestion;

    TextView tv_result_name,tv_result_nik, tv_result_status, tv_result_positon, tv_result_score;
    RatingBar ratingScore;


    Boolean connState, captureDone = false;
    UserList userlist;
    String type="";
    String semester ="";
    List<KPIQuestionsPJ> question;
    Button editBtn, approveBtn;
    String RToken;

    int postTemp = 0;

    int pos=0;

    String Jenis="";

    String isEditable = "N";

    Activity activity;

    private static final int PICK_FILE_REQUEST = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private List<ImageUploadModel> imgCaptureList = new ArrayList<>();

    public Boolean getConnState() {
        return connState;
    }

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    LinearLayout lnResult,lnQuestions,lnResultButton,lnQuestionsButton,lnProgressBar;

    KPIHeaderPJ kh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //mencegah exposes file uri
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setType(bundle.getString("KPIType"));
            userlist = (UserList) bundle.getSerializable("id");
            Jenis = bundle.getString("jenisKPI");
            semester = bundle.getString("semester");
        }

        requestRead();
        //Toast.makeText(getContext(),"start",Toast.LENGTH_LONG).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("Penilaian Karyawan");
        if (rootView == null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            rootView = inflater.inflate(R.layout.fragment_kpi_kuantitatif, container, false);

            recyclerViewKPI = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPI);
            tv_result_name = (TextView) rootView.findViewById(R.id.tv_result_nama);
            tv_result_nik = (TextView) rootView.findViewById(R.id.tv_result_nik);
            tv_result_status = (TextView) rootView.findViewById(R.id.tv_result_status);
            tv_result_positon = (TextView) rootView.findViewById(R.id.tv_result_position);
            ratingScore = (RatingBar) rootView.findViewById(R.id.ratingBarResult);
            tv_result_score = rootView.findViewById(R.id.tv_result_score);
            lnProgressBar = rootView.findViewById(R.id.linlaHeaderProgress);


            UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
            ArrayList<UserRealmModel> usr;
            usr = userRealmHelper.findAllArticle();
            RToken = usr.get(0).getToken();
            fillKPI(RToken);
            onSaveInstanceState(savedInstanceState);
        }
        else{

        }
        return rootView;
    }


    private void fillKPI(String RToken) {

        lnResult = rootView.findViewById(R.id.ln_result);
        lnQuestions = rootView.findViewById(R.id.ln_questions);
        lnResultButton = rootView.findViewById(R.id.ln_result_button);
        lnQuestionsButton = rootView.findViewById(R.id.ln_questions_button);
        editBtn = rootView.findViewById(R.id.btn_edit_result);
        approveBtn = rootView.findViewById(R.id.btn_approve_result);

        if(!type.equals("Approve")){
            editBtn.setVisibility(View.GONE);
        }

        lnQuestions.setVisibility(View.GONE);

        lnResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnResult.setVisibility(View.VISIBLE);
                lnQuestions.setVisibility(View.GONE);
            }
        });

        lnQuestionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnResult.setVisibility(View.GONE);
                lnQuestions.setVisibility(View.VISIBLE);
            }
        });


        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogApprove();
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable.equals("N")){
                    isEditable = "Y";
                    editBtn.setText("Edit");
                    lnResult.setVisibility(View.GONE);
                    lnQuestions.setVisibility(View.VISIBLE);
                    kpiAdapter.notifyDataSetChanged();
                }
                else if(isEditable.equals("Y")) {
                    isEditable = "N";
                    editBtn.setText("Edit");
                    kpiAdapter.notifyDataSetChanged();

                }
            }
        });



        //================================================================================

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        lnProgressBar.setVisibility(View.VISIBLE);
        editBtn.setEnabled(false);
        approveBtn.setEnabled(false);
        lnQuestionsButton.setEnabled(false);
        //"9859"
        Call<KPIHeaderPJ> call = apiService.getKPIHeaderPJ(userlist.getEmpNiK(),"Bearer "+RToken);
        call.enqueue(new Callback<KPIHeaderPJ>() {
            @Override
            public void onResponse(Call<KPIHeaderPJ> call, Response<KPIHeaderPJ> response) {
                int statusCode = response.code();
                kh= new KPIHeaderPJ();
                kh.setNilai("");
                kh.setSemester(semester);
                kh.setTahun("2019");
                kh.setScoreName(response.body().getScoreName());
                kh.setStatus(response.body().getStatus());
                kh.setStatus2(response.body().getStatus2());
                kh.setStatus1(response.body().getStatus1());
                kh.setPeriodeAwal(userlist.getDateStart());
                kh.setPeriodeAkhir(userlist.getDateEnd());
                kh.setStatusFG(response.body().getStatusFG());
                kh.setLocationName(userlist.getOrgName());
                kh.setAtasanLangsung(userlist.getNamaAtasanLangsung());
                kh.setNIKatasanLangsung(userlist.getNIKAtasanLangsung());
                kh.setAtasanTakLangsung(userlist.getNamaAtasanTakLangsung());
                kh.setNIKatasanTakLansung(userlist.getNIKAtasanTakLangsung());
                kh.setFotoAtasanLangsung(userlist.getFotoAtasanLangsung());
                kh.setFotoAtasanTakLangsung(userlist.getFotoAtasanTakLangsung());
                kh.setBawahanLangsung("");
                kh.setNilai(response.body().getNilai());
                kh.setStar(response.body().getStar());
                kh.setPositionName(userlist.getPositionName());
                kh.setTransId(response.body().getTransId());
                kh.setJobTitle(userlist.getJobTitleName());
                if(getType().equals("Approve")){
                    kh.setEmpName(userlist.getEmpName());
                    kh.setNIK(userlist.getEmpNiK());
                    kh.setDept(userlist.getOrgName());
                }
                else {
                    kh.setEmpName("");
                    kh.setNIK("");
                    kh.setDept("");
                }
                ArrayList <String> a;
                lKPIQuestion = new ArrayList<>();

                for (int i = 0; i < response.body().getKpiQuestionsList().size(); i++) {

                    a = new ArrayList<>();
                    for (int j = 1; j <= 4; j++) {
                        a.add(j+1+": 100% Implementation AP/PA + data General Increase tepat waktu");
                    }

                    KPIQuestionsPJ kq = new KPIQuestionsPJ();
                    kq.setBobot(response.body().getKpiQuestionsList().get(i).getBobot());

                    kq.setKPIcategory(response.body().getKpiQuestionsList().get(i).getKPIcategory());

                    if(i<=3){
                        kq.setPerspective("");
                    }
                    else {
                        kq.setPerspective("");
                    }
                    kq.setKPIDesc(response.body().getKpiQuestionsList().get(i).getKPIDesc());
                    kq.setHint(response.body().getKpiQuestionsList().get(i).getHint());
                    kq.setAnswered(true);
                    kq.setKpiNo(response.body().getKpiQuestionsList().get(i).getKpiNo());
                    kq.setTransId(response.body().getKpiQuestionsList().get(i).getTransId());
                    kq.setCheckedId(Integer.parseInt(response.body().getKpiQuestionsList().get(i).getCompRate()));

//                    kq.setCheckedId(4);

                    kq.setPhotoCapture(response.body().getKpiQuestionsList().get(i).getPhotoCapture());
                    //Toast.makeText(getContext(),response.body().getKpiQuestionsList().get(0).getPhotoCapture().get(0).getImgString(),Toast.LENGTH_LONG).show();

                    kq.setEvidence(response.body().getKpiQuestionsList().get(i).getEvidence());
                    kq.setAnswerenEvidence(true);
                    kq.setTransId(response.body().getKpiQuestionsList().get(i).getTransId());
                    lKPIQuestion.add(kq);
                }
                kh.setKpiQuestionsList(lKPIQuestion);
                initQuestionsAdapter(kh);



                if(!kh.getEmpName().equals("")) {

                    tv_result_name.setText(kh.getEmpName());
                    tv_result_nik.setText(kh.getNIK());
                    tv_result_positon.setText(kh.getPositionName());
                }
                else{
                    tv_result_name.setVisibility(View.GONE);
                    tv_result_nik.setVisibility(View.GONE);
                    tv_result_positon.setVisibility(View.GONE);
                }


//                int total=0;
//                int i=0;
//                for(i=0;i<kh.getKpiQuestionsList().size();i++) {
//                    total += kh.getKpiQuestionsList().get(i).getCheckedId();
//                }
                //ratingScore.setRating((float) total/kh.getKpiQuestionsList().size());
                setFinalScore(kh,RToken);
                    lnProgressBar.setVisibility(View.GONE);
                    editBtn.setEnabled(true);
                    approveBtn.setEnabled(true);
                    lnQuestionsButton.setEnabled(true);
            }
            @Override
            public void onFailure(Call<KPIHeaderPJ> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(),Toast.LENGTH_LONG).show();
//                lnProgressBar.setVisibility(View.GONE);
//                editBtn.setEnabled(true);
//                approveBtn.setEnabled(true);
//                lnQuestionsButton.setEnabled(true);
            }
        });

        //================================================================================

    }

    private void initQuestionsAdapter(KPIHeaderPJ kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        kpiAdapter = new KPIAdapter(getActivity(), getContext(), kh, userlist.getEmpNiK(), getType(),  this, Jenis);
        recyclerViewKPI.setAdapter(kpiAdapter);

    }

    private File createTemporaryFile(String part, String ext) throws Exception
    {
        File tempDir= Environment.getExternalStorageDirectory();
        tempDir=new File(tempDir.getAbsolutePath()+"/.temp/");
        if(!tempDir.exists())
        {
            tempDir.mkdirs();
        }
        return File.createTempFile(part, ext, tempDir);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK)
        {

            try {

                galleryAddPic();


                //Bitmap photo = (Bitmap) data.getExtras().get("data");


                if (pos != postTemp) {
                    if (question.get(pos).getPhotoCapture() != null) {
                        imgCaptureList = new ArrayList<>();
                        for (int i = 0; i < question.get(pos).getPhotoCapture().size(); i++) {
                            imgCaptureList.add(question.get(pos).getPhotoCapture().get(i));
                        }
                        // Toast.makeText(getContext(),"not null", Toast.LENGTH_LONG).show();
                    } else {
                        imgCaptureList = new ArrayList<>();
                    }

                }
                ImageUploadModel ium = new ImageUploadModel();
//
////                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                        Uri selectedImage = data.getData();
//
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
////
////                    // Get the cursor
//                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                        filePathColumn, null, null, null);
//                // Move to first row
//                cursor.moveToFirst();
////
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//
//                //Uri imageUri = data.getData();
//
//                final InputStream imageStream = getContext().getContentResolver().openInputStream(selectedImage);
//                Bitmap photo = BitmapFactory.decodeStream(imageStream);
//
//



                //============================================================================
                //get image URI
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), photo, "Title", null);
//                Uri uri = Uri.parse(path);
//                //============================================================================
//
//                String path2 = "";
//                if (getContext().getContentResolver() != null) {
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContext().getContentResolver().query(uri, proj, null, null, null);
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                        path2 = cursor.getString(idx);
//                        cursor.close();
//                    }
//                }

//                //============================================================================
//                //Convert Bitmap to String

//                File dir = Environment.getExternalStorageDirectory();
//                String pathh = dir.getParent();

//                List<String> arr = new ArrayList<>();
//                arr = Arrays.asList(path2.split("/"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                BitmapFactory.decodeFile(mCurrentPhotoPath).compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
//                String temp = "";
//                //============================================================================

                captureDone = true;

                ium.setImgType(".jpg");
                ium.setImgString(temp);
                Toast.makeText(getContext(),mCurrentPhotoPath,Toast.LENGTH_LONG).show();

                ium.setFilepath(mCurrentPhotoPath);



                imgCaptureList.add(ium);

                question.get(pos).setPhotoCapture(imgCaptureList);
                postTemp = pos;
                if (kpiAdapter != null) {
                    kpiAdapter.notifyDataSetChanged();
                }

                recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity,"Y");
                recyclerViewImg.setAdapter(adapter);
////
//                //cara buat imgCaptureList setiap beda recycler view rest jadi null
            }
            catch (Exception e){
                Toast.makeText(getContext(), "error: "+e.toString(), Toast.LENGTH_LONG).show();

            }
        }

        else if(requestCode == PICK_FILE_REQUEST){
            String temp ="";
            try {

                if (pos != postTemp) {
                    if (question.get(pos).getPhotoCapture() != null) {
                        imgCaptureList = new ArrayList<>();
                        for (int i = 0; i < question.get(pos).getPhotoCapture().size(); i++) {
                            imgCaptureList.add(question.get(pos).getPhotoCapture().get(i));
                        }
                        // Toast.makeText(getContext(),"not null", Toast.LENGTH_LONG).show();
                    } else {
                        imgCaptureList = new ArrayList<>();
                    }

                }
                ImageUploadModel ium = new ImageUploadModel();

                Uri selectedImageUri = data.getData();
                String imagepath = FilePath.getPath(getContext(), selectedImageUri);
                String extension = imagepath.substring(imagepath.lastIndexOf("."));
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger images
                // options.inSampleSize = 10;


                if(extension.equals(".pdf")){
                    File originalFile = new File(imagepath);
                    temp = null;
                    try {
                        FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
                        byte[] bytes = new byte[(int)originalFile.length()];
                        fileInputStreamReader.read(bytes);
                        temp = new String(Base64.encodeToString(bytes,Base64.DEFAULT));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(extension.equals(".jpg") ||extension.equals(".png") ||extension.equals(".jpeg") || extension.equals(".bmp") || extension.equals(".gif")){
                    //============ image =======================
                    final Bitmap bitmap = BitmapFactory.decodeFile(imagepath, options);
                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
                    byte [] b=baos.toByteArray();
                    temp=Base64.encodeToString(b, Base64.DEFAULT);
                    //===========================================
                }

                captureDone = true;
                ium.setImgType(extension);
                ium.setImgString(temp);
                ium.setFilepath(imagepath);

                imgCaptureList.add(ium);

                question.get(pos).setPhotoCapture(imgCaptureList);
                postTemp = pos;
                if (kpiAdapter != null) {
                    kpiAdapter.notifyDataSetChanged();
                }

                recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewImg.setLayoutManager(mLayoutManager);
                KPIImageUploadAdapterTahunan adapter = new KPIImageUploadAdapterTahunan(question.get(pos).getPhotoCapture(), getContext(), activity,"Y");
                recyclerViewImg.setAdapter(adapter);

            }catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_FILE_REQUEST);
    }


    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    /**
     * requestPermissions and do something
     *
     */
    public void requestRead() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            readFile();
        }
    }

    public void readFile() {
        // do something
    }



    private File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                "evidence",  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 20);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Pilih Aksi");
        String[] pictureDialogItems = {
                "Pilih dokumen dari media penyimpanan",
                "Ambil gambar dari kamera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showFileChooser();
                                break;
                            case 1:
                                try {

                                    dispatchTakePictureIntent();

//                                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//
////                                    Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fname_" +
////                                            String.valueOf(System.currentTimeMillis()) + ".jpg"));
////                                    photoCaptureIntent.putExtra("data", imageUri);
////
////                                    if (photoCaptureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
////                                        // Start the image capture intent to take photo
////                                        startActivityForResult(photoCaptureIntent, 20);
////                                    }
//
//                                            startActivityForResult(photoCaptureIntent, 20);


                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(), "error: "+e.toString(),Toast.LENGTH_SHORT).show();
                                }
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


    @Override
    public void onEvent(int position, List<KPIQuestionsPJ> questions) {
        showPictureDialog();
        pos = position;
        question = questions;

    }

    @Override
    public List<ImageUploadModel> onResult() {
        return imgCaptureList;
    }

    @Override
    public void redirect (int tabPosition) {
        ViewPager vp = getActivity().findViewById(R.id.KPIViewPager);
        vp.setCurrentItem(tabPosition);
    }

    @Override
    public String isEditables() {
        return isEditable;
    }

    @Override
    public void setEditables(String edit, KPIHeaderPJ khh) {

            fillKPI(RToken);
            lnResult.setVisibility(View.VISIBLE);

    }





    private void setFinalScore(KPIHeaderPJ khh, String RToken){
        try {
            ratingScore.setRating(Float.valueOf(khh.getStar()));
        }
        catch (Exception e){
            ratingScore.setRating(0);

        }
//        Toast.makeText(getContext(),khh.getStar(),Toast.LENGTH_LONG).show();
        tv_result_score.setText("Skor Akhir: "+ khh.getNilai());
        tv_result_status.setText(khh.getScoreName());


    }



    private void showDialogApprove(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.approve_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button approve = (Button) dialog.findViewById(R.id.btn_yes);
        Button cancel = (Button) dialog.findViewById(R.id.btn_no);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
                ArrayList<UserRealmModel> usr;
                usr = userRealmHelper.findAllArticle();


                KPIStatusPostPJ kpiStatusPostPJ = new KPIStatusPostPJ();
                kpiStatusPostPJ.setEmpNik(usr.get(0).getEmpNIK());
                kpiStatusPostPJ.setTransId(kh.getTransId());
                kpiStatusPostPJ.setStatus(kh.getStatusFG());

                //Toast.makeText(getContext(),kpiStatusPostPJ.getTransId()+"-"+kpiStatusPostPJ.getStatus()+"-"+kpiStatusPostPJ.getEmpNik(),Toast.LENGTH_LONG).show();

                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                //retrofit post
                Call<Void> call = apiService.postKPIAnswerStatusPJ(kpiStatusPostPJ,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        int statusCode = response.code();
                        getActivity().getSupportFragmentManager().popBackStack();
                        Toast.makeText(getContext(),"Approved!",Toast.LENGTH_LONG).show();

                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();

                    }
                });

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Approve Canceled", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
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
