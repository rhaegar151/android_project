package com.example.yogeshwar.rentalplatform;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class sk extends AppCompatActivity {

    private final static int PICK_IMAGE_RQ=1;

    book_details b;
    shopdetails s;
    int flag;
    DatabaseReference db,db3;
    StorageReference sR;
    StorageTask stk;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    String eml,src;
    Button b5,b3,b7,b8,b9,b10,b11;
    ImageView img;
    ProgressBar pb1;
    RadioButton r1,r2,r3,r4,r6,r7,r8,r9;
    Uri imguri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sk);

        Bundle data=getIntent().getExtras();
        eml=data.getString("user");

        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        e3=(EditText)findViewById(R.id.e3);
        b5=(Button)findViewById(R.id.b5);
        b3=(Button)findViewById(R.id.b3);
        pb1=(ProgressBar)findViewById(R.id.pb1);
        img=(ImageView)findViewById(R.id.img);
        r1=(RadioButton)findViewById(R.id.r1);
        r2=(RadioButton)findViewById(R.id.r2);
        r6=(RadioButton)findViewById(R.id.r6);
        r7=(RadioButton)findViewById(R.id.r7);
        r8=(RadioButton)findViewById(R.id.r8);
        r9=(RadioButton)findViewById(R.id.r9);
        e4=(EditText)findViewById(R.id.e4);
        e5=(EditText)findViewById(R.id.e5);
        b8=(Button)findViewById(R.id.b8);
        e6=(EditText)findViewById(R.id.e6);
        e7=(EditText)findViewById(R.id.e7);
        e8=(EditText)findViewById(R.id.e8);
        b9=(Button)findViewById(R.id.b9);
        r3=(RadioButton)findViewById(R.id.r3);
        r4=(RadioButton)findViewById(R.id.r4);
        b11=(Button)findViewById(R.id.b11);
        e9=(EditText)findViewById(R.id.e9);
        e10=(EditText)findViewById(R.id.e10);

        b7=(Button)findViewById(R.id.b7);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("@");
        arrayList.add(".");
        arrayList.add("_");

        for (int i = 0; i < arrayList.size(); i++) {
            if (eml.contains(arrayList.get(i)));
            eml=eml.replace(arrayList.get(i), "");}
    }

    public void info(View view){

        Intent i=new Intent(this,detail_info.class);
        i.putExtra("umail",eml);
        startActivity(i);
    }

    public void sign_out(View view){

     Intent i=new Intent(this,MainActivity.class);
     startActivity(i);
    }



    public void add(View view)
    {
       makeinvisible();

        e1.setVisibility(View.VISIBLE);
        b5.setVisibility(View.VISIBLE);
        b3.setVisibility(View.VISIBLE);
        img.setVisibility(View.VISIBLE);
        pb1.setVisibility(View.VISIBLE);
        e2.setVisibility(View.VISIBLE);
        e3.setVisibility(View.VISIBLE);
        r6.setVisibility(View.VISIBLE);
        r7.setVisibility(View.VISIBLE);
    }

    public void CF(View view)
    {
      Intent i=new Intent();
      i.setType("image/*");
      i.setAction(Intent.ACTION_GET_CONTENT);

      startActivityForResult(i,PICK_IMAGE_RQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_RQ && resultCode==RESULT_OK && data!=null && data.getData()!=null){

            imguri=data.getData();
            Toast.makeText(this,imguri.toString(),Toast.LENGTH_LONG).show();
            Picasso.with(this).load(imguri).into(img);

        }

    }

    private String gfe(Uri u){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(u));
    }

    public void don(View view) {

        if(!validateform()){
            return;
        }

        if(stk!=null && stk.isInProgress()){
            Toast.makeText(this,"Upload in progress",Toast.LENGTH_SHORT).show();

        }
        else if (imguri != null) {

            sR= FirebaseStorage.getInstance().getReference("books");

            Toast.makeText(sk.this,"line "+138,Toast.LENGTH_SHORT).show();
            StorageReference fR=sR.child(System.currentTimeMillis()+"."+gfe(imguri));
            b = new book_details();            // do not place 142,143,144 inside inner class
            b.setBname(e1.getText().toString()); // will throw error
            b.setPrice(Integer.parseInt(e2.getText().toString()));
            b.setNc(Integer.parseInt(e3.getText().toString()));

              stk=fR.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                  @Override
                  public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                      Handler h=new Handler();
                      h.postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              pb1.setProgress(0);
                          }
                      },500);
                      Toast.makeText(sk.this,"upload successful",Toast.LENGTH_SHORT).show();
              }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                  Toast.makeText(sk.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                  }
              }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                  pb1.setProgress((int)progress);
                  }
              }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                      task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                          @Override
                          public void onSuccess(Uri uri) {
                              if(r6.isChecked())
                              db = FirebaseDatabase.getInstance().getReference().child("book").child(eml);
                              else if(r7.isChecked())
                                  db = FirebaseDatabase.getInstance().getReference().child("dvd").child(eml);

                              b.setMurl(uri.toString());
                              db.child(""+System.currentTimeMillis()).setValue(b);
                          }
                      });
                  }
              });



        }
        else
            Toast.makeText(this,"No file selected",Toast.LENGTH_LONG).show();

        e1.setText("");
        e2.setText("");
    }

    void makeinvisible(){

        e1.setVisibility(View.GONE);
        e3.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        img.setVisibility(View.GONE);
        pb1.setVisibility(View.GONE);
        e2.setVisibility(View.GONE);
        r1.setVisibility(View.GONE);
        r2.setVisibility(View.GONE);
        r6.setVisibility(View.GONE);
        r7.setVisibility(View.GONE);
        r8.setVisibility(View.GONE);
        r9.setVisibility(View.GONE);

        e6.setVisibility(View.GONE);
        e7.setVisibility(View.GONE);
        e8.setVisibility(View.GONE);
        b9.setVisibility(View.GONE);

        e5.setVisibility(View.GONE);
        b7.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);
        e4.setVisibility(View.GONE);

        r3.setVisibility(View.GONE);
        r4.setVisibility(View.GONE);
        b11.setVisibility(View.GONE);
        e9.setVisibility(View.GONE);
        e10.setVisibility(View.GONE);

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
        if(!r6.isChecked() && !r7.isChecked())
        {
            Toast.makeText(this,"please choose any one option",Toast.LENGTH_LONG).show();
            vd=false;
        }

        return vd;

    }

    public void upd(View view){


     makeinvisible();
        r1.setVisibility(View.VISIBLE);
        r2.setVisibility(View.VISIBLE);
        b7.setVisibility(View.VISIBLE);
    }

    public void doupd(View view){


        if(r1.isChecked()){

            e4.setVisibility(View.VISIBLE);
            e5.setVisibility(View.VISIBLE);
            e10.setVisibility(View.VISIBLE);
            b8.setVisibility(View.VISIBLE);
            r8.setVisibility(View.VISIBLE);
            r9.setVisibility(View.VISIBLE);

        }

        else if(r2.isChecked()){

            e4.setVisibility(View.GONE);
            e5.setVisibility(View.GONE);
            b8.setVisibility(View.GONE);




            e6.setVisibility(View.VISIBLE);
            e7.setVisibility(View.VISIBLE);
            e8.setVisibility(View.VISIBLE);
            b9.setVisibility(View.VISIBLE);
        }
        Toast.makeText(this,"Update the necessary fields"+"\n"+
                "Leave the rest blank",Toast.LENGTH_LONG).show();

   }
   public void modifyPinfo(View view){

       db3=FirebaseDatabase.getInstance().getReference().child("Shopkeeper");

       db3.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot i:dataSnapshot.getChildren()){
                   s=i.getValue(shopdetails.class);

                   if(eml.equals(i.getKey())){

                       if(!TextUtils.isEmpty(e6.getText().toString()))
                          s.setNam(e6.getText().toString());

                       if(!TextUtils.isEmpty(e7.getText().toString()))
                           s.setLat(Double.parseDouble(e7.getText().toString()));

                       if(!TextUtils.isEmpty(e8.getText().toString()))
                           s.setLg(Double.parseDouble(e8.getText().toString()));

                       db3.child(i.getKey()).setValue(s);
                       Toast.makeText(sk.this,"Shop details updated successfully",Toast.LENGTH_SHORT).show();
                       break;
                   }
               }



           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


   }
   public void modifyItem(View view){
        if(TextUtils.isEmpty(e4.getText().toString())){
            e4.setError("Required.");
            return;
        }


       src=e4.getText().toString();

        if(r8.isChecked())
        db3=FirebaseDatabase.getInstance().getReference().child("book").child(eml);
        else if(r9.isChecked())
            db3=FirebaseDatabase.getInstance().getReference().child("dvd").child(eml);
        flag=1;


        db3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i:dataSnapshot.getChildren()){
                    b=i.getValue(book_details.class);
                   if(src.equals(b.getBname())){
                       flag=0;

                     if(!TextUtils.isEmpty(e10.getText().toString()))
                       b.setNc(Integer.parseInt(e10.getText().toString()));

                       if(!TextUtils.isEmpty(e5.getText().toString()))
                           b.setPrice(Integer.parseInt(e5.getText().toString()));

                       db3.child(i.getKey()).setValue(b);
                       Toast.makeText(sk.this,b.getNc()+"info updated successfully",Toast.LENGTH_SHORT).show();
                       e5.setText("");
                       e10.setText("");
                       e4.setText("");
                       break;
                   }
                }
                if(flag==1){
                  Toast.makeText(sk.this,"No such item exist in your records"+"\n"+
                   "You may add one if you wish so",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



   }

   public void deleteitem(View view){

        makeinvisible();



       r3.setVisibility(View.VISIBLE);
       r4.setVisibility(View.VISIBLE);
       b11.setVisibility(View.VISIBLE);
       e9.setVisibility(View.VISIBLE);

   }

   public void deleteit(View view){

      if(r3.isChecked()){
          db3=FirebaseDatabase.getInstance().getReference().child("book").child(eml);
      }
      else if(r4.isChecked()){
          db3=FirebaseDatabase.getInstance().getReference().child("dvd").child(eml);
      }

      src=e9.getText().toString();
      flag=1;
      db3.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot i:dataSnapshot.getChildren()){
                  b=i.getValue(book_details.class);
                  if(src.equals(b.getBname())){
                      db3.child(i.getKey()).removeValue();
                      Toast.makeText(sk.this,"Item deleted successfully",Toast.LENGTH_LONG).show();
                      flag=0;
                      break;
                  }
              }
              if(flag==1){
                  Toast.makeText(sk.this,"No such item exist in your records",Toast.LENGTH_LONG).show();
              }


          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });

       e9.setText("");

   }


}
