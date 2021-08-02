package ai.nextbillion;

import android.app.Application;

import com.nbmap.nbmapsdk.Nbmap;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Nbmap.getInstance(this, getString(R.string.nbmap_access_token));
    }
}
