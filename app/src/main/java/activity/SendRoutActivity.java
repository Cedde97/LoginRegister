package activity;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

import connection.Client;
import connection.RoutHelper;

/**
 * Created by Joshi on 10.09.2017.
 */

public class SendRoutActivity extends AsyncTask<Void, Void, Void> {
    private Client client = new Client();
    private Socket socket;
    private RoutHelper rh;

    public SendRoutActivity(Socket socket, RoutHelper rh){
        this.socket = socket;
        this.rh = rh;
    }

    protected Void doInBackground(Void... arg0) {


        Log.d("Vor Try", "Block");

        try {
            Log.d("Send Routhelper ", "");
            client.sendRoutHelperAsByteArray(socket, rh);
        } catch (IOException e) {
            Log.d("client.sendNode", e.toString());
        } catch (Exception e) {
            Log.d("RoutHelperExc: ", e.toString());
        }
        return null;
    }
    //ServerSeite hash Function Aufruf von getIP
}