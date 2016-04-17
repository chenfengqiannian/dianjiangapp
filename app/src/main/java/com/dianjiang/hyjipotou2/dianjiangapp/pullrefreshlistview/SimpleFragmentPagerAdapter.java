package com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter{

        final int PAGE_COUNT = 4;
        private String tabTitles[] = new String[]{"已发布工程","未发布工程","招标中工程","已完工工程"};
        private Context context;

        public SimpleFragmentPagerAdapter(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            return sFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
}
