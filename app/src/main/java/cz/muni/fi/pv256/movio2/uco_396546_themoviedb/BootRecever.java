package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.sync.UpdaterSyncAdapter;

/**
 * Created by Huvart on 14/01/2017.
 */

public class BootRecever extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent)
    {
        UpdaterSyncAdapter.initializeSyncAdapter(context);
    }
}
