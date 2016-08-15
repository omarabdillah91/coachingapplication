package model;

/**
 * Created by adrianch on 15/08/2016.
 */
public class Coachee {

    private String name;
    private String firebaseID;

    public Coachee(String name) {
        this.name = name;
    }

    public Coachee(String name, String firebaseID) {
        this.name = name;
        this.firebaseID = firebaseID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }
    @Override
    public String toString() {
        return "Coachee{" +
                "name='" + name + '\'' +
                ", firebaseID='" + firebaseID + '\'' +
                '}';
    }
}
