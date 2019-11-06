package Models;

public class Meetup {

    public String date;
    public String time;
    public String match;
    public String status;
    public int rating;

    public Meetup() {
    }

    public Meetup(String m_date, String m_time, String m_match) {
        date = m_date;
        time = m_time;
        match = m_match;
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

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
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