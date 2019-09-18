package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan.KPIImageUploadAdapterTahunan;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIHeader;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIQuestions;
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


public class KPIKuantitatifTahunanFragment extends Fragment implements KPIAdapterTahunan.EventListener{

    public static KPIHeader smt1 = new KPIHeader();
    public static KPIHeader smt2 = new KPIHeader();
    public static KPIHeader initKH = new KPIHeader();

    View rootView;
    RecyclerView recyclerViewKPI, recyclerViewImg;
    KPIAdapterTahunan kpiAdapterTahunan;
    List<KPIQuestions> lKPIQuestion;

    String mCurrentPhotoPath;

    Boolean connState, captureDone = false;
    UserList userList;
    String type="";
    public static String typeStatic;
    String semester ="";
    List<KPIQuestions> question;

    private static final int PICK_FILE_REQUEST = 1;

    int postTemp = -1;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    int tabPos;
    ArrayList<UserRealmModel> usr;


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
            try {
                userList = (UserList) bundle.getSerializable("id");
            }
            catch (Exception e){

            }
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

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        usr = userRealmHelper.findAllArticle();


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


        if(getType().equals("Approve")){
            kh.setCompany(userList.getCompanyName());
            kh.setLocationName(userList.getLocationName());
            kh.setEmpName(userList.getEmpName());
            kh.setNIK(userList.getEmpNiK());
            kh.setDept(userList.getJobTitleName());
            kh.setAtasanLangsung(userList.getNamaAtasanLangsung());
            kh.setNIKAtasanLangsung(userList.getNIKAtasanLangsung());
            kh.setAtasanTakLangsung(userList.getNamaAtasanTakLangsung());
            kh.setNIKAtasanTakLangsung(userList.getNIKAtasanTakLangsung());
        }
        else {
            kh.setCompany(usr.get(0).getCompanyName());
            kh.setLocationName(usr.get(0).getLocationName());

            kh.setEmpName(usr.get(0).getEmpName());
            kh.setNIK(usr.get(0).getEmpNIK());
            kh.setDept(usr.get(0).getDept());

            kh.setAtasanLangsung(usr.get(0).getNamaAtasanLangsung());
            kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
            kh.setAtasanTakLangsung(usr.get(0).getNamaAtasanTakLangsung());
            kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
        }
             ArrayList <String> a;
            lKPIQuestion = new ArrayList<>();

            //===================================== DUMMY ============================================
            KPIQuestions kq = new KPIQuestions();

            a = new ArrayList<>();
            a.add("5: No. 4 + IMPACT Eksplorasi");
            a.add("4: No. 3 + IMPACT Eksploitasi");
            a.add("3: HRIS 2/2 Completion & implementasi berjalan lancar");
            a.add("2: HRIS 1/2 Completion & implementasi");
            a.add("1: HRIS 1/2 Completion");
            kq.setHint(a);
            kq.setBobot("17.5%/ 35%");
            kq.setKPIcategory("Kuantitatif");
            kq.setPerspective("Internal Process");
            kq.setKPIDesc(" HRIS Live (New PA (annual, penilaian pelatihan/pejabat), Assessment Online M2, Training Mobile App,  Digitalisasi Comben, Insentif Online, OrganizationStructure M1)");
           kq.setAnswered(true);
            kq.setCheckedId(0);
            kq.setAnsweredPendukung(true);
            kq.setAnsweredPenghambat(true);
            kq.setAnsweredCatatan(true);
            kq.setCatatanLain("");
            kq.setPendukung("");
            kq.setPenghambat("");
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);

            a = new ArrayList<>();
            kq = new KPIQuestions();
            a.add("5: No. 4 + IMPACT Eksplorasi");
            a.add("4: No. 3 + IMPACT Eksploitasi");
            a.add("3: 100% Live (Recruitment & e-Recruitment)");
            a.add("2: 90% - <100% Live");
            a.add("1: <90% Live");
            kq.setHint(a);
            kq.setBobot("17.5%/ 35%");
            kq.setKPIcategory("Kuantitatif");
            kq.setPerspective("Internal Process");
            kq.setKPIDesc("Implementasi HRIS Crystal Milestone 3");
            kq.setAnswered(true);
            kq.setCheckedId(0);
            kq.setAnsweredPendukung(true);
            kq.setAnsweredPenghambat(true);
            kq.setAnsweredCatatan(true);
            kq.setCatatanLain("");
            kq.setPendukung("pendukung");
            kq.setPenghambat("");
            kq.setIdKPI("dasdasd");
            lKPIQuestion.add(kq);

        a = new ArrayList<>();
        kq = new KPIQuestions();
        a.add("5: No. 4 + IMPACT Eksplorasi");
        a.add("4: No. 3 + IMPACT Eksploitasi");
        a.add("3: 99% Uptime & Data absen updated");
        a.add("2: 80% - <99%");
        a.add("1: < 80%");
        kq.setHint(a);
        kq.setBobot("17.5%/ 35%");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Customer");
        kq.setKPIDesc("Operational Support (Mesin Absen & SMS Gateway)");
        kq.setAnswered(true);
        kq.setCheckedId(0);
        kq.setAnsweredPendukung(true);
        kq.setAnsweredPenghambat(true);
        kq.setAnsweredCatatan(true);
        kq.setCatatanLain("");
        kq.setPendukung("");
        kq.setPenghambat("");
        kq.setIdKPI("dasdasd");
        lKPIQuestion.add(kq);

        a = new ArrayList<>();
        kq = new KPIQuestions();
        a.add("5: No. 4 + IMPACT Eksplorasi");
        a.add("4: No. 3 + IMPACT Eksploitasi");
        a.add("3: 100% Implementation AP/PA tepat waktu");
        a.add("2: 80% - <100% Implementation AP/PA ");
        a.add("1: <=80% Implementation AP/PA");
        kq.setHint(a);
        kq.setBobot("17.5%/ 35%");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Customer");
        kq.setKPIDesc("Pelaksanaan AP & PA");
        kq.setAnswered(true);
        kq.setCheckedId(0);
        kq.setAnsweredPendukung(true);
        kq.setAnsweredPenghambat(true);
        kq.setAnsweredCatatan(true);
        kq.setCatatanLain("");
        kq.setPendukung("");
        kq.setPenghambat("");
        kq.setIdKPI("dasdasd");
        lKPIQuestion.add(kq);

        a = new ArrayList<>();
        kq = new KPIQuestions();
        a.add("5: No. 4 + mengikuti konvensi Kalbe atau eksternal");
        a.add("4: No. 3 yang mendapatkan INNOVATION AWARD ");
        a.add("3: No. 2 + membuat inovasi Eksplorasi/Eksploitasi");
        a.add("2: Inovasi impact yang teregister");
        a.add("1: Memiliki ide Inovasi");
        kq.setHint(a);
        kq.setBobot("17.5%/ 35%");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Learning");
        kq.setKPIDesc("Innoproject Assignment");
        kq.setAnswered(true);
        kq.setCheckedId(0);
        kq.setAnsweredPendukung(true);
        kq.setAnsweredPenghambat(true);
        kq.setAnsweredCatatan(true);
        kq.setCatatanLain("");
        kq.setPendukung("");
        kq.setPenghambat("");
        kq.setIdKPI("dasdasd");
        lKPIQuestion.add(kq);

        //========================================================================================


            kh.setKpiQuestionsList(lKPIQuestion);
            setInitKh(kh);
            initQuestionsAdapter(kh);

    }

    private void initQuestionsAdapter(KPIHeader kh) {
        recyclerViewKPI.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewQuestions.setHasFixedSize(true);
        if(getType().equals("Approve")) {
            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, userList.getEmpNiK(), getType(), this, Jenis);
        }
        else{
            kpiAdapterTahunan = new KPIAdapterTahunan(getActivity(), getContext(), kh, usr.get(0).getEmpNIK(), getType(), this, Jenis);
        }
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
                galleryAddPic();
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

                //Toast.makeText( getContext(),Integer.toString(question.get(pos).getPhotoCapture().size()),Toast.LENGTH_LONG).show();

                postTemp = pos;
                if (kpiAdapterTahunan != null) {
                    kpiAdapterTahunan.notifyDataSetChanged();
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
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }



}
