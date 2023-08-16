package com.example.homeambalaunce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.chaquo.python.PyObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class burn_input_Activity extends AppCompatActivity {
    RadioGroup cause,depth;
    Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burn_input);
        try{
            cause=(RadioGroup)findViewById(R.id.cause);
            depth=(RadioGroup)findViewById(R.id.depth);
            confirm=(Button)findViewById(R.id.confirm_brn);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selected_id_cause=cause.getCheckedRadioButtonId();
                    int selected_id_depth=depth.getCheckedRadioButtonId();
                    if ((selected_id_cause==-1)||(selected_id_depth==-1)){
                        results_utils.showDialog(burn_input_Activity.this,"عفوا يجب ادخل البيانات كاملة" );
                    }else{
                            String selected_nameId_cause = getResources().getResourceEntryName(selected_id_cause);
                            String selected_nameId_depth = getResources().getResourceEntryName(selected_id_depth);
                            boolean isLargeArea_bool = selected_nameId_depth.equals("largeArea");
                            boolean hasRiskCondition_bool = hasRiskCodition();
                            PyObject obj=results_utils.startPython(burn_input_Activity.this,"burn_py");
                            Boolean isRisked=obj.callAttr("getIsRisk",selected_nameId_cause,selected_nameId_depth,isLargeArea_bool,hasRiskCondition_bool).toBoolean();
                            Map<String,StringBuffer> data=loadBurnFirsTAid(isRisked,selected_id_cause);
                            results_utils.goToActivityWithData(burn_input_Activity.this,showFirstAds.class,data);
                    }
            }});



        }catch(Exception ex){
            results_utils.showDialog(burn_input_Activity.this,"sorry ,something went wrong ,see logs\n");
            Log.e("error",ex.toString());
        }
    }

    private Map<String, StringBuffer> loadBurnFirsTAid(boolean isrisked,int selected_id_cause) {
        Map<String, StringBuffer> data=new HashMap<>();
        data.put("problem",new StringBuffer("اسعافات حرائق"));
        data.put("isrisked",new StringBuffer(isrisked?"حالة خطر يجب الذهاب بسرعى المستشفى":"حالة ليست خطيرة"));

        String typeOfBurn_arabic=((RadioButton)findViewById(selected_id_cause)).getText().toString();
        data.put("type",new StringBuffer(typeOfBurn_arabic));
        StringBuffer getFirstAid=getFirstAid(typeOfBurn_arabic);
        data.put("FirstAid",getFirstAid);

        data.put("callIfRisk",new StringBuffer(isrisked?"الحالة خطرة يرجاء الاتصال ب08067335555 ":""));

        return data;
    }

    private StringBuffer getFirstAid(String typeOfBurnArabic) {
        StringBuffer firstAid=new StringBuffer();
        firstAid.append("حلول عامة:1-حاول تبريد مكان الجرح بالماء عالاقل 10دقائق\n" +
                        "2-قم بحماية المنطقة المصابة\n"+
                        "3-لاتقم بفتح البثرات\n" +
                "4-قم بوضع مسكن الالام"
                );
        switch(typeOfBurnArabic) {
            case "احتراق لسبب تلامس حرارى":
                firstAid.append("\n-قم بكشف المنطقة المصابة وابعاد اللبس المشتعل عنها" +
                        "-اجعل المصاب يرقد على جانب واحد لمنعه من التقيو\n" +
                        "-عند جرح شديد شل حركة المنطقة المصابة\n" +
                        "-عند احتراق الملابس:حاول دحرجة المريض ببطى وغطيها ببطانية لاخماد الحريق واتصل بسرعة بالاسعاف\n");
                break;
            case "تلامس مادة كيميائية":
                firstAid.append("-اتبع تعليمات الحماية على المواد\n" +
                        "-عند قيام باى اسعاف لاتنسى PPEلبس الامان\n" +
                        "-غسل المنطقة عالاقل تلت ساعة\n");
                break;
            case "تلامس كهربى":
                firstAid.append("-لا تقترب من المصاب لكن قم بعزله فقط واغلاق لكهراء من المصدر الرئيسى\n" +
                        "-لاتستهين بالاثر الكهربى حتى لو لم يظهر اعراض بعد معافاته\n" );
                break;
            default://تعرض عالى لاشعة
                firstAid.append("-قف منطلة مظلة\n" +
                        "-استخدم كريمات\n" +
                        "-اشرب سوائل باردة\n" +
                        "-قم باستحمام لمدة 10دقائق\n" +
                        "-خد مسكن اللام\n");
        }
        return firstAid;
    }

    private boolean hasRiskCodition(){
        CheckBox large_precent=(CheckBox)findViewById(R.id.large_precent);
        CheckBox import_part=(CheckBox)findViewById(R.id.import_part);
        CheckBox sychological=(CheckBox)findViewById(R.id.sychological);
        CheckBox poisioned=(CheckBox)findViewById(R.id.poisioned);
        if (large_precent.isChecked()||import_part.isChecked()||sychological.isChecked()||poisioned.isChecked()){
            return true;
        }
        return false;
    }
}