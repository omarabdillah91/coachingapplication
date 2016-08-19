package service;

import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import dao.CoachingQADAO;
import dto.CoachingActivityDTO;

/**
 * Created by adrianch on 15/08/2016.
 */
public class CoachingQuestionAnswerService {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoachingQAService";
    private static final String childNode = "coachingQuestionAnswer";

    public static void insertCoachingActivity(String coachingSessionID,
                                              List<CoachingActivityDTO> coachingActivityDTOs,
                                              final InsertCoachingQuestionAnswerListener listener){

        DatabaseReference newRef = mDatabase.child(childNode).child(coachingSessionID);
        newRef.setValue(coachingActivityDTOs, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.d(TAG, databaseError.toString());
                }
                listener.onInsertQuestionAnswerCompleted(true);
            }
        });

    }

    public interface InsertCoachingQuestionAnswerListener {
        void onInsertQuestionAnswerCompleted(boolean isSuccess);
    }
}
