package dao;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import entity.CoachingSessionEntity;
import io.realm.Realm;
import io.realm.RealmResults;
import model.Coaching;
import utility.ConstantUtil;
import utility.SharedPreferenceUtil;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionDAO {

    private static final String TAG ="CoachingSessionDAO";
    //private static final Realm realm = Realm.getDefaultInstance();

    public static void deleteUnfinishedCoaching(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CoachingSessionEntity> sessionList = realm.where(CoachingSessionEntity.class).
                equalTo("isFinished",false).findAll();
        realm.beginTransaction();
        sessionList.deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void getUnsubmittedCoaching(GetListCoachingListener listener) {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<Coaching> coachingList = new ArrayList<>();
        RealmResults<CoachingSessionEntity> sessionList = realm.where(CoachingSessionEntity.class)
                .equalTo("isSubmitted", false).equalTo("isFinished", true).findAll();
        for (CoachingSessionEntity cs : sessionList) {
            Date date = new Date(cs.getDate());
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            format.setTimeZone(TimeZone.getTimeZone("Indonesia"));
            String date_formatted = format.format(date);
            String status = "";
            coachingList.add(new Coaching(cs.getId(), cs.getCoacheeName(),
                    date_formatted, (cs.isSubmitted()) ? "Sent" : "Submitted"));
        }
        listener.onUnsubmittedCoachingReceived(coachingList);
    }

    public static void getCoachingSession(String coachingSessionID, GetCoachingListener listener){
        Realm realm = Realm.getDefaultInstance();
        CoachingSessionEntity entity =
                realm.where(CoachingSessionEntity.class)
                        .equalTo("id", coachingSessionID).findFirst();


        if(entity != null){
            Log.d(TAG, entity.toString());
            listener.onCoachingReceived(entity);
        } else {
            listener.onCoachingReceived(null);
        }
    }

    public static void insertNewCoaching(String coacheeName, String coacheeEmail,
                                         String coacheePosition, String firstManagerName,
                                         String firstManagerEmail, String firstManagerPosition,
                                         String cdTeamName,
                                         String cdTeamEmail, String cdTeamPosition,
                                         final InsertCoachingListener listener) {
        Realm realm = Realm.getDefaultInstance();

        String coachID = SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_ID);
        String coachName = SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_NAME);

        CoachingSessionEntity entity = new CoachingSessionEntity(coacheeName, coacheeEmail,
                coacheePosition, firstManagerName, firstManagerEmail, firstManagerPosition,
                "", "", "", cdTeamName,
                cdTeamEmail, cdTeamPosition, coachID, coachName);

        realm.beginTransaction();
        realm.copyToRealm(entity);
        realm.commitTransaction();

        listener.onInsertCoachingCompleted(entity.getId());

        /*
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(entity);
                listener.onInsertCoachingCompleted(entity.getId());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Insert Success");
                listener.onInsertCoachingCompleted(entity.getId());
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                listener.onInsertCoachingCompleted(null);
            }
        });*/
    }

    public static void updateGuideline(final String coachingSessionID,
                                               final int coachingGuideline,
                                               final int language,
                                               final UpdateCoachingListener listener) {
        Realm realm = Realm.getDefaultInstance();
        CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                .equalTo("id", coachingSessionID).findFirst();

        if(entity != null){
            realm.beginTransaction();
            entity.setCoachingGuideline(coachingGuideline);
            entity.setLanguage(language);
            realm.commitTransaction();
        }

        listener.onGuidelineUpdated(entity != null);
        /*realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                        .equalTo("id", coachingSessionID).findFirst();

                entity.setCoachingGuideline(coachingGuideline);
                listener.onGuidelineUpdated(true);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onGuidelineUpdated(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                listener.onGuidelineUpdated(false);
            }
        });*/
    }

    public static void updateDistributorStoreArea(final String coachingSessionID,
                                                  final String distributor,
                                                  final String area, final String store,
                                                  final UpdateCoachingListener listener){
        Realm realm = Realm.getDefaultInstance();
        CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                .equalTo("id", coachingSessionID).findFirst();


        if(entity != null){
            realm.beginTransaction();
            entity.setDistributor(distributor);
            entity.setArea(area);
            entity.setStore(store);
            realm.commitTransaction();
        }

        listener.onGuidelineUpdated(entity != null);

        /*realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                        .equalTo("id", coachingSessionID).findFirst();

                entity.setDistributor(distributor);
                entity.setArea(area);
                entity.setStore(store);
                listener.onGuidelineUpdated(true);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onGuidelineUpdated(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                listener.onGuidelineUpdated(false);
            }
        });*/
    }

    public static void updateAction(final String coachingSessionID, final String action,
                                    final UpdateCoachingListener listener){
        Realm realm = Realm.getDefaultInstance();

        CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                .equalTo("id", coachingSessionID).findFirst();

        if(entity != null){
            realm.beginTransaction();
            entity.setAction(action);
            entity.setFinished(true);
            realm.commitTransaction();
        }

        listener.onGuidelineUpdated(entity != null);

        /*realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                        .equalTo("id", coachingSessionID).findFirst();

                entity.setAction(action);
                listener.onGuidelineUpdated(false);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                listener.onGuidelineUpdated(true);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d(TAG, error.getMessage());
                listener.onGuidelineUpdated(false);
            }
        });*/
    }

    public static void updateSubmitted(final String coachingSessionID, boolean isSubmitted,
                                       final UpdateCoachingListener listener){
        Realm realm = Realm.getDefaultInstance();
        CoachingSessionEntity entity = realm.where(CoachingSessionEntity.class)
                .equalTo("id", coachingSessionID).findFirst();

        if(entity != null){
            realm.beginTransaction();
            entity.setSubmitted(true);
            realm.commitTransaction();
        }

        listener.onGuidelineUpdated(entity != null);
    }

    public interface GetCoachingListener {
        void onCoachingReceived(CoachingSessionEntity coachingSessionEntity);
    }

    public interface GetListCoachingListener {
        void onUnsubmittedCoachingReceived(List<Coaching> coachingList);
    }

    public interface InsertCoachingListener {
        void onInsertCoachingCompleted(String id);
    }

    public interface UpdateCoachingListener {
        void onGuidelineUpdated(boolean isSuccess);
    }
}
