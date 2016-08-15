package unilever.coachingform;

import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import dao.CoachingQADAO;
import dao.CoachingSessionDAO;
import entity.CoachingQA;
import entity.CoachingSession;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coachee;
import model.CoacheeHistory;
import model.Coaching;
import service.CoacheeHistoryService;
import service.CoacheeService;
import utility.RealmUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<MainApp> {
    public ApplicationTest() {
        super(MainApp.class);
    }

    private static final String TAG = "ApplicationTest";


    @Override
    public void setUp() throws Exception {
        createApplication();
        /*RealmConfiguration realmConfig = new RealmConfiguration.Builder(getContext()).build();
        Realm.setDefaultConfiguration(realmConfig);*/

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class).findAll();
        RealmResults<CoachingQA> coachingQAs = realm.where(CoachingQA.class).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        coachingQAs.deleteAllFromRealm();
        realm.commitTransaction();
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

        for(int i = 0 ; i < 5; i++){
            CoachingSession coachingSession = new CoachingSession();
            coachingSession.setCoachName("Coach");
            coachingSession.setCoacheeName("Coachee-" + System.currentTimeMillis() / 1000);

            CoachingSessionDAO.insertCoaching(coachingSession, new CoachingSessionDAO.InsertCoachingListener() {
                @Override
                public void onCompleted(String guid) {
                    Log.d(TAG, "Insert : " + guid);
                }
            });
        }

        CoachingSessionDAO.getUnsubmittedCoaching(new CoachingSessionDAO.GetCoachingListener() {
            @Override
            public void onReceived(List<Coaching> coachingList) {
                Log.d(TAG, coachingList.toString());
                Log.d(TAG, coachingList.size() + "");
            }
        });


        String coachingSessionID = RealmUtil.generateID();
        List<CoachingQA> coachingQAs = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            CoachingQA coachingQA = new CoachingQA();
            coachingQA.setGuid(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("Column 1");
            coachingQA.setQuestionID("Question" + i);
            coachingQA.setTextAnswer("Right");
            coachingQA.setTickAnswer(false);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        CoachingQADAO.insertCoachingQA(coachingQAs, new CoachingQADAO.InsertCoachingQAListener() {
            @Override
            public void onCompleted(boolean isSuccess) {
                Log.d(TAG, "Insert Coaching QA : " + isSuccess);
                assertEquals(isSuccess, true);
            }
        });

        CoachingQADAO.getCoachingQA(coachingSessionID, new CoachingQADAO.GetCoachingQAListener() {
            @Override
            public void onReceived(List<CoachingQA> coachingQAList) {
                Log.d(TAG, coachingQAList.toString());
                assertEquals(coachingQAList.size(), 5);
            }
        });

        final CountDownLatch signal = new CountDownLatch(2);

        //Get coachee history
        CoacheeHistoryService.getCoacheeHistory("-KO0f6c9vRKTo5cg9m5u",
                new CoacheeHistoryService.GetCoacheeHistoryServiceListener() {
            @Override
            public void onReceived(List<CoacheeHistory> coacheeHistories) {
                Log.d(TAG, coacheeHistories.toString());
                assertEquals(coacheeHistories.size(), 1);
                signal.countDown();
            }
        });

        //Get list coachee
        CoacheeService.getCoachee(new CoacheeService.GetCoacheeListener() {
            @Override
            public void onReceived(List<Coachee> coacheeList) {
                Log.d(TAG, coacheeList.toString());
                assertEquals(coacheeList.size(), 1);
                signal.countDown();
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }
}