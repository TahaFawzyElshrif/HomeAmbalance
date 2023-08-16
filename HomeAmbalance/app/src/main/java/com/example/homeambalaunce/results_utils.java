package com.example.homeambalaunce;




import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.Arrays;
import java.util.Map;

public class results_utils {//for additional useful methods
    public static void showDialog(AppCompatActivity currentActivity,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        builder.setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
    public static PyObject startPython(AppCompatActivity currentActivity,String module){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(currentActivity));
        }
        Python py=Python.getInstance();
        PyObject obj= py.getModule(module);
        return obj;
    }
    public static void goToActivity(AppCompatActivity from,Class dir) {
        if (from == null || dir == null) {
            Log.e("error","null page");
            return;
        }else {
            Intent go = new Intent(from, dir);
            from.startActivity(go);
        }
    }
    public static void  goToActivityWithData(AppCompatActivity from, Class dir,Map<String,StringBuffer> data) {
        if (from == null || dir == null) {
            Log.e("error","null page");
        }else {
            Intent go = new Intent(from, dir);

            Object[] data_keys=  data.keySet().toArray();//to access set easily
            for (int i=0;i<data_keys.length;i++){
                StringBuffer crnt_key_data=data.get(data_keys[i]);
                go.putExtra(data_keys[i].toString(),crnt_key_data.toString());
            }

            from.startActivity(go);
        }
    }
}
