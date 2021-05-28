package vn.edu.hcmus.fit.mssv18127014_18127208.map.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import vn.edu.hcmus.fit.mssv18127014_18127208.map.Adapters.MainPagerAdapter;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.Models.TabFragment;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.R;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments.DataListFragment;
import vn.edu.hcmus.fit.mssv18127014_18127208.map.Views.Fragments.MapFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout ;
    MainPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getViews();
        setupVariables();

        setupFragmentPagerAdapter();
        setupTabLayout();
    }

    private void getViews() {
        this.tabLayout = findViewById(R.id.tabLayout);
    }

    private void setupVariables() {
        this.fragmentPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    private void setupTabLayout() {
        this.tabLayout.setTabsFromPagerAdapter(this.fragmentPagerAdapter);
        TabLayout.Tab tab = this.tabLayout.getTabAt(0);
        if (tab != null) {
            tab.select();
        }
    }

    private void setupFragmentPagerAdapter() {
        ArrayList<TabFragment> tabFragments = new ArrayList<>();
        tabFragments.add(new TabFragment(new DataListFragment(), 0, getString(R.string.list)));
        tabFragments.add(new TabFragment(new MapFragment(), 0, getString(R.string.map)));

        this.fragmentPagerAdapter.setTabFragments(tabFragments);
    }
}