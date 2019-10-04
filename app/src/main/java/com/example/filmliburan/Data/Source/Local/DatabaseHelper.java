package com.example.filmliburan.Data.Source.Local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="filmfavorite.db";
    private static final int DATABASE_VERSION=1;
    private static String SQL_CREATE_TABLE_MOVIE= String.format(" CREATE TABLE %s "
            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " %s TEXT NOT NULL, " +
            " %s TEXT NOT NULL, " +
            " %s TEXT NOT NULL, " +
            " %s INTEGER, " +
            " %s TEXT NOT NULL, " +
            " %s INTEGER, " +
            " %s TEXT NOT NULL ) " ,
            DatabaseContract.TABLE_MOVIE,
            DatabaseContract.FavoriteColumn._ID,
            DatabaseContract.FavoriteColumn.JUDUL,
            DatabaseContract.FavoriteColumn.DESKRIPSI,
            DatabaseContract.FavoriteColumn.GAMBARPOPULE,
            DatabaseContract.FavoriteColumn.POPULER,
            DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE,
            DatabaseContract.FavoriteColumn.GENRE,
            DatabaseContract.FavoriteColumn.RELEASEDATE);

    private static String SQL_CREATE_TABLE_TVSHOW= String.format(" CREATE TABLE %s "
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s INTEGER, " +
                    " %s TEXT NOT NULL, " +
                    " %s TEXT NOT NULL, " +
                    " %s INTEGER, " +
                    " %s TEXT NOT NULL ) " ,
            DatabaseContract.TABLE_TVSHOW,
            DatabaseContract.FavoriteColumn._ID,
            DatabaseContract.FavoriteColumn.JUDUL,
            DatabaseContract.FavoriteColumn.GAMBARPOPULE,
            DatabaseContract.FavoriteColumn.POPULER,
            DatabaseContract.FavoriteColumn.DESKRIPSI,
            DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE,
            DatabaseContract.FavoriteColumn.GENRE,
            DatabaseContract.FavoriteColumn.RELEASEDATE);


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS "+DatabaseContract.TABLE_MOVIE);
        db.execSQL(" DROP TABLE IF EXISTS "+DatabaseContract.TABLE_TVSHOW);
        onCreate(db);
    }
}
