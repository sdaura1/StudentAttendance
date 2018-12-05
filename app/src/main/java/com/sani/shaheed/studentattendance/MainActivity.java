package com.sani.shaheed.studentattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText username, password;
    String u, p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                u = username.getText().toString().trim();
                p = password.getText().toString().trim();

                if (u.equals("infortech") && p.equals("password")) {
                    startActivity(new Intent(MainActivity.this, ScanActivity.class));
                    finish();
                }
            }
        });
    }
}
