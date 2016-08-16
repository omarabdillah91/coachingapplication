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
public class CoachingActivityService {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoachingActivityService";
    private static final String childNode = "coachingActivity";

    public static void insertCoachingActivity(String coachingSessionID,
                                              List<CoachingActivityDTO> coachingActivityDTOs,
                                              final CoachingQADAO.InsertCoachingQAListener listener){

        DatabaseReference newRef = mDatabase.child(childNode).child(coachingSessionID);
        newRef.setValue(coachingActivityDTOs, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError != null){
                    Log.d(TAG, databaseError.toString());
                }
                listener.onCompleted(true);
            }
        });

    }

    public interface InsertCoachingActivityListener {
        void onCompleted(boolean isSuccess);
    }
}
