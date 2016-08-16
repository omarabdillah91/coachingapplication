package dto;

/**
 * Created by adria on 8/13/2016.
 */
public class CoacheeHistoryDTO {

    private String coachID;
    private String coachName;
    private String distributor;
    private String store;
    private String area;
    private String action;
    private long date;
    private long dateCreated;
    private int coachingGuideline;

    public CoacheeHistoryDTO() {
    }

    public CoacheeHistoryDTO(String coachID, String coachName, String distributor, String store,
                             String area, String action,int coachingGuideline) {
        this.coachID = coachID;
        this.coachName = coachName;
        this.distributor = distributor;
        this.store = store;
        this.area = area;
        this.action = action;
        this.date = System.currentTimeMillis() / 1000;
        this.dateCreated = System.currentTimeMillis() / 1000;
        this.coachingGuideline = coachingGuideline;
    }

    public String getCoachID() {
        return coachID;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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

    public int getCoachingGuideline() {
        return coachingGuideline;
    }

    public void setCoachingGuideline(int coachingGuideline) {
        this.coachingGuideline = coachingGuideline;
    }

    @Override
    public String toString() {
        return "CoacheeHistoryDTO{" +
                "coachName='" + coachName + '\'' +
                ", coachID='" + coachID + '\'' +
                '}';
    }
}
