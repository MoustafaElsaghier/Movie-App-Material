package elsaghier.example.com.movieappplus.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import elsaghier.example.com.movieappplus.DataBase.FilmContract;
import elsaghier.example.com.movieappplus.FilmDetailsActivity;
import elsaghier.example.com.movieappplus.Model.Film;
import elsaghier.example.com.movieappplus.R;

/**
 * Created by ELSaghier on 10/16/2017.
 */

public class FilmCursorAdapter extends CursorAdapter {

    private Context mContext;
    private static int sLoaderID;

    public static class ViewHolder {
        public final ImageView poster;

        public ViewHolder(View view) {
            poster = (ImageView) view.findViewById(R.id.poster_img);
        }
    }


    public FilmCursorAdapter(Context context, Cursor c, int flags, int LoaderId) {
        super(context, c, flags);
        sLoaderID = LoaderId;
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        int layoutId = R.layout.main_recyc_item;

        View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        Glide.with(context).load("http://image.tmdb.org/t/p/w185" + cursor.getString(cursor.getColumnIndex
                (FilmContract.FilmEntry.imgUrl))).into(viewHolder.poster);

        viewHolder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Film model = new Film();
                model.setBackdropPath(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.backdrop_path)));

                model.setOverview(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.overview)));

                model.setPosterPath(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.imgUrl)));

                model.setTitle(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.original_title)));

                model.setVoteAverage(cursor.getDouble(cursor.getColumnIndex
                        (FilmContract.FilmEntry.vote_average)));

                model.setReleaseDate(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.release_date)));

                model.setSelected(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.isSelected)));

                model.setGeneres(cursor.getString(cursor.getColumnIndex
                        (FilmContract.FilmEntry.Generes)));
                Intent intent = new Intent(mContext, FilmDetailsActivity.class);
                if (model.getSelected().equals("1")) {
                    intent.putExtra("Favourite", true);
                }
                intent.putExtra("film", model);
                mContext.startActivity(intent);
            }
        });


    }
}
