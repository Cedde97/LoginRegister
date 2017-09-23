package task;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import connection.Client;
import connection.RoutHelper;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.ForeignData;
import model.Node;
import source.DateiMemoDbHelper;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import util.DBUtil;

/**
 * Created by Joshi on 17.09.2017.
 */

public class PicRoutingTask extends AsyncTask<String, Void, Void>{
    private static Context appContext;
    private static DateiMemoDbHelper dbHelper;
    private final static int PORTNR = 9797;
    private Socket socket;
    private Client client = new Client();
    private DBUtil dbu = new DBUtil();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
    private NeighborDbSource nDB = new NeighborDbSource();

    @Override
    protected Void doInBackground(String... params) {
        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int fotoId= Integer.parseInt(params[3]);
        int uId  = Integer.parseInt(params[4]);

        ForeignData fd = new ForeignData(ip,x,y,fotoId,uId);
        RoutHelper rh = new RoutHelper(ip,x,y,uId);

        //routingCheckZone leitet routing weiter falls nicht in der Zone
        if(!routingCheckZone(rh)){
            try{
                socket = new Socket(nDB.getUip(determineRoutingDestination(rh)),PORTNR);
                client.sendForeignDataAsByteArray(socket,fd);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return null;
    }


    private double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
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

    private int determineRoutingDestination(RoutHelper rh){
        double[] distance = new double[3];
        for (int i = 0; i < nDB.getCount(); i++) {
            distance[i] = computeDistance(rh.getX(), rh.getY(), nDB.getPunktXNeighbor(i),
                    nDB.getPunktYNeighbor(i));
        }
        int index = compareValues(distance);

        return index;
    }

    private boolean routingCheckZone(RoutHelper rh) {
        try {
            if (dbu.initOwnZone(ownDb).checkIfInMyZone(rh.getX(), rh.getY())) {
                //hier noch statt 3 getUID von OnlineDB
                Node newNode = new Node(rh.getID(), rh.getX(), rh.getY(), rh.getIP(), ownDb.getCountPeers() + 1, dbu.initOwnZone(ownDb));
                ownDb.updateCountPeers();
                if (ownDb.getAllDateiMemos().checkIfMaxPeersCount()) {

                    //noch testen
                    Socket socket = new Socket(rh.getIP(), PORTNR);


                    //hier ein remote aufruf von updateNeighbourAndPeerForeign an die rh.getIp() senden
                    //testen ob geht
                    return true;
                }
            }
        } catch (YMustBeLargerThanZeroException | XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
