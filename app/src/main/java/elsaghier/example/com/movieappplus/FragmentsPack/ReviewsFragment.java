package elsaghier.example.com.movieappplus.FragmentsPack;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import elsaghier.example.com.movieappplus.Adapters.ReviewAdapter;
import elsaghier.example.com.movieappplus.ApiWork.ApiClient;
import elsaghier.example.com.movieappplus.ApiWork.MovieInterFace;
import elsaghier.example.com.movieappplus.Model.Film;
import elsaghier.example.com.movieappplus.Model.ReviewModel;
import elsaghier.example.com.movieappplus.Model.ReviewResponse;
import elsaghier.example.com.movieappplus.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class ReviewsFragment extends Fragment {

    RecyclerView reviews;
    ReviewAdapter reviewAdapter;
    MovieInterFace movieInterFace;
    Call<ReviewResponse> ReviewsCall;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    private Film model;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_frag, container, false);
        Bundle b = getArguments();
        model = (Film) b.getSerializable("Film");

        init(view);
        getReviews();
        return view;
    }

    public void init(View view) {
        reviews = view.findViewById(R.id.reviewsRecycler);
        reviews.setVisibility(View.INVISIBLE);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        reviews.setLayoutManager(recyclerViewLayoutManager);
    }

    public void getReviews() {
        movieInterFace = ApiClient.getClient().create(MovieInterFace.class);
        ReviewsCall = movieInterFace.getFilmReviews("movie/" + model.getId() + "/reviews", getResources().getString(R.string.api_key));
        ReviewsCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                reviews.setVisibility(View.VISIBLE);
                List<ReviewModel> data = response.body().getResults();
                reviewAdapter = new ReviewAdapter(getContext(), data);
                reviews.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                System.out.println("Errot " + t.getMessage());
            }
        });
    }
}
