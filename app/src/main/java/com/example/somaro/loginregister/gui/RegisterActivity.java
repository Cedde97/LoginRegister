package com.example.somaro.loginregister.gui;

import android.content.Context;
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

import bootstrap.AllIPsActivity;
import bootstrap.InsertOwnIPActivity;
import connection.Client;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.Node;
import model.Zone;
import source.DatabaseManager;
import source.DateiMemoDbHelper;
import source.DateiMemoDbSource;
import task.CheckEmptyOnlineDBTask;
import task.RequestJoinTask;

public class RegisterActivity extends AppCompatActivity {
    private int id;
    private Zone ownZone;
    private Corner topRight;
    private Corner topLeft;
    private Corner bottomRight;
    private Corner bottomLeft;
    private boolean isEmpty ;
    private static Context appContext;
    private static DateiMemoDbHelper dbHelper;
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isEmpty = startCheckEmptyOnlineDBTask();
        setContentView(R.layout.activity_register);

        appContext = this.getApplicationContext();
        dbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dbHelper);


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

                                Log.d("nachIni: ", ""+isEmpty);
                                //isEmpty wird von startCheckEmptyOnlineDBTask initialisiert
                                if(isEmpty){
                                    Log.d("is empty","hat gefunkt");
                                    // das dieses Gerät das Erste ist, bekommt es die Grenzwerte von CAN als Zone/Corner
                                    topRight    = new Corner(1.0,1.0);
                                    topLeft     = new Corner(0.0,1.0);
                                    bottomRight = new Corner(1.0,0.0);
                                    bottomLeft  = new Corner(0.0,0.0);
                                    ownZone     = new Zone(topLeft,topRight,bottomLeft,bottomRight);
                                    //bekommt die IP des eignenen Gerätes
                                    String ip = Client.getOwnIpAddress();
                                    //fügt die eigene IP zu dem Bootstrap-Server hinzu
                                    startInsertOwnIP();

                                    //hiermit holt man die id von dem DB-Server
                                    Intent intent = getIntent();
                                    String name = intent.getStringExtra("name");
                                    id = intent.getIntExtra("id",0);
                                    Node ownNode = new Node(id,Node.hashX(ip),Node.hashY(ip),ip,0,ownZone);
                                    ownDb.createDateiMemo(ownNode);
                                }else {
                                    Log.d("in Else","");
                                    startRequestJoinTask();
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
                        } catch (JSONException | YMustBeLargerThanZeroException | XMustBeLargerThanZeroException e) {
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

    private void startInsertOwnIP() throws JSONException {
        new InsertOwnIPActivity().execute();
    }

    private void startRequestJoinTask(){
        new RequestJoinTask().execute();
    }

    private boolean startCheckEmptyOnlineDBTask(){

        new CheckEmptyOnlineDBTask(new CheckEmptyOnlineDBTask.AsyncResponse(){
            @Override
            public void processFinish(boolean result) {
                Log.d("result: ", ""+result);
                isEmpty = result;
            }
        }).execute();
        return isEmpty;
    }

}

