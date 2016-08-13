package dto;

/**
 * Created by adria on 8/13/2016.
 */
public class CoachingActivityDTO {
    private String questionID;
    private String columnID;
    private String textAnswer;
    private boolean tickAnswer;

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

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

    public boolean isTickAnswer() {
        return tickAnswer;
    }

    public void setTickAnswer(boolean tickAnswer) {
        this.tickAnswer = tickAnswer;
    }
}
