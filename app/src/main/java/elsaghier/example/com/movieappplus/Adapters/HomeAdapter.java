package elsaghier.example.com.movieappplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import elsaghier.example.com.movieappplus.Model.Film;
import elsaghier.example.com.movieappplus.R;
import elsaghier.example.com.movieappplus.FilmDetailsActivity;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Film> Films;
    private Context context;
  //  private boolean inFav;

    public HomeAdapter(List<Film> Films, Context context/*, boolean */) {
        this.Films = Films;
        this.context = context;
    //    this.inFav = inFav;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyc_item, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Film model = Films.get(position);

        Glide.with(context).load("http://image.tmdb.org/t/p/w185" + model.getPosterPath()).into(holder.poster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, FilmDetailsActivity.class);
                if (model.getSelected().equals("1")) {
                    intent.putExtra("Favourite", true);
                }
                intent.putExtra("film", model);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Films.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;

        MyViewHolder(View v) {
            super(v);
            poster = itemView.findViewById(R.id.poster_img);
        }
    }
}
