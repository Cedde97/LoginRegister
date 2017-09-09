package com.example.somaro.loginregister.gui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import connection.Client;
import connection.RoutHelper;
import connection.ServerThreadActivity;
import model.Neighbour;
import model.Node;
import model.Zone;


public class UserAreaActivity extends Activity {
    private Client client;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imageView;
    static final int CAM_REQUEST = 1;

    Button routRequest, fileTransferRequest, neighbourTransfer, startServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        routRequest = (Button) findViewById(R.id.routingRequest);
        routRequest.setOnClickListener(RoutClickListener);

        fileTransferRequest = (Button) findViewById(R.id.fileTransferButton);
        fileTransferRequest.setOnClickListener(FileTransferListener);

        neighbourTransfer = (Button) findViewById(R.id.neighbourTransferButton);
        neighbourTransfer.setOnClickListener(NeighbourTransferListener);

        startServer = (Button) findViewById(R.id.startServerButton);
        startServer.setOnClickListener(StartServerListener);

        imageView = (ImageView) findViewById(R.id.imageView);
        final TextView welcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        String message = name + " welcome to your user area";
        welcomeMsg.setText(message);
    }


    View.OnClickListener StartServerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent i = new Intent(getApplicationContext(), ServerThreadActivity.class);
            Log.d("Started Server", i.toString());
            startActivity(i);
        }
    };


    View.OnClickListener NeighbourTransferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            client = new Client();


            try {
                Socket socket = new Socket("192.168.2.110", 8080);
                Zone zone = new Zone();
                Neighbour neighbour = new Neighbour(01l, 1.1, 2.2, 3.3, 4.4, 1.1, 2.2, 3.3, 4.4, 0.0, 0.1, "192.33.2.12", 12.3);

                NeighbourTransferThread nft = new NeighbourTransferThread(socket, neighbour);
                nft.execute();
            } catch (IOException e) {
                Log.d("NeighbourTransfer: ", e.toString());
            }
        }
    };

    class NeighbourTransferThread extends AsyncTask<String, String, String> {
        Socket socket = null;
        Neighbour neighbour = null;

        public NeighbourTransferThread(Socket socket, Neighbour neighbour) {
            this.socket = socket;
            this.neighbour = neighbour;
        }

        protected String doInBackground(String... args) {

            try {

                client.sendNeighbourAsByteArray(socket, neighbour);

                socket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * Button-Test fÃ¼r FileTransfer
     */
    View.OnClickListener FileTransferListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            client = new Client();
            String path = (Environment.getExternalStorageDirectory() + "/Hont.jpg");

            try {
                File file = new File(path);
                Socket socket = new Socket("192.168.2.110", 8080);
                FileTransferThread ftt = new FileTransferThread(socket, file);
                ftt.execute();
            } catch (IOException e) {
                Log.d("FileTransfer: ", e.toString());
            }
        }
    };


    class FileTransferThread extends AsyncTask<String, String, String> {
        Socket socket = null;
        File file = null;

        public FileTransferThread(Socket socket, File file) {
            this.socket = socket;
            this.file = file;
        }

        protected String doInBackground(String... args) {

            try {

                client.sendImageAsByteArray(socket, file);

                socket.close();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    /**
     * Button Test fÃ¼r Routing
     */
    View.OnClickListener RoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            client = new Client();
            Zone zone = new Zone();
            try {
                Socket socket = new Socket("192.168.2.110", 8080);

                RoutHelper rh = new RoutHelper("192.168.2.101", 0.5, 0.4, 02l);

                SendRoutTask srt = new SendRoutTask(socket, rh);
                srt.execute();


            } catch (IOException e) {
                Log.d("client.sendNode", e.toString());
            }

        }
    };

    class SendRoutTask extends AsyncTask<Void, Void, Void> {

        Socket socket;
        RoutHelper rh;

        public SendRoutTask(Socket socket, RoutHelper rh) {
            this.socket = socket;
            this.rh = rh;
        }

        protected Void doInBackground(Void... arg0) {

            try {
                client.sendRoutHelperAsByteArray(socket, rh);
            } catch (IOException e) {
                Log.d("client.sendNode", e.toString());
            } catch (Exception e) {
                Log.d("RoutHelper: ", rh.toString());
            }
            return null;
        }
        //ServerSeite hash Function Aufruf von getIP
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
        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return filename;
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
        String uriString = (dir.getAbsolutePath() +"/"
                + System.currentTimeMillis() + ".jpg");
        return uriString;
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

}