package com.trycatch.planet.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

/**
 * чтоб не париться
 * Cursor cursor = sqLiteDatabase.rawQuery("select * from mission INNER JOIN player ON
 * player.id = mission.foreign_key_player where mission.level < player.level", null);
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database_resource";

    private static final String TABLE_PLAYER = "player";
    private static final String COLUMN_PLAYER_ID = "player_id";
    private static final String COLUMN_PLAYER_NICKNAME = "nickname";
    private static final String COLUMN_PLAYER_LEVEL = "player_level";
    private static final String COLUMN_PLAYER_GOLD = "gold";
    private static final String COLUMN_PLAYER_MATTER = "matter";
    private static final String COLUMN_PLAYER_SECOND = "second";

    private static final String TABLE_MISSION = "mission";
    private static final String COLUMN_MISSION_ID = "mission_id";
    private static final String COLUMN_MISSION_MISSION_NAME = "mission_name";
    private static final String COLUMN_MISSION_MISSION_DESCRIPTION = "description";
    private static final String COLUMN_MISSION_MISSION_DONE = "done";
    private static final String COLUMN_MISSION_MISSION_LEVEL = "mission_level";
    private static final String COLUMN_MISSION_FOREIGN_KEY_PLAYER = "foreign_key_player";

    private int level;
    private int gold;
    private int matter;
    private float seconds;

    Thread missionThread;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPlayer = "create table " + TABLE_PLAYER + "(" + COLUMN_PLAYER_ID + " INTEGER primary key, " + COLUMN_PLAYER_NICKNAME + " TEXT, " +
                COLUMN_PLAYER_LEVEL + " INTEGER, " + COLUMN_PLAYER_GOLD + " INTEGER, " + COLUMN_PLAYER_MATTER + " INTEGER, " + COLUMN_PLAYER_SECOND + " FLOAT)";

        String createMission = "create table " + TABLE_MISSION + "(" + COLUMN_MISSION_ID + " INTEGER primary key, " + COLUMN_MISSION_MISSION_NAME + " TEXT, " +
                COLUMN_MISSION_MISSION_DESCRIPTION + " TEXT, " + COLUMN_MISSION_MISSION_DONE + " INTEGER, " + COLUMN_MISSION_MISSION_LEVEL + " INTEGER, " + COLUMN_MISSION_FOREIGN_KEY_PLAYER + " INTEGER, " +
                "foreign key(" + COLUMN_MISSION_FOREIGN_KEY_PLAYER + ") references " + TABLE_PLAYER + "(" + COLUMN_PLAYER_ID + "))";
        db.execSQL(createPlayer);
        db.execSQL(createMission);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_PLAYER);
        onCreate(db);
    }

    public boolean addPlayer(String nickname) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_NICKNAME, nickname);
        contentValues.put(COLUMN_PLAYER_LEVEL, 1);
        contentValues.put(COLUMN_PLAYER_GOLD, 500);
        contentValues.put(COLUMN_PLAYER_MATTER, 500);
        contentValues.put(COLUMN_PLAYER_SECOND, 1000);
        sqLiteDatabase.insert(TABLE_PLAYER, null, contentValues);
        return true;
    }

    public String getName() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_GOLD + " from " + TABLE_PLAYER, null);
        cursor.moveToFirst();
        String nickname = cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_GOLD));
        return nickname;
    }

    public boolean addMission(String missionName, String description, int missionLevel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MISSION_MISSION_NAME, missionName);
        contentValues.put(COLUMN_MISSION_MISSION_NAME, description);
        contentValues.put(COLUMN_MISSION_MISSION_DONE, 0);
        contentValues.put(COLUMN_MISSION_MISSION_LEVEL, missionLevel);
        contentValues.put(COLUMN_MISSION_FOREIGN_KEY_PLAYER, 1);
        sqLiteDatabase.insert(TABLE_MISSION, null, contentValues);
        return true;
    }

    public synchronized void setLevel(int level) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_LEVEL, level);
        sqLiteDatabase.update(TABLE_PLAYER, contentValues, COLUMN_PLAYER_ID + " = 1", null);
    }

    public synchronized int getLevel() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_LEVEL + " " +
                "from " + TABLE_PLAYER, null);
        cursor.moveToFirst();
        level = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_LEVEL)));
        return level;
    }

    public synchronized void setGold(int gold) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_GOLD, gold);
        sqLiteDatabase.update(TABLE_PLAYER, contentValues, COLUMN_PLAYER_ID + " = 1", null);
    }

    public synchronized int getGold() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_GOLD + " " +
                "from " + TABLE_PLAYER, null);
        cursor.moveToFirst();
        gold = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_GOLD)));
        return gold;
    }

    public synchronized void setMatter(int matter) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLAYER_MATTER, matter);
        sqLiteDatabase.update(TABLE_PLAYER, contentValues, COLUMN_PLAYER_ID + " = 1", null);
    }

    public synchronized int getMatter() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_MATTER + " " +
                "from " + TABLE_PLAYER, null);
        cursor.moveToFirst();
        matter = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_MATTER)));
        return matter;
    }

    public synchronized void setSecond(double second) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        double percentSeconds = Double.parseDouble(String.valueOf(getSecond())) / 100;
        double subtractSeconds = percentSeconds * second;
        contentValues.put(COLUMN_PLAYER_SECOND, getSecond() - subtractSeconds);
        sqLiteDatabase.update(TABLE_PLAYER, contentValues, COLUMN_PLAYER_ID + " = 1", null);
    }

    public synchronized double getSecond() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_SECOND + " " +
                "from " + TABLE_PLAYER, null);
        cursor.moveToFirst();
        seconds = Float.parseFloat((cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_SECOND))));
        return seconds;
    }

    public ArrayList getAllMissions() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_MISSION, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_MISSION_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getSuccessMissions() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_MISSION + " where " + COLUMN_MISSION_MISSION_DONE + " = 1", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_MISSION_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getAvailableMissions() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_MISSION + " where " + COLUMN_MISSION_MISSION_DONE + " = 0", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_MISSION_NAME)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public void checkMissions() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_MISSION_ID +
                " from " + TABLE_MISSION + " join " + TABLE_PLAYER + " on " + COLUMN_PLAYER_ID + " = " + COLUMN_MISSION_FOREIGN_KEY_PLAYER +
                " where " + COLUMN_MISSION_MISSION_LEVEL + " >= " + COLUMN_PLAYER_LEVEL + " and " +
                COLUMN_MISSION_MISSION_DONE + " = 0", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MISSION_ID)))) {
                case 1:
                    startMission(1000, 1);
                    break;
                case 2:
                    startMission(1500, 2);
                    break;
                default:
                    break;
            }
            cursor.moveToNext();
        }
    }

    public void startMission(final int gold, final int idMission) {//еще много работы
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        missionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (getGold() < gold) {
                    try {
                        Cursor cursor = sqLiteDatabase.rawQuery("select " + COLUMN_PLAYER_GOLD + " " +
                                "from " + TABLE_PLAYER, null);
                        cursor.moveToFirst();
                        if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PLAYER_GOLD))) >= gold) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(COLUMN_MISSION_MISSION_DONE, 1);
                            sqLiteDatabase.update(TABLE_MISSION, contentValues, COLUMN_MISSION_ID + " = " + idMission, null);
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_MISSION_MISSION_DONE, 1);
                sqLiteDatabase.update(TABLE_MISSION, contentValues, COLUMN_MISSION_ID + " = " + idMission, null);
            }
        });
        missionThread.start();
    }
}