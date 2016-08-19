package service;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dto.UserDataDTO;

/**
 * Created by adrian on 8/19/2016.
 */
public class UserDataService {

    private static DatabaseReference mDatabase =  FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "UserDataService";
    private static final String node = "userData";

    public static void getUserData(final String userID, final GetUserDataListener listener){
        mDatabase.child(node).child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDataDTO userDataDTO = dataSnapshot.getValue(UserDataDTO.class);
                Log.d(TAG, userDataDTO.toString());
                listener.onUserDataReceived(userDataDTO);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                listener.onUserDataReceived(null);
            }
        });
    }

    public interface GetUserDataListener {
        void onUserDataReceived(UserDataDTO userDataDTO);
    }
}
