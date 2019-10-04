package com.example.filmliburan.Preview.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.example.filmliburan.Preview.Main.Fragment.Favorite.FavoriteFragment;
import com.example.filmliburan.Preview.Main.Fragment.Movies.MoviesFragment;
import com.example.filmliburan.Preview.Main.Fragment.TvShow.TvShowFragment;
import com.example.filmliburan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private MoviesFragment movieFragment;
    private TvShowFragment tvShowFragment;
    private FavoriteFragment favoriteFragment;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
        tabFragment();
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.movies); // change to whichever id should be default
        }
    }

    private void initial() {
        movieFragment= new MoviesFragment();
        tvShowFragment= new TvShowFragment();
        favoriteFragment= new FavoriteFragment();
        bottomNavigationView= findViewById(R.id.menu_buttom_main);
    }
    private void tabFragment(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.movies:
                        setFragment(movieFragment);
                        return true;
                    case R.id.tv_show:
                        setFragment(tvShowFragment);
                        return true;
                    case R.id.favorite:
                        setFragment(favoriteFragment);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent= new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

//    private void profilViewPager() {
//        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new MoviesFragment()); // index 2
//        adapter.addFragment(new TvShowFragment()); //index 1
//        ViewPager viewPager = (ViewPager) findViewById(R.id.profil_viewPager);
//        viewPager.setAdapter(adapter);
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsProfil);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(1).setText(getString(R.string.movies));
//        tabLayout.getTabAt(0).setText(getString(R.string.tvshow));
//    }
}
