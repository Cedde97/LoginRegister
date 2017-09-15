package task;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import model.PeerMemo;

import source.PeerDbSource;

/**
 * Created by Joshi on 15.09.2017.
            */

    public class UpdatePeerDbTask extends AsyncTask<PeerMemo,Void, Void> {
        private PeerDbSource pDB = new PeerDbSource();

        public UpdatePeerDbTask(){

        }

        @Override
        protected Void doInBackground(PeerMemo... params) {

            PeerMemo pm = params[0];
            PeerMemo pm1= params[1];
            PeerMemo pm2= params[2];
            PeerMemo pm3= params[3];
            List<PeerMemo> pmList = new ArrayList<PeerMemo>();

            pmList.add(pm);
            pmList.add(pm1);
            pmList.add(pm2);
            pmList.add(pm3);
            int i =0;
            while(!pmList.isEmpty()){
                pDB.createPeerMemo(pmList.get(i++));
            }





            return null;
        }
}
