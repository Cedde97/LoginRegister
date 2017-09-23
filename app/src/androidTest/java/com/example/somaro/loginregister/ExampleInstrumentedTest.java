package com.example.somaro.loginregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.somaro.loginregister.gui.UserAreaActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import app.App;
import connection.Client;
import connection.RoutHelper;
import exception.*;
import model.*;
import source.*;
import util.DBUtil;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@SuppressWarnings("deprecation")
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.somaro.loginregister", appContext.getPackageName());
    }


    @Test
    public void test() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        if (database == null) {
            dateiMemoDbHelper.onCreate(database);
        }
        //dateiMemoDbHelper.onCreate(database);

        dateiMemoDbHelper.onUpgrade(database, 0, dateiMemoDbHelper.DB_VERSION);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        ForeignDataDbSource foreignDataDbSource = new ForeignDataDbSource();
        NeighborDbSource neighborDbSource = new NeighborDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();
        Node dateiMemo = new Node();
        //PeerMemo peerMemo = new PeerMemo();
        ForeignData foreignData = new ForeignData();
        OwnDataMemo ownDataMemo = new OwnDataMemo();
        Neighbour neighborMemo = new Neighbour();


        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;

        Corner cornerBottomLeftNeighbour;
        Corner cornerBottomRightNeighbour;
        Corner cornerTopLeftNeighbour;
        Corner cornerTopRightNeighbour;
        Zone zoneNeighbour;

        try {
            cornerBottomLeft = new Corner(0.0, 0.0);
            cornerBottomRight = new Corner(1.0, 0.0);
            cornerTopLeft = new Corner(0.0, 1.0);
            cornerTopRight = new Corner(1.0, 1.0);

            //dateiMemoDbSource.deleteDateiMemo();


            zone = new Zone(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);

            dateiMemo.setUid(7872);
            dateiMemo.setTopRight(cornerTopRight);
            dateiMemo.setBottomLeft(cornerBottomLeft);
            dateiMemo.setTopLeft(cornerTopLeft);
            dateiMemo.setBottomRight(cornerBottomRight);
            dateiMemo.setPunktX(0.3);
            dateiMemo.setPunktY(0.4);
            dateiMemo.setIP("227.0.0.0/8");
            dateiMemo.setCountPeers(2);
            dateiMemo.setMyZone(zone);
            dateiMemoDbSource.createDateiMemo(dateiMemo);

            dateiMemoDbSource.updatePunktX(0.333333);
            dateiMemoDbSource.updatePunktY(0.444444);
            dateiMemoDbSource.updateCountPeers();

            dateiMemoDbSource.updateCornerBottomLeftX(0.111122151111111);
            dateiMemoDbSource.updateCornerBottomLeftY(0.111112222221);
            dateiMemoDbSource.updateCornerBottomRightX(0.1111158111111);
            dateiMemoDbSource.updateCornerBottomRightY(0.1111571111111);
            dateiMemoDbSource.updateCornerTopLeftX(0.11111353111111);
            dateiMemoDbSource.updateCornerTopLeftY(0.11111163611111);
            dateiMemoDbSource.updateCornerTopRightX(0.11111158511111);
            dateiMemoDbSource.updateCornerTopRightY(0.1111851111111);

            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid());
            Log.d("TEST", "DATEIMEMO_TOPRIGHT " + dateiMemoDbSource.getCornerTopRightX() + ", " + dateiMemoDbSource.getCornerTopRightY());
            Log.d("TEST", "DATEIMEMO_BOTTOMRIGHT " + dateiMemoDbSource.getCornerBottomRightX() + ", " + dateiMemoDbSource.getCornerBottomRightY());
            Log.d("TEST", "DATEIMEMO_TOPLEFT " + dateiMemoDbSource.getCornerTopLeftX() + ", " + dateiMemoDbSource.getCornerTopLeftY());
            Log.d("TEST", "DATEIMEMO_BOTTOMLEFT " + dateiMemoDbSource.getCornerBottomLeftX() + ", " + dateiMemoDbSource.getCornerBottomLeftY());
            Log.d("TEST", "DATEIMEMO_PUNKTX " + dateiMemoDbSource.getPunktX());
            Log.d("TEST", "DATEIMEMO_PUNKTY " + dateiMemoDbSource.getPunktY());
            Log.d("TEST", "DATEIMEMO_IP " + dateiMemoDbSource.getIp(dateiMemo.getUid()));
            Log.d("TEST", "DATEIMEMO_COUNTPEERS " + dateiMemoDbSource.getCountPeers());
            //Log.d("TEST", "DATEIMEMO_ZONE " + dateiMemoDbSource.getZone());

            Log.d("TEST", "DATEIMEMO_ALL" + dateiMemoDbSource.getAllDateiMemos());


            //assertEquals(7872, dateiMemoDbSource.getUid());

            //assertEquals(7872, dateiMemoDbSource.getUid());

            //assertEquals(0.3, dateiMemoDbSource.getPunktX(), 0);
            //assertEquals(0.4, dateiMemoDbSource.getPunktY(), 0);

            //create Peer
            PeerMemo peerMemo = new PeerMemo();
            peerMemo.setPeerIp("1.1.1.1");
            peerMemo.setUid(dateiMemoDbSource.getUid());
            //peerMemo.setPeerId(3);
            peerDbSource.createPeerMemo(peerMemo);

            peerMemo.setPeerIp("1.1.1.0");
            peerMemo.setUid(dateiMemoDbSource.getUid());
            peerDbSource.createPeerMemo(peerMemo);


            //getEachPeer-Test
            //
            List<PeerMemo> peerMemoList = peerDbSource.getEachPeer(1);
            Log.d("Test getEachPeer", "=============================================================");

            for (int i = 0; i < peerMemoList.size(); i++) {
                String output = "Node_Peer_ID: " + peerMemoList.get(i).getUid() +
                        //"\n Status: "+ peerMemoList.get(i).isChecked() +
                        "\nPeer ID: " + peerMemoList.get(i).getPeerId() +
                        "\n IP: " + peerMemoList.get(i).getPeerIp();

                Log.d("Result", output);
            }
            Log.d("Test getEachPeer", "=============================================================");

            //getAllPeer-Test
            //
            List<PeerMemo> peerMemoListAll = peerDbSource.getAllPeer();
            Log.d("Test getAllPeer", "=============================================================");

            for (int i = 0; i < peerMemoListAll.size(); i++) {
                String output = "Node_Peer_ID: " + peerMemoListAll.get(i).getUid() +
                        //"\n Status: "+ peerMemoList.get(i).isChecked() +
                        "\nPeer ID: " + peerMemoListAll.get(i).getPeerId() +
                        "\n IP: " + peerMemoListAll.get(i).getPeerIp();

                Log.d("Result", output);
            }
            Log.d("Test getAllPeer", "=============================================================");

            String p = peerDbSource.getPeerIp(peerDbSource.getUidPeer());

            assertEquals("1.1.1.1", p);

            Log.d("TEST", "PEER_UID " + peerDbSource.getUidPeer());

            Log.d("HALLO", "AAAAAAAAAAAAAAAAAA: String_IP " + p);

            //create Foreign
            //foreign key
            foreignData.setUid(dateiMemo.getUid());
            //foreignData.setChecked(true);
            foreignData.setFotoId(2);
            foreignData.setPunktX(0.5);
            foreignData.setPunktY(0.5);
            foreignData.setForeignIp("277.0.0.1");
            foreignDataDbSource.createForeignData(foreignData);


            double x = foreignDataDbSource.getPunktXForeign(foreignData.getUid());

            Log.d("TEST", "FOREIGNDATADB_ID " + foreignDataDbSource.getUidForeign());
            Log.d("TEST", "FOREIGNDATADB_FOTOID " + foreignDataDbSource.getFotoId(foreignData.getUid()));
            Log.d("TEST", "FOREIGNDATADB_PUNKTY " + foreignDataDbSource.getPunktYForeign(foreignData.getUid()));
            Log.d("TEST", "FOREIGNDATADB_IP " + foreignDataDbSource.getforeignIp(foreignData.getUid()));

            assertEquals(0.5, x, 0);
            Log.d("HALLO", "AAAAAAAAAAAAAAAAAA: double_punktX " + x);

            //create own Data
            //foreign key
            Log.d("TEST", "OWNDATA_UID " + ownDataMemo.getUid());

            ownDataMemo.setUid(dateiMemo.getUid());
            ownDataMemo.setFileId(3);
            //ownDataMemo.setChecked(true);
            Log.d("TEST", "OWNDATA_UID " + ownDataMemo.getUid());
            Log.d("TEST", "OWNDATA_FILEID " + ownDataMemo.getFileId());

            ownDataDbSource.createOwnData(ownDataMemo);

            Log.d("TEST", "OWNDATADB_FILEID " + ownDataDbSource.getFileId(ownDataMemo.getUid()));

            Log.d("TEST", "OWNDATADB_UID " + ownDataDbSource.getUID(ownDataMemo.getFileId()));

            Log.d("TEST", "OWNDATADB " + ownDataDbSource.getAllOwnData());
            //create neighbour
            //foreign key

            Neighbour n = new Neighbour();
            n.setUid(dateiMemoDbSource.getUid());
            //neighborMemo.setChecked(true);
            n.setCornerTopRightX(0.5);
            n.setCornerTopRightY(0.6);
            n.setCornerTopLeftX(0.2);
            n.setCornerTopLeftY(0.2);
            n.setCornerBottomLeftX(0.4);
            n.setCornerBottomLeftY(0.6);
            n.setCornerBottomRightX(0.5);
            n.setCornerBottomRightY(0.8);
            n.setPunktX(0.2);
            n.setPunktY(0.4);
            n.setUIP("277.0.0.0/8");
            n.setRTT(25.89);
            neighborDbSource.createNeighborMemo(n);

            neighborMemo.setUid(dateiMemoDbSource.getUid());
            //neighborMemo.setChecked(true);
            neighborMemo.setCornerTopRightX(0.5);
            neighborMemo.setCornerTopRightY(0.6);
            neighborMemo.setCornerTopLeftX(0.2);
            neighborMemo.setCornerTopLeftY(0.2);
            neighborMemo.setCornerBottomLeftX(0.4);
            neighborMemo.setCornerBottomLeftY(0.6);
            neighborMemo.setCornerBottomRightX(0.5);
            neighborMemo.setCornerBottomRightY(0.8);
            neighborMemo.setPunktX(0.2);
            neighborMemo.setPunktY(0.4);
            neighborMemo.setUIP("277.0.0.0/8");
            neighborMemo.setRTT(25.89);
            //neighborMemo.setNeighbour_id(2);
            neighborDbSource.createNeighborMemo(neighborMemo);

            neighborMemo.setUid(dateiMemoDbSource.getUid());
            //neighborMemo.setChecked(true);
            neighborMemo.setCornerTopRightX(0.3);
            neighborMemo.setCornerTopRightY(0.4);
            neighborMemo.setCornerTopLeftX(0.1);
            neighborMemo.setCornerTopLeftY(0.5);
            neighborMemo.setCornerBottomLeftX(0.1);
            neighborMemo.setCornerBottomLeftY(0.9);
            neighborMemo.setCornerBottomRightX(0.4);
            neighborMemo.setCornerBottomRightY(0.2);
            neighborMemo.setPunktX(0.3);
            neighborMemo.setPunktY(0.4);
            neighborMemo.setUIP("277.0.0.0/8");
            neighborMemo.setRTT(25.82);
            //neighborMemo.setNeighbour_id(2);
            neighborDbSource.createNeighborMemo(neighborMemo);

            neighborMemo.setUid(dateiMemoDbSource.getUid());
            //neighborMemo.setChecked(true);
            neighborMemo.setCornerTopRightX(0.2);
            neighborMemo.setCornerTopRightY(0.1);
            neighborMemo.setCornerTopLeftX(0.6);
            neighborMemo.setCornerTopLeftY(0.8);
            neighborMemo.setCornerBottomLeftX(0.3);
            neighborMemo.setCornerBottomLeftY(0.5);
            neighborMemo.setCornerBottomRightX(0.3);
            neighborMemo.setCornerBottomRightY(0.1);
            neighborMemo.setPunktX(0.6);
            neighborMemo.setPunktY(0.4);
            neighborMemo.setUIP("277.0.0.0/8");
            neighborMemo.setRTT(25.86);
            //neighborMemo.setNeighbour_id(2);
            neighborDbSource.createNeighborMemo(neighborMemo);


            neighborDbSource.updateCornerBottomLeftYNeighbor(1, 0.6666666);
            neighborDbSource.updateCornerBottomLeftXNeighbor(1, 0.4444444);
            neighborDbSource.updateCornerBottomRightXNeighbor(1, 0.5555555);
            neighborDbSource.updateCornerBottomRightYNeighbor(1, 0.8888888);
            neighborDbSource.updateCornerTopLeftXNeighbor(1, 0.2222222);
            neighborDbSource.updateCornerTopLeftYNeighbor(1, 0.2222224);
            neighborDbSource.updateCornerTopRightXNeighbor(1, 0.5555556);
            neighborDbSource.updateCornerTopRightYNeighbor(1, 0.6666665);

            //getEachNeighbor Test
            List<Neighbour> neighborMemoList = neighborDbSource.getEachNeighbour(1);
            Log.d("Test getEachNeighbor", "=============================================================");
            //double cornerTopRightX1 = neighborMemoList.get(1).getCornerTopRightX();
            // Log.d("TEST", "REsult" + cornerTopRightX1);
            for (int i = 0; i < neighborMemoList.size(); i++) {
                String output = "Neighbor_ID: " + neighborMemoList.get(i).getNeighbour_id() +
                        "\n Neighbor_ID_Foreign: " + neighborMemoList.get(i).getUid() +
                        //"\n Status: "+ neighborMemoList.get(i).isChecked() +
                        "\n Corner Top Right X: " + neighborMemoList.get(i).getCornerTopRightX() +
                        "\n Corner Top Right Y: " + neighborMemoList.get(i).getCornerTopRightY() +
                        "\n Corner Top Left X: " + neighborMemoList.get(i).getCornerTopLeftX() +
                        "\n Corner Top Left Y: " + neighborMemoList.get(i).getCornerTopLeftY() +
                        "\n Corner Bottom Right X: " + neighborMemoList.get(i).getCornerBottomRightX() +
                        "\n Corner Bottom Right Y: " + neighborMemoList.get(i).getCornerBottomRightY() +
                        "\n Corner Bottom Left X: " + neighborMemoList.get(i).getCornerBottomLeftX() +
                        "\n Corner Bottom Left Y: " + neighborMemoList.get(i).getCornerBottomLeftY() +
                        "\n Punkt X: " + neighborMemoList.get(i).getPunktX() +
                        "\n Punkt Y: " + neighborMemoList.get(i).getPunktY() +
                        "\n IP: " + neighborMemoList.get(i).getUIP() +
                        "\n RTT: " + neighborMemoList.get(i).getRTT();

                Log.d("Result", output);
            }
            Log.d("Test getEachNeighbor", "=============================================================");

            //getAllNeighbor test
            //
            List<Neighbour> neighbourMemoListAll = neighborDbSource.getAllNeighborMemo();
            Log.d("Test getAll", "=============================================================");
//            double cornerTopRightX1All = neighbourMemoListAll.get(1).getCornerTopRightX();
//            double cornerTopRightX2All = neighbourMemoListAll.get(2).getCornerTopRightX();
//            Log.d("TEST", "BBBBBBBBB" + cornerTopRightX1All);
//            Log.d("TEST", "BBBBBBBBB" + cornerTopRightX2All);
            for (int i = 0; i < neighbourMemoListAll.size(); i++) {
                String output = "Neighbor_ID: " + neighbourMemoListAll.get(i).getNeighbour_id() +
                        "\n Neighbor_ID_Foreign: " + neighbourMemoListAll.get(i).getUid() +
                        //"\n Status: "+ neighborMemoList.get(i).isChecked() +
                        "\n Corner Top Right X: " + neighbourMemoListAll.get(i).getCornerTopRightX() +
                        "\n Corner Top Right Y: " + neighbourMemoListAll.get(i).getCornerTopRightY() +
                        "\n Corner Top Left X: " + neighbourMemoListAll.get(i).getCornerTopLeftX() +
                        "\n Corner Top Left Y: " + neighbourMemoListAll.get(i).getCornerTopLeftY() +
                        "\n Corner Bottom Right X: " + neighbourMemoListAll.get(i).getCornerBottomRightX() +
                        "\n Corner Bottom Right Y: " + neighbourMemoListAll.get(i).getCornerBottomRightY() +
                        "\n Corner Bottom Left X: " + neighbourMemoListAll.get(i).getCornerBottomLeftX() +
                        "\n Corner Bottom Left Y: " + neighbourMemoListAll.get(i).getCornerBottomLeftY() +
                        "\n Punkt X: " + neighbourMemoListAll.get(i).getPunktX() +
                        "\n Punkt Y: " + neighbourMemoListAll.get(i).getPunktY() +
                        "\n IP: " + neighbourMemoListAll.get(i).getUIP() +
                        "\n RTT: " + neighbourMemoListAll.get(i).getRTT();

                Log.d("Result", output);
            }

            Log.d("Test getAll", "=============================================================");


            Log.d("TEST", "NEIGHBOUR_ID " + neighborDbSource.getNID(1));
            Log.d("TEST", "NEIGHBOUR_TOPRIGHT " + neighborDbSource.getCornerTopRightXNeighbor(1) + ", " + neighborDbSource.getCornerTopRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMRIGHT " + neighborDbSource.getCornerBottomRightXNeighbor(1) + ", " + neighborDbSource.getCornerBottomRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_TOPLEFT " + neighborDbSource.getCornerTopLeftXNeighbor(1) + ", " + neighborDbSource.getCornerTopLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMLEFT " + neighborDbSource.getCornerBottomLeftXNeighbor(1) + ", " + neighborDbSource.getCornerBottomLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_X " + neighborDbSource.getPunktXNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_Y " + neighborDbSource.getPunktYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_IP " + neighborDbSource.getUip(1));
            double r = neighborDbSource.getRTT(1);

            Log.d("HALLO", "AAAAAAAAAAAAAAAAAA: double_RTT " + r);


//            assertEquals(7872,neighborDbSource.getNID(1));
            //assertEquals(0.5, neighborDbSource.getCornerTopRightXNeighbor(1), 0);
            //assertEquals(0.6, neighborDbSource.getCornerTopRightYNeighbor(1), 0);

            //dateiMemoDbSource.deleteDateiMemo();
            //foreignData.deleteForeignData();
            //neighborDbSource.deleteNeighbormemo();
            //ownDataDbSource.deleteOwnData();
            //peerDbSource.deletePeerMemo();

        } catch (XMustBeLargerThanZeroException xMBLTZE) {
            Log.d("", "111111");
        } catch (YMustBeLargerThanZeroException yMBLTZE) {
            Log.d("", "222222");
        } catch (Exception e) {
            Log.d("", "333336 " + e.getMessage());
        }
        //foreign key
//        peerMemo.setUid(dateiMemoDbSource.getUid());
//        peerMemo.setPeerIp("277.0.0.1");
//        peerDbSource.createPeerMemo(peerMemo);
//
//
//        dateiMemoDbSource.deleteDateiMemo();
//        foreignData.deleteForeignData();
//        neighborDbSource.deleteNeighbormemo();
//        ownDataDbSource.deleteOwnData();
//        peerDbSource.deletePeerMemo();

    }

    /**
     * @author Alexander Lukacs
     */
    @Test
    public void testExist() {
        // DAS BRAUCHT MAN UM ZUGRIFF AUF DIE DATNEBANK ZU BEKOMMEN
        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        NeighborDbSource neighborDbSource = new NeighborDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();
        ForeignDataDbSource foreignDataDbSource = new ForeignDataDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();

        Log.d("TEST", "NODE " + dateiMemoDbSource.getAllDateiMemos());
        Log.d("TEST", "NEIGHBOR" + neighborDbSource.getAllNeighborMemo());
        Log.d("TEST", "PEERS" + peerDbSource.getAllPeer());
        Log.d("TEST", "FOREIGNDATA" + foreignDataDbSource.getAllForeignData());
        Log.d("TEST", "OWNDATA" + ownDataDbSource.getAllOwnData());

        Log.d("TEST", "NEIGHBOR_ONE" + neighborDbSource.getEachNeighbour(1));

    }

    @Test
    public void TestUpdateCorner() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        if (database == null) {
            dateiMemoDbHelper.onCreate(database);
        }
        //dateiMemoDbHelper.onCreate(database);

        //dateiMemoDbHelper.onUpgrade(database,0,dateiMemoDbHelper.DB_VERSION);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        ForeignDataDbSource foreignDataDbSource = new ForeignDataDbSource();
        NeighborDbSource neighborDbSource = new NeighborDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();
        Node dateiMemo = new Node();
        PeerMemo peerMemo = new PeerMemo();
        ForeignData foreignData = new ForeignData();
        OwnDataMemo ownDataMemo = new OwnDataMemo();
        Neighbour neighborMemo = new Neighbour();


        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;

        Corner cornerBottomLeftNeighbour;
        Corner cornerBottomRightNeighbour;
        Corner cornerTopLeftNeighbour;
        Corner cornerTopRightNeighbour;
        Zone zoneNeighbour;

        try {
            cornerBottomLeft = new Corner(0.0, 0.0);
            cornerBottomRight = new Corner(1.0, 0.0);
            cornerTopLeft = new Corner(0.0, 1.0);
            cornerTopRight = new Corner(1.0, 1.0);

            //dateiMemoDbSource.deleteDateiMemo();


            zone = new Zone(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);

            dateiMemo.setUid(7872);
            dateiMemo.setTopRight(cornerTopRight);
            dateiMemo.setBottomLeft(cornerBottomLeft);
            dateiMemo.setTopLeft(cornerTopLeft);
            dateiMemo.setBottomRight(cornerBottomRight);
            dateiMemo.setPunktX(0.3);
            dateiMemo.setPunktY(0.4);
            dateiMemo.setIP("227.0.0.0/8");
            dateiMemo.setCountPeers(2);
            dateiMemo.setMyZone(zone);
            dateiMemoDbSource.createDateiMemo(dateiMemo);

            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid());
            Log.d("TEST", "DATEIMEMO_TOPRIGHT " + dateiMemoDbSource.getCornerTopRightX() + ", " + dateiMemoDbSource.getCornerTopRightY());
            Log.d("TEST", "DATEIMEMO_BOTTOMRIGHT " + dateiMemoDbSource.getCornerBottomRightX() + ", " + dateiMemoDbSource.getCornerBottomRightY());
            Log.d("TEST", "DATEIMEMO_TOPLEFT " + dateiMemoDbSource.getCornerTopLeftX() + ", " + dateiMemoDbSource.getCornerTopLeftY());
            Log.d("TEST", "DATEIMEMO_BOTTOMLEFT " + dateiMemoDbSource.getCornerBottomLeftX() + ", " + dateiMemoDbSource.getCornerBottomLeftY());
            Log.d("TEST", "DATEIMEMO_PUNKTX " + dateiMemoDbSource.getPunktX());
            Log.d("TEST", "DATEIMEMO_PUNKTY " + dateiMemoDbSource.getPunktY());
            Log.d("TEST", "DATEIMEMO_IP " + dateiMemoDbSource.getIp(dateiMemo.getUid()));
            Log.d("TEST", "DATEIMEMO_COUNTPEERS " + dateiMemoDbSource.getCountPeers());

            Log.d("Test", "UPDATE PUNKT X und Y");

            dateiMemoDbSource.updatePunktX(0.4);
            dateiMemoDbSource.updatePunktY(0.5);

            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid());
            Log.d("TEST", "DATEIMEMO_TOPRIGHT " + dateiMemoDbSource.getCornerTopRightX() + ", " + dateiMemoDbSource.getCornerTopRightY());
            Log.d("TEST", "DATEIMEMO_BOTTOMRIGHT " + dateiMemoDbSource.getCornerBottomRightX() + ", " + dateiMemoDbSource.getCornerBottomRightY());
            Log.d("TEST", "DATEIMEMO_TOPLEFT " + dateiMemoDbSource.getCornerTopLeftX() + ", " + dateiMemoDbSource.getCornerTopLeftY());
            Log.d("TEST", "DATEIMEMO_BOTTOMLEFT " + dateiMemoDbSource.getCornerBottomLeftX() + ", " + dateiMemoDbSource.getCornerBottomLeftY());
            Log.d("TEST", "DATEIMEMO_PUNKTX " + dateiMemoDbSource.getPunktX());
            Log.d("TEST", "DATEIMEMO_PUNKTY " + dateiMemoDbSource.getPunktY());
            Log.d("TEST", "DATEIMEMO_IP " + dateiMemoDbSource.getIp(dateiMemo.getUid()));
            Log.d("TEST", "DATEIMEMO_COUNTPEERS " + dateiMemoDbSource.getCountPeers());

            assertEquals(7872, dateiMemoDbSource.getUid(), 0);
            assertEquals(0.4, dateiMemoDbSource.getPunktX(), 0);
            assertEquals(0.5, dateiMemoDbSource.getPunktY(), 0);

            //create neighbour
            //foreign key
            neighborMemo.setUid(dateiMemo.getUid());
            //neighborMemo.setChecked(true);
            neighborMemo.setCornerTopRightX(0.5);
            neighborMemo.setCornerTopRightY(0.6);
            neighborMemo.setCornerTopLeftX(0.2);
            neighborMemo.setCornerTopLeftY(0.2);
            neighborMemo.setCornerBottomLeftX(0.4);
            neighborMemo.setCornerBottomLeftY(0.6);
            neighborMemo.setCornerBottomRightX(0.5);
            neighborMemo.setCornerBottomRightY(0.8);
            neighborMemo.setPunktX(0.2);
            neighborMemo.setPunktY(0.4);
            neighborMemo.setUIP("277.0.0.0/8");
            neighborMemo.setRTT(25.89);
            //neighborMemo.setNeighbour_id(2);
            neighborDbSource.createNeighborMemo(neighborMemo);

            Log.d("TEST", "NEIGHBOUR_ID " + neighborDbSource.getNID(1));
            Log.d("TEST", "NEIGHBOUR_TOPRIGHT " + neighborDbSource.getCornerTopRightXNeighbor(1) + ", " + neighborDbSource.getCornerTopRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMRIGHT " + neighborDbSource.getCornerBottomRightXNeighbor(1) + ", " + neighborDbSource.getCornerBottomRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_TOPLEFT " + neighborDbSource.getCornerTopLeftXNeighbor(1) + ", " + neighborDbSource.getCornerTopLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMLEFT " + neighborDbSource.getCornerBottomLeftXNeighbor(1) + ", " + neighborDbSource.getCornerBottomLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_X " + neighborDbSource.getPunktXNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_Y " + neighborDbSource.getPunktYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_IP " + neighborDbSource.getUip(1));

            Log.d("TEST", "================================================================");
            Log.d("TEST", "UPDATE CORNER");
            Log.d("TEST", "================================================================");

            neighborDbSource.updateCornerBottomLeftXNeighbor(1, 0.5);
            neighborDbSource.updateCornerTopRightXNeighbor(1, 0.6);
            neighborDbSource.updateCornerBottomRightYNeighbor(1, 0.3);
            neighborDbSource.updateCornerTopLeftYNeighbor(1, 0.3);

            Log.d("TEST", "NEIGHBOUR_ID " + neighborDbSource.getNID(1));
            Log.d("TEST", "NEIGHBOUR_TOPRIGHT " + neighborDbSource.getCornerTopRightXNeighbor(1) + ", " + neighborDbSource.getCornerTopRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMRIGHT " + neighborDbSource.getCornerBottomRightXNeighbor(1) + ", " + neighborDbSource.getCornerBottomRightYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_TOPLEFT " + neighborDbSource.getCornerTopLeftXNeighbor(1) + ", " + neighborDbSource.getCornerTopLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_BOTTOMLEFT " + neighborDbSource.getCornerBottomLeftXNeighbor(1) + ", " + neighborDbSource.getCornerBottomLeftYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_X " + neighborDbSource.getPunktXNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_PUNKT_Y " + neighborDbSource.getPunktYNeighbor(1));
            Log.d("TEST", "NEIGHBOUR_IP " + neighborDbSource.getUip(1));

            assertEquals(0.5, neighborDbSource.getCornerBottomLeftXNeighbor(1), 0);
            assertEquals(0.6, neighborDbSource.getCornerTopRightXNeighbor(1), 0);
            assertEquals(0.3, neighborDbSource.getCornerBottomRightYNeighbor(1), 0);
            assertEquals(0.3, neighborDbSource.getCornerTopLeftYNeighbor(1), 0);


        } catch (XMustBeLargerThanZeroException xMBLTZE) {
            Log.d("", "111111");
        } catch (YMustBeLargerThanZeroException yMBLTZE) {
            Log.d("", "222222");
        } catch (Exception e) {
            Log.d("", "333333 " + e.getMessage());
        }
    }


    @Test
    public void testSplitt_Vertical() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);


        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        dateiMemoDbHelper.onUpgrade(database, 0, dateiMemoDbHelper.DB_VERSION);

        Node node1;
        Node node2;
        Node node3;
        Node node4;

        Neighbour neighbour1;
        Neighbour neighbour2;
        Neighbour neighbour3;
        Neighbour neighbour4;

        DateiMemoDbSource dateiMemoDbSource1 = new DateiMemoDbSource();
        DateiMemoDbSource dateiMemoDbSource2 = new DateiMemoDbSource();
        DateiMemoDbSource dateiMemoDbSource3 = new DateiMemoDbSource();
        DateiMemoDbSource dateiMemoDbSource4 = new DateiMemoDbSource();

        NeighborDbSource neighborDbSource1 = new NeighborDbSource();
        NeighborDbSource neighborDbSource2 = new NeighborDbSource();
        NeighborDbSource neighborDbSource3 = new NeighborDbSource();
        NeighborDbSource neighborDbSource4 = new NeighborDbSource();

        try {
            Corner topRight = new Corner(1.0, 1.0);
            Corner topLeft = new Corner(0.0, 1.0);
            Corner bottomRight = new Corner(1.0, 0.0);
            Corner bottomLeft = new Corner(0.0, 0.0);

            // Man benötigt für jeden Node eine eigene Zone,
            // damit die Aufrufe zum updaten der Corner nicht Auswirkungen auf die Corner der anderen Nodes hat
            Zone zone = new Zone(topLeft, topRight, bottomLeft, bottomRight);

            node1 = new Node(1, 0.1, 0.2, "1.1.1.1", 3, zone);
            node2 = new Node(2, 0.9, 0.8, "1.1.1.2", 3, zone);
            node3 = new Node(3, 0.4, 0.3, "1.1.1.3", 3, zone);
            node4 = new Node(4, 0.6, 0.1, "1.1.1.4", 3, zone);

            neighbour1 = new Neighbour(node1.getUid(), node1.getPunktX(), node1.getPunktY(), node1.getIP(), node1.getMyZone(), 1);
            neighbour2 = new Neighbour(node2.getUid(), node2.getPunktX(), node2.getPunktY(), node2.getIP(), node2.getMyZone(), 1);
            neighbour3 = new Neighbour(node3.getUid(), node3.getPunktX(), node3.getPunktY(), node3.getIP(), node3.getMyZone(), 1);
            neighbour4 = new Neighbour(node4.getUid(), node4.getPunktX(), node4.getPunktY(), node4.getIP(), node4.getMyZone(), 1);


            dateiMemoDbSource1.createDateiMemo(node1);
            dateiMemoDbSource2.createDateiMemo(node2);
            dateiMemoDbSource3.createDateiMemo(node3);
            dateiMemoDbSource4.createDateiMemo(node4);


            zone.split(node1, node2, node3, node4);

            /*neighborDbSource1.createNeighborMemo(neighbour1);
            neighborDbSource2.createNeighborMemo(neighbour2);
            neighborDbSource3.createNeighborMemo(neighbour3);
            neighborDbSource3.createNeighborMemo(neighbour4);*/

            dateiMemoDbSource1.updateCornerBottomRightY(node1.getMyZone().getBottomRight().getY());
            dateiMemoDbSource1.updateCornerBottomRightX(node1.getMyZone().getBottomRight().getX());
            dateiMemoDbSource1.updateCornerBottomLeftX(node1.getMyZone().getBottomLeft().getX());
            dateiMemoDbSource1.updateCornerBottomLeftY(node1.getMyZone().getBottomLeft().getY());
            dateiMemoDbSource1.updateCornerTopRightX(node1.getMyZone().getTopRight().getX());
            dateiMemoDbSource1.updateCornerTopRightY(node1.getMyZone().getTopRight().getY());
            dateiMemoDbSource1.updateCornerTopLeftX(node1.getMyZone().getTopLeft().getX());
            dateiMemoDbSource1.updateCornerTopLeftY(node1.getMyZone().getTopLeft().getY());


            Log.d("TEST", "NEIGHBOR " + neighborDbSource1.getAllNeighborMemo());

            Log.d("TEST", "NODE1: " + dateiMemoDbSource1.getAllDateiMemos());
            Log.d("TEST", node1.getNeighbourList().toString());

        } catch (XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (YMustBeLargerThanZeroException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSplitt_Horizontal() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        Node node1;
        Node node2;
        Node node3;
        Node node4;

        try {
            Corner topRight = new Corner(1.0, 1.0);
            Corner topLeft = new Corner(0.0, 1.0);
            Corner bottomRight = new Corner(1.0, 0.0);
            Corner bottomLeft = new Corner(0.0, 0.0);

            // Man benötigt für jeden Node eine eigene Zone,
            // damit die Aufrufe zum updaten der Corner nicht Auswirkungen auf die Corner der anderen Nodes hat
            Zone zone = new Zone(topLeft, topRight, bottomLeft, bottomRight);

            node1 = new Node(1, 0.1, 0.2, "1.1.1.1", 3, zone);
            node2 = new Node(2, 0.9, 0.8, "1.1.1.2", 3, zone);
            node3 = new Node(3, 0.4, 0.3, "1.1.1.3", 3, zone);
            node4 = new Node(4, 0.6, 0.1, "1.1.1.4", 3, zone);


            zone.split(node1, node2, node3, node4);
            zone.split(node1, node2, node3, node4);

            Log.d("TEST", "NODE1: " + node1.getMyZone());
            Log.d("TEST", "NODE2: " + node2.getMyZone());
            Log.d("TEST", "NODE3: " + node3.getMyZone());
            Log.d("TEST", "NODE4: " + node4.getMyZone());

        } catch (XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (YMustBeLargerThanZeroException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_Node_checkIfInMyZone_True() {
        Node node = new Node();
        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.0, 0.0);
            cornerBottomRight = new Corner(1.0, 0.0);
            cornerTopLeft = new Corner(0.0, 1.0);
            cornerTopRight = new Corner(1.0, 1.0);
            zone = new Zone(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);

            System.out.print("IP: 192.111.23.4 = " + node.hashX("192.111.23.4") + ", " + node.hashY("192.111.23.4") + "\n");
            System.out.print("IP: 255.255.255.255 = " + node.hashX("255.255.255.255") + ", " + node.hashY("255.255.255.255") + "\n");
            System.out.print("IP: 180.1.23.123 = " + node.hashX("180.1.23.123") + ", " + node.hashY("180.1.23.123") + "\n");
            System.out.print("IP: 12.191.3.255 = " + node.hashX("12.191.3.255") + ", " + node.hashY("12.191.3.255") + "\n");
            System.out.print("IP: 1.111.223.34 = " + node.hashX("1.111.223.34") + ", " + node.hashY("1.111.223.34") + "\n");
            System.out.print("IP: 0.0.0.0 = " + node.hashX("0.0.0.0") + ", " + node.hashY("0.0.0.0") + "\n");
            System.out.print("IP: 78.31.3.129 = " + node.hashX("78.31.3.129") + ", " + node.hashY("78.31.3.129") + "\n");
            System.out.print("IP: 111.111.111.111 = " + node.hashX("111.111.111.111") + ", " + node.hashY("111.111.111.111") + "\n");
            System.out.print("IP: 222.222.222.222 = " + node.hashX("222.222.222.222") + ", " + node.hashY("222.222.222.222") + "\n");
            System.out.print("IP: 12.191.10.255 = " + node.hashX("12.191.10.255") + ", " + node.hashY("12.191.10.255") + "\n");
            System.out.print("IP: 12.191.11.255 = " + node.hashX("12.191.11.255") + ", " + node.hashY("12.191.11.255") + "\n");
            System.out.print("IP: 12.191.12.255 = " + node.hashX("12.191.12.255") + ", " + node.hashY("12.191.12.255") + "\n");
            System.out.print("IP: 12.255.255.255 = " + node.hashX("12.191.13.255") + ", " + node.hashY("12.191.13.255") + "\n");

            assertEquals(true, zone.checkIfInMyZone(node.hashX("192.111.23.4"), node.hashY("192.111.23.4")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("180.1.23.123"), node.hashY("180.1.23.123")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("12.191.3.255"), node.hashY("12.191.3.255")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("1.111.223.34"), node.hashY("1.111.223.34")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("255.255.255.255"), node.hashY("255.255.255.255")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("0.0.0.0"), node.hashY("0.0.0.0")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("78.31.3.129"), node.hashY("78.31.3.129")));

        } catch (XMustBeLargerThanZeroException xMBLTZE) {

        } catch (YMustBeLargerThanZeroException yMBLTZE) {

        } catch (Exception e) {

        }
    }

    @Test
    public void test_Node_checkIfInMyZone_False() {
        Node node = new Node();
        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.7, 0.3);
            cornerBottomRight = new Corner(0.8, 0.3);
            cornerTopLeft = new Corner(0.7, 0.8);
            cornerTopRight = new Corner(0.8, 0.8);
            zone = new Zone(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);

            System.out.print(node.hashX("192.111.23.4") + ", " + node.hashY("192.111.23.4") + "\n");
            System.out.print(node.hashX("255.255.255.255") + ", " + node.hashY("255.255.255.255") + "\n");
            System.out.print(node.hashX("0.0.0.0") + ", " + node.hashY("0.0.0.0") + "\n");
            System.out.print(node.hashX("78.31.3.129") + ", " + node.hashY("78.31.3.129") + "\n");
            assertEquals(false, zone.checkIfInMyZone(node.hashX("192.111.23.4"), node.hashY("192.111.23.4")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("255.255.255.255"), node.hashY("255.255.255.255")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("0.0.0.0"), node.hashY("0.0.0.0")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("78.31.3.129"), node.hashY("78.31.3.129")));

        } catch (XMustBeLargerThanZeroException xMBLTZE) {

        } catch (YMustBeLargerThanZeroException yMBLTZE) {

        } catch (Exception e) {

        }
    }

    @Test
    public void test_ComputeDistance() {
        Node node = new Node();
        double x = node.hashX("192.111.23.4");
        double y = node.hashY("192.111.23.4");
        System.out.println(node.computeDistance(node.hashX("12.191.25.255"), node.hashY("12.191.25.255"), node.hashX("12.191.10.255"), node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.9.255"), node.hashY("12.191.9.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("1.1.1.100"), node.hashY("1.1.1.100")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.10.255"), node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.11.255"), node.hashY("12.191.11.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.12.255"), node.hashY("12.191.12.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.13.255"), node.hashY("12.191.13.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("0.0.0.0"), node.hashY("0.0.0.0")));
        System.out.println(node.computeDistance(x, y, node.hashX("255.255.255.255"), node.hashY("255.255.255.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("78.31.3.129"), node.hashY("78.31.3.129")));
    }

    @Test
    public void test_CompareValues() {
        Node node = new Node();
        double x = node.hashX("192.111.23.4");
        double y = node.hashY("192.111.23.4");
        double dis[] = new double[4];
        dis[0] = node.computeDistance(x, y, node.hashX("12.191.10.255"), node.hashY("12.191.10.255"));
        dis[1] = node.computeDistance(x, y, node.hashX("12.191.11.255"), node.hashY("12.191.11.255"));
        dis[2] = node.computeDistance(x, y, node.hashX("12.191.12.255"), node.hashY("12.191.12.255"));
        dis[3] = node.computeDistance(x, y, node.hashX("12.191.13.255"), node.hashY("12.191.13.255"));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.10.255"), node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.11.255"), node.hashY("12.191.11.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.12.255"), node.hashY("12.191.12.255")));
        System.out.println(node.computeDistance(x, y, node.hashX("12.191.13.255"), node.hashY("12.191.13.255")));
        System.out.println(node.compareValues(dis));
        assertEquals(1, node.compareValues(dis));
    }

    @Test
    public void test_UpdateNeighbourAndPeer() throws YMustBeLargerThanZeroException, XMustBeLargerThanZeroException, IOException {
        List<Neighbour> neighbourList = new ArrayList<>();
        List<PeerMemo> peerMemoList = new ArrayList<>();
        String ip = "192.168.2.115";

        Corner cornerBottomLeft1 = new Corner(0.0, 0.0);
        Corner cornerBottomRight1 = new Corner(0.5, 0.0);
        Corner cornerTopLeft1 = new Corner(0.0, 1.0);
        Corner cornerTopRight1 = new Corner(0.5, 1.0);

        Corner cornerBottomLeft2 = new Corner(0.5, 0.0);
        Corner cornerBottomRight2 = new Corner(1.0, 0.0);
        Corner cornerTopLeft2 = new Corner(0.5, 1.0);
        Corner cornerTopRight2 = new Corner(1.0, 1.0);

        Zone zone = new Zone(cornerTopLeft1, cornerTopRight1, cornerBottomLeft1, cornerBottomRight1);
        Zone neighbourZone = new Zone(cornerTopLeft2, cornerTopRight2, cornerBottomLeft2, cornerBottomRight2);
        Neighbour n = new Neighbour(03, 0.4, 0.5, ip, zone, 0.5);
        Neighbour n1 = new Neighbour(03, 0.5, 0.5, ip, neighbourZone, 0.5);
        PeerMemo p = new PeerMemo(03, 93, ip);
        neighbourList.add(n);
        neighbourList.add(n1);
        peerMemoList.add(p);


        Node node = new Node(01, 0.5, 0.5, ip, 2, zone);
        node.setNeighbourList(neighbourList);
        node.setPeerMemoList(peerMemoList);
        RoutHelper rh = new RoutHelper(ip, 0.3, 0.5, 02);

        Node neuerNode = node.routing(rh);
        System.out.println(neuerNode.toString());

    }

    private final static int PORTNR = 9797;


    private Socket socket;
    private Client client = new Client();
    private DBUtil dbu = new DBUtil();
    private DateiMemoDbSource ownDb = new DateiMemoDbSource();
    private NeighborDbSource nDB = new NeighborDbSource();
    private PeerDbSource pDB = new PeerDbSource();
    RoutHelper rh;

    @Test
    public void testUpdate() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        Corner cornerBottomLeft2 = null;
        Corner cornerBottomRight2 = null;
        Corner cornerTopLeft2 = null;
        Corner cornerTopRight2 = null;
        try {
            cornerBottomLeft2 = new Corner(0.5, 0.0);
            cornerBottomRight2 = new Corner(1.0, 0.0);
            cornerTopLeft2 = new Corner(0.5, 1.0);
            cornerTopRight2 = new Corner(1.0, 1.0);
        } catch (XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (YMustBeLargerThanZeroException e) {
            e.printStackTrace();
        }
        Zone neighbourZone = new Zone(cornerTopLeft2, cornerTopRight2, cornerBottomLeft2, cornerBottomRight2);

        Node node1;
        node1 = new Node(1, 0.1, 0.2, "1.1.1.1", 2, neighbourZone);

        String ip = "1.1.1.5";
        double x = 0.5;
        double y = 0.3;
        int id = 1;
        rh = new RoutHelper(ip, x, y, id);

        /*ownDb.createDateiMemo(node1);*/

        Neighbour n1 = new Neighbour(1, 0.5, 0.5, ip, neighbourZone, 0.5);

        /*nDB.createNeighborMemo(n1);
        nDB.createNeighborMemo(n1);
        nDB.createNeighborMemo(n1);
        nDB.createNeighborMemo(n1);*/

        Log.d("TEST", " " + nDB.getAllNeighborMemo().toString());

        Log.d("TEST", " " + determineRoutingDestination(rh));

        //Log.d("TEST"," " +routingCheckZone(rh));

    }

    private double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
    }

    private int determineRoutingDestination(RoutHelper rh) {
        double[] distance = new double[14];
        for (int i = 0; i < nDB.getCount(); i++) {
            distance[i] = computeDistance(rh.getX(), rh.getY(), nDB.getPunktXNeighbor(i + 1),
                    nDB.getPunktYNeighbor(i + 1));
        }
        int index = compareValues(distance);

        return index;
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
                    //client.sendListAsByteArray(socket, (ArrayList) pDB.getAllPeer());
                    //client.sendListAsByteArrayNeighbour(socket, (ArrayList) nDB.getAllNeighborMemo());
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

    @Test
    public void testEigensBildbekommen() {

        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        if (database == null) {
            dateiMemoDbHelper.onCreate(database);
        }

        dateiMemoDbHelper.onUpgrade(database, 0, dateiMemoDbHelper.DB_VERSION);

        OwnDataMemo ownDataMemo = new OwnDataMemo();
        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();
        Node dateiMemo = new Node();
        ForeignData foreignData = new ForeignData();
        ForeignDataDbSource foreignDataDbSource = new ForeignDataDbSource();

        Corner cornerBottomLeft = null;
        Corner cornerBottomRight = null;
        Corner cornerTopLeft = null;
        Corner cornerTopRight = null;
        Zone zone;

        try {
            cornerBottomLeft = new Corner(0.0, 0.0);
            cornerBottomRight = new Corner(1.0, 0.0);
            cornerTopLeft = new Corner(0.0, 1.0);
            cornerTopRight = new Corner(1.0, 1.0);
        } catch (XMustBeLargerThanZeroException e) {
            e.printStackTrace();
        } catch (YMustBeLargerThanZeroException e) {
            e.printStackTrace();
        }


        //dateiMemoDbSource.deleteDateiMemo();


        zone = new Zone(cornerTopLeft, cornerTopRight, cornerBottomLeft, cornerBottomRight);

        dateiMemo.setUid(7872);
        dateiMemo.setTopRight(cornerTopRight);
        dateiMemo.setBottomLeft(cornerBottomLeft);
        dateiMemo.setTopLeft(cornerTopLeft);
        dateiMemo.setBottomRight(cornerBottomRight);
        dateiMemo.setPunktX(0.3);
        dateiMemo.setPunktY(0.4);
        dateiMemo.setIP("227.0.0.0/8");
        dateiMemo.setCountPeers(2);
        dateiMemo.setMyZone(zone);
        dateiMemoDbSource.createDateiMemo(dateiMemo);

        ownDataMemo.setFileId(3);
        ownDataMemo.setUid(dateiMemoDbSource.getUid());
        ownDataDbSource.createOwnData(ownDataMemo);


        dateiMemo.setUid(1);
        dateiMemo.setTopRight(cornerTopRight);
        dateiMemo.setBottomLeft(cornerBottomLeft);
        dateiMemo.setTopLeft(cornerTopLeft);
        dateiMemo.setBottomRight(cornerBottomRight);
        dateiMemo.setPunktX(0.3);
        dateiMemo.setPunktY(0.4);
        dateiMemo.setIP("227.0.0.0/8");
        dateiMemo.setCountPeers(2);
        dateiMemo.setMyZone(zone);
        //dateiMemoDbSource.createDateiMemo(dateiMemo);

        foreignData.setFotoId(2);
        foreignData.setUid(dateiMemoDbSource.getUid());
        foreignData.setForeignIp(dateiMemoDbSource.getIp(dateiMemoDbSource.getUid()));
        foreignDataDbSource.createForeignData(foreignData);

        foreignData.setUid(dateiMemoDbSource.getUid());
        foreignData.setFotoId(5);
        foreignData.setForeignIp(dateiMemoDbSource.getIp(dateiMemoDbSource.getUid()));
        foreignDataDbSource.createForeignData(foreignData);


        for (int i = 1; i <= foreignDataDbSource.getAllForeignData().size();i++)
        getImage(i, foreignDataDbSource);
    }


        private String getFile(int uid, ForeignDataDbSource foreignDataDb)
        {
            String foto = "";
            Log.d("TEST",""+ uid );
            if (uid == foreignDataDb.getUidForeign()) {
                foto = foreignDataDb.getFotoId(foreignDataDb.getUidForeign()) + ".jpg";
                Log.d("TEST", "FOTO " + foto);
            }
            return foto;
        }

    public File getImage(int uid, ForeignDataDbSource foreignDataDb)
    {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator+"images"+ File.separator+"CAN_PICS"+ File.separator + getFile(uid,foreignDataDb));
        return file;
    }
  /*  @Test
    public void testSendIPAddress() throws IOException {
        Client client = new Client();
        try{
            client.sendeAlles("127.0.0.1","hashX","192.101.101.1",0.3,0.88766,2);

        }catch(UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void receivingServer() throws IOException {
        Server server = new Server();
        server.start();

    }
*/

    }
//    @Test
//    public void TestUpdateCorner() {
//
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
//        DatabaseManager.initializeInstance(dateiMemoDbHelper);
//
//        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
//        PeerDbSource peerDbSource = new PeerDbSource();
//
//        Corner cornerBottomLeft;
//        Corner cornerBottomRight;
//        Corner cornerTopLeft;
//        Corner cornerTopRight;
//        Zone zone;
//        try {
//            cornerBottomLeft = new Corner(0.0,0.0);
//            cornerBottomRight = new Corner(1.0,0.0);
//            cornerTopLeft = new Corner(0.0,1.0);
//            cornerTopRight = new Corner(1.0,1.0);
//
//        Node dateiMemo = new Node();
//        dateiMemo.setUid(7872);
//        //dateiMemo.setChecked(true);
//        dateiMemo.setTopRight(cornerTopRight);
//        dateiMemo.setBottomLeft(cornerBottomLeft);
//        dateiMemo.setTopLeft(cornerTopLeft);
//        dateiMemo.setBottomRight(cornerBottomRight);
//        dateiMemo.setPunktX(0.3);
//        dateiMemo.setPunktY(0.4);
//        dateiMemo.setIP("227.0.0.0/8");
//        dateiMemo.setCountPeers(2);
//        dateiMemoDbSource.createDateiMemo(dateiMemo);
//
//        dateiMemoDbSource.updateCornerBottomLeftX(0.5);
//        dateiMemoDbSource.updateCornerBottomLeftY(0.8);
//        dateiMemoDbSource.updateCornerBottomRightX(0.6);
//        dateiMemoDbSource.updateCornerBottomRightY(0.4);
//        dateiMemoDbSource.updateCornerTopLeftX(0.2);
//        dateiMemoDbSource.updateCornerTopLeftY(0.5);
//        dateiMemoDbSource.updateCornerTopRightX(0.9);
//        dateiMemoDbSource.updateCornerTopRightY(0.1);
//
//
//            PeerMemo peerMemo = new PeerMemo();
//            peerMemo.setPeerIp(dateiMemo.getIP());
//            peerMemo.setUid(dateiMemo.getUid());
//            //peerMemo.setPeerId(3);
//            peerDbSource.createPeerMemo(peerMemo);
//
//            Log.d("TEST", peerDbSource.getAllPeer().toString());
//
//        assertEquals(0.1,dateiMemoDbSource.getCornerTopRightY(),0);
//        assertEquals(0.9,dateiMemoDbSource.getCornerTopRightX(),0);
//        assertEquals(0.5,dateiMemoDbSource.getCornerTopLeftY(),0);
//        assertEquals(0.2,dateiMemoDbSource.getCornerTopLeftX(),0);
//        assertEquals(0.4,dateiMemoDbSource.getCornerBottomRightY(),0);
//        assertEquals(0.6,dateiMemoDbSource.getCornerBottomRightX(),0);
//        assertEquals(0.8, dateiMemoDbSource.getCornerBottomLeftY(),0);
//        assertEquals(0.5, dateiMemoDbSource.getCornerBottomLeftX(), 0);
//
//            assertEquals(peerDbSource.getPeerIp(peerDbSource.getUidPeer()),"227.1.0.0/8");
//
//        }
//        catch(XMustBeLargerThanZeroException xMBLTZE)
//        {
//
//        }catch(YMustBeLargerThanZeroException yMBLTZE)
//        {
//
//        }catch( Exception e)
//        {
//            Log.d("EXCEPTION", e.getMessage());
//        }
//
//    }
//
//    @Test
//    public void testSplit_Vertical()
//    {
//
//       /* Context appContext = InstrumentationRegistry.getTargetContext();
//        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
//        DatabaseManager.initializeInstance(dateiMemoDbHelper);
//
//        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
//*/
//
//        Corner cornerBottomLeft;
//        Corner cornerBottomRight;
//        Corner cornerTopLeft;
//        Corner cornerTopRight;
//        Zone zone;
//        try {
//            cornerBottomLeft = new Corner(0.0,0.0);
//            cornerBottomRight = new Corner(1.0,0.0);
//            cornerTopLeft = new Corner(0.0,1.0);
//            cornerTopRight = new Corner(1.0,1.0);
//
//            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);
//
//
//            /*Node node1 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node2 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node3 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node4 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);*/
//
//            Node node1 = new Node(1,0.1,0.4,"1.1.1.1" ,3, zone);
//            Node node2 = new Node(1,0.9,0.8,"1.2.1.1" ,3, zone);
//            Node node3 = new Node(1,0.9,0.9,"1.3.1.1" ,3, zone);
//            Node node4 = new Node(1,0.1,0.1,"1.4.1.1" ,3, zone);
//
//            /*DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
//            dateiMemoDbSource.createDateiMemo(node1);
//            dateiMemoDbSource.createDateiMemo(node2);
//            dateiMemoDbSource.createDateiMemo(node3);
//            dateiMemoDbSource.createDateiMemo(node4);*/
//
//            zone.split(node1,node2,node3,node4);
//
//           /* Log.d("TEST", "NODE1: " + node1.getMyZone().toString() + "BottomRightX: " + node1.getBottomRight().getX());
//            Log.d("TEST", "NODE2: " + node2.getMyZone().toString() + "BottomRightX: " + node2.getBottomRight().getX());
//            Log.d("TEST", "NODE3: " + node3.getMyZone().toString() + "BottomRightX: " + node3.getBottomRight().getX());
//            Log.d("TEST", "NODE4: " + node4.getMyZone().toString() + "BottomRightX: " + node4.getBottomRight().getX());*/
//
//            assertEquals(0.5, node1.getBottomRight().getX(), 0);
//            assertEquals(0.5, node1.getTopRight().getX() ,0);
//            assertEquals(0.5, node2.getBottomRight().getX(), 0);
//            assertEquals(0.5, node2.getTopRight().getX() ,0);
//            assertEquals(0.5, node3.getBottomLeft().getX(), 0);
//            assertEquals(0.5, node3.getTopLeft().getX() ,0);
//            assertEquals(0.5, node4.getBottomLeft().getX(), 0);
//            assertEquals(0.5, node4.getTopLeft().getX() ,0);
//        }
//        catch(XMustBeLargerThanZeroException xMBLTZE)
//        {
//
//        }catch(YMustBeLargerThanZeroException yMBLTZE)
//        {
//
//        }catch( Exception e)
//        {
//            Log.d("Exception", e.getMessage());
//        }
//
//    }
//
//    @Test
//    public void testSplit_Horizontal()
//    {
//        /*Context appContext = InstrumentationRegistry.getTargetContext();
//        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
//        DatabaseManager.initializeInstance(dateiMemoDbHelper);
//
//        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();*/
//
//
//        Corner cornerBottomLeft;
//        Corner cornerBottomRight;
//        Corner cornerTopLeft;
//        Corner cornerTopRight;
//        Zone zone;
//        try {
//            cornerBottomLeft = new Corner(0.0,0.0);
//            cornerBottomRight = new Corner(1.0,0.0);
//            cornerTopLeft = new Corner(0.0,1.0);
//            cornerTopRight = new Corner(1.0,1.0);
//            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);
//
//            /*Node node1 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node2 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node3 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
//            Node node4 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);*/
//
//            Node node1 = new Node(1,0.3,0.4,"1.1.1.1" ,3, zone);
//            Node node2 = new Node(1,0.6,0.8,"1.2.1.1" ,3, zone);
//            Node node3 = new Node(1,0.7,0.3,"1.3.1.1" ,3, zone);
//            Node node4 = new Node(1,0.1,0.7,"1.4.1.1" ,3, zone);
//
//            /*dateiMemoDbSource.createDateiMemo(node1);
//            dateiMemoDbSource.createDateiMemo(node2);
//            dateiMemoDbSource.createDateiMemo(node3);
//            dateiMemoDbSource.createDateiMemo(node4);*/
//
//            zone.split(node1,node2,node3,node4);
//            zone.split(node1,node2,node3,node4);
//
//            Log.d("TEST", "NODE1: " + node1.getMyZone().toString());
//            Log.d("TEST", "NODE2: " + node2.getMyZone().toString());
//            Log.d("TEST", "NODE3: " + node3.getMyZone().toString());
//            Log.d("TEST", "NODE4: " + node4.getMyZone().toString());
//
//            assertEquals(0.5, node1.getTopLeft().getY(), 0);
//            assertEquals(0.5, node1.getTopRight().getY() ,0);
//            assertEquals(0.5, node2.getBottomRight().getY(), 0);
//            assertEquals(0.5, node2.getBottomLeft().getY() ,0);
//            assertEquals(0.5, node3.getTopRight().getY(), 0);
//            assertEquals(0.5, node3.getTopLeft().getY() ,0);
//            assertEquals(0.5, node4.getBottomLeft().getY(), 0);
//            assertEquals(0.5, node4.getBottomRight().getY() ,0);
//        }
//        catch(XMustBeLargerThanZeroException xMBLTZE)
//        {
//
//        }catch(YMustBeLargerThanZeroException yMBLTZE)
//        {
//
//        }catch( Exception e)
//        {
//            Log.d("Exception", e.getMessage());
//        }
//
//    }
