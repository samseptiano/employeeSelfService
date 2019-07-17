package com.example.samuelseptiano.employeeselfservice.Fragment.KPIMasaJabatanFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeaderPJ;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestionsPJ;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.FilePath;
import com.example.samuelseptiano.employeeselfservice.utils.MathRegexParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class KPIKuantitatifFragment extends Fragment  implements KPIAdapter.EventListener{

    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapter kpiAdapter;
    List<KPIQuestionsPJ> lKPIQuestion;

    TextView tv_result_name,tv_result_nik, tv_result_status, tv_result_positon, tv_result_score;
    RatingBar ratingScore;

    Boolean connState, captureDone = false;
    String empId="";
    String type="";
    String semester ="";
    List<KPIQuestionsPJ> question;
    Button editBtn;

    int postTemp = 0;

    int pos=0;

    String Jenis="";

    String isEditable = "N";

    Activity activity;

    private static final int PICK_FILE_REQUEST = 1;

    private List<ImageUploadModel> imgCaptureList = new ArrayList<>();

    public Boolean getConnState() {
        return connState;
    }

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    LinearLayout lnResult,lnQuestions,lnResultButton,lnQuestionsButton;

    KPIHeaderPJ kh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setType(bundle.getString("KPIType"));
            setEmpId(bundle.getString("id"));
            Jenis = bundle.getString("jenisKPI");
            semester = bundle.getString("semester");
        }

        requestRead();

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

            fillKPI();
            onSaveInstanceState(savedInstanceState);
        }
        else{

        }
        return rootView;
    }

    private void approveKPI(){
    }

    private void fillKPI() {

        lnResult = rootView.findViewById(R.id.ln_result);
        lnQuestions = rootView.findViewById(R.id.ln_questions);
        lnResultButton = rootView.findViewById(R.id.ln_result_button);
        lnQuestionsButton = rootView.findViewById(R.id.ln_questions_button);
        editBtn = rootView.findViewById(R.id.btn_edit_result);

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

        kh= new KPIHeaderPJ();
        kh.setBobot("30%");
        kh.setSemester(semester);
        kh.setTahun("2019");
        kh.setStatus("Approved");
        kh.setPeriodeAwal("10/05/2017");
        kh.setPeriodeAkhir("10/05/2019");
        kh.setLocationName("KND-Aceh");
        kh.setAtasanLangsung("Samuel Septiano");
        kh.setNIKatasanLangsung("181201101");
        kh.setAtasanTakLangsung("Abdurrahman Wahid");
        kh.setNIKatasanTakLansung("381201101");
        kh.setBawahanLangsung("sadasds");
        kh.setStatus1("Not Approved");
        kh.setStatus2("Not Approved");
        kh.setJobTitle("Sales");
        if(getType().equals("Approve")){
            kh.setEmpName("Michael");
            kh.setNIK("111201101");
            kh.setDept("HRIS");
        }
        else {
            kh.setEmpName("");
            kh.setNIK("");
            kh.setDept("");
        }
        ArrayList <String> a;
        lKPIQuestion = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {

            a = new ArrayList<>();
            for (int j = 1; j <= 4; j++) {
                a.add(j+1+": 100% Implementation AP/PA + data General Increase tepat waktu");
            }

            KPIQuestionsPJ kq = new KPIQuestionsPJ();
            kq.setBobot("17.5%/ 35%");

            kq.setKPIcategory("Kuantitatif");

            if(i<4){
                kq.setPerspective("Internal Process");
            }
            else {
                kq.setPerspective("Performance");
            }
            kq.setKPIDesc(i+" HRIS Live (New PA (annual, penilaian pelatihan/pejabat), Assessment Online M2, Training Mobile App,  Digitalisasi Comben, Insentif Online, OrganizationStructure M1)");
            kq.setHint(a);
            kq.setAnswered(true);
            kq.setCheckedId(4);
            kq.setEvidence("String Evidence"+i);
            kq.setAnswerenEvidence(true);
//            kq.setAnswered(true);
//            kq.setCheckedId(3);
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);
        }
        kh.setKpiQuestionsList(lKPIQuestion);
        initQuestionsAdapter(kh);

//
        for (int i = 1; i <= 6; i++) {

            a = new ArrayList<>();
            for (int j = 1; j <= 4; j++) {
                a.add(j+1+": 100% Implementation AP/PA + data General Increase tepat waktu");
            }

            KPIQuestionsPJ kq = new KPIQuestionsPJ();
            kq.setBobot("17.5%/ 35%");

            kq.setKPIcategory("Kualitatif");

            if(i<=3){
                kq.setPerspective("Internal Process 2");
            }
            else {
                kq.setPerspective("Performance 2");
            }
            kq.setKPIDesc(i+" HRIS Live (New PA (annual, penilaian pelatihan/pejabat), Assessment Online M2, Training Mobile App,  Digitalisasi Comben, Insentif Online, OrganizationStructure M1)");
            kq.setHint(a);
            kq.setAnswered(true);
            kq.setCheckedId(5);
            kq.setEvidence("String Evidence"+i);
            kq.setAnswerenEvidence(true);
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);
        }
        kh.setKpiQuestionsList(lKPIQuestion);
        initQuestionsAdapter(kh);



    if(!kh.getEmpName().equals("")) {

        tv_result_name.setText(kh.getEmpName());
        tv_result_nik.setText(kh.getNIK());
        tv_result_positon.setText(kh.getJobTitle());
    }
    else{
        tv_result_name.setVisibility(View.GONE);
        tv_result_nik.setVisibility(View.GONE);
        tv_result_positon.setVisibility(View.GONE);
    }


        int total=0;
        int i=0;
        for(i=0;i<kh.getKpiQuestionsList().size();i++) {
            total += kh.getKpiQuestionsList().get(i).getCheckedId();
        }
        //ratingScore.setRating((float) total/kh.getKpiQuestionsList().size());
        ratingScore.setRating((float) kh.getKpiQuestionsList().get(0).getCheckedId());
        tv_result_score.setText("Skor Akhir:");

    }

    private void initQuestionsAdapter(KPIHeaderPJ kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        kpiAdapter = new KPIAdapter(getActivity(), getContext(), kh, getEmpId(), getType(),  this, Jenis);
        recyclerViewKPI.setAdapter(kpiAdapter);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK)
        {
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

//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                    // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
//
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap photo = BitmapFactory.decodeStream(imageStream);


                //============================================================================
                //Convert Bitmap to String
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
                //============================================================================
                captureDone = true;

                ium.setImgType( imgDecodableString.substring(imgDecodableString.lastIndexOf(".")));
                ium.setImgString(temp);
                ium.setFilepath(imgDecodableString);

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

                //cara buat imgCaptureList setiap beda recycler view rest jadi null
            }
            catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

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
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE_REQUEST);
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
                                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(photoCaptureIntent, 20);
                                }
                                catch (Exception e){
                                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
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
        kh = khh;
        //Toast.makeText(getContext(),Integer.toString(kh.getKpiQuestionsList().get(0).getCheckedId()),Toast.LENGTH_SHORT).show();
        isEditable = edit;
        kpiAdapter.notifyDataSetChanged();
        editBtn.setText("Edit");
        lnResult.setVisibility(View.VISIBLE);
        lnQuestions.setVisibility(View.GONE);

        //ratingScore.setRating((float) kh.getKpiQuestionsList().get(0).getCheckedId());
    }


}
