package lingchuang.lesson_0812_sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2015/8/12.
 */
public class DBHelper extends SQLiteOpenHelper {
    static String DBname="business";
    static int DBversion=1;
    String createStr="Create Table users( _id INTEGER PRIMARY KEY autoincrement, name TEXT not null, tel TEXT, email TEXT);";

    public DBHelper(Context context) {
        super(context, DBname, null,
                DBversion);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createStr);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
