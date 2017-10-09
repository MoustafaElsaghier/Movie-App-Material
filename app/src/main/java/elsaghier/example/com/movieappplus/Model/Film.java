package elsaghier.example.com.movieappplus.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Film implements Serializable {

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    private String selected = "0";
    @SerializedName("id")
    private int id;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();

    @SerializedName("backdrop_path")
    private String backdropPath;

    private String Generes="";

    public String getGeneres() {
        if (genreIds.size() != 0) {
            for (int i = 0; i < genreIds.size(); ++i)
                Generes += genreIds.get(i) + ",";
            return Generes;
        } else
            return Generes;
    }

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    /**
     * No args constructor for use in serialization
     */
    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


    public void setGeneres(String generes) {
        Generes = generes;
        String s[] = Generes.split(",");
        for (int i = 0; i < s.length; ++i) {
            genreIds.add(i, Integer.parseInt(s[i]));
        }
    }
}