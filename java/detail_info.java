package com.example.yogeshwar.rentalplatform;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class detail_info extends AppCompatActivity {

DatabaseReference db;
EditText e1,e2,e3;
Button b;
String umail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        b=(Button)findViewById(R.id.b);


    }

    public void don(View view){
        //db.FirebasegetReference().child("shopkeeper");
        db= FirebaseDatabase.getInstance().getReference();
        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);

        if(!validateform()){
            return;
        }

        Bundle data=getIntent().getExtras();
        umail=data.getString("umail");

        final shopdetails s=new shopdetails();
        s.setNam(e1.getText().toString());
        double l1,l2;
        l1=Double.parseDouble(e2.getText().toString());
        l2=Double.parseDouble(e3.getText().toString());
        s.setLat(l1);
        s.setLg(l2);

        db.child("Shopkeeper").child(umail).setValue(s);
        b.setVisibility(View.GONE);
        Toast.makeText(this,"Info recorded successfully",Toast.LENGTH_LONG).show();

    }
    boolean validateform(){
        boolean vd = true;
        if (TextUtils.isEmpty(e1.getText().toString())) {

            e1.setError("Required.");

            vd = false;

        } else {

            e1.setError(null);

        }

        if (TextUtils.isEmpty(e2.getText().toString())) {

            e2.setError("Required.");

            vd = false;

        } else {

            e2.setError(null);

        }

        if (TextUtils.isEmpty(e3.getText().toString())) {

            e3.setError("Required.");

            vd = false;

        } else {

            e3.setError(null);

        }



        return vd;

    }
}
