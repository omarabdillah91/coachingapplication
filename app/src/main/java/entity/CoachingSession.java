package entity;

import io.realm.RealmObject;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSession extends RealmObject {

    private String guid;
    private String coacheeID;
    private String coachName;
    private String coachID;
    private String store;
    private String distributor;
    private String area;
    private int coachingGuideline;
    private long date;
    private long dateCreated;
    private boolean isSubmitted;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCoacheeID() {
        return coacheeID;
    }

    public void setCoacheeID(String coacheeID) {
        this.coacheeID = coacheeID;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getCoachingGuideline() {
        return coachingGuideline;
    }

    public void setCoachingGuideline(int coachingGuideline) {
        this.coachingGuideline = coachingGuideline;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }
}
