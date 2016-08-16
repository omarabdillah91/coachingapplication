package dao;

import java.util.List;

import entity.CoachingQA;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by adrianch on 15/08/2016.
 */
public class CoachingQADAO {

    public static void getCoachingQA(String coachingSessionID, GetCoachingQAListener listener) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingQA> coachingQAs = realm.where(CoachingQA.class)
                .equalTo("coachingSessionID", coachingSessionID).findAll();
        listener.onReceived(coachingQAs);
    }

    public static void insertCoachingQA(List<CoachingQA> coachingQAs,
                                        InsertCoachingQAListener listener){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(coachingQAs);
        realm.commitTransaction();

        listener.onCompleted(true);
    }

    public interface GetCoachingQAListener {
        void onReceived(List<CoachingQA> coachingQAList);
    }

    public interface InsertCoachingQAListener {
        void onCompleted(boolean isSuccess);
    }
}
