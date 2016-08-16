package unilever.coachingform;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by adrianch on 15/08/2016.
 */
public class MainApp extends Application {

    @Override
    public void onCreate()
    {
        super.onCreate();
        initSingletons();
    }

    protected void initSingletons()
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(
                getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
