package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dto.CoacheeHistoryDTO;

/**
 * Created by adria on 8/13/2016.
 */
public class CoacheeHistoryService {

    private static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoacheeHistoryService";

    public static void getCoacheeHistory(String coacheeID){
        Log.d(TAG, "someshit");
        mDatabase.child("coacheeHistory").child(coacheeID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            CoacheeHistoryDTO dto = d.getValue(CoacheeHistoryDTO.class);
                            Log.d(TAG, dto.toString());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }
}
