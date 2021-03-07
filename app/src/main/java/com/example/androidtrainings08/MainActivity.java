package com.example.androidtrainings08;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

//loginAct
public class MainActivity extends AppCompatActivity {
    private TextView text_register,msg_error;
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
        msg_error = findViewById(R.id.msg_error);
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
                    CheckUserLogin(email_str,pass_str);
                }
            }
        });

    }


    private void CheckUserLogin(String email,String pass){

        try {
            String api_login_url = "https://pure-crag-04729.herokuapp.com/api/auth/login";

            JsonObject body = new JsonObject();
            body.addProperty("email",email);
            body.addProperty("password",pass);

            Ion.with(MainActivity.this)
                    .load(api_login_url)
                    .setJsonObjectBody(body)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if(e != null)
                            {
                                Toast.makeText(MainActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                                Log.e("TAG", "onCompleted: Something wen wrong ERROR = "+e.getMessage() );
                            }
                            else
                            {
//                            scenarios  :
//                            1- Correct email + pass
//                            2- email Not found
//                            3- incorrect password

//                           scenarios  1
                                if(result.has("userId"))
                                {
                                    String userId = result.get("userId").getAsString();
                                    sharedpreferences.edit().putBoolean("logged",true).putString("email_user",email).putString("id_user",userId).apply();
                                    startActivity(new Intent(MainActivity.this,ActivityMenu.class));
                                    finish();
                                }
                                else
//                             scenarios  2+3
                                {
                                    String error = result.get("error").getAsString();
                                    msg_error.setText(error);
                                    msg_error.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

        }catch(Exception e)
        {
            Log.e("TAG", "CheckUserLogin: JsonExcp  =  "+e.getMessage() );
        }



    }


    private void checkUser() {
        // tvérifi el shared pref est ce que 3andha var logged ou nn
        // tvérifi est ce que logged == true ou bien == false
        // == true => MenuAct
//        == false => loginAct
        if(sharedpreferences.contains("logged"))
        {
            if(sharedpreferences.getBoolean("logged",false))
//                ==true
                startActivity(new Intent(MainActivity.this,ActivityMenu.class));
        }
        else
        {
            sharedpreferences.edit().putBoolean("logged",false).apply();
        }
    }
}