package com.ndondot.sapiku;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.ndondot.sapiku.Adapter.PeternakAdapter;
import com.ndondot.sapiku.Adapter.PeternakModel;
import com.ndondot.sapiku.Fragment.FilterFragment;
import com.ndondot.sapiku.Adapter.FragmentAdapter;
import com.ndondot.sapiku.Fragment.HistoryFragment;
import com.ndondot.sapiku.Fragment.HomeFragment;
import com.ndondot.sapiku.Fragment.NearestFragment;
import com.ndondot.sapiku.Fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getResources().getStringArray(R.array.title)[0]);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_nearest:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_filter:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navigation_history:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.navigation_profile:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null)
                    prevMenuItem.setChecked(false);
                else
                    bottomNavigationView.getMenu().getItem(position).setChecked(false);
                setTitle(getResources().getStringArray(R.array.title)[position]);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragmet(new HomeFragment());
        adapter.addFragmet(new NearestFragment());
        adapter.addFragmet(new FilterFragment());
        adapter.addFragmet(new HistoryFragment());
        adapter.addFragmet(new ProfileFragment());
        viewPager.setAdapter(adapter);
    }
}
