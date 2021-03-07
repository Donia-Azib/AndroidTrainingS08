package com.example.androidtrainings08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,pass,username,cnfpass;
    private Button btn_register;
    private ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        cnfpass = findViewById(R.id.confPass);
        username = findViewById(R.id.username);
        btn_register = findViewById(R.id.btn_register);
        btn_back = findViewById(R.id.btn_back);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = email.getText().toString();
                String pass_str = pass.getText().toString();
                String cnf_pass_str = cnfpass.getText().toString();
                String username_str = username.getText().toString();


                if(email_str.isEmpty() || pass_str.isEmpty() || cnf_pass_str.isEmpty() || username_str.isEmpty())
                    Toast.makeText(RegisterActivity.this, "Complete the form ... !", Toast.LENGTH_SHORT).show();
                else
                {
                    if(pass_str.equals(cnf_pass_str)) {
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(RegisterActivity.this, "Password not match .....", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}