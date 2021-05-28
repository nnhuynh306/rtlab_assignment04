package vn.edu.hcmus.fit.mssv18127014_18127208.map.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;


import java.util.ArrayList;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.Models.TabFragment;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments.DataListFragment;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments.MapFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

    ArrayList<TabFragment> tabFragments = new ArrayList<>();


    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void setTabFragments(ArrayList<TabFragment> tabFragments) {
        this.tabFragments = tabFragments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabFragments.get(position).getTitle();
    }

}
