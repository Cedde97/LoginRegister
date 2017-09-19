package task;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import connection.Client;
import connection.RoutHelper;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.ForeignData;
import source.DateiMemoDbSource;
import source.ForeignDataDbSource;
import source.NeighborDbSource;
import util.DBUtil;

/**
 * Created by Joshi on 19.09.2017.
 */

public class recoverPicturesTask extends AsyncTask<String,Void,Void>{
    private final static int PORTNR = 9797;


    private Socket socket;
    private Client client = new Client();
    private DBUtil dbu = new DBUtil();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
    private NeighborDbSource nDB = new NeighborDbSource();
    private ForeignDataDbSource fDb = new ForeignDataDbSource();


    @Override
    protected Void doInBackground(String... params) {
        //Ip des ursprünglichen Bildbesitzers
        String ip = params[0];
        //x,y-Werte von einem Bild
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int uId = Integer.parseInt(params[3]);

        RoutHelper rh = new RoutHelper(ip,x,y,uId);

        if(!routingCheckZone(rh)){
            try {
                socket = new Socket(nDB.getUip(determineRoutingDestination(rh)), PORTNR);
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
            //testen ob initOwnZone geht
            if (dbu.initOwnZone(ownDb).checkIfInMyZone(rh.getX(), rh.getY())) {
                List<ForeignData> fdArray;
                fdArray = fDb.getAllForeignData();
                List<ForeignData> myPics = new ArrayList<ForeignData>();
                for(int i = 0; i <= fdArray.size(); i++){
                    if(fdArray.get(i).getUid() == rh.getID()){
                        myPics.add(fdArray.get(i));
                    }
                }
                Socket socket = new Socket(rh.getIP(), PORTNR);
                // TODO: 19.09.2017 Jetzt noch alle Bilder(die in myPics sind) von Gerät holen und an socket senden  


                return true;

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
