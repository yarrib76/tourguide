package com.yamil.tourguide.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yamil on 4/12/16.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tourguide.sqlite";
    public static final int DATABASE_VERSION = 6;

    private static final String CREATE_TABLE_BLUETOOTH = "CREATE TABLE IF NOT EXISTS " + Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS +
            "(" + Contract.BluetoothActivos.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , "
            + Contract.BluetoothActivos.COL_NOMBRE + " TEXT, "
            + Contract.BluetoothActivos.COL_MACADDRESS + " TEXT, "
            + Contract.BluetoothActivos.COL_ESTADO + " INT)";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BLUETOOTH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.BluetoothActivos.TABLA_BLUETOOTHACTIVOS);
        onCreate(db);
    }
}
