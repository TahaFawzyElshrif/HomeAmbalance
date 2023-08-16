package com.example.homeambalaunce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.HashMap;
import java.util.Map;

public class Diabetes_type extends AppCompatActivity {
    Button confirm;
    TextView ageTXT,weightTXT,heightTXT,fast,unfast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_type);
        confirm =(Button) findViewById(R.id.confirm_db);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ageTXT=(TextView) findViewById(R.id.age);
                 weightTXT=(TextView) findViewById(R.id.weight);
                 heightTXT=(TextView) findViewById(R.id.height);
                 fast=(TextView) findViewById(R.id.fast);
                 unfast=(TextView) findViewById(R.id.unfast);
                 Map<String, StringBuffer> firstAid=loadDiabetesFirsTAid();
                 results_utils.goToActivityWithData(Diabetes_type.this,showFirstAds.class,firstAid);

        }});




    }
    private Map<String, StringBuffer> loadDiabetesFirsTAid() {
        StringBuffer SugarType=SugarType();
        String HighOrLowOrHealth=HighOrLowOrHealth();

        Map<String, StringBuffer> data = new HashMap<>();
        data.put("problem", new StringBuffer("اسعافات السكر"));
        data.put("isrisked", new StringBuffer(HighOrLowOrHealth));//isrisked refer to case part in showFirstAds activity
        data.put("type", new StringBuffer(SugarType));
        StringBuffer getFirstAid = firstAidDb();
        data.put("FirstAid", getFirstAid);


        return data;
    }
    private StringBuffer firstAidDb() {
        StringBuffer FA=new StringBuffer();
        FA.append("-قم باعطاءه مادة عصير سكرى اذا كان انخفاض سكر واذا كان ارتفاع اجعل يتناول سائل بدون سكر(عند وجود نوبة)" +
                "-اذا كان مغمى عليه اتصل ب\n000" +
                "-عند حالة خطرة تابع العامات الحيوية حتى تاتى المستشفى\n" +
                "-ابقى المريض دافئا (عند نوبات)\n" +
                "-لاتنسى تناول الاغذية الصحية ورياضة وغذاء منتظم واخذ دواء بانتظام\n");
        return FA;
    }

    private String HighOrLowOrHealth() {
        try{
            String s="بناء على القياسات المدخلة:";
            double fast_num=Double.parseDouble(fast.getText().toString());
            double unfast_num=Double.parseDouble(unfast.getText().toString());
            if ((fast_num>70 && fast_num<100)&&(unfast_num>70 && unfast_num<140)){
                return s+"مستوى السكر طبيعى";
            }else if ((fast_num<=70)&&(unfast_num<=70)){
                return s+"مستوى السكر منخفض";

            }else if ((fast_num<=100)&&( unfast_num>=140)){
                return s+"مستوى السكر مرتفع";

            }else{
                return s+ "ادخل البيانات فى كل الحقلين او راجع بياناتك او اتصل بالدكتور";
            }
        }catch(NumberFormatException ex){
            //results_utils.showDialog(Diabetes_type.this,"sorry ,check input \n");
            //Log.e("error",ex.toString());
            return "";
        }
    }

    private StringBuffer SugarType(){
            try{
                // casting inputs ,so used try catch to check it
                StringBuffer msg=new StringBuffer();
                double age=Double.parseDouble(ageTXT.getText().toString());
                double weight=Double.parseDouble(weightTXT.getText().toString());
                double height=Double.parseDouble(heightTXT.getText().toString());
                PyObject obj=results_utils.startPython(Diabetes_type.this,"diabetes_py");
                Boolean isType2=obj.callAttr("checkType2",age,weight,height).toBoolean();
                msg.append("بناء على المعلومات المدخلة :عمر : "+age+" ,وزن= "+weight+" ,طول" +
                        "= "+height+".\n المريض ربما يكون مريض سكر من نوع "+(isType2?"2":"1"));
                return msg;
            }catch(Exception ex){
              //  results_utils.showDialog(Diabetes_type.this,"sorry ,check input \n");
               // Log.e("error",ex.toString());
                return new StringBuffer("");
            }

    }

}