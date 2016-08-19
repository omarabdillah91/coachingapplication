package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dto.CoacheeDTO;
import dto.CoacheeHistoryDTO;
import model.Coachee;

/**
 * Created by adria on 8/13/2016.
 */
public class CoacheeService {

    private static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "CoachingSessionService";

    public static void getCoachee(final GetCoacheeListener listener){
        final List<Coachee> coacheeList = new ArrayList<>();
        mDatabase.child("coachee").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot d : dataSnapshot.getChildren()){
                            CoacheeDTO dto = d.getValue(CoacheeDTO.class);
                            coacheeList.add(new Coachee(dto.getName(), d.getKey()));
                        }
                        listener.onReceived(coacheeList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
    }

    public interface GetCoacheeListener {
        void onReceived(List<Coachee> coacheeList);
    }
}
