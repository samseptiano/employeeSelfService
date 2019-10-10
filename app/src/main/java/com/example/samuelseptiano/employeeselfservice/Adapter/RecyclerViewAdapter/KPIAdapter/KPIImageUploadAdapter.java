package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

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
import com.example.samuelseptiano.employeeselfservice.Helper.UserRealmHelper;
import com.example.samuelseptiano.employeeselfservice.Model.DelImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class KPIImageUploadAdapter extends RecyclerView.Adapter<KPIImageUploadAdapter.MyViewHolder> {

    private List<ImageUploadModel> imgList;
    private Context context;
    private Activity activity;
    String isEditable;
    String kpiNo,transId;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFilename;
        public Button btnDownload;
        public ImageView imgThumbnail,imgCancel;
        public LinearLayout lnImgCaptureList;

        public MyViewHolder(View view) {
            super(view);
//            tvFilename = (TextView) view.findViewById(R.id.tv_filename);
            imgThumbnail = (ImageView) view.findViewById(R.id.img_thumbnail);
//            imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
            lnImgCaptureList = view.findViewById(R.id.lnImgCaptureList);
        }

    }


    public KPIImageUploadAdapter(List<ImageUploadModel> imgList, Context context, Activity activity, String isEditable, String kpiNo, String transId) {
        this.context = context;
        this.imgList = imgList;
        this.activity = activity;
        this.isEditable = isEditable;
        this.kpiNo = kpiNo;
        this.transId = transId;
    }

    @Override
    public KPIImageUploadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_upload_item_list, parent, false);

        return new KPIImageUploadAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(KPIImageUploadAdapter.MyViewHolder holder, int position) {
        String filepath = imgList.get(position).getFilepath();
        String fileImg = imgList.get(position).getImgString();
        String fileType = imgList.get(position).getImgType();
//        holder.tvFilename.setText(filepath);

        holder.lnImgCaptureList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImg(fileImg, filepath,fileType,position);
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
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imgThumbnail.setImageBitmap(decodedByte);
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


    private void showDialogImg(String result, String fileName, String fileType, int position){
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
            //return null;
        }
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
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

                UserRealmHelper userRealmHelper = new UserRealmHelper(context);
                ArrayList<UserRealmModel> usr = userRealmHelper.findAllArticle();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

                DelImageUploadModel delImageUploadModel = new DelImageUploadModel();
                delImageUploadModel.setKpiNo(kpiNo);
                delImageUploadModel.setTransID(transId);
                delImageUploadModel.setFilepath(imgList.get(position).getFilepath());
                delImageUploadModel.setImgString(imgList.get(position).getImgString());
                delImageUploadModel.setImgType(imgList.get(position).getImgType());


                apiService.delKPIAnswerAttPJ(delImageUploadModel,"Bearer "+usr.get(0).getToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Observer<DelImageUploadModel>() {

                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(DelImageUploadModel delImageUploadModel1) {

                            }

                            @Override
                            public void onError(Throwable e) {
                               imgList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "File Dihapus...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            @Override
                            public void onComplete() {
                                imgList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "File Dihapus...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

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
