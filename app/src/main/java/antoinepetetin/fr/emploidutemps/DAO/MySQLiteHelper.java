package antoinepetetin.fr.emploidutemps.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_COLOR;
import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_DESCRIPTION;
import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_END;
import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_ID;
import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_START;
import static antoinepetetin.fr.emploidutemps.DAO.EventsDataSource.COLUMN_SUMMARY;

/**
 * Created by antoine on 17/12/16.
 */

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_EVENTS = "events";

    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE = "create table " + TABLE_EVENTS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_START + " text not null, "
            + COLUMN_END + " text not null, "
            + COLUMN_SUMMARY + " text not null, "
            + COLUMN_DESCRIPTION + " text not null,"
            + COLUMN_COLOR + " text not null"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }
}
