package com.example.homeambalaunce;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;


public class chooseTypeDiagonist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type_diagonist);
        results_utils.showDialog(this,"مرحبا فى HomeAmbalaunce هدف التطبيق هو نظام Expert System للمساعدة عند " +
                "بعذ الطوارى التى تتطلب اسعافات اولية -لقد حاولت تصحح المعلومات لكن حتى الان لم تتم المراجعة من طرف صحى");


        Button diabetes=(Button)findViewById(R.id.Diabete);
        diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results_utils.goToActivity(chooseTypeDiagonist.this,Diabetes_type.class);
            }
        });

        Button about=(Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results_utils.goToActivity(chooseTypeDiagonist.this,about.class);
            }
        });

        Button bleeding=(Button)findViewById(R.id.bleeding);
        bleeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results_utils.goToActivity(chooseTypeDiagonist.this,bleeding_Activ.class);
            }
        });

        Button Burn_Bt=(Button)findViewById(R.id.Burn_Bt);
        Burn_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results_utils.goToActivity(chooseTypeDiagonist.this,burn_input_Activity.class);
            }
        });

        Button eplipsy=(Button)findViewById(R.id.eplipsy);
        eplipsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eplipsyFirstAid();
            }
        });

        Button faint=(Button)findViewById(R.id.faint);
        faint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faintFirstAid();
            }
        });
    }

    private void eplipsyFirstAid() {
        try {
            Map<String, StringBuffer> data = new HashMap<>();
            data.put("problem", new StringBuffer("اسعافات الصرع"));
            data.put("isrisked", new StringBuffer("خطر اذا استمرت النوبة اكثر من دقائق"));
            data.put("type", new StringBuffer("اسعافات الصرع"));
            data.put("FirstAid", new StringBuffer("-ابعده عن الضغط العصبى-سجاير-" +
                    "العاب فديو-ضوء ساطع-شاشات-كحول" +
                    "-ابعد الاشياء الخطرة عن مكانه\n" +
                    "-لاتجعله ياكل او يضع اى شى فى فمه حتى لو دواء وزل اى شى فى فمه\n" +
                    "-لاتخاول تعصبه\n" +
                    "-لاتحاول ان تصمده\n" +
                    "-ابعده عن منبهات\n" +
                    "-اجعله يرقد على جانب واحد\n"));

            results_utils.goToActivityWithData(chooseTypeDiagonist.this, showFirstAds.class, data);
        }catch (Exception ex){
            Log.e("error",ex.toString());
        }
    }


    private void faintFirstAid() {
        try {
            Map<String, StringBuffer> data = new HashMap<>();
            data.put("problem", new StringBuffer("اسعافات الاغماء"));
            data.put("isrisked", new StringBuffer("خطر اذا طولت او ظهرت اعراض اخرى"));
            data.put("type", new StringBuffer("اسعافات الاغماء"));
            data.put("FirstAid", new StringBuffer("" +
                    "-امنعه من السقوط\n" +
                    "-اجعله يرقد على جانب واحد ويرفع قدمه ادا كان ذلك ممكنا\n" +
                    "-اخلع الملابس الضيقة\n" +
                    "-اجعل المريض يجلس ويضع راسه بين ركبتيه\n " +
                    "-اشرب سوائل-تعرض لهواء\n " ));
            results_utils.goToActivityWithData(chooseTypeDiagonist.this, showFirstAds.class, data);
        }catch (Exception ex){
            Log.e("error",ex.toString());
        }
    }


}