package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dto.CoachingSessionDTO;
import model.CoacheeHistory;
import utility.ConstantUtil;
import utility.SharedPreferenceUtil;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionService {

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoachingSessionService";
    private static final String childNode = "coachingSession";

    public static void getCoacheeHistory(String coacheeEmail, final GetCoacheeHistoryListener listener) {
        final List<CoacheeHistory> coacheeHistories = new ArrayList<>();
        mDatabase.child(childNode).orderByChild("coacheeEmail").equalTo(coacheeEmail)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    CoachingSessionDTO dto = d.getValue(CoachingSessionDTO.class);
                                    Log.d(TAG, dto.toString());

                                    if(dto.getCoachName().equals(SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_EMAIL))){
                                        coacheeHistories.add(new CoacheeHistory(dto.getCoachName(),
                                                dto.getDate(), dto.getAction()));
                                    }
                                }
                                listener.onCoacheeHistoryReceived(coacheeHistories);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                                listener.onCoacheeHistoryReceived(coacheeHistories);
                            }
                        });
    }

    public static void getCoachingHistory(String coachEmail, final GetCoacheeHistoryListener listener){
        final List<CoacheeHistory> coacheeHistories = new ArrayList<>();
        mDatabase.child(childNode).orderByChild("coachName").equalTo(coachEmail)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    CoachingSessionDTO dto = d.getValue(CoachingSessionDTO.class);
                                    Log.d(TAG, dto.toString());

                                    /*if(dto.getCoachName().equals(SharedPreferenceUtil.getString(ConstantUtil.SP_COACH_EMAIL))){
                                        coacheeHistories.add(new CoacheeHistory(dto.getCoachName(),
                                                dto.getDate(), dto.getAction()));
                                    }*/
                                    coacheeHistories.add(new CoacheeHistory(dto.getCoacheeName(),
                                            dto.getDate(), dto.getAction()));
                                }
                                listener.onCoacheeHistoryReceived(coacheeHistories);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                                listener.onCoacheeHistoryReceived(coacheeHistories);
                            }
                        });
    }

    public static void insertCoachingSession(String coachingSessionID, CoachingSessionDTO coachingSessionDTO,
                                             final InsertCoachingSessionListener listener) {

        final DatabaseReference newRef = mDatabase.child(childNode).child(coachingSessionID);
        newRef.setValue(coachingSessionDTO, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d(TAG, databaseError.toString());
                }
                listener.onInsertSessionCompleted(databaseError == null);
            }
        });

    }

    public interface GetCoacheeHistoryListener {
        void onCoacheeHistoryReceived(List<CoacheeHistory> coacheeHistories);
    }

    public interface InsertCoachingSessionListener {
        void onInsertSessionCompleted(boolean isSuccess);
    }
}
