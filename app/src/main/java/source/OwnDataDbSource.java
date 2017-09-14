package source;

/**
 * Created by en on 13.08.17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import android.content.ContentValues;
import android.database.Cursor;

import model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class OwnDataDbSource {
    private static final String LOG_TAG = OwnDataDbSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DateiMemoDbHelper dbHelper;
    private DateiMemoDbSource dateiMemoDbSource;
    private OwnDataMemo ownDataMemo;

    //Array
    private String[] columns_OwnData = {
            DateiMemoDbHelper.COLUMN_FILEID,
            DateiMemoDbHelper.COLUMN_UID,
            //DateiMemoDbHelper.COLUMN_CHECKED
    };


    public OwnDataDbSource(){
       // ownDataMemo = new OwnDataMemo();
    }


    //
    //==================================================================================================================
    //

    /*
   *
   *
   *           Converting List to Double -- List to Integer -- List to Long
   *
   * */
    public double listToDouble(List<Double> list){
        double[] tmp = new double[list.size()];
        double ret = 0;

        for (int i = 0; i < list.size(); ++i) { //iterate over the elements of the list
            tmp[i] = Double.valueOf(list.get(i));
        }
        for (int j = 0; j < tmp.length; ++j) {
            ret = tmp[j];
        }

        return ret;
    }

    public int listToInt(List<Integer> list){
        int[] tmp = new int[list.size()];
        int ret = 0;

        for (int i = 0; i < list.size(); ++i) { //iterate over the elements of the list
            tmp[i] = Integer.valueOf(list.get(i));
        }
        for (int j = 0; j < tmp.length; ++j) {
            ret = tmp[j];
        }

        return ret;
    }

    public long listToLong(List<Long> list){
        long[] tmp = new long[list.size()];
        long ret = 0;

        for (int i = 0; i < list.size(); ++i) { //iterate over the elements of the list
            tmp[i] = Long.valueOf(list.get(i));
        }
        for (int j = 0; j < tmp.length; ++j) {
            ret = tmp[j];
        }

        return ret;
    }


    //
    //==================================================================================================================
    //


    //
    //    public long uid;
    //    public boolean checked;
    //    public int fileId
    //----------------------------- Insert, delete, update, get values in Table ---------------------------------
    //
    //
    /*
    *
    *                                             Insert Data
    *
    *
    * */
    public int createOwnData(OwnDataMemo ownDataMemo) {
        database = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_OID, ownDataMemo.getUid());
        //automatisch
        values.put(DateiMemoDbHelper.COLUMN_FILEID, ownDataMemo.getFileId());

        //
        //insert row
        //insert muss long
        //
        int ownData_Id = (int)database.insert(DateiMemoDbHelper.TABLE_OWNDATA_LIST, null, values);

        DatabaseManager.getInstance().closeDatabase();

        return ownData_Id;
    }

    /*
*
*
*                                           Delete data
*
*
*
* */
    public void deleteOwnData() {
        database = DatabaseManager.getInstance().openDatabase();
        database.delete(DateiMemoDbHelper.TABLE_OWNDATA_LIST, null, null);
        DatabaseManager.getInstance().closeDatabase();
        //Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + ownDataMemo.getUid() + " Inhalt: " + ownDataMemo.toString());
    }
    /*
    *
    * ==================================================================================================================
    * */

    /*
*
*
*               Hilfklasse für Update Methode und Insert Methode
*
* */
//    private OwnDataMemo cursorToOwnData(Cursor cursor) {
//        int idIndex = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_UID);
//        int idChecked = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
//        int idFileId = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_FILEID);
//
//
//
//        long uid = cursor.getLong(idIndex);
//
//        int intValueChecked = cursor.getInt(idChecked);
//        boolean isChecked = (intValueChecked != 0);
//
//        int fileId = cursor.getInt(idFileId);
//
//
//
//        OwnDataMemo ownDataMemo = new OwnDataMemo(uid, isChecked, fileId);
//
//        return ownDataMemo;
//    }

    /**
     * @author Joshua Zabel
     * @param index
     * @return
     */
    public int getFileId(int index){
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_FILEID+" FROM " + DateiMemoDbHelper.TABLE_OWNDATA_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_OID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery,null);

        if(cursor != null){
            int fotoId = cursor.getInt(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_FILEID));
            if(fotoId >= 0){
                cursor.close();
                DatabaseManager.getInstance().closeDatabase();
                return fotoId;
            }
        }
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return -1;
    }

    /**
     * @author Joshua Zabel
     * @param index
     * @return
     */
    public long getUID(int index){
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_OID +" FROM " + DateiMemoDbHelper.TABLE_OWNDATA_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_FILEID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery,null);
        cursor.moveToFirst();
        //if(cursor != null){
            long uID = cursor.getInt(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_OID));
            //if(uID >= 0){
                cursor.close();
                DatabaseManager.getInstance().closeDatabase();
                return uID;
            //}

        //}
        //cursor.close();
        //DatabaseManager.getInstance().closeDatabase();
        //return -1;
    }
    /*
   *
   *
   *                   get Foto ID
   *
   *
   * */
    public int getFileId(long uid) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<long> UidList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_FILEID + " FROM " + DateiMemoDbHelper.TABLE_OWNDATA_LIST+ " WHERE "
                + DateiMemoDbHelper.COLUMN_OID + " = " + uid;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        int fileId;
        fileId = cursor.getInt(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_FILEID));

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return fileId;
    }
    //
    // ================================================================================================================================
    //

    /*
    *
    *           Get UID
    *
    * */
    public double getUidOwn() {
        database = DatabaseManager.getInstance().openDatabase();
        DatabaseManager.getInstance().closeDatabase();
        return dateiMemoDbSource.getUid();
    }

    public List<OwnDataMemo> getAllOwnData() {
        List<OwnDataMemo> OwnDataList = new LinkedList<OwnDataMemo>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_OWNDATA_LIST;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);


        //3. Durchführen Zeile und füge in List hinzu
        OwnDataMemo ownDataMemo = new OwnDataMemo();
        if (cursor.moveToFirst()) {
            do {
                //ownDataMemo = new OwnDataMemo();
                ownDataMemo.setUid(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_OID)));
                //ownDataMemo.setChecked(isChecked);
                ownDataMemo.setFileId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_FILEID)));


                // Add Own Data to own data List
                OwnDataList.add(ownDataMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return OwnDataList;
    }

    public List<OwnDataMemo> getEachOwnData(int file_id) {
        List<OwnDataMemo> OwnDataList = new LinkedList<OwnDataMemo>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_OWNDATA_LIST+ " WHERE "
                + DateiMemoDbHelper.COLUMN_FILEID + " = " + file_id;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);


        //3. Durchführen Zeile und füge in List hinzu
        OwnDataMemo ownDataMemo = null;
        if (cursor.moveToFirst()) {
            do {
                //ownDataMemo = new OwnDataMemo();
                ownDataMemo.setUid(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_OID)));
                //ownDataMemo.setChecked(isChecked);
                ownDataMemo.setFileId(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_FILEID)));


                // Add Own Data to own data List
                OwnDataList.add(ownDataMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return OwnDataList;
    }
}
