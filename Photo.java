package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Photo extends Activity implements View.OnClickListener {
    static final int REQEUST_TAKE_PHOTO = 1;
    static final int REQEUST_SAVE_PHOTO = 2;


    Button btnUploadPhoto,check;
    String mCurrentPhotoPath;
    ImageView iv_capture;
    Uri photoURI,albumURI=null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        iv_capture = (ImageView) findViewById(R.id.photo);
        check=(Button)findViewById(R.id.check);
        btnUploadPhoto=(Button)findViewById(R.id.btnUploadPhoto);
        btnUploadPhoto.setOnClickListener(this);
    }

    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQEUST_TAKE_PHOTO);
    }

    private void saveImage() {
        Intent Intent = new Intent("com.android.camera.action.CROP");

        Intent.setDataAndType(photoURI,"image/*");
        Intent.putExtra("output",albumURI);
        Log.d("hello",albumURI.toString());
        startActivityForResult(Intent,REQEUST_SAVE_PHOTO);
    }

    private File createImageFile() throws IOException{
        String imageFileName="tmp_" + String.valueOf(System.currentTimeMillis())+".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory(),imageFileName);
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK){
            Toast.makeText(getApplicationContext(), "onActivityResult: not ok",Toast.LENGTH_LONG).show();
        } else {
            switch (requestCode){
                case REQEUST_TAKE_PHOTO:
                    File albumFile = null;
                    try{
                        albumFile= createImageFile();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    if(albumFile != null){
                        albumURI = Uri.fromFile(albumFile);
                    }
                    photoURI = data.getData();

                    Bitmap image_bitmap = null;
                    try {
                        image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_capture.setImageBitmap(image_bitmap);
                    break;
                case REQEUST_SAVE_PHOTO:
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(photoURI);
                    mediaScanIntent.setData(albumURI);
                    this.sendBroadcast(mediaScanIntent);
                    break;
            }

        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case(R.id.check):
                saveImage();
                Intent intent = new Intent(Photo.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case(R.id.btnUploadPhoto):
                doTakeAlbumAction();
                break;
        }


    }

}