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
 * Created by Joshi on 07.09.2017.
 */
public class RequestJoinTask extends AsyncTask<String, Void, Void> {
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
    protected Void doInBackground(String... params) {
        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int id = Integer.parseInt(params[3]);
        RoutHelper rh = new RoutHelper(ip, x, y, id);
        Log.d("in RequestJoinTask","AAAAAAAAA");
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

    private int compareValues(double[] distances) {
        int index = 0;
        double temp = distances[0];
        for (int i = 1; i < distances.length; i++) {
            if (temp > distances[i]) {

                temp = distances[i];
                index = i;
            }
        }
        return index;
    }


}
