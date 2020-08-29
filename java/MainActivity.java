package com.example.yogeshwar.rentalplatform;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText e1,e2;
    RadioButton r1,r2;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=(EditText)findViewById(R.id.e1);
        e2=(EditText)findViewById(R.id.e2);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    public void sign_up(View view){
        createSignInIntent();
    }
    public void createSignInIntent() {

        // [START auth_fui_create_intent]

        // Choose authentication providers

        List<AuthUI.IdpConfig> providers = Arrays.asList(

                new AuthUI.IdpConfig.EmailBuilder().build());



        // Create and launch sign-in intent

        startActivityForResult(

                AuthUI.getInstance()

                        .createSignInIntentBuilder()

                        .setAvailableProviders(providers)

                        .build(),

                RC_SIGN_IN);

        // [END auth_fui_create_intent]

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);



            if (resultCode == RESULT_OK) {

                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(MainActivity.this,"Welcome "+user.getDisplayName()
                        + "\nPlease sign in now....",Toast.LENGTH_LONG).show();
                // ...

            } else {

                // Sign in failed. If response is null the user canceled the

                // sign-in flow using the back button. Otherwise check

                // response.getError().getErrorCode() and handle the error.

                // ...

            }

        }

    }

    // [END auth_fui_result]

    public void sign_in(View view)
    {
        signIn(e1.getText().toString(),e2.getText().toString());
    }



    private void signIn(String email, String password) {



        if (!validateForm()) {

            return;

        }




        // [START sign_in_with_email]

        mAuth.signInWithEmailAndPassword(email, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            // Sign in success, update UI with the signed-in user's information
                            r1=(RadioButton)findViewById(R.id.r1);
                            r2=(RadioButton)findViewById(R.id.r2);

                            FirebaseUser user = mAuth.getCurrentUser();
                            if(r1.isChecked()) {
                                Intent i = new Intent(MainActivity.this, sk.class);
                                i.putExtra("user", user.getEmail());
                                startActivity(i);
                            }
                            else if(r2.isChecked())
                            {
                                Intent i = new Intent(MainActivity.this, cust.class);
                                i.putExtra("user", user.getDisplayName());
                                startActivity(i);
                            }
                            //updateUI(user);

                        } else {

                            // If sign in fails, display a message to the user.

                            // Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this,"Incorrect combination ",Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                        }
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]

                    }

                });

        // [END sign_in_with_email]

    }

    private boolean validateForm() {

        boolean valid = true;



        String email = e1.getText().toString();

        if (TextUtils.isEmpty(email)) {

            e1.setError("Required.");

            valid = false;

        } else {

            e1.setError(null);

        }



        String password = e2.getText().toString();

        if (TextUtils.isEmpty(password)) {

            e2.setError("Required.");

            valid = false;

        } else {

            e2.setError(null);

        }



        return valid;

    }


}
