package dao;

import java.util.List;

import entity.CoachingQuestionAnswerEntity;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by adrianch on 15/08/2016.
 */
public class CoachingQuestionAnswerDAO {

    public static void getCoachingQA(String coachingSessionID, GetCoachingQAListener listener) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntities =
                realm.where(CoachingQuestionAnswerEntity.class)
                        .equalTo("coachingSessionID", coachingSessionID).findAll();
        listener.onQuestionAnswerReceived(coachingQuestionAnswerEntities);
    }

    public static void insertCoachingQA(List<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntities,
                                        InsertCoachingQAListener listener) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(coachingQuestionAnswerEntities);
        realm.commitTransaction();

        listener.onInsertQuestionAnswerCompleted(true);
    }

    public interface GetCoachingQAListener {
        void onQuestionAnswerReceived(List<CoachingQuestionAnswerEntity>
                                              coachingQuestionAnswerEntityList);
    }

    public interface InsertCoachingQAListener {
        void onInsertQuestionAnswerCompleted(boolean isSuccess);
    }
}
