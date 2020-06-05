package com.enseval.samuelseptiano.hcservice.Fragment.KPIMasaJabatanFragment;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.appyvet.rangebar.RangeBar;
import com.enseval.samuelseptiano.hcservice.Fragment.PerformanceAppraisalPKFragment.ManagerFragment;
import com.enseval.samuelseptiano.hcservice.Helper.EmpOrgRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpLocationModel;
import com.enseval.samuelseptiano.hcservice.Model.APIModel.KPI.EmpOrgModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPAModel;
import com.enseval.samuelseptiano.hcservice.Model.FilterPJModel;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.EmpOrgRealmModel;

import com.enseval.samuelseptiano.hcservice.Api.ApiClient;
import com.enseval.samuelseptiano.hcservice.Api.ApiInterface;
import com.enseval.samuelseptiano.hcservice.Helper.UserRealmHelper;
import com.enseval.samuelseptiano.hcservice.Model.RealmModel.UserRealmModel;
import com.enseval.samuelseptiano.hcservice.NetworkConnection.ConnectivityReceiver;
import com.enseval.samuelseptiano.hcservice.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import mabbas007.tagsedittext.TagsEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class KPIApproveListFragment extends Fragment implements TagsEditText.TagsEditListener, View.OnClickListener  {


    View rootView;
    Spinner spinnerPeriode;
    ImageButton imgFilter;

    String type="";


    List<String> tagSite= new ArrayList<>();
    List<String> tagDir = new ArrayList<>();


    List<EmpOrgModel> empOrgModelList = new ArrayList<>();
    List<EmpLocationModel> empLocationModelList = new ArrayList<>();

    List<String> orgNameList = new ArrayList<>();
    List<String> locationNameList = new ArrayList<>();


    String direktorat, site,empType;
    int monthStart = 0;
    int monthEnd = Calendar.getInstance().get(Calendar.MONTH);

    String periodeAwal=Calendar.getInstance().get(Calendar.YEAR)+"-01-01";

    Date date = new Date();
    String periodeAkhir=new SimpleDateFormat("yyyy-MM-dd").format(date);

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
        rootView = inflater.inflate(R.layout.fragment_kpi_approve_list, container, false);

        //int todaysDate = Calendar.getInstance().get(Calendar.DATE);
//        monthStart = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        monthEnd = Calendar.getInstance().get(Calendar.MONTH) + 2;
        //int currentYear = Calendar.getInstance().get(Calendar.YEAR);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            setConnState(bundle.getBoolean("ConnState"));
            type = bundle.getString("type");
            empType = bundle.getString("empType");

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
        dialog.setContentView(R.layout.filter_dialog_pk);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageButton closeButton = (ImageButton) dialog.findViewById(R.id.ib_close);
        TextView tvMonthStart = dialog.findViewById(R.id.tvMonthStart);
        TextView tvMonthEnd = dialog.findViewById(R.id.tvMonthEnd);

        TagsEditText mTagsEditText = dialog.findViewById(R.id.tagsDirektorat);
        TagsEditText mTagsSite = dialog.findViewById(R.id.tagsSite);
        Button btnReset = dialog.findViewById(R.id.btnReset);
        Button btnOk = dialog.findViewById(R.id.btnOk);
        RangeBar rangePeriode = dialog.findViewById(R.id.rangebar);



        //rangePeriode.setBarColor(getContext().getColor(R.color.colorPrimary));
        rangePeriode.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                if(leftPinIndex<=rightPinIndex){
                    monthStart = leftPinIndex;
                    monthEnd = rightPinIndex;
                    tvMonthStart.setText(leftPinValue);
                    tvMonthEnd.setText(rightPinValue);

                    periodeAwal=Calendar.getInstance().get(Calendar.YEAR)+"-"+(rangePeriode.getLeftIndex()+1)+"-01";
                    periodeAkhir=Calendar.getInstance().get(Calendar.YEAR)+"-"+(rangePeriode.getRightIndex()+1)+"-28";
                }
                else{
                    monthStart = rightPinIndex;
                    monthEnd = leftPinIndex;
                    tvMonthStart.setText(rightPinValue);
                    tvMonthEnd.setText(leftPinValue);
                    periodeAkhir=Calendar.getInstance().get(Calendar.YEAR)+"-"+rangePeriode.getLeftIndex()+"-28";
                    periodeAwal=Calendar.getInstance().get(Calendar.YEAR)+"-"+rangePeriode.getRightIndex()+"-28";
                }
            }
        });

        rangePeriode.setRangePinsByIndices(monthStart,monthEnd);

        String start = getMonth(monthStart);
        String end = getMonth(monthEnd);

        tvMonthStart.setText(start);
        tvMonthEnd.setText(end);

        rangePeriode.setSelectorColor(getContext().getColor(R.color.colorPrimary));
        rangePeriode.setPinColor(getContext().getColor(R.color.colorPrimaryDark));
        rangePeriode.setPinTextListener(new RangeBar.OnRangeBarTextListener() {
            @Override
            public String getPinValue(RangeBar rangeBar, int tickIndex) {
                String month="";
                if(tickIndex+1==1) {
                    month= "Jan";
                }else if(tickIndex+1==2) {
                    month= "Feb";
                }else if(tickIndex+1==3) {
                    month= "Mar";
                }else if(tickIndex+1==4) {
                    month= "Apr";
                }else if(tickIndex+1==5) {
                    month= "Mei";
                }else if(tickIndex+1==6) {
                    month= "Jun";
                }else if(tickIndex+1==7) {
                    month= "Jul";
                }else if(tickIndex+1==8) {
                    month= "Agu";
                }
                else if(tickIndex+1==9) {
                    month= "Sep";
                }
                else if(tickIndex+1==10) {
                    month= "Okt";
                }
                else if(tickIndex+1==11) {
                    month= "Nov";
                }
                else if(tickIndex+1==12) {
                    month= "Des";
                }
                return month;

            }
        });

        List<String> tahuns = new ArrayList<String>();
        tahuns.add("- tahun -");
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)-1));
        tahuns.add(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        // Creating adapter for spinner



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, tahuns);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner


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
                direktorat = mTagsEditText.getText().toString();
                site = mTagsSite.getText().toString();



                FilterPJModel filterPJModel = new FilterPJModel(periodeAwal,periodeAkhir,direktorat,site);
                mManagerFragment.update(filterPJModel);

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
                mTagsEditText.setText("");
                mTagsSite.setText("");

                monthStart = Calendar.getInstance().get(Calendar.MONTH);
                monthEnd = Calendar.getInstance().get(Calendar.MONTH) + 1;

                rangePeriode.setRangePinsByIndices(monthStart,monthEnd);

                String start = getMonth(monthStart);
                String end = getMonth(monthEnd);

                tvMonthStart.setText(start);
                tvMonthEnd.setText(end);
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
            Bundle bundle = new Bundle();
            bundle.putString("empType", empType);
            mManagerFragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.content_main, mManagerFragment, "tag").commit();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }

    }

    public String getMonth(int tickIndex) {
        String month="";
        if(tickIndex+1==1) {
            month= "Jan";
        }else if(tickIndex+1==2) {
            month= "Feb";
        }else if(tickIndex+1==3) {
            month= "Mar";
        }else if(tickIndex+1==4) {
            month= "Apr";
        }else if(tickIndex+1==5) {
            month= "Mei";
        }else if(tickIndex+1==6) {
            month= "Jun";
        }else if(tickIndex+1==7) {
            month= "Jul";
        }else if(tickIndex+1==8) {
            month= "Agu";
        }
        else if(tickIndex+1==9) {
            month= "Sep";
        }
        else if(tickIndex+1==10) {
            month= "Okt";
        }
        else if(tickIndex+1==11) {
            month= "Nov";
        }
        else if(tickIndex+1==12) {
            month= "Des";
        }
        return month;

    }


}
