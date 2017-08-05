package com.lzm.knittinghelp2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lzm.knittinghelp2.repositories.PartDbHelper;
import com.lzm.knittinghelp2.repositories.PatternDbHelper;
import com.lzm.knittinghelp2.repositories.SectionDbHelper;
import com.lzm.knittinghelp2.repositories.StepDbHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "knitCrochetHelp.db";

    protected static final String TABLE_PATTERN = "patterns";
    protected static final String TABLE_SECTION = "sections";
    protected static final String TABLE_PART = "parts";
    protected static final String TABLE_STEP = "steps";

    protected static final String ID = "id";
    protected static final String CREATION_DATE = "creation_date";
    protected static final String UPDATE_DATE = "update_date";
    private static final String[] KEYS_COMMON = {ID, CREATION_DATE, UPDATE_DATE};

    protected Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, TABLE_PATTERN, KEYS_COMMON, PatternDbHelper.KEYS);
        createTable(db, TABLE_SECTION, KEYS_COMMON, SectionDbHelper.KEYS);
        createTable(db, TABLE_PART, KEYS_COMMON, PartDbHelper.KEYS);
        createTable(db, TABLE_STEP, KEYS_COMMON, StepDbHelper.KEYS);

        DbInserter dbInserter = new DbInserter(db);
        dbInserter.insertStarterPatterns();

        updateWhenNew(db);
    }

    private void updateWhenNew(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void upgradeTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        dropTable(db, tableName);
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    private void createTable(SQLiteDatabase db, String tableName, String[] common, String[] columnNames) {
        db.execSQL(createTableSql(tableName, common, columnNames));
    }

    private void dropTable(SQLiteDatabase db, String tableName) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
    }

    private static String createTableSql(String tableName, String[] common, String[] columnNames) {
        String sql = "CREATE TABLE " + tableName + " (" +
                common[0] + " INTEGER PRIMARY KEY," + //id
                common[1] + " DATETIME"; //date
        for (String c : columnNames) {
            if (c.startsWith("lat") || c.startsWith("long")) {
                sql += ", " + c + " REAL";
            } else {
                sql += ", " + c + " TEXT";
            }
        }
        sql += ")";
        return sql;
    }
}
