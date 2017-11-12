package elsaghier.example.com.movieappplus.FragmentsPack;


import android.content.ContentValues;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elsaghier.example.com.movieappplus.Adapters.CastAdapter;
import elsaghier.example.com.movieappplus.ApiWork.ApiClient;
import elsaghier.example.com.movieappplus.ApiWork.MovieInterFace;
import elsaghier.example.com.movieappplus.DataBase.FilmContract;
import elsaghier.example.com.movieappplus.DataBase.MyDBHelper;
import elsaghier.example.com.movieappplus.Model.CastModel;
import elsaghier.example.com.movieappplus.Model.CastResponse;
import elsaghier.example.com.movieappplus.Model.Film;
import elsaghier.example.com.movieappplus.Model.FilmsResponse;
import elsaghier.example.com.movieappplus.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static elsaghier.example.com.movieappplus.R.id.fab;


public class FilmInfoFragment extends Fragment {

    Map<Integer, String> genres = new HashMap<>();
    private Film model;
    ImageView backDrop;
    TextView filmName, filmYear, filmRating, filmGenre, filmOverView;

    RecyclerView CastRecycler;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    CastAdapter castAdapter;
    FloatingActionButton addToFav;
    MyDBHelper helper;

    MovieInterFace movieInterFace;
    Call<CastResponse> CastCall;
    Call<FilmsResponse> SimilarCall;
    Bundle b;
    List<CastModel> cast;

    public FilmInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_info_frag, container, false);
        b = getArguments();
        model = (Film) b.getSerializable("Film");

        //Initialize Component
        init(view);
        //First Section Data (Details)
        setFilmData();
        // Second Section data (Cast)
        getCastData();
        return view;
    }

    public void init(View view) {
        //Fill Generes
        genres.put(28, "Action");
        genres.put(12, "Adventure");
        genres.put(16, "Animation");
        genres.put(35, "Comedy");
        genres.put(80, "Crime");
        genres.put(99, "Documentary");
        genres.put(18, "Drama");
        genres.put(10751, "Family");
        genres.put(14, "Fantasy");
        genres.put(36, "History");
        genres.put(27, "Horror");
        genres.put(10402, "Music");
        genres.put(9648, "Mystery");
        genres.put(10749, "Romance");
        genres.put(878, "Science Fiction");
        genres.put(10770, "TV DB_Movie");
        genres.put(53, "Thriller");
        genres.put(10752, "War");
        genres.put(37, "Western");

        // First Section
        backDrop = view.findViewById(R.id.imgBack);
        filmGenre = view.findViewById(R.id.filmGenre);
        filmName = view.findViewById(R.id.filmName);
        filmRating = view.findViewById(R.id.filmRating);
        filmYear = view.findViewById(R.id.filmYear);
        filmOverView = view.findViewById(R.id.filmOverView);

        Glide.with(this).load("http://image.tmdb.org/t/p/w185" + model.getPosterPath()).into(backDrop);

        // Cast (Second Section)
        CastRecycler = view.findViewById(R.id.filmCasts);
        CastRecycler.setVisibility(View.INVISIBLE);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        CastRecycler.setLayoutManager(recyclerViewLayoutManager);



        movieInterFace = ApiClient.getClient().create(MovieInterFace.class);
        helper = new MyDBHelper(getContext());


        addToFav = view.findViewById(fab);
        if (model.getSelected().equals("1"))
            addToFav.setImageResource(android.R.drawable.star_big_on);
        else
            addToFav.setImageResource(android.R.drawable.btn_star_big_off);


        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FavouriteMessage = "";
                if (model.getSelected().equals("0")) {
                    addToFav.setImageResource(android.R.drawable.star_big_on);
                    model.setSelected("1");
                    FavouriteMessage = "Added To Favourite";
                    insertFilmInContent(model);
                } else {
                    FavouriteMessage = "Removed From Favourite";
                    addToFav.setImageResource(android.R.drawable.star_big_off);
                    model.setSelected("0");
                    deleteFilm(model.getId());
                }
                Snackbar.make(view, FavouriteMessage, BaseTransientBottomBar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (model.getSelected().equals("1")) {
                                    addToFav.setImageResource(android.R.drawable.star_big_off);
                                    model.setSelected("0");
                                    deleteFilm(model.getId());
                                } else {
                                    addToFav.setImageResource(android.R.drawable.star_big_on);
                                    model.setSelected("1");
                                    insertFilmInContent(model);
                                }
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });

    }

    private void deleteFilm(int filmId) {
        getActivity().getContentResolver().delete(FilmContract.CONTENT_URI, "id=?", new String[]{String.valueOf(filmId)});
    }

    private void insertFilmInContent(Film model) {
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.id, model.getId());
        values.put(FilmContract.FilmEntry.imgUrl, model.getPosterPath());
        values.put(FilmContract.FilmEntry.overview, model.getOverview());
        values.put(FilmContract.FilmEntry.original_title, model.getTitle());
        values.put(FilmContract.FilmEntry.vote_average, model.getVoteAverage());
        values.put(FilmContract.FilmEntry.release_date, model.getReleaseDate());
        values.put(FilmContract.FilmEntry.backdrop_path, model.getBackdropPath());
        values.put(FilmContract.FilmEntry.isSelected, model.getSelected());
        values.put(FilmContract.FilmEntry.Generes, model.getGeneres());
        Uri uri = getActivity().getContentResolver().insert(FilmContract.CONTENT_URI, values);
    }

    public void setFilmData() {
        filmName.setText(model.getTitle());
        filmYear.setText(model.getReleaseDate());
        filmRating.setText(String.valueOf(model.getVoteAverage()));
        filmOverView.setText(model.getOverview());
        String listString = "", genere = "";
        if (b.getBoolean("Fav")) {
            genere = model.getGeneres();
        } else
            genere = model.getGeneres();
        String[] generesId;
        if (!genere.equals(""))
            generesId = genere.split(",");
        else
            generesId = new String[0];
        listString = "";
        for (int index = 0; index < generesId.length; ++index) {
            int genNum = Integer.parseInt(generesId[index]);
            listString += genres.get(genNum);
            if (index != generesId.length - 1)
                listString += ", ";
        }
        filmGenre.setText(listString);
    }

    public void getCastData() {
        System.out.println(model.getId() + " THIS IS ID");
        CastCall = movieInterFace.getFilmCasts("movie/" + String.valueOf(model.getId() + "/credits"), getResources().getString(R.string.api_key));
        CastCall.enqueue(new Callback<CastResponse>() {
            @Override
            public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                cast = response.body().getCast();
                CastRecycler.setVisibility(View.VISIBLE);

                castAdapter = new CastAdapter(getContext(), cast);
                CastRecycler.setAdapter(castAdapter);
            }

            @Override
            public void onFailure(Call<CastResponse> call, Throwable t) {

            }
        });
    }



}
