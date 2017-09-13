package model;


import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.List;


import connection.Client;
import connection.RoutHelper;

/**
 * @author Joshua Zabel
 */
public class Node {
    private static final int    maxPeers= 3;
    private static final long   DIVIDER=2552552552l;
    private static final int    PORTNR = 8080;


    private long   uid;
    private double punktX;
    private double punktY;
    private String iP;
    private int    countPeers;
    private Corner topRight;
    private Corner topLeft;
    private Corner bottomRight;
    private Corner bottomLeft;
    private Zone   ownZone = new Zone();
    private Socket socket;
    private Client client = new Client();


    public Node ()
    {

    }



    /**
     * Konstruktor zum Erstellen eines Nodes
     * @param uid User-ID
     * @param punktX X-Wert wo der Knoten in CAN ist
     * @param punktY Y-Wert wo der Knoten in CAN ist
     * @param iP IP des Gerätes auf dem die APP läuft
     * @param countPeers Anzahl der Peers
     */
    public Node(long uid, double punktX, double punktY, String iP, int countPeers, Zone ownZone) {
        this.uid                 = uid;
        this.punktX              = punktX;
        this.punktY              = punktY;
        this.iP                  = iP;
        this.countPeers          = countPeers;
        this.ownZone             = ownZone;
    }



    /**
     * Diese Methode liefert einen x-Wert der zwischen 0 und 1 liegt
     * Es wird durch 2552552552l geteilt, da so die Werte zwischen 0 und 1 liegt
     * @param ip Anhand der IP wird ein x-Wert berechnet
     * @return Gebe einen double X-Wert zurÃ¼ck
     */
    public static double hashX(String ip) {
        double x = ip.hashCode();
        if(x < 0){
            x = x/(-DIVIDER);
            return x;
        }else{
            x = x/DIVIDER;
            return x;
        }
    }

    /**
     * Diese Methode liefert einen Y-Wert der zwischen 0 und 1 liegt
     * Es wird durch 2552552552l geteilt, da so die Werte zwischen 0 und 1 liegen und die IP wird von hinten nach vorne gelesen durch Methode-Umkehren
     * @param ip Anhand der IP wird ein Y-Wert berechnet
     * @return Gebe einen double Y-Wert zurÃ¼ck
     */
    public static double hashY(String ip){
        String hash2 = umkehren(ip);
        double y = hash2.hashCode();
        if(y < 0){
            y = y/(-DIVIDER);
            return y;
        }else{
            y = y/DIVIDER;
            return y;
        }
    }

    /**
     * Methode zum umkehren von einer IP-Adresse
     * @param ip Eine IP-Adresse
     * @return Gibt die IP-Adresse umgekehrt zurÃ¼ck
     */
    public static String umkehren( String ip )
    {
        String umgekehrt = new String();

        for ( int j = ip.length()-1; j >= 0; j-- )
            umgekehrt += ip.charAt(j);

        return umgekehrt;
    }

    /**
     * @param ip Ist die eigene IP
     * @param x X-Wert des zu routenden Bildes
     * @param y Y-Wert des zu routenden Bildes
     * @param FotoId Foto-ID
     */
    private void picRouting(String ip, double x, double y, int FotoId, int uid) throws IOException {
        //// TODO: 15.08.2017 Verbindungsaufbau zu der ip um Bild herunterzuladen und dann zu speichern
        /// TODO: 15.08.2017 verbindungsaufbau zu Peers und diesen werden die Informationen zum Bild Ã¼bermittelt und nun laden sie sich das Bild von zuletzt gerouteten Node herunter
        //fortsetzung des routing

    }

    /**
     * Methode die den Routing-Vorgang weiterleitet falls das Ziel noch nicht erreicht wurde
     * @param ip IP des zu routenden Knoten
     * @param x X-Wert des zu routenden Knoten
     * @param y Y-Wert des zu routenden Knoten
     * @param id kann jeweils FotoID oder UID sein, wird benÃ¶tigt sodass man seinen Peers die nÃ¶tigen Informationen zu dem neuen Knoten geben kann
     */
    public void receiveRoutingRequest(String ip, double x, double y, int id) throws IOException {

        //fortsetzung des routing
        routing(ip,x,y,id);
    }
    /**
     * Hilfsmethode zum routing um zu Ã¼berprÃ¼fen ob der zu routende Knoten in der momentanen Zone liegt
     * @param ip Des zu routenden Knoten/Bild
     * @param x Des zu routenden Knoten/Bild
     * @param y Des zu routenden Knoten/Bild
     * @param id Des zu routenden Knoten/Bild
     */
    private void routingCheckZoneDB(String ip, double x ,double y, int id){
        if(getMyZone().checkIfInMyZone(x,y)){
            //was fÃ¼r peerId mitte?
            PeerMemo pm = new PeerMemo();

            //neuen Knoten seine aktuelle PeersList geben (mit sichselbst)
            //neuen Knoten eintragen in eigene peer list

            //// TODO: 14.08.2017  Reply to Request-Method(muss setPeers(mit sich selbst) und setNeighbours mitsenden)
            //// TODO: 14.08.2017 Muss aktuelle Peers Ã¼ber den neuen Knoten Informieren, sodass diese ihre Peerliste updaten. Nun update deine eigene Peerlist
            //// TODO: 08.09.2017 abbrechen
            if(checkIfMaxPeersCount()){

                //// TODO: 15.08.2017 informiere deine Peers das sie nun Splitten mÃ¼ssen// methode die einen Splitt aufruft
                //// TODO: 14.08.2017 SPLITT
                // TODO: 08.09.2017 abbrechen
            }

        }
    }

    private Node routingCheckZone(String ip, double x ,double y, long id){

        Node returnNode = new Node();

        if(getMyZone().checkIfInMyZone(x,y)){
            Node newNode = new Node(id, x, y, ip, 3, getMyZone());
            if(checkIfMaxPeersCount()){
                //splitt

            }
            return newNode;

        }
        return returnNode;
    }


    public Node routing(String ip, double x ,double y, long id) throws IOException {
        Node nodeNew = routingCheckZone(ip,x,y,id);

        // socket = new Socket("192.168.2.110", PORTNR);
        RoutHelper rh = new RoutHelper(ip,x,y,id);
        return nodeNew;
        //client.sendRoutHelperAsByteArray(socket,rh);

    }
    /*

    *//**
     * Routing Methode: In der Routing-Methode wird die Distanz zu allen Nachbarn berechnet und zu dem routet zu dem die Distanz am geringsten ist
     * @param ip Des zu routenden Knoten/Bild
     * @param x Des zu routenden Knoten/Bild
     * @param y Des zu routenden Knoten/Bild
     * @param id Des zu routenden Knoten/Bild
     *//*
    public void routingDB(String ip, double x ,double y, int id) throws IOException {
        double neighbourX, neighbourY;
        double [] distances = new double[4];

        //bricht routing ab falls richtige zone gefunden wurde
        routingCheckZone(ip,x,y,id);


        //bei 1 anfangen?
        for(int i=0; i<=distances.length ; i++){

            //Die x und y Werte des Nachbarn von der DB holen
            neighbourX = nDB.getPunktXNeighbor(i);
            neighbourY = nDB.getPunktYNeighbor(i);
            // Nun diese Distanzen berechnen und die am nÃ¤chsten an dem Punkt zu dem gerouted werden soll.
            distances[i] = computeDistance(x,y,neighbourX,neighbourY);

        }
        int index = compareValues(distances);
        String connectIP = nDB.getUip(index);

        //getNeighbour(index).getIP(); in dem socket eintragen
        socket = new Socket(connectIP,PORTNR);


        RoutHelper rh = new RoutHelper(ip,x,y,id);
        //routing request
        client.sendRoutHelperAsByteArray(socket,rh);
        //// TODO: 07.09.2017 sende ein receiveRoutingRequest  an ip
        //// TODO: 14.08.2017 Verbindungsaufbau zu dem Neighbour der an Stelle == Index steht und IP und x,y-Werte Ã¼bertragen so das dieser weiter routen kann, bzw recreive routing request bei ihm aufrufen
    }*/


    /**
     * Mit dieser Methode findet ein neuer Knoten einen Einstiegspunkt in das CAN, indem er den Bootstrapserver nach einer IP anfragt
     *
     */
    /*
    private static void requestJoin() throws JSONException {
        AllIPsActivity all = new AllIPsActivity();
        if(all.ipsList.isEmpty()){
            Log.e("Fehler " , "Ist leer!");
        }else{

            for (int i = 0; i <all.ipsList.size(); i++){



                Log.d("Inhalt ", all.ipsList.get(i).toString());
            }

        }
        //// TODO: 15.08.2017 getBootsTrapIP() Methode
        //// TODO: 15.08.2017 nun Verbindung zu dieser IP herstellen und routing-Anfrage mit(eigener IP und x ,y Werten, id und isNode als Parameter)
    }
    */

    /**
     * Methode um ein Bild auf dem GerÃ¤t und dann auch im CAN zu lÃ¶schen
     * @param id Des zu lÃ¶schenden Bildes
     * @param x Des Bildes
     * @param y Des Bildes
     */
    private void delPicInCan(String ip, int id, double x, double y){
        // TODO: 15.08.2017 Erst muss delPicInCan aufgerufen werden bevor das Bild auf dem eigenen GerÃ¤t gelÃ¶scht wird
        // TODO: 15.08.2017 checke deinen foreignData Table um zu sehen ob die id,x und y Ã¼bereinstimmen, Falls dies der Fall ist lÃ¶sche das Bild
        // TODO: 15.08.2017
        // benÃ¶tigen die Methoden getID, getX und getY auf den foreignDataTable, @somar wie lÃ¶scht man ein Bild auf dem GerÃ¤t
    }

    /**
     * Diese Methode wird aufgerufen wenn ein neues Bild geschossen wurde und in CAN eingefÃ¼gt werden soll
     * @param ip IP des Besitzers
     * @param x
     * @param y
     * @param fotoId
     */
    public void placePicInCan(String ip, double x, double y, int fotoId) throws IOException {
        //receivePicRoutingRequest(ip, x, y, fotoId);
    }

    /**
     * Diese Methode berechnet die Distanz zwischen den zu Routenden Knoten und den Neighbours des aktuellen Knotens(der routet)
     * @param x Des zu routenden Knoten
     * @param y Des zu routenden Knoten
     * @param neighbourX
     * @param neighbourY
     * @return Die Distanz zwischen den 2 Punkten
     */
    public double computeDistance(double x, double y, double neighbourX, double neighbourY) {
        double dis = Math.abs(x - neighbourX) + Math.abs(y - neighbourY);
        return dis;
    }

    /**
     * Methode mit der ein neuer Knoten seinen Peers seine ID und IP (zweck=zum Eintragen in PeersDB) mitteilen kann
     * @param ip
     *//*
    private void informPeersAboutYourself(String ip) {
        //// TODO: 0114.08.27    user.getUid(); von DB, user.getIP von DB
        //long uid = dateiMemoDbSource.getUid();


        //// TODO: 14.08.2017 sende an alle deine Peers ein setPeer mit diesen Informationen
        peerMemo.setPeerId(uid);
        peerMemo.setPeerIp(ip);
        //// TODO: 14.08.2017 user.getUid(); von DB, user.getIP von DB
        //// TODO: 14.08.2017 sende an alle deine Peers ein setPeer mit diesen Informationen
    }*/

    /**
     * Vergleiche alle Distanzen der Nachbarn
     * @param distances Array mit allen Distanzen der Neighbour zu dem zu routenden Knoten
     * @return den index(in NeighbourDB) mit der geringsten Distanz
     */
    public int compareValues(double [] distances){
        int index = 0;
        double temp =  distances[0];
        for(int i= 1 ; i< distances.length; i++){
            if(temp > distances[i]){

                temp = distances[i];
                index = i;
            }
        }
        return index;
    }


    /**
     * Dient dazu seine eigenen Bilder nach ausfallen des eigenen GerÃ¤tes wieder zu downloaden
     * @param ip Eigene IP
     * @param x des Bildes
     * @param y des Bildes
     * @param fotoID
     * @param uid
     * @throws IOException
     *//*
    public void checkForOwnData(String ip, double x, double y, int fotoID, int uid) throws IOException {
        for(int i =0; i<=4; i++){
            //muss ich hier this.ownDataDB machen?
            int tempFID  = ownDataDB.getFotoId(i);
            long tempUID = ownDataDB.getUID(i);
            if(tempFID == fotoID && tempUID == uid){
                socket = new Socket(ip,PORTNR);
                // TODO: 08.09.2017   get file mit dieser fotoID und UID
                File file = null;
                client.sendImageAsByteArray(socket,file);

            }
        }


        picRouting(ip,x,y,fotoID,uid);
        // TODO: 28.08.2017  checken ob OwnData( auch wirklich die Bilder)
    }*/

    /**
     * Methode die aufgerufen wird wenn das routing beendet ist und die DB's des neuen Knoten updaten muss
     * @param ip Des neuen Knotens
     */
    private void replyToRequest(String ip) throws IOException {
        socket = new Socket(ip,PORTNR);


        //muss setPeers aufrufen
        //Liste senden?
        //// TODO: 15.08.2017 Verbindung zu IP herstellen, und setPeer und setNeighbour aufrufen auf diesem Knoten(mit den eigenen Peers und Neighbour-Werten)
        //// TODO: 15.08.2017 nach update der eigenen PeersDB muss Ã¼berprÃ¼ft werden ob die Anzahl Peers nun 3 betrÃ¤gt, falls dies der Fall ist => Split
        // TODO: 05.09.2017 Node erstellen. und an IP senden
    }


    public void increasePeersCount(){
        if(checkIfMaxPeersCount()){

        }else{
            countPeers++;
        }
    }

    public void decreasePeersCount(){
        if(countPeers < 1){

        }else{
            countPeers--;
        }
    }


    private boolean checkIfMaxPeersCount(){
        if (countPeers == maxPeers){
            return true;
        }else{
            return false;
        }
    }


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Corner getTopRight(){
        return getMyZone().getTopRight();
    }

    public Corner getTopLeft(){
        return getMyZone().getTopLeft();
    }

    public Corner getBottomRight(){
        return getMyZone().getTopRight();
    }

    public Corner getBottomLeft(){
        return getMyZone().getBottomLeft();
    }

    public void setTopRight(Corner topRight){
        this.topRight = topRight;
        getMyZone().setTopRight(topRight);
    }

    public void setTopLeft(Corner topLeft){
        this.topLeft = topLeft;
        getMyZone().setTopLeft(topLeft);
    }
    public void setBottomRight(Corner bottomRight){
        this.bottomRight = bottomRight;
        getMyZone().setBottomRight(bottomRight);
    }
    public void setBottomLeft(Corner bottomLeft){
        this.bottomLeft = bottomLeft;
        getMyZone().setBottomLeft(bottomLeft);
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

     public String getIP() {
     return iP;
     //return dateiMemoDbSource.getIp(dateiMemoDbSource.getUid());
     }
    public void setIP(String IP) {
        this.iP = IP;
    }

    public int getCountPeers() {
        return countPeers;
    }

    public void setCountPeers(int countPeers) {
        this.countPeers = countPeers;
    }

    public Zone getMyZone(){
        return ownZone;
    }

    public void setMyZone(Zone zone){
        this.ownZone = zone;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("topLeft: " + ownZone.getTopLeft().toString() +
                "\n top right: " + ownZone.getTopRight().toString() +
                "\n bottom left: " + ownZone.getBottomLeft().toString() +
                "\n bottom right: " + ownZone.getBottomRight().toString());

        return sb.toString();
    }




    /*public static void main(String [] args) throws JSONException {
        requestJoin();
    }*/


}
