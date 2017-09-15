package task;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import model.*;

/**
 * Created by Alexander on 15.09.2017.
 */

public class SplitTask extends AsyncTask<Node,String,String> {

    public interface AsyncResponse {
        void processFinish(double result);
    }

    private SplitTask.AsyncResponse delegate = null;

    public SplitTask(SplitTask.AsyncResponse delegate)throws JSONException {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Node... params) {

        Zone zone = new Zone();

        if (zone.getLengthX(params[0]) >= zone.getLengthY(params[0])) {

            Node[] nodeArray = zone.determineLocationX(params[0],params[1],params[2],params[3]);

            double midX = zone.getLengthX(nodeArray[0]) / 2.0;
            // set peers und neigbour und update Corner
            Log.d("TEST_BOTTOMRight_Vorher", "NODE1: " + nodeArray[0].getMyZone().toString());
            nodeArray[0].getBottomRight().setX(midX);
            Log.d("TEST_BOTTOMRight_Nach", "NODE1: " + nodeArray[0].getMyZone().toString());

            nodeArray[0].getTopRight().setX(midX);
            Log.d("TEST_TOPRight", "NODE1: " + nodeArray[0].getMyZone().toString());

            nodeArray[1].getTopRight().setX(midX);
            Log.d("TEST_TOPRight", "NODE4: " + nodeArray[3].getMyZone().toString());
            nodeArray[1].getBottomRight().setX(midX);

            nodeArray[2].getBottomLeft().setX(midX);
            nodeArray[2].getTopLeft().setX(midX);

            nodeArray[3].getTopLeft().setX(midX);
            nodeArray[3].getBottomLeft().setX(midX);

            Log.d("TEST_NACHhER", "ARRAY: " + nodeArray[0].getMyZone().toString() + "\n" +
                    nodeArray[1].getMyZone().toString() + "\n" +
                    nodeArray[2].getMyZone().toString() + "\n" +
                    nodeArray[3].getMyZone().toString() + "\n");

        } else {
            Node[] nodeArray = zone.determineLocationY(params[0],params[1],params[2],params[3]);
            double midY =  zone.getLengthY(nodeArray[0]) / 2.0;
            // set peers und neigbour und update Corner

            nodeArray[0].getTopRight().setY(midY);
            nodeArray[0].getTopLeft().setY(midY);

            nodeArray[1].getBottomLeft().setY(midY);
            nodeArray[1].getBottomRight().setY(midY);

            nodeArray[2].getTopRight().setY(midY);
            nodeArray[2].getTopLeft().setY(midY);

            nodeArray[3].getBottomRight().setY(midY);
            nodeArray[3].getBottomLeft().setY(midY);
        }

        return null;
    }

}
