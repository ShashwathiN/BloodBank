package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class donor_add extends AppCompatActivity {
Button finish,clear;
ImageView image;
    int amounts;

FirebaseDatabase db,bb;
DatabaseReference databaseReference;
Spinner spinner;
int blood;
int m=10000;
TextInputEditText amount,name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SharedPreferences sh= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        m=sh.getInt("number",1000);
        finish = findViewById(R.id.finish);
        spinner=findViewById(R.id.blood);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.bloodgroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        clear = findViewById(R.id.clear);
        image = findViewById(R.id.back);
        db = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");
        bb = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");



        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
phone=findViewById(R.id.phone);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(" ");
                amount.setText(" ");
                phone.setText("");
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m=m+1;
                if(name.getText().toString().isEmpty()||phone.getText().toString().isEmpty()||phone.getText().toString().length()!=10||spinner.getSelectedItem().toString().equals("\t\tSelect Blood Group")||amount.getText().toString().isEmpty())
                {
                    Toast.makeText(donor_add.this, "Please enter all the fields with valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    donorinfo u = new donorinfo(name.getText().toString(), phone.getText().toString(),spinner.getSelectedItem().toString(), amount.getText().toString(), Calendar.getInstance().getTime());
                    db.getReference().child("donorinfo").child("" + m).setValue(u);
                    Toast.makeText(donor_add.this, "data with id "+m+" added successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putInt("number", m);
                    myEdit.apply();
                    amount.setText("");
                    name.setText("");
                }


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(donor_add.this, "Thank you", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
