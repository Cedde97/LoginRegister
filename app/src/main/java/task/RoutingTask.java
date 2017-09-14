package task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

import connection.Client;
import connection.RoutHelper;
import connection.Serialization;
import model.Node;
import model.PeerMemo;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;

/**
 * Created by Joshi on 07.09.2017.
 */
public class RoutingTask extends AsyncTask<String, String , String> {
    private final static int PORTNR = 9797;

    private Serialization serialization = new Serialization();
    private Socket socket;
    private Client client;

    //wie mache ich das mit routing und receiveRoutingRequest also wie rufe ich sie jeweils auf? da sie sich gegenseitig aufrufen
    @Override
    protected String doInBackground(String... params) {
        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int id   = Integer.parseInt(params[3]);

        RoutHelper rh = new RoutHelper(ip,x,y,id);
        Node nodeNew = routingCheckZone(rh);
        double[] distance = new double[3];

        try {
            if(serialization.getSerialzedNode().getNeighbourList() != null) {
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
            client.sendRoutHelperAsByteArray(socket,rh);
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




    /**
     * als wenn node.updateNeighbourAndPeer(newNode) so aufgerufen wird bekommt newNode die Listen von node
     * Methode um dem übergebenen Knoten seine eigene Neighbour und Peerlist zu übertragen
     * @param newNode dieser Knoten bekommt die Neighbour und Peerlist
     */
    public void updateNeighbourAndPeer(Node newNode)  {
        try {
            if(serialization.getSerialzedNode().getNeighbourList() != null){
                //setzte die NeighbourList des neuen Knoten auf seine eigene
                newNode.getNeighbourList().addAll(serialization.getSerialzedNode().getNeighbourList());
                if(serialization.getSerialzedNode().getPeerMemoList() != null){
                    //setzte die PeerList des neuen Knoten auf seinsy;
                    PeerMemo pm = new PeerMemo((int)serialization.getSerialzedNode().getUid(),serialization.getSerialzedNode().getIP());
                    newNode.getPeerMemoList().add(pm);
                    newNode.setCountPeers(serialization.getSerialzedNode().getPeerMemoList().size());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private Node routingCheckZone(RoutHelper rh) {
        try {
            if(serialization.getSerialzedNode().getMyZone().checkIfInMyZone(rh.getX(),rh.getY())){
                //hier noch statt 3 getUID von OnlineDB
                Node newNode = new Node(rh.getID(), rh.getX(), rh.getY(), rh.getIP(), serialization.getSerialzedNode().getCountPeers(), serialization.getSerialzedNode().getMyZone());
                serialization.getSerialzedNode().increaseCountPeers();
                if(serialization.getSerialzedNode().checkIfMaxPeersCount()){


                    //splitt
                }else{
                    //testen ob geht
                    updateNeighbourAndPeer(newNode);
                }
                return newNode;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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



}
