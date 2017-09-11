package model;




/**/
public class Neighbour {

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
    //private boolean checked;
    private long uid;
    private long neighbour_id;



    //    public Neighbour(){
//
//    }
//
        public Neighbour(long uid,
                        double cornerTopRightX, double cornerTopRightY, double cornerTopLeftX, double cornerTopLeftY,
                        double cornerBottomRightX, double cornerBottomRightY, double cornerBottomLeftX, double cornerBottomLeftY,
                        double punktX, double punktY, String UIP, double RTT) {
        this.uid = uid;
        this.cornerTopRightX = cornerTopRightX;
        this.cornerTopRightY = cornerTopRightY;
        this.cornerTopLeftX = cornerTopLeftX;
        this.cornerTopLeftY = cornerTopLeftY;
        this.cornerBottomRightX = cornerBottomRightX;
        this.cornerBottomRightY = cornerBottomRightY;
        this.cornerBottomLeftX = cornerBottomLeftX;
        this.cornerBottomLeftY = cornerBottomLeftY;
        this.punktX = punktX;
        this.punktY = punktY;
        this.UIP = UIP;
        this.RTT = RTT;
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

    public void setCornerBottomRightX(double cornerBottomRightX) {
        this.cornerBottomRightX = cornerBottomRightX;
    }

    public double getCornerBottomRightY() {
        return cornerBottomRightY;
    }

    public void setCornerBottomRightY(double cornerBottomRightY) {
        this.cornerBottomRightY = cornerBottomRightY;
    }

    public double getCornerBottomLeftX() {
        return cornerBottomLeftX;
    }

    public void setCornerBottomLeftX(double cornerBottomLeftX) {
        this.cornerBottomLeftX = cornerBottomLeftX;
    }

    public double getCornerBottomLeftY() {
        return cornerBottomLeftY;
    }

    public void setCornerBottomLeftY(double cornerBottomLeftY) {
        this.cornerBottomLeftY = cornerBottomLeftY;
    }

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

//    public boolean isChecked() {
//        return checked;
//    }
//
//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }

    public long getUid() {
        return uid;
    }

    public  void setUid(long uid) {
        this.uid = uid;
    }

    public long getNeighbour_id() {
        return neighbour_id;
    }

    public void setNeighbour_id(long neighbour_id) {
        this.neighbour_id = neighbour_id;
    }

    @Override
    public String toString() {
        String output = uid + " -- " + UIP + " -- " + neighbour_id +
                "\nCorner top Left : x -> "+ cornerTopLeftX + " -- y -> "+ cornerTopLeftY +
                "\nCorner top Right : x -> "+ cornerTopRightX + " -- y -> "+ cornerTopRightY +
                "\nCorner Bottom Left : x -> "+ cornerBottomLeftX + " -- y -> "+ cornerBottomLeftY +
                "\nCorner Bottom Right : x -> "+ cornerBottomRightX + " -- y -> "+ cornerBottomRightY +
                "\nCorner Punkt : x -> "+ punktX + " -- y -> "+ punktY +
                "\n RTT : "+ RTT;
        return output;
    }


}
