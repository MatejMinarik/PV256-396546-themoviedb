package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by Huvart on 10/10/16.
 */

public class MovieAdapter extends BaseAdapter{

        private Context mAppContext;
        private ArrayList<Movie> mMovieList;

        public MovieAdapter(ArrayList<Movie> movieList, Context context) {
            mMovieList = movieList;
            mAppContext = context.getApplicationContext();
        }

        @Override
        public int getCount() {
            return mMovieList.size();
        }

        @Override
        public Object getItem(int position) {
            return mMovieList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(mAppContext).inflate(R.layout.list_item_movie, parent, false);
                ViewHolder holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.bindView(mAppContext, mMovieList.get(position));

            return convertView;
        }

        private static class ViewHolder {
            private ImageView coverIv;

            public ViewHolder(View view) {
                coverIv = (ImageView) view.findViewById(R.id.list_item_icon);
            }

            public void bindView(Context context, Movie movie) {
                if (movie == null)  return;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    coverIv.setImageDrawable(context.getDrawable(movie.mCoverId));
                } else {
                    coverIv.setImageDrawable(context.getResources().getDrawable(movie.mCoverId, context.getTheme()));
                }
                coverIv.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
}
