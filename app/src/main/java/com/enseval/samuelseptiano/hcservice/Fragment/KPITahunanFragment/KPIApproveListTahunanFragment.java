package com.enseval.samuelseptiano.hcservice.Fragment.KPITahunanFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPAFragment.ManagerFragment;
import com.enseval.samuelseptiano.hcservice.Helper.EmpJobTitleRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpLocationModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpJobTtlRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpOrgRealmModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class KPIApproveListTahunanFragment extends Fragment  implements TagsEditText.TagsEditListener, View.OnClickListener {

    View rootView;
    Spinner spinnerPeriode;
    ImageButton imgFilter;

    String type="";

    int spinnertahunIdx=2;
    List<String> tagSite= new ArrayList<>();
    List<String> tagDir = new ArrayList<>();


    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<EmpLocationModel> empLocationModelList = new ArrayList<>();

    List<String> orgNameList = new ArrayList<>();
    List<String> locationNameList = new ArrayList<>();


    String direktorat,tahun, site;

    ManagerFragment mManagerFragment;

    public void setConnState(Boolean connState) {
        this.connState = connState;
    }


    Boolean connState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list_tahunan, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            type = bundle.getString("type");
            tahun = bundle.getString("tahun");
        }

        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();
        String posId = usr.get(0).getPositionId();
        String nik = usr.get(0).getEmpNIK();

        EmpOrgRealmHelper empOrgRealmHelper = new EmpOrgRealmHelper(getContext());
        ArrayList<EmpOrgRealmModel> org;
        org = empOrgRealmHelper.findAllArticle();

        for (int i=0;i<org.size();i++){
            EmpOrgModel em = new EmpOrgModel();
            em.setOrgName(org.get(i).getOrgName());
            em.setOrgCode(org.get(i).getOrgCode());
            empOrgModelList.add(em);
            orgNameList.add(org.get(i).getOrgName());
        }
                ApiInterface apiService = ApiClient.getClient(getContext()).create(ApiInterface.class);
                Call<List<EmpLocationModel>> call2 = apiService.getEmpLocation(nik,Integer.toString(Calendar.getInstance().get(Calendar.YEAR)),"Bearer "+usr.get(0).getToken());
                call2.enqueue(new Callback<List<EmpLocationModel>>() {
                    @Override
                    public void onResponse(Call<List<EmpLocationModel>> call, Response<List<EmpLocationModel>> response) {
                        int statusCode = response.code();
                        empLocationModelList= response.body();
                        for (int i=0;i<empLocationModelList.size();i++){
                            locationNameList.add(empLocationModelList.get(i).getLocationname());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<EmpLocationModel>> call, Throwable t) {
                        Toast.makeText(getContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });


        init();
        return rootView;
    }


    private void init(){
        mManagerFragment = new ManagerFragment().newInstance();


        imgFilter = rootView.findViewById(R.id.imgBtnFilter);

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogFilter();

            }
        });

        spinnerPeriode = rootView.findViewById(R.id.spinnerPeriode);
        if(type.equals("start")) {
            replaceFragment();
        }
        else if(type.equals("status")){
//            fr = new TotalFragment("",);
//            Bundle bundle3 = new Bundle();
//            bundle3.putBoolean("ConnState", ConnectivityReceiver.isConnected());
//            //bundle3.putString("type", "status");
//            fr.setArguments(bundle3);
//            fm = ((FragmentActivity) getContext()).getSupportFragmentManager();
//            ft = fm.beginTransaction();
//            ft.replace(R.id.fragment_frame, fr);
//            ft.addToBackStack("TotalFragment");
//            ft.commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu,inflater);

        MenuItem mSearch = menu.findItem(R.id.mi_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String text = s.toLowerCase(Locale.getDefault());
                try {

                } catch (Exception e) {

                }

                return true;

            }
        });
    }



    private void showDialogFilter(){
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.filter_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        MaterialSpinner spinnerTahun = dialog.findViewById(R.id.spinnerTahun);
        spinnerTahun.setBackgroundResource(R.drawable.shapedropdown);
        spinnerTahun.setPadding(25, 10, 25, 10);
        TagsEditText mTagsEditText = dialog.findViewById(R.id.tagsDirektorat);
        TagsEditText mTagsSite = dialog.findViewById(R.id.tagsSite);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnOk = dialog.findViewById(R.id.btnOk);

//        TagView tagView = dialog.findViewById(R.id.tag_view_test);
//        tagView.setHint("Add your skill");
//        //tagView.addTagSeparator(TagSeparator.AT_SEPARATOR);
//        tagView.addTagLimit(5);
//        // tagView.setTagBackgroundColor(Color.RED);
//        String[] tagList = new String[]{"C++", "Java", "PHP"};
//        tagView.setTagList(tagList);


        List<String> tahuns = new ArrayList<String>();
        tahuns.add("- tahun -");
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1));
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        // Creating adapter for spinner



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinnerTahun.setAdapter(dataAdapter);
        spinnerTahun.setSelectedIndex(spinnertahunIdx);


        mTagsEditText.setHint("Type Organization name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsEditText.setTagsWithSpacesEnabled(true);
        mTagsEditText.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line,orgNameList));
        mTagsEditText.setThreshold(1);


        mTagsSite.setHint("Type Site Name");
        //mTagsEditText.setTagsListener(MainActivity.this);
        mTagsSite.setTagsWithSpacesEnabled(true);
        mTagsSite.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, locationNameList));
        mTagsSite.setThreshold(1);

        if(tagDir.size()>0 || tagSite.size()>0){
            //spinnerTahun.setSelectedIndex(spinnertahunIdx);
            mTagsEditText.setTags(tagDir.toArray(new String[0]));
            mTagsSite.setTags(tagSite.toArray(new String[0]));
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( spinnerTahun.getItems().get(spinnerTahun.getSelectedIndex()).toString().equals("- tahun -")){
                    tahun = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
                }
                else {
                    tahun = spinnerTahun.getItems().get(spinnerTahun.getSelectedIndex()).toString();
                }
                direktorat = mTagsEditText.getText().toString();
                site = mTagsSite.getText().toString();
                FilterPAModel filterPAModel = new FilterPAModel(tahun,direktorat,site);
                mManagerFragment.update(filterPAModel);

                spinnertahunIdx=spinnerTahun.getSelectedIndex();
                tagSite = new ArrayList<>();
                tagDir = new ArrayList<>();
                for(int i=0;i<mTagsEditText.getTags().size();i++){
                    tagDir.add(mTagsEditText.getTags().get(i));
                }
                for(int i=0;i<mTagsSite.getTags().size();i++){
                    tagSite.add(mTagsSite.getTags().get(i));
                }

            dialog.dismiss();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerTahun.setSelectedIndex(0);
                mTagsEditText.setText("");
                mTagsSite.setText("");
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dialog.dismiss();
            }
        });

        dialog.show();
    }


//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            m.showDropDown();
//        }
//    }

    @Override
    public void onTagsChanged(Collection<String> tags) {
        //Log.d(TAG, "Tags changed: ");
        //Log.d(TAG, Arrays.toString(tags.toArray()));
    }

    @Override
    public void onEditingFinished() {
        //Log.d(TAG,"OnEditing finished");
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mTagsEditText.getWindowToken(), 0);
//        //mTagsEditText.clearFocus();
    }

    private void setButtonClickListener(@IdRes int id) {
        getActivity().findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    public void replaceFragment() {

        try {

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, mManagerFragment, "tag").commit();

        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

    }


}
