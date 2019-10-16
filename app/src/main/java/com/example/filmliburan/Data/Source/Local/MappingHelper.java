package com.example.filmliburan.Data.Source.Local;

import android.database.Cursor;

import com.example.filmliburan.Data.Model.Movie;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Movie> mapCursorToArrayList(Cursor movieCursor){
        ArrayList<Movie> movieList= new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn._ID));
            String judul= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.JUDUL));
            String deskripsi= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.DESKRIPSI));
            String gambarpopuler= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GAMBARPOPULE));
            double populer= movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.POPULER));
            String originallanguage= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE));
            int genre= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GENRE));
            String releasedate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.RELEASEDATE));
            movieList.add(new Movie(id,judul,deskripsi, gambarpopuler, populer, originallanguage, genre, releasedate));

        }
        return movieList;
    }

    public static Movie mapCursorToObject(Cursor movieCursor){
        movieCursor.moveToFirst();
        int id= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn._ID));
        String judul= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.JUDUL));
        String deskripsi= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.DESKRIPSI));
        String gambarpopuler= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GAMBARPOPULE));
        double populer= movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.POPULER));
        String originallanguage= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE));
        int genre= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GENRE));
        String releasedate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.RELEASEDATE));

        return new Movie(id, judul, deskripsi, gambarpopuler, populer, originallanguage, genre, releasedate);

    }
}
