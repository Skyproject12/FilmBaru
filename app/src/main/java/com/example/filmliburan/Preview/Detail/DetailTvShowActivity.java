package com.example.filmliburan.Preview.Detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class DetailTvShowActivity extends AppCompatActivity {

    TextView textJudul;
    TextView textPopuler;
    ImageView gambarPopuler;
    TvShow tvShow;
    TextView textDeskripsi;
    TextView textOriginalLanguage;
    TextView textGenre;
    TextView textReleaseDate;
    Menu menu;
    FavoriteHelper favoriteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_tv_show);
        favoriteHelper= FavoriteHelper.getInstace(getApplicationContext());
        favoriteHelper.open();
        textJudul= findViewById(R.id.text_judul);
        textPopuler= findViewById(R.id.text_populer);
        gambarPopuler= findViewById(R.id.gambar_populer);
        tvShow= getIntent().getParcelableExtra("intent");
        textJudul.setText(tvShow.getJudul());
        textPopuler.setText(String.valueOf(tvShow.getPopuler()));
        Picasso.get().load(tvShow.getGambar()).into(gambarPopuler);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        textDeskripsi= findViewById(R.id.text_deskripsi);
        textOriginalLanguage= findViewById(R.id.text_language);
        textGenre= findViewById(R.id.text_genre);
        textReleaseDate= findViewById(R.id.text_date);
        textGenre.setText(String.valueOf(tvShow.getGenre()));
        textDeskripsi.setText(tvShow.getDeskripsi());
        textOriginalLanguage.setText(tvShow.getOriginalLanguage());
        textReleaseDate.setText(tvShow.getReleaseDate());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        if(favoriteHelper.getAllTvshowById(tvShow.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_star));
        }else if(!favoriteHelper.getAllTvshowById(tvShow.getId())){
            menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuItem menuItem= menu.findItem(R.id.favorite_detail);
        switch (item.getItemId()){
            case R.id.favorite_detail:
                if(favoriteHelper.getAllTvshowById(tvShow.getId())){
                    Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_starko));
                    favoriteHelper.deleteFavoriteTvshow(tvShow.getId());
                }else if(!favoriteHelper.getAllTvshowById(tvShow.getId())){
                    Toast.makeText(this, "masuk", Toast.LENGTH_SHORT).show();
                    menuItem.setIcon(ContextCompat.getDrawable(this,R.drawable.ic_star));
                    favoriteHelper.insertFavoriteTvshow(tvShow);
                }
                break;
            case R.id.home:
                Intent intent= new  Intent(DetailTvShowActivity.this, MainActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
