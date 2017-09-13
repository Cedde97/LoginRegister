package model;


import android.util.Log;

/**
 * Created by gyorgyi on 22/08/17.
 */
public class Zone {
    private Corner topLeft;
    private Corner topRight;
    private Corner bottomLeft;
    private Corner bottomRight;



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

    public void split(Node bottomLeftNode, Node topLeftNode, Node bottomRightNode, Node topRightNode) {



        // we split the zone along the longest side
        if (getLengthX(bottomLeftNode) >= getLengthY(bottomLeftNode)) {

            Node[] nodeArray = determineLocationX(bottomLeftNode,topLeftNode,bottomRightNode,topRightNode);

            Log.d("TEST_VORHER", "ARRAY: " + "\n" + "PunktX " + nodeArray[0].getPunktX() + " PunktY " + nodeArray[0].getPunktY() + "\n" +
                    nodeArray[0].getMyZone().toString() + "\n" +
                    "PunktX " + nodeArray[1].getPunktX() + " PunktY " + nodeArray[1].getPunktY() + "\n" +
                    nodeArray[1].getMyZone().toString() + "\n" +
                    "PunktX " + nodeArray[2].getPunktX() + " PunktY " + nodeArray[2].getPunktY() + "\n" +
                    nodeArray[2].getMyZone().toString() + "\n" +
                    "PunktX " + nodeArray[3].getPunktX() + " PunktY " + nodeArray[3].getPunktY() + "\n" +
                    nodeArray[3].getMyZone().toString() + "\n");

            double midX = getLengthX(nodeArray[0]) / 2.0;
            // set peers und neigbour und update Corner

            nodeArray[0].getBottomRight().setX(midX);
            Log.d("TEST_BOTTOMRight", "NODE1: " + nodeArray[0].getMyZone().toString());

            nodeArray[0].getTopRight().setX(midX);
            Log.d("TEST_TOPRight", "NODE1: " + nodeArray[0].getMyZone().toString());

            nodeArray[1].getTopRight().setX(midX);
            Log.d("TEST_TOPRight", "NODE4: " + nodeArray[3].getMyZone().toString());
            nodeArray[1].getBottomRight().setX(midX);

            nodeArray[2].getBottomLeft().setX(midX);
            nodeArray[2].getTopLeft().setX(midX);

            nodeArray[3].getTopLeft().setX(midX);
            nodeArray[3].getBottomLeft().setX(midX);

            /*Log.d("TEST_NACHhER", "ARRAY: " + nodeArray[0].getMyZone().toString() + "\n" +
                    nodeArray[1].getMyZone().toString() + "\n" +
                    nodeArray[2].getMyZone().toString() + "\n" +
                    nodeArray[3].getMyZone().toString() + "\n");*/

        } else {
            Node[] nodeArray = determineLocationY(bottomLeftNode,topLeftNode,bottomRightNode,topRightNode);
            double midY =  getLengthY(nodeArray[0]) / 2.0;
            // set peers und neigbour und update Corner

            nodeArray[0].getTopRight().setY(midY);
            nodeArray[0].getTopLeft().setY(midY);

            nodeArray[1].getBottomLeft().setY(midY);
            nodeArray[1].getBottomRight().setY(midY);

            nodeArray[2].getTopRight().setY(midY);
            nodeArray[2].getTopLeft().setY(midY);

            nodeArray[3].getBottomRight().setY(midY);
            nodeArray[3].getBottomLeft().setY(midY);
        }

    }

    private Node[] determineLocationX(Node node1, Node node2, Node node3, Node node4)
    {
        Node[] nodeArray = new Node[4];

        nodeArray[0] = node1;
        nodeArray[1] = node2;
        nodeArray[2] = node3;
        nodeArray[3] = node4;

        sortiere(nodeArray);

        return nodeArray;
    }

    private Node[] determineLocationY(Node node1, Node node2, Node node3, Node node4)
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
                    if (x[i].getPunktX() > pivot.getPunktX()) {
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
    private double getLengthY(Node node) {
        return   node.getTopLeft().getY() - node.getBottomLeft().getY();
    }

    /**
     * Get the length of the X side of the zone
     */
    private double getLengthX(Node node) {
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

        sb.append("TopRight " + topRight.toString()+", ")
                .append("TopLeft " + topLeft.toString()+", ")
                .append("BottomRight " + bottomRight.toString()+", ")
                .append("BottomLeft " + bottomLeft.toString()+", \n");

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
