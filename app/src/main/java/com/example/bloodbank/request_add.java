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

import java.util.Calendar;

public class request_add extends AppCompatActivity {
    Button finish,clear;
    ImageView image;
    FirebaseDatabase db,db1;
    DatabaseReference database1,database2;
    Spinner spinner;
    int avail;
    int ap=0,an=0,bn=0,bp=0,on=0,op=0,abp=0,abn=0;
    int apr=0,anr=0,bnr=0,bpr=0,onr=0,opr=0,abpr=0,abnr=0;


    int m=10000;
    TextInputEditText amount,name,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        find_donation();
        SharedPreferences sh= getSharedPreferences("MySharedPref",MODE_PRIVATE);
        m=sh.getInt("number",1000);
        finish = findViewById(R.id.finish);
        spinner=findViewById(R.id.blood);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.bloodgroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        clear = findViewById(R.id.clear);
        phone=findViewById(R.id.phone);
        image = findViewById(R.id.back);
        db = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");
        db1 = FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app");
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText(" ");
                amount.setText(" ");
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {
                m=m+1;
                if(name.getText().toString().isEmpty()||phone.getText().toString().length()!=10||spinner.getSelectedItem().toString().equals("\t\tSelect Blood Group")||amount.getText().toString().isEmpty())
                {
                    Toast.makeText(request_add.this, "Please enter all the fields with valid details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    switch (spinner.getSelectedItem().toString()){
                        case "\t\tAB+ve":
                            avail=abp-abpr;
                            break;
                        case "\t\tAB-ve":
                            avail=abn-abnr;
                            break;
                        case "\t\tB+ve":
                            avail=bp-bpr;
                            break;
                        case "\t\tB-ve":
                            avail=bn-bnr;
                            break;
                        case "\t\tA+ve":
                            avail=ap-apr;
                            break;
                        case "\t\tA-ve":
                            avail=an;
                            break;
                        case "\t\tO+ve":
                            avail=op-opr;
                            break;
                        case "\t\tO-ve":
                            avail=on-onr;
                            break;
                    }
                    int need= new Integer(amount.getText().toString());
                    donorinfo u = new donorinfo(name.getText().toString(), phone.getText().toString(),spinner.getSelectedItem().toString(), amount.getText().toString(), Calendar.getInstance().getTime());
                    if(avail>need){
                        db.getReference().child("requestinfo").child("" + m).setValue(u);
                        Toast.makeText(request_add.this, "data with id "+m+" added successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();
                        myEdit.putInt("number", m);
                        myEdit.apply();
                        amount.setText("");
                        name.setText("");
                       startActivity(new Intent(request_add.this,request_add.class));
                    }
                    else
                    {
                        Toast.makeText(request_add.this, "Sorry, Available "+u.blood.replaceAll("\\s","")+" is just "+avail+"ml", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(request_add.this, "Thank you", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void find_donation() {
        ap=an=bn=bp=on=op=abp=abn=0;
        database1=FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("donorinfo");
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    donorinfo donor=dataSnapshot.getValue(donorinfo.class);
                    if(donor.blood.equals("\t\tAB+ve"))
                    {
                        abp=abp+ new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tAB-ve")){
                        abn=abn+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tB+ve")){
                        bp=bp+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tB-ve")){
                        bn=bn+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tA+ve")){
                        ap=ap+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tA-ve")){
                        an=an+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tO+ve")){
                        op=op+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tO-ve")){
                        on=on+new Integer(donor.amount.toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        database2=FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("requestinfo");
        database2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    donorinfo donor=dataSnapshot.getValue(donorinfo.class);
                    if(donor.blood.equals("\t\tAB+ve"))
                    {
                        abpr=abpr+ new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tAB-ve")){
                        abnr=abnr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tB+ve")){
                        bpr=bpr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tB-ve")){
                        bnr=bnr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tA+ve")){
                        apr=apr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tA-ve")){
                        anr=anr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tO+ve")){
                        opr=opr+new Integer(donor.amount.toString());
                    }
                    else if(donor.blood.equals("\t\tO-ve")){
                        onr=onr+new Integer(donor.amount.toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
