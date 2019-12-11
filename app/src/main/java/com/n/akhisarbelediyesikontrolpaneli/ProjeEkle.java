package com.n.akhisarbelediyesikontrolpaneli;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjeEkle extends AppCompatActivity {

    private EditText proje_adı_edit,proje_özet_edit,proje_açılaması_edit;
    private ImageView image_view;
    private Button tamam_button;




    private Button btnChoose, btnUpload;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;



    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference mRef;
    FirebaseDatabase database;


    private Button mSelectImage;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private EditText ad_soyad_edit_text,telefon_no_edit_text,tc_no_edit_text,açıklama_edit_text;

    private String proje_adı_str,proje_özet_str,proje_açılaması_str;


    private DatabaseReference dbRef,dbRef1;
    private FirebaseDatabase db;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proje_ekle);

        proje_adı_edit=(EditText)findViewById(R.id.proje_adı_edit_text);
        proje_özet_edit=(EditText)findViewById(R.id.proje_özet_edit_text);

        proje_açılaması_edit=(EditText)findViewById(R.id.proje_açıklaması_edit_text);

        image_view=(ImageView)findViewById(R.id.imgView);

        tamam_button=(Button)findViewById(R.id.tamam_button);



        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("Projeler");






        image_view.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){

                chooseImage();

            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        tamam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                proje_adı_str=proje_adı_edit.getText().toString();
                proje_özet_str=proje_özet_edit.getText().toString();
                proje_açılaması_str=proje_açılaması_edit.getText().toString();

                if (proje_adı_str.equals("")||proje_özet_str.equals("")||proje_açılaması_str.equals("")){


                    Toast.makeText(ProjeEkle.this, "Proje adı, özeti ve açıklamsı giriniz!", Toast.LENGTH_SHORT).show();

                }

                else {

                    uploadImage();

                }




            }
        });




















    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image_view.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }


    private void uploadImage() {




        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Yükleniyor...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(final Uri uri) {

                                    String uniqueId = UUID.randomUUID().toString();

                                            Uri downloadUrl = uri;
                                            String fileUrl = downloadUrl.toString();



                                            final ProjeVeriler projeVeriler = new ProjeVeriler(proje_adı_str,proje_özet_str,proje_açılaması_str,fileUrl);

                                            dbRef.push().setValue(projeVeriler);








                                }
                            });
                            progressDialog.dismiss();
                            Toast.makeText(ProjeEkle.this, "Yüklendi", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProjeEkle.this, "Hata "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Yükleniyor... "+(int)progress+"%");
                        }
                    });
        }
        else {

            Toast.makeText(this, "Lütfen bir fotoğraf yükleyin!", Toast.LENGTH_LONG).show();
        }

    }

}
