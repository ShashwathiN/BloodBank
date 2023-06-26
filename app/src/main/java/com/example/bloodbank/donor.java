
package com.example.bloodbank;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.SearchView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class donor extends AppCompatActivity {
    ImageButton newentry,delete,update;
    SearchView searchView;
    ImageView back ;
    TextView name,blood,phone,date,amount;
    FirebaseDatabase db;
    String m;
    ImageView i;
    TableLayout stk;
    View tbr;
    int tabrow=0;

    int chec=9,nameid=10,amountid=11,bloodid=12,phoneid=13,dateid=14;
    boolean bool;
    String str="";
    DatabaseReference database1,database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //i=findViewById(R.id.o);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newentry=findViewById(R.id.newentry);
       // delete=findViewById(R.id.delete);
        stk =  findViewById(R.id.tablelayout);
        update=findViewById(R.id.update);
        searchView=findViewById(R.id.search_bar);
        addHeader();
        adddata();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                database=FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("donorinfo");
                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        stk.removeAllViews();
                        int flag=0;
                        addHeader();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            donorinfo donor=dataSnapshot.getValue(donorinfo.class);
                            m=dataSnapshot.getKey();
                            if(m.equals(s)||donor.name.contains(s)||donor.blood.contains(s)||donor.amount.equals(s)||donor.date.equals(s)||donor.phone.equals(s)) {
                                addTable(m, donor.name.replaceAll("\\s",""),donor.phone.replaceAll("\\s",""),donor.blood.replaceAll("\\s",""),donor.amount);
                                flag=1;
                            }

                        }
                        if(flag==1){
                            Toast.makeText(donor.this, "Results available", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(donor.this, "No such data", Toast.LENGTH_SHORT).show();
                            adddata();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adddata();
                return false;
            }
        });
        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
int checked=0;
                for (int i=1;i<stk.getChildCount();i++){
                    tbr=stk.getChildAt(i);
                    CheckBox check=tbr.findViewById(chec);
                    if(check.isChecked()) {
                        checked=checked+1;
                        str=check.getText().toString();
                        m=check.getText().toString();
                        blood=tbr.findViewById(bloodid);
                        deletetable(str,m,blood.getText().toString());
                    }
                }
                if(checked==0)
                {
                    Toast.makeText(donor.this, "please select something", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(donor.this, "Data deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;
                for (int i=1;i<stk.getChildCount();i++){
                    tbr=stk.getChildAt(i);
                    CheckBox check=tbr.findViewById(chec);
                    if(check.isChecked()) {
                        count=count+1;
                        m=check.getText().toString();
                        name=tbr.findViewById(nameid);
                        blood=tbr.findViewById(bloodid);
                        amount=tbr.findViewById(amountid);
                        phone=tbr.findViewById(phoneid);
                    }
                }
                if(count==0){
                    Toast.makeText(donor.this, "please click something", Toast.LENGTH_SHORT).show();
                }
                else if(count>=2){
                    Toast.makeText(donor.this, "Please edit one record at a time", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent= new Intent(donor.this,update_donor.class);
                    intent.putExtra("id",m);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("amount", amount.getText().toString());
                    intent.putExtra("blood", "\t\t"+blood.getText().toString());
                    intent.putExtra("phone", phone.getText().toString());
                    startActivity(intent);
                }
            }
        });
        newentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(donor.this, donor_add.class);
                startActivity(intent);

            }
        });
    }
    private void adddata() {

        database=FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("donorinfo");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                stk.removeAllViews();
                addHeader();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    donorinfo donor=dataSnapshot.getValue(donorinfo.class);
                    m=dataSnapshot.getKey();
                    addTable(m, donor.name.replaceAll("\\s",""),donor.phone.replaceAll("\\s",""),donor.blood.replaceAll("\\s",""),donor.amount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*public void deletetable(String str,String id,String blood_group) {
        database=FirebaseDatabase.getInstance("https://blood-ad655-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("donorinfo");
        database.child(str).removeValue();

    }
*/
    public void addTable(String m,String name,String phone, String blood, String amount) {
        TableRow tbrow0 = new TableRow(donor.this);
        tbrow0.setId(tabrow);
        CheckBox tv0 = new CheckBox(donor.this);
        tv0.setText(m);
        tv0.setBackgroundColor(Color.WHITE);
        tv0.setTextColor(Color.parseColor("#751111"));
        tv0.setTextSize(13);
        tv0.setId(chec);
        tbrow0.addView(tv0);

        TextView tv1 = new TextView(donor.this);
        tv1.setText(name);
        tv1.setBackgroundColor(Color.WHITE);
        tv1.setId(nameid);
        tv1.setTextColor(Color.parseColor("#751111"));
        tv1.setTextSize(13);
        tbrow0.addView(tv1);

        TextView tv5 = new TextView(donor.this);
        tv5.setText("\t"+phone+"\t\t");
        tv5.setId(phoneid);
        tv5.setBackgroundColor(Color.WHITE);
        tv5.setTextColor(Color.parseColor("#751111"));
        tv5.setTextSize(13);
        tbrow0.addView(tv5);

        TextView tv2 = new TextView(donor.this);
        tv2.setText(blood);
        tv2.setBackgroundColor(Color.WHITE);
        tv2.setTextColor(Color.parseColor("#751111"));
        tv2.setTextSize(13);
        tv2.setId(bloodid);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(donor.this);
        tv3.setText(amount);
        tv3.setId(amountid);
        tv3.setBackgroundColor(Color.WHITE);
        tv3.setTextColor(Color.parseColor("#751111"));
        tv3.setTextSize(13);
        tbrow0.addView(tv3);


        stk.addView(tbrow0);

    }
    public void addHeader() {

        TableLayout stk=(TableLayout) findViewById(R.id.tablelayout);
        TableRow tbrow0 = new TableRow(this);

        TextView tv0=new TextView(this);
        tv0.setText("\n\t\t\tID\t\t\t\n");
        tv0.setTextColor(Color.WHITE);
        //tv0.setBackgroundColor(Color.parseColor("#751111"));
        tv0.setTextSize(15);
        tbrow0.addView(tv0);

        TextView tv1=new TextView(this);
        tv1.setText("\nName\t\t\n");
        tv1.setTextColor(Color.WHITE);
        //tv1.setBackgroundColor(Color.parseColor("#751111"));
        tv1.setTextSize(15);
        tbrow0.addView(tv1);

        TextView tv5=new TextView(this);
        tv5.setText("\n\t\tPhone\t\t\n");
        tv5.setTextColor(Color.WHITE);
        //tv3.setBackgroundColor(Color.parseColor("#751111"));
        tv5.setTextSize(15);
        tbrow0.addView(tv5);

        TextView tv2=new TextView(this);
        tv2.setTextColor(Color.WHITE);
        tv2.setText("\nBlood Group\t\t\n");
        //tv2.setBackgroundColor(Color.parseColor("#751111"));
        tv2.setTextSize(15);
        tbrow0.addView(tv2);

        TextView tv3=new TextView(this);
        tv3.setText("\nAmount(ml)\t\t\n");
        tv3.setTextColor(Color.WHITE);
        //tv3.setBackgroundColor(Color.parseColor("#751111"));
        tv3.setTextSize(15);
        tbrow0.addView(tv3);



        tbrow0.setBackgroundColor(Color.parseColor("#751111"));
        stk.addView(tbrow0);




    }
}





