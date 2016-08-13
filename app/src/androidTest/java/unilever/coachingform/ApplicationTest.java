package unilever.coachingform;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.List;

import dao.CoachingSessionDAO;
import entity.CoachingSession;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import model.Coaching;
import service.CoacheeHistoryService;
import utility.RealmUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> implements CoachingSessionDAO.CoachingSessionListener {
    public ApplicationTest() {
        super(Application.class);
    }

    private static final String TAG = "ApplicationTest";

    @Override
    public void setUp() throws Exception {
        createApplication();
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);

/*        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        realm.commitTransaction();*/
    }

    public void testRealm() throws Exception {
        /*Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        CoachingSession session = realm.createObject(CoachingSession.class);
        String guid = RealmUtil.generateID();
        session.setGuid(guid);
        session.setDate(System.currentTimeMillis() / 1000);
        realm.commitTransaction();

        Log.d(TAG, guid);

        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class).findAll();
        assertEquals(1, sessionList.size());
        assertEquals(sessionList.get(0).getGuid(), guid);

        CoacheeHistoryService.getCoacheeHistory("-KO0f6c9vRKTo5cg9m5u");*/
        for(int i = 0 ; i < 14; i++){
            CoachingSession cs = new CoachingSession();
            cs.setDate(System.currentTimeMillis() / 1000);
            cs.setCoacheeID(RealmUtil.generateID());
            cs.setSubmitted(false);
            CoachingSessionDAO.insertCoaching(cs, this);
        }

        CoachingSessionDAO.getUnsubmittedCoaching(this);
    }

    @Override
    public void onCoachingReceived(List<Coaching> coachingList) {
        Log.d(TAG, coachingList.size() + " nt");
    }

    @Override
    public void onCoachingInserted(boolean succees) {

    }
}