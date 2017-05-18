package id.sch.smktelkom_mlg.privateassignment.xirpl109.marveljos.app;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by zhe on 14/05/2017.
 */

public class Marvel extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
