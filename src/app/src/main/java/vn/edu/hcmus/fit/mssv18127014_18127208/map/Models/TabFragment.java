package vn.edu.hcmus.fit.mssv18127014_18127208.map.Models;

import androidx.fragment.app.Fragment;

public class TabFragment {
    private Fragment fragment;
    private int icon;
    private String title;

    public TabFragment(Fragment fragment, int icon, String title) {
        this.fragment = fragment;
        this.icon = icon;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
