package com.example.androidtrainings08;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class ActivityMenu extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Button btn_scan , btn_img;
    private ImageView image;
    private static final int REQUEST_CODE_QR_SCAN = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        btn_scan = findViewById(R.id.btn_qr);
        btn_img = findViewById(R.id.btn_img);
        image = findViewById(R.id.img);


//        1-scan
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tvérifi est ce que la permission granted ou nn !
                if (ContextCompat.checkSelfPermission(ActivityMenu.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED)
                {
//                    ytlob la permission
                    ActivityCompat.requestPermissions(ActivityMenu.this, new String[] {Manifest.permission.CAMERA}, 150);
                }
                else
                {
                    Intent i = new Intent(ActivityMenu.this, QrCodeActivity.class);
                    startActivityForResult( i,REQUEST_CODE_QR_SCAN);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK)
        {
            Log.d("LOGTAG","COULD NOT GET A GOOD RESULT.");
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(ActivityMenu.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d("LOGTAG","Have scan result in your app activity :"+ result);
            AlertDialog alertDialog = new AlertDialog.Builder(ActivityMenu.this).create();
            alertDialog.setTitle("Scan result");
            alertDialog.setMessage(result);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }






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