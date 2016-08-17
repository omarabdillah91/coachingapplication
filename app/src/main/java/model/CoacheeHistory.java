package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by adrianch on 15/08/2016.
 */
public class CoacheeHistory {

    private String coachName;
    private String coacheeName;
    private String date;
    private String action;

    public CoacheeHistory(String coachName, long date, String action) {
        Date tanggal = new Date(date);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Indonesia"));
        this.coachName = coachName;
        this.date = format.format(tanggal);
        this.action = action;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "CoacheeHistory{" +
                "coachName='" + coachName + '\'' +
                '}';
    }
}
