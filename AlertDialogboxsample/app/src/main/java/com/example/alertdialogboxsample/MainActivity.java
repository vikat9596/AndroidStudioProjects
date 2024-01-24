package com.example.alertdialogboxsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        alertDialog.setTitle("Terms & Conditions");
        alertDialog.setIcon(R.drawable.baseline_info_24);
        alertDialog.setMessage("Have you read all the terms & Conditions");

        alertDialog.setButton(btn1,);

    }
}