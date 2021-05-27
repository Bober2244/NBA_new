package com.example.db_for_st;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBTeams {

    private static final String DATABASE_NAME = "Teams.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Team";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEAM = "Team";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_TEAM = 1;

    private SQLiteDatabase mDataBase;

    public DBTeams(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String team) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TEAM, team);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(Teams md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TEAM, md.getTeam());
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public Teams select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String Team = mCursor.getString(NUM_COLUMN_TEAM);
        return new Teams(id, Team);
    }

    public ArrayList<Teams> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<Teams> arr = new ArrayList<Teams>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String Team = mCursor.getString(NUM_COLUMN_TEAM);
                arr.add(new Teams(id, Team));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEAM+ " TEXT); ";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}