package com.example.somaro.loginregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import app.App;
import exception.*;
import model.*;
import source.*;

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
    public void test()
    {

        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        if(database == null)
        {
            dateiMemoDbHelper.onCreate(database);
        }
        //dateiMemoDbHelper.onCreate(database);

        dateiMemoDbHelper.onUpgrade(database,0,dateiMemoDbHelper.DB_VERSION);

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
            cornerBottomLeft = new Corner(0.0,0.0);
            cornerBottomRight = new Corner(1.0,0.0);
            cornerTopLeft = new Corner(0.0,1.0);
            cornerTopRight = new Corner(1.0,1.0);

            //dateiMemoDbSource.deleteDateiMemo();


            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);

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



            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid() );
            Log.d("TEST", "DATEIMEMO_TOPRIGHT " + dateiMemoDbSource.getCornerTopRightX() + ", " + dateiMemoDbSource.getCornerTopRightY());
            Log.d("TEST", "DATEIMEMO_BOTTOMRIGHT " + dateiMemoDbSource.getCornerBottomRightX() + ", " + dateiMemoDbSource.getCornerBottomRightY());
            Log.d("TEST", "DATEIMEMO_TOPLEFT " + dateiMemoDbSource.getCornerTopLeftX() + ", " + dateiMemoDbSource.getCornerTopLeftY());
            Log.d("TEST", "DATEIMEMO_BOTTOMLEFT " + dateiMemoDbSource.getCornerBottomLeftX() + ", " + dateiMemoDbSource.getCornerBottomLeftY());
            Log.d("TEST", "DATEIMEMO_PUNKTX " + dateiMemoDbSource.getPunktX());
            Log.d("TEST", "DATEIMEMO_PUNKTY " + dateiMemoDbSource.getPunktY());
            Log.d("TEST", "DATEIMEMO_IP " + dateiMemoDbSource.getIp(dateiMemo.getUid()));
            Log.d("TEST", "DATEIMEMO_COUNTPEERS " + dateiMemoDbSource.getCountPeers());
            //Log.d("TEST", "DATEIMEMO_ZONE " + dateiMemoDbSource.getZone());


            //assertEquals(7872, dateiMemoDbSource.getUid());

            //assertEquals(7872, dateiMemoDbSource.getUid());

            assertEquals(0.3, dateiMemoDbSource.getPunktX(), 0);
            assertEquals(0.4, dateiMemoDbSource.getPunktY(), 0);

            //create Peer
            PeerMemo peerMemo1 = new PeerMemo();
            peerMemo1.setPeerIp("1.1.1.1");
            peerMemo1.setUid(dateiMemoDbSource.getUid());
            //peerMemo.setPeerId(3);
            peerDbSource.createPeerMemo(peerMemo1);

            peerMemo.setPeerIp("1.1.1.0");
            peerMemo.setUid(dateiMemoDbSource.getUid());
            peerDbSource.createPeerMemo(peerMemo);

            //getEachPeer-Test
            //
            List<PeerMemo> peerMemoList= peerDbSource.getEachPeer(0);
            Log.d("Test getEachPeer","=============================================================");

            for(int i= 0; i < peerMemoList.size(); i++){
                String output = "Node_Peer_ID: "+ peerMemoList.get(i).getUid() +
                        //"\n Status: "+ peerMemoList.get(i).isChecked() +
                        "\nPeer ID: "+ peerMemoList.get(i).getPeerId() +
                        "\n IP: "+ peerMemoList.get(i).getPeerIp();

                Log.d("Result", output);
            }
            Log.d("Test getEachPeer","=============================================================");

            //getAllPeer-Test
            //
            List<PeerMemo> peerMemoListAll= peerDbSource.getAllPeer();
            Log.d("Test getAllPeer","=============================================================");

            for(int i= 0; i < peerMemoList.size(); i++){
                String output = "Node_Peer_ID: "+ peerMemoListAll.get(i).getUid() +
                        //"\n Status: "+ peerMemoList.get(i).isChecked() +
                        "\nPeer ID: "+ peerMemoListAll.get(i).getPeerId() +
                        "\n IP: "+ peerMemoListAll.get(i).getPeerIp();

                Log.d("Result", output);
            }
            Log.d("Test getAllPeer","=============================================================");

            String  p = peerDbSource.getPeerIp(peerDbSource.getUidPeer());

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

            assertEquals(0.5,x,0);
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


            neighborDbSource.createNeighborMemo(n);

            //getEachNeighbor Test
            List<Neighbour> neighborMemoList= neighborDbSource.getEachNeighbour(1);
            Log.d("Test getEachNeighbor","=============================================================");
            //double cornerTopRightX1 = neighborMemoList.get(1).getCornerTopRightX();
           // Log.d("TEST", "REsult" + cornerTopRightX1);
            for (int i= 0; i < neighborMemoList.size(); i++) {
                String output = "Neighbor_ID: "+ neighborMemoList.get(i).getNeighbour_id() +
                        "\n Neighbor_ID_Foreign: "+ neighborMemoList.get(i).getUid() +
                        //"\n Status: "+ neighborMemoList.get(i).isChecked() +
                        "\n Corner Top Right X: "+ neighborMemoList.get(i).getCornerTopRightX() +
                        "\n Corner Top Right Y: "+ neighborMemoList.get(i).getCornerTopRightY() +
                        "\n Corner Top Left X: "+ neighborMemoList.get(i).getCornerTopLeftX() +
                        "\n Corner Top Left Y: "+ neighborMemoList.get(i).getCornerTopLeftY() +
                        "\n Corner Bottom Right X: "+ neighborMemoList.get(i).getCornerBottomRightX() +
                        "\n Corner Bottom Right Y: "+ neighborMemoList.get(i).getCornerBottomRightY() +
                        "\n Corner Bottom Left X: "+ neighborMemoList.get(i).getCornerBottomLeftX() +
                        "\n Corner Bottom Left Y: "+ neighborMemoList.get(i).getCornerBottomLeftY() +
                        "\n Punkt X: "+ neighborMemoList.get(i).getPunktX() +
                        "\n Punkt Y: "+ neighborMemoList.get(i).getPunktY() +
                        "\n IP: "+ neighborMemoList.get(i).getUIP() +
                        "\n RTT: "+ neighborMemoList.get(i).getRTT();

                Log.d("Result", output);
            }
            Log.d("Test getEachNeighbor","=============================================================");

            //getAllNeighbor test
            //
            List<Neighbour> neighbourMemoListAll= neighborDbSource.getAllNeighborMemo();
            Log.d("Test getAll","=============================================================");
//            double cornerTopRightX1All = neighbourMemoListAll.get(1).getCornerTopRightX();
//            double cornerTopRightX2All = neighbourMemoListAll.get(2).getCornerTopRightX();
//            Log.d("TEST", "BBBBBBBBB" + cornerTopRightX1All);
//            Log.d("TEST", "BBBBBBBBB" + cornerTopRightX2All);
            for (int i= 0; i < neighborMemoList.size(); i++) {
                String output = "Neighbor_ID: "+ neighbourMemoListAll.get(i).getNeighbour_id() +
                        "\n Neighbor_ID_Foreign: "+ neighbourMemoListAll.get(i).getUid() +
                        //"\n Status: "+ neighborMemoList.get(i).isChecked() +
                        "\n Corner Top Right X: "+ neighbourMemoListAll.get(i).getCornerTopRightX() +
                        "\n Corner Top Right Y: "+ neighbourMemoListAll.get(i).getCornerTopRightY() +
                        "\n Corner Top Left X: "+ neighbourMemoListAll.get(i).getCornerTopLeftX() +
                        "\n Corner Top Left Y: "+ neighbourMemoListAll.get(i).getCornerTopLeftY() +
                        "\n Corner Bottom Right X: "+ neighbourMemoListAll.get(i).getCornerBottomRightX() +
                        "\n Corner Bottom Right Y: "+ neighbourMemoListAll.get(i).getCornerBottomRightY() +
                        "\n Corner Bottom Left X: "+ neighbourMemoListAll.get(i).getCornerBottomLeftX() +
                        "\n Corner Bottom Left Y: "+ neighbourMemoListAll.get(i).getCornerBottomLeftY() +
                        "\n Punkt X: "+ neighbourMemoListAll.get(i).getPunktX() +
                        "\n Punkt Y: "+ neighbourMemoListAll.get(i).getPunktY() +
                        "\n IP: "+ neighbourMemoListAll.get(i).getUIP() +
                        "\n RTT: "+ neighbourMemoListAll.get(i).getRTT();

                Log.d("Result", output);
            }

            Log.d("Test getAll","=============================================================");


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
            assertEquals(0.5, neighborDbSource.getCornerTopRightXNeighbor(1), 0);
            assertEquals(0.6, neighborDbSource.getCornerTopRightYNeighbor(1), 0);

            //dateiMemoDbSource.deleteDateiMemo();
            //foreignData.deleteForeignData();
            //neighborDbSource.deleteNeighbormemo();
            //ownDataDbSource.deleteOwnData();
            //peerDbSource.deletePeerMemo();

        }
        catch(XMustBeLargerThanZeroException xMBLTZE)
        {
            Log.d("", "111111");
        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {
            Log.d("", "222222");
        }catch( Exception e)
        {
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

    @Test
    public void TestUpdateCorner() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        SQLiteDatabase database;
        database = DatabaseManager.getInstance().openDatabase();

        if(database == null)
        {
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

        try{
            cornerBottomLeft = new Corner(0.0,0.0);
            cornerBottomRight = new Corner(1.0,0.0);
            cornerTopLeft = new Corner(0.0,1.0);
            cornerTopRight = new Corner(1.0,1.0);

            //dateiMemoDbSource.deleteDateiMemo();


            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);

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

            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid() );
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

            Log.d("TEST", "DATEIMEMO_UID " + dateiMemoDbSource.getUid() );
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


        } catch(XMustBeLargerThanZeroException xMBLTZE)
        {
            Log.d("", "111111");
        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {
            Log.d("", "222222");
        }catch( Exception e)
        {
            Log.d("", "333333 " + e.getMessage());
        }
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
}
