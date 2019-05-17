package com.example.samuelseptiano.employeeselfservice.Fragment.KPIFragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter;
import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.ProfileDataFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile.QRFragment;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.KPIQuestions;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class KPIKuantitatifFragment extends Fragment  implements KPIAdapter.EventListener{

    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapter kpiAdapter;
    List<KPIQuestions> lKPIQuestion;

    Boolean connState, captureDone = false;
    String empId="";
    String type="";
    String semester ="";
    List<KPIQuestions> question;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootView = inflater.inflate(R.layout.fragment_kpi_kuantitatif, container, false);

        recyclerViewKPI = (RecyclerView) rootView.findViewById(R.id.recyclerViewKPI);
        fillKPI();
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
        kh.setAtasanLangsung("dasdas");
        kh.setBawahanLangsung("sadasds");
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

            KPIQuestions kq = new KPIQuestions();
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
//            kq.setAnswered(true);
//            kq.setCheckedId(3);
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);
        }
        kh.setKpiQuestionsList(lKPIQuestion);
        initQuestionsAdapter(kh);
    }

    private void initQuestionsAdapter(KPIHeader kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        kpiAdapter = new KPIAdapter(getActivity(), getContext(), kh, getEmpId(), getType(),  this, Jenis);
        recyclerViewKPI.setAdapter(kpiAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK)
        {
            try {
                if(pos != postTemp){
                    if(question.get(pos).getPhotoCapture() != null){
                        imgCaptureList = new ArrayList<>();
                       for(int i=0;i<question.get(pos).getPhotoCapture().size();i++) {
                            imgCaptureList.add(question.get(pos).getPhotoCapture().get(i));
                        }
                       // Toast.makeText(getContext(),"not null", Toast.LENGTH_LONG).show();
                    }
                    else{
                        imgCaptureList = new ArrayList<>();
                    }

                }
                   ImageUploadModel ium = new ImageUploadModel();

//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
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
                final Bitmap photo = BitmapFactory.decodeStream(imageStream);;

                    //============================================================================
                    //Convert Bitmap to String
                    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG,100, baos);
                    byte [] b=baos.toByteArray();
                    String temp=Base64.encodeToString(b, Base64.DEFAULT);
                    //============================================================================
                    captureDone = true;

                    ium.setImgString(temp);
                    ium.setFilepath(imgDecodableString);

                    imgCaptureList.add(ium);

                    question.get(pos).setPhotoCapture(imgCaptureList);

                    postTemp = pos;

                        if (kpiAdapter != null) {
                            kpiAdapter.notifyDataSetChanged();
                        }

//                    recyclerViewImg = (RecyclerView) rootView.findViewById(R.id.recycler_view_image_upload);
//                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                    recyclerViewImg.setLayoutManager(mLayoutManager);
//                    KPIImageUploadAdapter adapter = new KPIImageUploadAdapter(question.get(pos).getPhotoCapture(), getContext(), activity);
//                    recyclerViewImg.setAdapter(adapter);

                    //cara buat imgCaptureList setiap beda recycler view rest jadi null

            }
            catch (Exception e){
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();

            }
        }

    }

    @Override
    public void onEvent(int position, List<KPIQuestions> questions) {
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, 20);
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
}
