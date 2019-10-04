package com.example.filmliburan.Preview.Main.Fragment.TvShow;


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

import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Remote.TvShow.TvShowViewModel;
import com.example.filmliburan.Preview.Detail.DetailTvShowActivity;
import com.example.filmliburan.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    TvshowAdapter adapter;
    View view;
    ProgressBar progressBar;
    TvShowViewModel tvShowViewModel;
    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        adapter= new TvshowAdapter();
        RecyclerView recyclerView= view.findViewById(R.id.recycler_tvshow);
        progressBar= view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        tvShowViewModel= ViewModelProviders.of(getActivity()).get(TvShowViewModel.class);

        tvShowViewModel.setTvshow();
        getProgress(true);
        tvShowViewModel.getTvshow().observe(getActivity(), getTvShowListr);
        IntentToDetailActivity();
        return view;
    }

    public Observer<ArrayList<TvShow>> getTvShowListr= new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(ArrayList<TvShow> tvShows) {
            if(tvShows!=null) {
                adapter.setItem(tvShows);
                if (adapter != null) {
                    getProgress(false);
                }
            }
        }
    };

    public void getProgress(Boolean progress){
        if(progress){
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void IntentToDetailActivity(){
        adapter.setOnItemClickCallback(new TvshowAdapter.OnItemClickCallback() {
            @Override
            public void onItmCliked(TvShow tvShow) {
                String status="tvshow";
                Intent intent= new Intent(getActivity(), DetailTvShowActivity.class);
                intent.putExtra("intent",tvShow);
                intent.putExtra("status", status);
                startActivity(intent);
            }
        });
    }
}
