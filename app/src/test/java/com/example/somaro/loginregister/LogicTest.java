package com.example.somaro.loginregister;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connection.RoutHelper;
import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import model.*;

import static org.junit.Assert.*;


public class LogicTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test_Node_checkIfInMyZone_True()
    {
        Node node = new Node();
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

            System.out.print("IP: 192.111.23.4 = " + node.hashX("192.111.23.4") + ", " + node.hashY("192.111.23.4") + "\n");
            System.out.print("IP: 255.255.255.255 = " +node.hashX("255.255.255.255") + ", " + node.hashY("255.255.255.255") + "\n");
            System.out.print("IP: 180.1.23.123 = " +node.hashX("180.1.23.123") + ", " + node.hashY("180.1.23.123") + "\n");
            System.out.print("IP: 12.191.3.255 = " +node.hashX("12.191.3.255") + ", " + node.hashY("12.191.3.255") + "\n");
            System.out.print("IP: 1.111.223.34 = " +node.hashX("1.111.223.34") + ", " + node.hashY("1.111.223.34") + "\n");
            System.out.print("IP: 0.0.0.0 = " +node.hashX("0.0.0.0") + ", " + node.hashY("0.0.0.0") + "\n");
            System.out.print("IP: 78.31.3.129 = " +node.hashX("78.31.3.129") + ", " + node.hashY("78.31.3.129") + "\n");
            System.out.print("IP: 111.111.111.111 = " +node.hashX("111.111.111.111") + ", " + node.hashY("111.111.111.111") + "\n");
            System.out.print("IP: 222.222.222.222 = " +node.hashX("222.222.222.222") + ", " + node.hashY("222.222.222.222") + "\n");
            System.out.print("IP: 12.191.10.255 = " +node.hashX("12.191.10.255") + ", " + node.hashY("12.191.10.255") + "\n");
            System.out.print("IP: 12.191.11.255 = " +node.hashX("12.191.11.255") + ", " + node.hashY("12.191.11.255") + "\n");
            System.out.print("IP: 12.191.12.255 = " +node.hashX("12.191.12.255") + ", " + node.hashY("12.191.12.255") + "\n");
            System.out.print("IP: 12.255.255.255 = " +node.hashX("12.191.13.255") + ", " + node.hashY("12.191.13.255") + "\n");

            assertEquals(true, zone.checkIfInMyZone(node.hashX("192.111.23.4"), node.hashY("192.111.23.4")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("180.1.23.123"), node.hashY("180.1.23.123")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("12.191.3.255"), node.hashY("12.191.3.255")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("1.111.223.34"), node.hashY("1.111.223.34")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("255.255.255.255"), node.hashY("255.255.255.255")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("0.0.0.0"), node.hashY("0.0.0.0")));
            assertEquals(true, zone.checkIfInMyZone(node.hashX("78.31.3.129"), node.hashY("78.31.3.129")));

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
    public void test_Node_checkIfInMyZone_False()
    {
        Node node = new Node();
        Corner cornerBottomLeft;
        Corner cornerBottomRight;
        Corner cornerTopLeft;
        Corner cornerTopRight;
        Zone zone;
        try {
            cornerBottomLeft = new Corner(0.7,0.3);
            cornerBottomRight = new Corner(0.8,0.3);
            cornerTopLeft = new Corner(0.7 ,0.8);
            cornerTopRight = new Corner(0.8,0.8);
            zone = new Zone(cornerTopLeft,cornerTopRight,cornerBottomLeft,cornerBottomRight);

            System.out.print(node.hashX("192.111.23.4") + ", " + node.hashY("192.111.23.4") + "\n");
            System.out.print(node.hashX("255.255.255.255") + ", " + node.hashY("255.255.255.255") + "\n");
            System.out.print(node.hashX("0.0.0.0") + ", " + node.hashY("0.0.0.0") + "\n");
            System.out.print(node.hashX("78.31.3.129") + ", " + node.hashY("78.31.3.129") + "\n");
            assertEquals(false, zone.checkIfInMyZone(node.hashX("192.111.23.4"), node.hashY("192.111.23.4")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("255.255.255.255"), node.hashY("255.255.255.255")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("0.0.0.0"), node.hashY("0.0.0.0")));
            assertEquals(false, zone.checkIfInMyZone(node.hashX("78.31.3.129"), node.hashY("78.31.3.129")));

        }catch(XMustBeLargerThanZeroException xMBLTZE)
        {

        }catch(YMustBeLargerThanZeroException yMBLTZE)
        {

        }catch( Exception e)
        {

        }
    }

    @Test
    public void test_ComputeDistance()
    {
        Node node = new Node();
        double x = node.hashX("192.111.23.4");
        double y = node.hashY("192.111.23.4");
        System.out.println(node.computeDistance(node.hashX("12.191.25.255"),node.hashY("12.191.25.255") ,node.hashX("12.191.10.255"),node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.9.255"),node.hashY("12.191.9.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("1.1.1.100"),node.hashY("1.1.1.100")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.10.255"),node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.11.255"),node.hashY("12.191.11.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.12.255"),node.hashY("12.191.12.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.13.255"),node.hashY("12.191.13.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("0.0.0.0"),node.hashY("0.0.0.0")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("255.255.255.255"),node.hashY("255.255.255.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("78.31.3.129"),node.hashY("78.31.3.129")));
    }

    @Test
    public void test_CompareValues()
    {
        Node node = new Node();
        double x = node.hashX("192.111.23.4");
        double y = node.hashY("192.111.23.4");
        double dis[] = new double[4];
        dis [0] = node.computeDistance(x ,y ,node.hashX("12.191.10.255"),node.hashY("12.191.10.255"));
        dis [1] = node.computeDistance(x ,y ,node.hashX("12.191.11.255"),node.hashY("12.191.11.255"));
        dis [2] = node.computeDistance(x ,y ,node.hashX("12.191.12.255"),node.hashY("12.191.12.255"));
        dis [3] = node.computeDistance(x ,y ,node.hashX("12.191.13.255"),node.hashY("12.191.13.255"));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.10.255"),node.hashY("12.191.10.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.11.255"),node.hashY("12.191.11.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.12.255"),node.hashY("12.191.12.255")));
        System.out.println(node.computeDistance(x ,y ,node.hashX("12.191.13.255"),node.hashY("12.191.13.255")));
        System.out.println(node.compareValues(dis));
        assertEquals(1, node.compareValues(dis));
    }

    @Test
    public void test_UpdateNeighbourAndPeer() throws YMustBeLargerThanZeroException, XMustBeLargerThanZeroException, IOException {
        List<Neighbour> neighbourList = new ArrayList<>();
        List<PeerMemo> peerMemoList = new ArrayList<>();
        String ip = "192.168.2.115";

        Corner cornerBottomLeft1 = new Corner(0.0,0.0);
        Corner cornerBottomRight1 = new Corner(0.5,0.0);
        Corner cornerTopLeft1 = new Corner(0.0,1.0);
        Corner cornerTopRight1 = new Corner(0.5,1.0);

        Corner cornerBottomLeft2 = new Corner(0.5,0.0);
        Corner cornerBottomRight2 = new Corner(1.0,0.0);
        Corner cornerTopLeft2 = new Corner(0.5,1.0);
        Corner cornerTopRight2 = new Corner(1.0,1.0);

        Zone zone = new Zone(cornerTopLeft1,cornerTopRight1,cornerBottomLeft1,cornerBottomRight1);
        Zone neighbourZone = new Zone(cornerTopLeft2, cornerTopRight2, cornerBottomLeft2, cornerBottomRight2);
        Neighbour n = new Neighbour(03l,0.4,0.5,ip,zone,0.5);
        Neighbour n1 = new Neighbour(03l,0.5,0.5,ip,neighbourZone,0.5);
        PeerMemo p = new PeerMemo(03l,93,ip);
        neighbourList.add(n);
        neighbourList.add(n1);
        peerMemoList.add(p);


        Node node = new Node(01,0.5,0.5,ip,2,zone);
        node.setNeighbourList(neighbourList);
        node.setPeerMemoList(peerMemoList);
        RoutHelper rh = new RoutHelper(ip,0.3,0.5,02);

        Node neuerNode = node.routing(rh);
        System.out.println(neuerNode.toString());

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