package cz.muni.fi.pv256.movio2.uco_396546_themoviedb;

import android.os.Parcelable;

/**
 * Created by Huvart on 04/10/16.
 */

public class Movie implements Parcelable {

    private long mReleaseDate;
    private String mCoverPath;
    private String mTitle;
    private String mBackdrop;
    private float mPopularity;

    public Movie(long releaseDate, String coverPath, String title, String backdrop, float popularity){
        mReleaseDate = releaseDate;
        mCoverPath = coverPath;
        mTitle = title;
        mBackdrop = backdrop;
        mPopularity = popularity;
    }




}
