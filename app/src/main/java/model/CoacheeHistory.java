package model;

/**
 * Created by adrianch on 15/08/2016.
 */
public class CoacheeHistory {

    private String coachName;
    private String coacheeName;

    public CoacheeHistory(String coachName) {
        this.coachName = coachName;
    }

    public String getCoacheeName() {
        return coacheeName;
    }

    public void setCoacheeName(String coacheeName) {
        this.coacheeName = coacheeName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    @Override
    public String toString() {
        return "CoacheeHistory{" +
                "coachName='" + coachName + '\'' +
                '}';
    }
}
