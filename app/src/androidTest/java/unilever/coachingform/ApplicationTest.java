package unilever.coachingform;

import android.test.ApplicationTestCase;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import dto.UserDataDTO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coaching;
import service.SynchronizationService;
import service.UserDataService;
import utility.ConstantUtil;
import utility.PDFUtil;
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

        final int guideline = ConstantUtil.GUIDELINE_ASM_PUSH;
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

        final CountDownLatch signal3 = new CountDownLatch(1);
        final CountDownLatch signal4 = new CountDownLatch(2);

        //final List<CoachingQuestionAnswerEntity> coachingQAs = dummyDSRQA();
        final List<CoachingQuestionAnswerEntity> coachingQAs = dummyASMPUSHQA();
        Log.d(TAG, "QA : " + coachingQAs.size());

        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                assertEquals(true, isSuccess);
                signal3.countDown();
            }
        });


        signal3.await();

        CoachingQuestionAnswerDAO.getCoachingQA(coachingSessionID, new CoachingQuestionAnswerDAO.GetCoachingQAListener() {
            @Override
            public void onQuestionAnswerReceived(List<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntityList) {
                assertEquals(coachingQuestionAnswerEntityList.size(), coachingQAs.size());
                signal4.countDown();
            }
        });


        SynchronizationService.syncCoachingSession(coachingSessionID, new SynchronizationService.SyncCoachingListener() {
            @Override
            public void onSyncCoachingCompleted(boolean isSucceed) {
                assertEquals(true, isSucceed);
                signal4.countDown();
            }
        });

        signal4.await();

        CoachingSessionDAO.updateSubmitted(coachingSessionID, true,
                new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        assertEquals(isSuccess, true);
                    }
                });

        CoachingSessionDAO.getUnsubmittedCoaching(new CoachingSessionDAO.GetListCoachingListener() {
            @Override
            public void onUnsubmittedCoachingReceived(List<Coaching> coachingList) {
                assertEquals(coachingList.size(), 0);
            }
        });

        final CountDownLatch signal6 = new CountDownLatch(1);
        PDFUtil.createPDF(coachingSessionID, new PDFUtil.GeneratePDFListener() {
            @Override
            public void onPDFGenerated(boolean isSuccess) {
                signal6.countDown();
            }
        });

        signal6.await();
    }

    private static List<CoachingQuestionAnswerEntity> dummyDSRQA(){
        String[] sebelumID = {"1", "2", "3", "4a", "4b", "4c", "4d", "4e"};
        String[] saatID = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] setelahID = {"1", "2"};

        final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();

        for (String id : sebelumID) {
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("dsr_sebelum_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        for (String id : saatID) {
            for (int i = 1; i <= 10; i++) {
                CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
                coachingQA.setId(RealmUtil.generateID());
                coachingQA.setCoachingSessionID(coachingSessionID);
                coachingQA.setColumnID("customer_" + i);
                coachingQA.setQuestionID("dsr_saat_" + id);
                coachingQA.setTextAnswer("Right");
                coachingQA.setTickAnswer(true);
                coachingQA.setHasTickAnswer(true);
                coachingQAs.add(coachingQA);
            }
        }

        for (String id : setelahID) {
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("dsr_setelah_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        for(int i = 1; i <= 3; i++){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("dsr_summary_" + i);
            coachingQA.setTextAnswer("Summary" + i);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        return coachingQAs;
    }

    private static List<CoachingQuestionAnswerEntity> dummyFAQA(){
        String[] faID = {"1a","1b","2a","2b","3a","4a","5a","6a","6b","6c",
                "6d","rpi","7a","7b","7c","kompetitor"};

        final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();

        for (String id : faID){
            for (int i = 1; i <= 5; i++) {
                CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
                coachingQA.setId(RealmUtil.generateID());
                coachingQA.setCoachingSessionID(coachingSessionID);
                coachingQA.setColumnID("Product " + i);
                coachingQA.setQuestionID("fa_" + id);
                coachingQA.setTextAnswer("Remarks " + id);
                coachingQA.setTickAnswer(true);
                coachingQA.setHasTickAnswer(true);
                coachingQAs.add(coachingQA);
            }
        }

        for(int i = 1; i <= 3; i++){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("fa_summary_" + i);
            coachingQA.setTextAnswer("Summary" + i);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        return coachingQAs;
    }

    private static List<CoachingQuestionAnswerEntity> dummyASMPUSHQA(){
        final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();

        String[] asmSebelum = {"1_1_a", "1_1_b", "1_1_c", "1_1_d", "1_1_e", "1_2_a", "1_2_b"
                , "1_2_c", "1_3_a", "1_3_b", "1_4_a", "1_4_b", "1_5_a", "1_5_b", "1_5_c"
                , "1_5_d", "1_5_e", "1_6_a", "1_6_b", "2_1", "2_2_a", "2_2_b", "2_2_c"
                , "2_2_d", "2_2_e"};

        for (String id : asmSebelum){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("asm_push_sebelum_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        String[] asmReport = {"1_a","1_b","2_a","2_b","3_a","3_b","3_c","3_d"};

        for (String id : asmReport){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("asm_push_report_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        String[] asmInfra = {"1_a","1_b","1_c","1_d","1_e","1_f","1_g","2_a","2_b","2_c","2_d"
                ,"2_e","3"};

        for (String id : asmInfra){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("asm_push_infra_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        String[] asmMarket = {"1","2","3","4","5"};
        for (String id : asmMarket){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("asm_push_market_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        String[] asmSummary = {"1","2","3"};
        for (String id : asmSummary){
            CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
            coachingQA.setId(RealmUtil.generateID());
            coachingQA.setCoachingSessionID(coachingSessionID);
            coachingQA.setColumnID("");
            coachingQA.setQuestionID("asm_push_summary_" + id);
            coachingQA.setTextAnswer("Remarks " + id);
            coachingQA.setTickAnswer(true);
            coachingQA.setHasTickAnswer(true);
            coachingQAs.add(coachingQA);
        }

        return coachingQAs;
    }



    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        final CountDownLatch signal = new CountDownLatch(2);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("coachingSession").child(coachingSessionID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                signal.countDown();
            }
        });
        mDatabase.child("coachingQuestionAnswer").child(coachingSessionID).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                signal.countDown();
            }
        });

        signal.await();

    }
}