package com.sani.shaheed.studentattendance

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logInButton = findViewById(R.id.loginButton) as Button
        val usernameEdt = findViewById(R.id.userName) as EditText
        val passwordEdt = findViewById(R.id.password) as EditText

        logInButton.setOnClickListener {
            if (usernameEdt.text != null && usernameEdt.text.toString() == "infortech" && passwordEdt.text.toString() == "password" && passwordEdt.text != null) {
                startActivity(Intent(this@MainActivity1, ScanActivity::class.java))
                finish()
            }
        }
    }
}