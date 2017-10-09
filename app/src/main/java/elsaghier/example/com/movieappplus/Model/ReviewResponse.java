package elsaghier.example.com.movieappplus.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ELSaghier on 9/6/2017.
 */


public class ReviewResponse {

    @SerializedName("results")
    private List<ReviewModel> results = null;

    public List<ReviewModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewModel> results) {
        this.results = results;
    }

}
