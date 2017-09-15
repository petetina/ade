package antoinepetetin.fr.emploidutemps.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import antoinepetetin.fr.emploidutemps.Models.Event;

/**
 * Created by antoine on 17/12/16.
 */

public class EventsDataSource {
    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_START = "start";
    public static final String COLUMN_END = "end";
    public static final String COLUMN_SUMMARY = "summary";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COLOR = "color";

    private String[] allColumns = { COLUMN_ID, COLUMN_START, COLUMN_END, COLUMN_SUMMARY, COLUMN_DESCRIPTION, COLUMN_COLOR };

    public EventsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    private Event insert(Event event) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_START, event.getStart().toString());
        values.put(COLUMN_END, event.getEnd().toString());
        values.put(COLUMN_SUMMARY, event.getSummary());
        values.put(COLUMN_DESCRIPTION, event.getDescription());
        values.put(COLUMN_COLOR,event.getColor());

        long insertId = database.insert(MySQLiteHelper.TABLE_EVENTS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS,
                allColumns, COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Event newEvent = cursorToEvent(cursor);
        cursor.close();
        return newEvent;
    }

    //Retourne le nombre de lignes insérées dans la base
    public long saveAll(List<Event> liste)
    {
        open();
        deleteAll();
        for(int i=0; i<liste.size(); i++)
        {
            insert(liste.get(i));
        }
        long count = getNbEvents();
        close();
        return count;
    }
    //Retourne le nombre de lignes supprimées
    public int deleteAll() {
        return database.delete(MySQLiteHelper.TABLE_EVENTS, "1", null);
    }

    public List<Event> getAllEvents() {
        open();
        List<Event> events = new ArrayList<>();
        if(getNbEvents()>0) {
            Cursor cursor = database.query(MySQLiteHelper.TABLE_EVENTS,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Event event = cursorToEvent(cursor);
                events.add(event);
                cursor.moveToNext();
            }
            // assurez-vous de la fermeture du curseur
            cursor.close();
            close();
        }
        return events;
    }

    private long getNbEvents() {
        long cnt  = DatabaseUtils.queryNumEntries(dbHelper.getReadableDatabase(), MySQLiteHelper.TABLE_EVENTS);
        return cnt;
    }

    private Event cursorToEvent(Cursor cursor) {
        Event event = new Event();
        event.setStart(cursor.getString(1));
        event.setEnd(cursor.getString(2));
        event.setSummary(cursor.getString(3));
        event.setDescription(cursor.getString(4));
        event.setColor(cursor.getString(5));

        return event;
    }
}
