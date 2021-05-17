package com.example.androidsqlitereader.ui.main;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.androidsqlitereader.DataFragment;
import com.example.androidsqlitereader.Overview;
import com.example.androidsqlitereader.QueryFragment;
import com.example.androidsqlitereader.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    private ArrayList<Fragment> fragments;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        fragments = new ArrayList<Fragment>();

        fragments.add(new Overview());
        fragments.add(new DataFragment());
        fragments.add(new QueryFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Overview";
            case 1:
                return "Details";
            case 2:
                return "SQL Query";
            default:
                return "Undefined";
        }
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    public Fragment GetFragment(int id)
    {
        return fragments.get(id);
    }
}