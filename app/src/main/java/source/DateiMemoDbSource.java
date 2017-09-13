package source;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import android.content.ContentValues;
import android.database.Cursor;

import source.DateiMemoDbHelper;
import model.Node;
import source.DatabaseManager;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.R.attr.id;


public class DateiMemoDbSource {

    private static final String LOG_TAG = DateiMemoDbSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DateiMemoDbHelper dbHelper;
    private PeerDbSource peerDbSource;
    private Node dateiMemo;

    //neue Array String für Datei
    private String[] columns = {
            DateiMemoDbHelper.COLUMN_UID, //------------------------ Table Datei
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
            DateiMemoDbHelper.COLUMN_IP,
            DateiMemoDbHelper.COLUMN_COUNTPEERS,
            //DateiMemoDbHelper.COLUMN_CHECKED
    };

    public DateiMemoDbSource(){
        dateiMemo = new Node();
    }

    //
    //==================================================================================================================
    //


    /*
   *
   *
   *           Converting List to Double -- List to Integer -- List to int
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

    //
    //==================================================================================================================
    //


    //
    // String username, String password, int uid, boolean checked,
    // double cornerTopRightX und Y, double cornerTopLeftx und Y, double cornerBottomRightX und Y,
    // double cornerBottomLeftX und Y, double punktX, double punktY, double IP, int countPeers
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
    public int createDateiMemo(Node dateiMemo) {
        database = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        //values.put(DateiMemoDbHelper.COLUMN_UID, dateiMemo.getUid());
        //values.put(DateiMemoDbHelper.COLUMN_CHECKED, dateiMemo.isChecked());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX, dateiMemo.getTopLeft().getX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY, dateiMemo.getTopLeft().getY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX, dateiMemo.getTopRight().getX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY, dateiMemo.getTopRight().getY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX, dateiMemo.getBottomLeft().getX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY, dateiMemo.getBottomLeft().getY());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, dateiMemo.getBottomRight().getX());
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY, dateiMemo.getBottomRight().getY());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTX, dateiMemo.getPunktX());
        values.put(DateiMemoDbHelper.COLUMN_PUNKTY, dateiMemo.getPunktY());
        values.put(DateiMemoDbHelper.COLUMN_IP, dateiMemo.getIP());
        values.put(DateiMemoDbHelper.COLUMN_COUNTPEERS, dateiMemo.getCountPeers());

        //
        //insert row
        //insert muss long
        //
        int data_Id;
        data_Id = (int)database.insert(DateiMemoDbHelper.TABLE_DATEI_LIST, null, values);
        DatabaseManager.getInstance().closeDatabase();


        return data_Id;
    }



    /*
    *
    *
    *                                           Delete data
    *
    *
    *
    * */
    public void deleteDateiMemo() {

        database = DatabaseManager.getInstance().openDatabase();
        database.delete(DateiMemoDbHelper.TABLE_DATEI_LIST, null, null);
        DatabaseManager.getInstance().closeDatabase();
        //Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: " + dateiMemo.toString());
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
    public void updateCornerTopRightX(double newCornerTopRightX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX, newCornerTopRightX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerTopRightY(double newCornerTopRightY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY, newCornerTopRightY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
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
    public void updateCornerTopLeftX(double newCornerTopLeftX) {

        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX, newCornerTopLeftX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();

    }

    public void updateCornerTopLeftY(double newCornerTopLeftY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY, newCornerTopLeftY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
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
    public void updateCornerBottomRightX(double newCornerBottomRightX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, newCornerBottomRightX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerBottomRightY(double newCornerBottomRightY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY, newCornerBottomRightY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
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
    public void updateCornerBottomLeftX(double newCornerBottomLeftX) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();
        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX, newCornerBottomLeftX);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }

    public void updateCornerBottomLeftY(double newCornerBottomLeftY) {
        //1. Öffne Database
        database = DatabaseManager.getInstance().openDatabase();

        //2. Erstell neue Wert
        ContentValues values = new ContentValues();
        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY, newCornerBottomLeftY);

        //3. Update Database
        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST, //UPDATE which TABLE
                values, // SET query
                null, // should be WHERE query
                null  // should be Array
        );

        //4. Schliess Database
        DatabaseManager.getInstance().closeDatabase();
    }
    /*
    *  ================================================================================================================================
    */



    /*
    *
    *
    *               Hilfklasse für Update Methode und Insert Methode
    *
    * */
//    private DateiMemo cursorToDateiMemo(Cursor cursor) {
//        int idIndex = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_UID);
//        int idChecked = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
//        int idTopRightX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX);
//        int idTopRightY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY);
//        int idTopLeftX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX);
//        int idTopLeftY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY);
//        int idBottomRightX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX);
//        int idBottomRightY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY);
//        int idBottomLeftX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY);
//        int idBottomLeftY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY);
//        int idPunktX = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTX);
//        int idPunktY = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTY);
//        int idIP = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_IP);
//        int idCountPeers = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_COUNTPEERS);
//
//
//
//        long uid = cursor.getLong(idIndex);
//
    //        int intValueChecked = cursor.getInt(idChecked);
    //        boolean isChecked = (intValueChecked != 0);
//
//        double cornerTopRightX = cursor.getDouble(idTopRightX);
//        double cornerTopRightY = cursor.getDouble(idTopRightY);
//        double cornerTopLeftX = cursor.getDouble(idTopLeftX);
//        double cornerTopLeftY = cursor.getDouble(idTopLeftY);
//        double cornerBottomRightX = cursor.getDouble(idBottomRightX);
//        double cornerBottomRightY = cursor.getDouble(idBottomRightY);
//        double cornerBottomLeftX= cursor.getDouble(idBottomLeftX);
//        double cornerBottomLeftY = cursor.getDouble(idBottomLeftY);
//        double punktX = cursor.getDouble(idPunktX);
//        double punktY = cursor.getDouble(idPunktY);
//        String IP = cursor.getString(idIP);
//
//        int countPeers = cursor.getInt(idCountPeers);
//
//
//        DateiMemo dateiMemo = new DateiMemo();
//
//        return dateiMemo;
//    }


    /*
    *           Get
    *
    *
    *           Corner Bottom Right X und Y
    *
    *
    *
    * */
    public double getCornerBottomRightX() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerBottomRightX;
        CornerBottomRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX));

//        while(!cursor.isAfterLast()) {
//            CornerBottomRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX));
//            //CornerBottomRightList.add(CornerBottomRight);
//            Log.d(LOG_TAG, selectQuery);
//            cursor.moveToNext();
//        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerBottomRightX;
    }

    public double getCornerBottomRightY() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerBottomRightY;
        CornerBottomRightY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTY));

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerBottomRightY;
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
    public double getCornerBottomLeftX() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomLeftList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerBottomLeftX;
        CornerBottomLeftX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerBottomLeftX;
    }

    public double getCornerBottomLeftY() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerBottomLeftList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerBottomLeftY;
        CornerBottomLeftY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerBottomLeftY;
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
    public double getCornerTopRightX() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerTopRightX;
        CornerTopRightX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerTopRightX;
    }

    public double getCornerTopRightY() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerTopRightY;
        CornerTopRightY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerTopRightY;
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
    public double getCornerTopLeftX() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerTopLeftX;
        CornerTopLeftX = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerTopLeftX;
    }

    public double getCornerTopLeftY() {
        database = DatabaseManager.getInstance().openDatabase();
        //List<Double> CornerTopRightList = new ArrayList<>();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        double CornerTopLeftY;
        CornerTopLeftY = cursor.getDouble(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY));


        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return CornerTopLeftY;
    }
    //
    // ================================================================================================================================
    //


    /*
    *
    *
    *                   get UID
    *
    *
    * */
    public long getUid() {
        //List<long> UidList = new ArrayList<>();
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_UID + " FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST;

        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        long UID;
        UID = cursor.getLong(cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_UID));

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return UID;
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
    public double getPunktX(long dateiMemo_Id) {
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_PUNKTX +" FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_UID + " = " + dateiMemo_Id;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        double punktX;
        punktX = c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTX));
        DatabaseManager.getInstance().closeDatabase();
        return punktX;
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
    public double getPunktY(long dateiMemo_Id) {
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_PUNKTY +" FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST + " WHERE "
                + DateiMemoDbHelper.COLUMN_UID + " = " + dateiMemo_Id;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        double punktY;
        punktY = c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTY));
        DatabaseManager.getInstance().closeDatabase();
        return punktY;
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
    public String getIp(long dateiMemo_Id) {
        database = DatabaseManager.getInstance().openDatabase();
        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_IP +" FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST +
                " WHERE " + DateiMemoDbHelper.COLUMN_UID + " = " + dateiMemo_Id;

        Log.e(LOG_TAG, selectQuery);

        Cursor c = database.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();
        String Ip;
        Ip = c.getString(c.getColumnIndex(DateiMemoDbHelper.COLUMN_IP));
        DatabaseManager.getInstance().closeDatabase();
        return Ip;
    }
    //
    // ================================================================================================================================
    //

    public List<Node> getAllDateiMemos() {
        List<Node> DateiMemoList = new LinkedList<Node>();

        //1. query
        String query = "SELECT * FROM " + dbHelper.TABLE_DATEI_LIST;

        //2. open Database
        database = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = database.rawQuery(query, null);

//        int idChecked = cursor.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
//        int intValueChecked = cursor.getInt(idChecked);
//        boolean isChecked = (intValueChecked != 0);


        //3. Durchführen Zeile und füge in List hinzu
        Node dateiMemo = null;
        if (cursor.moveToFirst()) {
            do {
                dateiMemo = new Node();
                dateiMemo.setUid(cursor.getLong(cursor.getColumnIndex(dbHelper.COLUMN_UID)));
                //dateiMemo.setChecked(isChecked);
                dateiMemo.getTopLeft().setX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTX)));
                dateiMemo.getTopLeft().setY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPLEFTY)));
                dateiMemo.getTopRight().setX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTX)));
                dateiMemo.getTopRight().setY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERTOPRIGHTY)));
                dateiMemo.getBottomLeft().setX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTX)));
                dateiMemo.getBottomLeft().setY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMLEFTY)));
                dateiMemo.getBottomRight().setX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTX)));
                dateiMemo.getBottomRight().setY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_CORNERBOTTOMRIGHTY)));
                dateiMemo.setPunktX(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTX)));
                dateiMemo.setPunktY(cursor.getDouble(cursor.getColumnIndex(dbHelper.COLUMN_PUNKTY)));
                dateiMemo.setIP(cursor.getString(cursor.getColumnIndex(dbHelper.COLUMN_IP)));
                dateiMemo.setCountPeers(cursor.getInt(cursor.getColumnIndex(dbHelper.COLUMN_COUNTPEERS)));


                // Add book to books
                DateiMemoList.add(dateiMemo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return DateiMemoList;
    }

}


//Ungebrauchte Code






//    public Cursor getCorner(int dateiMemo_Id) {
//
//        String selectQuery = "SELECT "+ DateiMemoDbHelper.COLUMN_CORNERTOPLEFT +", "+ DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT +
//                            ", "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT + ", "+ DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT
//                            +" FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST + " WHERE "
//                            + DateiMemoDbHelper.COLUMN_UID + " = " + dateiMemo_Id;
//
//        Log.e(LOG_TAG, selectQuery);
//
//        Cursor c = database.rawQuery(selectQuery, null);
//
//        if (c != null)
//            c.moveToFirst();
//
//
//        return c;
//    }


//    /*
//    *
//    *
//    *                               Get einzelne Data
//    *
//    *
//    *
//    * */
//    public DateiMemo getDataEinzelneRow(int dateiMemo_Id) {
//
//
//
//        String selectQuery = "SELECT  * FROM " + DateiMemoDbHelper.TABLE_DATEI_LIST + " WHERE "
//                + DateiMemoDbHelper.COLUMN_UID + " = " + dateiMemo_Id;
//
//        Log.e(LOG_TAG, selectQuery);
//
//        Cursor c = database.rawQuery(selectQuery, null);
//        int idChecked = c.getColumnIndex(DateiMemoDbHelper.COLUMN_CHECKED);
//        int intValueChecked = c.getInt(idChecked);
//        boolean isChecked = (intValueChecked > 0);
//        if (c != null)
//            c.moveToFirst();
//
//        DateiMemo dateiMemo = new DateiMemo();
//        dateiMemo.setUid(c.getint(c.getColumnIndex(DateiMemoDbHelper.COLUMN_UID)));
//        dateiMemo.setUsername((c.getString(c.getColumnIndex(DateiMemoDbHelper.COLUMN_USERNAME))));
//        dateiMemo.setPassword(c.getString(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PASSWORD)));
//        dateiMemo.setChecked(isChecked);
//        dateiMemo.setCornerBottomLeft(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFT)));
//        dateiMemo.setCornerBottomRight(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHT)));
//        dateiMemo.setCornerTopLeft(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPLEFT)));
//        dateiMemo.setCornerTopRight(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHT)));
//        dateiMemo.setPunktX(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTX)));
//        dateiMemo.setPunktY(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_PUNKTY)));
//        dateiMemo.setIP(c.getDouble(c.getColumnIndex(DateiMemoDbHelper.COLUMN_IP)));
//        dateiMemo.setCountPeers(c.getInt(c.getColumnIndex(DateiMemoDbHelper.COLUMN_COUNTPEERS)));
//
//        c.close();
//
//        return dateiMemo;
//    }








//    public DateiMemo updateDateiMemo(long uid, boolean newChecked,
//                                     double newCornerTopRightX, double newCornerTopRightY, double newCornerTopLeftX, double newCornerTopLeftY,
//                                     double newCornerBottomRightX, double newCornerBottomRightY, double newCornerBottomLeftX, double newCornerBottomLeftY,
//                                     double newPunktX, double newPunktY, double newIP, int newCountPeers) {
//        int intValueChecked = (newChecked)? 1 : 0;
//        newCountPeers = peerDbSource.getPeersCount();
//        ContentValues values = new ContentValues();
//        values.put(DateiMemoDbHelper.COLUMN_CHECKED, intValueChecked);
//
//        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTX, newCornerTopLeftX);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPLEFTY, newCornerTopLeftY);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTX, newCornerTopRightX);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERTOPRIGHTY, newCornerTopRightY);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTX, newCornerBottomLeftX);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMLEFTY, newCornerBottomLeftY);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, newCornerBottomRightX);
//        values.put(DateiMemoDbHelper.COLUMN_CORNERBOTTOMRIGHTX, newCornerBottomRightY);
//        values.put(DateiMemoDbHelper.COLUMN_PUNKTX, newPunktX);
//        values.put(DateiMemoDbHelper.COLUMN_PUNKTY, newPunktY);
//        values.put(DateiMemoDbHelper.COLUMN_IP, newIP);
//        values.put(DateiMemoDbHelper.COLUMN_COUNTPEERS, newCountPeers);
//        values.put(DateiMemoDbHelper.COLUMN_UID, uid);
//
//
//        database.update(DateiMemoDbHelper.TABLE_DATEI_LIST,
//                values,
//                DateiMemoDbHelper.COLUMN_UID + "=" + uid,
//                null);
//
//        Cursor cursor = database.query(DateiMemoDbHelper.TABLE_DATEI_LIST,
//                columns, DateiMemoDbHelper.COLUMN_UID + "=" + uid,
//                null, null, null, null);
//
//        cursor.moveToFirst();
//        DateiMemo dateiMemo = cursorToDateiMemo(cursor);
//        cursor.close();
//
//        return dateiMemo;
//    }