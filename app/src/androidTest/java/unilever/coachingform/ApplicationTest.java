package unilever.coachingform;

import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import dao.CoachingSessionDAO;
import dto.UserDataDTO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coaching;
import service.UserDataService;
import utility.ConstantUtil;
import utility.RealmUtil;
import utility.SharedPreferenceUtil;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<MainApp> {
    public ApplicationTest() {
        super(MainApp.class);
    }

    private static final String TAG = "ApplicationTest";
    private static String coachingSessionID;
    private static String coacheeID = "-KO0f6c9vRKTo5cg9m5u";

    @Override
    public void setUp() throws Exception {
        createApplication();

        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSessionEntity> sessionList = realm.where(CoachingSessionEntity.class).findAll();
        RealmResults<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntities = realm.where(CoachingQuestionAnswerEntity.class).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        coachingQuestionAnswerEntities.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public void testSharedPreference() throws Exception {
        String email = "adrian@gmail.com";
        String username = "adrian";
        String id = RealmUtil.generateID();
        SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_EMAIL, email);
        SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_ID, id);
        SharedPreferenceUtil.putString(ConstantUtil.SP_COACH_NAME, username);

        assertEquals(email, SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_EMAIL));
        assertEquals(username, SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_NAME));
        assertEquals(id, SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_ID));
    }

    public void testUserDataService() throws Exception {
        String userID = "ecOtI8PwyCOllfEfs5w2WgGEtxV2";

        final CountDownLatch signal = new CountDownLatch(2);

        UserDataService.getUserData(userID, new UserDataService.GetUserDataListener() {
            @Override
            public void onUserDataReceived(UserDataDTO userDataDTO) {
                assertEquals(userDataDTO.getName(), "Adrian Chriswanto");
                signal.countDown();
            }
        });

        UserDataService.getUserData("asdfasdf", new UserDataService.GetUserDataListener() {
            @Override
            public void onUserDataReceived(UserDataDTO userDataDTO) {
                assertEquals(userDataDTO, null);
                signal.countDown();
            }
        });

        signal.await();
    }

    public void testCoachingSessionDAO() throws Exception {
        final CountDownLatch signal1 = new CountDownLatch(1);
        final CountDownLatch signal2 = new CountDownLatch(3);

        CoachingSessionDAO.insertNewCoaching("coachee1", "coachee1@gmail.com",
                "engineer", "", "", "", "", "", "", "", "", "",
                new CoachingSessionDAO.InsertCoachingListener() {
                    @Override
                    public void onInsertCoachingCompleted(String id) {
                        coachingSessionID = id;
                        Log.d(TAG, "Cpacjee : " + id);
                        signal1.countDown();
                    }
                });

        signal1.await(30, TimeUnit.SECONDS);

        final int guideline = 1;
        final String area = "area1";
        final String distributor = "dist1";
        final String store = "Store1";
        final String action = "Action1";

        CoachingSessionDAO.updateAction(coachingSessionID, action,
                new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        assertEquals(isSuccess, true);
                        signal2.countDown();
                    }
                });

        CoachingSessionDAO.updateDistributorStoreArea(coachingSessionID, distributor, area,
                store, new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        assertEquals(isSuccess, true);
                        signal2.countDown();
                    }
                });

        CoachingSessionDAO.updateGuideline(coachingSessionID, guideline,
                new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        Log.d(TAG, isSuccess + "");
                        assertEquals(isSuccess, true);
                        signal2.countDown();
                    }
                });

        signal2.await(30, TimeUnit.SECONDS);

        CoachingSessionDAO.getCoachingSession(coachingSessionID,
                new CoachingSessionDAO.GetCoachingListener() {
                    @Override
                    public void onCoachingReceived(CoachingSessionEntity entity) {
                        assertEquals(entity.getAction(), action);
                        assertEquals(entity.getArea(), area);
                        assertEquals(entity.getDistributor(), distributor);
                        assertEquals(entity.getStore(), store);
                        assertEquals(entity.getCoachingGuideline(), guideline);
                    }
                });
    }


}