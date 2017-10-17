package elsaghier.example.com.movieappplus;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import elsaghier.example.com.movieappplus.FragmentsPack.FilmInfoFragment;
import elsaghier.example.com.movieappplus.FragmentsPack.ReviewsFragment;
import elsaghier.example.com.movieappplus.FragmentsPack.VideosFragment;
import elsaghier.example.com.movieappplus.Model.Film;

public class FilmDetailsActivity extends AppCompatActivity {
    private ImageView poster;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    boolean favourite = false;
    Film film;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_film);
        //Intailize Component
        savedInstanceState = new Bundle();
        init(savedInstanceState);

        poster = findViewById(R.id.FilmPoster);
        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + film.getBackdropPath()).into(poster);

    }

    public void init(final Bundle savedInstanceState) {

        //Toolbar Section
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        // displaying back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // get Data
        Intent intent = getIntent();
        film = (Film) intent.getSerializableExtra("film");
        favourite = intent.getBooleanExtra("Favourite", false);

        // tab section
        tableLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.ViewPager);

        // ViewPager Adapter
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String fragments[] = {"INFO", "Reviews", "Videos"};

            @Override
            public Fragment getItem(int position) {
                savedInstanceState.putSerializable("Film", film);
                savedInstanceState.putBoolean("Fav", favourite);
                switch (position) {
                    case 0:
                        FilmInfoFragment fragment = new FilmInfoFragment();
                        fragment.setArguments(savedInstanceState);
                        return fragment;
                    case 1:
                        ReviewsFragment fragment1 = new ReviewsFragment();
                        fragment1.setArguments(savedInstanceState);
                        return fragment1;
                    case 2:
                        VideosFragment fragment2 = new VideosFragment();
                        fragment2.setArguments(savedInstanceState);
                        return fragment2;
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragments[position];
            }
        });
        tableLayout.setupWithViewPager(viewPager);
    }

}
