package model;


import android.util.Log;

import java.util.ArrayList;

import exception.XMustBeLargerThanZeroException;
import exception.YMustBeLargerThanZeroException;
import source.DateiMemoDbSource;
import source.NeighborDbSource;
import source.PeerDbSource;

/**
 * Created by gyorgyi on 22/08/17.
 */
public class Zone implements java.io.Serializable {
    private static final long serialVersionUID = 10617473022291464L;
    private Corner topLeft = new Corner();
    private Corner topRight= new Corner();
    private Corner bottomLeft= new Corner();
    private Corner bottomRight= new Corner();

    private DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource() ;
    private NeighborDbSource neighborDbSource = new NeighborDbSource();
    private PeerDbSource peerDbSource = new PeerDbSource();


    public Zone()
    {

    }

    public Zone(Corner topLeft, Corner topRight, Corner bottomLeft, Corner bottomRight){
        this.topLeft = topLeft;
        this.topRight= topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }







    /**
     * Methode zum Testen ob ein Knoten/Bild in der eigenen Zone liegt
     * @author Alexander Lukacs
     * @param x
     * @param y
     * @return True falls Knoten/Bild in der Zone liegt, false falls Knoten/bild nicht in der Zone liegt
     */
    public boolean checkIfInMyZone(double x, double y) {
        if(x > topLeft.getX() && x <= topRight.getX())
        {
            if (y > bottomLeft.getY() && y <= topLeft.getY() ) {
                return true;
            }
        }
        return false;
    }
/*
    *//**
     * Decides whether this zone and zone z are neighbours
     * @param z zone
     * @return true when this zone and zone z are neighbours
     *//*
    public boolean isNeighbour(Zone z) {

        if(sectionsOverlap(z.x1, z.x2, this.x1, this.x2) && sectionsTouch(z.y1, z.y2, this.y1, this.y2)) {
            return true;
        }else if(sectionsOverlap(z.y1, z.y2, this.y1, this.y2) && sectionsTouch(z.x1, z.x2, this.x1, this.x2)) {
            return true;
        }else {
            return false;
        }
    }

    *//**
     * Decides whether point (x; y) is in this zone
     * @param x
     * @param y
     * @return true when the point is in the zone
     *//*
    public boolean contains(double x, double y) {
        if((x >= x1 && x <= x2) && (y >= y1 && y <= y2)) {
            return true;
        } else {
            return false;
        }
    }*/

    /**
     * Determines whether sections a and b overlap
     * @param a1 starting point of section a
     * @param a2 end point of section a
     * @param b1 starting point of section b
     * @param b2 end point of section b
     * @return true when the two section overlap
     */
    private static boolean sectionsOverlap(double a1, double a2, double b1, double b2) {
        if(((a2 > b1) && (a2 <= b2)) || ((b2 > a1) && (b2 <= a2))) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * Determines whether sections a and b touch each other
     * @param a1 starting point of section a
     * @param a2 end point of section a
     * @param b1 starting point of section b
     * @param b2 end point of section b
     * @return true when the two section touch each other
     */
    private static boolean sectionsTouch(double a1, double a2, double b1, double b2) {
        if((a2 == b1) || (b2 == a1)) {
            return true;
        }else{
            return false;
        }
    }

    public Corner getTopRight(){
        return topRight;
    }

    public Corner getTopLeft(){
        return topLeft;
    }

    public Corner getBottomLeft() {
        return bottomLeft;
    }

    public Corner getBottomRight() {
        return bottomRight;
    }

    public void setTopRight(Corner topRight){
        this.topRight = topRight;
    }

    public void setTopLeft(Corner topLeft) {
        this.topLeft = topLeft;
    }

    public void setBottomRight(Corner bottomRight) {
        this.bottomRight = bottomRight;
    }

    public void setBottomLeft(Corner bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    /**
     * Split a zone along its longest side into two new zones.

     * @apiNote The contents of the node(s) owning this zone are not modified by this method, and thus has to be done elsewhere.
     *
     * @return the new pair of zones created by splitting this zone
     */

    public void split(Node node1, Node node2, Node node3, Node node4) {

        // we split the zone along the longest side
        if (getLengthX(node1) >= getLengthY(node1)) {

            Node[] nodeArray = determineLocationX(node1,node2,node3,node4);

            double midX = getLengthX(nodeArray[0]) / 2.0;
            // set peers und neighbor und update Corner

            try {
//--------------------------------------NODE1-----------------------------------------------------------------------------------
                Corner bottomRight1 = new Corner(midX,nodeArray[0].getMyZone().getBottomRight().getY());
                Corner topRight1 = new Corner(midX,nodeArray[0].getMyZone().getTopRight().getY());

                nodeArray[0].getMyZone().setBottomRight(bottomRight1);

                nodeArray[0].getMyZone().setTopRight(topRight1);

                ArrayList<PeerMemo> peerMemos1 = new ArrayList<PeerMemo>();

                peerMemos1.clear();
                PeerMemo peerMemo1 = new PeerMemo(nodeArray[1].getUid(),1,nodeArray[1].getIP());

                peerMemos1.add(peerMemo1);

                nodeArray[0].setPeerMemoList(peerMemos1);
                nodeArray[0].setCountPeers(1);

                ArrayList<Neighbour> neighbours1 = new ArrayList<Neighbour>();
//--------------------------------------NODE2-----------------------------------------------------------------------------------

                Corner bottomRight2 = new Corner(midX,nodeArray[1].getMyZone().getBottomRight().getY());
                Corner topRight2 = new Corner(midX,nodeArray[1].getMyZone().getTopRight().getY());

                nodeArray[1].getMyZone().setBottomRight(bottomRight2);

                nodeArray[1].getMyZone().setTopRight(topRight2);

                ArrayList<PeerMemo> peerMemos2 = new ArrayList<PeerMemo>();
                peerMemos2.clear();
                PeerMemo peerMemo2 = new PeerMemo(nodeArray[0].getUid(),1,nodeArray[0].getIP());
                peerMemos2.add(peerMemo2);

                nodeArray[1].setPeerMemoList(peerMemos2);

                nodeArray[1].setCountPeers(1);

                ArrayList<Neighbour> neighbours2 = new ArrayList<Neighbour>();
//---------------------------------------NODE3----------------------------------------------------------------------------------

                Corner bottomLeft1 = new Corner(midX,nodeArray[2].getMyZone().getBottomLeft().getY());
                Corner topLeft1 = new Corner(midX,nodeArray[2].getMyZone().getTopLeft().getY());

                nodeArray[2].getMyZone().setBottomLeft(bottomLeft1);

                nodeArray[2].getMyZone().setTopLeft(topLeft1);

                ArrayList<PeerMemo> peerMemos3 = new ArrayList<PeerMemo>();
                peerMemos3.clear();
                PeerMemo peerMemo3 = new PeerMemo(nodeArray[3].getUid(),1,nodeArray[3].getIP());
                peerMemos3.add(peerMemo3);

                nodeArray[2].setPeerMemoList(peerMemos3);

                nodeArray[2].setCountPeers(1);

                ArrayList<Neighbour> neighbours3 = new ArrayList<Neighbour>();
//---------------------------------------NODE4----------------------------------------------------------------------------------

                Corner bottomLeft2 = new Corner(midX,nodeArray[3].getMyZone().getBottomLeft().getY());
                Corner topLeft2 = new Corner(midX,nodeArray[3].getMyZone().getTopLeft().getY());

                nodeArray[3].getMyZone().setBottomLeft(bottomLeft2);

                nodeArray[3].getMyZone().setTopLeft(topLeft2);

                ArrayList<PeerMemo> peerMemos4 = new ArrayList<PeerMemo>();
                peerMemos4.clear();
                PeerMemo peerMemo4 = new PeerMemo(nodeArray[2].getUid(),1,nodeArray[2].getIP());
                peerMemos4.add(peerMemo4);

                nodeArray[3].setPeerMemoList(peerMemos4);

                nodeArray[3].setCountPeers(1);

                ArrayList<Neighbour> neighbours4 = new ArrayList<Neighbour>();
//----------------------------------------SET-NEIGHBORS-------------------------------------------------------------------------------------
                Neighbour neighbour1 = new Neighbour(nodeArray[2].getUid(),nodeArray[2].getPunktX(),nodeArray[2].getPunktY(),
                        nodeArray[2].getIP(),nodeArray[2].getMyZone(),1);

                neighbours1.add(neighbour1);
                nodeArray[0].setNeighbourList(neighbours1);

                neighborDbSource.createNeighborMemo(neighbour1);

                Neighbour neighbour2 = new Neighbour(nodeArray[3].getUid(),nodeArray[3].getPunktX(),nodeArray[3].getPunktY(),
                        nodeArray[3].getIP(),nodeArray[3].getMyZone(),1);

                neighbours2.add(neighbour2);
                nodeArray[1].setNeighbourList(neighbours2);

                neighborDbSource.createNeighborMemo(neighbour2);

                Neighbour neighbour3 = new Neighbour(nodeArray[1].getUid(),nodeArray[1].getPunktX(),nodeArray[1].getPunktY(),
                        nodeArray[1].getIP(),nodeArray[1].getMyZone(),1);

                neighbours3.add(neighbour3);
                nodeArray[2].setNeighbourList(neighbours3);

                neighborDbSource.createNeighborMemo(neighbour3);

                Neighbour neighbour4 = new Neighbour(nodeArray[0].getUid(),nodeArray[0].getPunktX(),nodeArray[0].getPunktY(),
                        nodeArray[0].getIP(),nodeArray[0].getMyZone(),1);

                neighbours4.add(neighbour4);
                nodeArray[3].setNeighbourList(neighbours4);

                neighborDbSource.createNeighborMemo(neighbour4);

            } catch (XMustBeLargerThanZeroException e) {
                e.printStackTrace();
            } catch (YMustBeLargerThanZeroException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("EXCEPTION", "NODE4 " + e.getMessage());
            }
        } else {
            Node[] nodeArray = determineLocationY(node1,node2,node3,node4);
            double midY =  getLengthY(nodeArray[0]) / 2.0;
            // set peers und neigbour und update Corner

            try {
                Corner topRight = new Corner(nodeArray[0].getMyZone().getTopRight().getX(),midY);
                Corner topLeft = new Corner(nodeArray[0].getMyZone().getTopLeft().getX(),midY);

                nodeArray[0].getMyZone().setTopRight(topRight);

                nodeArray[0].getMyZone().setTopLeft(topLeft);

                ArrayList<PeerMemo> peerMemos = new ArrayList<PeerMemo>();
                peerMemos.clear();
                PeerMemo peerMemo = new PeerMemo(nodeArray[2].getUid(),1,nodeArray[2].getIP());
                peerMemos.add(peerMemo);

                nodeArray[0].setPeerMemoList(peerMemos);

                nodeArray[0].setCountPeers(1);

                ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();

                Neighbour neighbour = new Neighbour(nodeArray[1].getUid(),nodeArray[1].getPunktX(),nodeArray[1].getPunktY(),
                        nodeArray[1].getIP(),nodeArray[1].getMyZone(),1);
                neighbours.add(neighbour);

                nodeArray[0].setNeighbourList(neighbours);
            } catch (XMustBeLargerThanZeroException e) {
                e.printStackTrace();
            } catch (YMustBeLargerThanZeroException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("EXCEPTION", "NODE1 " + e.getMessage());
            }


            try {
                Corner bottomLeft = new Corner(nodeArray[1].getMyZone().getBottomLeft().getX(),midY);
                Corner bottomRight = new Corner(nodeArray[1].getMyZone().getBottomRight().getX(),midY);

                nodeArray[1].getMyZone().setBottomLeft(bottomLeft);

                nodeArray[1].getMyZone().setBottomRight(bottomRight);

                ArrayList<PeerMemo> peerMemos = new ArrayList<PeerMemo>();
                peerMemos.clear();
                PeerMemo peerMemo = new PeerMemo(nodeArray[3].getUid(),1,nodeArray[3].getIP());
                peerMemos.add(peerMemo);

                nodeArray[1].setPeerMemoList(peerMemos);

                nodeArray[1].setCountPeers(1);

                ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();

                Neighbour neighbour = new Neighbour(nodeArray[0].getUid(),nodeArray[0].getPunktX(),nodeArray[0].getPunktY(),
                        nodeArray[0].getIP(),nodeArray[0].getMyZone(),1);
                neighbours.add(neighbour);

                nodeArray[1].setNeighbourList(neighbours);
            } catch (XMustBeLargerThanZeroException e) {
                e.printStackTrace();
            } catch (YMustBeLargerThanZeroException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("EXCEPTION", "NODE2 " + e.getMessage());
            }

            try {
                Corner topRight = new Corner(nodeArray[2].getMyZone().getTopRight().getX(),midY);
                Corner topLeft = new Corner(nodeArray[2].getMyZone().getTopLeft().getX(),midY);

                nodeArray[2].getMyZone().setTopRight(topRight);

                nodeArray[2].getMyZone().setTopLeft(topLeft);

                ArrayList<PeerMemo> peerMemos = new ArrayList<PeerMemo>();
                peerMemos.clear();
                PeerMemo peerMemo = new PeerMemo(nodeArray[0].getUid(),1,nodeArray[0].getIP());
                peerMemos.add(peerMemo);

                nodeArray[2].setPeerMemoList(peerMemos);

                nodeArray[2].setCountPeers(1);

                ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();

                Neighbour neighbour = new Neighbour(nodeArray[3].getUid(),nodeArray[3].getPunktX(),nodeArray[3].getPunktY(),
                        nodeArray[3].getIP(),nodeArray[3].getMyZone(),1);
                neighbours.add(neighbour);

                nodeArray[2].setNeighbourList(neighbours);

            } catch (XMustBeLargerThanZeroException e) {
                e.printStackTrace();
            } catch (YMustBeLargerThanZeroException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("EXCEPTION", "NODE3 " + e.getMessage());
            }

            try {
                Corner bottomLeft = new Corner(nodeArray[3].getMyZone().getBottomLeft().getX(),midY);
                Corner bottomRight = new Corner(nodeArray[3].getMyZone().getBottomRight().getX(),midY);

                nodeArray[3].getMyZone().setBottomLeft(bottomLeft);

                nodeArray[3].getMyZone().setBottomRight(bottomRight);

                ArrayList<PeerMemo> peerMemos = new ArrayList<PeerMemo>();
                peerMemos.clear();
                PeerMemo peerMemo = new PeerMemo(nodeArray[1].getUid(),1,nodeArray[1].getIP());
                peerMemos.add(peerMemo);

                nodeArray[3].setPeerMemoList(peerMemos);

                nodeArray[3].setCountPeers(1);


                ArrayList<Neighbour> neighbours = new ArrayList<Neighbour>();

                Neighbour neighbour = new Neighbour(nodeArray[2].getUid(),nodeArray[2].getPunktX(),nodeArray[2].getPunktY(),
                        nodeArray[2].getIP(),nodeArray[2].getMyZone(),1);

                neighbours.add(neighbour);

                nodeArray[3].setNeighbourList(neighbours);
            } catch (XMustBeLargerThanZeroException e) {
                e.printStackTrace();
            } catch (YMustBeLargerThanZeroException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                Log.d("EXCEPTION", "NODE4 " + e.getMessage());
            }
        }



    }

    public Node[] determineLocationX(Node node1, Node node2, Node node3, Node node4)
    {
        Node[] nodeArray = new Node[4];

        nodeArray[0] = node1;
        nodeArray[1] = node2;
        nodeArray[2] = node3;
        nodeArray[3] = node4;

        sortiere(nodeArray);

        return nodeArray;
    }

    public Node[] determineLocationY(Node node1, Node node2, Node node3, Node node4)
    {
        Node[] nodeArray = new Node[4];

        nodeArray[0] = node1;
        nodeArray[1] = node2;
        nodeArray[2] = node3;
        nodeArray[3] = node4;

        sortiere(nodeArray);

        return nodeArray;
    }
        public  void sortiere(Node x[]) {
            qSort(x, 0, x.length-1);
            }

            public  void qSort(Node x[], int links, int rechts) {
                if (links < rechts) {
                    int i = partition(x,links,rechts);
                    qSort(x,links,i-1);
                    qSort(x,i+1,rechts);
                }
            }

            public  int partition(Node x[], int links, int rechts) {
                Node pivot, help;
                int i, j;
                pivot = x[rechts];
                i     = links;
                j     = rechts-1;
                while(i<=j) {
                    if (x[i].getPunktY() > pivot.getPunktY()) {
                        // tausche x[i] und x[j]
                        help = x[i];
                        x[i] = x[j];
                        x[j] = help;
                        j--;
                    } else i++;
                }
                // tausche x[i] und x[rechts]
                help      = x[i];
                x[i]      = x[rechts];
                x[rechts] = help;

                return i;
            }



       // Log.d("TEST", "Determine");
      /* if(node1.getPunktX()<= node2.getPunktX() || node1.getPunktX()<= node3.getPunktX() || node1.getPunktX()<= node4.getPunktX() ) {
           Log.d("1.TEST_X", "NODE1");
           if(node1.getPunktY() <= node2.getPunktY() || node1.getPunktY() <= node3.getPunktY() || node1.getPunktY() <= node4.getPunktY()){
               Log.d("1.TEST_Y", "NODE1");
                nodeArray[0] = node1;
           }
       }
       if(node2.getPunktX()<= node3.getPunktX() || node2.getPunktX()<= node4.getPunktX() ||  node2.getPunktX() <= node1.getPunktX()){
           Log.d("1.TEST_X", "NODE2");
           if(node2.getPunktY() <= node1.getPunktY() || node2.getPunktY() <= node3.getPunktY() || node2.getPunktY() <= node4.getPunktY()){
               Log.d("1.TEST_Y", "NODE2");
                nodeArray[0] = node2;
           }
       }
       if(node3.getPunktX()<= node2.getPunktX() || node3.getPunktX()<= node1.getPunktX() || node3.getPunktX()<= node4.getPunktX() ) {
           Log.d("1.TEST_X", "NODE3");
            if(node3.getPunktY() <= node2.getPunktY() || node3.getPunktY() <= node1.getPunktY() || node3.getPunktY() <= node4.getPunktY()){
                Log.d("1.TEST_Y", "NODE3");
                nodeArray[0] = node3;
            }
        }
        if(node4.getPunktX()<= node3.getPunktX() || node4.getPunktX()<= node2.getPunktX() ||  node4.getPunktX() <= node1.getPunktX()){
           Log.d("1.TEST_X", "NODE4");
            if(node4.getPunktY() <= node1.getPunktY() || node4.getPunktY() <= node3.getPunktY() || node4.getPunktY() <= node2.getPunktY()){
                Log.d("1.TEST_Y", "NODE4");
                nodeArray[0] = node4;
            }
        }

        if(node1.getPunktX()<= node2.getPunktX() | node1.getPunktX()<= node3.getPunktX() | node1.getPunktX()<= node4.getPunktX() ) {
            Log.d("2.TEST_X", "NODE1" + node1.getPunktY() + ", " + node4.getPunktY());
            if(node1.getPunktY() >= node2.getPunktY() | node1.getPunktY() >= node3.getPunktY() | node1.getPunktY() >= node4.getPunktY()){
                Log.d("2.TEST_Y", "NODE1");
                nodeArray[1] = node1;
            }
        }
        if(node2.getPunktX()<= node3.getPunktX() && node2.getPunktX()<= node4.getPunktX() &&  node2.getPunktX() <= node1.getPunktX()){
            Log.d("2.TEST_X", "NODE2");
            if(node2.getPunktY() >= node1.getPunktY() && node2.getPunktY() >= node3.getPunktY() && node2.getPunktY() >= node4.getPunktY()){
                Log.d("2.TEST_Y", "NODE2");
                nodeArray[1] = node2;
            }
        }
        if(node3.getPunktX()<= node2.getPunktX() && node3.getPunktX()<= node1.getPunktX() && node3.getPunktX()<= node4.getPunktX() ) {
            Log.d("2.TEST_X", "NODE3");
            if(node3.getPunktY() >= node2.getPunktY() && node3.getPunktY() >= node1.getPunktY() && node3.getPunktY() >= node4.getPunktY()){
                Log.d("2.TEST_Y", "NODE3");
                nodeArray[1] = node3;
            }
        }
        if(node4.getPunktX()<= node3.getPunktX() && node4.getPunktX()<= node2.getPunktX() &&  node4.getPunktX() <= node1.getPunktX()){
            Log.d("2.TEST_X", "NODE4");
            if(node4.getPunktY() >= node1.getPunktY() && node4.getPunktY() >= node3.getPunktY() && node4.getPunktY() >= node2.getPunktY()){
                Log.d("2.TEST_Y", "NODE4");
                nodeArray[1] = node4;
            }
        }


        if(node1.getPunktX()>= node2.getPunktX() && node1.getPunktX()>= node3.getPunktX() && node1.getPunktX()>= node4.getPunktX() ) {
            Log.d("3.TEST_X", "NODE1");
            if(node1.getPunktY() <= node2.getPunktY() && node1.getPunktY() <= node3.getPunktY() && node1.getPunktY() <= node4.getPunktY()){
                Log.d("3.TEST_Y", "NODE1");
                nodeArray[2] = node1;
            }
        }
        if(node2.getPunktX()>= node3.getPunktX() && node2.getPunktX()>= node4.getPunktX() &&  node2.getPunktX() >= node1.getPunktX()){
            Log.d("3.TEST_X", "NODE2");
            if(node2.getPunktY() <= node1.getPunktY() && node2.getPunktY() <= node3.getPunktY() && node2.getPunktY() <= node4.getPunktY()){
                Log.d("3.TEST_Y", "NODE2");
                nodeArray[2] = node2;
            }
        }
        if(node3.getPunktX()>= node2.getPunktX() && node3.getPunktX()>= node1.getPunktX() && node3.getPunktX()>= node4.getPunktX() ) {
            Log.d("3.TEST_X", "NODE3");
            if(node3.getPunktY() <= node2.getPunktY() && node3.getPunktY() <= node1.getPunktY() && node3.getPunktY() <= node4.getPunktY()){
                Log.d("3.TEST_Y", "NODE3");
                nodeArray[2] = node3;
            }
        }
        if(node4.getPunktX()>= node3.getPunktX() && node4.getPunktX()>= node2.getPunktX() &&  node4.getPunktX() >= node1.getPunktX()){
            Log.d("3.TEST_X", "NODE4");
            if(node4.getPunktY() <= node1.getPunktY() && node4.getPunktY() <= node3.getPunktY() && node4.getPunktY() <= node2.getPunktY()){
                Log.d("3.TEST_Y", "NODE4");
                nodeArray[2] = node4;
            }
        }


        if(node1.getPunktX()>= node2.getPunktX() && node1.getPunktX()>= node3.getPunktX() && node1.getPunktX()>= node4.getPunktX() ) {
            Log.d("4.TEST_X", "NODE1");
            if(node1.getPunktY() >= node2.getPunktY() && node1.getPunktY() >= node3.getPunktY() && node1.getPunktY() >= node4.getPunktY()){
                Log.d("4.TEST_Y", "NODE1");
                nodeArray[3] = node1;
            }
        }
        if(node2.getPunktX()>= node3.getPunktX() && node2.getPunktX()>= node4.getPunktX() &&  node2.getPunktX() >= node1.getPunktX()){
            Log.d("4.TEST_X", "NODE2");
            if(node2.getPunktY() >= node1.getPunktY() && node2.getPunktY() >= node3.getPunktY() && node2.getPunktY() >= node4.getPunktY()){
                Log.d("4.TEST_Y", "NODE2");
                nodeArray[3] = node2;
            }
        }
        if(node3.getPunktX()>= node2.getPunktX() && node3.getPunktX()>= node1.getPunktX() && node3.getPunktX()>= node4.getPunktX() ) {
            Log.d("4.TEST_X", "NODE3");
            if(node3.getPunktY() >= node2.getPunktY() && node3.getPunktY() >= node1.getPunktY() && node3.getPunktY() >= node4.getPunktY()){
                Log.d("4.TEST_Y", "NODE3");
                nodeArray[3] = node3;
            }
        }
        if(node4.getPunktX()>= node3.getPunktX() && node4.getPunktX()>= node2.getPunktX() &&  node4.getPunktX() >= node1.getPunktX()){
            Log.d("4.TEST_X", "NODE4");
            if(node4.getPunktY() >= node1.getPunktY() && node4.getPunktY() >= node3.getPunktY() && node4.getPunktY() >= node2.getPunktY()){
                Log.d("4.TEST_Y", "NODE4");
                nodeArray[3] = node4;
            }
        }*/

    /**
     * Get the length of the Y side of the zone
     */
    public double getLengthY(Node node) {
        return   node.getTopLeft().getY() - node.getBottomLeft().getY();
    }

    /**
     * Get the length of the X side of the zone
     */
    public double getLengthX(Node node) {
        return  node.getBottomRight().getX() - node.getBottomLeft().getX();
    }

    /**
     * Merge two zones and create a single one from the merged zones.
     * The two zones have to be neighbours and share a common side of the same length in order to be mergeable.
     *
     * @param //z1 The first zone to merge
     * @param //z2 The second zone to merge
     * @return the zone created by merging the two zones
     */
    /*   BRAUCHEN WIR DAS ÃœBERHAUPT
    public static Zone merge(Zone z1, Zone z2) {

        if (!z1.isNeighbour(z2)) {
            throw new IllegalArgumentException("ERROR: " + z1 + " and " + z2 + " are not neighbours, and thus cannot be merged.");

        } else if (z1.getLengthX() == z2.getLengthX() && z1.x1 == z2.x1 && z1.x2 == z2.x2) {

            // the two zones share an X-side of the same length, now we check which one is above the other
            if (z1.y2 == z2.y1) {

                // z1 is below z2

                return new Zone(z1.x1, z1.x2, z1.y1, z2.y2);
            } else {

                // z1 is above z2

                return new Zone(z1.x1, z1.x2, z2.y1, z1.y2);
            }

        } else if (z1.getLengthY() == z2.getLengthY() && z1.y1 == z2.y1 && z1.y2 == z2.y2) {

            // the two zones share a Y-side of the same length, now we check which one is right to the other
            if (z1.x2 == z2.x1) {

                // z2 is right to z1

                return new Zone(z1.x1, z2.x2, z1.y1, z1.y2);
            } else {

                // z2 is left to z1

                return new Zone(z2.x1, z1.x2, z1.y1, z1.y2);
            }

        } else {
            throw new IllegalArgumentException("ERROR: " + z1 + " and " + z2 + " are neighbours, but do not have sides of the same length," +
                    " and thus cannot be merged.");
        }
    }*/


    public String toString(){
        StringBuffer sb = new StringBuffer();

        sb.append("\n\tTopRight " + topRight.toString()+", \n")
                .append("\tTopLeft " + topLeft.toString()+", \n")
                .append("\tBottomRight " + bottomRight.toString()+", \n")
                .append("\tBottomLeft " + bottomLeft.toString()+", \n");

        return sb.toString();
    }


/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Zone zone = (Zone) o;

        if (Double.compare(zone.x1, x1) != 0) return false;
        if (Double.compare(zone.y1, y1) != 0) return false;
        if (Double.compare(zone.x2, x2) != 0) return false;
        return Double.compare(zone.y2, y2) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x1);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y1);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }*/
}
