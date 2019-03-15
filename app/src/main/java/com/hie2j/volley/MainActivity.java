package com.hie2j.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txt_Result;
    private EditText edit_Phone;

    private static final String TAG = "MainActivity";

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Result = findViewById(R.id.txt_result);
        edit_Phone = findViewById(R.id.edit_phone);
        requestQueue = Volley.newRequestQueue(MainActivity.this);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在主线程直接运行
                useStringRequestGET(edit_Phone.getText().toString());
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useStringRequestPOST(edit_Phone.getText().toString());
            }
        });
    }

    private void useStringRequestPOST(final String phone) {
        String baseUrl = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG,s);
                txt_Result.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> map = new HashMap<>();
                map.put("mobileCode",phone);
                map.put("userID","");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void useStringRequestGET(String phone) {
        String baseUrl = "http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo";
        String phoneUrl = baseUrl.concat("?mobileCode=").concat(phone).concat("&userID=");
        StringRequest stringRequest = new StringRequest(phoneUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.e(TAG,s);
                txt_Result.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG,volleyError.toString());
            }
        });
        requestQueue.add(stringRequest);
    }
}
