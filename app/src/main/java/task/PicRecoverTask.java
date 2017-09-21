package task;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;

import connection.Client;
import connection.RoutHelper;
import model.ForeignData;
import source.DateiMemoDbSource;
import source.ForeignDataDbSource;
import source.PeerDbSource;

/**
 * Created by Alexander on 18.09.2017.
 */

public class PicRecoverTask extends AsyncTask<String,Void,Void> {

    private final static int PORTNR = 9797;
    private Socket socket;
    private Client client = new Client();
    private PeerDbSource peerDB = new PeerDbSource();
    private ForeignDataDbSource foreignDataDb = new ForeignDataDbSource();

    public interface AsyncResponse {
        void processFinish(double result);
    }
    @Override
    protected Void doInBackground(String... params) {
        int uId  = Integer.parseInt(params[0]);

        String ip = peerDB.getPeerIp(uId);

        double xFoto = foreignDataDb.getPunktXForeign(uId);
        double yFoto = foreignDataDb.getPunktYForeign(uId);
        int fotoId = foreignDataDb.getFotoId(uId);

        ForeignData fd = new ForeignData(ip,xFoto,yFoto,fotoId,uId);

        try {
            socket = new Socket(ip,PORTNR);
            client.sendForeignDataAsByteArray(socket,fd);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}
