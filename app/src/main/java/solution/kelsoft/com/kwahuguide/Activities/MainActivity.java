package solution.kelsoft.com.kwahuguide.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import solution.kelsoft.com.kwahuguide.Fragment.AttractionFragment;
import solution.kelsoft.com.kwahuguide.Fragment.HotelFragment;
import solution.kelsoft.com.kwahuguide.R;

public class MainActivity extends AppCompatActivity {
    public static final int FRAG_Attraction = 0;
    public static final int FRAG_Hotel = 1;
    private PagerAdapter mAdapter;
    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new PagerAdapter(getSupportFragmentManager());

        //Initializing the widget toolbar and setting it to display
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Initializing the widget TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Initializing ViewPager
        pager = (ViewPager) findViewById(R.id.mPager);
        pager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(pager);
    }


    //Class PagerAdapter that hold all the corresponding field of the various Fragment
    class PagerAdapter extends FragmentStatePagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
            // this.Title = title;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch (position) {
                case FRAG_Attraction:
                    fragment = AttractionFragment.newInstance("", "");
                    break;
                case FRAG_Hotel:
                    fragment = HotelFragment.newInstance(" ", " ");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            String title = " ";
            switch (position) {
                case 0:
                    title = "Attractions";
                    break;
                case 1:
                    title = "Hotels";
                    break;
            }

            return title;
        }
    }
}
