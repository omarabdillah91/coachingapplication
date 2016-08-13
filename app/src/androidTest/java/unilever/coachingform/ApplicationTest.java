package unilever.coachingform;

import android.app.Application;
import android.test.ApplicationTestCase;

import entity.CoachingSession;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import utility.RealmUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        createApplication();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void testRealm() throws Exception {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        CoachingSession session = realm.createObject(CoachingSession.class);
        String guid = RealmUtil.generateID();
        session.setGuid(guid);
        session.setDate(System.currentTimeMillis() / 1000);
        realm.commitTransaction();

        System.out.println(guid);

        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class).findAll();
        assertEquals(1, sessionList.size());

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}