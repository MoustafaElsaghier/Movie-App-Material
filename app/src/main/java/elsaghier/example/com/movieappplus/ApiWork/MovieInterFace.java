package elsaghier.example.com.movieappplus.ApiWork;

import elsaghier.example.com.movieappplus.Model.CastResponse;
import elsaghier.example.com.movieappplus.Model.FilmsResponse;
import elsaghier.example.com.movieappplus.Model.ReviewResponse;
import elsaghier.example.com.movieappplus.Model.VideoResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MovieInterFace {
    // Movies
    @GET("movie/popular")
    Call<FilmsResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<FilmsResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<FilmsResponse> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<FilmsResponse> getUpComingMovies(@Query("api_key") String apiKey);

    @GET
    Call<CastResponse> getFilmCasts(@Url String movieId, @Query("api_key") String apiKey);

    @GET
    Call<VideoResponse> getVideos(@Url String movieId, @Query("api_key") String apiKey);

    @GET
    Call<ReviewResponse> getFilmReviews(@Url String movieId, @Query("api_key") String apiKey);


}
