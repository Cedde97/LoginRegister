package bootstrap;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Axel Czuck
 * class to retrieve all IPs, which are stored on a webserver
 */
public class AllIPsActivity extends AsyncTask<String, String, String[]> {
    private String[] ipsList;

    /**
     * Interface to retrieve the result-list in an String-array
     */
    public interface AsyncResponse {
        void processFinish(String[] result);
    }

    private AsyncResponse delegate = null;

    /**
     * constructor for the result from the response-interface
     * @param delegate
     * @throws JSONException
     */
    public AllIPsActivity(AsyncResponse delegate) throws JSONException {
        this.delegate = delegate;
    }

    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();

    // JSON Node names
    private static final String TAG_DB;

    static {
        TAG_DB = "bootstraps";
    }

    private static final String TAG_IP = "IP";


    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    /**
     * getting all IPs from url with a GET-Request
     * @param args
     * @return string-array of all IPs, which are stored on the webserver
     * */
    @Override
    protected String[] doInBackground(String...args) {
        //list, which won't be needed but is necessary as parameter (POST-Request will need it)
        List<NameValuePair> paramsGet = new ArrayList<>();

        // getting JSON string from URL
        JSONObject jsonGET = null;

        try {
            //Connects to Bootstrap-Webserver and retrieve all IPs
            String url_all_ips = "https://axelczuck.000webhostapp.com/get_all_bootstrap.php";
            jsonGET = jParser.makeHttpRequest(url_all_ips, "GET", paramsGet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Check your log cat for JSON reponse
        Log.d("All IPs ", jsonGET.toString());

        /**
         * Parse the returned JSON-Object into Strings
         **/
        try {
            // If IPs were found

            // Getting Array of IPs
            JSONArray ips = jsonGET.getJSONArray(TAG_DB);

            //initializing result-array
            ipsList = new String[ips.length()];

            // looping through all IPs
            for (int i = 0; i < ips.length(); i++) {
                JSONObject c = ips.getJSONObject(i);

                // Storing each json item in variable
                String ip = c.getString(TAG_IP);

                //Retrieved the correct IPs?
                Log.d("Single IPs ", ip);

                //storing IPs in string-array
                ipsList[i] = ip;


            }return ipsList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delegate result-array from doInBackground
     * @param args
     */
    @Override
    protected void onPostExecute(String[] args) {

        delegate.processFinish(args);
        /*for (int i = 0; i < args.length; i ++){
            Log.d("IpList in onPostExecute", args[i].toString());
        }*/
    }



}

