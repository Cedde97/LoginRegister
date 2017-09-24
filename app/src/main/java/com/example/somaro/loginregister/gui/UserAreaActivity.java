package com.example.somaro.loginregister.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somaro.loginregister.R;

import org.json.JSONException;

import java.io.File;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import connection.ServerThread;
import source.DatabaseManager;
import source.DateiMemoDbHelper;
import source.NeighborDbSource;
import task.FileTransferTask;
import task.RequestJoinTask;
import task.SendNeighBourListTask;
import activity.SendRoutActivity;
import bootstrap.AllIPsActivity;
import bootstrap.InsertOwnIPActivity;
import connection.Client;
import connection.RoutHelper;
import connection.ServerThreadActivity;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.*;

public class UserAreaActivity extends Activity {
    private static final int IMAGE_GALLERY_REQUEST = 20;
    private static final int PORT = 9797;
    private ServerSocket serverSocket;

    private ImageView imageView;

    private Client client;
    private static String bootsIp = null;

    private static final int CAM_REQUEST = 1;
    private int bildAnzahl;
    private String singleParsed = "";
    private String dataParsed = "";
    private int id ;
    private NeighborDbSource nDb = new NeighborDbSource();
    private String data = null;



    private Button routRequest, fileTransferRequest, neighbourTransfer, firstRouting;
    

    public String getPhotoId( ){

        String data = "";

        data = "" + getId() + getBildAnzahl() ;
    return data;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *Hier hat der Papa geädert, dass die globale Variable direkt gesetzt wird
         *Würde es im onCreate lassen, denn wenn man sie über einen Clicklistener realisieren will, muss man die Threads wieder mal handlen. Der Asynctask ist sonst nicht schnell genug fertig. Bzw gibts
         *thread-sync probleme
         */
        /*try {
            String bootsIp = startGetBootsIp();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        setContentView(R .layout.activity_user_area);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        firstRouting = (Button) findViewById(R.id.firstRouting);
        firstRouting.setOnClickListener(FirstRouteClickListener);

        routRequest = (Button) findViewById(R.id.routingRequest);
        routRequest.setOnClickListener(RoutClickListener);

        fileTransferRequest = (Button) findViewById(R.id.fileTransferButton);
        fileTransferRequest.setOnClickListener(FileTransferListener);

        neighbourTransfer = (Button) findViewById(R.id.neighbourTransferButton);
        neighbourTransfer.setOnClickListener(NeighbourTransferListener);

        imageView = (ImageView) findViewById(R.id.imageView);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        id = intent.getIntExtra("id",0);
       // String phototID = photoId(id);
        String message = id + " " + getPhotoId() + name + " welcome to your user area";

        welcomeMsg.setText(message);

        Context appContext;
        DateiMemoDbHelper dbHelper;

        //===============================================
        appContext = this.getApplicationContext();
        dbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dbHelper);
        //===============================================

        Thread serverThread = new Thread(new ServerThread());
        serverThread.start();
    }

    /**
     * @author Joshua Zabel
     *
     */
    private View.OnClickListener FirstRouteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            client = new Client();
            //Eigene IP holen, benötigen wir für RouteHelper
            String ownIP = Client.getOwnIpAddress();
            double x = Node.hashX(ownIP);
            double y = Node.hashY(ownIP);
            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            id = intent.getIntExtra("id",0);
            RoutHelper rh = new RoutHelper(ownIP,x,y,id);
            startRequestJoin(rh);
        }
    };

    /**
     * @author Joshua Zabel
     * Button der das Routen eines Knoten Simuliert.
     */
    private View.OnClickListener RoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            client = new Client();

            try {
                //ip von Bootstrap-Server holen
                //ip des "simulierten" Knoten der bereits in CAN ist

                Log.d("Globale BootsIP", bootsIp);
                //Socket socket = new Socket(bootsIp, PORT);
                Socket socket = new Socket("192.168.2.102",PORT);


                Log.d("Socket: ", socket.toString());
                String ownIP = Client.getOwnIpAddress();
                Log.d("ownIP: ", ownIP);
                //uid holen
                //Daten des zu routenden Knoten
                RoutHelper rh = new RoutHelper(ownIP, Node.hashX(ownIP), Node.hashY(ownIP), 02);
                Log.d("Routhelper: ", rh.toString());

                //senden des RoutHelper-Objectes
                Log.d("Clicked", "RoutRequest");
                SendRoutActivity srt = new SendRoutActivity(socket, rh);
                srt.execute();

            } catch (Exception e){
                e.printStackTrace();
            }

        }
    };


    /**
     * @author Joshua Zabel
     * Methode zum Simulieren des Sendens einer NeigbourList
     */
    private View.OnClickListener NeighbourTransferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            client = new Client();


            try {
                Socket socket = new Socket("192.168.2.102", PORT);
                ArrayList<Neighbour> arrayList= (ArrayList)nDb.getAllNeighborMemo();
                /*Neighbour n = new Neighbour();
                Neighbour n1 = new Neighbour();
                Corner topRight = new Corner(0.0,0.0);
                Corner topLeft = new Corner(0.0,0.0);
                Corner bottomRight = new Corner(0.0,0.0);
                Corner bottomLeft = new Corner(1.0,1.0);
                n.setUid(8754);
                n.getMyZone().setTopRight(topRight);
                n.getMyZone().setTopLeft(topLeft);
                n.getMyZone().setBottomRight(bottomRight);
                n.getMyZone().setBottomLeft(bottomLeft);
                n.setPunktX(0.2);
                n.setPunktY(0.4);
                n.setUIP("277.0.0.0/8");
                n.setRTT(25.89);*/

                Log.d("before sending", ""+arrayList.toString());
                SendNeighBourListTask snl =new SendNeighBourListTask(socket,arrayList);
                snl.execute();
                //Neighbour neighbour = new Neighbour(01l, 0.0, 0.1, "192.33.2.12", 12.3);

                //NeighbourTransferActivity nft = new NeighbourTransferActivity(socket, neighbour);
                // nft.execute();
            } catch (IOException e) {
                Log.d("NeighbourTransfer: ", e.toString());
            }
        }
    };


    /**
     * Button-Test fÃ¼r FileTransfer
     */
    private View.OnClickListener FileTransferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            client = new Client();
            String path = (Environment.getExternalStorageDirectory() + "/Hont.jpg");

            try {
                File file = new File(path);
                Socket socket = new Socket("192.168.2.115", PORT);
                FileTransferTask ftt = new FileTransferTask(socket, file);
                ftt.execute();
            } catch (IOException e) {
                Log.d("FileTransfer: ", e.toString());
            }
        }
    };


    /**Auch eine Änderung von Papa: In der If-Abfrage einfach die globale Variable gesetzte und reutrnt.
     * Methode, welche einen Knoten aus der Liste der Bootstrap-Nodes auswählt
     * @return IP des Bootstrap-Knotes, zu dem geroutet werden soll
     * @throws JSONException
     */
    private String startGetBootsIp() throws JSONException {

        new AllIPsActivity(new AllIPsActivity.AsyncResponse(){

            @Override
            public void processFinish(String[] ipArray){
                for(int i = 0; i<ipArray.length; i++){

                    if(ipArray[i].contains("192.168.2.115")){
                        UserAreaActivity.bootsIp = ipArray[i];
                        Log.d("StatGetBootsIp", UserAreaActivity.bootsIp);
                    }
                }
            }
        }).execute();
        return bootsIp;
    }


    /**
     *  Methode zum starten der Kammera
     *
     * @param v
     */
    public void btnSaveClicked (View v) {

        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent, CAM_REQUEST);
    }

    /**
     * Methode zum Anzeigen des Bildes in der App
     * Und Aufruf der anderen Methoden
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        saveImageFile(bitmap) ;
    }

    /**
     *
     * Methode zum Speichern des Bildes
     *
     * @param bitmap Das Bild
     * @return der Dateiname
     */
    private String saveImageFile(Bitmap bitmap) {
        OwnDataMemo ownDataMemo = new OwnDataMemo(getId(),Integer.parseInt(getPhotoId()));
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bildAnzahl = bildAnzahl +1 ;

        Toast.makeText(UserAreaActivity.this, getPhotoId() , Toast.LENGTH_SHORT).show();

        return filename;
    }

    /**
     * @author Alexander Lukacs
     *
     * Liefert die ID des Knotens
     *
     * @return ID des Knoten
     */
    public int getId()
    {
        return id;
    }

    public int getBildAnzahl(){

        return  bildAnzahl;
    }

    /**
     * Methode zum Erstellen des Speicherpfades
     *
     * @return der Speicherpfad
     */
    private String getFilename() {

        File file = new File(Environment.getExternalStorageDirectory() + File.separator+"images");

        File dir = new File(file, "CAN_PICS");
        Toast.makeText(UserAreaActivity.this, dir.toString(), Toast.LENGTH_SHORT).show();
        /*if(dir.mkdirs()){

            String uriSting = (dir.getAbsolutePath() +"/"
                    + System.currentTimeMillis() + ".jpg");
                return uriString;
        }else{
            // TODO: 29.08.2017 throw exception
        }*/

        dir.mkdirs();
        String uriString = (dir.getAbsolutePath() +"/" + "#"
                + getPhotoId() + "#" + ".jpg");
        return uriString;
    }

    private void insertOwnIP() throws JSONException {
        new InsertOwnIPActivity().execute();
    }

    private void startRequestJoin(RoutHelper rh){
        new RequestJoinTask().execute(rh);
    }

    /**
     * Ã–ffnen der Foto Gallery
     *
     * @param v
     */
    public void onImageGalleryClick(View v){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Toast.makeText(UserAreaActivity.this, pictureDirectory.toString(), Toast.LENGTH_SHORT).show();
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);

        photoPickerIntent.setDataAndType(data, "image/*");

        startActivityForResult(photoPickerIntent,IMAGE_GALLERY_REQUEST );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
