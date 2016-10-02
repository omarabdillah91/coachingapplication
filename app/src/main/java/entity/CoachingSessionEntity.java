package entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import dto.CoachingSessionDTO;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import utility.ConstantUtil;
import utility.RealmUtil;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingSessionEntity extends RealmObject {

    @PrimaryKey
    private String id;
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
    private boolean isSubmitted;
    private boolean isFinished;

    public CoachingSessionEntity(){}

    public CoachingSessionEntity(String coacheeName, String coacheeEmail,
                                 String coacheePosition, String firstManagerName,
                                 String firstManagerEmail, String firstManagerPosition,
                                 String secondManagerName, String secondManagerEmail,
                                 String secondManagerPosition, String cdCapabilityTeamName,
                                 String cdCapabilityTeamEmail, String cdCapabilityTeamPosition,
                                 String coachID, String coachName) {
        this.id = RealmUtil.generateID();
        this.coacheeName = coacheeName;
        this.coacheeEmail = coacheeEmail;
        this.coacheePosition = coacheePosition;
        this.firstManagerName = firstManagerName;
        this.firstManagerEmail = firstManagerEmail;
        this.firstManagerPosition = firstManagerPosition;
        this.secondManagerName = secondManagerName;
        this.secondManagerEmail = secondManagerEmail;
        this.secondManagerPosition = secondManagerPosition;
        this.cdCapabilityTeamName = cdCapabilityTeamName;
        this.cdCapabilityTeamEmail = cdCapabilityTeamEmail;
        this.cdCapabilityTeamPosition = cdCapabilityTeamPosition;
        this.coachID = coachID;
        this.coachName = coachName;
        this.isSubmitted = false;
        this.isFinished = false;
        this.date = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isSubmitted() {
        return isSubmitted;
    }

    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getFormattedDate(){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Indonesia"));
        return format.format(getDate());
    }

    public String getGuidelineRep(){
        String guideline = "";
        if(getCoachingGuideline() == ConstantUtil.GUIDELINE_DSR){
            guideline = "DSR";
        } else if(getCoachingGuideline() == ConstantUtil.GUIDELINE_FASA) {
            guideline = "FA_SA_Merchandiser";
        } else if(getCoachingGuideline() == ConstantUtil.GUIDELINE_DTS_PULL){
            guideline = "DTS_PULL";
        } else if(getCoachingGuideline() == ConstantUtil.GUIDELINE_SR_PULL){
            guideline = "SR_PULL";
        } else if(getCoachingGuideline() == ConstantUtil.GUIDELINE_ASM_PULL){
            guideline = "ASM_PULL";
        } else if(getCoachingGuideline() == ConstantUtil.GUIDELINE_ASM_PUSH){
            guideline = "ASM_PUSH";
        }
        return guideline;
    }

    public String getPdfFileName(){
       return getGuidelineRep() + " - " + getCoacheeName() + " - " + getCoachName() + " - " + getFormattedDate() + ".pdf";
       //return "test.pdf";
    }

    public CoachingSessionDTO toDTO(){
        CoachingSessionDTO dto = new CoachingSessionDTO();
        dto.setCoacheeName(coacheeName);
        dto.setCoacheeEmail(coacheeEmail);
        dto.setCoacheePosition(coacheePosition);
        dto.setFirstManagerName(firstManagerName);
        dto.setFirstManagerPosition(firstManagerPosition);
        dto.setFirstManagerEmail(firstManagerEmail);
        dto.setSecondManagerName(secondManagerName);
        dto.setSecondManagerEmail(secondManagerEmail);
        dto.setSecondManagerPosition(secondManagerPosition);
        dto.setCdCapabilityTeamName(cdCapabilityTeamName);
        dto.setCdCapabilityTeamEmail(cdCapabilityTeamEmail);
        dto.setCdCapabilityTeamPosition(cdCapabilityTeamPosition);
        dto.setCoachID(coachID);
        dto.setCoachName(coachName);
        dto.setDistributor(distributor);
        dto.setStore(store);
        dto.setArea(area);
        dto.setAction(action);
        dto.setDate(date);
        dto.setDateCreated(System.currentTimeMillis() / 1000);
        dto.setCoachingGuideline(coachingGuideline);

        return dto;
    }

    @Override
    public String toString() {
        return "CoachingSessionEntity{" +
                "id='" + id + '\'' +
                ", coacheeName='" + coacheeName + '\'' +
                ", coacheeEmail='" + coacheeEmail + '\'' +
                ", coacheePosition='" + coacheePosition + '\'' +
                ", coachID='" + coachID + '\'' +
                ", coachName='" + coachName + '\'' +
                ", distributor='" + distributor + '\'' +
                ", store='" + store + '\'' +
                ", area='" + area + '\'' +
                ", action='" + action + '\'' +
                ", date=" + date +
                ", coachingGuideline=" + coachingGuideline +
                ", isSubmitted=" + isSubmitted +
                '}';
    }
}
