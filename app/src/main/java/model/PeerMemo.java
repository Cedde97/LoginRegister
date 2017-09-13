package model;


public class PeerMemo {
    //DateiMemo dateiMemo;

    private long uid;
    private long peerId;
    private String peerIp;


    public PeerMemo(){
    }

    public PeerMemo(long uid, int peerId, String peerIp) {
        this.uid = uid;
        this.peerId = peerId;
        this.peerIp = peerIp;

    }

    public PeerMemo(int peerId, String peerIp){
        this.peerId = peerId;
        this.peerIp = peerIp;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getPeerId() {
        return peerId;
    }

    public void setPeerId(long peerId) {
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
        String output = "UserID: " +uid + ", PeerID: " + peerId + ", IP: "+ peerIp+"\n";
        return output;
    }
}
