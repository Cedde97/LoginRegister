package connection;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.ForeignData;
import model.Neighbour;
import model.Node;
import model.PeerMemo;
import model.Zone;
import source.NeighborDbSource;
import source.PeerDbSource;
import task.HashXTask;
import task.HashYTask;
import task.RoutingTask;
import task.UpdateNeighbourDbTask;

/**
 * Created by Cedric on 06.09.2017.
 * last change by Joshua Zabel
 */

public class ServerThreadActivity extends Activity {
    private static final int PORTNR = 9797;
    private static final int FILETRANSFER = 1;
    private static final int NODETRANSFER = 2;
    private static final int NEIGHTRANSFER = 4;
    private static final int PEERTRANSFER = 5;
    private static final int FOREIGNTRANS = 6;
    private static final int ROUTING = 7;
    private static final int PEERLIST = 9;
    private static final int NEIGHBOURLIST = 11;

    private Socket socket = null;
    private Server server = new Server();
    private NeighborDbSource nDB = new NeighborDbSource();
    private PeerDbSource pDB = new PeerDbSource();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ServerThread().execute();
    }


    class ServerThread extends AsyncTask<String, String, String> {


        protected String doInBackground(String... args) {

            Serialization serialization = new Serialization();
            ServerSocket ss = null;
            Node node = null;
            RoutHelper routHelper = null;
            Neighbour neighbour = null;
            PeerMemo peerMemo = null;
            ForeignData foreignData = null;

            try {
                Log.d("Server is started", "In ServerThreadActivity");
                ss = new ServerSocket(PORTNR);

                Log.d("Waiting for request", "ServerThreadActivity");
                Socket s = ss.accept();

                Log.d("Client connected", "ServerThreadActivity");

                byte[] buffer = server.receiveByteArray(ss, s);

                Log.d("BufferBytes: " + buffer.length, "");

                Log.d("Received ByteArray", "");

                int methodName = serialization.getByteHeader(buffer);

                System.out.println(Integer.toString(methodName));


                switch (methodName) {

                    case FILETRANSFER: {
                        Log.d("File Transfer Request", "");

                        String pathDestination = "C://Users/Cedric/Pictures/test/placeholderNew1.jpg";

                        File newFile = new File(pathDestination);

                        byte[] bufferBody = serialization.getByteData(buffer);

                        server.saveFileFromByteArray(bufferBody, newFile);

                        Log.d("Converted ByteArray", "");

                        Log.d("Saved File to: ", "pathDestination");

                        break;
                    }

                    case 2: {
                        Log.d("Node Transfer Request", "");

                        byte[] bufferBody = serialization.getByteData(buffer);

                        node = serialization.deserializdeNode(bufferBody);

                        Log.d(node.toString(), "");

                        break;
                    }

                    case ROUTING: {
                        Log.d("Routing: ", "");

                        RoutHelper rh = server.getRoutHelper(buffer);
                        Node nodeNew = serialization.getSerialzedNode().routing(rh);
                        if (nodeNew != null) {
                            //starte node transfer
                        }
                        Log.d("nodeNew ", nodeNew.toString());

                    }

                    case PEERLIST: {
                        Log.d("List: ", "");

                        ArrayList<PeerMemo> list = server.getListPeer(buffer);
                        PeerMemo p = null, p1 =null, p2 = null;
                        int i = list.size();
                        Log.d("PeerList filled", " "+list.toString());

                        if(i == 1){
                            p = list.get(i--);
                        }else if(i>1){
                            p1 = list.get(i--);
                            if (i >= 1) {
                                p2 = list.get(i--);

                            }
                        }else{

                        }

                        startUpdatePeers(p,p1,p2);
                        Log.d("List: ", list.toString());
                        Log.d("PEEEEEEEEEEEERS", pDB.getAllPeer().toString());
                    }

                    case NEIGHBOURLIST: {
                        Log.d("NeighbourList:", "");
                        int i;
                        ArrayList<Neighbour> list = server.getListNeighbour(buffer);
                        Neighbour n = null, n1 = null, n2 = null, n3 = null;
                        i = list.size();
                        Log.d("NeighbourList filled", " "+list.toString());

                        if (i == 1) {
                            n = list.get(i--);
                        } else if (i > 1) {
                            n1 = list.get(i--);
                            if (i > 1) {
                                n2 = list.get(i--);
                                if (i >= 1) {
                                    n3 = list.get(i);
                                }
                            }
                        } else {

                        }
                        startUpdateNeighbours(n, n1, n2, n3);
                        Log.d("NeighBOUUUUUUUR", "" + nDB.getAllNeighborMemo().toString());

                        //Log.d("List: ",  list.toString());

                    }
                }

                ss.close();
            }catch(Exception e){
                e.printStackTrace();
            } finally{
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



    private void startUpdatePeers(PeerMemo p, PeerMemo p1, PeerMemo p2){
        new AsyncTask<PeerMemo,Void,Void>(){

            @Override
            protected Void doInBackground(PeerMemo... params) {
                int i;
                for(i=0; i<params.length; i++){
                    pDB.createPeerMemo(params[i]);
                }
                return null;
            }
            // vieleicht noch Pram zu execute
        }.execute();
    }


    private void startUpdateNeighbours(Neighbour n, Neighbour n1, Neighbour n2, Neighbour n3) {

        new AsyncTask<Neighbour, Void, Void>() {

            @Override
            protected Void doInBackground(Neighbour... params) {
                int i;
                for(i=0; i<params.length; i++){
                    nDB.createNeighborMemo(params[i]);
                }


                return null;
            }
        }.execute();
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

