package task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

import connection.Client;
import connection.RoutHelper;
import model.PeerMemo;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;

/**
 * Created by Joshi on 07.09.2017.
 */

public class RoutingTask extends AsyncTask<String, String , String> {
    private final static int PORTNR = 9797;

    //bekommt man so die DB
    private PeerDbSource peerDB;
    private NeighborDbSource nDB;
    private Socket socket;
    private Client client;

    //wie mache ich das mit routing und receiveRoutingRequest also wie rufe ich sie jeweils auf? da sie sich gegenseitig aufrufen
    @Override
    protected String doInBackground(String... params) {
        double neighbourX, neighbourY;
        double [] distances = new double[4];

        String ip = params[0];
        double x = Double.parseDouble(params[1]);
        double y = Double.parseDouble(params[2]);
        int id   = Integer.parseInt(params[3]);

        //bricht routing ab falls richtige zone gefunden wurde
        routingCheckZone(ip,x,y,id);


        //bei 1 anfangen?
        for(int i=0; i<=distances.length ; i++){

            //Die x und y Werte des Nachbarn von der DB holen
            neighbourX = nDB.getPunktXNeighbor(i);
            neighbourY = nDB.getPunktYNeighbor(i);
            // Nun diese Distanzen berechnen und die am nächsten an dem Punkt zu dem gerouted werden soll.
            distances[i] = computeDistance(x,y,neighbourX,neighbourY);

        }
        int index = compareValues(distances);
        String connectIP = nDB.getUip(index);

        //getNeighbour(index).getIP(); in dem socket eintragen
        try {
            socket = new Socket(connectIP,PORTNR);
        } catch (IOException e) {
            e.printStackTrace();
        }


        RoutHelper rh = new RoutHelper(ip,x,y,id);
        //routing request
        try {
            client.sendRoutHelperAsByteArray(socket,rh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //// TODO: 07.09.2017 sende ein receiveRoutingRequest  an ip
        //// TODO: 14.08.2017 Verbindungsaufbau zu dem Neighbour der an Stelle == Index steht und IP und x,y-Werte übertragen so das dieser weiter routen kann, bzw recreive routing request bei ihm aufrufen
        return null;
    }


    private double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
    }

    /**
     * Hilfsmethode zum routing um zu überprüfen ob der zu routende Knoten in der momentanen Zone liegt
     *
     */

    private void routingCheckZone(String ip, double x ,double y, int id){
        //getMyZone().checkIfInMyZone(x,y)
        if(true){
            //was für peerId mitte?
            PeerMemo pm = new PeerMemo(id,0,ip);

            //neuen Knoten seine aktuelle PeersList geben (mit sichselbst)
            //neuen Knoten eintragen in eigene peer list

            //// TODO: 14.08.2017  Reply to Request-Method(muss setPeers(mit sich selbst) und setNeighbours mitsenden)
            //// TODO: 14.08.2017 Muss aktuelle Peers über den neuen Knoten Informieren, sodass diese ihre Peerliste updaten. Nun update deine eigene Peerlist
            //// TODO: 08.09.2017 abbrechen
            if(checkIfMaxPeersCount()){

                //// TODO: 15.08.2017 informiere deine Peers das sie nun Splitten müssen// methode die einen Splitt aufruft
                //// TODO: 14.08.2017 SPLITT
                // TODO: 08.09.2017 abbrechen
            }

        }
    }


    //wo countPeers, es gibt keine peersCount methode für db
    private boolean checkIfMaxPeersCount(){
        if (peerDB.getPeersCount() == 3){
            return true;
        }else{
            return false;
        }
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
