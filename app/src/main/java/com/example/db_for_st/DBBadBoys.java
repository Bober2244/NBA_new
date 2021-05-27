package com.example.db_for_st;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBBadBoys {

    private static final String DATABASE_NAME = "Badboys.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Boy";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BADBOY = "BadBoy";
    private static final String COLUMN_GOALSHOME = "GoalsHome";
    private static final String COLUMN_GOALSGUAST = "GoalsGuast";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_BADBOY = 1;
    private static final int NUM_COLUMN_GOALSHOME = 2;
    private static final int NUM_COLUMN_GOALSGUAST = 3;

    private SQLiteDatabase DataBase;

    public DBBadBoys(Context context) {
        OpenHelp mOpenHelper = new OpenHelp(context);
        DataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String badboy, int goalhoouse, int goalguest) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BADBOY, badboy);
        cv.put(COLUMN_GOALSHOME, goalhoouse);
        cv.put(COLUMN_GOALSHOME, goalguest);
        return DataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(BadBoys md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_BADBOY, md.getBadboy());
        cv.put(COLUMN_GOALSHOME, md.getGoalshouse());
        cv.put(COLUMN_GOALSGUAST, md.getGoalsguest());

        return DataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }

    public void delete(long id) {
        DataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public BadBoys select(long id) {
        Cursor mCursor = DataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String Badboy = mCursor.getString(NUM_COLUMN_BADBOY);
        int Goalshome = mCursor.getInt(NUM_COLUMN_GOALSHOME);
        int Goalsguest = mCursor.getInt(NUM_COLUMN_GOALSGUAST);
        return new BadBoys(id, Badboy, Goalshome, Goalsguest);
    }

    public ArrayList<BadBoys> selectAll() {
        Cursor mCursor = DataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<BadBoys> arr = new ArrayList<BadBoys>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String Name = mCursor.getString(NUM_COLUMN_BADBOY);
                int GoalsHome = mCursor.getInt(NUM_COLUMN_GOALSHOME);
                int Goalsguest = mCursor.getInt(NUM_COLUMN_GOALSGUAST);
                arr.add(new BadBoys(id, Name, GoalsHome, Goalsguest));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelp extends SQLiteOpenHelper {

        OpenHelp(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BADBOY+ " TEXT, " +
                    COLUMN_GOALSHOME + " INT, " +
                    COLUMN_GOALSGUAST + " INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
