package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class available_blood extends AppCompatActivity {
    int ap=0,an=0,bn=0,bp=0,on=0,op=0,abp=0,abn=0;
    int apr=0,anr=0,bnr=0,bpr=0,onr=0,opr=0,abpr=0,abnr=0;
    DatabaseReference database1,database2;
    ImageView image;
    TextView apve,anve,bpve,bnve,opve,onve,abpve,abnve;
    Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_blood);
        find_donation();
        refresh=findViewById(R.id.refresh);
        image=findViewById(R.id.back);
        apve=findViewById(R.id.ap);
        anve=findViewById(R.id.an);
        bpve=findViewById(R.id.bp);
        bnve=findViewById(R.id.bn);
        opve=findViewById(R.id.op);
        onve=findViewById(R.id.on);
        abpve=findViewById(R.id.abp);
        abnve=findViewById(R.id.abn);
        apve.setText("\n\n"+"A+VE"+"\n"+ap+" ml");
        anve.setText("\n\n"+"A-VE"+"\n"+an+" ml");
        bpve.setText("\n\n"+"B+VE"+"\n"+bp+" ml");
        bnve.setText("\n\n"+"B-VE"+"\n"+bn+" ml");
        opve.setText("\n\n"+"O+VE"+"\n"+op+" ml");
        onve.setText("\n\n"+"O-VE"+"\n"+on+" ml");
        abpve.setText("\n\n"+"AB+VE"+"\n"+abp+" ml");
        abnve.setText("\n\n"+"AB-VE"+"\n"+abn+" ml");
        Toast.makeText(this, "Please enter refresh button", Toast.LENGTH_SHORT).show();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(available_blood.this, "Blood list updated", Toast.LENGTH_SHORT).show();
                ap=ap-apr;
                an=an-anr;
                bp=bp-bpr;
                bn=bn-bnr;
                op=op-opr;
                on=on-onr;
                abp=abp-abpr;
                abn=abn-abnr;
                apve.setText("\n\n"+"A+VE"+"\n"+ap+" ml");
                anve.setText("\n\n"+"A-VE"+"\n"+an+" ml");
                bpve.setText("\n\n"+"B+VE"+"\n"+bp+" ml");
                bnve.setText("\n\n"+"B-VE"+"\n"+bn+" ml");
                opve.setText("\n\n"+"O+VE"+"\n"+op+" ml");
                onve.setText("\n\n"+"O-VE"+"\n"+on+" ml");
                abpve.setText("\n\n"+"AB+VE"+"\n"+abp+" ml");
                abnve.setText("\n\n"+"AB-VE"+"\n"+abn+" ml");
refresh.setEnabled(false);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(available_blood.this, "Thank you", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void find_donation() {
        ap=an=bn=bp=on=op=abp=abn=0;
        database1= FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("donorinfo");
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