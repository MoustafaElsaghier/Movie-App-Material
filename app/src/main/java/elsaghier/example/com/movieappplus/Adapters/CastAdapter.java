package elsaghier.example.com.movieappplus.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import elsaghier.example.com.movieappplus.Model.CastModel;
import elsaghier.example.com.movieappplus.R;



public class CastAdapter extends RecyclerView.Adapter<CastAdapter.MyViewHolder> {
    private List<CastModel> casts;
    private Context context;

    public CastAdapter(Context context, List<CastModel> casts) {
        this.casts = casts;
        this.context = context;
    }

    @Override

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CastModel model = casts.get(position);

        holder.setActorImage(model.getProfilePath());
        holder.setActorName(model.getName());
        holder.setCharName(model.getCharacter());
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView actorImage;
        private TextView charName;
        private TextView actorName;

        MyViewHolder(View v) {
            super(v);
            actorImage = itemView.findViewById(R.id.castImage);
            actorName = itemView.findViewById(R.id.actorName);
            charName = itemView.findViewById(R.id.charName);
        }

        void setActorName(String actName) {
            actorName.setText(actName);
        }

        void setCharName(String characterName) {
            charName.setText(characterName);
        }

        void setActorImage(String path) {
            Glide.with(context).load("http://image.tmdb.org/t/p/w185" + path).into(actorImage);
        }

    }
}
