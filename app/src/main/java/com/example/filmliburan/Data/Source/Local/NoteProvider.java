package com.example.filmliburan.Data.Source.Local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.AUTHORITY;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.CONTENT_URI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.TABLE_MOVIE;

public class NoteProvider extends ContentProvider {
    public NoteProvider() {
    }

    private static final int MOVIE=1;
    private static final int MOVIE_ID=2;
    private FavoriteHelper favoriteHelper;

    private static UriMatcher sUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE+ "/#", MOVIE_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted= favoriteHelper.deleteById(uri.getLastPathSegment());
                break;
                default:
                    deleted=0;
                    break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added= favoriteHelper.insert(values);
                break;
                default:
                    added=0;
                    break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
        return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public boolean onCreate() {

        favoriteHelper= FavoriteHelper.getInstace(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor ;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor= favoriteHelper.queryAll();
                break;
            case MOVIE_ID:
                cursor= favoriteHelper.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor=null;
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                updated= favoriteHelper.update(uri.getLastPathSegment(), values);
                break;
                default:
                    updated=0;
                    break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI,null);
        return updated;
    }
}
