package model;

/**
 * Created by eridhobufferyrollian on 12.08.17.
 */

public class OwnDataMemo implements java.io.Serializable{
    private static final long serialVersionUID = -9108470830188537841L;

    public int uid;
    public int fileId;


    public OwnDataMemo(){}

    public OwnDataMemo(int uid, int fileId){
        this.uid = uid;
        this.fileId = fileId;
   }

   public double hashX(long uid , int fotoId)
   {
       String xWert =  " " + uid + fotoId ;
       double hashX = Double.parseDouble("0." + hash(xWert));
       return hashX;
   }

    public double hashY(long uid , int fotoId)
    {
        String yWert =  uid + fotoId + " ";
        double hashY = Double.parseDouble("0." + hash(yWert));
        return hashY;
    }

   private int hash(String hashWert)
   {
       return Math.abs(hashWert.hashCode());
   }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        String output = "\n\nUID " + uid + "\nFOTO_ID " +  fileId;
        return output;
    }
}