package com.example.filmliburan.Data.Source.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.DESKRIPSI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.GAMBARPOPULE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.GENRE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.JUDUL;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.POPULER;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.RELEASEDATE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TABLE_MOVIE;
//import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TABLE_NAME;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.TABLE_TVSHOW;

public class FavoriteHelper {
    private static final String DATABASE_MOVIE= TABLE_MOVIE;
    private static final String DATABASE_TVSHOW= TABLE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;
    private FavoriteHelper (Context context){
        databaseHelper= new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstace(Context context){
        if(INSTANCE == null){
            synchronized (SQLiteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE= new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException{
        database= databaseHelper.getWritableDatabase();
    }
    public void close(){
        databaseHelper.close();
        if(database.isOpen()){
            database.close();
        }
    }

    public ArrayList<Movie> getAllFavoriteMovie(){
        ArrayList<Movie> arrayList= new ArrayList<>();
        Cursor cursor= database.query(DATABASE_MOVIE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC ",
                null);
        cursor.moveToFirst();
        Movie movie;
        if(cursor.getCount()>0){
            do {
                movie= new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                movie.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                movie.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBARPOPULE)));
                movie.setPopuler(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULER)));
                movie.setOriginalLanguege(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALLANGUAGE)));
                movie.setGenre(cursor.getInt(cursor.getColumnIndexOrThrow(GENRE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteMovie(Movie movie){
        ContentValues values= new ContentValues();
        values.put(_ID, movie.getId());
        values.put(JUDUL, movie.getJudul());
        values.put(DESKRIPSI, movie.getDeskripsi());
        values.put(GAMBARPOPULE, movie.getGambar());
        values.put(POPULER, movie.getPopuler());
        values.put(ORIGINALLANGUAGE, movie.getOriginalLanguege());
        values.put(GENRE, movie.getGenre());
        values.put(RELEASEDATE, movie.getReleaseDate());
        return database.insert(DATABASE_MOVIE, null, values);

    }
    public int updateFavoriteMovie(Movie movie){
        ContentValues values= new ContentValues();
        values.put(JUDUL, movie.getJudul());
        values.put(DESKRIPSI, movie.getDeskripsi());
        values.put(GAMBARPOPULE, movie.getGambar());
        values.put(POPULER, movie.getPopuler());
        values.put(ORIGINALLANGUAGE, movie.getOriginalLanguege());
        values.put(GENRE, movie.getGenre());
        values.put(RELEASEDATE, movie.getReleaseDate());
        return database.update(DATABASE_MOVIE, values, _ID + " =' "+movie.getId()+"'", null);

    }
    public int deleteFavoriteMovie(int id){
        return database.delete(TABLE_MOVIE, _ID + " =' "+id+"'", null);
    }

    public Boolean getAllByMovieId(int id){
        SQLiteDatabase db= databaseHelper.getReadableDatabase();

        boolean Favorite= true;
        Cursor cursor;
        String sql= " SELECT * FROM "+ DATABASE_MOVIE + " WHERE " + _ID + " =' " + id +"'";
        cursor= db.rawQuery(sql, null);
        Favorite= cursor.getCount() >0;

        cursor.close();
        return Favorite;
    }


    public ArrayList<TvShow> getAllFavoriteTvShow(){
        ArrayList<TvShow> arrayList= new ArrayList<>();
        Cursor cursor= database.query(DATABASE_TVSHOW, null,
                null,
                null,
                null,
                null,
                _ID + " ASC ",
                null);
        cursor.moveToFirst();
        TvShow movie;
        if(cursor.getCount()>0){
            do {
                movie= new TvShow();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                movie.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                movie.setGambar(cursor.getString(cursor.getColumnIndexOrThrow(GAMBARPOPULE)));
                movie.setPopuler(cursor.getDouble(cursor.getColumnIndexOrThrow(POPULER)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndexOrThrow(ORIGINALLANGUAGE)));
                movie.setGenre(cursor.getInt(cursor.getColumnIndexOrThrow(GENRE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASEDATE)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while(!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertFavoriteTvshow(TvShow movie){
        ContentValues values= new ContentValues();
        values.put(_ID, movie.getId());
        values.put(JUDUL, movie.getJudul());
        values.put(DESKRIPSI, movie.getDeskripsi());
        values.put(GAMBARPOPULE, movie.getGambar());
        values.put(POPULER, movie.getPopuler());
        values.put(ORIGINALLANGUAGE, movie.getOriginalLanguage());
        values.put(GENRE, movie.getGenre());
        values.put(RELEASEDATE, movie.getReleaseDate());
        return database.insert(DATABASE_TVSHOW, null, values);

    }
    public int updateFavoriteTvshow(TvShow movie){
        ContentValues values= new ContentValues();
        values.put(JUDUL, movie.getJudul());
        values.put(DESKRIPSI, movie.getDeskripsi());
        values.put(GAMBARPOPULE, movie.getGambar());
        values.put(POPULER, movie.getPopuler());
        values.put(ORIGINALLANGUAGE, movie.getOriginalLanguage());
        values.put(GENRE, movie.getGenre());
        values.put(RELEASEDATE, movie.getReleaseDate());
        return database.update(DATABASE_TVSHOW, values, _ID + " =' "+movie.getId()+"'", null);

    }
    public int deleteFavoriteTvshow(int id){
        return database.delete(TABLE_TVSHOW, _ID + " =' "+id+"'", null);
    }

    public Boolean getAllTvshowById(int id){
        SQLiteDatabase db= databaseHelper.getReadableDatabase();

        boolean Favorite= true;
        Cursor cursor;
        String sql= " SELECT * FROM "+ DATABASE_TVSHOW + " WHERE " + _ID + " =' " + id +"'";
        cursor= db.rawQuery(sql, null);
        Favorite= cursor.getCount() >0;

        cursor.close();
        return Favorite;
    }

}
