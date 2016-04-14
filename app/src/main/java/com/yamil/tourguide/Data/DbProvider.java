package com.yamil.tourguide.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by yamil on 4/12/16.
 */
public class DbProvider extends DbHelper{
    Cursor c;
    public DbProvider(Context context) {
        super(context);
    }
    public long insert (String tabla, ContentValues contentValues){
        long id;
        final SQLiteDatabase db = getWritableDatabase();

        try {
            id = db.insertOrThrow(tabla, null, contentValues);
        } catch (SQLException e) {
            id = -1;
            Log.e("ERROR_DB", e.getMessage());
        }
        return id;
    }

    public Cursor query (String tabla, String[] columns, String selection , String[] selectionArgs){
        final SQLiteDatabase db = getReadableDatabase();
        c = db.query(tabla, columns, selection, selectionArgs, null, null, null);
        return c;
    }

    public Cursor query (String tabla, String[] columns, String selection , String[] selectionArgs, String columnOrder){
        final SQLiteDatabase db = getReadableDatabase();
        c = db.query(tabla, columns, selection, selectionArgs, null, null, columnOrder + " ASC");
        return c;
    }
    public Cursor rawQuery (String query){
        final SQLiteDatabase db = getReadableDatabase();
        c = db.rawQuery(query, null);
        return c;
    }

    public long delete (String tabla, String columns, String[] selectionArgs){

        long rowsDeleted;
        final SQLiteDatabase db = getWritableDatabase();
        rowsDeleted = db.delete(tabla, columns, selectionArgs);
        return rowsDeleted;
    }

    public void deleteAllTable(String tableName){
        final SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ tableName);
    }

    public long update (String tabla, ContentValues values, String selection, String[] selectionArgs){

        long rowsUpdated;
        final SQLiteDatabase db = getWritableDatabase();
        rowsUpdated = db.update(tabla, values, selection, selectionArgs);
        return rowsUpdated;
    }
}
