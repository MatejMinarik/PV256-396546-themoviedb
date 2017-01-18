package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Genre;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.Movie;

/**
 * Created by Huvart on 23/10/2016.
 */

public class GenresListRecyclerAdapter extends RecyclerView.Adapter<GenresListRecyclerAdapter.ViewHolder> {

    private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
    private Context mContext;
    private ArrayList<Genre> mGenreList;
    private LayoutInflater mInflater;

    public GenresListRecyclerAdapter(LayoutInflater inflater, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener, Context context, ArrayList<Genre> genreList) {
        mGenreList = genreList;
        mContext = context;
        mListener = listener;
        mInflater = inflater;
    }

    public ArrayList<Genre> getGenreList() {
        return mGenreList;
    }

    public void setGenreList(ArrayList<Genre> genreList) {
        mGenreList = genreList;
    }

    @Override
    public GenresListRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.list_item_genre, parent, false);
        return new GenresListRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenresListRecyclerAdapter.ViewHolder holder, int position) {
        Genre item = mGenreList.get(position);
        holder.bindView(mContext, item, mListener);
    }

    @Override
    public int getItemCount() {
        return mGenreList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private Genre mGenre;
        private RecyclerView mMovieView;
        private TextView mNameView;
        private MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener mListener;
        private Context mContext;
        public MovieListRecyclerAdapter mAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            mMovieView = (RecyclerView) itemView.findViewById(R.id.recycler_view_genres);
            mNameView = (TextView) itemView.findViewById(R.id.genres_name);
        }


        public void bindView(Context context, Genre item, MovieListRecyclerAdapter.ViewHolder.OnMovieSelectListener listener) {
            mListener = listener;
            mContext = context;
            mNameView.setText(item.getGanreName());

            mGenre = item;

            mMovieView.setHasFixedSize(true);

            mMovieView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            mMovieView.setItemAnimator(new DefaultItemAnimator());

            if (mGenre.getMovieList() != null && !mGenre.getMovieList().isEmpty()){
                setAdapter(mMovieView, mGenre.getMovieList());
            }
        }

        private void setAdapter(RecyclerView movieView, List<Movie> movieList) {
            mAdapter = new MovieListRecyclerAdapter(mListener, mContext, movieList);
            movieView.setAdapter(mAdapter);
        }

    }


}
