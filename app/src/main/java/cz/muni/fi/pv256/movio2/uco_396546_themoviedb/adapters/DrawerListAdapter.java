package cz.muni.fi.pv256.movio2.uco_396546_themoviedb.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.R;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.DownloadApiInterface;
import cz.muni.fi.pv256.movio2.uco_396546_themoviedb.model.HamburgerMenuItem;

/**
 * Created by Huvart on 08/01/2017.
 */

public class DrawerListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<HamburgerMenuItem> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<HamburgerMenuItem> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }else {
            view = convertView;
        }
        TextView titleView = (TextView) view.findViewById(R.id.drawer_item_text);
        //ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( mNavItems.get(position).getText() );
        //iconView.setImageResource(mNavItems.get(position).mIcon);

        return view;
    }
}