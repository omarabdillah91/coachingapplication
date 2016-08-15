package dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import entity.CoachingSession;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coaching;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionDAO {

    public static void getUnsubmittedCoaching(GetCoachingListener listener){
        ArrayList<Coaching> coachingList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSession> sessionList = realm.where(CoachingSession.class)
                .equalTo("isSubmitted",false).findAll();
        for(CoachingSession cs : sessionList){
            Date date = new Date(cs.getDate());
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Indonesia"));
            String date_formatted = format.format(date);
            String status = "";
            coachingList.add(new Coaching(cs.getCoacheeName(),
                    date_formatted,(cs.isSubmitted())?"Unsent": "Sent"));
        }
        listener.onReceived(coachingList);
    }

    public static void insertCoaching(CoachingSession coachingSession,
                                      InsertCoachingListener listener){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(coachingSession);
        realm.commitTransaction();

        listener.onCompleted(true);
    }

    public interface GetCoachingListener {
        void onReceived(List<Coaching> coachingList);
    }

    public interface InsertCoachingListener {
        void onCompleted(boolean isSuccess);
    }
}
