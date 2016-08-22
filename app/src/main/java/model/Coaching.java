package model;

/**
 * Created by omar.abdillah on 13/08/2016.
 */
public class Coaching {
    public String id;
    public String coachee;
    public String date;
    public String status;

    public Coaching(String id, String a, String b,String c) {
        this.id = id;
        this.coachee = a;
        this.date = b;
        this.status = c;
    }

    public void setCoachee(String coachee) {
        this.coachee = coachee;
    }

    public String getCoachee() {
        return coachee;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Coaching{" +
                "coachee='" + coachee + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
