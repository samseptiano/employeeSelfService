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
        actionBar.setTitle("PA Tahunan");

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
        kh.setStatus("Not Approved");
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
            kh.setDept(usr.get(0).getJobTitleName());



            //================= DUMMY ====================================
            kh.setAtasanLangsung("Rudy Djajasaputra");
            kh.setNIKAtasanLangsung("070500332");
            //kh.setNIKAtasanLangsung(usr.get(0).getNikAtasanLangsung());
            kh.setAtasanTakLangsung("Jenny Kurniasih T");
            kh.setNIKAtasanTakLangsung("920900100");
            //kh.setNIKAtasanTakLangsung(usr.get(0).getNikAtasanTakLangsung());
        }
             ArrayList <String> a;
            lKPIQuestion = new ArrayList<>();

            //===================================== DUMMY ============================================
            KPIQuestions kq = new KPIQuestions();

                a = new ArrayList<>();
                a.add("> 105%");
                a.add("101 - 105%");
                a.add("95 - 100%");
                a.add("90 - <95%");
                a.add("< 90%");
                kq.setHint(a);
                kq.setBobot("7.5/ 50");
                kq.setKPIcategory("Kuantitatif");
                kq.setPerspective("Customer");
                kq.setKPIDesc("Active Outlet(YTD Dec 19)");
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
                //Toast.makeText(getContext(),kq.getKPIDesc(),Toast.LENGTH_LONG).show();

            a = new ArrayList<>();
            kq = new KPIQuestions();
            a.add(">=100%");
            a.add("95 - 99%");
            a.add("90 - 94%");
            a.add("85 - 89 %");
            a.add("< 85%");
            kq.setHint(a);
            kq.setBobot("2.5/ 50");
            kq.setKPIcategory("Kuantitatif");
            kq.setPerspective("Customer");
            kq.setKPIDesc("Effective Call (effective dibagi target EC)");
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
        a.add("> 105%");
        a.add("101 - 105%");
        a.add("95 - 100%");
        a.add("90 - <95%");
        a.add("< 90%");
        kq.setHint(a);
        kq.setBobot("7.5/ 50");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Customer");
        kq.setKPIDesc("#YTD Active Outlet (AO) EMOS");
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
        a.add("> 105%");
        a.add("101 - 105%");
        a.add("95 - 100%");
        a.add("90 - <95%");
        a.add("< 90%");
        kq.setHint(a);
        kq.setBobot("20/ 50");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Financial");
        kq.setKPIDesc("Revenue (Gross Sales)");
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
        a.add("< 98%");
        a.add("98 -< 100%");
        a.add("100 - 102%");
        a.add("103 - 105%");
        a.add("> 105%");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("Financial");
        kq.setKPIDesc("Direct Expense");
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
        a.add("97.5% - 102.5%");
        a.add("95% - 97.5% 102.5% - 105%");
        a.add("92.5% - 95% 105% - 107.5%");
        a.add("90% - 92.5% 107.5% - 110%");
        a.add("< 90% 110%");
        kq.setHint(a);
        kq.setBobot("2.5/ 50");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("internal Process");
        kq.setKPIDesc("DOI");
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
        a.add("< 98%");
        a.add("98 -< 100%");
        a.add("100 - 102%");
        a.add("103 - 105%");
        a.add("> 105%");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kuantitatif");
        kq.setPerspective("internal Process");
        kq.setKPIDesc("DOAR");
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
        a.add("5: Mampu dan berani membuat keputusan bisnis (termasuk keputusan yang tidak populer) setelah menganalisa mendalam sehubungan dengan apa yang sedang terjadi di perusahaan. Keputusan yang dibuat tersebut mendapat dukungan dan komitmen dari pihak lain yang terkait.");
        a.add("4: Mampu menemukan MASALAH POTENSIAL. Dalam menganalisa masalah tidak hanya mampu memberikan pilihan solusi, namun juga sudah mampu mengidentifikasi adanya potensi masalah di masa depan sekaligus menentukan tindakan pencegahannya serta membuat keputusan yang tepat.");
        a.add("3: Mampu menentukan penyelesaian masalah yang tepat berdasarkan alternatif penyelesaian masalah yang ada dan mempertimbangkan aspek resiko dalam penyelesaian masalah tersebut.");
        a.add("2: Mampu menemukan AKAR MASALAH dari beberapa persoalan. Multi cases yang dihadapi mampu dianalisa dengan analisa timbal balik dan sudah bersifat luas dan mendalam serta memberikan alternatif solusi.");
        a.add("1: Mampu menemukan hubungan dari BEBERAPA persoalan, namun belum melakukan analisa timbal balik");
        kq.setHint(a);
        kq.setBobot("7.5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("ANALYSIS & PROBLEM SOLVING");
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
        a.add("5: Mampu berperan aktif untuk memuluskan transformasi yang dicanangkan oleh perusahaan dan senantiasa memastikan tim yang dikelola memiliki speed dan agility terhadap perkembangan tren dan teknologi dengan mengembangkan inclusive communication. (menciptakan komunikasi yang supportif dan efektif tanpa birokrasi)");
        a.add("4: Mampu menciptakan suasana yang harmonis dan netral terhadap keberagaman budaya, suku, agama dll., serta membangun semangat kerjasama tim dan direktoratnya untuk terlibat dalam tim cross functional untuk mencapai tujuan perusahaan");
        a.add("3: Mampu menggerakkan individu atau tim menuju target perubahan yang dicanangkan organisasi secara efektif, memastikan tim menyambut secara aktif perkembangan teknologi dan tren yang terjadi. Mampu bekerjasama dalam tim cross functional untuk mencapai tujuan perusahaan");
        a.add("2: Mampu menggerakkan individu atau tim menuju target perubahan yang dicanangkan organisasi secara efektif, memastikan tim menyambut secara aktif perkembangan teknologi dan tren yang terjadi. Mampu bekerjasama dalam tim cross functional untuk mencapai tujuan perusahaan. Konsisten untuk terbuka terhadap gagasan yang ditawarkan orang lain, merekomendasikan dan menggunakan ide bagus dari sumber diluar lingkungan sekitar untuk memecahkan masalah, namun belum bisa menggerakkan perubahan individu atau tim menuju target yang dicanangkan perusahaan.");
        a.add("1: Kurang terbuka terhadap perubahan, dan belum konsisten menggerakkan perubahan individu atau tim.");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("CHANGE LEADERSHIP & DEVELOPING OTHERS");
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
        a.add("5: Menemukan cara-cara baru yg inovatif untuk meningkatkan service level");
        a.add("4: Proaktif mencari feedback dan memperbaiki service level untuk meningkatkan kepuasan pelanggan, dan menjadikan masalah pelanggan sebagai tanggungjawab pribadi");
        a.add("3: Mampu membangun hubungan interpersonal yang baik sehingga pelanggan merasa dihargai, dengan menggali kebutuhan pelanggan, aktif membantu menyelesaikan masalah, serta mengembangkan solusi yang tepat sesuai dengan tujuan perusahaan.");
        a.add("2: Berusaha memahami keluhan pelanggan dan sudah mulai memberikan saran/solusi.");
        a.add("1: Peduli terhadap keluhan pelanggan namun hanya sebatas menampung keluhan dan belum memberikan saran/solusi.");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("CUSTOMER FOCUS");
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
        a.add("5: Melakukan perbaikan pada metode kerja Tidak hanya menetapkan target kerja pribadi yang menantang tetapi secara proaktif melakukan perbaikan pada metode kerjanya untuk mencapai target tersebut. Upaya tersebut terbukti mampu mencapai target menantang yang telah dibuat.");
        a.add("4: Menetapkan standar kerja pribadi untuk meningkatkan kinerja Membuat target pribadi yang lebih menantang dari target manajemen dan berhasil mencapainya.");
        a.add("3: Bekerja keras dan berhasil mencapai target kerjanya, dalam pencapaian target tersebut tidak menyerah dalam menghadapi hambatan yang ditemui, ulet dan mau menghadapi kesulitan, tidak menghindar dari masalah");
        a.add("2: Tidak hanya sekedar mengerjakan rincian tugas, tapi juga menunjukkan kerja kerasnya dalam bekerja meskipun masih perlu diarahkan agar berorientasi pada pencapaian target kerja.");
        a.add("1: Dalam bekerja hanya sekedar mengerjakan rincian tugasnya saja tapi belum berorientasi pada target kerja yang harus dicapai. Tidak melakukan upaya lebih dari sekedar menjalankan tugas yang dibebankan.");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("DRIVE FOR RESULT & DEVELOPING ONESELF");
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
        a.add("5: Meraih penghargaan Exploration Kalbe Group");
        a.add("4: Top 10 Impact Exploration/Exploitation EPM/Kalbe Group dan atau Impact Exploration/Exploitation yang ditetapkan sebagai standard nasional.");
        a.add("3: Menghasilkan Impact Exploration/Exploitation EPM atau Idea Exploration yang disetujui oleh Komite Inovasi untuk direalisasikan");
        a.add("2: Menciptakan Idea Exploration/Exploitation");
        a.add("1: Tidak ada Idea atau Impact untuk Exploration/Exploitation EPM");
        kq.setHint(a);
        kq.setBobot("7.5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("INNOVATION");
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
        a.add("5: Mampu menggunakan hubungan strategis dengan pihak lain untuk pengembangan peluang bisnis ke depan.");
        a.add("4: Mampu memberikan informasi yang dibutuhkan oleh pihak terkait dan dalam penyampaian informasi tersebut disesuaikan dengan pihak yang dihadapi. Dan mampu mengembangkan hubungan strategis antar kedua belah pihak (Mampu mencapai tujuan yang diinginkan)");
        a.add("3: Mengelola hubungan yang strategis dengan pihak-pihak yang terkait, dengan membangun komunikasi antara kedua belah pihak dengan tujuan untuk mencapai tujuan organisasi yang telah ditetapkan. (Mampu membawakan apa yang diinginkan organisasi)");
        a.add("2: Mulai membangun hubungan tetapi komunikasi yang dibangun masih sebatas komunikasi satu arah, tanpa melihat kebutuhan pihak terkait");
        a.add("1: Masih sebatas memberikan informasi kepada pihak-pihak terkait dan menjalin hubungan terbatas pekerjaan");
        kq.setHint(a);
        kq.setBobot("5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("MANAGING RELATIONSHIP & EFFECTIVE COMMUNICATION");
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
        a.add("5: Mampu mengajarkan cross project team / direct subordinate agar mereka mampu menjabarkan activity plan ke dalam aktivitas kerja secara spesifik dan sistematis beserta ukuran keberhasilan, dan mengelola implementasinya melalui PDCA dan sudah mempertimbangkan aspek-aspek sumber daya (dana, manusia, prasarana) yang berhubungan dengan tanggung jawabnya.");
        a.add("4: Mampu mengajarkan cross project team / direct subordinate agar mereka mampu menjabarkan activity plan ke dalam aktivitas kerja secara spesifik dan sistematis beserta ukuran keberhasilan, dan mengelola implementasinya melalui PDCA.");
        a.add("3: Mampu membuat perencanaan kerja secara sistematis, dan mengelola implementasinya secara efektif dengan melakukan kontrol secara konsisten dan membuat corrective action yang tepat sehingga hasil yang diperoleh sesuai dengan rencana");
        a.add("2: Mampu membuat perencanaan kerja secara sistematis, dan mengelola implementasinya sekalipun proses control dan corrective action masih belum rutin dan konsisten dilaksanakan");
        a.add("1: Membuat perencanaan kerja dan ukuran keberhasilan seadanya (kurang memahami hubungannya dengan strategi dan sasaran perusahaan) serta kemampuan mengelola implementasinya masih kurang (kurang monitoring dan kurang tanggap untuk melakukan corrective action)");
        kq.setHint(a);
        kq.setBobot("7.5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("PDCA");
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
        a.add("5: Mampu memahami situasi lingkungan dan menggerakkan cross project tim menuju perubahan Mampu memahami tuntutan perubahan lingkungan eksternal yang berpengaruh pada bisnis perusahaan dan mengajak cross project tim untuk menyesuaikan diri secara aktif terhadap tuntutan perubahan tersebut.");
        a.add("4: Menyesuaikan strategi pribadi dengan situasi yang dihadapi, sekaligus menyamakan pandangan keseluruhan cross project tim. Terhadap situasi yang baru, tidak hanya proaktif mengubah perilaku namun sudah mampu menyusun strategi atau taktik sesuai dengan tuntutan yang ada serta menyamakan pandangan seluruh cross project tim melalui berbagai media");
        a.add("3: Aktif mengubah perilaku untuk Menyesuaikan diri dan Menunjukkan sikap proaktif pada situasi baru (lingkungan baru, pekerjaan baru) sehingga sebelum muncul masalah sudah berhasil menyesuaikan diri dengan situasi tersebut. Bisa dilakukan dengan mengubah cara kerja, pola pikir, pendekatan dsb.");
        a.add("2: Menanggulangi stress secara Konstruktif Dalam situasi yang berubah-ubah/stresfull mampu menunjukkan kinerja yang efektif dan terfokus pada penyelesaian masalah yang ada.");
        a.add("1: Terbuka terhadap perubahan dan memiliki ketahanan kerja Bersikap positif terhadap perubahan yang ada dan tidak berusaha menghindari masalah. Dalam hal ini mencoba untuk tetap bekerja sebaik mungkin dengan situasi yang ada.");
        kq.setHint(a);
        kq.setBobot("7.5/ 50");
        kq.setKPIcategory("Kualitatif");
        kq.setPerspective("");
        kq.setKPIDesc("PROAKTIF & ADAPTIF");
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
                Toast.makeText(getContext(),"Document Uploaded!",Toast.LENGTH_LONG).show();

                ium.setFilepath(mCurrentPhotoPath);



                imgCaptureList.add(ium);

                question.get(pos).setPhotoCapture(imgCaptureList);

                //Toast.makeText( getContext(),Integer.toString(question.get(pos).getPhotoCapture().size()),Toast.LENGTH_LONG).show();

                postTemp = pos;
                if (kpiAdapterTahunan != null) {
                    kpiAdapterTahunan.currentPosition = pos+1;
//                    kpiAdapterTahunan.isVisible = true;
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
                Toast.makeText(getContext(),"Document Uploaded!",Toast.LENGTH_LONG).show();
                imgCaptureList.add(ium);

                question.get(pos).setPhotoCapture(imgCaptureList);
                postTemp = pos;
                if (kpiAdapterTahunan != null) {
                    kpiAdapterTahunan.currentPosition = pos+1;
//                    kpiAdapterTahunan.isVisible = true;
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
