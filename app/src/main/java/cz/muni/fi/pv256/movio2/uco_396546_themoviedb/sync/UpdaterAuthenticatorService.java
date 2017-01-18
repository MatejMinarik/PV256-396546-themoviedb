package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Huvart on 14/01/2017.
 */

public class UpdaterAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private UpdaterAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new UpdaterAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
