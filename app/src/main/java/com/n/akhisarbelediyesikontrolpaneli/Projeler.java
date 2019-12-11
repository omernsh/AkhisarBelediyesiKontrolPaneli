package com.n.akhisarbelediyesikontrolpaneli;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Projeler extends AppCompatActivity {

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private FirebaseUser fUser;
    private ArrayList<ProjeVeriler> chatLists = new ArrayList<>();
    private CustomAdapter customAdapter;
    private ListView projeler_listesi;
    List<String> ıd_listesi,teslimat_bekleyen_listesi;
    int tes_bek_say_int;
    private TextView teslimat_bekleyen_sayisi_tv;
    private  String vale_isim_str;
    SharedPreferences sharedPref;

    Context context=this;

    String e_mail_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projeler);



        projeler_listesi = (ListView)findViewById(R.id.projeler_listesi);



        Button proje_ekle_button=(Button)findViewById(R.id.proje_ekle_button);

        db = FirebaseDatabase.getInstance();
        customAdapter = new CustomAdapter(getApplicationContext(),chatLists);
        projeler_listesi.setAdapter(customAdapter);

        dbRef = db.getReference("Projeler");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatLists.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    ProjeVeriler projeVeriler = ds.getValue(ProjeVeriler.class);
                    chatLists.add(projeVeriler);



                }
                customAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        proje_ekle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ProjeEkle.class);
                startActivity(intent);

            }
        });














    }
}
