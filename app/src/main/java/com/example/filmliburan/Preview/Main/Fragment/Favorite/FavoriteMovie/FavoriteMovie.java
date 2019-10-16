package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie;


import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Source.Local.DatabaseContract;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Data.Source.Local.LoadfavoriteCallback;
import com.example.filmliburan.Data.Source.Local.MappingHelper;
import com.example.filmliburan.Preview.Detail.DetailMovieActivity;
import com.example.filmliburan.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.CONTENT_URI;


public class FavoriteMovie extends Fragment  {

    private RecyclerView recyclerView;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private static final String EXTRA_STATE= "EXTRA_STATE";
    private ProgressBar progressBar;
    TextView textKosong;
    ArrayList<Movie> movieList;
    View view;


    public FavoriteMovie() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        recyclerView= view.findViewById(R.id.recycler_favorite_movie);
        progressBar= view.findViewById(R.id.progressMovie);
        textKosong= view.findViewById(R.id.text_kosong);
        movieList= new ArrayList<>();
        tampilPengguna();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        favoriteMovieAdapter= new FavoriteMovieAdapter(movieList);
        recyclerView.setAdapter(favoriteMovieAdapter);
        if(movieList.size()==0){
            textKosong.setVisibility(View.VISIBLE);
        }
        else{
            textKosong.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentToDetail();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void tampilPengguna(){
        String [] projection= {
                DatabaseContract.FavoriteColumn._ID,
                DatabaseContract.FavoriteColumn.JUDUL,
                DatabaseContract.FavoriteColumn.DESKRIPSI,
                DatabaseContract.FavoriteColumn.GAMBARPOPULE,
                DatabaseContract.FavoriteColumn.POPULER,
                DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE,
                DatabaseContract.FavoriteColumn.GENRE,
                DatabaseContract.FavoriteColumn.RELEASEDATE,
        };
        Cursor movieCursor= getContext().getContentResolver().query(
                CONTENT_URI,
                projection,
                null,
                null,
                null,
                null
        );
        if(movieCursor!=null){
            movieCursor.moveToFirst();
        }
        movieList= new ArrayList<>();
        for (int i = 0; i <movieCursor.getCount() ; i++) {
            int id= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn._ID));
            String judul= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.JUDUL));
            String deskripsi= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.DESKRIPSI));
            String gambarpopuler= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GAMBARPOPULE));
            double populer= movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.POPULER));
            String originallanguage= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE));
            int genre= movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.GENRE));
            String releasedate= movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumn.RELEASEDATE));
            movieList.add(new Movie(id,judul,deskripsi, gambarpopuler, populer, originallanguage, genre, releasedate));
            movieCursor.moveToNext();
        }
        movieCursor.close();
    }


    private void IntentToDetail() {
        favoriteMovieAdapter.setOnItemCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(Movie movie) {
                Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("film", movie);
                Uri currentMovie= ContentUris.withAppendedId(CONTENT_URI, movie.getId());
                intent.setData(currentMovie);
                startActivity(intent);
            }
        });
    }


}
