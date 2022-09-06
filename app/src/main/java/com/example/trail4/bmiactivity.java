package com.example.trail4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.data.SingleRefDataBufferIterator;

import java.text.DecimalFormat;

public class bmiactivity extends AppCompatActivity {

    android.widget.Button mrecalculatebmi;

    TextView mbmidisplay,mbmicategory,mgender;
    Intent intent;
    ImageView mimageview;
    String mbmi;
    double intbmi;

    String height;
    String weight;
    double intheight,intweight;
    RelativeLayout mbackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"white\"><//font>"));
        getSupportActionBar().setTitle("Result");
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#1e1d1d"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        intent=getIntent();

        mbmidisplay=findViewById(R.id.bmidisplay);
        mbmicategory=findViewById(R.id.bmicategory);
        mgender=findViewById(R.id.genderdisplay);
        mimageview=findViewById(R.id.imageview);
        mrecalculatebmi=findViewById(R.id.recalculatebmi);
        mbackground=findViewById(R.id.contentlayout);

        height=intent.getStringExtra("height");
        weight=intent.getStringExtra("weight");

        intheight=Double.parseDouble(height);
        intweight=Double.parseDouble(weight);





        intheight=intheight/100;

        intbmi=intweight/(intheight*intheight);

        mbmi=Double.toString(intbmi);


        double finalbmi = Math.floor(intbmi * 100) / 100;

        String pattern="#.###";
        DecimalFormat decimalFormat=new DecimalFormat(pattern);
        String f=decimalFormat.format(finalbmi);
         double fi=Double.parseDouble(f);

        if(fi<16){
            mbmicategory.setText("Severe Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.crosss);
        }
        else if (fi<16.9 && fi>16){
            mbmicategory.setText("Moderate Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.warning);
        }
        else if (fi<18.4 && fi>17){
            mbmicategory.setText("Mild Thinness");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.warning);
        }
        else if (fi<25 && fi>18.4){
            mbmicategory.setText("Normal");
            //mbackground.setBackgroundColor(Color.YELLOW);
            mimageview.setImageResource(R.drawable.ok);
        }
        else if (fi<29.4 && fi>25) {
            mbmicategory.setText("Overweight");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.warning);
        }
        else{
            mbmicategory.setText("Obese");
            mbackground.setBackgroundColor(Color.RED);
            mimageview.setImageResource(R.drawable.crosss);
        }


        mgender.setText(intent.getStringExtra("gender"));
        mbmidisplay.setText(mbmi);


        mrecalculatebmi=findViewById(R.id.recalculatebmi);

        mrecalculatebmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(bmiactivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}