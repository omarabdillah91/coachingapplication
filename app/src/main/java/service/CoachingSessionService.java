package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dto.CoacheeHistoryDTO;
import model.CoacheeHistory;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionService {

    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoachingSessionService";
    private static final String childNode = "coachingSession";

    public static void getCoacheeHistory(String coacheeEmail, final GetCoacheeHistoryServiceListener listener) {
        final List<CoacheeHistory> coacheeHistories = new ArrayList<>();
        mDatabase.child(childNode).orderByChild("coacheeEmail").equalTo(coacheeEmail)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    CoacheeHistoryDTO dto = d.getValue(CoacheeHistoryDTO.class);
                                    Log.d(TAG, dto.toString());
                                    coacheeHistories.add(new CoacheeHistory(dto.getCoachName(),
                                            dto.getDate(), dto.getAction()));
                                    listener.onCoacheeHistoryReceived(coacheeHistories);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                            }
                        });
    }

    public static void insertCoacheeHistory(String coacheeID, CoacheeHistoryDTO coacheeHistoryDTO,
                                            final InsertCoacheeHistoryServiceListener listener) {

        final DatabaseReference newRef = mDatabase.child(childNode).child(coacheeID).push();
        newRef.setValue(coacheeHistoryDTO, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d(TAG, databaseError.toString());
                }
                listener.onInsertSessionCompleted(newRef.getKey());
            }
        });

    }

    public interface GetCoacheeHistoryServiceListener {
        void onCoacheeHistoryReceived(List<CoacheeHistory> coacheeHistories);
    }

    public interface InsertCoacheeHistoryServiceListener {
        void onInsertSessionCompleted(String coacheeHistoryID);
    }
}
