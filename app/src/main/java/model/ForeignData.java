package model;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import source.ForeignDataDbSource;

/**
 * Created by eridhobufferyrollian on 12.08.17.
 * Class Foreign Data
 */

public class ForeignData implements java.io.Serializable{
    //DateiMemo dateiMemo;
    private static final long serialVersionUID = -3180520592609573364L;
    private int uid;
    private int fotoId;
    private double punktX;
    private double punktY;
<<<<<<< HEAD
    private String foreignIP;


    public ForeignData(String foreignIp, double punktX, double punktY, int fotoId, int uid){
=======
    private String foreignIp;

    public ForeignData(double punktX, double punktY, int fotoId, long uid){
>>>>>>> 2f248724cce97bdc1027b5c4891aa4bf698ebf58
        this.uid = uid;
        this.fotoId = fotoId;
        this.punktX = punktX;
        this.punktY = punktY;
        this.foreignIP = foreignIp;
    }


    public ForeignData(){

    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

<<<<<<< HEAD
//    public boolean isChecked() {
//        return checked;
//    }
//
//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }

    public void setForeignIp(String ip)
    {
        this.foreignIP = ip;
    }

    public String getForeignIp()
    {
        return foreignIP;
    }

=======
>>>>>>> 2f248724cce97bdc1027b5c4891aa4bf698ebf58
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

    private String getFile(int uid,ForeignDataDbSource fDb) {
        String foto = "";
        Log.d("TEST",""+ uid );
        if (uid == fDb.getUidForeign()) {
            foto = fDb.getFotoId(fDb.getUidForeign()) + ".jpg";
            Log.d("TEST", "FOTO " + foto);
        }
        return foto;
    }

    public File getImage(int uid, ForeignDataDbSource fDb)
    {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator+"images"+ File.separator+"CAN_PICS"+ File.separator + getFile(uid,fDb));
        return file;
    }

    @Override
    public String toString() {
        String output = "\n\nUID " + uid + "\nFOTO_ID " + fotoId +
                "\nPunkt : x -> "+ punktX + "\nPunkt : y -> "+ punktY +
                "\nForeign IP : " + foreignIp;
        return output;
    }
}
