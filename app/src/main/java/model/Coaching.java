package model;

/**
 * Created by omar.abdillah on 13/08/2016.
 */
public class Coaching {
    public String coachee;
    public String date;
    public String status;

    public Coaching(String a, String b,String c) {
        this.coachee = a;
        this.date = b;
        this.status = c;
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
