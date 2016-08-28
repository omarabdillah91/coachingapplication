package dao;

import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public static List<CoachingQuestionAnswerEntity> filterQAByQuestionID(Collection<CoachingQuestionAnswerEntity> entityList,
                                                                           String questionID){
        List<CoachingQuestionAnswerEntity> result = new ArrayList<>();
        for(CoachingQuestionAnswerEntity entity : entityList){
            if(entity.getQuestionID() == questionID){
                result.add(entity);
            }
        }
        return result;
    }

    public static List<CoachingQuestionAnswerEntity> filterQAByColumnID(Collection<CoachingQuestionAnswerEntity> entityList,
                                                                          String columnID){
        List<CoachingQuestionAnswerEntity> result = new ArrayList<>();
        for(CoachingQuestionAnswerEntity entity : entityList){
            if(entity.getColumnID() == columnID){
                result.add(entity);
            }
        }
        return result;
    }

    public static String[] uniqueColumnID(Collection<CoachingQuestionAnswerEntity> entityList){
        Set<String> result = new HashSet<>();
        for(CoachingQuestionAnswerEntity entity : entityList){
            if(entity.getColumnID().contentEquals("")){
                continue;
            }
            result.add(entity.getColumnID());
        }

        return Arrays.copyOf(result.toArray(), result.size(), String[].class);
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
