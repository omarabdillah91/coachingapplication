package dao;

import java.util.ArrayList;
import java.util.List;

import entity.CoachingSession;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coaching;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionDAO {

    public static void getUnsubmittedCoaching(CoachingSessionListener listener){
        ArrayList<Coaching> coachingList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class)
                .equalTo("isSubmitted",false).findAll();
        for(CoachingSession cs : sessionList){
            coachingList.add(new Coaching(cs.getCoacheeID(),
                    String.valueOf(cs.getDate()), String.valueOf(cs.isSubmitted())));
        }
        listener.onCoachingReceived(coachingList);
    }

    public static void insertCoaching(CoachingSession coachingSession, CoachingSessionListener listener){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(coachingSession);
        realm.commitTransaction();

        listener.onCoachingInserted(true);
    }


    public interface CoachingSessionListener {
        void onCoachingReceived(List<Coaching> coachingList);
        void onCoachingInserted(boolean succees);
    }
}
