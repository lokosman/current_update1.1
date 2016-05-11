package solution.kelsoft.com.kwahuguide.Activities;

import android.content.ComponentName;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import solution.kelsoft.com.kwahuguide.CustomMessage;
import solution.kelsoft.com.kwahuguide.Fragment.AttractionFragment;
import solution.kelsoft.com.kwahuguide.Fragment.HotelFragment;
import solution.kelsoft.com.kwahuguide.R;
import solution.kelsoft.com.kwahuguide.Services.MyService;

public class MainActivity extends AppCompatActivity {
    public static final int FRAG_Attraction = 0;
    public static final int FRAG_Hotel = 1;
    private static final int JOB_ID = 100;
    private PagerAdapter mAdapter;
    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager pager;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mJobScheduler = JobScheduler.getInstance(this);
        constructJob();
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

    //Method for handling a jobScheduler here
    private void constructJob() {
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, MyService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_overflow) {
            CustomMessage.t(this, "Setting selected");
            return true;
        }
        if (id == R.id.exit) {
            this.finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_overflow);
        MenuItem itemSearch = menu.findItem(R.id.action_search);
        return true;
    }

    class task extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }


    //Class PagerAdapter that hold all the corresponding field of the various Fragment
    private class PagerAdapter extends FragmentStatePagerAdapter {
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
                //Adding Event Tab for trying

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
