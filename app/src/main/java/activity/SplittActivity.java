package activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.somaro.loginregister.R;

import org.json.JSONException;

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.Node;
import model.Zone;
import task.HashXTask;
import task.SplitTask;

/**
 * Created by Alexander on 15.09.2017.
 */

public class SplittActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Corner topRight = new Corner(1.0,1.0);
            Corner topLeft = new Corner(0.0,1.0);
            Corner bottomRight = new Corner(1.0,0.0);
            Corner bottomLeft = new Corner(0.0,0.0);

            Zone zone = new Zone(topLeft,topRight,bottomLeft,bottomRight);

            Node node1 = new Node(1,0.1,0.5,"1.1.1.1",3,zone);
            Node node2 = new Node(2,0.2,0.6,"1.1.1.2",3,zone);
            Node node3 = new Node(3,0.3,0.7,"1.1.1.3",3,zone);
            Node node4 = new Node(4,0.4,0.8,"1.1.1.4",3,zone);

            startSplitt(node1,node2,node3,node4);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void startSplitt(Node node1, Node node2, Node node3 , Node node4) throws JSONException {
        new SplitTask(new SplitTask.AsyncResponse(){
            @Override
            public void processFinish(double d){
                Log.d("Split in processFinish ", "d"+d);
            }
        }).execute(node1,node2,node3,node4);
    }
}
