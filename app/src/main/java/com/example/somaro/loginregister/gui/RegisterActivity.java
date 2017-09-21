package com.example.somaro.loginregister.gui;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.somaro.loginregister.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.vtUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final Button bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success){
                                //start routing/ Abfrage ob das dies der erste Knoten ist der sich anmeldet
                                // Nach dem erfolgreichen Registrieren öffnet sich die Login Seite

                                if(chickEmptyDatabase())
                                {
                                    Log.d("TEST", "DATENBANK IST LEER");
                                    //Die IP vom ersten Knoten muss zum Bootstrap Server
                                    //Zone auf Eckpunkte setzen
                                    //Knoten serialisieren
                                }else
                                {
                                    //IP vom Bootstrap Server holen
                                    //Join-Request an diese IP senden
                                }

                                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequeast registerRequeast = new RegisterRequeast(name, username,password,age , responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequeast);
            }
        });
    }

    protected boolean chickEmptyDatabase() {

        String jsonString = "";
        String line;
        HttpURLConnection httpURLConnection = null;
        try {
            URL u = new URL("https://agelong-rations.000webhostapp.com/abfrage.php");
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            jsonString = stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        if (jsonString.equals("True")) {
            return true;
        } else{
            return false;
        }

    }
}
