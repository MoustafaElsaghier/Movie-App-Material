package elsaghier.example.com.movieappplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import elsaghier.example.com.movieappplus.Model.VideoModel;
import elsaghier.example.com.movieappplus.R;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private List<VideoModel> videos;
    private Context context;

    public VideoAdapter(Context context, List<VideoModel> videos) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VideoModel model = videos.get(position);

        holder.setTittle(model.getName());
        holder.setThumbnails(model.getKey());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + model.getKey()));
                context.startActivity(Intent.createChooser(intent, "Select Preferred One"));
            }
        });
    }

    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnails;
        private TextView Tittle;

        MyViewHolder(View v) {
            super(v);
            thumbnails = itemView.findViewById(R.id.videoImage);
            Tittle = itemView.findViewById(R.id.videoTittle);
        }

        void setThumbnails(String key) {
            Glide.with(context).load("http://img.youtube.com/vi/" + key + "/1.jpg").into(thumbnails);
        }

        void setTittle(String videoTittle) {
            Tittle.setText(videoTittle);
        }
    }
}
