package antoinepetetin.fr.emploidutemps.Models;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;

    public MyDate(String date) {
        day = Integer.parseInt(date.substring(0,2));
        month = Integer.parseInt(date.substring(3,5));
        year = Integer.parseInt(date.substring(6,10));
        hour = Integer.parseInt(date.substring(11,13));
        minutes = Integer.parseInt(date.substring(14,16));
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public Calendar toGregorianCalendar(){
        return new GregorianCalendar(year,month,day,hour, minutes);
    }

    @Override
    public String toString() {
        return day+"/"+month+"/"+year+" "+hour+"h"+minutes;
    }
}
