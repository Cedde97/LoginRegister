package source;

/**
 * Created by en on 13.08.17.
 */

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
import model.Neighbour;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NeighborDbSource {
    private static final String LOG_TAG = NeighborDbSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DateiMemoDbHelper dbHelper;
    private DateiMemoDbSource dateiMemoDbSource;
    private Neighbour neighborMemo;

    //neue Array String für Neighbor
    private String[] columns_Neighbor = {
            DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX,
            DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY,
            DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX,
            DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX,
            DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY,
            DateiMemoDbHelper.COLUMN_PUNKTX,
            DateiMemoDbHelper.COLUMN_PUNKTY,
            DateiMemoDbHelper.COLUMN_UIP,
            DateiMemoDbHelper.COLUMN_RTT,
            DateiMemoDbHelper.COLUMN_UID,
            //DateiMemoDbHelper.COLUMN_CHECKED
    };

    public NeighborDbSource(){
        neighborMemo = new Neighbour();
    }



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
    //    private double cornerTopRightX;
    //    private double cornerTopRightY;
    //    private double cornerTopLeftX;
    //    private double cornerTopLeftY;
    //    private double cornerBottomRightX;
    //    private double cornerBottomRightY;
    //    private double cornerBottomLeftX;
    //    private double cornerBottomLeftY;
    //    private double punktX;
    //    private double punktY;
    //    private String UIP;
    //    private double RTT;
    //    private boolean checked;
    //    private long uid;
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
    public int createNeighborMemo(Neighbour neighborMemo) {
        database = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_NID, neighborMemo.getUid());
        //values.put(DateiMemoDbHelper.COLUMN_CHECKED, neighborMemo.isChecked());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX, neighborMemo.getCornerTopLeftX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY, neighborMemo.getCornerTopLeftY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX, neighborMemo.getCornerTopRightX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY, neighborMemo.getCornerTopRightY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX, neighborMemo.getCornerBottomLeftX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY, neighborMemo.getCornerBottomLeftY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, neighborMemo.getCornerBottomRightX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY, neighborMemo.getCornerBottomRightY());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTX, neighborMemo.getPunktX());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTY, neighborMemo.getPunktY());
        values.put(DateiMemoDbHelper.COLUMN_UIP, neighborMemo.getUIP());
        values.put(DateiMemoDbHelper.COLUMN_RTT, neighborMemo.getRTT());
        //automatisch
        //values.put(DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID, neighborMemo.getNeighbour_id());



        //
        //insert row
        //insert muss long
        //
        int neighbor_Id = (int) database.insert(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, null, values);
//        database.query(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, null, DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + neighbor_Id,
//                null, null, null, null);
        DatabaseManager.getInstance().closeDatabase();



        return neighbor_Id;
    }

    /*
  *
  *
  *                                           Delete data
  *
  *
  *
  * */
    public void deleteNeighbormemo() {
        database = DatabaseManager.getInstance().openDatabase();
        database.delete(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, null, null);
        DatabaseManager.getInstance().closeDatabase();
        //Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + neighborMemo.getUid() + " Inhalt: " + neighborMemo.toString());
    }
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
    /*
    *
    *               Update Corner Top Right X und Y
    *
    *
    *
    *
    *
    * */
    public void updateCornerTopRightXNeighbor(int neighbour_id, double newCornerTopRightX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX, newCornerTopRightX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerTopRightYNeighbor(int neighbour_id, double newCornerTopRightY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY, newCornerTopRightY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    //
    // ================================================================================================================================
    //


    /*
    *               Update Corner Top Left X und Y
    *
    *
    *
    *
    *
    * */
    public void updateCornerTopLeftXNeighbor(int neighbour_id, double newCornerTopLeftX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX, newCornerTopLeftX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerTopLeftYNeighbor(int neighbour_id, double newCornerTopLeftY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY, newCornerTopLeftY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    //
    // ================================================================================================================================
    //

    /*
   *               Update Corner Bottom Right X und Y
   *
   *
   *
   *
   *
   *
   * */
    public void updateCornerBottomRightXNeighbor(int neighbour_id, double newCornerBottomRightX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, newCornerBottomRightX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerBottomRightYNeighbor(int neighbour_id, double newCornerBottomRightY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY, newCornerBottomRightY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    //
    // ================================================================================================================================
    //

    /*
  *               Update Corner Bottom Left X und Y
  *
  *
  *
  *
  *
  *
  * */
    public void updateCornerBottomLeftXNeighbor(int neighbour_id, double newCornerBottomLeftX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX, newCornerBottomLeftX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerBottomLeftYNeighbor(int neighbour_id, double newCornerBottomLeftY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY, newCornerBottomLeftY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    /*
    *  ================================================================================================================================
    */

    /*
    *               Update Round Trip Time
    *
    *
    *
    *
    *
    *
    * */
    public void udpateRTT(int neighbour_id, double newRTT) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_RTT, newRTT);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_NEIGHBOR_LIST, //UPDATE which TABLE
                values, // SET query
                DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + "=" + neighbour_id, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    /*
    *  ================================================================================================================================
    */

    /*
    *           Get
    *
    *
    *           Corner Bottom Right X und Y
    *
    *
    *
    * */
    public double getCornerBottomRightXNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();

        double cornerBottomRightX;
        cornerBottomRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX));



//        while(!cursor.isAfterLast()) {
//            CornerBottomRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX));
//            //CornerBottomRightList.add(CornerBottomRight);
//            Log.d(LOG_TAG, selectQuery);
//            cursor.moveToNext();
//        }

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerBottomRightX;
    }

    public double getCornerBottomRightYNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerBottomRightY;
        cornerBottomRightY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY));

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerBottomRightY;
    }
    //
    // ================================================================================================================================
    //


    /*
    *           Get
    *
    *
    *           Corner Bottom Left X und Y
    *
    *
    *
    * */
    public double getCornerBottomLeftXNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomLeftList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerBottomLeftX;
        cornerBottomLeftX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX));

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerBottomLeftX;
    }

    public double getCornerBottomLeftYNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomLeftList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerBottomLeftY;
        cornerBottomLeftY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY));

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerBottomLeftY;
    }
    //
    // ================================================================================================================================
    //


    /*
    *           Get
    *
    *
    *           Corner Top Right X und Y
    *
    *
    * */
    public double getCornerTopRightXNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerTopRightX;
        cornerTopRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX));

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerTopRightX;
    }

    public double getCornerTopRightYNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerTopRightY;
        cornerTopRightY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY));


        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerTopRightY;
    }

    //
    // ================================================================================================================================
    //


    /*
    *           Get
    *
    *
    *           Corner Top Left X und Y
    *
    *
    * */
    public double getCornerTopLeftXNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerTopLeftX;
        cornerTopLeftX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX));


        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerTopLeftX;
    }

    public double getCornerTopLeftYNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double cornerTopLeftY;
        cornerTopLeftY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY));


        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return cornerTopLeftY;
    }
    //
    // ================================================================================================================================
    //

    /*
    *           Get
    *
    *
    *           Punkt X
    *
    *
    * */
    public double getPunktXNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_PUNKTX +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        double punktXNeighbor;
        punktXNeighbor = c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTX));

        c.close();

        DatabaseManager.getInstance().closeDatabase();

        return punktXNeighbor;
    }
    //
    // ================================================================================================================================
    //



    /*
    *           Get
    *
    *
    *           Punkt Y
    *
    *
    * */
    public double getPunktYNeighbor(int index) {
        database = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_PUNKTY +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        double punktYNeighbor;
        punktYNeighbor = c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTY));

        c.close();

        DatabaseManager.getInstance().closeDatabase();

        return punktYNeighbor;
    }
    //
    // ================================================================================================================================
    //


    /*
    *           Get
    *
    *
    *           IP
    *
    *
    * */
    public String getUip(int index) {
        database = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_UIP +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        String Uip;
        Uip = c.getString(c.getColumnIndex(DateiMemoDbHelper.COLUMN_UIP));

        c.close();

        DatabaseManager.getInstance().closeDatabase();

        return Uip;
    }
    //
    // ================================================================================================================================
    //

    /*
   *           Get
   *
   *
   *           RTT
   *
   *
   * */
    public double getRTT(long index) {
        database = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_RTT +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        double RTT;
        RTT = c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_RTT));

        c.close();

        DatabaseManager.getInstance().closeDatabase();

        return RTT;
    }
    //
    // ================================================================================================================================
    //

    /**
     * @author Joshua Zabel
     * @param index
     * @return die NID des Neighbours an stelle index
     */
    public long getNID(int index) {
        database = DatabaseManager.getInstance().openDatabase();

        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_NID +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID + " = " + index;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        long uID;
        uID = c.getLong(c.getColumnIndex(DateiMemoDbHelper.COLUMN_NID));

        c.close();

        DatabaseManager.getInstance().closeDatabase();

        return uID;
    }

    public List<Neighbour> getAllNeighborMemo() {
        List<Neighbour> NeighborMemoList = new LinkedList<Neighbour>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_NEIGHBOR_LIST;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);

//        int idChecked = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
//        int intValueChecked = cursor.getInt(idChecked);
//        boolean isChecked = (intValueChecked != 0);


        //3. Durchführen Zeile und füge in List hinzu
        Neighbour neighborMemo = null;
        if (cursor.moveToFirst()) {
            do {
                neighborMemo = new Neighbour();
                neighborMemo.setUid(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_NID)));
                //neighborMemo.setChecked(isChecked);
                neighborMemo.setCornerTopLeftX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTX)));
                neighborMemo.setCornerTopLeftY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTY)));
                neighborMemo.setCornerTopRightX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTX)));
                neighborMemo.setCornerTopRightY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTY)));
                neighborMemo.setCornerBottomLeftX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTX)));
                neighborMemo.setCornerBottomLeftY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTY)));
                neighborMemo.setCornerBottomRightX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTX)));
                neighborMemo.setCornerBottomRightY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTY)));
                neighborMemo.setPunktX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTX)));
                neighborMemo.setPunktY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTY)));
                neighborMemo.setUIP(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_UIP)));
                neighborMemo.setRTT(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_RTT)));
                neighborMemo.setNeighbour_id(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_NEIGHBOUR_ID)));


                // Add book to books
                NeighborMemoList.add(neighborMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return NeighborMemoList;
    }


    public int getCount(){
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID +" FROM " + DateiMemoDbHelper.TABLE_NEIGHBOR_LIST ;
        Cursor c = database.rawQuery(selectQuery,null);

        c.moveToLast();

        int i = c.getInt(c.getColumnIndex(DateiMemoDbHelper.COLUMN_NEIGHBOUR_ID));

        c.close();
        DatabaseManager.getInstance().closeDatabase();
        return i;
    }


    /**
     * @author Joshua Zabel
     * @return den Neighbour an der stelle index
     */
//    public Neighbour getNeighbour(int index){
//        database = DatabaseManager.getInstance().openDatabase();
//
//        double topRightX = getCornerTopRightXNeighbor(index);
//        double topRightY = getCornerTopRightYNeighbor(index);
//        double topLeftX  = getCornerTopLeftXNeighbor(index);
//        double topLeftY  = getCornerTopLeftYNeighbor(index);
//        double bottomRightX = getCornerBottomRightXNeighbor(index);
//        double bottomRightY= getCornerBottomRightYNeighbor(index);
//        double bottomLeftX = getCornerBottomLeftXNeighbor(index);
//        double bottomLeftY = getCornerBottomLeftYNeighbor(index);
//        double punktX = getPunktXNeighbor(index);
//        double punktY = getPunktYNeighbor(index);
//        String uIp    = getUip(index);
//        double rtt    = getRTT(index);
//        long uid      = getUID(index);
//
//        Neighbour n = new Neighbour(uid,topRightX,topRightY,topLeftX,topLeftY,bottomRightX,bottomRightY,bottomLeftX,bottomLeftY,punktX,punktY,uIp,rtt);
//        return n;
//    }

    public List<Neighbour> getEachNeigbourMemo() {
        List<Neighbour> NeighborMemoList = new LinkedList<Neighbour>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_NEIGHBOR_LIST;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);


        //3. Durchführen Zeile und füge in List hinzu
        Neighbour neighborMemo = null;
        if (cursor.moveToFirst()) {
            do {
                //neighborMemo = new Neighbour();
                neighborMemo.setUid(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_NID)));
                //neighborMemo.setChecked(isChecked);
                neighborMemo.setCornerTopLeftX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTX)));
                neighborMemo.setCornerTopLeftY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTY)));
                neighborMemo.setCornerTopRightX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTX)));
                neighborMemo.setCornerTopRightY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTY)));
                neighborMemo.setCornerBottomLeftX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTX)));
                neighborMemo.setCornerBottomLeftY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTY)));
                neighborMemo.setCornerBottomRightX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTX)));
                neighborMemo.setCornerBottomRightY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTY)));
                neighborMemo.setPunktX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTX)));
                neighborMemo.setPunktY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTY)));
                neighborMemo.setUIP(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_UIP)));
                neighborMemo.setRTT(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_RTT)));
                neighborMemo.setNeighbour_id(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_NEIGHBOUR_ID)));

                //add neighbour to NeighbourList
                NeighborMemoList.add(neighborMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();

        DatabaseManager.getInstance().closeDatabase();

        return NeighborMemoList;
    }
}
