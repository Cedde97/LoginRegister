package source;

/**
 * Created by en on 13.08.17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import android.content.ContentValues;
import android.database.Cursor;

import source.DatabaseManager;
import source.DateiMemoDbHelper;
import model.Node;
import model.ForeignData;
import model.OwnDataMemo;
import model.PeerMemo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.util.Objects;

public class PeerDbSource implements java.io.Serializable{
    private static final long serialVersionUID = -6065692797211394914L;
    private static final String LOG_TAG = PeerDbSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DateiMemoDbHelper dbHelper;
    private DateiMemoDbSource dateiMemoDbSource = new DateiMemoDbSource();
    //private PeerMemo peerMemo;  // TODO: löschen, da nie benutzt

    //private static final int    maxPeers= Node.maxPeers;  // TODO: model.Node.maxPeers verwenden

    public PeerDbSource(){

        //peerMemo = new PeerMemo();
    }





    //
    //==================================================================================================================
    //





    //
    //==================================================================================================================
    //


    //
    //    private long uid;
    //    public int peerId;
    //    public String peerIp;
    //    private boolean checked;
    //
    //----------------------------- Insert, delete, update, get values in Table ---------------------------------
    //
    //
    /*
    *
    *                                             Insert Data
    *
    *
    * */
    public void createPeerMemo(PeerMemo peerMemo) {     // TODO: addPeer macht mehr Sinn als Name
        database = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //automatisch
        //values.put(DateiMemoDbHelper.COLUMN_PEERID, peerMemo.getPeerId());
        values.put(DateiMemoDbHelper.COLUMN_PEERIP, peerMemo.getPeerIp());
        values.put(DateiMemoDbHelper.COLUMN_PID, peerMemo.getUid());

        //
        //insert row
        //
        database.insert(DateiMemoDbHelper.TABLE_PEER_LIST, null, values);
        DatabaseManager.getInstance().closeDatabase();

    }

    /*
    *
    *
    *                                           Delete data
    *
    *
    *
    * */
    public void deleteAllPeerMemo() {      // TODO: diese Methode wuerde alle Peers von jedem Node loeschen, warum nicht als deleteAllPeers nennen? Ist das ueberhaupt so gewollt?
        database = DatabaseManager.getInstance().openDatabase();
        database.delete(DateiMemoDbHelper.TABLE_PEER_LIST, null, null);
        DatabaseManager.getInstance().closeDatabase();
        //Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + peerMemo.getUid() + " Inhalt: " + peerMemo.toString());
    }

    //
    //Es wird nur eine Peer löschen,wo wir die peer_id bestimmen. (maximal peer_id = 3)
    //
    public void deleteEachPeer(int peer_id){    // TODO: diese Methode loescht nur die Eintrage mit einem bestimmtem peerId.
        database = DatabaseManager.getInstance().openDatabase();
        database.delete(DateiMemoDbHelper.TABLE_PEER_LIST, //which Table
                DateiMemoDbHelper.COLUMN_PEERID +" = "+ peer_id, //where clause -- "bestimmte peer zu löschen"
                null);
        DatabaseManager.getInstance().closeDatabase();
    }

    // TODO: wir brauchen eine Methode, die nur ein bestimmtes Peer loescht!

    /*
    *
    * ==================================================================================================================
    * */


     /*
    *  ----------------------------------              update data        ----------------------------------------------------------------
    *
    *
    *
    *
    * */

//    public PeerMemo updatePeerMemo(long newUid, int newPeerId, int newPeerIp, boolean newChecked) {
//        int intValueChecked = (newChecked)? 1 : 0;
//        newUid = dateiMemoDbSource.getUid();
//        ContentValues values = new ContentValues();
//        values.put(DateiMemoDbHelper.COLUMN_UID, newUid);
//        values.put(DateiMemoDbHelper.COLUMN_PEERID, newPeerId);
//        values.put(DateiMemoDbHelper.COLUMN_PEERIP, newPeerIp);
//        values.put(DateiMemoDbHelper.COLUMN_CHECKED, intValueChecked);
//
//
//        database.update(DateiMemoDbHelper.TABLE_PEER_LIST,
//                values,
//                DateiMemoDbHelper.COLUMN_UID + "=" + newUid,
//                null);
//
//        Cursor cursor = database.query(DateiMemoDbHelper.TABLE_PEER_LIST,
//                columns_Peer, DateiMemoDbHelper.COLUMN_UID + "=" + newUid,
//                null, null, null, null);
//
//        cursor.moveToFirst();
//        PeerMemo peerMemo = cursorToPeerMemo(cursor);
//        cursor.close();
//
//        return peerMemo;
//    }


    /*
    *
    *
    *                   Peerscount -- Increase -- Decrease
    *
    *
    * */
    public int getPeersCount() {
        database = DatabaseManager.getInstance().openDatabase();
        String countQuery = "SELECT * FROM " + DateiMemoDbHelper.TABLE_PEER_LIST;   // TODO: heisst dass, das in unserem Datenbank nur die eigene Peers speichern?
        Cursor cursor = database.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        // return count
        return count;
    }


    public void decreaseCountPeers (int peer_id) {  // TODO: peerCount braucht man nicht unbedingt in der Datenbank spichern!
        if (getPeersCount() == 0){
            System.out.println("No more Peers");
        }
        deleteEachPeer(peer_id);   // TODO: BUG, da maxPeers ist nicht der UID der Node!!!
        //return getPeersCount();
    }

//    public int increaseCountPeers (PeerMemo peerMemo) { // TODO: wir haben schon eine createPeerMemo() Methode fuer diesen Zweck!
//        if (getPeersCount() == 2){
//            System.out.println("Prepare to split");
//        }
//        createPeerMemo(peerMemo);
//        return getPeersCount();
//    }
    //
    //===============================================================================================
    //




    /*
    *           Get
    *
    *
    *           All Data
    *
    *
    *
    * */


    /*
    *
    *           Get UID
    *
    * */
    public int getUidPeer() {
        //database = DatabaseManager.getInstance().openDatabase();
        int id = dateiMemoDbSource.getUid();
        //DatabaseManager.getInstance().closeDatabase();
        return id;
    }

<<<<<<< HEAD

    public int getPeer(int index) // TODO: was genau sollte diese Methode liefern?
    {
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT " + DateiMemoDbHelper.COLUMN_PEERID + " FROM " + DateiMemoDbHelper.TABLE_PEER_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_PEERID + " = " + index;  /* TODO: BUG diese SQL Anfrage liefert einchach den Wert von dem "index" Argument von dieser Methode! Siehe Aufschluesselung unten:

           SELECT peerId
           FROM peer_list
           WHERE peerId = ?
         */

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        int Id = cursor.getInt(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PEERID));

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return Id;
    }
=======
//    public int getPeer(long index)  // TODO: was genau sollte diese Methode liefern?
//    {
//        database = DatabaseManager.getInstance().openDatabase();
//        String selectQuery = "SELECT " + DateiMemoDbHelper.COLUMN_PEERID + " FROM " + DateiMemoDbHelper.TABLE_PEER_LIST + " WHERE "
//                + DateiMemoDbHelper.COLUMN_PEERID + " = " + index;  /* TODO: BUG diese SQL Anfrage liefert einchach den Wert von dem "index" Argument von dieser Methode! Siehe Aufschluesselung unten:
//
//           SELECT peerId
//           FROM peer_list
//           WHERE peerId = ?
//         */
//
//        Cursor cursor = database.rawQuery(selectQuery, null);
//
//        cursor.moveToFirst();
//
//        int Id = cursor.getInt(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PEERID));
//
//        cursor.close();
//
//        DatabaseManager.getInstance().closeDatabase();
//
//        return Id;
//    }
>>>>>>> 2f248724cce97bdc1027b5c4891aa4bf698ebf58


    /*
    *
    *
    *           Get Peer Ip
    *
    * */
    public String getPeerIp(int index) {
        //List<Integer> PeerIdList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_PEERIP + " FROM " + DateiMemoDbHelper.TABLE_PEER_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_PID + " = " + index;

        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        String peerIp;
        peerIp = cursor.getString(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PEERIP));


        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return peerIp;
    }

    public List<PeerMemo> getAllPeer() {
        List<PeerMemo> PeerMemoList = new LinkedList<PeerMemo>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_PEER_LIST;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);

        //3. Durchführen Zeile und füge in List hinzu
        PeerMemo peerMemo = null;
        if (cursor.moveToFirst()) {
            do {
                peerMemo = new PeerMemo();
                peerMemo.setUid(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PID)));
                //peerMemo.setChecked(isChecked);
                peerMemo.setPeerIp(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PEERIP)));
                peerMemo.setPeerId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PEERID)));


                // Add Peer to PeerList
                PeerMemoList.add(peerMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return PeerMemoList;
    }

    //
    //Bestimmte Info von einzelner Peer rauszuholen
    //
    public List<PeerMemo> getEachPeer(int peer_id) {
        List<PeerMemo> PeerMemoList = new LinkedList<PeerMemo>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_PEER_LIST+ " WHERE "
                + DateiMemoDbHelper.COLUMN_PEERID + " = " + peer_id;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);


        //3. Durchführen Zeile und füge in List hinzu
        PeerMemo peerMemo = null;
        if (cursor.moveToFirst()) {
            do {
                peerMemo = new PeerMemo();
                peerMemo.setUid(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PID)));
                //peerMemo.setChecked(isChecked);
                peerMemo.setPeerIp(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_PEERIP)));
                peerMemo.setPeerId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_PEERID)));


                // Add peer to peer List
                PeerMemoList.add(peerMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return PeerMemoList;
    }

}


