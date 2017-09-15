package task;

import android.os.AsyncTask;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import connection.Client;
import connection.RoutHelper;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Node;
import model.PeerMemo;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;
import util.DBUtil;

/**
 * Created by Joshi on 07.09.2017.
 */
public class RoutingTask extends AsyncTask<String, Void, Void> {
    private final static int PORTNR = 9797;


    private Socket socket;
    private Client client = new Client();
    private DBUtil dbu = new DBUtil();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
    private NeighborDbSource nDB = new NeighborDbSource();
    private PeerDbSource pDB = new PeerDbSource();


    //wie mache ich das mit routing und receiveRoutingRequest also wie rufe ich sie jeweils auf? da sie sich gegenseitig aufrufen
    @Override
    protected Void doInBackground(String... params) {
        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int id = Integer.parseInt(params[3]);

        RoutHelper rh = new RoutHelper(ip, x, y, id);
        if (!routingCheckZone(rh)) {
            double[] distance = new double[3];

            try {


                for (int i = 0; i < nDB.getCount(); i++) {
                    distance[i] = computeDistance(rh.getX(), rh.getY(), nDB.getPunktXNeighbor(i),
                            nDB.getPunktYNeighbor(i));
                }
                int index = compareValues(distance);

                socket = new Socket(nDB.getUip(index), PORTNR);

            } catch (IOException e) {
                e.printStackTrace();
            }
            //berechne die Distanz von den Neighbourn zu den x,y-Werten und liefere den Index an welcher Stelle der Neighbour steht der am nächsten an den x,y-Werten ist


            try {
                client.sendRoutHelperAsByteArray(socket, rh);
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


    private boolean routingCheckZone(RoutHelper rh) {
        try {
            if (dbu.initOwnZone(ownDb).checkIfInMyZone(rh.getX(), rh.getY())) {
                //hier noch statt 3 getUID von OnlineDB
                Node newNode = new Node(rh.getID(), rh.getX(), rh.getY(), rh.getIP(), ownDb.getCountPeers() + 1, dbu.initOwnZone(ownDb));
                ownDb.updateCountPeers();
                if (ownDb.getAllDateiMemos().checkIfMaxPeersCount()) {


                    //splitt
                    return true;
                } else {
                    //noch testen
                    Socket socket = new Socket(rh.getIP(), PORTNR);
                    client.sendNodeAsByteArray(socket, newNode);
                    client.sendListAsByteArray(socket, (ArrayList) pDB.getAllPeer());
                    client.sendListAsByteArrayNeighbour(socket, (ArrayList) nDB.getAllNeighborMemo());

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
