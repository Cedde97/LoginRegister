package activity;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import connection.Client;

/**
 * Created by Joshi on 10.09.2017.
 */

public   class FileTransferActivity extends AsyncTask<String, String, String> {
    private Client client;
    private Socket socket = null;
    private File file = null;

    public FileTransferActivity(Socket socket, File file) {
        this.socket = socket;
        this.file = file;
    }

    protected String doInBackground(String... args) {

        try {

            client.sendImageAsByteArray(socket, file);

            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}