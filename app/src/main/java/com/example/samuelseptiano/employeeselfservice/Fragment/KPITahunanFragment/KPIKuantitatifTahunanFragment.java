package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

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
import android.os.Environment;
import android.provider.DocumentsContract;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.utils.FilePath;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class KPIKuantitatifTahunanFragment extends Fragment  implements KPIAdapterTahunan.EventListener{

    public static KPIHeader smt1 = new KPIHeader();
    public static KPIHeader smt2 = new KPIHeader();
    public static KPIHeader initKH = new KPIHeader();

    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapterTahunan kpiAdapterTahunan;
    List<KPIQuestions> lKPIQuestion;

    Boolean connState, captureDone = false;
    String empId="";
    String type="";
    public static String typeStatic;
    String semester ="";
    List<KPIQuestions> question;

    private static final int PICK_FILE_REQUEST = 1;

    int postTemp = 0;

    int pos=0;

    String Jenis="";

    Activity activity;

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
    int tabPos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRetainInstance(true);
        requestRead();


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            setType(bundle.getString("KPIType"));
            typeStatic = bundle.getString("KPIType");
            setEmpId(bundle.getString("id"));
            Jenis = bundle.getString("jenisKPI");
            semester = bundle.getString("semester");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(false);
        actionBar.setTitle("KPI Tahunan");


        if (rootView == null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            rootView = inflater.inflate(R.layout.fragment_kpi_kuantitatif_tahunan, container, false);

            recyclerViewKPI = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPI);
            fillKPI();
            onSaveInstanceState(savedInstanceState);
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove _rootView from the existing parent view group
            // in onDestroyView() (it will be added back).
        }

        //Toast.makeText(getContext(),tabPos,Toast.LENGTH_SHORT).show();

        return rootView;
    }

    private void approveKPI(){
    }

    private void fillKPI() {

        KPIHeader kh = new KPIHeader();
        kh.setBobot("30%");
        kh.setSemester(semester);
        kh.setTahun("2019");
        kh.setStatus("Approved");
        kh.setStatus1("Not Approved");
        kh.setStatus2("Not Approved");
        kh.setAtasanLangsung("Mega");
        kh.setNIKAtasanLangsung("131413478");
        kh.setAtasanTakLangsung("Samuel Septiano");
        kh.setNIKAtasanTakLangsung("181201101");
        kh.setCompany("EMP");
        kh.setLocationName("KND-Pusat");

        if(getType().equals("Approve")){
            kh.setEmpName("Samuel Septiano");
            kh.setNIK("181201101");
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

            KPIQuestions kq = new KPIQuestions();
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
            kq.setCheckedId(5);
            kq.setAnsweredPendukung(true);
            kq.setAnsweredPenghambat(true);
            kq.setAnsweredCatatan(true);
            kq.setCatatanLain("catatan");
            kq.setPendukung("pendukung");
            kq.setPenghambat("penghambat");
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);
        }
        kh.setKpiQuestionsList(lKPIQuestion);
        setInitKh(kh);
        initQuestionsAdapter(kh);

//
        for (int i = 1; i <= 6; i++) {

            a = new ArrayList<>();
            for (int j = 1; j <= 4; j++) {
                a.add(j+1+": 100% Implementation AP/PA + data General Increase tepat waktu");
            }

            KPIQuestions kq = new KPIQuestions();
            kq.setBobot("17.5%/ 35%");

            kq.setKPIcategory("Kualitatif");

            if(i<=3){
                kq.setPerspective("Internal Process 2");
            }
            else {
                kq.setPerspective("Performance 2");
            }
            kq.setKPIDesc(i+" HRISS Live (New PA (annual, penilaian pelatihan/pejabat), Assessment Online M2, Training Mobile App,  Digitalisasi Comben, Insentif Online, OrganizationStructure M1)");
            kq.setHint(a);
            kq.setAnswered(true);
            kq.setCheckedId(3);
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);
        }
        kh.setKpiQuestionsList(lKPIQuestion);
        initQuestionsAdapter(kh);
    }

    private void initQuestionsAdapter(KPIHeader kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, getEmpId(), getType(),  this, Jenis);
        recyclerViewKPI.setAdapter(kpiAdapterTahunan);
        //Toast.makeText(getContext(),kh.getCompany(),Toast.LENGTH_SHORT).show();

    }


    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("*/*"); // intent.setType("video/*"); to select videos to upload
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FILE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    if (kpiAdapterTahunan != null) {
                        kpiAdapterTahunan.notifyDataSetChanged();
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
                if (kpiAdapterTahunan != null) {
                    kpiAdapterTahunan.notifyDataSetChanged();
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



    public void setInitKh(KPIHeader kh){
        initKH = kh;
    }
    public KPIHeader getInitKh(){
            return initKH;
    }

    /**
     * do you want to do
     */
    public void readFile() {
        // do something
    }

    /**
     * onRequestPermissionsResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onEvent(int position, List<KPIQuestions> questions) {
        showPictureDialog();
        pos = position;
        question = questions;
    }

    @Override
    public List<ImageUploadModel> onResult() {
        return imgCaptureList;
    }

    @Override
    public void redirect (int tabPos) {
        ViewPager vp = getActivity().findViewById(R.id.KPIViewPager);
        vp.setCurrentItem(tabPos);
    }

    @Override
    public void setQuestion(KPIHeader a, int semester) {
        if(semester==1){
            smt1 = a;
         }
        else{
            smt2 = a;
        }
    }

    @Override
    public void getQuestionSmt() {
        try{
            Toast.makeText(getContext(),Integer.toString(smt1.getKpiQuestionsList().get(0).getCheckedId())+" "+Integer.toString(smt2.getKpiQuestionsList().get(0).getCheckedId()), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(getContext(),"Field Masih Kosong.. Silahkan Isi KPI Anda",Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public KPIHeader getQuestionSmt1() {
        return smt1;
    }

    public void resetQuestion(){
        smt1 = new KPIHeader();
        smt2 = new KPIHeader();
    }

    @Override
    public KPIHeader getQuestionSmt2() {
        return smt2;
    }


    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showFileChooser();
                                break;
                            case 1:
                                Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(photoCaptureIntent, 20);
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }


}
