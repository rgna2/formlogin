package com.example.user.deeapps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegctivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_regctivity);

        Button mLogin = findViewById(R.id.Login);
        Button mRegis = findViewById(R.id.registrasi);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
                return;

            }
        });
        mRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplication(), RegisActivity.class);
                startActivity(intent);
                return;
            }
        });


    }
}
