package com.example.somaro.loginregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

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

        dateiMemoDbHelper.onUpgrade(database,0,1);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        ForeignDataDbSource foreignData = new ForeignDataDbSource();
        NeighborDbSource neighborDbSource = new NeighborDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();
        Node dateiMemo = new Node();
        PeerMemo peerMemo = new PeerMemo();


        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
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

            peerMemo.setPeerIp("1.1.1.1");
            peerMemo.setUid(dateiMemoDbSource.getUid());
            //peerMemo.setPeerId(3);
            peerDbSource.createPeerMemo(peerMemo);

            String  p = peerDbSource.getPeerIp(peerDbSource.getUidPeer());

            Log.d("HALLO", "AAAAAAAAAAAAAAAAAA: PEER_ID " + p);

            dateiMemoDbSource.deleteDateiMemo();
            //foreignData.deleteForeignData();
            //neighborDbSource.deleteNeighbormemo();
            //ownDataDbSource.deleteOwnData();
            peerDbSource.deletePeerMemo();

        }
        catch(XMustBeLargerThanZeroException xMBLTZE)
        {
            Log.d("", "111111");
        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {
            Log.d("", "222222");
        }catch( Exception e)
        {
            Log.d("", "333333 " + e.getMessage());
        }
        //foreign key
        peerMemo.setUid(dateiMemoDbSource.getUid());
        peerMemo.setPeerIp("277.0.0.1");
        peerDbSource.createPeerMemo(peerMemo);


        //dateiMemoDbSource.deleteDateiMemo();
        //foreignData.deleteForeignData();
        //neighborDbSource.deleteNeighbormemo();
        //ownDataDbSource.deleteOwnData();
        //peerDbSource.deletePeerMemo();

    }

    @Test
    public void TestUpdateCorner() {

        Context appContext = InstrumentationRegistry.getTargetContext();
        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();

        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.0,0.0);
            cornerBottomRight = new Corner(1.0,0.0);
            cornerTopLeft = new Corner(0.0,1.0);
            cornerTopRight = new Corner(1.0,1.0);

        Node dateiMemo = new Node();
        dateiMemo.setUid(7872);
        //dateiMemo.setChecked(true);
        dateiMemo.setTopRight(cornerTopRight);
        dateiMemo.setBottomLeft(cornerBottomLeft);
        dateiMemo.setTopLeft(cornerTopLeft);
        dateiMemo.setBottomRight(cornerBottomRight);
        dateiMemo.setPunktX(0.3);
        dateiMemo.setPunktY(0.4);
        dateiMemo.setIP("227.0.0.0/8");
        dateiMemo.setCountPeers(2);
        dateiMemoDbSource.createDateiMemo(dateiMemo);

        dateiMemoDbSource.updateCornerBottomLeftX(0.5);
        dateiMemoDbSource.updateCornerBottomLeftY(0.8);
        dateiMemoDbSource.updateCornerBottomRightX(0.6);
        dateiMemoDbSource.updateCornerBottomRightY(0.4);
        dateiMemoDbSource.updateCornerTopLeftX(0.2);
        dateiMemoDbSource.updateCornerTopLeftY(0.5);
        dateiMemoDbSource.updateCornerTopRightX(0.9);
        dateiMemoDbSource.updateCornerTopRightY(0.1);


            PeerMemo peerMemo = new PeerMemo();
            peerMemo.setPeerIp(dateiMemo.getIP());
            peerMemo.setUid(dateiMemo.getUid());
            //peerMemo.setPeerId(3);
            peerDbSource.createPeerMemo(peerMemo);


        assertEquals(0.1,dateiMemoDbSource.getCornerTopRightY(),0);
        assertEquals(0.9,dateiMemoDbSource.getCornerTopRightX(),0);
        assertEquals(0.5,dateiMemoDbSource.getCornerTopLeftY(),0);
        assertEquals(0.2,dateiMemoDbSource.getCornerTopLeftX(),0);
        assertEquals(0.4,dateiMemoDbSource.getCornerBottomRightY(),0);
        assertEquals(0.6,dateiMemoDbSource.getCornerBottomRightX(),0);
        assertEquals(0.8, dateiMemoDbSource.getCornerBottomLeftY(),0);
        assertEquals(0.5, dateiMemoDbSource.getCornerBottomLeftX(), 0);

            assertEquals(peerDbSource.getPeerIp(peerDbSource.getUidPeer()),"227.1.0.0/8");

        }
        catch(XMustBeLargerThanZeroException xMBLTZE)
        {

        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {

        }catch( Exception e)
        {

        }

    }

    @Test
    public void testSplit_Vertical()
    {

       /* Context appContext = InstrumentationRegistry.getTargetContext();
        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
*/

        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.0,0.0);
            cornerBottomRight = new Corner(1.0,0.0);
            cornerTopLeft = new Corner(0.0,1.0);
            cornerTopRight = new Corner(1.0,1.0);

            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);

            /*Node node1 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node2 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node3 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node4 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);*/

            Node node1 = new Node(1,0.3,0.4,"1.1.1.1" ,3, zone);
            Node node2 = new Node(1,0.6,0.8,"1.2.1.1" ,3, zone);
            Node node3 = new Node(1,0.7,0.3,"1.3.1.1" ,3, zone);
            Node node4 = new Node(1,0.1,0.7,"1.4.1.1" ,3, zone);

            DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
            dateiMemoDbSource.createDateiMemo(node1);
            dateiMemoDbSource.createDateiMemo(node2);
            dateiMemoDbSource.createDateiMemo(node3);
            dateiMemoDbSource.createDateiMemo(node4);

            zone.split(node1,node2,node3,node4);

            assertEquals(0.5, dateiMemoDbSource.getCornerBottomRightX(), 0);
            assertEquals(0.5, dateiMemoDbSource.getCornerTopRightX() ,0);
            assertEquals(0.5, node2.getBottomRight().getX(), 0);
            assertEquals(0.5, node2.getTopRight().getX() ,0);
            assertEquals(0.5, node3.getBottomLeft().getX(), 0);
            assertEquals(0.5, node3.getTopLeft().getX() ,0);
            assertEquals(0.5, node4.getBottomLeft().getX(), 0);
            assertEquals(0.5, node4.getTopLeft().getX() ,0);
        }
        catch(XMustBeLargerThanZeroException xMBLTZE)
        {

        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {

        }catch( Exception e)
        {

        }

    }

    @Test
    public void testSplit_Horizontal()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();


        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.0,0.0);
            cornerBottomRight = new Corner(1.0,0.0);
            cornerTopLeft = new Corner(0.0,1.0);
            cornerTopRight = new Corner(1.0,1.0);
            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);

            /*Node node1 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node2 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node3 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);
            Node node4 = new Node(cornerBottomLeft,cornerBottomRight,cornerTopLeft,cornerTopRight);*/

            Node node1 = new Node(1,0.3,0.4,"1.1.1.1" ,3, zone);
            Node node2 = new Node(1,0.6,0.8,"1.2.1.1" ,3, zone);
            Node node3 = new Node(1,0.7,0.3,"1.3.1.1" ,3, zone);
            Node node4 = new Node(1,0.1,0.7,"1.4.1.1" ,3, zone);

            dateiMemoDbSource.createDateiMemo(node1);
            dateiMemoDbSource.createDateiMemo(node2);
            dateiMemoDbSource.createDateiMemo(node3);
            dateiMemoDbSource.createDateiMemo(node4);

            zone.split(node1,node2,node3,node4);
            zone.split(node1,node2,node3,node4);

            assertEquals(0.5, node1.getTopLeft().getY(), 0);
            assertEquals(0.5, node1.getTopRight().getY() ,0);
            assertEquals(0.5, node2.getBottomRight().getY(), 0);
            assertEquals(0.5, node2.getBottomLeft().getY() ,0);
            assertEquals(0.5, node3.getTopRight().getY(), 0);
            assertEquals(0.5, node3.getTopLeft().getY() ,0);
            assertEquals(0.5, node4.getBottomLeft().getY(), 0);
            assertEquals(0.5, node4.getBottomRight().getY() ,0);
        }
        catch(XMustBeLargerThanZeroException xMBLTZE)
        {

        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {

        }catch( Exception e)
        {

        }

    }
}
