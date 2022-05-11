package com.si6b.ridermotogp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.si6b.ridermotogp.R;

public class UbahActivity extends AppCompatActivity {
    private int yId;
    private String yNama, yNomor, yNegara, ySponsor;
    private EditText etNama, etNomor, etNegara, etSponsor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent tangkap = getIntent();
        yId = tangkap.getIntExtra("xId",-1);
        yNama = tangkap.getStringExtra("xNama");
        yNomor = tangkap.getStringExtra("xNomor");
        yNegara = tangkap.getStringExtra("xNegara");
        ySponsor = tangkap.getStringExtra("xSponsor");

        etNama.setText(yNama);
        etNomor.setText(yNomor);
        etNegara.setText(yNegara);
        etSponsor.setText(ySponsor);


            etNama = findViewById(R.id.et_nama);
            etNomor = findViewById(R.id.et_nomor);
            etNegara = findViewById(R.id.et_sponsor);
            etSponsor = findViewById(R.id.et_sponsor);

        }
    }


