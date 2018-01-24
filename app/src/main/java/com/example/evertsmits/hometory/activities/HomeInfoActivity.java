package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.evertsmits.hometory.R;
import com.example.evertsmits.hometory.tabs.HouseInfoTab;
import com.example.evertsmits.hometory.tabs.InventoryInfoTab;

/**
 * class HomeInfoActivity created by Evert Smits
 * this class is used to contain the fragments in the tabbed layout.
 */

public class HomeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_info);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    /**
     * BackToCategoryOverview function to return to the category overview page
     *
     * @param view is used because the xml has an onclick
     */
    public void backToCategoryOverview(View view) {
        Intent intent = new Intent(this, CategoryOverviewActivity.class);
        startActivity(intent);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {


        /**
         * SectionsPagerAdapter constructor for an pager adapter
         *
         * @param fm fragment manager provided to the object
         */
        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * getItem is used to gain the right fragment for the tab
         *
         * @param position current position of the tabbed layout
         * @return returns a new fragment
         */
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new HouseInfoTab();
                case 1:
                    return new InventoryInfoTab();
                default:
                    return null;
            }
        }

        /**
         * getCount function that returns the amount of pages
         *
         * @return amount of pages
         */
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        /**
         * getPageTitle function that supports the tabbed layout to show titles
         *
         * @param position takes the position to determine which title goes to the current
         * @return the proper title
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "House Info";
                case 1:
                    return "Total inventory";
            }
            return null;
        }
    }
}
