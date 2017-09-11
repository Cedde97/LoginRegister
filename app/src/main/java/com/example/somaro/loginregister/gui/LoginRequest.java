package com.example.somaro.loginregister.gui;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Somaro on 04.06.2017.
 */

public class LoginRequest extends StringRequest{

    // für localhost muss Nur die aktuelle IP einsetzen (ipconfig)
    //private static final String LOGIN_REQUEST_URL = "http://192.168.1.162/Login.php";
    private static final String LOGIN_REQUEST_URL = "http://agelong-rations.000webhostapp.com/Login.php";
    private static final String GET_USER_ID_URL = "http://agelong-rations.000webhostapp.com/abfrage.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

    }



    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
