package unilever.coachingform;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import android.content.Context;

/**
 * Created by adrianch on 15/08/2016.
 */
public class MainApp extends Application {

    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();
        initSingletons();
    }

    public static Context getContext(){
        return context;
    }

    protected void initSingletons()
    {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(
                getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
        context = getApplicationContext();
    }
}
