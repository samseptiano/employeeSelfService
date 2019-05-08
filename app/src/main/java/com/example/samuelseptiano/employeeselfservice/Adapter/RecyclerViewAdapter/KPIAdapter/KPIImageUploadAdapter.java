package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelseptiano.employeeselfservice.Model.APIModel.Home.Home;
import com.example.samuelseptiano.employeeselfservice.Model.ImageUploadModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetail;
import com.example.samuelseptiano.employeeselfservice.RoutingTemplateHomeDetail.RoutingHomeDetailInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KPIImageUploadAdapter extends RecyclerView.Adapter<com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter.MyViewHolder> {

    private List<ImageUploadModel> imgList;
    private Context context;
    private Activity activity;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFilename;
        public ImageView imgThumbnail,imgCancel;
        public LinearLayout lnImgCaptureList;

        public MyViewHolder(View view) {
            super(view);
            tvFilename = (TextView) view.findViewById(R.id.tv_filename);
            imgThumbnail = (ImageView) view.findViewById(R.id.img_thumbnail);
            imgCancel = (ImageView) view.findViewById(R.id.img_cancel);
            lnImgCaptureList = view.findViewById(R.id.lnImgCaptureList);
        }

    }


    public KPIImageUploadAdapter(List<ImageUploadModel> imgList, Context context, Activity activity) {
        this.context = context;
        this.imgList = imgList;
        this.activity = activity;

    }

    @Override
    public com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_upload_item_list, parent, false);

        return new com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapter.KPIImageUploadAdapter.MyViewHolder holder, int position) {
        String filepath = imgList.get(position).getFilepath();
        String fileImg = imgList.get(position).getImgString();
        holder.tvFilename.setText(filepath);

        holder.lnImgCaptureList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImg(fileImg);
            }
        });

        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context,"Image Removed..",Toast.LENGTH_SHORT).show();
            }
        });

        try {
            byte[] decodedString = Base64.decode(fileImg, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imgThumbnail.setImageBitmap(decodedByte);

        } catch(Exception e) {
            e.getMessage();
            //return null;
        }

    }
    @Override
    public int getItemCount() {
        return imgList.size();
    }


    private void showDialogImg(String result){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.img_dialog);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgZoom = (ImageView) dialog.findViewById(R.id.imageZoom);

        try {
            byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgZoom.setImageBitmap(decodedByte);

        } catch(Exception e) {
            e.getMessage();
            //return null;
        }

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
