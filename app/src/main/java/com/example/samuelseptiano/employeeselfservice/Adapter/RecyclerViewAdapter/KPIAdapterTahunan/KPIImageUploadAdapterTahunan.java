package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.samuelseptiano.employeeselfservice.Api.ApiClient;
import com.example.samuelseptiano.employeeselfservice.Api.ApiInterface;
import com.example.samuelseptiano.employeeselfservice.Fragment.PerformanceAppraisal.adapter.TabsAdapter;
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.KPIApproveList;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KPIImageUploadAdapterTahunan extends RecyclerView.Adapter<KPIImageUploadAdapterTahunan.MyViewHolder> {

    private List<ImageUploadModelPA> imgList;
    private Context context;
    private Activity activity;
    String isEditable;
    String kpiId,paId,compId,semester,kpiType;

    private UserRealmHelper userRealmHelper = new UserRealmHelper(context);
    private ArrayList<UserRealmModel> usr;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public LinearLayout lnImgCaptureList;

        public MyViewHolder(View view) {
            super(view);
//            tvFilename = (TextView) view.findViewById(R.id.tv_filename);
            imgThumbnail = (ImageView) view.findViewById(R.id.img_thumbnail);
//            imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
            lnImgCaptureList = view.findViewById(R.id.lnImgCaptureList);
        }

    }


    public KPIImageUploadAdapterTahunan(List<ImageUploadModelPA> imgList, Context context, Activity activity, String isEditable, String paId, String kpiId, String compId, String semester, String kpiType) {
        this.context = context;
        this.imgList = imgList;
        this.activity = activity;
        this.isEditable = isEditable;
        this.semester = semester;
        this.paId = paId;
        this.kpiId = kpiId;
        this.compId = compId;
        this.kpiType = kpiType;
    }

    @Override
    public KPIImageUploadAdapterTahunan.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_upload_item_list, parent, false);

        return new KPIImageUploadAdapterTahunan.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIImageUploadAdapterTahunan.MyViewHolder holder, int position) {
        String filepath = imgList.get(position).getFilename();
        String fileImg = imgList.get(position).getImgString();
        String fileType = imgList.get(position).getFileExt();


        holder.lnImgCaptureList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImg(fileImg, filepath,position, fileType, paId, kpiId, compId, semester, kpiType);
            }
        });

        //if(isEditable.equals("Y")) {
//            holder.imgCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imgList.remove(position);
//                    notifyDataSetChanged();
//                    Toast.makeText(context, "Image Removed..", Toast.LENGTH_SHORT).show();
//                }
//            });
        //}

        try {
            if(fileType.equals(".pdf")){
                holder.imgThumbnail.setImageResource(R.drawable.pdf);
            }
            if(fileType.equals(".ppt") || fileType.equals(".pptx")){
                holder.imgThumbnail.setImageResource(R.drawable.ppt);
            }
            else if(fileType.equals(".doc") || fileType.equals(".docx")){
                holder.imgThumbnail.setImageResource(R.drawable.doc);
            }
            else if(fileType.equals(".xls") || fileType.equals(".xlsx")){
                holder.imgThumbnail.setImageResource(R.drawable.xls);
            }
            else if(fileType.equals(".jpg") ||fileType.equals(".png")||fileType.equals(".gif")||fileType.equals(".jpeg")) {
                byte[] decodedString = Base64.decode(fileImg, Base64.DEFAULT);
                try {
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.imgThumbnail.setImageBitmap(decodedByte);
                }
                catch (OutOfMemoryError e){
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }
                //holder.imgThumbnail.setImageResource(R.drawable.photo_capture_icon);

            }

        } catch(Exception e) {
            e.getMessage();
            //return null;
        }

    }
    @Override
    public int getItemCount() {
        return imgList.size();
    }


    private void showDialogImg(String result, String fileName, int position, String fileType, String paId, String kpiId, String compId, String semester, String kpiType){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.img_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgZoom = (ImageView) dialog.findViewById(R.id.imageZoom);
        TextView tvfileName = (TextView) dialog.findViewById(R.id.tvFileName);
        Button btnDelete = (Button) dialog.findViewById(R.id.btnCancel);
        Button btnDownload = (Button) dialog.findViewById(R.id.btnDownload);

        tvfileName.setText(fileName);

        try {
            if(fileType.equals(".jpg") ||fileType.equals(".png")||fileType.equals(".gif")||fileType.equals(".jpeg")) {
                byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                 Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgZoom.setImageBitmap(decodedByte);
            }
            else{
                imgZoom.setVisibility(View.GONE);
            }

        } catch(Exception e) {
            e.getMessage();
            Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            //return null;
        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
                    final File dwldsPath = new File(fileName);
                    byte[] pdfAsBytes = Base64.decode(result, 0);
                    FileOutputStream os;
                    os = new FileOutputStream(dwldsPath, false);
                    os.write(pdfAsBytes);
                    os.flush();
                    os.close();
                    Toast.makeText(context,"File Berhasil Didownloaded!",Toast.LENGTH_SHORT).show();

                    File file = new File(fileName);
                    Intent target = new Intent(Intent.ACTION_VIEW);

                    if(fileType.equals(".ppt")||fileType.equals(".pptx")) {
                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
                    }
                    if(fileType.equals(".pdf")) {
                        target.setDataAndType(Uri.fromFile(file), "application/pdf");
                    }
                    if(fileType.equals(".doc") || fileType.equals(".docx")) {
                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-word");
                    }
                    if(fileType.equals(".xls") || fileType.equals(".xlsx")) {
                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
                    }
                    if(fileType.equals(".jpg") ||fileType.equals(".png")||fileType.equals(".gif")||fileType.equals(".jpeg")){
                        try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(fileName), "image/*");
                        activity.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
                            // Instruct the user to install a PDF reader here, or something
                            Toast.makeText(context,"Please install Document Reader First", Toast.LENGTH_SHORT).show();
                        }
                    }
                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent intent = Intent.createChooser(target, "Open File");
                    try {
                        context.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
                        // Instruct the user to install a PDF reader here, or something
                        Toast.makeText(context,"Please install Document Reader First", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });




        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr = userRealmHelper.findAllArticle();

                ImageUploadModelPostPA imageUploadModelPostPA = new ImageUploadModelPostPA();
                imageUploadModelPostPA.setEmpNIK(usr.get(0).getEmpNIK());
                imageUploadModelPostPA.setPaId(imgList.get(position).getPaId());
                if(kpiType.equals("KUALITATIF")){
                    imageUploadModelPostPA.setCompId(kpiId);
                    imageUploadModelPostPA.setKpiId("0");
                }
                else{
                    imageUploadModelPostPA.setKpiId(kpiId);
                    imageUploadModelPostPA.setCompId("0");
                }
                imageUploadModelPostPA.setSemester(semester);
                imageUploadModelPostPA.setFileExt(imgList.get(position).getFileExt());
                imageUploadModelPostPA.setFilename(imgList.get(position).getFilename());
                imageUploadModelPostPA.setImgString(imgList.get(position).getImgString());


                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ImageUploadModelPostPA> call = apiService.delKPIEvidencePA(imageUploadModelPostPA,"Bearer "+usr.get(0).getToken());
                call.enqueue(new Callback<ImageUploadModelPostPA>() {
                    @Override
                    public void onResponse(Call<ImageUploadModelPostPA> call, Response<ImageUploadModelPostPA> response) {
                        int statusCode = response.code();

                        imgList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "File Removed..", Toast.LENGTH_SHORT).show();

                    }
                    @Override
                    public void onFailure(Call<ImageUploadModelPostPA> call, Throwable t) {
                        imgList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });



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
