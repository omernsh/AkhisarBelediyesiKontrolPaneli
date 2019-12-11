package com.n.akhisarbelediyesikontrolpaneli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button projeler_button=(Button)findViewById(R.id.projeler);
        Button çek_gönder=(Button)findViewById(R.id.çek_gönder);


        projeler_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Projeler.class);
                startActivity(intent);

            }
        });


    }
}
