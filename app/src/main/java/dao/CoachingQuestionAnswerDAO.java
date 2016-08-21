package dao;

import android.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void getCoachingQA(String coachingSessionID, GetCoachingQAMapListener listener){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntities =
                realm.where(CoachingQuestionAnswerEntity.class)
                        .equalTo("coachingSessionID", coachingSessionID).findAll();

        Map<Pair<String, String>, CoachingQuestionAnswerEntity> map = new HashMap<>();

        for(CoachingQuestionAnswerEntity entity: coachingQuestionAnswerEntities){
            map.put(new Pair<>(entity.getQuestionID(), entity.getColumnID()), entity);
        }

        listener.onQAMapReceived(map);

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

    public interface GetCoachingQAMapListener {
        void onQAMapReceived(Map<Pair<String, String>, CoachingQuestionAnswerEntity> qaMap);
    }

    public interface InsertCoachingQAListener {
        void onInsertQuestionAnswerCompleted(boolean isSuccess);
    }
}
