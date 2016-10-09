package dto;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionDTO {

    private String coacheeName;
    private String coacheeEmail;
    private String coacheePosition;
    private String firstManagerName;
    private String firstManagerEmail;
    private String firstManagerPosition;
    private String secondManagerName;
    private String secondManagerEmail;
    private String secondManagerPosition;
    private String cdCapabilityTeamName;
    private String cdCapabilityTeamEmail;
    private String cdCapabilityTeamPosition;
    private String coachID;
    private String coachName;
    private String distributor;
    private String store;
    private String area;
    private String action;
    private long date;
    private long dateCreated;
    private int coachingGuideline;
    private int language;

    public CoachingSessionDTO() {
    }

    public CoachingSessionDTO(String coachID, String coachName, String distributor, String store,
                              String area, String action, int coachingGuideline) {
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

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getCoacheeName() {
        return coacheeName;
    }

    public void setCoacheeName(String coacheeName) {
        this.coacheeName = coacheeName;
    }

    public String getCoacheeEmail() {
        return coacheeEmail;
    }

    public void setCoacheeEmail(String coacheeEmail) {
        this.coacheeEmail = coacheeEmail;
    }

    public String getCoacheePosition() {
        return coacheePosition;
    }

    public void setCoacheePosition(String coacheePosition) {
        this.coacheePosition = coacheePosition;
    }

    public String getFirstManagerName() {
        return firstManagerName;
    }

    public void setFirstManagerName(String firstManagerName) {
        this.firstManagerName = firstManagerName;
    }

    public String getFirstManagerEmail() {
        return firstManagerEmail;
    }

    public void setFirstManagerEmail(String firstManagerEmail) {
        this.firstManagerEmail = firstManagerEmail;
    }

    public String getFirstManagerPosition() {
        return firstManagerPosition;
    }

    public void setFirstManagerPosition(String firstManagerPosition) {
        this.firstManagerPosition = firstManagerPosition;
    }

    public String getSecondManagerName() {
        return secondManagerName;
    }

    public void setSecondManagerName(String secondManagerName) {
        this.secondManagerName = secondManagerName;
    }

    public String getSecondManagerEmail() {
        return secondManagerEmail;
    }

    public void setSecondManagerEmail(String secondManagerEmail) {
        this.secondManagerEmail = secondManagerEmail;
    }

    public String getSecondManagerPosition() {
        return secondManagerPosition;
    }

    public void setSecondManagerPosition(String secondManagerPosition) {
        this.secondManagerPosition = secondManagerPosition;
    }

    public String getCdCapabilityTeamName() {
        return cdCapabilityTeamName;
    }

    public void setCdCapabilityTeamName(String cdCapabilityTeamName) {
        this.cdCapabilityTeamName = cdCapabilityTeamName;
    }

    public String getCdCapabilityTeamEmail() {
        return cdCapabilityTeamEmail;
    }

    public void setCdCapabilityTeamEmail(String cdCapabilityTeamEmail) {
        this.cdCapabilityTeamEmail = cdCapabilityTeamEmail;
    }

    public String getCdCapabilityTeamPosition() {
        return cdCapabilityTeamPosition;
    }

    public void setCdCapabilityTeamPosition(String cdCapabilityTeamPosition) {
        this.cdCapabilityTeamPosition = cdCapabilityTeamPosition;
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
        return "CoachingSessionDTO{" +
                "coacheeName='" + coacheeName + '\'' +
                ", coacheeEmail='" + coacheeEmail + '\'' +
                ", coachName='" + coachName + '\'' +
                ", coachID='" + coachID + '\'' +
                '}';
    }
}
