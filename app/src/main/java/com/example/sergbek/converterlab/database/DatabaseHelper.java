package com.example.sergbek.converterlab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "currency_database.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_ORGANIZATION = "organization";
    public static final String TABLE_REGIONS = "regions";
    public static final String TABLE_CITIES = "cities";
    public static final String TABLE_ORGTYPES = "orgtypes";
    public static final String TABLE_CURRENCIES = "currencies";
    public static final String TABLE_COURSE = "course";
    public static final String TABLE_DATE = "date";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORGTYPE = "orgType";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OLD_ID = "oldId";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY_ID = "cityId";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_REGION_ID = "regionId";

    public static final String COLUMN_VALUES_ORGTYPE = "val_orgtype";
    public static final String COLUMN_VALUES_REGIONS = "val_regions";
    public static final String COLUMN_VALUES_CITIES = "val_cities";
    public static final String COLUMN_VALUES_CURRENCIES = "val_currencies";

    public static final String COLUMN_ORGANIZATION = "organization";
    public static final String COLUMN_ABBR = "abbr";
    public static final String COLUMN_FULL_TITLE = "full_tittle";
    public static final String COLUMN_ASK = "ask";
    public static final String COLUMN_BID = "bid";
    public static final String COLUMN_CHANGE_ASK = "change_ask";
    public static final String COLUMN_CHANGE_BID = "change_bid";

    public static final String COLUMN_DATE = "date";

    private static final String CREATE_ORGANIZATION_TABLE = "CREATE TABLE " + TABLE_ORGANIZATION + "("
            + COLUMN_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE," + COLUMN_ORGTYPE
            + " TEXT," + COLUMN_PHONE
            + " TEXT," + COLUMN_TITLE + " TEXT," + COLUMN_OLD_ID
            + " TEXT," + COLUMN_ADDRESS + " TEXT," + COLUMN_CITY_ID + " TEXT,"
            + COLUMN_LINK + " TEXT," + COLUMN_REGION_ID + " TEXT,"
            + "FOREIGN KEY (" + COLUMN_REGION_ID + ")REFERENCES " + TABLE_REGIONS + "(" + COLUMN_ID + ")"
            + "FOREIGN KEY (" + COLUMN_CITY_ID + ")REFERENCES " + TABLE_CITIES + "(" + COLUMN_ID + ")"
            + "FOREIGN KEY (" + COLUMN_ORGTYPE + ")REFERENCES " + TABLE_ORGTYPES + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_REGIONS_TABLE = "CREATE TABLE " + TABLE_REGIONS
            + "(" + COLUMN_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE," + COLUMN_VALUES_REGIONS + " TEXT" + ")";

    private static final String CREATE_CITIES_TABLE = "CREATE TABLE " + TABLE_CITIES
            + "(" + COLUMN_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE," + COLUMN_VALUES_CITIES + " TEXT" + ")";

    private static final String CREATE_ORGTYPES_TABLE = "CREATE TABLE " + TABLE_ORGTYPES
            + "(" + COLUMN_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE," + COLUMN_VALUES_ORGTYPE + " TEXT" + ")";

    private static final String CREATE_CURRENCIES_TABLE = "CREATE TABLE " + TABLE_CURRENCIES
            + "(" + COLUMN_ID + " TEXT PRIMARY KEY ON CONFLICT REPLACE," + COLUMN_VALUES_CURRENCIES + " TEXT" + ")";

    private static final String CREATE_CURS_TABLE = "CREATE TABLE " + TABLE_COURSE + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement," + COLUMN_ORGANIZATION
            + " TEXT NOT NULL," + COLUMN_ABBR + " TEXT," + COLUMN_FULL_TITLE + " TEXT," + COLUMN_ASK
            + " TEXT," + COLUMN_BID + " TEXT," + COLUMN_CHANGE_ASK + " INTEGER," + COLUMN_CHANGE_BID + " INTEGER,"
            + "FOREIGN KEY (" + COLUMN_ABBR + ")REFERENCES " + TABLE_CURRENCIES + "(" + COLUMN_ID + ")"
            + "FOREIGN KEY (" + COLUMN_FULL_TITLE + ")REFERENCES " + TABLE_CURRENCIES + "(" + COLUMN_VALUES_CURRENCIES + ")"
            + "FOREIGN KEY (" + COLUMN_ORGANIZATION + ")REFERENCES " + TABLE_ORGANIZATION + "(" + COLUMN_ID + ")"
            + ")";

    private static final String CREATE_DATE_TABLE = "CREATE TABLE " + TABLE_DATE
            + "(" + COLUMN_ID + " INTEGER PRIMARY KEY autoincrement," + COLUMN_DATE + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ORGANIZATION_TABLE);

        db.execSQL(CREATE_REGIONS_TABLE);

        db.execSQL(CREATE_CITIES_TABLE);

        db.execSQL(CREATE_ORGTYPES_TABLE);

        db.execSQL(CREATE_CURRENCIES_TABLE);

        db.execSQL(CREATE_CURS_TABLE);

        db.execSQL(CREATE_DATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGANIZATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGTYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);

        onCreate(db);
    }

}
