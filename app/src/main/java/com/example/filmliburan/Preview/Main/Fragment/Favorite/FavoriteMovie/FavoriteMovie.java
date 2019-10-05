package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Data.Source.Local.LoadfavoriteCallback;
import com.example.filmliburan.Preview.Detail.DetailMovieActivity;
import com.example.filmliburan.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class FavoriteMovie extends Fragment implements LoadfavoriteCallback {

    private RecyclerView recyclerView;
    private FavoriteMovieAdapter favoriteMovieAdapter;
//    private static HandlerThread handlerThread;
    private FavoriteHelper favoriteHelper;
//    private DataObserver observer;
    private static final String EXTRA_STATE= "EXTRA_STATE";
    private ProgressBar progressBar;
    TextView textKosong;
    View view;


    public FavoriteMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        recyclerView= view.findViewById(R.id.recycler_favorite_movie);
        progressBar= view.findViewById(R.id.progressMovie);
        textKosong= view.findViewById(R.id.text_kosong);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        favoriteMovieAdapter= new FavoriteMovieAdapter(getActivity());
        favoriteMovieAdapter.getListMovie();
        recyclerView.setAdapter(favoriteMovieAdapter);
        favoriteHelper= FavoriteHelper.getInstace(getContext().getApplicationContext());
        favoriteHelper.open();
        if(savedInstanceState==null){
            new LoadfavoriteAsnyc(favoriteHelper,this).execute();
        }
        else{
            ArrayList<Movie> list= savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if(list!=null) {
                favoriteMovieAdapter.setListMovie(list);
            }
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentToDetail();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoriteHelper.close();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(outState!=null) {
            outState.putParcelableArrayList(EXTRA_STATE, favoriteMovieAdapter.getListMovie());
        }
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        Log.d("FavoriteMovie", "preExecute: ");
    }

    @Override
    public void postExecute(ArrayList<Movie> movie) {
        progressBar.setVisibility(View.INVISIBLE);
        favoriteMovieAdapter.setListMovie(movie);
        if(movie.size()==0){
            textKosong.setVisibility(View.VISIBLE);
        }
        else{
            textKosong.setVisibility(View.GONE);
        }
        Log.d("FavoriteMovie", "postExecute: ");
    }

    private static class LoadfavoriteAsnyc extends AsyncTask<Void, Void, ArrayList<Movie>>{

        private final WeakReference<LoadfavoriteCallback> weakCallback;
        private final WeakReference<FavoriteHelper>favoriteHelper;
        private LoadfavoriteAsnyc(FavoriteHelper favoriteHe, LoadfavoriteCallback callback){
            favoriteHelper= new WeakReference<>(favoriteHe);
            weakCallback= new WeakReference<>(callback);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return favoriteHelper.get().getAllFavoriteMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movie) {
            super.onPostExecute(movie);
            weakCallback.get().postExecute(movie);
        }
    }

    private void IntentToDetail(){
        favoriteMovieAdapter.setOnItemCallback(new FavoriteMovieAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(Movie movie) {
                Intent intent= new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("film", movie);
                startActivity(intent);
            }
        });
    }

}
