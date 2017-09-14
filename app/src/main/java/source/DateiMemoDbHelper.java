package source;



/**
 * Created by en on 15.06.17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import app.App;

import java.security.PublicKey;

public class DateiMemoDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DateiMemoDbHelper.class.getSimpleName();



//-------------Wie man eine verbindung zu dem SQL macht-------------------------------

    //######################    neue Database   #######################################
    public static final String DB_NAME = "p2p.db";
    public static final int DB_VERSION = 8;
    //##################################################################################



    //------------------------- neue tabelle    -------------------------------------
    public static final String TABLE_DATEI_LIST  = "datei_list";
    public static final String TABLE_PEER_LIST = "peer_list";
    public static final String TABLE_NEIGHBOR_LIST = "neighbor_list";
    public static final String TABLE_OWNDATA_LIST = "owndata_list";
    public static final String TABLE_FOREIGNDATA_LIST = "foreigndata_list";



    public static final String COLUMN_UID = "_uid";
    public static final String COLUMN_PID = "peer_id_foreign";
    public static final String COLUMN_OID = "own_id_foreign";
    public static final String COLUMN_FID = "foreign_id_foreign";
    public static final String COLUMN_NID = "neighbour_id_foreign";
    //    public static final String COLUMN_USERNAME = "username";
//    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IP = "IP";
    public static final String COLUMN_COUNTPEERS = "CountPeers";


    public static final String COLUMN_NEIGHBOUR_ID = "neighbour_id";
    public static final String COLUMN_PEERID = "peerId";
    public static final String COLUMN_PEERIP = "peerIp";



    public static final String COLUMN_UIP = "uip";
    //    public static final String COLUMN_CORNERTOPRIGHT = "cornerTopRight";
//    public static final String COLUMN_CORNERTOPLEFT = "cornerTopLeft";
//    public static final String COLUMN_CORNERBOTTOMRIGHT = "cornerBottomRight";
//    public static final String COLUMN_CORNERBOTTOMLEFT = "cornerBottomLeft";
    public static final String COLUMN_PUNKTX = "punktX";
    public static final String COLUMN_PUNKTY = "punktY";
    public static final String COLUMN_RTT = "rtt";



    public static final String COLUMN_FOTOID = "fotoId";
    public static final String COLUMN_FILEID = "fileId";

    public static final String COLUMN_CORNERTOPRIGHTX = "cornerTopRightX";
    public static final String COLUMN_CORNERTOPRIGHTY = "cornerTopRightY";
    public static final String COLUMN_CORNERBOTTOMRIGHTX = "cornerBottomRightX";
    public static final String COLUMN_CORNERBOTTOMRIGHTY = "cornerBottomRightY";
    public static final String COLUMN_CORNERTOPLEFTX = "cornerTopLeftX";
    public static final String COLUMN_CORNERTOPLEFTY = "cornerTopLeftY";
    public static final String COLUMN_CORNERBOTTOMLEFTX = "cornerBottomLeftX";
    public static final String COLUMN_CORNERBOTTOMLEFTY = "cornerBottomLeftY";


    //public static final String COLUMN_CHECKED = "checked";



    public static final String SQL_CREATE_TABLE_DATEI =
            "CREATE TABLE " + TABLE_DATEI_LIST +
                    "(" + COLUMN_UID + " INTEGER PRIMARY KEY," +
                    COLUMN_CORNERTOPRIGHTX + " REAL NOT NULL," +
                    COLUMN_CORNERTOPRIGHTY + " REAL NOT NULL," +
                    COLUMN_CORNERTOPLEFTX + " REAL NOT NULL," +
                    COLUMN_CORNERTOPLEFTY + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMRIGHTX + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMRIGHTY + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMLEFTX + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMLEFTY + " REAL NOT NULL," +
                    COLUMN_PUNKTX + " REAL NOT NULL," +
                    COLUMN_PUNKTY + " REAL NOT NULL," +
                    COLUMN_IP + " TEXT NOT NULL," +
                    COLUMN_COUNTPEERS + " INTEGER NOT NULL );" ;

    public static final String SQL_CREATE_TABLE_PEERS =
            "CREATE TABLE " + TABLE_PEER_LIST +
                    " ( " + COLUMN_PEERID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PEERIP + " TEXT NOT NULL," +
                    COLUMN_PID + " INTEGER NOT NULL," +//);";
                    " FOREIGN KEY ( " + COLUMN_PID + " ) REFERENCES "+ TABLE_DATEI_LIST +"("+ COLUMN_UID + "));";

    public static final String SQL_CREATE_TABLE_NEIGBHORS =
            "CREATE TABLE " + TABLE_NEIGHBOR_LIST +
                    " ( " + COLUMN_NEIGHBOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_UIP + " TEXT NOT NULL," +
                    COLUMN_CORNERTOPRIGHTX + " REAL NOT NULL," +
                    COLUMN_CORNERTOPRIGHTY + " REAL NOT NULL," +
                    COLUMN_CORNERTOPLEFTX + " REAL NOT NULL," +
                    COLUMN_CORNERTOPLEFTY + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMRIGHTX + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMRIGHTY + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMLEFTX + " REAL NOT NULL," +
                    COLUMN_CORNERBOTTOMLEFTY + " REAL NOT NULL," +
                    COLUMN_PUNKTX + " REAL NOT NULL," +
                    COLUMN_PUNKTY + " REAL NOT NULL," +
                    COLUMN_RTT + " REAL NOT NULL," +
                    COLUMN_NID + " INTEGER NOT NULL," + // );";
                    " FOREIGN KEY ("+ COLUMN_NID +") REFERENCES "+ TABLE_DATEI_LIST +"("+ COLUMN_UID + "));";

    public static final String SQL_CREATE_TABLE_OWNDATAS =
            "CREATE TABLE " + TABLE_OWNDATA_LIST +
                    " ( " + COLUMN_OID + " INTEGER PRIMARY KEY," +
                    COLUMN_FILEID + " INTEGER NOT NULL," + // );";
                    " FOREIGN KEY ("+ COLUMN_OID +") REFERENCES "+ TABLE_DATEI_LIST +"("+ COLUMN_UID + "));";

    public static final String SQL_CREATE_TABLE_FOREIGNDATAS =
            "CREATE TABLE " + TABLE_FOREIGNDATA_LIST +
                    " ( " + COLUMN_FOTOID + " INTEGER PRIMARY KEY," +
                    COLUMN_PUNKTX + " REAL NOT NULL," +
                    COLUMN_PUNKTY + " REAL NOT NULL," +
                    COLUMN_IP + " TEXT NOT NULL," +
                    COLUMN_FID + " INTEGER NOT NULL," + // );";
                    " FOREIGN KEY ("+ COLUMN_FID +") REFERENCES "+ TABLE_DATEI_LIST +"("+ COLUMN_UID + "));";

    public static final String SQL_DROP_DATEI = "DROP TABLE IF EXISTS " + TABLE_DATEI_LIST;
    public static final String SQL_DROP_PEERS = "DROP TABLE IF EXISTS " + TABLE_PEER_LIST;
    public static final String SQL_DROP_NEIGHBORS = "DROP TABLE IF EXISTS " + TABLE_NEIGHBOR_LIST;
    public static final String SQL_DROP_OWNDATAS = "DROP TABLE IF EXISTS " + TABLE_OWNDATA_LIST;
    public static final String SQL_DROP_FOREIGNDATAS = "DROP TABLE IF EXISTS " + TABLE_FOREIGNDATA_LIST;

    //-------------------------------------------------------------------------------


    //Konstruktor
    //SUPER verwendet man, weil unsere "helper" ist eine Ableitung von SQLiteOpenHelper
    public DateiMemoDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public DateiMemoDbHelper() {
        super(App.getContext(), DB_NAME, null, DB_VERSION);
    }

    //enable foreign key
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    // Die onCreate-Methode wird nur aufgerufen, falls die Datenbank noch nicht existiert
    @Override
    public void onCreate(SQLiteDatabase db) {
        //1. Info
        Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE_TABLE_DATEI + SQL_CREATE_TABLE_PEERS
                + SQL_CREATE_TABLE_NEIGBHORS + SQL_CREATE_TABLE_OWNDATAS + SQL_CREATE_TABLE_FOREIGNDATAS + " angelegt.");



        //2. Erstellung eine Datenbank mit String "SQL_CREATE" als Parameter
        db.execSQL(SQL_CREATE_TABLE_DATEI);
        db.execSQL(SQL_CREATE_TABLE_PEERS);
        db.execSQL(SQL_CREATE_TABLE_NEIGBHORS);
        db.execSQL(SQL_CREATE_TABLE_OWNDATAS);
        db.execSQL(SQL_CREATE_TABLE_FOREIGNDATAS);
    }

    //wird zur Aktualisierung einer bereits bestehenden Datenbank benutzt
    // Die onUpgrade-Methode wird aufgerufen, sobald die neue Versionsnummer höher
    // als die alte Versionsnummer ist und somit ein Upgrade notwendig wird
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(LOG_TAG, "Die Tabelle mit Versionsnummer " + oldVersion + " wird entfernt.");
        db.execSQL(SQL_DROP_DATEI);
        db.execSQL(SQL_DROP_PEERS);
        db.execSQL(SQL_DROP_NEIGHBORS);
        db.execSQL(SQL_DROP_OWNDATAS);
        db.execSQL(SQL_DROP_FOREIGNDATAS);
        onCreate(db);
    }
}

//"FOREIGN KEY ("+ COLUMN_PID +") REFERENCES "+ TABLE_DATEI_LIST +"("+ COLUMN_UID + "));";