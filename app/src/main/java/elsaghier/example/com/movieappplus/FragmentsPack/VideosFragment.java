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

import elsaghier.example.com.movieappplus.Adapters.VideoAdapter;
import elsaghier.example.com.movieappplus.ApiWork.ApiClient;
import elsaghier.example.com.movieappplus.ApiWork.MovieInterFace;
import elsaghier.example.com.movieappplus.Model.Film;
import elsaghier.example.com.movieappplus.Model.VideoModel;
import elsaghier.example.com.movieappplus.Model.VideoResponse;
import elsaghier.example.com.movieappplus.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideosFragment extends Fragment {
    MovieInterFace movieInterFace;
    Call<VideoResponse> VideoCall;
    private Film model;
    RecyclerView videoRecycler;
    VideoAdapter videoAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_frag, container, false);
        Bundle b = getArguments();
        model = (Film) b.getSerializable("Film");
        init(view);
        getVideos();
        return view;
    }

    public void init(View view) {
        videoRecycler = view.findViewById(R.id.videos);
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        movieInterFace = ApiClient.getClient().create(MovieInterFace.class);
        videoRecycler.setLayoutManager(recyclerViewLayoutManager);
    }

    public void getVideos() {
        VideoCall = movieInterFace.getVideos("movie/" + String.valueOf(model.getId() + "/videos"), getResources().getString(R.string.api_key));

        VideoCall.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                List<VideoModel> data = response.body().getResults();
                videoAdapter = new VideoAdapter(getContext(), data);
                videoRecycler.setAdapter(videoAdapter);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {

            }
        });
    }
}
