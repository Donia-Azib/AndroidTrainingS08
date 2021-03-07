package com.example.androidtrainings08;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//loginAct
public class MainActivity extends AppCompatActivity {
    private TextView text_register;
    private EditText email, pass;
    private Button btn;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//  1er arg : MyPREFERENCES = le nom de shared Pref
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        checkUser();

        text_register = findViewById(R.id.text_Register);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btn = findViewById(R.id.btn_login);


        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                1-nekho les str mel editText
//                2- nvérifiw est ce le form cv ou nn
//                3- modifer sharedpref
                String email_str = email.getText().toString();
                String pass_str = pass.getText().toString();
                if(email_str.isEmpty() || pass_str.isEmpty())
                    Toast.makeText(MainActivity.this, "Complete the form ... !", Toast.LENGTH_SHORT).show();
                else
                {
                    sharedpreferences.edit().putBoolean("logged",true).putString("email_user",email_str).apply();
                    startActivity(new Intent(MainActivity.this,ActivityMenu.class));
                }
            }
        });

    }


    private void checkUser() {
        // tvérifi el shared pref est ce que 3andha var logged ou nn
        // tvérifi est ce que logged == true ou bien == false
        // == true => MenuAct
//        == false => loginAct
        if(sharedpreferences.contains("logged"))
        {
            if(sharedpreferences.getBoolean("logged"))
//                ==true
                startActivity(new Intent(MainActivity.this,ActivityMenu.class));
        }
        else
        {
            sharedpreferences.edit().putBoolean("logged",false).apply();
        }
    }
}