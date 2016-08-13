package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dto.CoacheeDTO;
import dto.CoacheeHistoryDTO;

/**
 * Created by adria on 8/13/2016.
 */
public class CoacheeService {

    private static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoacheeHistoryService";

    public static void getCoachee(){
        Log.d(TAG, "someshit");
        mDatabase.child("coachee").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            CoacheeDTO dto = d.getValue(CoacheeDTO.class);
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
