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
public class CoacheeHistoryService {

    private static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoacheeHistoryService";

    public static void getCoacheeHistory(String coacheeID, final GetCoacheeHistoryServiceListener listener){
        final List<CoacheeHistory> coacheeHistories = new ArrayList<>();
        mDatabase.child("coacheeHistory").child(coacheeID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            CoacheeHistoryDTO dto = d.getValue(CoacheeHistoryDTO.class);
                            Log.d(TAG, dto.toString());
                            coacheeHistories.add(new CoacheeHistory(dto.getCoachName()));
                            listener.onReceived(coacheeHistories);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    public interface GetCoacheeHistoryServiceListener {
        void onReceived(List<CoacheeHistory> coacheeHistories);
    }
}
