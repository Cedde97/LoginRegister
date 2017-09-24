package task;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.Socket;

import bootstrap.AllIPsActivity;
import connection.Client;
import connection.RoutHelper;
import model.Corner;
import model.Node;
import model.Zone;
import source.DateiMemoDbSource;

/**
 * Created by Joshua Zabel on 07.09.2017.
 */
public class FirstJoinTask extends AsyncTask<RoutHelper, Void, Void> {
    private final static int PORTNR = 9797;

    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
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
        Corner topRight    = new Corner(1.0,1.0);
        Corner topLeft     = new Corner(0.0,1.0);
        Corner bottomRight = new Corner(1.0,0.0);
        Corner bottomLeft  = new Corner(0.0,0.0);
        Zone ownZone       = new Zone(topLeft,topRight,bottomLeft,bottomRight);
        Node ownNode = new Node(rh.getID(),Node.hashX(rh.getIP()),Node.hashY(rh.getIP()),rh.getIP(),0,ownZone);
        ownDb.createDateiMemo(ownNode);
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
