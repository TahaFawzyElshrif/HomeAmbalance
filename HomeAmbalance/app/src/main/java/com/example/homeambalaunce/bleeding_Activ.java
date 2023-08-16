package com.example.homeambalaunce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chaquo.python.PyObject;

import java.util.HashMap;
import java.util.Map;

public class bleeding_Activ extends AppCompatActivity {
    Button confirm;
    RadioGroup rson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bleeding);
        try {
            rson = (RadioGroup) findViewById(R.id.rson);
            confirm = (Button) findViewById(R.id.confirm_brn);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int selected_id_rson = rson.getCheckedRadioButtonId();
                    if (selected_id_rson == -1) {
                        results_utils.showDialog(bleeding_Activ.this, "عفوا يجب ادخل البيانات كاملة");
                    } else {
                        String selected_nameId_rson = getResources().getResourceEntryName(selected_id_rson);
                        boolean has_risk_condition_bool = hasRiskCodition();
                        PyObject obj = results_utils.startPython(bleeding_Activ.this, "bleeding");
                        Boolean isRisked = obj.callAttr("isrisk", selected_nameId_rson, has_risk_condition_bool).toBoolean();
                        Map<String, StringBuffer> data = loadBleedFirsTAid(isRisked, selected_id_rson);
                        results_utils.goToActivityWithData(bleeding_Activ.this, showFirstAds.class, data);


                    }
                }

            });
        } catch (Exception ex) {
            results_utils.showDialog(bleeding_Activ.this, "sorry ,something went wrong ,see logs\n");
            Log.e("error", ex.toString());
        }
    }

    private Map<String, StringBuffer> loadBleedFirsTAid(Boolean isRisked, int selectedIdRson) {
        Map<String, StringBuffer> data = new HashMap<>();
        data.put("problem", new StringBuffer("اسعافات نزيف"));
        data.put("isrisked", new StringBuffer(isRisked ? "حالة خطر يجب الذهاب بسرعى المستشفى" : "حالة ليست خطيرة"));

        String typeOfBleed_arabic = ((RadioButton) findViewById(selectedIdRson)).getText().toString();
        data.put("type", new StringBuffer(typeOfBleed_arabic));
        StringBuffer getFirstAid = getFirstAid(typeOfBleed_arabic);
        data.put("FirstAid", getFirstAid);


        return data;
    }

    private StringBuffer getFirstAid(String typeOfBleedArabic) {
        StringBuffer firstAid = new StringBuffer();
        switch (typeOfBleedArabic) {
                case "اعراض كدمات او نزيف داخلى": {
                    firstAid.append("-اجعله يرقد على جانب واحد ويرفع ركبته (اذا كان يسمح الجرح)\n" +
                            "-قم بتغطيه الجرح ببطانية مثلا\n" +
                            "-احتفظ بعينة من اى مادة يفرزها\n" +
                            "-الحالة خطرة قم باتصال ب 155");
                    break;

                }
                case "اسهال دموى":{
                    firstAid.append("-حاول اخد عينة  \n" +
                            "-ابق المريض فى وضع راحة واتصل بالمستشفى\n");
                    break;
                }
                case "قئ دموى":{
                firstAid.append("-حاول اخد عينة \n" +
                        "-ابق المريض فى وضع راحة واتصل بالمستشفى\n");

                break;
                }case"جرح انف":{
                    firstAid.append("-اجلس" +
                            "-عكس المعروف انحنى لامام لمنع الدم من شفط الدم\n" +
                            "-اغلق انفك وحاول التنفس من فمك لمدة ع الاقل 10 دقئق\n" +
                            "-ادا لم ينجح ما فوق قم باستخدم كمادة باردة\n" +
                            "-اذا لم ينجح جرب الضغط الخفيف على موضع الاصابة\n" +
                            "-اذا لم ينجح قم بوضع شاش او قطنة بها ادرينالين\n" +
                            "-والا اتصل بالمستشفى\n");

                        break;
                 }case "جرح اذن":{
                        firstAid.append("-ضع المريض على ظهره -رفع راسه-وضع راسه --,ناحية الاذن المصابة\n" +
                                "-قم بتغطيه الاذن (احرص على عدم جرح قناة الاذن)\n" +
                                "-اطلب مساعدة المستشفى\n");

                        break;
                }default: {//جرح راس
                        firstAid.append("-قم بربط الجرح (لا تربط اذا كان منطقة مكسورة او بها جسم غريب)\n" +
                                "-راقب اذا حدث اغماء او فقد انتباه\n" +
                                "-اتصل بالمستشفى\n");

                        break;
                }}
        return firstAid;
    }

    private boolean hasRiskCodition(){
        CheckBox import_part=(CheckBox)findViewById(R.id.importpart);
        CheckBox chocked=(CheckBox)findViewById(R.id.chocked);
        CheckBox artir=(CheckBox)findViewById(R.id.artir);
        if (artir.isChecked()||import_part.isChecked()||chocked.isChecked()){
            return true;
        }
        return false;
    }
}