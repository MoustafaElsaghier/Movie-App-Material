package elsaghier.example.com.movieappplus.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ELSaghier on 8/29/2017.
 */

public class FilmsResponse {
    @SerializedName("results")
    private List<Film> film = new ArrayList<>();
    @SerializedName("page")
    private
    int page;
    @SerializedName("total_results")
    private
    int tot_res;
    @SerializedName("total_pages")
    private
    int tot_pages;

    @Nullable
    public List<Film> getFilms() {
        return film;
    }

    public void setFilm(List<Film> film) {
        this.film = film;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTot_res() {
        return tot_res;
    }

    public void setTot_res(int tot_res) {
        this.tot_res = tot_res;
    }

    public int getTot_pages() {
        return tot_pages;
    }

    public void setTot_pages(int tot_pages) {
        this.tot_pages = tot_pages;
    }
}
