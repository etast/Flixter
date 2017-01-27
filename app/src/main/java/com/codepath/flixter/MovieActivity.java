package com.codepath.flixter;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.codepath.flixter.adapters.MovieArrayAdapter;
import com.codepath.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {
    private SwipeRefreshLayout swipeContainer;

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                @Override
                                                public void onRefresh() {
                                                    fetchMovieData();
                                                }
                                            }

        );
        lvItems = (ListView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        fetchMovieData();
    }

    private void fetchMovieData () {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String ResponseData = response.body().string();
                    MovieActivity.this.runOnUiThread(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             JSONArray movieJsonResults = null;
                                                             try {
                                                                 JSONObject json = new JSONObject(ResponseData);
                                                                 movieJsonResults = json.getJSONArray("results");
                                                                 movies.clear();
                                                                 movies.addAll(Movie.fromJSONArray(movieJsonResults));
                                                                 movieAdapter.notifyDataSetChanged();
                                                                 swipeContainer.setRefreshing(false);
                                                             } catch (JSONException e) {
                                                                 e.printStackTrace();
                                                             }
                                                         }
                                                     }
                    );
            }
        });
    }
}
