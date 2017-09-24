package task;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.Socket;

import bootstrap.AllIPsActivity;
import connection.Client;
import connection.RoutHelper;

/**
 * Created by Joshua Zabel on 07.09.2017.
 */
public class RequestJoinTask extends AsyncTask<RoutHelper, Void, Void> {
    private final static int PORTNR = 9797;


    private Socket socket;
    private Client client = new Client();
    private String bootIps[];

    @Override
    protected void onPreExecute() {
        try {
            determineRoutingDestination();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(RoutHelper... params) {
        RoutHelper rh =  params[0];
        Log.d("in RequestJoinTask","");
        for(int i = 0; i< bootIps.length; i++){
            try {
                socket = new Socket(bootIps[i], PORTNR);
                client.sendRoutHelperAsByteArray(socket,rh);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void determineRoutingDestination() throws JSONException {

        new AllIPsActivity( new AllIPsActivity.AsyncResponse(){

            @Override
            public void processFinish(String[] result) {
                bootIps = result;
            }
        }).execute();
    }


}
