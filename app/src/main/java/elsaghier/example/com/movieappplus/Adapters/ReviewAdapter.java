package elsaghier.example.com.movieappplus.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import elsaghier.example.com.movieappplus.Model.ReviewModel;
import elsaghier.example.com.movieappplus.R;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private List<ReviewModel> reviews;
    private Context context;

    public ReviewAdapter(Context context, List<ReviewModel> casts) {
        this.reviews = casts;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ReviewModel model = reviews.get(position);

        holder.setContent(model.getContent());
        holder.setAuthor(model.getAuthor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getContent().length() > 100) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle(model.getAuthor())
                            .setMessage(model.getContent())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView content;

        MyViewHolder(View v) {
            super(v);
            author = itemView.findViewById(R.id.review_author);
            content = itemView.findViewById(R.id.review_content);
        }

        void setAuthor(String authorName) {
            author.setText(authorName);
        }

        void setContent(String reviewContent) {
            if (reviewContent.length() > 200)
                content.setText(reviewContent.substring(0, 150) + " ...");
            else
                content.setText(reviewContent);
        }
    }
}
