package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class update_donor extends AppCompatActivity {
    Button finish,clear;
    ImageView image;
    FirebaseDatabase db;
    TextInputEditText amount,name,phone;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_donor);
        finish = findViewById(R.id.finish);
        clear = findViewById(R.id.clear);
        image = findViewById(R.id.back);
        phone=findViewById(R.id.phone);
        spinner=findViewById(R.id.blood);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bloodgroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        db = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");
        Intent intent=getIntent();
        String pname= intent.getStringExtra("name");
        pname=pname.replaceAll("\\s","");
        String pamount=intent.getStringExtra("amount");
        String pphone=intent.getStringExtra("phone");
        pphone=pphone.replaceAll("\\s","");

        phone.setText(pphone);
        spinner.setEnabled(false);
        String m=intent.getStringExtra("id");
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        name.setText(pname);
        amount.setText(pamount);
        amount.setEnabled(false);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                amount.setText("");
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getReference().child("donorinfo").child("").child("" + m).removeValue();
                String pblood= intent.getStringExtra("blood");
                donorinfo u = new donorinfo(name.getText().toString(), phone.getText().toString(),pblood, pamount, Calendar.getInstance().getTime());
                if(name.getText().toString().equals("")||phone.getText().toString().length()!=10||amount.getText().toString().equals("")){
                    Toast.makeText(update_donor.this, "Please enter all the fields with valid details", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.getReference().child("donorinfo").child("").child("" + m).setValue(u);
                    Toast.makeText(update_donor.this, "data updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
