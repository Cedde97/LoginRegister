package com.example.somaro.loginregister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

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

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        ForeignDataDbSource foreignData = new ForeignDataDbSource();
        NeighborDbSource neighborDbSource = new NeighborDbSource();
        OwnDataDbSource ownDataDbSource = new OwnDataDbSource();
        PeerDbSource peerDbSource = new PeerDbSource();

        Node dateiMemo = new Node();
        dateiMemo.setUid(7872);
        //dateiMemo.setChecked(true);
        dateiMemo.setCornerTopRightX(0.5);
        dateiMemo.setCornerTopRightY(0.6);
        dateiMemo.setCornerTopLeftX(0.2);
        dateiMemo.setCornerTopLeftY(0.2);
        dateiMemo.setCornerBottomLeftX(0.4);
        dateiMemo.setCornerBottomLeftY(0.6);
        dateiMemo.setCornerBottomRightX(0.5);
        dateiMemo.setCornerBottomRightY(1);
        dateiMemo.setPunktX(0.2);
        dateiMemo.setPunktY(0.4);
        dateiMemo.setIP("277.0.0.0/8");
        dateiMemo.setCountPeers(2);
        dateiMemoDbSource.createDateiMemo(dateiMemo);

        PeerMemo peerMemo = new PeerMemo();
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

        /*Context appContext = InstrumentationRegistry.getTargetContext();
        DateiMemoDbHelper dateiMemoDbHelper = new DateiMemoDbHelper(appContext);
        DatabaseManager.initializeInstance(dateiMemoDbHelper);

        DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
        Node dateiMemo = new Node();
        dateiMemo.setUid(7872);
        //dateiMemo.setChecked(true);
        dateiMemo.setCornerTopRightX(0.5);
        dateiMemo.setCornerTopRightY(0.9);
        dateiMemo.setCornerBottomLeftX(0.2);
        dateiMemo.setCornerBottomLeftY(0.3);
        dateiMemo.setCornerTopLeftX(dateiMemo.getCornerBottomLeftX());
        dateiMemo.setCornerTopLeftY(dateiMemo.getCornerTopRightY());
        dateiMemo.setCornerBottomRightX(dateiMemo.getCornerTopRightX());
        dateiMemo.setCornerBottomRightY(dateiMemo.getCornerBottomLeftY());
        dateiMemo.setPunktX(0.3);
        dateiMemo.setPunktY(0.4);
        dateiMemo.setIP("277.0.0.0/8");
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


        assertEquals(0.1,dateiMemoDbSource.getCornerTopRightY(),0);
        assertEquals(0.9,dateiMemoDbSource.getCornerTopRightX(),0);
        assertEquals(0.5,dateiMemoDbSource.getCornerTopLeftY(),0);
        assertEquals(0.2,dateiMemoDbSource.getCornerTopLeftX(),0);
        assertEquals(0.4,dateiMemoDbSource.getCornerBottomRightY(),0);
        assertEquals(0.6,dateiMemoDbSource.getCornerBottomRightX(),0);
        assertEquals(0.8, dateiMemoDbSource.getCornerBottomLeftY(),0);
        assertEquals(0.5, dateiMemoDbSource.getCornerBottomLeftX(), 0);
        */
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

            assertEquals(0.5, node1.getBottomRight().getX(), 0);
            assertEquals(0.5, node1.getTopRight().getX() ,0);
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
