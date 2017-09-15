package antoinepetetin.fr.emploidutemps.Models;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import antoinepetetin.fr.emploidutemps.R;

/**
 * Created by antoine on 17/12/16.
 */

public class Event {
    private String start;
    private String end;
    private String summary;
    private String description;
    private String color;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<WeekViewEvent> getWeekViewEvents(Context ctx, List<Event> liste, int month, int year){
        List<WeekViewEvent> resultat = new ArrayList<>();

        for(int i=0; i<liste.size(); i++)
        {
            MyDate date = new MyDate(liste.get(i).getStart());
            if(date.getMonth()== month && date.getYear()== year) {
                resultat.add(liste.get(i).adapter(ctx));
            }
        }
        return resultat;
    }

    private WeekViewEvent adapter(Context ctx){

        MyDate start = new MyDate(getStart());
        Calendar startTime = new GregorianCalendar(2017,start.getMonth()-1,start.getDay(),start.getHour(),start.getMinutes());

        //Pareil pour endTime
        MyDate end = new MyDate(getEnd());
        Calendar endTime = new GregorianCalendar(2017,end.getMonth()-1,end.getDay(),end.getHour(),end.getMinutes());

        //Construction de l'évènement à afficher
        WeekViewEvent resultat = new WeekViewEvent(1,getSummary(),getDescription(), startTime,endTime);

        //Log.e("cours","getStart = " +getStart() + "?= start = "+ start +" et getEnd = " + getEnd() + "?= end=" + end +" et summary = "+summary);

        //Gestion des couleurs
        String colors[] = color.replace(" ","").replace("rgb(","").replace(")","").split(",");
        resultat.setColor(
                Color.rgb(
                    Integer.parseInt(colors[0]),
                    Integer.parseInt(colors[1]),
                    Integer.parseInt(colors[2])
                )

        );

        return resultat;
    }
}
