package task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import connection.Client;
import connection.RoutHelper;
import connection.Serialization;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Neighbour;
import model.Node;
import model.PeerMemo;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;
import util.DBUtil;

/**
 * Created by Joshi on 07.09.2017.
 */
public class RoutingTask extends AsyncTask<String, String , String> {
    private final static int PORTNR = 9797;

    @Override
    protected String doInBackground(String... params) {
        return null;
    }

/*

    private Socket socket;
    private Client client;
    private DBUtil dbu = new DBUtil();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
    private NeighborDbSource nDB = new NeighborDbSource();

    //wie mache ich das mit routing und receiveRoutingRequest also wie rufe ich sie jeweils auf? da sie sich gegenseitig aufrufen
    @Override
    protected String doInBackground(String... params) {
        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int id = Integer.parseInt(params[3]);

        RoutHelper rh = new RoutHelper(ip, x, y, id);
        Node nodeNew = routingCheckZone(rh);
        double[] distance = new double[3];

        try {

            if(.getNeighbourList() != null) {
                for (int i = 0; i < serialization.getSerialzedNode().getNeighbourList().size(); i++) {
                    distance[i] = computeDistance(rh.getX(), rh.getY(),serialization.getSerialzedNode().getNeighbourList().get(i).getPunktX(),
                            serialization.getSerialzedNode().getNeighbourList().get(i).getPunktY());
                }
                int index = compareValues(distance);

                socket = new Socket(serialization.getSerialzedNode().getNeighbourList().get(index).getUIP(), PORTNR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //berechne die Distanz von den Neighbourn zu den x,y-Werten und liefere den Index an welcher Stelle der Neighbour steht der am nächsten an den x,y-Werten ist


            try {
                client.sendRoutHelperAsByteArray(socket, rh);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //return nodeNew;

            return null;
        }


    private double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
    }




    */
/**
     * als wenn node.updateNeighbourAndPeer(newNode) so aufgerufen wird bekommt newNode die Listen von node
     * Methode um dem übergebenen Knoten seine eigene Neighbour und Peerlist zu übertragen
     * @param newNode dieser Knoten bekommt die Neighbour und Peerlist
     *//*

    private void updateNeighbourAndPeer(Node newNode)  {
        try {
                //setzte die NeighbourList des neuen Knoten auf seine eigene
                //newNode.getNeighbourList().addAll(serialization.getSerialzedNode().getNeighbourList());
            // EachNeighbourMemo anpassen sodass man keine id übergeben muss
            List<Neighbour> neighbourList = nDB.getEachNeigbourMemo(1);
            // noch die neighbourList dem newNode geben

                    //setzte die PeerList des neuen Knoten auf seinsy;
            PeerMemo pm = new PeerMemo((int) ownDb.getUid(), ownDb.getIp(1));



            newNode.getPeerMemoList().add(pm);
            newNode.setCountPeers(serialization.getSerialzedNode().getPeerMemoList().size());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private Node routingCheckZone(RoutHelper rh) {
        try {
            if(dbu.initOwnZone().checkIfInMyZone(rh.getX(),rh.getY())){
                //hier noch statt 3 getUID von OnlineDB
                Node newNode = new Node(rh.getID(), rh.getX(), rh.getY(), rh.getIP(), ownDb.getCountPeers(), dbu.initOwnZone());
                ownDb.updateCountPeers();
                if(ownDb.getAllDateiMemos().checkIfMaxPeersCount()){


                    //splitt
                }else{
                    //hier ein remote aufruf von updateNeighbourAndPeerForeign an die rh.getIp() senden
                    //testen ob geht

                }
                return newNode;
            }
        } catch (YMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        }
        return null;
    }




    private int compareValues(double [] distances){
        int index = 0;
        double temp =  distances[0];
        for(int i= 1 ; i< distances.length; i++){
            if(temp > distances[i]){

                temp = distances[i];
                index = i;
            }
        }
        return index;
    }
*/



}
