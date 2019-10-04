package com.example.filmliburan.Preview.Main.Fragment.Movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public ArrayList<Movie> list= new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;
    public void setItems(ArrayList<Movie> item){
        list.clear();
        list.addAll(item);
        notifyDataSetChanged();
    }
    public void setOnItemCallback(OnItemClickCallback onItemClickCallback){
        this.onItemClickCallback= onItemClickCallback;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItmCliked(list.get(viewHolder.getAdapterPosition()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        TextView textDesskripsi;
        ImageView gambarMovie;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textJudul= itemView.findViewById(R.id.text_judul);
            textDesskripsi= itemView.findViewById(R.id.text_deskripsi);
            gambarMovie= itemView.findViewById(R.id.gambar_movie);
        }
        public void bind(Movie movie){
            textJudul.setText(movie.getJudul());
            textDesskripsi.setText(movie.getDeskripsi());
            Picasso.get().load(movie.getGambar()).into(gambarMovie);
        }
    }
    public interface OnItemClickCallback{
        void onItmCliked(Movie movie);
    }
}
