package elsaghier.example.com.movieappplus.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ELSaghier on 9/6/2017.
 */

public class VideoResponse implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<VideoModel> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoModel> getResults() {
        return results;
    }

    public void setResults(List<VideoModel> results) {
        this.results = results;
    }

}
