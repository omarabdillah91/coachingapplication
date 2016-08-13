package entity;

import io.realm.RealmObject;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingQA extends RealmObject {

    private String guid;
    private String coachingSessionID;
    private String columnID;
    private String questionID;
    private boolean tickAnswer;
    private String textAnswer;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCoachingSessionID() {
        return coachingSessionID;
    }

    public void setCoachingSessionID(String coachingSessionID) {
        this.coachingSessionID = coachingSessionID;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public boolean isTickAnswer() {
        return tickAnswer;
    }

    public void setTickAnswer(boolean tickAnswer) {
        this.tickAnswer = tickAnswer;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }
}
