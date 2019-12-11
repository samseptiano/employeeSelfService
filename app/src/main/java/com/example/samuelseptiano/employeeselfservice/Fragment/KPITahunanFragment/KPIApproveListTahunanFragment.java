package com.example.samuelseptiano.employeeselfservice.Fragment.KPITahunanFragment;

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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.samuelseptiano.employeeselfservice.Activity.MainActivity;
import com.example.samuelseptiano.employeeselfservice.Adapter.ViewPagerAdapter;
import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.ManagerFragment;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.TotalFragment;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.EmpOrgModel;
import com.example.samuelseptiano.employeeselfservice.Model.FilterPAModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.NetworkConnection.ConnectivityReceiver;
import com.example.samuelseptiano.employeeselfservice.R;
import com.google.android.material.tabs.TabLayout;
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

    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<String> orgNameList = new ArrayList<>();


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
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            type = bundle.getString("type");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list_tahunan, container, false);


        UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());
        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();
        String RToken = usr.get(0).getToken();
        String posId = usr.get(0).getPositionId();
        String nik = usr.get(0).getEmpNIK();



        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<EmpOrgModel>> call = apiService.getEmpOrg(usr.get(0).getEmpNIK(),"2019","Bearer "+usr.get(0).getToken());
        call.enqueue(new Callback<List<EmpOrgModel>>() {
            @Override
            public void onResponse(Call<List<EmpOrgModel>> call, Response<List<EmpOrgModel>> response) {
//                Toast.makeText(getContext(),response.code()+"",Toast.LENGTH_LONG).show();
                int statusCode = response.code();
                empOrgModelList = response.body();
                for (int i=0;i<empOrgModelList.size();i++){
                    orgNameList.add(response.body().get(i).getOrgName());
                }
            }
            @Override
            public void onFailure(Call<List<EmpOrgModel>> call, Throwable t) {
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

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        MaterialSpinner spinnerTahun = dialog.findViewById(R.id.spinnerTahun);
        spinnerTahun.setBackgroundResource(R.drawable.shapesignup);
        spinnerTahun.setPadding(25, 10, 25, 10);
        TagsEditText mTagsEditText = dialog.findViewById(R.id.tagsDirektorat);
        TagsEditText mTagsSite = dialog.findViewById(R.id.tagsSite);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnOk = dialog.findViewById(R.id.btnOk);


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
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.fruits)));
        mTagsSite.setThreshold(1);



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
