package com.iot.mobiledevelopment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Movie> movieList;
    private LayoutInflater layoutInflater;

    CustomAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);

        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        holder.textTitle.setText(movieList.get(position).getTitle());
        holder.textDesctiption.setText(movieList.get(position).getDescription());
        Picasso.get().load(movieList.get(position).getPoster()).into(holder.poster);
        holder.movieYear.setText(String.format("Year: %s", Integer.toString(movieList.get(position).getYear())));
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesctiption;
        private ImageView poster;
        private TextView movieYear;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            textDesctiption = itemView.findViewById(R.id.custom_desctription);
            poster = itemView.findViewById(R.id.custom_imageView);
            textTitle = itemView.findViewById(R.id.custom_title);
            movieYear = itemView.findViewById(R.id.custom_year);
        }

    }

}
