package unilever.coachingform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import dao.CoachingQuestionAnswerDAO;
import dao.CoachingSessionDAO;
import entity.CoachingQuestionAnswerEntity;
import entity.CoachingSessionEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import service.SynchronizationService;
import utility.PDFUtil;
import utility.RealmUtil;

public class TestActivity extends AppCompatActivity {

    private String coachingSessionID;
    private static final String TAG ="TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void sendEmail(View v){
        SynchronizationService.sendEmail(coachingSessionID, this);
    }

    public void generatePDF(View v){
        Log.d(TAG, "Generate PDF : " + coachingSessionID);
        PDFUtil.createPDF(coachingSessionID, new PDFUtil.GeneratePDFListener() {
            @Override
            public void onPDFGenerated(boolean isSuccess) {
                Log.d(TAG, "PDF Generated : " + isSuccess);
            }
        });
    }

    public void deleteTestData(View v){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSessionEntity> sessionList = realm.where(CoachingSessionEntity.class).findAll();
        RealmResults<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntities = realm.where(CoachingQuestionAnswerEntity.class).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        coachingQuestionAnswerEntities.deleteAllFromRealm();
        realm.commitTransaction();

        Log.d(TAG, "DELETE DATA");
    }

    public void insertTestData(View v){
        CoachingSessionDAO.insertNewCoaching("coachee1", "coachee1@gmail.com",
                "engineer", "", "omar.abdillah91@gmail.com", "", "", "adrianch@rocketmail.com", "",
                new CoachingSessionDAO.InsertCoachingListener() {
                    @Override
                    public void onInsertCoachingCompleted(String id) {
                        coachingSessionID = id;
                        Log.d(TAG, "Insert Coaching : " + id);
                    }
                });

        final int guideline = 2;
        final String area = "area1";
        final String distributor = "dist1";
        final String store = "Store1";
        final String action = "Action1";

        CoachingSessionDAO.updateAction(coachingSessionID, action,
                new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        Log.d(TAG, "Update Action : " + isSuccess);
                    }
                });

        CoachingSessionDAO.updateDistributorStoreArea(coachingSessionID, distributor, area,
                store, new CoachingSessionDAO.UpdateCoachingListener() {
                    @Override
                    public void onGuidelineUpdated(boolean isSuccess) {
                        Log.d(TAG, "Update Distributor : " + isSuccess);
                    }
                });

//        CoachingSessionDAO.updateGuideline(coachingSessionID, guideline,
//                new CoachingSessionDAO.UpdateCoachingListener() {
//                    @Override
//                    public void onGuidelineUpdated(boolean isSuccess) {
//                        Log.d(TAG, "Update Guideline : " + isSuccess);
//                    }
//                });

        String[] sebelumID = {"1","2","3","4","4a","4b","4c","4d","4e"};
        String[] saatID = {"1","2","3","3a","3b","3c","3d","3e","4","5","6"};
        String[] setelahID = {"1","2"};

        final List<CoachingQuestionAnswerEntity> coachingQAs = new ArrayList<>();

        for(String id : sebelumID){
            for(int i = 1; i <= 10; i++){
                CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
                coachingQA.setId(RealmUtil.generateID());
                coachingQA.setCoachingSessionID(coachingSessionID);
                coachingQA.setColumnID("customer_ke_" + i);
                coachingQA.setQuestionID("bahasa_dsr_sebelum_" + id);
                coachingQA.setTextAnswer("Right");
                coachingQA.setTickAnswer(true);
                coachingQA.setHasTickAnswer(true);
                coachingQAs.add(coachingQA);
            }
        }

        for(String id : saatID){
            for(int i = 1; i <= 10; i++){
                CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
                coachingQA.setId(RealmUtil.generateID());
                coachingQA.setCoachingSessionID(coachingSessionID);
                coachingQA.setColumnID("customer_ke_" + i);
                coachingQA.setQuestionID("bahasa_dsr_saat_" + id);
                coachingQA.setTextAnswer("Right");
                coachingQA.setTickAnswer(true);
                coachingQA.setHasTickAnswer(true);
                coachingQAs.add(coachingQA);
            }
        }

        for(String id : setelahID){
            for(int i = 1; i <= 10; i++){
                CoachingQuestionAnswerEntity coachingQA = new CoachingQuestionAnswerEntity();
                coachingQA.setId(RealmUtil.generateID());
                coachingQA.setCoachingSessionID(coachingSessionID);
                coachingQA.setColumnID("customer_ke_" + i);
                coachingQA.setQuestionID("bahasa_dsr_setelah_" + id);
                coachingQA.setTextAnswer("Right");
                coachingQA.setTickAnswer(true);
                coachingQA.setHasTickAnswer(true);
                coachingQAs.add(coachingQA);
            }
        }

        CoachingQuestionAnswerDAO.insertCoachingQA(coachingQAs, new CoachingQuestionAnswerDAO.InsertCoachingQAListener() {
            @Override
            public void onInsertQuestionAnswerCompleted(boolean isSuccess) {
                Log.d(TAG, "Insert QAs :" + isSuccess);
            }
        });
    }
}
