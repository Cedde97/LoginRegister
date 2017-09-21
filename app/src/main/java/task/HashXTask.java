package task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

/**
 * Created by Joshi on 07.09.2017.
 */

public class HashXTask extends AsyncTask<String, String , String> {

    private static final long DIVIDER= 2552552552l;

    private double hashX;

    public interface AsyncResponse {
        void processFinish(double result);
    }

    private AsyncResponse delegate = null;

    public HashXTask(AsyncResponse delegate)throws JSONException {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String ip = params[0];

        String xWert = ip;
        hashX = Double.parseDouble("0." + Math.abs(xWert.hashCode()));

return null;
    }

    @Override
    protected void onPostExecute(String s){
        double d = hashX;
        delegate.processFinish(d);
    }
}
