package app;



import android.app.Application;
import android.content.Context;
import android.util.Log;

import source.DateiMemoDbHelper;
import source.DatabaseManager;


/**
 * Created by en on 21.08.17.
 */

public class App extends Application {
    private static Context context;
    private static DateiMemoDbHelper dbHelper;
    private static final String LOG_TAG = App.class.getSimpleName();

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.d(LOG_TAG ,"APP.CONTEXT " + context);
        context = this.getApplicationContext();
        dbHelper = new DateiMemoDbHelper(context);
        //dbHelper = new DateiMemoDbHelper();
        DatabaseManager.initializeInstance(dbHelper);

    }


    public static Context getContext(){
        return context;
    }
}