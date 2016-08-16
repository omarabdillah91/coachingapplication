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
import model.CoacheeHistory;
import model.Coaching;
import utility.RealmUtil;

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

    public static void insertCoaching(String coacheeID, String coacheeName, String coachName,
                                      String coachID, String store, String distributor, String area,
                                      int coachingGuideline, InsertCoachingListener listener){

        CoachingSession coachingSession = new CoachingSession(coacheeID, coacheeName, coachName,
                coachID, store, distributor, area, coachingGuideline);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(coachingSession);
        realm.commitTransaction();

        listener.onCompleted(coachingSession.getGuid());
    }

    public interface GetCoachingListener {
        void onReceived(List<Coaching> coachingList);
    }

    public interface InsertCoachingListener {
        void onCompleted(String guid);
    }
}
