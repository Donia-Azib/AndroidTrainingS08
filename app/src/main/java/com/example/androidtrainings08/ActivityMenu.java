package com.example.androidtrainings08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ActivityMenu extends AppCompatActivity {

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

//    2 method bech najmou n'accediw lel menu "logout_menu"
//    1er method creation menu ely mawjoud "logout_menu" fi weset el action bar "ActivityMenu"

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    2 eme method action 3al les btn du menu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        1 verifier ama howa el btn ely saretlou el action
        if(item.getItemId() == R.id.logout_btn)
        {
            sharedPreferences.edit().putBoolean("logged",false).remove("email_user").remove("id_user").apply();
            startActivity(new Intent(ActivityMenu.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}