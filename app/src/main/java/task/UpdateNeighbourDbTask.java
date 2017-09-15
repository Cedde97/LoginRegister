package task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import model.Neighbour;
import source.NeighborDbSource;
/**
 * Created by Joshi on 15.09.2017.
 */

public class UpdateNeighbourDbTask extends AsyncTask<Neighbour,Void,Void> {
    private NeighborDbSource nDB = new NeighborDbSource();


    public UpdateNeighbourDbTask(){

    }

    @Override
    protected Void doInBackground(Neighbour... params) {
        Neighbour n = params[0];
        Neighbour n1= params[1];
        Neighbour n2= params[2];
        Neighbour n3= params[3];
        List<Neighbour> nList = new ArrayList<Neighbour>();

        nList.add(n);
        nList.add(n1);
        nList.add(n2);
        nList.add(n3);
        int i= 0;
        //while(!nList.isEmpty()){
            nDB.createNeighborMemo(nList.get(i++));
        //}

        return null;
    }





}
