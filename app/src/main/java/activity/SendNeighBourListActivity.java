package activity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import connection.Client;
import model.Neighbour;

/**
 * Created by Joshi on 15.09.2017.
 */

public class SendNeighBourListActivity extends AsyncTask<Void,Void,Void> {
    private Client client = new Client();
    private Socket socket;
    private ArrayList<Neighbour> nList;

    public SendNeighBourListActivity(Socket socket, ArrayList<Neighbour> nList){
        this.socket = socket;
        this.nList  = nList;
    }
    @Override
    protected Void doInBackground(Void... params) {
        Log.d("Vor Try ArrayNeighbour","");
        try {
            Log.d("SendArrayNeighbour",""+ nList.toString());
            client.sendListAsByteArrayNeighbour(socket,nList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
