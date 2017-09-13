package model;

/**
 * Created by eridhobufferyrollian on 12.08.17.
 */

public class OwnDataMemo {


    public long uid;
    public int fileId;



//    public OwnDataMemo(long uid, boolean checked, int fileId){
//        this.uid = uid;
//        this.checked = checked;
//        this.fileId = fileId;
//    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
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
        String output = uid + " -- " + fileId;
        return output;
    }
}