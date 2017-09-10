package activity;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import connection.Client;
import model.Neighbour;

/**
 * Created by Joshi on 10.09.2017.
 */

public  class NeighbourTransferActivity extends AsyncTask<String, String, String> {
    private Client client;
    private Socket socket = null;
    private Neighbour neighbour = null;

    public NeighbourTransferActivity(Socket socket, Neighbour neighbour) {
        this.socket = socket;
        this.neighbour = neighbour;
    }

    protected String doInBackground(String... args) {

        try {

            client.sendNeighbourAsByteArray(socket, neighbour);

            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}