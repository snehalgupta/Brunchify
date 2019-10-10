package Models;

public class Meetup {

    public String date;
    public String time;
    public User match;
    public String status;
    public int rating;

    public Meetup(String m_date, String m_time, User m_match) {
        date = m_date;
        time = m_time;
        match = m_match;
    }

    public void update_status(String m_status) {
        status = m_status;
    }

    public void rate_meetup(int m_rating) {
        rating = m_rating;
    }
}