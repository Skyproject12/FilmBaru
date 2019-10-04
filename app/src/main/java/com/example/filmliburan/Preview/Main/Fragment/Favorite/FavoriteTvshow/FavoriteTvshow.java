package com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteTvshow;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Data.Source.Local.LoadTvShowCallback;
import com.example.filmliburan.Data.Source.Local.LoadfavoriteCallback;
import com.example.filmliburan.Preview.Detail.DetailTvShowActivity;
import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteMovie.FavoriteMovieAdapter;
import com.example.filmliburan.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvshow extends Fragment implements LoadTvShowCallback {

    View view;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FavoriteHelper favoriteHelper;
    FavoriteTvshowAdapter favoriteTvmovieAdapter;
    private static final String EXTRA_STATE= "EXTRA_STATE";


    public FavoriteTvshow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);
        recyclerView= view.findViewById(R.id.recycler_favorite_tvshow);
        progressBar= view.findViewById(R.id.progressTvshow);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        favoriteHelper= FavoriteHelper.getInstace(getContext().getApplicationContext());
        favoriteHelper.open();
        favoriteTvmovieAdapter= new FavoriteTvshowAdapter(getActivity());
        recyclerView.setAdapter(favoriteTvmovieAdapter);
        if(savedInstanceState==null){
            new LoadfavoriteAsnyc(favoriteHelper,this).execute();
        }
        else{
            ArrayList<TvShow> list= savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            favoriteTvmovieAdapter.setListTvShow(list);
        }
        IntentToDetail();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteTvmovieAdapter.getListMovie());
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<TvShow> tvShows) {
        progressBar.setVisibility(View.INVISIBLE);
        favoriteTvmovieAdapter.setListTvShow(tvShows);
    }

    private static class LoadfavoriteAsnyc extends AsyncTask<Void, Void, ArrayList<TvShow>> {

        private final WeakReference<LoadTvShowCallback> weakCallback;
        private final WeakReference<FavoriteHelper>favoriteHelper;
        private LoadfavoriteAsnyc(FavoriteHelper favoriteHe, LoadTvShowCallback callback){
            favoriteHelper= new WeakReference<>(favoriteHe);
            weakCallback= new WeakReference<>(callback);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return favoriteHelper.get().getAllFavoriteTvShow();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            weakCallback.get().postExecute(tvShows);
        }
    }
    private void IntentToDetail(){
        favoriteTvmovieAdapter.setOnItemCallback(new FavoriteTvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(TvShow tvShow) {

                Intent intent= new Intent(getActivity(), DetailTvShowActivity.class);
                intent.putExtra("intent", tvShow);
                startActivity(intent);
            }
        });
    }

}
