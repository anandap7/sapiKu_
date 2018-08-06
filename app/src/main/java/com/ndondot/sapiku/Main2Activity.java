package com.ndondot.sapiku;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ndondot.sapiku.Adapter.FragmentAdapter;
import com.ndondot.sapiku.Fragment.BlankFragment;
import com.ndondot.sapiku.Fragment.FilterFragment;
import com.ndondot.sapiku.Fragment.HistoryFragment;
import com.ndondot.sapiku.Fragment.HomeFragment;
import com.ndondot.sapiku.Fragment.NearestFragment;
import com.ndondot.sapiku.Fragment.ColaborationFragment;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    CircleImageView mPhotoAccount;
    TextView mNameAccount;
    NavigationView navigationView;

    FirebaseUser user;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = FirebaseAuth.getInstance().getCurrentUser();

        setTitle(getResources().getStringArray(R.array.title)[0]);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.getHeaderView(0);
        mPhotoAccount = view.findViewById(R.id.photo_account_id);
        mNameAccount = view.findViewById(R.id.name_account);
        navigationView.setNavigationItemSelectedListener(this);

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
                    case R.id.navigation_colaboration:
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

        getDataAccount();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (user!=null){
            if (id == R.id.nav_account) {
                Toast.makeText(this, "Profil", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_notifikasi) {

            } else if (id == R.id.nav_alamat) {

            } else if (id == R.id.nav_riwayat) {

            } else if (id == R.id.nav_pengaturan) {

            } else if (id == R.id.nav_faq) {

            } else if (id == R.id.nav_tentang_kami){

            } else if (id == R.id.nav_signOut){
                signOut();
            }

        }else{
            if (id == R.id.nav_account) {
                startActivity(new Intent(this,SignInActivity.class));
            } else if (id == R.id.nav_notifikasi) {

            } else if (id == R.id.nav_alamat) {
                DatabaseReference ref = mRef.child("coba");
                ref.child("1").setValue("coba");
            } else if (id == R.id.nav_riwayat) {

            } else if (id == R.id.nav_pengaturan) {

            } else if (id == R.id.nav_faq) {

            } else if (id == R.id.nav_tentang_kami){

            } else if ((id== R.id.nav_signOut)){

            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setBackgroundColor(getResources().getColor(R.color.background_side_bar));
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupViewPager(ViewPager viewPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragmet(new HomeFragment());
        adapter.addFragmet(new NearestFragment());
        adapter.addFragmet(new FilterFragment());
        adapter.addFragmet(new HistoryFragment());
        adapter.addFragmet(new ColaborationFragment());
        viewPager.setAdapter(adapter);
    }

    public void getDataAccount(){
        if (user!=null){
            String uId = user.getUid();
            DatabaseReference ref = mRef.child("pembeli").child(uId);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("namaLengkap").getValue(String.class);
                    String imageUri = dataSnapshot.child("imageUri").getValue(String.class);
                    Picasso.get().load(imageUri).into(mPhotoAccount);
                    mNameAccount.setText(name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent signOut = new Intent(Main2Activity.this, Main2Activity.class);
        signOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(signOut);
        finish();
    }

}
