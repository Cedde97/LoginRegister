package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


import com.example.somaro.loginregister.R;
import bootstrap.*;

import org.json.JSONException;

import task.HashXTask;
import task.HashYTask;
import task.RoutingTask;

public class NodeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_performed);
        try{
            startHashX();
            startHashY();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void startHashX() throws JSONException {
        new HashXTask(new HashXTask.AsyncResponse(){
            @Override
            public void processFinish(double d){
                Log.d("HashX in processFinish ", "d"+d);
            }
        }).execute("123.142.0.1");
    }

    private void startHashY() throws JSONException {
        new HashYTask(new HashYTask.AsyncResponse(){
            @Override
            public void processFinish(double d){
                Log.d("HashY in processFinish ", "d"+d);
            }
        }).execute("123.142.0.1");
    }



}




/**
 * Created by Joshi on 07.09.2017.
 */