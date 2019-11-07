package Models;

public class Meetup {

    public String date;
    public String time;
    public String match1;
    public String match2;
    public String status;
    public int rating;

    public Meetup() {
    }

    public Meetup(String m_date, String m_time, String m_match1, String m_match2) {
        date = m_date;
        time = m_time;
        match1 = m_match1;
        match2 = m_match2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void update_status(String m_status) {
        status = m_status;
    }

    public void rate_meetup(int m_rating) {
        rating = m_rating;
    }
}