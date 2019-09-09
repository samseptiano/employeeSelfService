package com.example.samuelseptiano.employeeselfservice.Fragment.SubMenuProfile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.samuelseptiano.employeeselfservice.Helper.HomeRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.QrResultModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.HomeRealmModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.ArrayList;

public class QRFragment extends Fragment implements View.OnClickListener {


    String optionChoice;
    TextView tvNIK;
    ImageButton btnScanQr;
    UserRealmHelper userRealmHelper = new UserRealmHelper(getContext());

    Fragment fr;
    FragmentManager fm;
    FragmentTransaction ft;

    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ArrayList<UserRealmModel> usr;
        usr = userRealmHelper.findAllArticle();

        String id = usr.get(0).getEmpNIK();
        String userQR = usr.get(0).getUserQR();

        String strtext = id;

        View rootView = inflater.inflate(R.layout.fragment_qr, container,    false);
        tvNIK = (TextView) rootView.findViewById(R.id.tvNIK);
        btnScanQr = (ImageButton) rootView.findViewById(R.id.btnScanQr);
        ImageView myQR = (ImageView) rootView.findViewById(R.id.MyQR);
//        ImageView QRFrame = (ImageView) rootView.findViewById(R.id.qrFrame);

        String content = strtext;
        tvNIK.setText(content);

        btnScanQr.setOnClickListener(this);

        try {
            byte [] encodeByte=Base64.decode(userQR,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            if(size.x >1081 ){
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/4);
                int height = size.y - (size.y/4);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth-50, newHeight-50, true);
//                QRFrame.getLayoutParams().height = newHeight+100;
//                QRFrame.getLayoutParams().width = newWidth+100;
//                QRFrame.requestLayout();

                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }
            else{
                int imageWidth = bitmap.getWidth();
                int imageHeight = bitmap.getHeight();

                //Display display = getActivity().getWindowManager().getDefaultDisplay();
                //Point size = new Point();
                display.getSize(size);
                int width = size.x - (size.x/4);
                int height = size.y - (size.y/4);

                int newWidth = width; //this method should return the width of device screen.
                float scaleFactor = (float)newWidth/(float)imageWidth;
                int newHeight = (int)(imageHeight * scaleFactor);

                bitmap = Bitmap.createScaledBitmap(bitmap, newWidth-50, newHeight-50, true);
//                QRFrame.getLayoutParams().height = newHeight+100;
//                QRFrame.getLayoutParams().width = newWidth+100;
//                QRFrame.requestLayout();
                //Toast.makeText(getContext(),Integer.toString(size.x),Toast.LENGTH_SHORT).show();
            }

            myQR.setImageBitmap(bitmap);



        } catch(Exception e) {
            e.getMessage();
            return null;
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(getContext(), "QR Not found", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{

                    if(optionChoice.equals("Download")) {
                        showDialog(result.getContents());
                    }
                    else if(optionChoice.equals("Search")){
                        try{

                            //== format QR yang bisa di scan : {"eventId":"5","eventType":"Training"} ==

                            Gson g = new Gson();
                            QrResultModel results = g.fromJson(result.getContents(), QrResultModel.class);


                            HomeRealmHelper homeRealmHelper = new HomeRealmHelper(getContext());
                            HomeRealmModel homie = homeRealmHelper.findArticle(results.getEventId());

                            if(homie.getEventType().equals(results.getEventType())){
                                RoutingHomeDetailInterface routingHomeDetailInterface = new RoutingHomeDetail();
                                routingHomeDetailInterface.routingHomeDetail(results.getEventType(),getContext(),results.getEventId());

                            }
                            else{
                                Toast.makeText(getContext(),"No Event Found",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){

                            Toast.makeText(getContext()," No Event Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An Error Occurred While Scanning", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v) {

        showDialogOption();
    }


    private void showDialog(String result){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.qr_result_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton copytext = (ImageButton) dialog.findViewById(R.id.imageButtonCopy);
        EditText edtUrl = (EditText) dialog.findViewById(R.id.edtQRResult);
        copytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(edtUrl.getText());
                Toast.makeText(getContext(), "Text Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        edtUrl.setText(result);



        Button btnWebsite = (Button) dialog.findViewById(R.id.btnGotoWebsite);
        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String url = edtUrl.getText().toString();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(getContext(),"URL not valid",Toast.LENGTH_SHORT).show();
                }
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

    private void showDialogOption(){

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.qr_option_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton btnSearchEvent = (ImageButton) dialog.findViewById(R.id.imageButtonSearchEvent);
        ImageButton btnDownloadCertificate = (ImageButton) dialog.findViewById(R.id.imageButtonDownloadCertificate);
        btnDownloadCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy url
                optionChoice = "Download";
                qrScan = new IntentIntegrator(getActivity());
                qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                qrScan.setBeepEnabled(true);
                qrScan.setCameraId(0);
                qrScan.setBarcodeImageEnabled(true);
                qrScan.setPrompt("Scan QR for download certificate");
                qrScan.setCaptureActivity(CaptureActivity.class);
                qrScan.forSupportFragment(QRFragment.this).initiateScan();
                dialog.dismiss();
            }
        });

        btnSearchEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //copy url
                optionChoice = "Search";
                qrScan = new IntentIntegrator(getActivity());
                qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                qrScan.setBeepEnabled(true);
                qrScan.setCameraId(0);
                qrScan.setBarcodeImageEnabled(true);
                qrScan.setPrompt("Scan QR for search Event");
                qrScan.setCaptureActivity(CaptureActivity.class);
                qrScan.forSupportFragment(QRFragment.this).initiateScan();
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
