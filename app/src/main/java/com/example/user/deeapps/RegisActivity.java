package com.example.user.deeapps;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class RegisActivity extends AppCompatActivity {

    private Button mRegistration;
    private EditText mEmail , mPassword, mName;
    private FirebaseAuth mAtuh;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user!=null){
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    return;

                }
            }
        };
        mAtuh = FirebaseAuth.getInstance();
        mRegistration   = findViewById(R.id.registrasi);
        mEmail    = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mName     = findViewById(R.id.name);

        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String name = mName.getText().toString();

                mAtuh.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisActivity.this, new OnCompleteListener<AuthResult>()   {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(getApplication(), "Sign In Error", Toast.LENGTH_SHORT).show();
                        }else{
                            String userId = mAtuh.getCurrentUser().getUid();
                            DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                              Map userInfo = new HashMap<>();
                              userInfo.put("email",email);
                              userInfo.put("name",name);
                              userInfo.put("profileImageUrl","default");

                              currentUserDb.updateChildren(userInfo);
                        }

                    }
                });
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        mAtuh.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAtuh.removeAuthStateListener(firebaseAuthStateListener);
    }
}
