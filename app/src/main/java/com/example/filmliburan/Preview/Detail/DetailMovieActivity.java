package com.example.filmliburan.Preview.Detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmliburan.Data.Model.Movie;
import com.example.filmliburan.Data.Model.TvShow;
import com.example.filmliburan.Data.Source.Local.FavoriteHelper;
import com.example.filmliburan.Preview.Main.MainActivity;
import com.example.filmliburan.R;
import com.squareup.picasso.Picasso;

import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.DESKRIPSI;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.GAMBARPOPULE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.GENRE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.JUDUL;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.ORIGINALLANGUAGE;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.POPULER;
import static com.example.filmliburan.Data.Source.Local.DatabaseContract.FavoriteColumn.RELEASEDATE;

public class DetailMovieActivity extends AppCompatActivity {

    TextView textJudul;
    TextView textDeskripsi;
    ImageView gambarFilm;
    Movie movie;
    TextView populer;
    TextView originalLeangue;
    TextView genre;
    TextView releaseDate;
    Menu menu;
    FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        favoriteHelper= FavoriteHelper.getInstace(this.getApplicationContext());
        favoriteHelper.open();
        textJudul= findViewById(R.id.text_judul);
        textDeskripsi= findViewById(R.id.text_deskripsi);
        gambarFilm= findViewById(R.id.gambar_film);
        movie = getIntent().getParcelableExtra("film");
        textJudul.setText(movie.getJudul());
        textDeskripsi.setText(movie.getDeskripsi());
        Picasso.get().load(movie.getGambar()).into(gambarFilm);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        populer= findViewById(R.id.text_populer);
        originalLeangue= findViewById(R.id.text_originalLanguage);
        genre= findViewById(R.id.text_genre);
        releaseDate= findViewById(R.id.text_releasedate);
        populer.setText(String.valueOf(movie.getPopuler()));
        originalLeangue.setText(movie.getOriginalLanguege());
        genre.setText(String.valueOf(movie.getGenre()));
        releaseDate.setText(movie.getReleaseDate());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        if(favoriteHelper.getAllByMovieId(movie.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
        }else if(!favoriteHelper.getAllByMovieId(movie.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        switch (item.getItemId()){
            case R.id.favorite_detail:
                if(favoriteHelper.getAllByMovieId(movie.getId())){
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
                    favoriteHelper.deleteFavoriteMovie(movie.getId());
                }else if(!favoriteHelper.getAllByMovieId(movie.getId())){
                    Toast.makeText(this, "masuk", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_star));
                    favoriteHelper.insertFavoriteMovie(movie);
                }
                break;
            case R.id.home:
                Intent intent= new  Intent(DetailMovieActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
