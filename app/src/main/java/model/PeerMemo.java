package model;


public class PeerMemo implements java.io.Serializable{
    //DateiMemo dateiMemo;
    private static final long serialVersionUID = -3454312037450492244L;
    private int uid;
    private int peerId;
    private String peerIp;


    public PeerMemo(){
    }

    public PeerMemo(int uid, int peerId, String peerIp) {
        this.uid = uid;
        this.peerId = peerId;
        this.peerIp = peerIp;

    }

    public PeerMemo(int peerId, String peerIp){
        this.peerId = peerId;
        this.peerIp = peerIp;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPeerId() {
        return peerId;
    }

    public void setPeerId(int peerId) {
        this.peerId = peerId;
    }

    public String getPeerIp() {
        return peerIp;
    }

    public void setPeerIp(String peerIp) {
        this.peerIp = peerIp;
    }


    public int getLength(long zahl){
        String s = String.valueOf(zahl);
        return s.length();
    }


    public int getPeerCount () {
        int length = getLength(peerId);
        int i = 0;
        while (i<length){
            i = i+1;
        }
        return i;
    }


    @Override
    public String toString() {
        String output = "\n\nUserID: " +uid + ",\nPeerID: " + peerId + ",\nIP: "+ peerIp;
        return output;
    }
}
