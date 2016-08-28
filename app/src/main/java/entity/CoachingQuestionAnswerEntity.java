package entity;

import java.util.ArrayList;
import java.util.List;

import dto.CoachingQuestionAnswerDTO;
import io.realm.RealmObject;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingQuestionAnswerEntity extends RealmObject {

    private String id;
    private String coachingSessionID;
    private String questionID;
    private String columnID;
    private boolean tickAnswer;
    private String textAnswer;
    private boolean hasTickAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoachingSessionID() {
        return coachingSessionID;
    }

    public void setCoachingSessionID(String coachingSessionID) {
        this.coachingSessionID = coachingSessionID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getColumnID() {
        return columnID;
    }

    public void setColumnID(String columnID) {
        this.columnID = columnID;
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

    public boolean isHasTickAnswer() {
        return hasTickAnswer;
    }

    public void setHasTickAnswer(boolean hasTickAnswer) {
        this.hasTickAnswer = hasTickAnswer;
    }

    public String getTickStringRep(){
        return isTickAnswer() ? "Ya" : "Tidak";
    }

    public static List<CoachingQuestionAnswerDTO> toDTOs(List<CoachingQuestionAnswerEntity> coachingQuestionAnswerEntityList) {
        List<CoachingQuestionAnswerDTO> coachingQuestionAnswerDTOs = new ArrayList<>();

        for (CoachingQuestionAnswerEntity coachingQuestionAnswerEntity : coachingQuestionAnswerEntityList) {
            CoachingQuestionAnswerDTO dto = new CoachingQuestionAnswerDTO();
            dto.setTextAnswer(coachingQuestionAnswerEntity.getTextAnswer());
            dto.setColumnID(coachingQuestionAnswerEntity.getColumnID());
            dto.setQuestionID(coachingQuestionAnswerEntity.getQuestionID());
            dto.setTickAnswer(coachingQuestionAnswerEntity.isTickAnswer());
            coachingQuestionAnswerDTOs.add(dto);
        }

        return coachingQuestionAnswerDTOs;
    }
}
