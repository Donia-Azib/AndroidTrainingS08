package com.example.androidtrainings08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,pass,username,cnfpass;
    private Button btn_register;
    private ImageButton btn_back;
    private TextView error_txt;


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
        error_txt = findViewById(R.id.msg_error);


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
                       SignUpUser(email_str,username_str,pass_str,cnf_pass_str);
                    }
                    else
                        Toast.makeText(RegisterActivity.this, "Password not match .....", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void SignUpUser(String email, String username, String pass , String cnfPass)
    {
        try {
            String api_singup_api = "https://pure-crag-04729.herokuapp.com/api/auth/signup";

            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("username",username);
            jsonBody.addProperty("email",email);
            jsonBody.addProperty("password",pass);

            Ion.with(RegisterActivity.this)
                    .load(api_singup_api).setJsonObjectBody(jsonBody)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if(e != null)
                            {
                                Toast.makeText(RegisterActivity.this, "Something went wrong ... !", Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "onCompleted: Something went wrong ... ! error = "+e.getMessage() );
                            }
                            else
                            {
//                            scenarios  :
//                            1- Correct user signup
//                            2- email exist : expected `email` to be unique

//                                scenario 1
                                if(!result.has("error"))
                                {
                                    String msg = result.get("message").getAsString();
                                    Toast.makeText(RegisterActivity.this,msg, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    error_txt.setText(email+ " exist ... !");
                                    error_txt.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });





        }catch (Exception e)
        {
            Log.e("TAG", "SignUpUser: ERROR "+e.getMessage() );
        }
    }
}