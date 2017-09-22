package model;

/**
 * Created by eridhobufferyrollian on 12.08.17.
 * Class Foreign Data
 */

public class ForeignData implements java.io.Serializable{
    //DateiMemo dateiMemo;
    private static final long serialVersionUID = -3180520592609573364L;
    private long uid;
    private int fotoId;
    private double punktX;
    private double punktY;
    private String foreignIp;

    public ForeignData(double punktX, double punktY, int fotoId, long uid){
        this.uid = uid;
        this.fotoId = fotoId;
        this.punktX = punktX;
        this.punktY = punktY;
    }


    public ForeignData(){

    }


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getFotoId() {
        return fotoId;
    }

    public void setFotoId(int fotoId) {
        this.fotoId = fotoId;
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

    public String getForeignIp() {
        return foreignIp;
    }

    public void setForeignIp(String foreignIp) {
        this.foreignIp = foreignIp;
    }


    @Override
    public String toString() {
        String output = "\n\nUID " + uid + "\nFOTO_ID " + fotoId +
                "\nPunkt : x -> "+ punktX + "\nPunkt : y -> "+ punktY +
                "\nForeign IP : " + foreignIp;
        return output;
    }
}
