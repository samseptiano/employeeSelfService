package com.example.samuelseptiano.employeeselfservice.Adapter.RecyclerViewAdapter.KPIAdapterTahunan;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
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
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPA;
import com.example.samuelseptiano.employeeselfservice.Model.APIModel.KPI.PA.ImageUploadModelPostPA;
import com.example.samuelseptiano.employeeselfservice.Model.RealmModel.UserRealmModel;
import com.example.samuelseptiano.employeeselfservice.R;
import com.squareup.picasso.Picasso;

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

        //Toast.makeText(context,imgList.size()+"",Toast.LENGTH_LONG).show();

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
//                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    holder.imgThumbnail.setImageBitmap(decodedByte);
                    Picasso.get()
                            .load(ApiClient.BASE_URL+"pa/api/paTrans/evidences/"+filepath+"/"+fileType)
                            .placeholder(R.drawable.user)
                            .error(R.drawable.imgalt)
                            .into(holder.imgThumbnail);
                }
                catch (Exception e){
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
//                byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
//                 Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                imgZoom.setImageBitmap(decodedByte);
                Picasso.get()
                        .load(ApiClient.BASE_URL+"pa/api/paTrans/evidences/"+fileName+"/"+fileType)
                        .placeholder(R.drawable.user)
                        .error(R.drawable.imgalt)
                        .into(imgZoom);
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
//                try{
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ApiClient.BASE_URL+"pa/api/paTrans/evidences/"+fileName+"/"+fileType));

// only download via WIFI
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//                request.setAllowedNetworkTypes(DownloadManager.Request..NETWORK_MOBILE);
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                request.setTitle(fileName);
                request.setDescription("Downloading Evidence File");

// we just want to download silently
                request.setVisibleInDownloadsUi(true);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

// enqueue this request
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                long downloadID = downloadManager.enqueue(request);

//                    ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
//                    Call<ResponseBody> call = apiService.getFile(fileName,fileType);
//                    call.enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            int statusCode = response.code();
////                            File file = new File(Environment.getExternalStoragePublicDirectory(
////                                    Environment.DIRECTORY_DOWNLOADS)+"/"+fileName);
////                            try {
////                                file.createNewFile();
////                                Files.asByteSink(file).write(response.body().bytes());
////                                Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
////
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
////
////                            }
//
//
//
//                        }
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(context, "error: "+t.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });


//                    //Toast.makeText(context,result,Toast.LENGTH_LONG).show();
//                    final File dwldsPath = new File(fileName);
//                    byte[] pdfAsBytes = Base64.decode(result, 0);
//                    FileOutputStream os;
//                    os = new FileOutputStream(dwldsPath, false);
//                    os.write(pdfAsBytes);
//                    os.flush();
//                    os.close();
//
//                    File file = new File(fileName);
//                    Intent target = new Intent(Intent.ACTION_VIEW);
//
//                    if(fileType.equals(".ppt")||fileType.equals(".pptx")) {
//                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-powerpoint");
//                    }
//                    else if(fileType.equals(".pdf")) {
//                        target.setDataAndType(Uri.fromFile(file), "application/pdf");
//                    }
//                    else if(fileType.equals(".doc") || fileType.equals(".docx")) {
//                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-word");
//                    }
//                    else if(fileType.equals(".xls") || fileType.equals(".xlsx")) {
//                        target.setDataAndType(Uri.fromFile(file), "application/vnd.ms-excel");
//                    }
//                    else if(fileType.equals(".jpg") ||fileType.equals(".png")||fileType.equals(".gif")||fileType.equals(".jpeg")){
//                        try {
//
//                            Uri uri =  Uri.fromFile(file);
//                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//                            String mime = "*/*";
//                            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//                            if (mimeTypeMap.hasExtension(
//                                    mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
//                                mime = mimeTypeMap.getMimeTypeFromExtension(
//                                        mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
//                            intent.setDataAndType(uri,mime);
//                            activity.startActivity(intent);
//
////                        Intent intent = new Intent();
////                        intent.setAction(android.content.Intent.ACTION_VIEW);
////                        intent.setDataAndType(Uri.parse(fileName), "image/*");
////                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            activity.startActivity(intent);
//
//                        } catch (ActivityNotFoundException e) {
//                            Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
//                            // Instruct the user to install a PDF reader here, or something
//                            Toast.makeText(context,"Please install Document Reader First", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                    Intent intent = Intent.createChooser(target, "Open File");
//                    try {
//                        context.startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(context, e.toString(),Toast.LENGTH_LONG).show();
//                        // Instruct the user to install a PDF reader here, or something
//                        Toast.makeText(context,"Please install Document Reader First", Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(context,"File Berhasil Didownloaded!",Toast.LENGTH_SHORT).show();
//                }
//                catch (Exception e){
//                    Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show();
//                }
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


                ApiInterface apiService = ApiClient.getClient(context).create(ApiInterface.class);
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
                        Toast.makeText(context, "File Removed..", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
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

    public void showProgress(){

    }

}
