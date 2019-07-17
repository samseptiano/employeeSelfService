package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
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

import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.R;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class KPIImageUploadAdapter extends RecyclerView.Adapter<KPIImageUploadAdapter.MyViewHolder> {

    private List<ImageUploadModel> imgList;
    private Context context;
    private Activity activity;
    String isEditable;



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


    public KPIImageUploadAdapter(List<ImageUploadModel> imgList, Context context, Activity activity, String isEditable) {
        this.context = context;
        this.imgList = imgList;
        this.activity = activity;
        this.isEditable = isEditable;
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
                 btnDownload.setVisibility(View.GONE);
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
                }
                catch (Exception e){
                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "File Dihapus...", Toast.LENGTH_SHORT).show();
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
