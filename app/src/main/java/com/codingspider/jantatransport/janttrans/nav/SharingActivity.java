package com.codingspider.jantatransport.janttrans.nav;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.codingspider.jantatransport.janttrans.R;
import com.codingspider.jantatransport.janttrans.adapter.AdapterShare;
import com.codingspider.jantatransport.janttrans.fragment.OneFragment;
import com.codingspider.jantatransport.janttrans.fragment.TwoFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;



public class SharingActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static final String REGISTER_URL = "http://jt.codingspider.com/jt_app/jt_booked.php";
    SharedPreferences sharedpreferences;
    SharedPreferences prefs1;

    String phone, result;
    private RecyclerView mRVFishPrice;
    private AdapterShare mAdapter;
    String id1, phone1, email1, fname1, lname1, date1, isLoggedin;

    public SharingActivity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_sharing_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//
//        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs1 = this.getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        id1 = prefs1.getString("pref_id"," ");
        phone = prefs1.getString("pref_name"," ");
        email1 = prefs1.getString("pref_email"," ");
        fname1 = prefs1.getString("pref_fname"," ");
        lname1 = prefs1.getString("pref_lname"," ");
        date1 = prefs1.getString("pref_date"," ");

        System.out.println("IsLoggedIn On= " + isLoggedin);
        System.out.println("phone1 On= " + phone);
        System.out.println("email1 On= " + email1);
        System.out.println("fname1 On= " + fname1);
        System.out.println("lname1 On= " + lname1);
        System.out.println("date1 On= " + date1);


       /* toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);



        /*PlaceFromTo();*/

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new OneFragment(), "HALF SHARE");
        adapter.addFragment(new TwoFragment(), "LESS HALF");
        //    adapter.addFragment(new ExFragment(), "Example");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}