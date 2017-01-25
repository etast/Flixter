package com.codepath.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flixter.R;
import com.codepath.flixter.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flixter.R.id.tvOverview;
import static com.codepath.flixter.R.id.tvTitle;

/**
 * Created by etast on 1/24/17.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    private static class ViewHolder {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivImage;
    }
    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;
        String imgPath;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(tvOverview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivImage.setImageResource(0);
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverview());
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imgPath = movie.getBackdropPath();
        } else {
            imgPath = movie.getPosterPath();
        }
        Picasso.with(getContext()).load(imgPath).transform(new RoundedCornersTransformation(30, 30)).placeholder(R.drawable.movie_night).into(viewHolder.ivImage);
        return convertView;
    }
}
