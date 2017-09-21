package task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by Joshi on 20.09.2017.
 */

public class CheckEmptyOnlineDBTask extends AsyncTask<Void,Void,Boolean> {
    private boolean isEmpty;

    public interface AsyncResponse{
        void processFinish(boolean result);
    }

    private AsyncResponse delegate = null;

    public CheckEmptyOnlineDBTask(AsyncResponse delegate){
        this.delegate = delegate;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        String jsonString = "";
        String line;
        HttpURLConnection httpURLConnection = null;
        try {
            URL u = new URL("https://agelong-rations.000webhostapp.com/abfrage.php");
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);

            }
            jsonString = stringBuilder.toString();
            Log.d("jsonString: ", ""+ jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }
        if (jsonString.equals("True")) {
            isEmpty = true;
            return isEmpty;
        } else {
            isEmpty = false;
            return isEmpty;
        }
    }

    //noch testen
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        Log.d("isempty", ""+isEmpty);
        isEmpty = aBoolean;
        delegate.processFinish(isEmpty);
    }


}
