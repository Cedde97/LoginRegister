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

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.Corner;
import model.ForeignData;
import model.Neighbour;
import model.Node;
import model.PeerMemo;
import model.Zone;
import task.HashXTask;
import task.HashYTask;
import task.RoutingTask;

/**
 * Created by Cedric on 06.09.2017.
 * last change by Joshua Zabel
 */

public class ServerThreadActivity extends Activity{
    private static final int PORTNR       = 9797;
    private static final int FILETRANSFER = 1;
    private static final int NODETRANSFER = 2;
    private static final int NEIGHTRANSFER= 4;
    private static final int PEERTRANSFER = 5;
    private static final int FOREIGNTRANS = 6;
    private static final int ROUTING = 7;

    private Socket socket = null;
    private Server server = new Server();


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

            try{
                Log.d("Server is started","In ServerThreadActivity");
                ss = new ServerSocket(PORTNR);

                Log.d("Waiting for request","ServerThreadActivity");
                Socket s = ss.accept();

                Log.d("Client connected","ServerThreadActivity");

                byte[] buffer = server.receiveByteArray(ss, s);

                Log.d("BufferBytes: " + buffer.length,"");

                Log.d("Received ByteArray","");

                int methodName = serialization.getByteHeader(buffer);

                System.out.println(Integer.toString(methodName));


                switch(methodName){

                    case FILETRANSFER: {
                        Log.d("File Transfer Request","");

                        String pathDestination = "C://Users/Cedric/Pictures/test/placeholderNew1.jpg";

                        File newFile = new File(pathDestination);

                        byte[] bufferBody = serialization.getByteData(buffer);

                        server.saveFileFromByteArray(bufferBody, newFile);

                        Log.d("Converted ByteArray","");

                        Log.d("Saved File to: " ,"pathDestination");

                        break;
                    }

                    case 2: {
                        Log.d("Node Transfer Request","");

                        byte[] bufferBody = serialization.getByteData(buffer);

                        node = serialization.deserializdeNode(bufferBody);

                        Log.d(node.toString(),"");

                        break;
                    }

                    case ROUTING: {
                        Log.d("Routing: ","");

                        RoutHelper rh = server.getRoutHelper(buffer);
                        Node nodeNew = serialization.getSerialzedNode().routing(rh);
                        if(nodeNew != null){
                            //starte node transfer
                        }
                        Log.d("nodeNew ",nodeNew.toString());

                    }
                }

                ss.close();
            }catch (Exception e){
                e.printStackTrace();
            } finally{
                try{
                    if(ss != null)
                        ss.close();
                    Log.d("ServerSocket closed","");
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
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

