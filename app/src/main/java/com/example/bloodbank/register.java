package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {
    Button finish,clear;
    ImageView image;
    FirebaseDatabase db;
    TextInputEditText password,conpassword,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        finish = findViewById(R.id.finish);
        clear = findViewById(R.id.clear);
        image = findViewById(R.id.back);
        password=findViewById(R.id.password);
        conpassword=findViewById(R.id.conpassword);
        db = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");


        name = findViewById(R.id.name);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                password.setText("");
                conpassword.setText("");
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||password.getText().toString().isEmpty()||conpassword.getText().toString().isEmpty())
                {
                    Toast.makeText(register.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(conpassword.getText().toString())){
                    Toast.makeText(register.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    admininfo u = new admininfo(name.getText().toString(),password.getText().toString());
                    db.getReference().child("register").child("").push().setValue(u);
                    Toast.makeText(register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                    password.setText("");
                    name.setText("");
                    conpassword.setText("");
                    finish();
                }


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(register.this, "Thank you", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
