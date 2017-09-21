package model;




/**
 * @author Eridho
 * */
public class Neighbour implements java.io.Serializable{
    private static final long serialVersionUID = -4668388398681616091L;
    //DateiMemo dateiMemo;




    private double cornerTopRightX;
    private double cornerTopRightY;
    private double cornerTopLeftX;
    private double cornerTopLeftY;
    private double cornerBottomRightX;
    private double cornerBottomRightY;
    private double cornerBottomLeftX;
    private double cornerBottomLeftY;
    private double punktX;
    private double punktY;
    private String UIP;
    private double RTT;

    private Zone   ownZone = new Zone();

    private Corner topRight;
    private Corner topLeft;
    private Corner bottomRight;
    private Corner bottomLeft;
    private Zone zone = new Zone();
    //private boolean checked;
    private int uid;
    private int neighbour_id;

    public long getNeighbour_id() {
        return neighbour_id;
    }

    public void setNeighbour_id(int neighbour_id) {
        this.neighbour_id = neighbour_id;
    }

    public Neighbour(){

    }
//
//    public Neighbour(long uid,
//                     double cornerTopRightX, double cornerTopRightY, double cornerTopLeftX, double cornerTopLeftY,
//                     double cornerBottomRightX, double cornerBottomRightY, double cornerBottomLeftX, double cornerBottomLeftY,
//                     double punktX, double punktY, String UIP, double RTT) {
//        this.uid = uid;
//        this.cornerTopRightX = cornerTopRightX;
//        this.cornerTopRightY = cornerTopRightY;
//        this.cornerTopLeftX = cornerTopLeftX;
//        this.cornerTopLeftY = cornerTopLeftY;
//        this.cornerBottomRightX = cornerBottomRightX;
//        this.cornerBottomRightY = cornerBottomRightY;
//        this.cornerBottomLeftX = cornerBottomLeftX;
//        this.cornerBottomLeftY = cornerBottomLeftY;
//        this.punktX = punktX;
//        this.punktY = punktY;
//        this.UIP = UIP;
//        this.RTT = RTT;
//    }

   /**
     * @author Joshua Zabel
     * @param uid
     * @param punktX
     * @param punktY
     * @param ip
     * @param zone
     * @param rtt
     */
    public Neighbour(int uid, double punktX, double punktY, String ip, Zone zone, double rtt){
        this.uid = uid;
        this.punktX = punktX;
        this.punktY = punktY;
        this.UIP    = ip;
        this.ownZone   = new Zone(zone.getTopLeft(),zone.getTopRight(), zone.getBottomLeft(), zone.getBottomRight());
        this.RTT    = rtt;
    }

    public double getCornerTopRightX() {
        return cornerTopRightX;
    }

    public void setCornerTopRightX(double cornerTopRightX) {
        this.cornerTopRightX = cornerTopRightX;
    }

    public double getCornerTopRightY() {
        return cornerTopRightY;
    }

    public void setCornerTopRightY(double cornerTopRightY) {
        this.cornerTopRightY = cornerTopRightY;
    }

    public double getCornerTopLeftX() {
        return cornerTopLeftX;
    }

    public void setCornerTopLeftX(double cornerTopLeftX) {
        this.cornerTopLeftX = cornerTopLeftX;
    }

    public double getCornerTopLeftY() {
        return cornerTopLeftY;
    }

    public void setCornerTopLeftY(double cornerTopLeftY) {
        this.cornerTopLeftY = cornerTopLeftY;
    }

    public double getCornerBottomRightX() {
        return cornerBottomRightX;
    }

    public void setCornerBottomRightX(double cornerBottomRightX) {this.cornerBottomRightX = cornerBottomRightX;}

    public double getCornerBottomRightY() {
        return cornerBottomRightY;
    }

    public void setCornerBottomRightY(double cornerBottomRightY) {this.cornerBottomRightY = cornerBottomRightY;}

    public double getCornerBottomLeftX() {
        return cornerBottomLeftX;
    }

    public void setCornerBottomLeftX(double cornerBottomLeftX) {this.cornerBottomLeftX = cornerBottomLeftX;}

    public double getCornerBottomLeftY() {
        return cornerBottomLeftY;
    }

    public void setCornerBottomLeftY(double cornerBottomLeftY) {this.cornerBottomLeftY = cornerBottomLeftY;}

    public double getPunktX() {
        return punktX;
    }

    public void setPunktX(double punktX) {
        this.punktX = punktX;
    }

    public double getPunktY() {
        return punktY;
    }

    public void setPunktY(double punktY) {
        this.punktY = punktY;
    }

    public String getUIP() {
        return UIP;
    }

    public void setUIP(String UIP) {
        this.UIP = UIP;
    }

    public double getRTT() {
        return RTT;
    }

    public void setRTT(double RTT) {
        this.RTT = RTT;
    }

    public long getUid() {
        return uid;
    }

    public  void setUid(int uid) {
        this.uid = uid;
    }

    public Corner getTopRight(){
        return getMyZone().getTopRight();
    }

    public Corner getTopLeft(){
        return getMyZone().getTopLeft();
    }

    public Corner getBottomRight(){
        return getMyZone().getBottomRight();
    }

    public Corner getBottomLeft(){
        return getMyZone().getBottomLeft();
    }

    public void setTopRight(Corner topRight){
        getMyZone().setTopRight(topRight);
    }

    public void setTopLeft(Corner topLeft){
        getMyZone().setTopLeft(topLeft);
    }
    public void setBottomRight(Corner bottomRight){
        getMyZone().setBottomRight(bottomRight);
    }
    public void setBottomLeft(Corner bottomLeft){
        getMyZone().setBottomLeft(bottomLeft);
    }

    public Zone getMyZone(){
        return ownZone;
    }

    public void setMyZone(Zone zone){
        this.ownZone = zone;
    }


    @Override
    public String toString() {
        String output = "\n\nNeighbour: "+ getNeighbour_id() + "\nUserID: " + uid + ",\nIP: " + UIP + "\n" +
                "Zone: "+ getMyZone().toString()+
                "PunktX :"+ punktX + ",\nPunktY :"+ punktY +
                "\nRTT :"+ RTT;
        return output;
    }


}
