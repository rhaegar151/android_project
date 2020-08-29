package com.example.yogeshwar.rentalplatform;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ListAdapter;



public class cust extends AppCompatActivity {

    DatabaseReference db1,db2;

    TextView t1,t3;
    ListView glist;
    EditText e1;
    ArrayList<hy_brid> a3;
    shopdetails a1[];
    RadioButton r3,r4;
    String a2[];
    int k;//total shops
    int m;
    int ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust);
        final Bundle data=getIntent().getExtras();
        e1=(EditText)findViewById(R.id.e1);
        t1=(TextView)findViewById(R.id.t1);
        t3=(TextView)findViewById(R.id.t3);
        r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);
        t1.setText("Welcome "+data.getString("user"));

        a3=new ArrayList<>();
        db1=FirebaseDatabase.getInstance().getReference().child("Shopkeeper");
        db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              long ct=dataSnapshot.getChildrenCount();
                a1=new shopdetails[(int)ct];
                a2=new String[(int)ct];

                k=0;
                for(DataSnapshot i:dataSnapshot.getChildren()){

                    a1[k]=i.getValue(shopdetails.class);
                    a2[k]=(String) (i.getKey());
                    k++;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        glist=(ListView)findViewById(R.id.glist);
        glist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hy_brid h=(hy_brid) parent.getItemAtPosition(position);
                int y=0;
                try{
                    if(r3.isChecked())
                      y=0;
                    else if(r4.isChecked())
                        y=1;
                    double[] d={h.s.getLat(),h.s.getLg()};
                    String[] s={""+h.s.getLat(),""+h.s.getLg(),""+h.b.getPrice(),h.b.getBname(),h.b.murl,a2[m],""+y};
                    Intent i=new Intent(cust.this,MapsActivity.class);
                    i.putExtra("cdnt",s);

                    startActivity(i);

                }
                catch(Exception e)
                {
                    Toast.makeText(cust.this,"Parsing problem",Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(cust.this,"latitude: "+h.s.getLat()+"Longtitude "
                        +h.s.getLg(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void voices(View view){
        promptSpeechInput();

    }

    void promptSpeechInput(){
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"say something");

        try{
            startActivityForResult(i,100);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(this,"Speech to text not supported in your device",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK && data!=null){
            ArrayList<String> str=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            e1.setText(str.get(0));
        }

    }

    public void sign_out(View view){

        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void srch(View view){
        ct=0;
        a3.clear();
        t3.setVisibility(View.GONE);
        final String s=e1.getText().toString();

        if(r3.isChecked())
        db2= FirebaseDatabase.getInstance().getReference().child("book");
        else if(r4.isChecked())
            db2= FirebaseDatabase.getInstance().getReference().child("dvd");
        else
            db2= FirebaseDatabase.getInstance().getReference().child("book");

        db2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot i:dataSnapshot.getChildren()){

                    for(DataSnapshot j:i.getChildren()){

                        book_details b=j.getValue(book_details.class);

                         if(((b.getBname()).toLowerCase()).contains(s.toLowerCase()) && b.getNc()!=0){

                             a3.add(new hy_brid(b,shop(i.getKey())));
                             ct++;
                          }



                    }

                }

                if(ct==0){
                    t3.setText("NO MATCH FOUND....");
                    t3.setVisibility(View.VISIBLE);
                }
                else {

                    ListAdapter gadapt = new customAdapter(cust.this, a3);
                    glist.setAdapter(gadapt);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    shopdetails shop(final String str){

        int i;
          for(i=0;i<k;i++){
              if(str.equals(a2[i])){
                  m=i;
                  break;
              }

          }

             return a1[i];
    }

}
