package com.example.filmliburan.Preview.Main.Fragment.Movies;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Source.Remote.Movies.MovieViewModel;
import com.example.filmliburan.Preview.Detail.DetailMovieActivity;
import com.example.filmliburan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MovieViewModel movieViewModel;
    View view;
    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_movies, container, false);
        progressBar= view.findViewById(R.id.progressBar);
        RecyclerView recyclerView= view.findViewById(R.id.recycler_movie);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter= new MovieAdapter();
        recyclerView.setAdapter(adapter);
        movieViewModel = ViewModelProviders.of(getActivity()).get(MovieViewModel.class);
//        if(adapter!=null){
//            getProgress(false);
//        }

        // mengeset nilai movie
        movieViewModel.setMovie();
        getProgress(true);
        // mengambil nilai movie lalu di set ke adapter
        movieViewModel.getMovie().observe(getActivity(), getMovieList);
        IntentToDetail();
        return view;
    }

    public Observer<ArrayList<Movie>> getMovieList= new Observer<ArrayList<Movie>>(){
        @Override
        public void onChanged(ArrayList<Movie> movieslist){
            if(movieslist!=null){
                adapter.setItems(movieslist);
                if(adapter!=null) {
                    getProgress(false);
                }
            }
        }
    };

    public void getProgress(Boolean progress){
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void IntentToDetail(){
        adapter.setOnItemCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(Movie movie) {
                String status="movie";
                Intent intent= new Intent(getActivity(), DetailMovieActivity.class);
                intent.putExtra("film",movie);
                startActivity(intent);
            }
        });
    }

}
