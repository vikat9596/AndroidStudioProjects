package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText edtWeight, edtHeightFt, edtHeightIn;
        Button btnCal;
        TextView txtResult;
        LinearLayout linearMain;

        edtWeight = findViewById(R.id.edtWeight);
        edtHeightFt = findViewById(R.id.edtHeightFt);
        edtHeightIn = findViewById(R.id.edtHeightIn);
        btnCal = findViewById(R.id.btnCal);
        txtResult = findViewById(R.id.txtResult);
        linearMain = findViewById(R.id.linearMain);


        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int wt = Integer.parseInt(edtWeight.getText().toString());
               int ft = Integer.parseInt(edtHeightFt.getText().toString());
               int in = Integer.parseInt(edtHeightIn.getText().toString());

               int totalIn = ft*12 + in;

               double totalCm = totalIn*2.53;

               double totalM = totalCm/100;

               double bmi = wt/(totalM*totalM);

               if (bmi>25){
                   txtResult.setText("You are Overweight!");
                   linearMain.setBackgroundColor(getResources().getColor(R.color.Red));
               } else if (bmi<18){
                   txtResult.setText("You are Under Weight!");
                   linearMain.setBackgroundColor(getResources().getColor(R.color.Yellow));
               } else {
                   txtResult.setText("You are Healthy!");
                   linearMain.setBackgroundColor(getResources().getColor(R.color.Green));

               }
            }
        });



    }
}