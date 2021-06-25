package com.example.testbarang;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class TambahData extends AppCompatActivity{
    private DatabaseReference database;

    private Button btSubmit;
    private EditText etKode;
    private EditText etNama;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_barang);

        etKode = (EditText) findViewById(R.id.editNo);
        etNama = (EditText) findViewById(R.id.editNama);
        btSubmit = (Button) findViewById(R.id.btnOk);

        database = FirebaseDatabase.getInstance().getReference();

        btSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick (View v){
                if (!(etKode.getText().toString().isEmpty()) && !(etNama.getText().toString().isEmpty()) )
                    submitBrg(new Barang(etKode.getText().toString(),
                            etNama.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(), "Data TIdak Boleh Kosong", Toast.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etKode.getWindowToken(),0);
            }
        });
    }
    public void submitBrg(Barang brg){
        database.child("Barang").push().setValue(brg).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etKode.setText("");
                etNama.setText("");
                Toast.makeText(getApplicationContext(),"Data Berhasil Ditambahkan",Toast.LENGTH_LONG).show();
            }
        });
    }
    public static Intent getActIntent(Activity activity){
        return  new Intent(activity, TambahData.class);
    }
}