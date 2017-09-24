package connection;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;


import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.ForeignData;
import model.Neighbour;
import model.Node;
import model.PeerMemo;

import model.Zone;
import source.DatabaseManager;
import source.DateiMemoDbHelper;
import source.DateiMemoDbSource;
import source.ForeignDataDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;
import task.RoutingTask;


/**
 * Created by Cedric on 06.09.2017.
 * last change by Joshua Zabel
 */

public class ServerThreadActivity extends Activity {

    private static final int PORTNR = 9797;
  
    private static final int IMAGE_TRANSFER						 = 1;
    private static final int NODE_TRANSFER 						 = 2;
    private static final int ROUTING 							 = 3;
    private static final int NEIGHBOUR_TRANSFER					 = 4;
    private static final int PEERMEMO_TRANSFER					 = 5;
    private static final int FOREIGNDATA_TRANSFER 				 = 6;   
    private static final int PEERMEMO_LIST 						 = 7;
    private static final int NEIGHBOUR_LIST 					 = 8;
    private static final int ROUT_HELPER_PIC                     = 9;
    
    private Socket socket = null;
    private Server server = new Server();

    private static Context appContext;
    private static DateiMemoDbHelper dbHelper;

    private NeighborDbSource nDB = new NeighborDbSource();
    private PeerDbSource pDB = new PeerDbSource();
    private ForeignDataDbSource fDB = new ForeignDataDbSource();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ServerThread().execute();

        //===============================================
        appContext = this.getApplicationContext();
        dbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dbHelper);
        //===============================================

    }


    class ServerThread extends AsyncTask<Void, Void, Void> {


        protected Void doInBackground(Void... args) {

            Serialization serialization = new Serialization();
            ServerSocket ss = null;


            try {
                while(true) {
                    Log.d("Server is started", "In ServerThreadActivity");
                    ss = new ServerSocket(PORTNR);
                    Log.d("Waiting for request", "ServerThreadActivity");
                    Socket s = ss.accept();

                    Log.d("Client connected", "ServerThreadActivity");

                    byte[] buffer = server.receiveByteArray(ss, s);

                    Log.d("BufferBytes: " + buffer.length, "");

                    Log.d("Received ByteArray", "");

                    int methodName = serialization.getByteHeader(buffer);

                    Log.d("Header: ", "" + methodName);
                    switch (methodName) {

                        case IMAGE_TRANSFER: {
                            Log.d("File Transfer Request", "");

                            String pathDestination = "C://Users/Cedric/Pictures/test/placeholderNew1.jpg";

                            File newFile = new File(pathDestination);

                            byte[] bufferBody = serialization.getByteData(buffer);

                            server.saveFileFromByteArray(bufferBody, newFile);

                            Log.d("Converted ByteArray", "");

                            Log.d("Saved File to: ", "pathDestination");

                            break;
                        }

                        case NODE_TRANSFER: {
                            Log.d("Node Transfer Request", "");

                            Node node = server.getNode(buffer);

                            Log.d("Mein neuer Node: ", node.toString());

                            break;
                        }

                        case ROUTING: {

                            Log.d("Routing: ", "");

                            RoutHelper rh = server.getRoutHelper(buffer);
                            Log.d("RoutHelper: ", " " + rh.toString());
                            startRoutingTask(rh);

                            break;

                        }

                        case FOREIGNDATA_TRANSFER: {

                            Log.d("ForeignTransfer", "");
                            ForeignData fd = server.getForeignData(buffer);
                            fDB.createForeignData(fd);
                            Log.d("ForeignTransfer", "after Create");
                            break;

                        }

                        case PEERMEMO_LIST: {
                            Log.d("List: ", "");

                            ArrayList<PeerMemo> list = server.getListPeer(buffer);
                            PeerMemo p = null, p1 = null, p2 = null;
                            int i;
                            Log.d("PeerList filled", " " + list.toString());
                            PeerMemo[] array = new PeerMemo[2];
                            array[0] = p;
                            array[1] = p1;
                            array[2] = p2;
                            for (i = 0; i <= list.size(); i++) {
                                array[i] = list.get(i);
                            }

                            startUpdatePeers(p, p1, p2);
                            Log.d("List: ", list.toString());
                            Log.d("PEEEEEEEEEEEERS", pDB.getAllPeer().toString());
                            break;
                        }

                        case NEIGHBOUR_LIST: {
                            Log.d("NeighbourList:", "");
                            int i = 0;
                            ArrayList<Neighbour> list = server.getListNeighbour(buffer);

                            Log.d("List:", list.toString());

                            Neighbour n = null, n1 = null, n2 = null, n3 = null;
                            Neighbour[] array = new Neighbour[4];
                            array[0] = n;
                            array[1] = n1;
                            array[2] = n2;
                            array[3] = n3;
                            for (i = 0; i <= list.size() - 1; i++) {
                                array[i] = list.get(i);
                                array[i].setUid(ownDb.getUid());
                            }
                            startUpdateNeighbours(array[0], array[1], array[2], array[3]);
                            Log.d("NeighBOUUUUUUUR", "" + nDB.getAllNeighborMemo().toString());
                            break;
                        }
                        case ROUT_HELPER_PIC: {
                        	
                        	// tun Sie was sie nicht lassen können
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ss != null)
                        ss.close();
                    Log.d("ServerSocket closed", "");


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
    }


    /**
     * Diese Methode speichert die übergebenen PeerMemos in der Datenbank
     * @param p Erster Peer
     * @param p1 Zweiter Peer
     * @param p2 Dritter Peer
     * @author Joshua Zabel
     */
    private void startUpdatePeers(PeerMemo p, PeerMemo p1, PeerMemo p2){
        new AsyncTask<PeerMemo,Void,Void>(){

            @Override
            protected Void doInBackground(PeerMemo... params) {
                int i;
                for(i=0; i<params.length; i++){
                    if(params[i] != null){
                        pDB.createPeerMemo(params[i]);
                    }
                }
                return null;
            }
            // vieleicht noch Pram zu execute
        }.execute(p,p1,p2);
    }


    /**
     * Diese Methode speichert die übergebenen Neighbours in der Datenbank
     * @param n Erster Neighbour
     * @param n1 Zweiter Neighbour
     * @param n2 Dritter Neighbour
     * @param n3 Vierter Neighbour
     * @author Joshua Zabel
     */
    private void startUpdateNeighbours(Neighbour n, Neighbour n1, Neighbour n2, Neighbour n3) {
        new AsyncTask<Neighbour, Void, Void>() {
            @Override
            protected Void doInBackground(Neighbour... params) {
                int i;
                for(i=0; i<params.length; i++){
                    if(params[i] != null){
                        nDB.createNeighborMemo(params[i]);
                    }
                }
                return null;
            }
        }.execute(n,n1,n2,n3);
    }

    private void startRoutingTask(RoutHelper rh){
        new RoutingTask().execute(rh);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

